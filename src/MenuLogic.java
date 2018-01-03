import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class MenuLogic {

    private static final double MAXIMUM_CHARGE = 5.00;
    private Scanner scanner;
    private String username;
    private ParkingPlaceManager parkingPlaceManager;
    private double budget;

    public MenuLogic (ParkingPlaceManager parkingPlaceManager) {
        this.parkingPlaceManager = parkingPlaceManager;
        scanner = new Scanner(System.in);
    }

    void printMenu() {
        do {

            System.out.println("\n============ MENU ============");
            System.out.println("1. Show free slots.");
            System.out.println("2. Park the vehicle.");
            System.out.println("3. Dismiss entire parking lot.");
            System.out.println("4. Swap vehicles by places.");
            System.out.println("5. Collect fees.");
            System.out.println("6. Build new parking place.");
            System.out.println("7. Show budget.");
            System.out.println("8. Save parking place to file.");
            System.out.println("0. Exit program.");

            System.out.print("\n" + username + ", choose your action: ");
            int chooseAction;
            chooseAction = Integer.parseInt(scanner.nextLine());

            parseAnswer(chooseAction);
        } while (true);
    }

    private void parseAnswer(int chooseAction) {
        switch (chooseAction) {
            case 1:
                System.out.println("Amount of currently free parking places: " + countFreeSlots());
                System.out.print("====================================================================================\n");
                System.out.println("Amount of free parking places for CARS: " + countFreeCarSlots());
                System.out.print("====================================================================================\n");
                System.out.println("Amount of free parking places for HGV's: " + countFreeHGVSlots());
                System.out.print("====================================================================================\n");
                System.out.println("Amount of free parking places for MOTORCYCLES: " + countFreeMotoSlots());
                System.out.print("====================================================================================\n");

                printFreeCarSlotsID();
                printTakenCarSlotsID();
                printFreeHGVSlotsID();
                printTakenHGVSlotsID();
                printFreeMotoSlotsID();
                printTakenMotoSlotsID();
                System.out.println();
                break;

            case 2:
                System.out.println("Enter type of parking place.");
                System.out.println("C: Car" + "\nH: HGV" + "\nM: Motorcycle");
                String vehicleType = scanner.nextLine();
                chooseTypeOfPlaceToPark(vehicleType);
                break;

            case 3:
                dismissParkingPlace();
                System.out.println("All vehicles have been dismissed.");
                break;

            case 4:
                System.out.println("Enter first vehicle ID: ");
                int firstVehicleID = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter second vehicle ID: ");
                int secondVehicleID = Integer.parseInt(scanner.nextLine());
                swapVehiclesByPlaces(firstVehicleID, secondVehicleID);
                break;

            case 5:
                System.out.println("Enter amount: ");
                double charge = Double.parseDouble(scanner.nextLine());
                chargeDrivers(charge);
                printBudget(budget);
                break;

            case 6:
                System.out.println("Choose type of new parking place: "
                        + "\nC: CAR - 200.00"
                        + "\nH: HGV - 300.00"
                        + "\nM: MOTORCYCLE - 100.00");
                String typeOfParkingPlaceToBuild = scanner.nextLine();
                buildNewParkingPlace(typeOfParkingPlaceToBuild);
                break;

            case 7:
                printBudget(budget);
                break;

            case 8:
                try {
                    saveToFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 0:
                menuGoodBye();
                System.exit(0);
                break;

            default:
                System.out.println("Illegal operation. Menu item does not exist.");
                break;
        }
    }

    void menuGreeting() {
        System.out.println("============================ Welcome to Parking Manager ============================");
        System.out.print("Enter your username: ");
        username = scanner.nextLine();
        System.out.println("Hello, " + username + "!\n");
    }

    private void menuGoodBye() {
        System.out.println("\nGoodbye, " + username + "!");
    }

    private int countFreeCarSlots() {
        int freeCarSlotsCounter = 0;

        for (Map.Entry<Integer, ParkingPlace> integerParkingPlaceEntry : parkingPlaceManager.getParkingPlaceMap().entrySet()) {
            if (integerParkingPlaceEntry.getValue().getParkingType() == ParkingType.CAR
                    && !integerParkingPlaceEntry.getValue().isTaken()) {
                freeCarSlotsCounter++;
            }
        }
        return freeCarSlotsCounter;
    }

    private int countFreeMotoSlots() {
        int freeMotoSlotsCounter = 0;
        for (Map.Entry<Integer, ParkingPlace> integerParkingPlaceEntry : parkingPlaceManager.getParkingPlaceMap().entrySet()) {
            if (integerParkingPlaceEntry.getValue().getParkingType() == ParkingType.MOTORCYCLE
                    && !integerParkingPlaceEntry.getValue().isTaken()) {
                freeMotoSlotsCounter++;
            }
        }
        return freeMotoSlotsCounter;
    }

    private int countFreeHGVSlots() {
        int freeHGVSlotsCounter = 0;

        for (Map.Entry<Integer, ParkingPlace> integerParkingPlaceEntry : parkingPlaceManager.getParkingPlaceMap().entrySet()) {
            if (integerParkingPlaceEntry.getValue().getParkingType() == ParkingType.HGV
                    && !integerParkingPlaceEntry.getValue().isTaken()) {
                freeHGVSlotsCounter++;
            }
        }
        return freeHGVSlotsCounter;
    }

    private int countFreeSlots() {
        int freeParkingSlots = (countFreeCarSlots() + countFreeHGVSlots() + countFreeMotoSlots());
        return freeParkingSlots;
    }

    private void printFreeCarSlotsID() {
        System.out.print("Currently free parking places for CARS:  ");

        for (Map.Entry<Integer, ParkingPlace> integerParkingPlaceEntry : parkingPlaceManager.getParkingPlaceMap().entrySet()) {
            if (integerParkingPlaceEntry.getValue().getParkingType() == ParkingType.CAR
                    && !integerParkingPlaceEntry.getValue().isTaken()) {
                System.out.print(integerParkingPlaceEntry.getKey() + "|");
            }
        }
        System.out.print("\n====================================================================================");
    }

    private void printFreeMotoSlotsID() {
        System.out.print("\nCurrently free parking places for MOTORCYCLES:  ");

        for (Map.Entry<Integer, ParkingPlace> integerParkingPlaceEntry : parkingPlaceManager.getParkingPlaceMap().entrySet()) {
            if (integerParkingPlaceEntry.getValue().getParkingType() == ParkingType.MOTORCYCLE
                    && !integerParkingPlaceEntry.getValue().isTaken()) {
                System.out.print(integerParkingPlaceEntry.getKey() + "|");
            }
        }
        System.out.print("\n====================================================================================");
    }

    private void printFreeHGVSlotsID() {
        System.out.print("\nCurrently free parking places for HGV's:  ");

        for (Map.Entry<Integer, ParkingPlace> integerParkingPlaceEntry : parkingPlaceManager.getParkingPlaceMap().entrySet()) {
            if (integerParkingPlaceEntry.getValue().getParkingType() == ParkingType.HGV
                    && !integerParkingPlaceEntry.getValue().isTaken()) {
                System.out.print(integerParkingPlaceEntry.getKey() + "|");
            }
        }
        System.out.print("\n====================================================================================");
    }

    private void printTakenCarSlotsID() {
        System.out.print("\nCurrently taken parking places for CARS: ");

        for (Map.Entry<Integer, ParkingPlace> integerParkingPlaceEntry : parkingPlaceManager.getParkingPlaceMap().entrySet()) {
            if (integerParkingPlaceEntry.getValue().getParkingType() == ParkingType.CAR
                    && integerParkingPlaceEntry.getValue().isTaken()) {
                System.out.print(integerParkingPlaceEntry.getKey() + "|");
            }
        }
        System.out.print("\n====================================================================================");
    }

    private void printTakenMotoSlotsID() {
        System.out.print("\nCurrently taken parking places for MOTORCYCLES: ");

        for (Map.Entry<Integer, ParkingPlace> integerParkingPlaceEntry : parkingPlaceManager.getParkingPlaceMap().entrySet()) {
            if (integerParkingPlaceEntry.getValue().getParkingType() == ParkingType.MOTORCYCLE
                    && integerParkingPlaceEntry.getValue().isTaken()) {
                System.out.print(integerParkingPlaceEntry.getKey() + "|");
            }
        }
        System.out.print("\n====================================================================================");
    }

    private void printTakenHGVSlotsID() {
        System.out.print("\nCurrently taken parking places for HGV's: ");

        for (Map.Entry<Integer, ParkingPlace> integerParkingPlaceEntry : parkingPlaceManager.getParkingPlaceMap().entrySet()) {
            if (integerParkingPlaceEntry.getValue().getParkingType() == ParkingType.HGV
                    && integerParkingPlaceEntry.getValue().isTaken()) {
                System.out.print(integerParkingPlaceEntry.getKey() + "|");
            }
        }
        System.out.print("\n====================================================================================");
    }

    private void parkVehicle(ParkingType parkingType) {
        for (ParkingPlace parkingPlace : parkingPlaceManager.getParkingPlaceMap().values()) {
            if (parkingPlace.getParkingType() == parkingType && !parkingPlace.isTaken()) {
                parkingPlace.setTaken(true);
                break;
            }
        }
    }

    private void chooseTypeOfPlaceToPark(String vehicleType) {
        switch (vehicleType) {
            case "C":
                parkVehicle(ParkingType.CAR);
                System.out.println("Car has been parked.");
                break;
            case "H":
                parkVehicle(ParkingType.HGV);
                System.out.println("HGV has been parked.");
                break;
            case "M":
                parkVehicle(ParkingType.MOTORCYCLE);
                System.out.println("Motorcycle has been parked.");
                break;
            default:
                System.out.println("Illegal operation. Menu item does not exist.");
        }
    }

    private void dismissParkingPlace() {
        for (ParkingPlace parkingPlace : parkingPlaceManager.getParkingPlaceMap().values()) {
            parkingPlace.setTaken(false);
        }
    }

    private void swapVehiclesByPlaces(int firstVehicleID, int secondVehicleID) {
        if (parkingPlaceManager.getParkingPlaceMap().get(firstVehicleID).isTaken() || parkingPlaceManager.getParkingPlaceMap().get(secondVehicleID).isTaken()) {
            if (parkingPlaceManager.getParkingPlaceMap().get(firstVehicleID).getParkingType() == (parkingPlaceManager.getParkingPlaceMap().get(secondVehicleID).getParkingType())) {
                parkingPlaceManager.getParkingPlaceMap().put(firstVehicleID, parkingPlaceManager.getParkingPlaceMap().put(secondVehicleID, parkingPlaceManager.getParkingPlaceMap().get(firstVehicleID)));
                System.out.println("Replacing successful");
            } else {
                System.out.println("Cannot execute. Places have different type.");
            }
        } else {
            System.out.println("Cannot execute. One or more places are not taken.");
        }
    }

    private void chargeDrivers(double charge) {
        if (charge <= MAXIMUM_CHARGE) {
            for (ParkingPlace parkingPlace : parkingPlaceManager.getParkingPlaceMap().values()) {
                if (parkingPlace.isTaken()) {
                    collectFees(charge);
                }
            }
        } else {
            exodus(charge);
        }
    }

    private void exodusNotification(int driversLeftCounter) {
        System.out.println("The number of drivers, who left the parking lot due to a raised fees: " + driversLeftCounter);

    }

    private void exodus(double charge) {
        Random random = new Random();
        int driversLeftCounter = 0;
        for (ParkingPlace parkingPlace : parkingPlaceManager.getParkingPlaceMap().values()) {
            if (parkingPlace.isTaken()) {
                parkingPlace.setTaken(random.nextBoolean());
                if (!parkingPlace.isTaken()) {
                    driversLeftCounter++;
                } else {
                    collectFees(charge);
                }
            }
        }
        exodusNotification(driversLeftCounter);
    }

    private void collectFees(double charge) {
        budget += charge;
    }

    private void printBudget(double budget) {
        System.out.println("Budget available: " + budget);
    }

    private void buildNewParkingPlace(String typeOfSlotToBuild) {
        switch (typeOfSlotToBuild) {
            case "C":
                if (budget >= 200) {
                    parkingPlaceManager.setParkingPlaceID(parkingPlaceManager.getParkingPlaceID() + 1);
                    parkingPlaceManager.getParkingPlaceMap().put(parkingPlaceManager.getParkingPlaceID(), parkingPlaceManager.newCarSlot());
                    budget -= 200;
                    System.out.println("Parking place for car has been successfully built.");
                } else {
                    System.out.println("Cannot execute due to lack of funds.");
                }
                break;
            case "H":
                if (budget >= 300) {
                    parkingPlaceManager.setParkingPlaceID(parkingPlaceManager.getParkingPlaceID() + 1);
                    parkingPlaceManager.getParkingPlaceMap().put(parkingPlaceManager.getParkingPlaceID(), parkingPlaceManager.newHGVSlot());
                    budget -= 300;
                    System.out.println("Parking place for HGV has been successfully built.");
                } else {
                    System.out.println("Cannot execute due to lack of funds.");
                }
                break;
            case "M":
                if (budget >= 100) {
                    parkingPlaceManager.setParkingPlaceID(parkingPlaceManager.getParkingPlaceID() + 1);
                    parkingPlaceManager.getParkingPlaceMap().put(parkingPlaceManager.getParkingPlaceID(), parkingPlaceManager.newMotorcycleSlot());
                    budget -= 200;
                    System.out.println("Parking place for motorcycle has been successfully built.");
                } else {
                    System.out.println("Cannot execute due to lack of funds.");
                }
                break;
            default:
                System.out.println("Illegal operation. Menu item does not exist.");
            }
        }

    private void saveToFile() throws IOException {
        Files.write(Paths.get(".save.txt"), () -> parkingPlaceManager.getParkingPlaceMap().entrySet().stream().<CharSequence>map(e -> e.getKey() + "\t" + e.getValue()).iterator());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ParkingPlaceManager getParkingPlaceManager() {
        return parkingPlaceManager;
    }

    public void setParkingPlaceManager(ParkingPlaceManager parkingPlaceManager) {
        this.parkingPlaceManager = parkingPlaceManager;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public static double getMaximumCharge() {
        return MAXIMUM_CHARGE;
    }
}
