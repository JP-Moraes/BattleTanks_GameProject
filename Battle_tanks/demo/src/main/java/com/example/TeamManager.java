package com.example;

import java.util.ArrayList;
import java.util.List;

public class TeamManager {
    private TankManagement registry;
    private List<Tank> humanTeam;
    private List<Tank> aiTeam;

    public TeamManager(TankManagement registry) {
        this.registry = registry;
        this.humanTeam = new ArrayList<>();
        this.aiTeam = new ArrayList<>();
    }


    // = = = =  Metodos Principais = = = = =

    // Cria times para cada modo de maneira balanceada

    public boolean CreateBalancedTeams(GameMode gamemode) {
        ClearTeams();

        int teamSize = gamemode.getTeamSize();
        List<Tank> activeTanks = registry.getActiveTanks();

        if (activeTanks.size() < teamSize * 2){
            System.out.println("Not enough tanks for " + gamemode);
            return false;
        }

        System.out.println("Teams are ready for " + gamemode);
        return true;
    }

    //Cria os times baseado no Tipo de Piloto
    public void CreateTeamAuto() {

        List<Tank> allTanks = registry.getAllTanks();

        for (Tank tank : allTanks) {
            if (tank.getPilotType() == PilotType.HUMAN) {
                humanTeam.add(tank);
            } else {
                aiTeam.add(tank);
            }
        }

        System.out.println("Teams created automatically");
    }

    public void displayTeams() {
    System.out.println("\n=== 🎯 TEAMS ===");
    
    // ✅ MOSTRA TIME DO JOGADOR (já tem a list)
    System.out.println("👤 PLAYER TEAM (" + humanTeam.size() + " tanks):");
    for (Tank tank : humanTeam) {
        System.out.println("   🔸 " + tank.getCodename() + 
                         " [" + tank.getClassType() + " - " + tank.getPilotType() + "]");
    }
    
    // ✅ MOSTRA TIME DA IA (já tem a list)  
    System.out.println("\n🤖 AI TEAM (" + aiTeam.size() + " tanks):");
    for (Tank tank : aiTeam) {
        System.out.println("   🔸 " + tank.getCodename() + 
                         " [" + tank.getClassType() + " - " + tank.getPilotType() + "]");
    }
}


    // Metodos para os times de cada modo

    public void SetUp1v1() {
        CreateBalancedTeams(GameMode.ONE_VS_ONE);
    }

    public void SetUp3v3() {
        CreateBalancedTeams(GameMode.THREE_VS_THREE);
    }

    public void SetUp6v6() {
        CreateBalancedTeams(GameMode.SIX_VS_SIX);
    }

    public void ClearTeams() {
        humanTeam.clear();
        aiTeam.clear();
        System.out.println("Teams were cleared");
    }


    public List<Tank> getHumanTeam() {
        return new ArrayList<>(humanTeam);
    }


    public List<Tank> getAiTeam() {
        return new ArrayList<>(aiTeam);
    }
    
    public int GetHUMANTeamsize() {
        return humanTeam.size();
    }

    public int GetAITeamSize() {
        return aiTeam.size();
    }


    
    
    
}
