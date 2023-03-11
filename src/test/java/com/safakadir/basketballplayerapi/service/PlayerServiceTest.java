package com.safakadir.basketballplayerapi.service;

import com.safakadir.basketballplayerapi.exception.MaximumCapacityReachedException;
import com.safakadir.basketballplayerapi.model.Player;
import com.safakadir.basketballplayerapi.model.PlayerHistory;
import com.safakadir.basketballplayerapi.model.PlayerPage;
import com.safakadir.basketballplayerapi.model.PlayerPosition;
import com.safakadir.basketballplayerapi.repository.PlayerHistoryRepository;
import com.safakadir.basketballplayerapi.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

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
    @Autowired
    Environment env;
    @Value("${app.maxTeamSize}")
    int maxTeamSize;

    @BeforeEach
    void clear() {
        playerRepository.deleteAll();
        playerHistoryRepository.deleteAll();
    }

    @Test
    void shouldFindAllPlayersWithPage() {
        playerService.addPlayer("Name1", "Surname", PlayerPosition.C);
        playerService.addPlayer("Name2", "Surname", PlayerPosition.C);
        playerService.addPlayer("Name3", "Surname", PlayerPosition.C);

        PlayerPage playerPage = playerService.findAllPlayers(0, 10);
        assertNotNull(playerPage);
        assertEquals(1, playerPage.getTotalPages());
        assertEquals(0, playerPage.getCurrentPage());
        assertEquals(10, playerPage.getPageSize());
        assertEquals(3, playerPage.getPlayers().size());
        assertEquals(3, playerRepository.count());
        assertEquals(3, playerHistoryRepository.count());
    }

    @Test
    void shouldPaginateCorrectly() {
        for(int i = 1; i <= 10; i++) {
            playerService.addPlayer("Name"+i, "Surname", PlayerPosition.C);
        }

        PlayerPage playerPageFirst = playerService.findAllPlayers(0, 3);
        assertNotNull(playerPageFirst);
        assertEquals(4, playerPageFirst.getTotalPages());
        assertEquals(0, playerPageFirst.getCurrentPage());
        assertEquals(3, playerPageFirst.getPageSize());
        assertEquals(3, playerPageFirst.getPlayers().size());

        PlayerPage playerPageLast = playerService.findAllPlayers(3, 3);
        assertNotNull(playerPageLast);
        assertEquals(4, playerPageLast.getTotalPages());
        assertEquals(3, playerPageLast.getCurrentPage());
        assertEquals(3, playerPageLast.getPageSize());
        assertEquals(1, playerPageLast.getPlayers().size());

        PlayerPage playerPageEmpty = playerService.findAllPlayers(4, 3);
        assertNotNull(playerPageLast);
        assertEquals(4, playerPageEmpty.getTotalPages());
        assertEquals(4, playerPageEmpty.getCurrentPage());
        assertEquals(3, playerPageEmpty.getPageSize());
        assertEquals(0, playerPageEmpty.getPlayers().size());
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
    void shouldThrowWhenCapacityExceeded() {
        assertThrows(MaximumCapacityReachedException.class, () -> {
            for(int i = 1; i <= maxTeamSize+5; i++) {
                playerService.addPlayer("Name"+i, "Surname", PlayerPosition.C);
            }
        });
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