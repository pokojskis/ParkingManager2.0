import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ParkingPlaceManager {

    private Map<Integer, ParkingPlace> parkingPlaceMap;
    private Integer parkingPlaceID;
    private Random random;
    private ParkingPlace parkingPlace;

    public ParkingPlaceManager() {
        parkingPlaceMap = new HashMap<>();
    }

    void addParkingPlace() {
        for (int i = 1; i <= 100; i++) {
            parkingPlaceID = i;
            parkingPlaceMap.put(parkingPlaceID, randomizeParkingPlace());
        }
    }

    private ParkingPlace randomizeParkingPlace() {
        random = new Random();
        ParkingPlace parkingPlace = new ParkingPlace(randomizeParkingType(), random.nextBoolean());
        return parkingPlace;
    }

    private ParkingType randomizeParkingType() {
        random = new Random();
        int option;
        ParkingType randomizedType = null;
        option = random.nextInt(3);

        switch (option) {
            case 0: randomizedType = ParkingType.CAR;
            break;
            case 1: randomizedType = ParkingType.MOTORCYCLE;
            break;
            case 2: randomizedType = ParkingType.HGV;
            break;
        }
        return randomizedType;
    }

    ParkingPlace newCarSlot() {
        return new ParkingPlace(ParkingType.CAR, false);
    }

    ParkingPlace newMotorcycleSlot() {
        return new ParkingPlace(ParkingType.MOTORCYCLE, false);
    }

    ParkingPlace newHGVSlot() {
        return new ParkingPlace(ParkingType.HGV, false);
    }

    public Integer getParkingPlaceID() {
        return parkingPlaceID;
    }

    public void setParkingPlaceID(Integer parkingPlaceID) {
        this.parkingPlaceID = parkingPlaceID;
    }

    public Map<Integer, ParkingPlace> getParkingPlaceMap() {
        return parkingPlaceMap;
    }

    public void setParkingPlaceMap(Map<Integer, ParkingPlace> parkingPlaceMap) {
        this.parkingPlaceMap = parkingPlaceMap;
    }

    public ParkingPlace getParkingPlace() {
        return parkingPlace;
    }

    public void setParkingPlace (ParkingPlace parkingPlace) {
        this.parkingPlace = parkingPlace;
    }

}
