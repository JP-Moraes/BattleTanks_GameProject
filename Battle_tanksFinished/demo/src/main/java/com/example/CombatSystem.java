package com.example;

import java.util.List;
import java.util.Random;

public class CombatSystem {
    private TeamManager manager;
    private Random random;

    public CombatSystem(TeamManager manager) {
        this.manager = manager;
        this.random = new Random();
    }

    // = = = = = METODO PRINCIPAL DE TURNO = = = = = = 

    public void ExecuteBattleTurn() {

        List<Tank> humanTeam = manager.getHumanTeam();
        List<Tank> aiTeam = manager.getAiTeam();

        // Turno do Humano

        for (Tank attacker : humanTeam) {
            if(attacker.IsAlive()) {
                Tank target = selectRandomTarget(aiTeam);
                if (target != null && target.IsAlive()) {
                    ExecuteAttack(attacker, target);
                }
            }
        }

        // Turno da IA
        for (Tank attacker : aiTeam) {
            if (attacker.IsAlive()){
                Tank target = selectRandomTarget(humanTeam);
                if (target != null && target.IsAlive()) {
                    ExecuteAttack(attacker, target);
                }
            }
            
        }

        DisplayBattleStatus();
    }

    // = = = = METODO QUE EXECUTA O ATAQUE = = = = = = =

    public void ExecuteAttack(Tank attacker, Tank target) {
         System.out.println("\n " + attacker.getCodename() + " → " + target.getCodename());

         CombatResult result = attacker.getWeapon().CalculateDamageAndScore();

         //Recebe dano e Adiciona Pontuação
         if (result.getDamage() > 0) {

            target.TakeDamage(result.getDamage());
            target.AddHitScore(result.getScore());

            // Pontuação da destruição
            if (!target.IsAlive()) {
            int destructionBonus = 50;
            attacker.AddDestructionScore(destructionBonus);
            System.out.println("BOOM!!!" + target.getCodename() + " DESTROYED! +" + destructionBonus + " points");
          }

           // Mostra resultados
            System.out.println(" Accuracy: " + result.getAccuracy());
            System.out.println(" Damage: " + result.getDamage());
            System.out.println(" Score: " + result.getScore());
            System.out.println("   " + target.getCodename() + " Health: " + target.getIntegrity() + "%");
        } else {
            System.out.println("MISSED! No damage dealt");
        }
   }

    // = = = MÉTODOS DE SELEÇÃO DE ALVO = = =
    
    private Tank selectRandomTarget(List<Tank> targets) {
        List<Tank> aliveTargets = targets.stream()
            .filter(Tank::IsAlive)
            .toList();
        
        if (aliveTargets.isEmpty()) return null;
        
        return aliveTargets.get(random.nextInt(aliveTargets.size()));
    }

    // = = = = Metodo de Status = = = = = = 
    public void DisplayBattleStatus() {
        System.out.println("\n=== BATTLE STATUS ===");

        System.out.println(" PLAYER TEAM:");
         manager.getHumanTeam().forEach(tank -> {
            String status = tank.IsAlive() ? "HP" + tank.getIntegrity() + "%" : "DESTROYED";
            System.out.println("   " + tank.getCodename() + ": " + status + " | Score: " + tank.getTotalScore());
        });


         System.out.println(" AI TEAM:");
        manager.getAiTeam().forEach(tank -> {
            String status = tank.IsAlive() ? "HP" + tank.getIntegrity() + "%" : "DESTROYED";
            System.out.println("   " + tank.getCodename() + ": " + status + " | Score: " + tank.getTotalScore());
        });
    }


    // = = = = = Condição de vitoria = = = = = =
    public boolean IsBattleOver() {

        //Checa se tem PELO MENOS UM Tanque vivo.

        boolean humanTeamAlive = manager.getHumanTeam().stream().anyMatch(Tank :: IsAlive);
        boolean aiTeamAlive = manager.getAiTeam().stream().anyMatch(Tank :: IsAlive);

        return !humanTeamAlive || !aiTeamAlive;
    }

    //Resultados do tanque
    public void DisplayBattleResults() {
        System.out.println(" = = = =  BATTLE RESULTS = = = = = ");

        int humanTeamResult = manager.getHumanTeam().stream()
        .mapToInt(Tank::getTotalScore)
        .sum();

        int aiTeamResult = manager.getAiTeam().stream()
        .mapToInt(Tank::getTotalScore)
        .sum();

        System.out.println("Human Team Total Score: " + humanTeamResult);
        System.out.println("AI Team Total Score: " + aiTeamResult);

        if (humanTeamResult > aiTeamResult) {
            System.out.println("Human Team wins!");
        } else if (humanTeamResult < aiTeamResult) {
            System.out.println("Ai Team wins!");
        } else {
            System.out.println("Its a tie!");
        }

    }

}
