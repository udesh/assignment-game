package com.assignment.game.service;

import com.assignment.action.PlayerLogin;
import com.assignment.action.service.LoginService;
import com.assignment.game.Game;
import com.assignment.game.GameTurn;
import com.assignment.game.repository.GameQueryRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
class GameServiceTest {

    @Autowired
    GameService gameService;
    @Autowired
    GameQueryRepository gameQueryRepository;
    @Autowired
    LoginService loginService;
    @Value("${game.divideBy.value}")
    private int divideByValue;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test game builder is returning a not null object")
    void gameBuilder() {
        PlayerLogin PlayerLogin1 = new PlayerLogin(UUID.randomUUID(),
                "Player1","AUTO", LocalDateTime.now(),"ONLINE");
        PlayerLogin PlayerLogin2 = new PlayerLogin(UUID.randomUUID(),
                "Player2","AUTO", LocalDateTime.now(),"ONLINE");
        Game game = gameService.gameBuilder(PlayerLogin1,PlayerLogin2);
        Assertions.assertNotNull(game);
    }

    @Test
    @DisplayName("Check for start game message.")
    void startGameMessage() {
        PlayerLogin PlayerLogin1 = new PlayerLogin(UUID.randomUUID(),
                "Player1","AUTO", LocalDateTime.now(),"ONLINE");
        PlayerLogin PlayerLogin2 = new PlayerLogin(UUID.randomUUID(),
                "Player2","AUTO", LocalDateTime.now(),"ONLINE");
        Game game = gameService.gameBuilder(PlayerLogin1,PlayerLogin2);
        String message = game.getGameTurnList().get(0).getMessage().getCommand();
        assert(message).contains("Start the game with the number");
    }

    @Test
    @DisplayName("When a player1 login create a game")
    void createGame() {
        PlayerLogin PlayerLogin1 = new PlayerLogin(UUID.randomUUID(),
                "Player1","AUTO", LocalDateTime.now(),"ONLINE");
        loginService.getCommandRepository().save(PlayerLogin1);
        Assertions.assertNotNull(gameService.createGame("Player2", "AUTO"));
    }

    @Test
    @DisplayName("When two auto players complete the game check for not null object type.")
    void playAutoPlayers() {
        PlayerLogin PlayerLogin1 = new PlayerLogin(UUID.randomUUID(),
                "Player1","AUTO", LocalDateTime.now(),"ONLINE");
        loginService.getCommandRepository().save(PlayerLogin1);
        Assertions.assertNotNull(gameService.createGame("Player2", "AUTO"));
        Game game = gameQueryRepository.findTopByOrderByCreatedTimeDesc();
        gameService.play(game);
        Optional<Game> gameAfterAuto = gameQueryRepository.findById(game.getId().toString());
        Assertions.assertNotNull(gameAfterAuto);
    }

    @Test
    @DisplayName("When two auto players complete the game check WIN turn")
    void playAutoPlayerGameAndCheckForWin() {
        PlayerLogin PlayerLogin1 = new PlayerLogin(UUID.randomUUID(),
                "Player1","AUTO", LocalDateTime.now(),"ONLINE");
        loginService.getCommandRepository().save(PlayerLogin1);
        Assertions.assertNotNull(gameService.createGame("Player2", "AUTO"));
        Game game = gameQueryRepository.findTopByOrderByCreatedTimeDesc();
        gameService.play(game);
        int gameTurnsSize = game.getGameTurnList().size();
        GameTurn winGameTurn = game.getGameTurnList().get(gameTurnsSize - 1);
        assert(winGameTurn.getMessage().getCommand()).contains("WIN");
    }

    @Test
    @DisplayName("When two auto players complete the game check WIN turn result = 1")
    void playAutoPlayerGameAndCheckForWinOutput() {
        PlayerLogin PlayerLogin1 = new PlayerLogin(UUID.randomUUID(),
                "Player1","AUTO", LocalDateTime.now(),"ONLINE");
        loginService.getCommandRepository().save(PlayerLogin1);
        Assertions.assertNotNull(gameService.createGame("Player2", "AUTO"));
        Game game = gameQueryRepository.findTopByOrderByCreatedTimeDesc();
        gameService.play(game);
        int gameTurnsSize = game.getGameTurnList().size();
        GameTurn winGameTurn = game.getGameTurnList().get(gameTurnsSize - 1);
        Assertions.assertEquals(1, winGameTurn.getOutput());
    }

    @Test
    @DisplayName("When Auto and Manual player login check for next play turn")
    void playAutoPlayerGameAndCheckFor() {
        PlayerLogin PlayerLogin1 = new PlayerLogin(UUID.randomUUID(),
                "Player1","AUTO", LocalDateTime.now(),"ONLINE");
        loginService.getCommandRepository().save(PlayerLogin1);
        Assertions.assertNotNull(gameService.createGame("Player2", "AUTO"));
        Game game = gameQueryRepository.findTopByOrderByCreatedTimeDesc();
        Assertions.assertTrue(gameService.isAutoPlayTurn(game));
    }

    @Test
    @DisplayName("Check for current player")
    void getCurrentPlayer() {
        PlayerLogin PlayerLogin1 = new PlayerLogin(UUID.randomUUID(),
                "Player1","AUTO", LocalDateTime.now(),"ONLINE");
        loginService.getCommandRepository().save(PlayerLogin1);
        Assertions.assertNotNull(gameService.createGame("Player2", "AUTO"));
        Game game = gameQueryRepository.findTopByOrderByCreatedTimeDesc();
        Assertions.assertEquals("Player2", gameService.currentPlayer(game).getName());
    }

    @Test
    @DisplayName("Check for auto play condition in game turn")
    void getCurrentPlayerAuto() {
        PlayerLogin PlayerLogin1 = new PlayerLogin(UUID.randomUUID(),
                "Player1","AUTO", LocalDateTime.now(),"ONLINE");
        loginService.getCommandRepository().save(PlayerLogin1);
        Assertions.assertNotNull(gameService.createGame("Player2", "MANUAL"));
        Game game = gameQueryRepository.findTopByOrderByCreatedTimeDesc();
        Assertions.assertFalse(gameService.isAutoPlayTurn(game));
    }

    @Test
    @DisplayName("Check for game turn output")
    void playManualTurn() {
        PlayerLogin PlayerLogin1 = new PlayerLogin(UUID.randomUUID(),
                "Player1","AUTO", LocalDateTime.now(),"ONLINE");
        loginService.getCommandRepository().save(PlayerLogin1);
        Assertions.assertNotNull(gameService.createGame("Player2", "MANUAL"));
        Game game = gameQueryRepository.findTopByOrderByCreatedTimeDesc();
        int input = game.getGameTurnList().get(0).getOutput();
        int addedValue = input + 1;
        int result;
        if (!(addedValue % divideByValue == 0)) {
            result = input;
        } else {
            result = input/divideByValue;
        }
        gameService.gameTurnPlay(game, Integer.toString(1));
        Assertions.assertEquals(result, game.getGameTurnList().get(1).getOutput());
    }
}