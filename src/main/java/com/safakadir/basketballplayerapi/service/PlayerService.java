package com.safakadir.basketballplayerapi.service;

import com.safakadir.basketballplayerapi.exception.MaximumCapacityReachedException;
import com.safakadir.basketballplayerapi.model.Player;
import com.safakadir.basketballplayerapi.model.PlayerHistory;
import com.safakadir.basketballplayerapi.model.PlayerPage;
import com.safakadir.basketballplayerapi.model.PlayerPosition;
import com.safakadir.basketballplayerapi.repository.PlayerHistoryRepository;
import com.safakadir.basketballplayerapi.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerHistoryRepository playerHistoryRepository;

    @Value("${app.maxTeamSize}")
    private int MAX_TEAM_SIZE;

    public PlayerService(PlayerRepository playerRepository,
                         PlayerHistoryRepository playerHistoryRepository) {
        this.playerRepository = playerRepository;
        this.playerHistoryRepository = playerHistoryRepository;
    }

    public PlayerPage findAllPlayers(int page, int pageSize) {
        Page<Player> pageResult = playerRepository.findAll(PageRequest.of(page, pageSize));
        return new PlayerPage(pageResult.toList(), pageResult.getTotalPages(), pageResult.getNumber(), pageSize);
    }

    @Transactional
    public Player addPlayer(String name, String surname, PlayerPosition position) {
        long count = playerRepository.count();
        if(count >= MAX_TEAM_SIZE) {
            throw new MaximumCapacityReachedException("The team size had already reached its maximum capacity: "+MAX_TEAM_SIZE);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Player newPlayer = playerRepository.save(new Player(name, surname, position));
        playerHistoryRepository.save(new PlayerHistory(
                newPlayer.getId(),
                PlayerHistory.Operation.CREATE,
                newPlayer,
                new Timestamp(System.currentTimeMillis()),
                authentication.getName()));
        return newPlayer;
    }

    @Transactional
    public boolean deletePlayer(long id) {
        Optional<Player> existingPlayerOptional = playerRepository.findById(id);
        if (existingPlayerOptional.isPresent()) {
            Player existingPlayer = existingPlayerOptional.get();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            playerRepository.deleteById(id);
            playerHistoryRepository.save(new PlayerHistory(
                    id,
                    PlayerHistory.Operation.DELETE,
                    existingPlayer,
                    new Timestamp(System.currentTimeMillis()),
                    authentication.getName()));
            return true;
        }
        return false;
    }
}
