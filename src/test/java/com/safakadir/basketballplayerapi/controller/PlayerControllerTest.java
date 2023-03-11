package com.safakadir.basketballplayerapi.controller;

import com.safakadir.basketballplayerapi.model.PlayerPosition;
import com.safakadir.basketballplayerapi.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@GraphQlTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    GraphQlTester graphQlTester;

    @MockBean
    PlayerService playerService;

    @Test
    void shouldQueryAllPlayers() {
        // language=GraphQL
        String document = """
                query {
                    allPlayers {
                        id
                        name
                        surname
                        position
                    }
                }
                """;
        graphQlTester.document(document)
                .execute();

        verify(playerService, times(1)).findAllPlayers();
    }

    @Test
    void shouldRequestAddPlayerMutation() throws Exception {
        // language=GraphQL
        String document = """
                mutation {
                    addPlayer(name: "Name", surname: "Surname", position: C) {
                        id
                        name
                        surname
                        position
                    }
                }
                """;
        graphQlTester.document(document)
                .execute();

        verify(playerService, times(1)).addPlayer("Name", "Surname", PlayerPosition.C);
    }

    @Test
    void shouldRequestDeletePlayerMutation() throws Exception {
        // language=GraphQL
        String document = """
                mutation {
                    deletePlayer(id: "1")
                }
                """;
        graphQlTester.document(document)
                .execute();

        verify(playerService, times(1)).deletePlayer(1);
    }
}
