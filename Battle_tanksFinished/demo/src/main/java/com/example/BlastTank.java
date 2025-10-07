package com.example;

public class BlastTank extends Tank {

    
    public BlastTank(String codename, int id, TankClass classType, PilotType pilotType, int shield) {
        super(codename, id, TankClass.MEDIUM, pilotType, shield + 200);
        
    }


    @Override
    protected Module CreateModule() {
        return new RocketLauncher();
    }

    
}
