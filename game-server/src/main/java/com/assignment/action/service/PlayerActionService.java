package com.assignment.action.service;

import com.assignment.action.PlayerAction;
import com.assignment.action.repository.PlayerActionCommandRepository;
import com.assignment.action.repository.PlayerActionQueryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PlayerActionService implements IPlayerActionService {

    private final PlayerActionCommandRepository playerActionCommandRepository;
    private final PlayerActionQueryRepository playerActionQueryRepository;

    public PlayerActionService(PlayerActionCommandRepository playerActionCommandRepository,
                               PlayerActionQueryRepository playerActionQueryRepository) {
        this.playerActionCommandRepository = playerActionCommandRepository;
        this.playerActionQueryRepository = playerActionQueryRepository;
    }

    public void save(PlayerAction playerAction) {
        playerActionCommandRepository.save(playerAction);
    }

    @Override
    public void createPlayerActionAndSave(String playerName, String action) {
        PlayerAction playerAction = new PlayerAction(UUID.randomUUID(), playerName,
                action, LocalDateTime.now().toString());
        playerActionCommandRepository.save(playerAction);
    }

    public PlayerActionCommandRepository getPlayerActionCommandRepository() {
        return playerActionCommandRepository;
    }

    public PlayerActionQueryRepository getPlayerActionQueryRepository() {
        return playerActionQueryRepository;
    }
}
