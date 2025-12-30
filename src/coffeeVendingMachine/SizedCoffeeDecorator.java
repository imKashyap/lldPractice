package coffeeVendingMachine;

import java.util.HashMap;
import java.util.Map;

class SizedCoffeeDecorator implements Coffee {
    private final Coffee baseCoffee;
    private final CupSize size;

    public SizedCoffeeDecorator(Coffee baseCoffee, CupSize size) {
        this.baseCoffee = baseCoffee;
        this.size = size;
    }

    public String getName() { return baseCoffee.getName() + " (" + size.name() + ")"; }

    public double getPrice() {
        return baseCoffee.getPrice() + size.getExtraCost();
    }

    public Map<Ingredient, Integer> getRecipe() {
        Map<Ingredient, Integer> scaled = new HashMap<>();
        for (Map.Entry<Ingredient, Integer> e : baseCoffee.getRecipe().entrySet()) {
            scaled.put(e.getKey(), (int)(e.getValue() * size.getMultiplier()));
        }
        return scaled;
    }
}
