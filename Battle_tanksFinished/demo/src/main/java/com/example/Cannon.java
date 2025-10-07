package com.example;

public class Cannon extends Module{

    protected Cannon() {
        super(AmmoType.EXPLOSIVE, 150, 10);

    }

    // Configurações Especificas das Armas 

    @Override
    public double getHeatPerShot() { return 25; }

    @Override
    public double getHeatDissipationRate() { return 30; }

    @Override
    public double getOverheatThreshold() { return 175; }

    @Override
    public int getCoolingEffiency() { return 50;}

    @Override
    public int BaseReloadTurns() { return 4; }

    @Override
    public int getAmmoConsumption() { return 1; }

    @Override
    public int getAccuracyBack() { return 10;}

    @Override
    public int getAccuracyFrontal() { return 40; }

    @Override
    public int getAccuracyLateral() { return 30;}
    
}
