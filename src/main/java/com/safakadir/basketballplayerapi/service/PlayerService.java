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
    public Player addPlayer(String name, String surname, PlayerPosition position) throws Exception {
        Player newPlayer = playerRepository.save(createPlayer(name, surname, position));
        playerHistoryRepository.save(cretePlayerHistoryRecord(newPlayer.getId(), newPlayer, PlayerHistory.Operation.CREATE));
        return newPlayer;
    }

    @Transactional
    public boolean deletePlayer(long id) throws Exception {
        Optional<Player> existingPlayerOptional = playerRepository.findById(id);
        if (existingPlayerOptional.isPresent()) {
            Player existingPlayer = existingPlayerOptional.get();
            playerRepository.deleteById(id);
            playerHistoryRepository.save(cretePlayerHistoryRecord(id, existingPlayer, PlayerHistory.Operation.DELETE));
            return true;
        }
        return false;
    }

    private Player createPlayer(String name, String surname, PlayerPosition position) {
        Player player = new Player();
        player.setName(name);
        player.setSurname(surname);
        player.setPosition(position);
        return player;
    }

    private PlayerHistory cretePlayerHistoryRecord(long id, Player player, PlayerHistory.Operation operation) throws Exception {
        PlayerHistory record = new PlayerHistory();
        record.setPlayerId(id);
        record.setOperation(operation);
        record.setTimestamp(new Timestamp(System.currentTimeMillis()));
        record.setUsername("default");
        record.setData(objectMapper.writeValueAsString(player));
        return record;
    }
}
