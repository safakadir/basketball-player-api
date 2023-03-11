package com.safakadir.basketballplayerapi.model;

import com.safakadir.basketballplayerapi.model.converter.PlayerJsonConverter;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity(name = "player_history")
public class PlayerHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private long playerId;
    @Enumerated(EnumType.STRING)
    private Operation operation;
    @Convert(converter = PlayerJsonConverter.class)
    private Player data;
    private Timestamp timestamp;
    private String username;

    public PlayerHistory() {
    }

    public PlayerHistory(long playerId, Operation operation, Player data, Timestamp timestamp, String username) {
        this.playerId = playerId;
        this.operation = operation;
        this.data = data;
        this.timestamp = timestamp;
        this.username = username;
    }

    public PlayerHistory(long id, long playerId, Operation operation, Player data, Timestamp timestamp, String username) {
        this(playerId, operation, data, timestamp, username);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Player getData() {
        return data;
    }

    public void setData(Player data) {
        this.data = data;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public enum Operation {
        CREATE,
        UPDATE,
        DELETE
    }
}
