package com.assignment.game.service;

import com.assignment.action.PlayerAction;
import com.assignment.game.Game;

public interface IGameService {


    String createGame(String name, String playMode);

    void gamePlay(PlayerAction playerAction);

    void sendGameMessageAndSave(Game game);
}
