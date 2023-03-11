package com.safakadir.basketballplayerapi.model;

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
    private String data;
    private Timestamp timestamp;
    private String username;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
