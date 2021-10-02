package com.assignment.game;

import org.springframework.data.annotation.Id;

import java.util.Objects;
import java.util.UUID;

public class Player {

    @Id
    private final UUID id;
    private final String name;
    private final int input;
    private final String playMode;

    public Player(UUID id, String name, int input, String playMode) {
        this.id = id;
        this.name = name;
        this.input = input;
        this.playMode = playMode;
    }

    public UUID getId() {
        return id;
    }

    public int getInput() {
        return input;
    }

    public String getPlayMode() {
        return playMode;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (input != player.input) return false;
        if (!Objects.equals(id, player.id)) return false;
        if (!name.equals(player.name)) return false;
        return playMode.equals(player.playMode);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + input;
        result = 31 * result + playMode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", input=" + input +
                ", playMode='" + playMode + '\'' +
                '}';
    }
}
