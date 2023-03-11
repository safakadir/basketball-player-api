package com.safakadir.basketballplayerapi.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private PlayerPosition position;

    public Player() {
    }

    public Player(String name, String surname, PlayerPosition position) {
        this.name = name;
        this.surname = surname;
        this.position = position;
    }

    public Player(long id, String name, String surname, PlayerPosition position) {
        this(name, surname, position);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && Objects.equals(name, player.name) && Objects.equals(surname, player.surname) && position == player.position;
    }
}
