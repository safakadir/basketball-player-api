package com.safakadir.basketballplayerapi.service;

import com.safakadir.basketballplayerapi.model.Player;
import com.safakadir.basketballplayerapi.model.PlayerHistory;
import com.safakadir.basketballplayerapi.model.PlayerPosition;
import com.safakadir.basketballplayerapi.repository.PlayerHistoryRepository;
import com.safakadir.basketballplayerapi.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class PlayerServiceTest {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    PlayerHistoryRepository playerHistoryRepository;
    @Autowired
    PlayerService playerService;

    @BeforeEach
    void clear() {
        playerRepository.deleteAll();
        playerHistoryRepository.deleteAll();
    }

    @Test
    void shouldFindAllPlayers() {
        playerService.addPlayer("Name1", "Surname", PlayerPosition.C);
        playerService.addPlayer("Name2", "Surname", PlayerPosition.C);
        playerService.addPlayer("Name3", "Surname", PlayerPosition.C);

        List<Player> list = playerService.findAllPlayers();
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals(3, playerRepository.count());
        assertEquals(3, playerHistoryRepository.count());
    }

    @Test
    void shouldAddPlayer() {
        Player player = playerService.addPlayer("Name", "Surname", PlayerPosition.C);

        Player foundPlayer = playerRepository.findById(player.getId()).orElse(null);
        assertNotNull(player);
        assertNotNull(foundPlayer);
        assertEquals(1, playerRepository.count());
        assertEquals(1, playerHistoryRepository.count());
        assertEquals(player, foundPlayer);
        PlayerHistory foundPlayerHistory = playerHistoryRepository.findTopByPlayerIdOrderByTimestampDesc(player.getId());
        assertNotNull(foundPlayerHistory);
        assertEquals(player.getId(), foundPlayerHistory.getPlayerId());
        assertEquals(player, foundPlayerHistory.getData());
    }

    @Test
    void shouldDeletePlayer() {
        Player player = playerService.addPlayer("Name", "Surname", PlayerPosition.C);

        boolean result = playerService.deletePlayer(player.getId());

        assertTrue(result);
        assertEquals(0, playerRepository.count());
        assertEquals(2, playerHistoryRepository.count());
        PlayerHistory foundPlayerHistory = playerHistoryRepository.findTopByPlayerIdOrderByTimestampDesc(player.getId());
        assertNotNull(foundPlayerHistory);
        assertEquals(player, foundPlayerHistory.getData());
    }
}