package com.safakadir.basketballplayerapi.controller;

import com.safakadir.basketballplayerapi.config.RsaJwtKeyProperties;
import com.safakadir.basketballplayerapi.controller.exceptionresolver.ConstraintViolationExceptionResolver;
import com.safakadir.basketballplayerapi.controller.exceptionresolver.MaximumCapacityReachedExceptionResolver;
import com.safakadir.basketballplayerapi.exception.MaximumCapacityReachedException;
import com.safakadir.basketballplayerapi.model.PlayerPosition;
import com.safakadir.basketballplayerapi.service.PlayerService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@GraphQlTest(PlayerController.class)
class PlayerControllerTest {

    @MockBean
    RsaJwtKeyProperties mockProperties; //To prevent errors during initialization

    @Autowired
    GraphQlTester graphQlTester;

    @MockBean
    PlayerService playerService;

    @Value("${app.maxTeamSize}")
    int maxTeamSize;

    @Test
    void shouldQueryAllPlayers() {
        // language=GraphQL
        String document = """
                query {
                    allPlayers(page: 0, pageSize: 10) {
                        totalPages,
                        players {
                            id
                            name
                            surname
                            position
                        }
                    }
                }
                """;
        graphQlTester.document(document)
                .execute();

        verify(playerService, times(1)).findAllPlayers(0, 10);
    }

    @Test
    void shouldRequestAddPlayerMutation() {
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
    void shouldRequestDeletePlayerMutation() {
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

    @Test
    void shouldThrowWhenArgumentsInvalid() {
        String document = """
            mutation {
                addPlayer(name: "123", surname: "abc", position: C) {
                    id
                    name
                    surname
                }
            }
            """;

        GraphQlTester.Response response = graphQlTester.document(document)
                .execute();

        response.errors().expect(e -> e.getErrorType().toString().equals(
                ConstraintViolationExceptionResolver.ErrorType.ArgumentValidationError.name())
        );
    }

    @Test
    void shouldThrowWhenMaximumCapacity() {
        String document = """
            mutation {
                addPlayer(name: "Name", surname: "Surname", position: C) {
                    id
                }
            }
            """;

        when(playerService.addPlayer(any(), any(), any())).thenThrow(new MaximumCapacityReachedException("test"));

        GraphQlTester.Response response = graphQlTester.document(document)
                .execute();

        response.errors().expect(e -> e.getErrorType().toString().equals(
                MaximumCapacityReachedExceptionResolver.ErrorType.CapacityError.name())
        );
    }
}
