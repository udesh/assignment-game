package com.assignment.action;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class PlayerLogin {
    @Id
    private final UUID id;
    private final String name;
    private final String playMode;
    private final LocalDateTime createdTime;
    private final String status;

    /**
     * Add new human player command.
     *
     * @param name service to interact with running game.
     * @param playMode socket adapter.
     * @param createdTime socket adapter.
     */
    public PlayerLogin(UUID id, String name, String playMode, LocalDateTime createdTime, String status) {
        this.id = id;
        this.name = name;
        this.playMode = playMode;
        this.createdTime = createdTime;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlayMode() {
        return playMode;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public String getStatus() {
        return status;
    }
}
