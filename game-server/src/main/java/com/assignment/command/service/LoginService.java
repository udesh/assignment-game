package com.assignment.command.service;

import com.assignment.command.repository.PlayerLoginCommandRepository;
import com.assignment.command.repository.PlayerLoginQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {

    private final PlayerLoginCommandRepository playerLoginCommandRepository;

    private final PlayerLoginQueryRepository playerLoginQueryRepository;

    public LoginService(PlayerLoginCommandRepository playerLoginCommandRepository,
                        PlayerLoginQueryRepository playerLoginQueryRepository) {
        this.playerLoginCommandRepository = playerLoginCommandRepository;
        this.playerLoginQueryRepository = playerLoginQueryRepository;
    }

    public PlayerLoginQueryRepository getQueryRepository() {
        return  playerLoginQueryRepository;
    }

    public PlayerLoginCommandRepository getCommandRepository() {
        return  playerLoginCommandRepository;
    }

}
