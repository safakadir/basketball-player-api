package com.safakadir.basketballplayerapi.service;

import com.safakadir.basketballplayerapi.model.Player;
import com.safakadir.basketballplayerapi.model.PlayerPosition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {
    private final List<Player> playerList = new ArrayList<>();

    public List<Player> findAllPlayers() {
        return playerList;
    }

    public Player addPlayer(String name, String surname, PlayerPosition position) {
        Player newPlayer = createPlayer(name, surname, position);
        playerList.add(newPlayer);
        return newPlayer;
    }

    public boolean deletePlayer(long id) {
        return playerList.removeIf(p -> p.getId() == id);
    }

    private Player createPlayer(String name, String surname, PlayerPosition position) {
        Player player = new Player();
        long maxId = playerList.stream().map(Player::getId).reduce(0L, Math::max);
        player.setId(maxId+1);
        player.setName(name);
        player.setSurname(surname);
        player.setPosition(position);
        return player;
    }
}
