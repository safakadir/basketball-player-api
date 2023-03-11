package com.safakadir.basketballplayerapi.controller;

import com.safakadir.basketballplayerapi.model.Player;
import com.safakadir.basketballplayerapi.model.PlayerPosition;
import com.safakadir.basketballplayerapi.service.PlayerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @QueryMapping
    public List<Player> allPlayers() {
        return playerService.findAllPlayers();
    }

    @MutationMapping
    public Player addPlayer(@Argument String name, @Argument String surname, @Argument PlayerPosition position) {
        return playerService.addPlayer(name, surname, position);
    }

    @MutationMapping
    public boolean deletePlayer(@Argument long id) {
        return playerService.deletePlayer(id);
    }
}
