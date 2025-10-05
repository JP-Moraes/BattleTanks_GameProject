package com.example;

import java.util.scanner;

public class GameEngine() {
    
    private TankManagement registry; 
    private TankCreator creator; 
    private TeamManager manager; 
    private CombatSystem system; 
    private Scanner scanner;
    private boolean gameRunning; 

    public GameEngine() {
        InitializeGame();
    }

    public void InitializeGame() {

        // INICIALIZA COMPONENTES BASICOS

        this.registry = new TankManagement();
        this.manger =  new TeamManager(registry);
        this.scanner = new Scanner(System.in); 

        System.out.println("✅ Game initialized successfully!");
        System.out.println("🚀 Ready to play!");

    }

    public void StartGame() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        🎯 TANK BATTLE ARENA");
        System.out.println("=".repeat(50));
        
        while (gameRunning) {

            if (!inBattle) {
                ShowMainMenu();
            } else {
                RunBattle();
            }
        }
        
        shutdownGame();
    }

     // = = = MENU PRINCIPAL (VAMOS IMPLEMENTAR AOS POUCOS) = = =
    private void ShowMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. 🏭 Tank Management");
        System.out.println("2. 🎮 Game Setup");
        System.out.println("3. ⚔️ Start Battle");
        System.out.println("4. 🚪 Exit");
        System.out.print("Choose: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1 -> ShowTankManagement();
            case 2 -> ShowGameSetup();
            case 3 -> StartBattlePreparation();
            case 4 -> gameRunning = false;
            default -> System.out.println("❌ Invalid choice!");
        }
    }

    // = = = MÉTODOS BÁSICOS (IMPLEMENTAREMOS DEPOIS) = = =
    private void ShowTankManagement() {
        System.out.println("🔧 Tank Management - TO BE IMPLEMENTED");
        // Aqui vamos chamar o TankCreator depois
    }
    
    private void ShowGameSetup() {
        System.out.println("🎯 Game Setup - TO BE IMPLEMENTED");
        // Aqui vamos configurar modo e arena
    }
    
    private void StartBattlePreparation() {
        System.out.println("⚔️ Preparing Battle - TO BE IMPLEMENTED");
        // Aqui vamos verificar se pode iniciar batalha
    }
    
    private void RunBattle() {
        System.out.println("🎯 Battle in Progress - TO BE IMPLEMENTED");
        // Aqui vamos rodar o CombatSystem
    }

    // = = = MÉTODOS UTILITÁRIOS = = =
    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void shutdownGame() {
        System.out.println("\n🔄 Shutting down game...");
        scanner.close();
        System.out.println("👋 Thanks for playing Tank Battle Arena!");
    }

}