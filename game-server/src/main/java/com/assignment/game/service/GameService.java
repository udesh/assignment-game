package com.assignment.game.service;

import com.assignment.action.PlayerAction;
import com.assignment.application.model.Message;
import com.assignment.application.service.MessageService;
import com.assignment.application.service.RunnableMessageSender;
import com.assignment.action.PlayerLogin;
import com.assignment.action.service.LoginService;
import com.assignment.game.Game;
import com.assignment.game.GameTurn;
import com.assignment.game.Player;
import com.assignment.game.repository.GameCommandRepository;
import com.assignment.game.repository.GameQueryRepository;
import com.assignment.utils.Constants;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GameService implements IGameService {

    private final GameCommandRepository gameCommandRepository;

    private final GameQueryRepository gameQueryRepository;

    private final LoginService loginService;

    private final MessageService messengerService;

    @Value("${game.start.maximum.value}")
    private int maximumValue;

    @Value("${game.start.minimum.value}")
    private int minimumValue;

    @Value("${game.divideBy.value}")
    private int divideByValue;

    /**
     * @param gameCommandRepository repository for game data create and update
     * @param gameQueryRepository repository for game data read.
     * @param loginService login command and query and logic service
     * @param messengerService message sending service
     */
    public GameService(GameCommandRepository gameCommandRepository, GameQueryRepository gameQueryRepository,
                       LoginService loginService, MessageService messengerService) {
        this.gameCommandRepository = gameCommandRepository;
        this.gameQueryRepository = gameQueryRepository;
        this.loginService = loginService;
        this.messengerService = messengerService;
    }

    public String createGame(String name, String playMode) {

        PlayerLogin latestPlayer = loginService.getQueryRepository().findTopByOrderByCreatedTimeDesc();
        PlayerLogin playerLogin = new PlayerLogin(UUID.randomUUID(), name, playMode,
                LocalDateTime.now(), Constants.ONLINE);
        loginService.getCommandRepository().save(playerLogin);
        PlayerLogin currentPlayer = loginService.getQueryRepository().findTopByOrderByCreatedTimeDesc();
        if (latestPlayer != null) {
            Game game = gameBuilder(latestPlayer,currentPlayer);
            gameCommandRepository.save(game);
            return game.getGameIdentifier();
        }
        return null;
    }

    public void gamePlay(PlayerAction playerAction) {
        Game game = gameQueryRepository.findTopByOrderByCreatedTimeDesc();
        assert isValidBeforeGameRound(playerAction.getAction()) : Constants.INVALID_INPUT;
        if (Constants.START_COMMAND.equals(playerAction.getAction())) {
            play(game);
        } else {
            gameTurnPlay(game, playerAction.getAction());
        }
        sendGameMessageAndSave(game);
    }

    public void sendGameMessageAndSave(Game game) {
        RunnableMessageSender messageSender = new RunnableMessageSender(game,messengerService,gameCommandRepository);
        Thread messageSenderThread = new Thread(messageSender);
        messageSenderThread.start();
    }

    public void play(Game game) {
        while (isAutoPlayTurn(game)) gameTurnPlay(game, Constants.AUTO);
    }

    public Game gameBuilder(PlayerLogin latestPlayer, PlayerLogin currentPlayer) {
        List<GameTurn> gameTurnList = new ArrayList<>();
        List<PlayerLogin> playerLoginList = new ArrayList<>();
        playerLoginList.add(latestPlayer);
        playerLoginList.add(currentPlayer);
        Player player = new Player(UUID.randomUUID(), latestPlayer.getName(), 0, latestPlayer.getPlayMode());
        GameTurn gameTurn = getPlayerTurnForGameCreate(player);
        gameTurnList.add(gameTurn);
        String gameIdentifier = createGameIdentifier();
        return new Game(UUID.randomUUID(), gameTurnList, playerLoginList, gameIdentifier,
                LocalDateTime.now(), Constants.ONLINE);
    }


    public GameTurn getPlayerTurnForGameCreate(Player player) {
        int randomNumber = getRandomNumberBetween(minimumValue, maximumValue);
        GameTurn gameTurn = new GameTurn(UUID.randomUUID(), player, randomNumber);
        String message = Constants.GAME_START_MESSAGE + randomNumber;
        Message currentTurnMessage = messengerService.createMessage(player.getName(), message, null);
        gameTurn.setMessageStatus(Constants.NOT_PUBLISHED);
        gameTurn.setMessage(currentTurnMessage);
        return gameTurn;
    }

    public String getGameTurnMessage(int currenTurnInput, int addNumber, int result, String playerName) {
        var gameTurnMessage = String.format(Constants.GAME_RESULT_MESSAGE,
                currenTurnInput,
                addNumber, result,currenTurnInput, addNumber, divideByValue, result);
        var gameTurnWin = String.format(Constants.GAME_WIN_MESSAGE,
                currenTurnInput,
                addNumber, result, currenTurnInput, addNumber, divideByValue, result, playerName);
        return isWinning(result) ? gameTurnWin : gameTurnMessage;
    }

    public void gameTurnPlay(Game game, String playerAction) {
        var gameTurnsListSize = game.getGameTurnList().size();
        GameTurn lastGameTurn = game.getGameTurnList().get(gameTurnsListSize - 1);
        var currenTurnInput = lastGameTurn.getOutput();
        var residual = currenTurnInput % divideByValue;
        var addNumber = getAddNumber(residual);
        addGameTurn(game, playerAction, addNumber, currenTurnInput, lastGameTurn);
    }

    public GameTurn getGameTurnOnPlay(PlayerLogin currenPlayer, int currenTurnInput,int addNumber,int result,
                                       String gameIdentifier) {
        Player player = new Player(UUID.randomUUID(), currenPlayer.getName(), addNumber,
                currenPlayer.getPlayMode());
        GameTurn gameTurn = new GameTurn(UUID.randomUUID(), player, result);
        String senderName = isWinning(result) ? Constants.MESSAGE_FROM_GAME : gameTurn.getPlayer().getName();
        Message currentTurnMessage = messengerService.createMessage(senderName,
                getGameTurnMessage(currenTurnInput, addNumber, result, gameTurn.getPlayer().getName()), gameIdentifier );
        gameTurn.setMessage(currentTurnMessage);
        gameTurn.setMessageStatus(Constants.NOT_PUBLISHED);
        return gameTurn;
    }

    public boolean isAutoPlayTurn(Game game){

        var size = game.getGameTurnList().size();
        GameTurn gameTurn = game.getGameTurnList().get(size - 1);
        if (gameTurn.getOutput() == 1) return false;
        Player player = gameTurn.getPlayer();
        return game.getPlayerList().stream().anyMatch(playerLogin ->
                !playerLogin.getName().equals(player.getName())
                && playerLogin.getPlayMode().equals(Constants.AUTO));
    }

    public PlayerLogin currentPlayer(Game game) {
        int size = game.getGameTurnList().size();
        Player player = game.getGameTurnList().get(size - 1).getPlayer();
        return game.getPlayerList().stream().filter(playerLogin ->
                !playerLogin.getName().equals(player.getName())).findFirst().orElse(null);
    }

    public Integer getRandomNumberBetween(Integer minimumValue,Integer maximumValue) {
        return new Random().nextInt(maximumValue-minimumValue) + minimumValue;
    }


    private void addGameTurn(Game game, String command, Integer addNumber,
                             Integer currenTurnInput, GameTurn lastGameTurn) {
        if (command.equals(Constants.AUTO) || isValidAddNumberCommand(command, addNumber)) {
            var currentTurnOutPut = currenTurnInput + addNumber;
            var result = currentTurnOutPut / divideByValue;
            game.addGameTurn(getGameTurnOnPlay(currentPlayer(game), currenTurnInput,
                    addNumber, result, game.getGameIdentifier()));
        }  else {
            game.addGameTurn(getGameTurnWhenInputNotMatch(lastGameTurn,Integer.parseInt(command)));
        }
         play(game);
    }

    private GameTurn getGameTurnWhenInputNotMatch(GameTurn lastGameTurn,Integer addNumber) {
        GameTurn gameTurn = new GameTurn(UUID.randomUUID(), lastGameTurn.getPlayer(), lastGameTurn.getOutput());
        String gameReplayMessageString = String.format(Constants.GAME_RESULT_MESSAGE_FOR_REPLAY,
                 addNumber, divideByValue);
        var gameId = !Strings.isEmpty(lastGameTurn.getMessage().getGameId()) ?
                lastGameTurn.getMessage().getGameId() : Constants.REPLAY_TURN_DEFAULT;
        Message gameRelayMessage = messengerService.createMessage(Constants.MESSAGE_FROM_GAME,
                gameReplayMessageString, gameId );
        gameTurn.setMessage(gameRelayMessage);
        gameTurn.setMessageStatus(Constants.NOT_PUBLISHED);
        return gameTurn;
    }

    private String createGameIdentifier() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private boolean isValidBeforeGameRound(String command) {
        return command != null &&
                            (command.equals(Constants.START_COMMAND)
                            || command.equals(Constants.ZERO_VALUE)
                            || command.equals(Constants.MINUS_ONE)
                            || command.equals(Constants.PLUS_ONE));
    }

    private boolean isValidAddNumberCommand(String value, Integer addNumber) {
        return ((value.equals(Constants.ZERO_VALUE)
                || value.equals(Constants.MINUS_ONE)
                || value.equals(Constants.PLUS_ONE)) &&
                value.equals(String.valueOf(addNumber)));
    }

    private Integer getAddNumber(int residual) {
        switch (residual) {
            case 1:
                return -1;
            case 2:
                return 1;
            default:
                return 0;
        }
    }

    private boolean isWinning(int result) {
        return result == 1;
    }

}
