package coffeeVendingMachine;

import java.util.Map;

interface Coffee {
    String getName();
    double getPrice();
    Map<Ingredient, Integer> getRecipe();
}
