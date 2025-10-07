package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CSV {

    public static boolean exportBattleResults(TeamManager teamManager, GameMode gameMode, Arena arena) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String filename = "battle_results_" + timestamp + ".csv";
        
        try (FileWriter writer = new FileWriter(filename)) {
            // = = = CABEÇALHO = = =
            writer.write("TANK BATTLE ARENA - BATTLE RESULTS\n");
            writer.write("Generated:," + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            writer.write("Game Mode:," + gameMode + "\n");
            writer.write("Arena:," + arena.getName() + "\n");
            writer.write(",\n"); // Linha em branco
            
            // = = = TIME DO JOGADOR = = =
            writer.write("PLAYER TEAM\n");
            writer.write("Codename, Class, Pilot.Status, Shield, Integrity, Hit Score, Destruction Score, Total Score\n");
            
            List<Tank> playerTeam = teamManager.getHumanTeam();
            for (Tank tank : playerTeam) {
                writer.write(formatTankData(tank) + "\n");
            }
            writer.write("TOTAL,," + playerTeam.size() + " tanks: " + calculateTeamTotalScore(playerTeam) + "\n");
            writer.write(",\n"); // Linha em branco
            
            // = = = TIME DA IA = = =
            writer.write("AI TEAM\n");
            writer.write("Codename, Class, Pilot.Status, Shield, Integrity, Hit Score, Destruction Score, Total Score\n");
            
            List<Tank> aiTeam = teamManager.getAiTeam();
            for (Tank tank : aiTeam) {
                writer.write(formatTankData(tank) + "\n");
            }
            writer.write("TOTAL,," + aiTeam.size() + " tanks: " + calculateTeamTotalScore(aiTeam) + "\n");
            writer.write(",\n"); // Linha em branco
            
            // = = = ESTATÍSTICAS DA BATALHA = = =
            writer.write("BATTLE STATISTICS\n");
            writer.write("Category,Value\n");
            writer.write("Player Team Total Score," + calculateTeamTotalScore(playerTeam) + "\n");
            writer.write("AI Team Total Score," + calculateTeamTotalScore(aiTeam) + "\n");
            writer.write("Battle Winner," + determineWinner(playerTeam, aiTeam) + "\n");
            
            System.out.println(" Battle results exported to: " + filename);
            return true;
            
        } catch (IOException e) {
            System.out.println(" Error exporting battle results: " + e.getMessage());
            return false;
        }
    }

    private static String formatTankData(Tank tank) {
        String status = tank.IsAlive() ? "ACTIVE" : "DESTROYED";
            
        return String.format("%s ,%s ,%s ,%s ,%d ,%d ,%d ,%d ,%d",
            tank.getCodename(),
            tank.getClassType(),
            tank.getPilotType(),
            status,
            tank.getShield(),
            tank.getIntegrity(),
            tank.getHitScore(),
            tank.getDestructionScore(),
            tank.getTotalScore()
        );
    }

    private static int calculateTeamTotalScore(List<Tank> team) {
        return team.stream()
                .mapToInt(Tank::getTotalScore)
                .sum();
    }

    private static String determineWinner(List<Tank> playerTeam, List<Tank> aiTeam) {
        int playerScore = calculateTeamTotalScore(playerTeam);
        int aiScore = calculateTeamTotalScore(aiTeam);
        
        if (playerScore > aiScore) return "PLAYER TEAM";
        if (aiScore > playerScore) return "AI TEAM";
        return "DRAW";
    }
}
