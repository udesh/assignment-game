package com.assignment.game;

import com.assignment.action.PlayerLogin;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Document
public class Game {

     @Id
     private final UUID id;
     private final String gameIdentifier;
     private final List<PlayerLogin> playerList;
     private final List<GameTurn> gameTurnList;
     private final LocalDateTime createdTime;
     private final String status;

     public Game(UUID id, List<GameTurn> gameTurnList, List<PlayerLogin> playerList,
                 String gameIdentifier, LocalDateTime createdTime, String status) {
          this.id = id;
          this.gameTurnList = gameTurnList;
          this.playerList = playerList;
          this.gameIdentifier = gameIdentifier;
          this.createdTime = createdTime;
          this.status = status;
     }

     public void addGameTurn(GameTurn gameTurn) {
          gameTurnList.add(gameTurn);
     }

     public UUID getId() {
          return id;
     }

     public List<PlayerLogin> getPlayerList() {
          return playerList;
     }

     public List<GameTurn> getGameTurnList() {
          return gameTurnList;
     }

     public String getGameIdentifier() {
          return gameIdentifier;
     }

     public LocalDateTime getCreatedTime() {
          return createdTime;
     }

     public String getStatus() {
          return status;
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;

          Game game = (Game) o;

          if (!id.equals(game.id)) return false;
          if (!gameIdentifier.equals(game.gameIdentifier)) return false;
          if (!Objects.equals(playerList, game.playerList)) return false;
          if (!Objects.equals(gameTurnList, game.gameTurnList)) return false;
          if (!Objects.equals(createdTime, game.createdTime)) return false;
          return Objects.equals(status, game.status);
     }

     @Override
     public int hashCode() {
          int result = id.hashCode();
          result = 31 * result + gameIdentifier.hashCode();
          result = 31 * result + (playerList != null ? playerList.hashCode() : 0);
          result = 31 * result + (gameTurnList != null ? gameTurnList.hashCode() : 0);
          result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
          result = 31 * result + (status != null ? status.hashCode() : 0);
          return result;
     }

     @Override
     public String toString() {
          return "Game{" +
                  "id=" + id +
                  ", gameIdentifier='" + gameIdentifier + '\'' +
                  ", playerList=" + playerList +
                  ", gameTurnList=" + gameTurnList +
                  ", createdTime=" + createdTime +
                  ", status='" + status + '\'' +
                  '}';
     }
}
