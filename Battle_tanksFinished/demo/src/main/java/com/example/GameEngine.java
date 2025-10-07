package com.example;

import java.util.List;
import java.util.Scanner;

public class GameEngine {

     // = = = COMPONENTES PRINCIPAIS = = =
    private TankManagement registry;
    private TeamManager teamManager;
    private CombatSystem combatSystem;
    private TankCreator creator;
    private Scanner scanner;
    private boolean gameRunning; 

    // = = = ESTADO DO JOGO = = =
    private GameMode currentGameMode;
    private Arena currentArena;
    private boolean inBattle;

    public GameEngine() {
        initializeGame();
    }

     private void initializeGame() {
        System.out.println("🎮 Initializing Tank Battle Arena...");
        
        this.registry = new TankManagement();
        this.teamManager = new TeamManager(registry);
        this.creator = new TankCreator(registry);
        this.scanner = new Scanner(System.in);
        this.gameRunning = true;
        this.inBattle = false;
        
        System.out.println("Game initialized successfully!");
        System.out.println("Ready to play!");
    }
    
    // = = = MÉTODO PRINCIPAL = = =
    public void startGame() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("          TANK BATTLE ARENA");
        System.out.println("=".repeat(50));
        
        while (gameRunning) {
            if (!inBattle) {
                showMainMenu();
            } else {
                runBattle();
            }
        }
        
        shutdownGame();
    }

    private void showMainMenu() {

        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Tank Management");
        System.out.println("2. Game Setup");
        System.out.println("3. Start Battle");
        System.out.println("4. Exit");
        System.out.print("Choose: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1 -> showTankManagement();
            case 2 -> showGameSetup();
            case 3 -> StartBattlePreparation();
            case 4 -> gameRunning = false;
            default -> System.out.println("Invalid choice!");
        }
    }

    private void showTankManagement() {
        while (true) {

            System.out.println("\n=== 🏭 TANK MANAGEMENT ===");
            System.out.println("1. Create New Tank");
            System.out.println("2. View All Tanks");
            System.out.println("3. Search Tank");
            System.out.println("4. Delete Tank");
            System.out.println("5. ↩Back to Main Menu");
            System.out.print("Choose: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1 -> creator.CreateNewTank(); 
                case 2 -> registry.displayAllTanks();
                case 3 -> creator.searchTank(); 
                case 4 -> creator.deleteTank(); 
                case 5 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
    
    private void showGameSetup() {
        while (true) {
        System.out.println("\n=== GAME SETUP ===");
        System.out.println("1. Select Game Mode");
        System.out.println("2. Select Arena");
        System.out.println("3. View Current Configuration");
        System.out.println("4. Apply Configuration");
        System.out.println("5. ↩ Back to Main Menu");
        System.out.print("Choose: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1 -> selectGameMode();
            case 2 -> selectArena();
            case 3 -> displayCurrentConfiguration();
            case 4 -> applyConfiguration();
            case 5 -> { return; }
            default -> System.out.println("Invalid choice!");
        }
      }
    }
    
    private void StartBattlePreparation() {
        if (!hasEnoughTanksForBattle()) {
        System.out.println("Cannot start battle - not enough tanks!");
        System.out.println("You need at least 2 tanks (1 per team)");
        return;
    }
         if (!teamManager.areTeamsReady()) {
        System.out.println("🔄 Teams not configured. Creating teams automatically...");
        if (teamManager.CreateTeamAuto()) {
            System.out.println("Team created automatically!");
        } else {
            System.out.println("Failed to create teams!");
        }
      } 

      displayBattleConfiguration();

      if (!confirmBattleStart()) {
        System.out.println(" Battle cancelled by player");
        return;
      }

      startBattle();

    }
    
    private void runBattle() {
        System.out.println("\n BATTLE PHASE STARTED!");
    
        int turnNumber = 1;
    
        while (!combatSystem.IsBattleOver() && inBattle) {
        System.out.println("\n" + "#".repeat(40));
        System.out.println("           TURN " + turnNumber);
        System.out.println("#".repeat(40));
        
        // Executar turno
        combatSystem.ExecuteBattleTurn();
        turnNumber++;
        
        // Pequena pausa entre turnos (opcional)
        if (!combatSystem.IsBattleOver()) {
            System.out.println("\n Preparing next turn...");
            wait(3000);
        }
      }

      endBattle();
    }

    // =====================================================================================================================

    // Metodos de Batalha
    private void endBattle() {
    System.out.println("\n" + "#".repeat(40));
    System.out.println("          BATTLE COMPLETED!");
    System.out.println("#".repeat(40));
    
    // Mostrar resultados finais
    combatSystem.DisplayBattleResults();

    System.out.println("\n Exporting battle results to CSV...");
    boolean exportSuccess = CSV.exportBattleResults(
        teamManager, 
        currentGameMode, 
        currentArena
    );
    
    if (exportSuccess) {
        System.out.println("Battle data saved successfully!");
    }
    
    // Voltar ao menu principal
    this.inBattle = false;
    this.combatSystem = null;
    
    System.out.println("\n🎮 Returning to main menu...");
    wait(2000);
   }

   //Espera entre turnos
   private void wait(int milliseconds) {
    try {
        Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
  }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void shutdownGame() {
        System.out.println("\n Shutting down game...");
        scanner.close();
        System.out.println("Thanks for playing Tank Battle Arena!");
    }

    private void startBattle() {
    System.out.println("\n" + "".repeat(30));
    System.out.println("          BATTLE STARTING!");
    System.out.println("".repeat(30));
    
    this.combatSystem = new CombatSystem(teamManager);
    this.inBattle = true;
    
    
       runBattle();
    }

    private boolean confirmBattleStart() {
    System.out.print("\n Start battle with this configuration? (y/n): ");
    String response = scanner.nextLine().trim().toLowerCase();
    return response.equals("y") || response.equals("yes");

    }

    private boolean hasEnoughTanksForBattle() {
    int activeTanks = registry.getActiveTanks().size();
    int minTanksRequired = 2; // Mínimo: 1vs1
    
    if (activeTanks < minTanksRequired) {
        System.out.println(" Only " + activeTanks + " active tanks available");
        System.out.println(" Need at least " + minTanksRequired + " tanks for battle");
        return false;
    }
    
    return true;
    }

    private void displayBattleConfiguration() {
    System.out.println("\n=== BATTLE CONFIGURATION ===");
    System.out.println(" Game Mode: " + currentGameMode);
    System.out.println(" Arena: " + currentArena.getName());
    
    // Mostra composição dos times
    teamManager.displayTeams();
    
    System.out.println("\nPlayer Team Score: " + calculateTeamScore(teamManager.getHumanTeam()));
    System.out.println("\nAI Team Score: " + calculateTeamScore(teamManager.getAiTeam()));
    }

    private int calculateTeamScore(List<Tank> team) {
    return team.stream()
            .mapToInt(Tank::getTotalScore)
            .sum();
    }

    //=================================================================================================================

    //METODOS QUE CONFIGURA O JOGO

    private void selectGameMode() {
    System.out.println("\n=== SELECT GAME MODE ===");
    System.out.println("1. 1v1 Duel");
    System.out.println("2. 3v3 Team Battle");
    System.out.println("3. 6v6 Full War");
    System.out.println("4. Cancel");
    System.out.print("Choose mode: ");
    
    int choice = getIntInput();
    
    switch (choice) {
        case 1 -> {
            currentGameMode = GameMode.ONE_VS_ONE;
            System.out.println("Game Mode set to: 1v1 Duel");
        }
        case 2 -> {
            currentGameMode = GameMode.THREE_VS_THREE;
            System.out.println("Game Mode set to: 3v3 Team Battle");
        }
        case 3 -> {
            currentGameMode = GameMode.SIX_VS_SIX;
            System.out.println("Game Mode set to: 6v6 Full War");
        }
        case 4 -> System.out.println("Game mode selection cancelled");
        default -> System.out.println(" Invalid game mode!");
       }
    }

    private void selectArena() {
    System.out.println("\n=== SELECT ARENA ===");
    
    Arena[] arenas = Arena.values();
    for (int i = 0; i < arenas.length; i++) {
        System.out.println((i + 1) + ". " + arenas[i].getName());
    }
    System.out.println((arenas.length + 1) + ".  Cancel");
    
    System.out.print("Choose arena: ");
    int choice = getIntInput();
    
    if (choice >= 1 && choice <= arenas.length) {
        currentArena = arenas[choice - 1];
        System.out.println(" Arena set to: " + currentArena.getName());
    } else if (choice == arenas.length + 1) {
        System.out.println(" Arena selection cancelled");
    } else {
        System.out.println(" Invalid arena choice!");
    }
  }

  private void displayCurrentConfiguration() {
      System.out.println("\n=== CURRENT CONFIGURATION ===");
      System.out.println(" Game Mode: " + currentGameMode);
      System.out.println(" Arena: " + currentArena.getName());
    
    // Mostra requisitos do modo atual
      int requiredTanks = currentGameMode.getTeamSize() * 2;
      int availableTanks = registry.getActiveTanks().size();
    
      System.out.println("\n Requirements for " + currentGameMode + ":");
      System.out.println("   • Tanks needed: " + requiredTanks + " (" + currentGameMode.getTeamSize() + " per team)");
      System.out.println("   • Tanks available: " + availableTanks);
    
      if (availableTanks >= requiredTanks) {
        System.out.println(" Ready for battle!");
    } else {
        System.out.println("Need " + (requiredTanks - availableTanks) + " more tanks");
    }
 }

 private void applyConfiguration() {
    System.out.println("\n=== APPLYING CONFIGURATION ===");
    
    // Verifica se tem tanques suficientes para o modo selecionado
    int requiredTanks = currentGameMode.getTeamSize() * 2;
    int availableTanks = registry.getActiveTanks().size();
    
    if (availableTanks < requiredTanks) {
        System.out.println(" Not enough tanks for " + currentGameMode);
        System.out.println(" Need " + requiredTanks + " tanks, but only have " + availableTanks);
        System.out.println(" Create more tanks or select a different game mode");
        return;
    }
    
    // Cria times para o modo selecionado
    System.out.println(" Creating teams for " + currentGameMode + "...");
    boolean success = teamManager.CreateBalancedTeams(currentGameMode);
    
    if (success) {
        System.out.println("Configuration applied successfully!");
        System.out.println("Teams are ready for " + currentGameMode + " in " + currentArena.getName());
        teamManager.displayTeams();
    } else {
        System.out.println(" Failed to create teams!");
    }
}

  
}