import java.util.Objects;

public class ParkingPlace {

    private ParkingType parkingType;
    private boolean isTaken;

    public ParkingPlace(ParkingType parkingType, boolean isTaken) {
        this.parkingType = parkingType;
        this.isTaken = isTaken;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingPlace that = (ParkingPlace) o;
        return isTaken == that.isTaken &&
                parkingType == that.parkingType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(parkingType, isTaken);
    }

    @Override
    public String toString() {
        return "ParkingPlace{" +
                "parkingType=" + parkingType +
                ", isTaken=" + isTaken +
                '}';
    }
}
