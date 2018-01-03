public class Menu {

    ParkingPlaceManager parkingPlaceManager = new ParkingPlaceManager();
    MenuLogic menuLogic = new MenuLogic(parkingPlaceManager);

    public void start() {
        parkingPlaceManager.addParkingPlace();
        menuLogic.menuGreeting();
        menuLogic.printMenu();
    }
}
