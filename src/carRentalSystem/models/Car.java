package carRentalSystem.models;

public class Car {
    private final String id;
    private final String make;
    private final String model;
    private final String modelYear;
    private final CarType carType;
    private final String licensePlateNo;

    public Car(String id, String make, String model, String modelYear, CarType carType, String licensePlateNo) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.modelYear = modelYear;
        this.carType = carType;
        this.licensePlateNo = licensePlateNo;
    }

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getModelYear() {
        return modelYear;
    }

    public CarType getCarType() {
        return carType;
    }

    public String getLicensePlateNo() {
        return licensePlateNo;
    }

}
