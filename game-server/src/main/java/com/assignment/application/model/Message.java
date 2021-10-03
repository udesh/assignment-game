package com.assignment.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Message {

    @JsonProperty("sender")
    String sender;
    @JsonProperty("gameId")
    String gameId;
    @JsonProperty("command")
    String command;
    @JsonProperty("timestamp")
    String timestamp;

    public Message() {
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getGameId() {
        return gameId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", gameId='" + gameId + '\'' +
                ", command='" + command + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (!Objects.equals(sender, message.sender) || (!Objects.equals(gameId, message.gameId)))
            return false;
        if (!command.equals(message.command)) return false;
        return Objects.equals(timestamp, message.timestamp);
    }

    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (gameId != null ? gameId.hashCode() : 0);
        result = 31 * result + command.hashCode();
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
