package coffeeVendingMachine;

import java.util.List;

class Menu {
    private final List<Coffee> coffees = List.of(new Espresso(), new Latte(), new Cappuccino());

    public void displayOptions() {
        for (Coffee coffee : coffees) {
            System.out.println(coffee.getName() + " - ₹" + coffee.getPrice());
        }
        System.out.println("Sizes: SMALL, MEDIUM (+₹10), LARGE (+₹20)");
    }

    public Coffee getCoffeeByName(String name) {
        return coffees.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}