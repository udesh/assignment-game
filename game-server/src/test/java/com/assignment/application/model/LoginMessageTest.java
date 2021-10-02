package com.assignment.application.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginMessageTest {

    LoginMessage loginMessage;

    @BeforeEach
    void setUp() {
        loginMessage = new LoginMessage("Player1", "AUTO");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getName() {
        assertEquals("Player1", loginMessage.getName());
    }

    @Test
    void getPlayMode() {
        assertEquals("AUTO", loginMessage.getPlayMode());
    }
}