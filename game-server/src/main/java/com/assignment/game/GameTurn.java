package com.assignment.game;

import com.assignment.application.model.Message;
import org.springframework.data.annotation.Id;

import java.util.Objects;
import java.util.UUID;

public class GameTurn {

    @Id
    private final UUID id;
    private final Player player;
    private final int output;
    private Message message;
    private String messageStatus;

    public GameTurn(UUID id, Player player, int output) {
        this.id = id ;
        this.player = player;
        this.output = output;
    }

    public Player getPlayer() {
        return player;
    }

    public int getOutput() {
        return output;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameTurn gameTurn = (GameTurn) o;

        if (output != gameTurn.output) return false;
        if (!Objects.equals(id, gameTurn.id)) return false;
        if (!player.equals(gameTurn.player)) return false;
        if (!Objects.equals(message, gameTurn.message)) return false;
        return Objects.equals(messageStatus, gameTurn.messageStatus);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + player.hashCode();
        result = 31 * result + output;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (messageStatus != null ? messageStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GameTurn{" +
                "id=" + id +
                ", player=" + player +
                ", output=" + output +
                ", message=" + message +
                ", messageStatus='" + messageStatus + '\'' +
                '}';
    }
}
