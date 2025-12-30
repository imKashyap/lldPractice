package coffeeVendingMachine;

class CoffeeMachine {
    private final InventoryManager inventory = InventoryManager.getInstance();
    private final Menu menu = new Menu();

    public synchronized void processOrder(String coffeeName, CupSize size, PaymentStrategy payment) {
        Coffee base = menu.getCoffeeByName(coffeeName);
        if (base == null) {
            System.out.println("Invalid coffee selection.");
            return;
        }
        Coffee sized = new SizedCoffeeDecorator(base, size);

        if (!inventory.isAvailable(sized.getRecipe())) {
            System.out.println("Insufficient ingredients.");
            return;
        }

        if (!payment.pay(sized.getPrice())) {
            System.out.println("Payment failed.");
            return;
        }

        inventory.deductIngredients(sized.getRecipe());
        System.out.println("Dispensing: " + sized.getName());
        inventory.checkLowInventory();
    }

    public void displayMenu() {
        menu.displayOptions();
    }
}