package com.safakadir.basketballplayerapi.service;

import com.safakadir.basketballplayerapi.model.Player;
import com.safakadir.basketballplayerapi.model.PlayerPosition;
import com.safakadir.basketballplayerapi.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    public Player addPlayer(String name, String surname, PlayerPosition position) {
        Player newPlayer = createPlayer(name, surname, position);
        return playerRepository.save(newPlayer);
    }

    public boolean deletePlayer(long id) {
        playerRepository.deleteById(id);
        return true;
    }

    private Player createPlayer(String name, String surname, PlayerPosition position) {
        Player player = new Player();
        player.setName(name);
        player.setSurname(surname);
        player.setPosition(position);
        return player;
    }
}
