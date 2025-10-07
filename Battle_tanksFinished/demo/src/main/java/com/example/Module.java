package com.example;

public abstract class Module {

    private AmmoType ammoType;
    private double baseDMG; 
    private int reloadTime; //baseado em turnos
    protected int currentAmmo;
    private int maxAmmo;

    //Variaveis do Superaquecimento 
    protected double currentHeat; //Calor padrão dos módulos
    private boolean overheated; // Indica se o módulo está superaquecido

    //Carregamento
    private boolean reloading;

    //Variaveis do Pane 
    private boolean malfunctioning; // Indica se o módulo está com pane
    private int malfunctionChance; // Chance de ocorrer uma pane a cada tiro (em porcentagem)
    private int malfunctionDuration; // Duração da pane em turnos
    private int malfunctionTurnsRemaining; // Contador de turnos restantes de pane


    protected Module(AmmoType ammoType, double baseDMG, int maxAmmo) {
        this.ammoType = ammoType;
        this.baseDMG = baseDMG;
        this.maxAmmo = maxAmmo;
        this.currentAmmo = maxAmmo;

        // Valores Padrão das Armas
        this.currentHeat = 0;
        this.overheated = false;
        this.reloading = false;
        this.malfunctioning = false;
        this.malfunctionChance = 15;
        this.malfunctionDuration = 3; // 3 turnos padrão
        this.malfunctionTurnsRemaining = 0;

    } 

    // Configurações Superaquecimento para cada arma
    public abstract double getHeatPerShot();
    public abstract double getHeatDissipationRate();
    public abstract double getOverheatThreshold();
    public abstract int getCoolingEffiency();

    // Configurações do Recarregamento. 
    public abstract int BaseReloadTurns();
    public abstract int getAmmoConsumption();

    // Configurar precisão de cada Arma
    public abstract int getAccuracyBack();
    public abstract int getAccuracyFrontal();
    public abstract int getAccuracyLateral();

    //Nota: Precisão total deve ser 100%

    //==================================================

    // Metodo de Calcular Dano 

    protected double PositionDMGBonus(int accuracy) {

        if (accuracy <= getAccuracyBack()) return 1.4;
        if (accuracy <= getAccuracyBack() + getAccuracyLateral()) return 1.2;
        if (accuracy <= getAccuracyBack() + getAccuracyLateral() + getAccuracyFrontal()) return 1.0;
        return 0.0; // Errou o tiro
    }

    // Metodos de Pontuação por parte do Tanque 

    public int calculateHitScore(int accuracy) {

        if (accuracy <= getAccuracyBack()) return 40;
        if (accuracy <= getAccuracyBack() + getAccuracyLateral()) return 20;
        if (accuracy <= getAccuracyBack() + getAccuracyLateral() + getAccuracyFrontal()) return 10;
        return 0; // Errou o tiro

    }

    public CombatResult CalculateDamageAndScore() {
        if(!CanShot()) {
            return new CombatResult(0.0,0,0);
        }

        int accuracy = (int)(Math.random() * 100 + 1);
        double positionDMGMultiplier = PositionDMGBonus(accuracy);
        double baseDamage = baseDMG * positionDMGMultiplier;
        double finalDamage = baseDamage * ammoType.getAmmoBonus(); 
        int hitScore = calculateHitScore(accuracy);

        // Caracteristicas de Munição e Superaquecimento
        currentAmmo -= getAmmoConsumption();
        currentHeat += getHeatPerShot();

        if (currentAmmo <= 0) {
            StartReload();
        }
        
        if (currentHeat >= getOverheatThreshold()) {
            checkOverheat();
        }
        
        Malfunction(); //Pane

        return new CombatResult(finalDamage, hitScore, accuracy);
    }

    // Verifica se o módulo pode atirar

    public boolean CanShot() {
        return currentAmmo > 0 && 
        !overheated && 
        !malfunctioning;
    }

    // = = = = Metodo de Recargar o Modulo = = = = = = 

    public void UpdateReload () {
        if(!reloading) return;

        if (reloadTime > 0) {
             reloadTime--;
        }

        if (reloadTime <= 0) {
            currentAmmo = maxAmmo;
            endReload();
        }
    }

    public void StartReload () {
        if(currentAmmo < maxAmmo && !reloading) {
            reloading = true;
            reloadTime = BaseReloadTurns();
            System.out.println("Reloading started! Will take " + reloadTime + " turns.");
        }
    }

    public void endReload() {
        reloading = false; 
        System.out.println("Weapon fully reloaed!");
    }

    // - - - - Metodos do Superaquecimento = = = = = =

    public void checkOverheat() {
        overheated = true;
        currentHeat = (int) getOverheatThreshold(); // Define o calor atual para o limiar de superaquecimento
        System.out.println("Module has overheated! It will be non-operational until cooled down.");
    }

    public void endOverheat() {
        overheated = false;
        System.out.println("Module has cooled down and is operational again.");
    }

    public void updateCooling() {
        if (overheated) {
            currentHeat -= getCoolingEffiency(); // Resfria mais rápido se estiver superaquecido
        } else {
            currentHeat -= getHeatDissipationRate(); // Resfria na taxa normal
        }

        if (currentHeat < 0) {
            currentHeat = 0; // Evita que o calor fique negativo
        }

        if (overheated && currentHeat <= getOverheatThreshold()) {
            endOverheat();
        }
    }

    //  = = = = Metodos do Pane = = = = = = 

    public void Malfunction(){
        if (!malfunctioning) {
            int chance = (int)(Math.random() * 100) + 1; // Gera um número aleatório entre 1 e 100

            if (chance <= malfunctionChance) {
                checkMalfunction();
            }
        }
    }

    public void checkMalfunction() {
        malfunctioning = true;
        malfunctionTurnsRemaining = malfunctionDuration;
        System.out.println("Module has malfunctioned! It will be non-operational for " + malfunctionDuration + " turns.");
    }

    public void updateMalfunction() {
        if (malfunctioning) {
            malfunctionTurnsRemaining--;
            if (malfunctionTurnsRemaining <= 0) {
                endMalfunction();
            } 
        }
    }

    public void endMalfunction() {
        malfunctioning = false;
        System.out.println("Module has been repaired and is operational again.");
    }

    // ==========================================================================================================================

    public AmmoType getAmmoType() {
        return ammoType;
    }

    public void setAmmoType(AmmoType ammoType) {
        this.ammoType = ammoType;
    }

    public double getBaseDMG() {
        return baseDMG;
    }

    public void setBaseDMG(double baseDMG) {
        this.baseDMG = baseDMG;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public void setCurrentAmmo(int currentAmmo) {
        this.currentAmmo = currentAmmo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public int getMalfunctionDuration() {
        return malfunctionDuration;
    }

    public void setMalfunctionDuration(int malfunctionDuration) {
        this.malfunctionDuration = malfunctionDuration;
    }

    public int getMalfunctionTurns() {
        return malfunctionTurnsRemaining;
    }

    public void setMalfunctionTurns(int malfunctionTurns) {
        this.malfunctionTurnsRemaining = malfunctionTurns;
    }

}
