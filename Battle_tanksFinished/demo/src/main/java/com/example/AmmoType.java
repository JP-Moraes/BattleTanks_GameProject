package com.example;

public enum AmmoType {
    PIERCING(1.5), // Bonus de 50% de dano
    SHATTERING(0.8), // -20% de dano
    EXPLOSIVE(1.2); // Bonus de 20% de dano 

    private final double ammoBonus;

    AmmoType(double ammoBonus) {
        this.ammoBonus = ammoBonus;
    }

    public double getAmmoBonus() {
        return ammoBonus;
    }

}
