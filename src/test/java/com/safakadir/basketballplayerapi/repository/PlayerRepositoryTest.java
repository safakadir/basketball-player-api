package com.safakadir.basketballplayerapi.repository;

import com.safakadir.basketballplayerapi.model.Player;
import com.safakadir.basketballplayerapi.model.PlayerPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PlayerRepositoryTest {

    @Autowired
    PlayerRepository repository;

    @BeforeEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    void shouldSavePlayer() {
        Player player = repository.save(new Player("name1", "surname", PlayerPosition.C));
        assertNotNull(player);
        assertNotEquals(0L, player.getId());
        assertEquals("name1", player.getName());
        assertEquals("surname", player.getSurname());
        assertEquals(PlayerPosition.C, player.getPosition());
    }

    @Test
    void shouldFindPlayerById() {
        Player player = repository.save(new Player("name1", "surname", PlayerPosition.C));

        Player foundPlayer = repository.findById(player.getId()).orElse(null);

        assertNotNull(foundPlayer);
        assertNotEquals(0L, foundPlayer.getId());
        assertEquals("name1", foundPlayer.getName());
        assertEquals("surname", foundPlayer.getSurname());
        assertEquals(PlayerPosition.C, foundPlayer.getPosition());
    }

    @Test
    void shouldFindAllPlayers() {
        repository.save(new Player("name1", "surname", PlayerPosition.C));
        repository.save(new Player("name2", "surname", PlayerPosition.C));
        repository.save(new Player("name3", "surname", PlayerPosition.C));

        List<Player> list = repository.findAll();

        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("surname", list.get(0).getSurname());
    }

    @Test
    void shouldDeletePlayerById() {
        Player player = repository.save(new Player("name1", "surname", PlayerPosition.C));

        repository.deleteById(player.getId());

        Player foundPlayer = repository.findById(player.getId()).orElse(null);
        assertNull(foundPlayer);
    }

    @Test
    void shouldNotDeleteUnexistingPlayer() {
        Player player = repository.save(new Player("name1", "surname", PlayerPosition.C));

        repository.deleteById(player.getId()+111L);

        Player foundPlayer = repository.findById(player.getId()).orElse(null);
        assertNotNull(foundPlayer);
    }

}