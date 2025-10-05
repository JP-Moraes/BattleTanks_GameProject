package com.example;

public class Sniper extends Module {

    protected Sniper() {
        super(AmmoType.PIERCING, 100.0, 10);
    }

    private boolean isPerfectAim() {
        return Math.random() < 0.3; //Chance critica de 30% da sniper
    }

    @Override 
    public CombatResult CalculateDamageAndScore() {

        //Logica exclusiva da Sniper

        if(!CanShot()) return new CombatResult(0.0,0,0);

        // USA o accuracy da classe pai (já implementado)
        int accuracy = (int)(Math.random() * 100 + 1);
    
        double positionMultiplier = PositionDMGBonus(accuracy);
        int hitScore = calculateHitScore(accuracy);

        boolean criticalHIt = isPerfectAim();
        double aimMultiplier = criticalHIt ? 2.0 : 1.0;

        double baseDamage = super.getBaseDMG() * positionMultiplier * super.getAmmoType().getAmmoBonus();
        double finalDamage = baseDamage * aimMultiplier;

        if (criticalHIt) {
        hitScore += 30; // Bônus extra por crítico
    }

        // Efeitos do tiro (mantém igual)
        currentAmmo -= getAmmoConsumption();
        currentHeat += getHeatPerShot();

        if (currentAmmo <= 0) StartReload();
        if (currentHeat >= getOverheatThreshold()) checkOverheat();

        Malfunction();
    
        return new CombatResult(finalDamage, hitScore, accuracy);
    }

    @Override
    public int getAmmoConsumption() {
        return 1; //Consone 1 bala; 
    }

    @Override
    public double getMaxHeat() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMaxHeat'");
    }

    @Override
    public double getHeatPerShot() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getHeatPerShot'");
    }

    @Override
    public double getHeatDissipationRate() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getHeatDissipationRate'");
    }

    @Override
    public double getOverheatThreshold() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getOverheatThreshold'");
    }

    @Override
    public int getCoolingEffiency() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getCoolingEffiency'");
    }


    @Override
    public int BaseReloadTurns() {
        
        throw new UnsupportedOperationException("Unimplemented method 'BaseReloadTurns'");
    }

    @Override
    public int getAccuracyBack() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAccuracyBack'");
    }

    @Override
    public int getAccuracyFrontal() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAccuracyFrontal'");
    }

    @Override
    public int getAccuracyLateral() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAccuracyLateral'");
    }
    
}
