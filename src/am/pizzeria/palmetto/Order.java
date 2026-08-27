package am.pizzeria.palmetto;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

public class Order {

    private static final int MAXIMUM_NUMBER_OF_PIZZAS = 10;

    private static int orderCounter = 10001;

    private final int orderNumber;
    private final int customerNumber;
    private final DynamicArray pizzas;
    private final LocalTime orderTime;

    public Order(Customer customer) {

        this.orderNumber = orderCounter++;
        this.customerNumber = customer.getCustomerNumber();
        this.pizzas = new DynamicArray();
        this.orderTime = LocalTime.now();
    }

    public void addPizza(String type, String name, int quantity) {

        if (pizzas.size() >= MAXIMUM_NUMBER_OF_PIZZAS) {
            System.out.println("You cannot order more than 10 pizzas.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        String validName = validatePizzaName(name);

        String validType;

        if (type != null &&
                type.equalsIgnoreCase("CALZONE")) {

            validType = PizzaType.CALZONE.name();

        } else {

            validType = PizzaType.REGULAR.name();
        }

        Pizza pizza = new Pizza(validName, validType);

        pizzas.add(new PizzaItem(pizza, quantity));
    }

    private String validatePizzaName(String name) {

        if (name == null ||
                name.length() < 4 ||
                name.length() > 20 ||
                !isAllLatin(name)) {

            int index = pizzas.size() + 1;

            return "customer_name_" + index;
        }

        return name;
    }

    private boolean isAllLatin(String name) {

        for (int i = 0; i < name.length(); i++) {

            char c = name.charAt(i);

            if (!((c >= 'A' && c <= 'Z') ||
                    (c >= 'a' && c <= 'z'))) {

                return false;
            }
        }

        return true;
    }

    public void addIngredient(String ingredient) {

        if (pizzas.isEmpty()) {
            System.out.println("There are no pizzas in the order.");
            return;
        }

        addIngredient(
                pizzas.size() - 1,
                ingredient
        );
    }

    public void addIngredient(int pizzaIndex, String ingredient) {

        if (pizzaIndex < 0 ||
                pizzaIndex >= pizzas.size()) {

            System.out.println("Invalid pizza index.");
            return;
        }

        PizzaItem item =
                (PizzaItem) pizzas.get(pizzaIndex);

        item.getPizza().addIngredient(ingredient);
    }

    public String getOrderDescription() {

        StringBuilder result =
                new StringBuilder();

        for (int i = 0; i < pizzas.size(); i++) {

            PizzaItem item =
                    (PizzaItem) pizzas.get(i);

            result.append("[")
                    .append(orderNumber)
                    .append(" : ")
                    .append(customerNumber)
                    .append(": ")
                    .append(item.getPizza().getName())
                    .append(": ")
                    .append(item.getQuantity())
                    .append("]");

            if (i < pizzas.size() - 1) {
                result.append(System.lineSeparator());
            }
        }

        return result.toString();
    }

    public double calculatePizzaPrice(Pizza pizza) {

        double price;

        if (pizza.getType().equalsIgnoreCase("CALZONE")) {
            price = PizzaType.CALZONE.getPrice();
        } else {
            price = PizzaType.REGULAR.getPrice();
        }

        for (String ingredient : pizza.getIngredients()) {

            if (ingredient != null) {
                price += getIngredientPrice(ingredient);
            }
        }

        return price;
    }

    public double calculateTotal() {

        double total = 0;

        for (int i = 0; i < pizzas.size(); i++) {

            PizzaItem item =
                    (PizzaItem) pizzas.get(i);

            double pizzaPrice =
                    calculatePizzaPrice(item.getPizza());

            total += pizzaPrice * item.getQuantity();
        }

        return total;
    }

    private double getIngredientPrice(String ingredient) {

        switch (ingredient.toLowerCase()) {

            case "tomato paste":
                return 1.00;

            case "cheese":
                return 1.00;

            case "salami":
                return 1.50;

            case "bacon":
                return 1.20;

            case "garlic":
                return 0.30;

            case "corn":
                return 0.70;

            case "pepperoni":
                return 0.60;

            case "pepper":
                return 0.60;

            case "olives":
                return 0.50;

            default:
                return 0.00;
        }
    }

    public void printCheck() {

        System.out.println("********************************");
        System.out.println("Order: " + orderNumber);
        System.out.println("Client: " + customerNumber);
        System.out.println("Order time: " + orderTime);

        for (int i = 0; i < pizzas.size(); i++) {

            PizzaItem item =
                    (PizzaItem) pizzas.get(i);

            Pizza pizza = item.getPizza();

            System.out.println("Name: " + pizza.getName());
            System.out.println("--------------------------------");

            double basePrice =
                    pizza.getType().equalsIgnoreCase("CALZONE")
                            ? PizzaType.CALZONE.getPrice()
                            : PizzaType.REGULAR.getPrice();

            System.out.printf(
                    "Pizza Base (%s) %.2f €%n",
                    pizza.getType(),
                    basePrice
            );

            for (String ingredient : pizza.getIngredients()) {

                if (ingredient != null) {

                    System.out.printf(
                            "%s %.2f €%n",
                            ingredient,
                            getIngredientPrice(ingredient)
                    );
                }
            }

            System.out.println("--------------------------------");

            double pizzaPrice =
                    calculatePizzaPrice(pizza);

            System.out.printf(
                    "Amount: %.2f €%n",
                    pizzaPrice
            );

            System.out.println(
                    "Quantity: " + item.getQuantity()
            );

            System.out.println("--------------------------------");
        }

        System.out.printf(
                "Total amount: %.2f €%n",
                calculateTotal()
        );

        System.out.println("********************************");
    }

    public void printCheckToFile(String fileName) {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(fileName))) {

            writer.println("********************************");
            writer.println("Order: " + orderNumber);
            writer.println("Client: " + customerNumber);
            writer.println("Order time: " + orderTime);

            for (int i = 0; i < pizzas.size(); i++) {

                PizzaItem item =
                        (PizzaItem) pizzas.get(i);

                Pizza pizza = item.getPizza();

                writer.println("Name: " + pizza.getName());
                writer.println("--------------------------------");

                double basePrice =
                        pizza.getType().equalsIgnoreCase("CALZONE")
                                ? PizzaType.CALZONE.getPrice()
                                : PizzaType.REGULAR.getPrice();

                writer.printf(
                        "Pizza Base (%s) %.2f €%n",
                        pizza.getType(),
                        basePrice
                );

                for (String ingredient : pizza.getIngredients()) {

                    if (ingredient != null) {

                        writer.printf(
                                "%s %.2f €%n",
                                ingredient,
                                getIngredientPrice(ingredient)
                        );
                    }
                }

                writer.println("--------------------------------");

                writer.printf(
                        "Amount: %.2f €%n",
                        calculatePizzaPrice(pizza)
                );

                writer.println(
                        "Quantity: " + item.getQuantity()
                );

                writer.println("--------------------------------");
            }

            writer.printf(
                    "Total amount: %.2f €%n",
                    calculateTotal()
            );

            writer.println("********************************");

        } catch (IOException e) {

            System.out.println(
                    "Error while writing receipt: " +
                            e.getMessage()
            );
        }
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public DynamicArray getPizzas() {
        return pizzas;
    }

    private static class PizzaItem {

        private final Pizza pizza;
        private final int quantity;

        public PizzaItem(Pizza pizza, int quantity) {
            this.pizza = pizza;
            this.quantity = quantity;
        }

        public Pizza getPizza() {
            return pizza;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
