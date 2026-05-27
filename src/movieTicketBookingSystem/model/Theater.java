package movieTicketBookingSystem.model;

import java.util.ArrayList;
import java.util.List;

public class Theater {
    private final String theaterId;
    private final String name;
    private final String address;
    private final String city;
    private final List<Screen> screens;

    public Theater(String theaterId, String name, String address, String city, List<Screen> screens) {
        this.theaterId = theaterId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.screens = new ArrayList<>(screens);
    }

    public String getTheaterId() {
        return theaterId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public List<Screen> getScreens() {
        return List.copyOf(screens);
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
    }
}
