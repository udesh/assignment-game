package com.assignment.utils;

public class Constants {

    public static final String KAFKA_TOPIC = "kafka-game-2";
    public static final String GROUP_ID = "kafka-sandbox-1";
    public static final String KAFKA_BROKER = "localhost:9092";
    public static final String GAME_RESULT_MESSAGE = " \u26BD Input: %d , add: %d result: %d \u26BD " +
                                                     "(%d + (%d)/%d = %d)";
    public static final String GAME_RESULT_MESSAGE_FOR_REPLAY = "\u26BD add: %d ,Please enter the value to get to a " +
                                                     "number that is divisible by %d.";
    public static final String GAME_WIN_MESSAGE = "\uD83C\uDFC6 " + GAME_RESULT_MESSAGE + " WIN \uD83D\uDC51 for %s . " +
                                                   "Congratulations ! \uD83C\uDF89 \uD83C\uDF8A";
    public static final String GAME_START_MESSAGE = " \u26BD Start the game with the number:";
    public static final String GAME_WELCOME_MESSAGE = "Hi \uD83D\uDC4B %s joined the ⚽ game. ";
    public static final String MESSAGE_FROM_GAME = "Message from game with \uD83E\uDDE1";
    public static final String START_COMMAND = "START";
    public static final String ZERO_VALUE = "0";
    public static final String MINUS_ONE = "-1";
    public static final String PLUS_ONE = "1";
    public static final String LOGIN = "LOGIN";
    public static final String AUTO = "AUTO";
    public static final String REPLAY_TURN_DEFAULT = "REPLAY_TURN_DEFAULT";
    public static final String ONLINE = "ONLINE";
    public static final String NOT_PUBLISHED = "NOT_PUBLISHED";
    public static final String PUBLISHED = "PUBLISHED";
    public static final String INVALID_INPUT = "Invalid input value";
    public static final String DEFAULT_NAME = "Player";




}
