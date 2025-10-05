package com.example;

public enum GameMode {
    ONE_VS_ONE(1),
    THREE_VS_THREE(3),
    SIX_VS_SIX(6);

    private final int teamSize;

    GameMode(int teamSize) {
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }
    
}
