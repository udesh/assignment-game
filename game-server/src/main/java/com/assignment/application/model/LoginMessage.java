package com.assignment.application.model;

import javax.validation.constraints.NotNull;

public class LoginMessage {

    @NotNull(message="Name cannot be missing or empty")
    private final String name;
    private final String playMode;

    /** The model for communicate login details
     * @param name - The player name
     * @param playMode - The player play mode AUTO or MANUAL
     */
    public LoginMessage(String name, String playMode) {
        this.name = name;
        this.playMode = playMode;
    }

    public String getName() {
        return name;
    }

    public String getPlayMode() {
        return playMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginMessage that = (LoginMessage) o;

        if (!name.equals(that.name)) return false;
        return playMode.equals(that.playMode);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + playMode.hashCode();
        return result;
    }


}
