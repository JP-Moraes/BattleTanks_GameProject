package com.example;

public class FortressTank extends Tank {

    

    public FortressTank(String codename, int id, TankClass classType, PilotType pilotType, int shield) {
        super(codename, id, TankClass.HEAVY, pilotType, shield + 350);
        
    }

    @Override
    protected void InitializeTankSettings() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'InitializeTankSettings'");
    }

    @Override
    protected Module CreateModule() {
        return new Cannon();

    }

    @Override
    protected int getRepairTurnsDuration() {
        return 5;
    }
    
}
