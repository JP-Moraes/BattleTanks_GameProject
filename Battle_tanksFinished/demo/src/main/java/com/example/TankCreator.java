package com.example;

import java.util.Scanner;

public class TankCreator {
    private TankManagement management;
    private Scanner scanner;

    TankCreator(TankManagement management) {
        this.management = management;
        this.scanner = new Scanner(System.in);
    }

    // = = = = METODOS QUE CRIAM O TANQUE = = = = = 

    public void CreateNewTank() {

        System.out.println("= = = = CREATE A NEW TANK = = = = = ");

        // Escolher a classe do Tanque
        TankClass classType = ChooseTankClass();
        if (classType == null) return;

        // Criar o codenome do Tanque
        System.out.println("Type the Tank's Codename: ");
        String codename = scanner.nextLine();

        // Escolher Piloto
        PilotType pilotType = ChoosePilotType();
        if (pilotType == null) return;

        //Definir Escudo de do tanque (dependo da classe, virá com bonus + o que for digitado)
        System.out.println("Define the Tank's Shield : ");
        int shield = Integer.parseInt(scanner.nextLine());

        //Cria e manda o novo Tanque para o Registrador
        Tank newTank = CreateTank(classType, codename, pilotType, shield);
        if (newTank != null) {
            management.registerTank(newTank);
        }
        
    }


    // Metodos auxiliares 

    private TankClass ChooseTankClass() {

        System.out.println("Choose Tank Class:");
        System.out.println("1. Heavy Tank");
        System.out.println("2. Medium Tank");
        System.out.println("3. Light Tank");
        System.out.println("4. Cancel");
        
        int choice = Integer.parseInt(scanner.nextLine());
        return switch (choice) {
            case 1 -> TankClass.HEAVY;
            case 2 -> TankClass.MEDIUM;
            case 3 -> TankClass.LIGHT;
            default -> null;
        };
    }

    private PilotType ChoosePilotType() {
        System.out.println("Choose Pilot Type:");
        System.out.println("1. Human");
        System.out.println("2. Ai");
        System.out.println("5. Cancel");
        
        int choice = Integer.parseInt(scanner.nextLine());
        return switch (choice) {
            case 1 -> PilotType.HUMAN;
            case 2 -> PilotType.AI;
            default -> null;
        };
    }

    private Tank CreateTank(TankClass classType, String Codename, PilotType pilotType, int shield) {

        try {
            return switch (classType) {

                case HEAVY -> new FortressTank(Codename, 0, classType, pilotType, shield);
                case MEDIUM -> new BlastTank(Codename, 0, classType, pilotType, shield);
                case LIGHT -> new SniperTank(Codename, 0, classType, pilotType, shield);
            };

        } catch (Exception e) {
            System.out.println("Error in creating a Tank" + e.getMessage());
            return null;
        }
    
    }


    // = = = MÉTODOS DE BUSCA E EXCLUSÃO = = =
    
    public void searchTank() {
        System.out.println("\n=== SEARCH TANK ===");
        System.out.print("Enter tank codename: ");
        String codename = scanner.nextLine();
        
        Tank tank = management.findTankByCodename(codename);
        if (tank != null) {
            management.displayTankInfo(tank);
        } else {
            System.out.println("Tank '" + codename + "' not found!");
        }
    }
    
    public void deleteTank() {
        System.out.println("\n=== DELETE TANK ===");
        System.out.print("Enter tank codename to delete: ");
        String codename = scanner.nextLine();
        
        boolean success = management.unregisterTank(codename);
        if (!success) {
            System.out.println("Tank '" + codename + "' not found!");
        }
    }

    
}
