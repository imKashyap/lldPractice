package coffeeVendingMachine;

import java.util.Map;

class Espresso implements Coffee {
    public String getName() { return "Espresso"; }
    public double getPrice() { return 30.0; }
    public Map<Ingredient, Integer> getRecipe() {
        return Map.of(
                new Ingredient("Water"), 50,
                new Ingredient("CoffeeBeans"), 20
        );
    }
}
