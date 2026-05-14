## When to use which Design Patterns?

**Factory Pattern** <br>
When you have multiple different object all same parent class.
Like Vehicle(Car, Bike, Truck). You can create a Vehicle Factory and based on your requirement you can create different bus, car or truck objects.

---

**Strategy Pattern** <br>
When for doing a particular job, the operations could be different.
Like in case of fee calculator, Fee calculation is different in Weekdays/Weekends. So the operation is same to calculate the fee but the strategy changes.

---

**Observer Pattern** <br>
When on one change,you need to bring change in a no. of different places.
Like when the parking space gets vacant, You need to update display board, send notification to users

---

**Command Pattern** <br>
When you want the invoker to have dynamic commands.
So, You can just let invoker have a list of commands, which it executes.The list of commands can be populated during runtime.

---

**Singleton Pattern** <br>
When you want to have just one object.

---

**Decorator Pattern** <br>
Decorator Pattern is used to have additional functionality over a concrete implementation.
Like in a CoffeeVendingMachine, You have concrete Espresso, Mocha etc. But you want to provide a functionality to allow user to add more cream, more coffee beans etc.

---

**Misc:**
- Always return a Copy of List when in getters, so that address is not passed and changes could not be done like:
    ```java
    public List<LogHandler> getHandlers() {
        return List.copyOf(handlers);
    }
    ```
