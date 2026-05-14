package hotelManagementSystem.models.guest;

public class Guest {
    private final String guestId;
    private final String name;
    private final String aadharId;
    private final Gender gender;
    private final String contactInfo;
    private final int age;

    public Guest(String guestId, String name, String aadharId, Gender gender, int age, String contactInfo) {
        this.guestId = guestId;
        this.name = name;
        this.aadharId = aadharId;
        this.gender = gender;
        this.age = age;
        this.contactInfo = contactInfo;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getName() {
        return name;
    }

    public String getAadharId() {
        return aadharId;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    @Override
    public String toString() {
        return "Guest [guestId=" + guestId + ", name=" + name + "]";
    }

}
