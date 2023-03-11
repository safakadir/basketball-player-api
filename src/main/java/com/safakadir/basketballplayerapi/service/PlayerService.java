package com.safakadir.basketballplayerapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safakadir.basketballplayerapi.model.Player;
import com.safakadir.basketballplayerapi.model.PlayerHistory;
import com.safakadir.basketballplayerapi.model.PlayerPosition;
import com.safakadir.basketballplayerapi.repository.PlayerHistoryRepository;
import com.safakadir.basketballplayerapi.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerHistoryRepository playerHistoryRepository;
    private final ObjectMapper objectMapper;

    public PlayerService(PlayerRepository playerRepository,
                         PlayerHistoryRepository playerHistoryRepository, ObjectMapper objectMapper) {
        this.playerRepository = playerRepository;
        this.playerHistoryRepository = playerHistoryRepository;
        this.objectMapper = objectMapper;
    }

    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Transactional
    public Player addPlayer(String name, String surname, PlayerPosition position) {
        Player newPlayer = playerRepository.save(new Player(name, surname, position));
        playerHistoryRepository.save(new PlayerHistory(
                newPlayer.getId(),
                PlayerHistory.Operation.CREATE,
                newPlayer,
                new Timestamp(System.currentTimeMillis()),
                "default"));
        return newPlayer;
    }

    @Transactional
    public boolean deletePlayer(long id) {
        Optional<Player> existingPlayerOptional = playerRepository.findById(id);
        if (existingPlayerOptional.isPresent()) {
            Player existingPlayer = existingPlayerOptional.get();
            playerRepository.deleteById(id);
            playerHistoryRepository.save(new PlayerHistory(
                    id,
                    PlayerHistory.Operation.DELETE,
                    existingPlayer,
                    new Timestamp(System.currentTimeMillis()),
                    "default"));
            return true;
        }
        return false;
    }
}
