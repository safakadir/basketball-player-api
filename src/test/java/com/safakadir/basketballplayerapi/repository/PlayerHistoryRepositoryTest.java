package com.safakadir.basketballplayerapi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safakadir.basketballplayerapi.model.Player;
import com.safakadir.basketballplayerapi.model.PlayerHistory;
import com.safakadir.basketballplayerapi.model.PlayerPosition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class PlayerHistoryRepositoryTest {

    @Autowired
    PlayerHistoryRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldSavePlayerHistory() {
        Player player = new Player(1L, "Name", "Surname", PlayerPosition.C);
        long currentMillis = System.currentTimeMillis();
        PlayerHistory saved = repository.save(new PlayerHistory(
                player.getId(),
                PlayerHistory.Operation.CREATE,
                player,
                new Timestamp(currentMillis),
                "default"));

        PlayerHistory found = repository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertNotEquals(0L, found.getId());
        assertEquals(player.getId(), found.getPlayerId());
        assertEquals(currentMillis, found.getTimestamp().getTime());
        assertEquals(PlayerHistory.Operation.CREATE, found.getOperation());
        assertEquals("default", found.getUsername());

    }
}
