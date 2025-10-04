package com.example;

public class CombatResult {
    
    private final double damage;
    private final int score;
    private final int accuracy;
    
    // Construtor
    public CombatResult(double damage, int score, int accuracy) {
        this.damage = damage;
        this.score = score;
        this.accuracy = accuracy;
    }
    
    // Getters (importante serem públicos)
    public double getDamage() {
        return damage;
    }
    
    public int getScore() {
        return score;
    }
    
    public int getAccuracy() {
        return accuracy;
    }
}
