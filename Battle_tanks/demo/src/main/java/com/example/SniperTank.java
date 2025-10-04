package com.example;

public class SniperTank extends Tank{

    
    public SniperTank(String codename, int id, TankClass classType, PilotType pilotType, int shield) {
        super(codename, id, TankClass.LIGHT, pilotType, shield + 150);
        
    }

    @Override
    protected void InitializeTankSettings() {
        throw new UnsupportedOperationException("Unimplemented method 'InitializeTankSettings'");
    }

    @Override
    protected Module CreateModule() {
        return new Sniper();
    }

    @Override
    protected int getRepairTurnsDuration() {
        return 3; 
    }
    
}
