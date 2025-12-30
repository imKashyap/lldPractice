package coffeeVendingMachine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InventoryManager {
    private static final InventoryManager instance = new InventoryManager();
    private final Map<Ingredient, Integer> stock = new ConcurrentHashMap<>();

    private InventoryManager() {}

    public static InventoryManager getInstance() {
        return instance;
    }

    public synchronized boolean isAvailable(Map<Ingredient, Integer> recipe) {
        for (Map.Entry<Ingredient, Integer> e : recipe.entrySet()) {
            if (stock.getOrDefault(e.getKey(), 0) < e.getValue()) return false;
        }
        return true;
    }

    public synchronized void deductIngredients(Map<Ingredient, Integer> recipe) {
        for (Map.Entry<Ingredient, Integer> e : recipe.entrySet()) {
            stock.put(e.getKey(), stock.get(e.getKey()) - e.getValue());
        }
    }

    public void refill(Ingredient ingredient, int qty) {
        stock.put(ingredient, stock.getOrDefault(ingredient, 0) + qty);
    }

    public void checkLowInventory() {
        for (Map.Entry<Ingredient, Integer> e : stock.entrySet()) {
            if (e.getValue() < 30) {
                System.out.println("[ALERT] Low on " + e.getKey().getName());
            }
        }
    }
}