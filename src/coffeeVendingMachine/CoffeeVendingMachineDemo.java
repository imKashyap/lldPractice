package coffeeVendingMachine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CoffeeVendingMachineDemo {
    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine();
        InventoryManager inventory = InventoryManager.getInstance();

        // Refill ingredients
        inventory.refill(new Ingredient("Water"), 500);
        inventory.refill(new Ingredient("CoffeeBeans"), 200);
        inventory.refill(new Ingredient("Milk"), 200);
        inventory.refill(new Ingredient("MilkFoam"), 100);

        machine.displayMenu();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(() -> machine.processOrder("Espresso", CupSize.SMALL, new CashPayment()));
        executor.submit(() -> machine.processOrder("Latte", CupSize.MEDIUM, new UPIPayment()));
        executor.submit(() -> machine.processOrder("Cappuccino", CupSize.LARGE, new CashPayment()));

        executor.shutdown();
    }
}
