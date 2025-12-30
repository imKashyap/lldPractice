package coffeeVendingMachine;


import java.util.Map;

class Latte implements Coffee {
    public String getName() { return "Latte"; }
    public double getPrice() { return 40.0; }
    public Map<Ingredient, Integer> getRecipe() {
        return Map.of(
                new Ingredient("Water"), 50,
                new Ingredient("CoffeeBeans"), 20,
                new Ingredient("Milk"), 30
        );
    }
}
