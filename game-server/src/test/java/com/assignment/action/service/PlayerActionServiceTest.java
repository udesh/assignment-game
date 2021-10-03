package com.assignment.action.service;

import com.assignment.action.PlayerAction;
import com.assignment.action.repository.PlayerActionQueryRepository;
import com.assignment.utils.Constants;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class PlayerActionServiceTest {

    @Autowired
    PlayerActionService playerActionService;
    @Autowired
    PlayerActionQueryRepository playerActionQueryRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Save player action and check the data.")
    void createPlayerActionAndSave() {
        playerActionService.createPlayerActionAndSave("Player1", Constants.LOGIN);
        PlayerAction playerAction = playerActionQueryRepository.
                findByPlayerNameAndAction("Player1", Constants.LOGIN);
        Assertions.assertEquals("Player1", playerAction.getPlayerName());
        Assertions.assertEquals(Constants.LOGIN, playerAction.getAction());
    }
}