package carRentalSystem.models;

public class Customer {
    private final String id;
    private final String name;
    private final String phoneNo;
    private final String licenseId;

    public Customer(String id, String name, String phoneNo, String licenseId) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.licenseId = licenseId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getphoneNo() {
        return phoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getLicenseId() {
        return licenseId;
    }
}
