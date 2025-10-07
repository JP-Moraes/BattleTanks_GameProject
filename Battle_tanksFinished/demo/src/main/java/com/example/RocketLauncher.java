package com.example;

public class RocketLauncher extends Module{

    protected RocketLauncher() {
        super(AmmoType.EXPLOSIVE, 100, 6);
        
    }

    @Override
    public double getHeatPerShot() { return 35; }

    @Override
    public double getHeatDissipationRate() { return 30; }

    @Override
    public double getOverheatThreshold() { return 190; }

    @Override
    public int getCoolingEffiency() { return 40;}

    @Override
    public int BaseReloadTurns() { return 5;}

    @Override
    public int getAmmoConsumption() { return 1;}

    @Override
    public int getAccuracyBack() { return 10;}

    @Override
    public int getAccuracyFrontal() { return 40;}

    @Override
    public int getAccuracyLateral() { return 30;}
    
}
