package com.assignment.action;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class PlayerAction {

    @Id
    private final UUID id;
    private final String playerName;
    private final String action;
    private final String  createdTime;

    public PlayerAction(UUID id, String playerName, String action, String createdTime) {
        this.id = id;
        this.playerName = playerName;
        this.action = action;
        this.createdTime = createdTime;
    }

    public UUID getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAction() {
        return action;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerAction that = (PlayerAction) o;

        if (!id.equals(that.id)) return false;
        if (!playerName.equals(that.playerName)) return false;
        if (!action.equals(that.action)) return false;
        return createdTime.equals(that.createdTime);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + playerName.hashCode();
        result = 31 * result + action.hashCode();
        result = 31 * result + createdTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PlayerAction{" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", action='" + action + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
