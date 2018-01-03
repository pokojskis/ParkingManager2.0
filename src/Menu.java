public class Menu {

    private ParkingPlaceManager parkingPlaceManager;
    private MenuLogic menuLogic;

    public Menu() {
        parkingPlaceManager = new ParkingPlaceManager();
        menuLogic = new MenuLogic(parkingPlaceManager);
    }

    public void start() {
        parkingPlaceManager.addParkingPlace();
        menuLogic.menuGreeting();
        menuLogic.printMenu();
    }
}
