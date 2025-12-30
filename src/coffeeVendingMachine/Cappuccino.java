package coffeeVendingMachine;

import java.util.Map;

class Cappuccino implements Coffee {
    public String getName() { return "Cappuccino"; }
    public double getPrice() { return 45.0; }
    public Map<Ingredient, Integer> getRecipe() {
        return Map.of(
                new Ingredient("Water"), 50,
                new Ingredient("CoffeeBeans"), 20,
                new Ingredient("MilkFoam"), 30
        );
    }
}
