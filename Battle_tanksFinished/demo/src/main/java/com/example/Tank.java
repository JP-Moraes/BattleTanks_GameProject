package com.example;

public abstract class Tank {

    // Atributos comuns a todos os tanques
    // 2. Não tem velocidade. Logica de turnos.

    private String codename; 
    private int id;
    private TankClass classType;
    private PilotType pilotType;
    private TankStatus status;
    private int shield;
    private int integrity;
    private int repairTurn; // Tempo para concertar (recebe do metodo)
    private Module weapon;

    //Pontuação 
    private int hitScore;          
    private int destructionScore;   
    private int totalScore; 
    

    public Tank(String codename, int id, TankClass classType, PilotType pilotType, int shield) {
        this.codename = codename;
        this.id = id;
        this.classType = classType;
        this.pilotType = pilotType;
        this.shield = shield;

        this.status = TankStatus.ACTIVE;
        this.integrity = 100; // Integridade inicial
        this.destructionScore = 0;

        InitializeTankSettings();
        this.weapon = CreateModule();

    }

    protected abstract void InitializeTankSettings(); // Metodo abstrato para configurações específicas de cada tanque (Override)
    protected abstract Module CreateModule(); // Metodo abstrato para configurar os modulos do tanque (Override)
    protected abstract int getRepairTurnsDuration(); 


    // Metodo que verifica se o tanque ainda está ativo

    public boolean IsAlive() {
        return status == TankStatus.ACTIVE && integrity > 0;
    }

    // = = = = METODOS DE REPARO = = = = //

    public boolean CanRepair() {
        if (integrity < 50 &&
                status != TankStatus.DESTROYED &&
                status != TankStatus.UNDER_REPAIR &&
                integrity > 0) {

                    return true;
                }

        return false;
    } 

    public void StartRepair() {
        if (CanRepair()) {
            status = TankStatus.UNDER_REPAIR;
            repairTurn = getRepairTurnsDuration();
            System.out.println(codename + " is under repair for " + repairTurn + " turns.");
        } 
    }

    public void ProcessRepairTurn() {
        if (status == TankStatus.UNDER_REPAIR) {
            repairTurn--;
            integrity = Math.min(100, integrity + 15); // Repara 15 de integridade por turno, até o máximo de 100

            System.out.println(codename + " is being repaired. Remaining turns: " + repairTurn + ". Current integrity: " + integrity);

            if (repairTurn <= 0 || integrity >= 100) {
                 CompleteRepair();
            }
        }  
    }

    public void CompleteRepair() {
        status = TankStatus.ACTIVE; 
        repairTurn = 0;
        integrity = Math.min(100, integrity); // Garante que a integridade não exceda 100
        System.out.println(codename + " has completed repairs and is now active with integrity: " + integrity);
    }


    // = = = METODO DE TOMAR DANO = = = =

    public void TakeDamage(double damage) {

        if (status == TankStatus.DESTROYED) {
            System.out.println(codename + " already destroyed!");
            return;
        }

        if(shield > 0) {
            double damageToShield = Math.min(shield, damage);
            shield -= damageToShield;
            double damageToIntegrity = damage - damageToShield;

            if (damageToIntegrity > 0) {
            integrity -= damageToIntegrity;
            System.out.println("💥 Took " + damageToIntegrity + " integrity damage. Health: " + integrity + "%");

           } else {
            integrity -= damage;
            System.out.println("💥 Took " + damage + " integrity damage. Health: " + integrity + "%");

           }
        }

        if (integrity <= 0) {
            status = TankStatus.DESTROYED;
            integrity = 0;
            System.out.println("💀 " + codename + " DESTROYED!");
        } 
    }

    public CombatResult AttackAndScore (Tank target) {

        if (!IsAlive() || !weapon.CanShot()) {
            return new CombatResult(0.0,0,0);
        }

        CombatResult result = weapon.CalculateDamageAndScore();
        double damage = result.getDamage();

        if (damage > 0 && target != null) {
            target.TakeDamage(damage);
        }

        return result;
    }

    // = = = = Metodo de pontuação geral = = = =

    public void AddDestructionScore(int points) {
        this.destructionScore += points;  
        UpdateTotalScore();
        System.out.println("💀 " + codename + " +" + points + " destruction points!");
    }

    public void AddHitScore(int points) {
        this.hitScore += points;
        UpdateTotalScore();
        System.out.println("🎯 " + codename + " +" + points + " hit points! Total: " + hitScore);
    }

    private void UpdateTotalScore() {
        this.totalScore = hitScore + destructionScore;
    }


    //================================================================================================================

    // GETTERS E SETTERS

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TankClass getClassType() {
        return classType;
    }

    public void setClassType(TankClass classType) {
        this.classType = classType;
    }

    public PilotType getPilotType() {
        return pilotType;
    }

    public void setPilotType(PilotType pilotType) {
        this.pilotType = pilotType;
    }

    public TankStatus getStatus() {
        return status;
    }

        
    public void setStatus(TankStatus status) {
        this.status = status;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getIntegrity() {
        return integrity;
    }

    public void setIntegrity(int integrity) {
        this.integrity = integrity;
    }

    public int getRepairTurn() {
        return repairTurn;
    }

    public void setRepairTurn(int repairTurn) {
        this.repairTurn = repairTurn;
    }

    public Module getWeapon() {
        return weapon;
    }

    public void setWeapon(Module weapon) {
        this.weapon = weapon;
    }

    public int getHitScore() { return hitScore; }

    public int getDestructionScore() { return destructionScore; }

    public int getTotalScore() { return totalScore; }

    

}

