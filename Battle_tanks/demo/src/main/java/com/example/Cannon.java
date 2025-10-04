package com.example;

public class Cannon extends Module{

    protected Cannon() {
        super(AmmoType.EXPLOSIVE, 100, 10);

    }

    // Configurações Especificas das Armas 

    @Override
    public double getMaxHeat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMaxHeat'");
    }

    @Override
    public double getHeatPerShot() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHeatPerShot'");
    }

    @Override
    public double getHeatDissipationRate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHeatDissipationRate'");
    }

    @Override
    public double getOverheatThreshold() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOverheatThreshold'");
    }

    @Override
    public int getCoolingEffiency() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCoolingEffiency'");
    }

    @Override
    public int BaseReloadTurns() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'BaseReloadTurns'");
    }

    @Override
    public int getAmmoConsumption() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAmmoConsumption'");
    }

    @Override
    public int getAccuracyBack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccuracyBack'");
    }

    @Override
    public int getAccuracyFrontal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccuracyFrontal'");
    }

    @Override
    public int getAccuracyLateral() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccuracyLateral'");
    }
    
}
