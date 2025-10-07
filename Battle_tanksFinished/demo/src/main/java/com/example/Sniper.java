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
    public int getAmmoConsumption() { return 1; }

    @Override
    public double getHeatPerShot() { return 15; }

    @Override
    public double getHeatDissipationRate() { return 20; }

    @Override
    public double getOverheatThreshold() { return 200; }

    @Override
    public int getCoolingEffiency() { return 45;}

    //Configuração de Recarga

    @Override
    public int BaseReloadTurns() { return 5;}

    // Configurações de PRECISÂO

    @Override
    public int getAccuracyBack() { return 15; }

    @Override
    public int getAccuracyFrontal() { return 40; }

    @Override
    public int getAccuracyLateral() { return 25; }
    
}
