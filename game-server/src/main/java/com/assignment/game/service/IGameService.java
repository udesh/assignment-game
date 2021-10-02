package com.assignment.game.service;

import com.assignment.game.Game;

public interface IGameService {


    String createGame(String name, String playMode);

    void gamePlay(String value);

    void sendGameMessageAndSave(Game game);
}
