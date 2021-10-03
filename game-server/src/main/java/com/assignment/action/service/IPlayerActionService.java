package com.assignment.action.service;

import com.assignment.action.PlayerAction;

public interface IPlayerActionService {


    /** Save Player action
     * @param playerAction player action to save
     */
     void save(PlayerAction playerAction);

     void createPlayerActionAndSave(String playerName, String action);
}
