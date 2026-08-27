package am.pizzeria.palmetto;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

public class Order {

    private static final int MAXIMUM_NUMBER_OF_INGREDIENTS = 7;
    private static final int MAXIMUM_NUMBER_OF_PIZZAS = 10;

    private static int orderCounter = 10_001;

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


    public Order(Customer customer,
                 String type,
                 String name,
                 int quantity) {

        this(customer);

        addPizza(type, name, quantity);
    }


    public Order(Customer customer,
                 String[] ingredients,
                 String type,
                 String name,
                 int quantity) {

        this(customer);

        Pizza pizza = createPizza(type, name, quantity);

        if (ingredients != null) {
            for (String ingredient : ingredients) {
                if (ingredient != null) {
                    pizza.addIngredient(ingredient);
                }
            }
        }

        pizzas.add(new PizzaOrder(pizza, quantity));
    }

    private Pizza createPizza(String type, String name, int quantity) {

        if (name == null ||
                name.length() < 4 ||
                name.length() > 20 ||
                !isAllLatin(name)) {

            name = "customer_name_" + pizzas.size();
        }

        return new Pizza(
                name,
                normalizeType(type),
                new String[MAXIMUM_NUMBER_OF_INGREDIENTS]
        );
    }

    public void addPizza(String type, String name, int quantity) {

        if (pizzas.size() >= MAXIMUM_NUMBER_OF_PIZZAS) {
            System.out.println("Maximum number of pizzas is 10.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        Pizza pizza = createPizza(type, name, quantity);

        pizzas.add(new PizzaOrder(pizza, quantity));
    }

    public void addIngredient(String ingredient) {

        if (pizzas.isEmpty()) {
            System.out.println("There is no pizza in the order.");
            return;
        }

        PizzaOrder pizzaOrder =
                (PizzaOrder) pizzas.get(pizzas.size() - 1);

        pizzaOrder.getPizza().addIngredient(ingredient);
    }

    public void addIngredient(int pizzaIndex, String ingredient) {

        if (pizzaIndex < 0 || pizzaIndex >= pizzas.size()) {
            System.out.println("Invalid pizza index.");
            return;
        }

        PizzaOrder pizzaOrder =
                (PizzaOrder) pizzas.get(pizzaIndex);

        pizzaOrder.getPizza().addIngredient(ingredient);
    }

    public String getOrderDescription() {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < pizzas.size(); i++) {

            PizzaOrder pizzaOrder =
                    (PizzaOrder) pizzas.get(i);

            result.append("[")
                    .append(orderNumber)
                    .append(": ")
                    .append(customerNumber)
                    .append(": ")
                    .append(pizzaOrder.getPizza().getName())
                    .append(": ")
                    .append(pizzaOrder.getQuantity())
                    .append("]");

            if (i < pizzas.size() - 1) {
                result.append(System.lineSeparator());
            }
        }

        return result.toString();
    }

    public double calculateTotal() {

        double total = 0;

        for (int i = 0; i < pizzas.size(); i++) {

            PizzaOrder pizzaOrder =
                    (PizzaOrder) pizzas.get(i);

            Pizza pizza = pizzaOrder.getPizza();

            double price =
                    getPizzaTypePrice(pizza.getType());

            for (String ingredient : pizza.getIngredients()) {

                if (ingredient != null) {
                    price += getIngredientPrice(ingredient);
                }
            }

            total += price * pizzaOrder.getQuantity();
        }

        return total;
    }

    public void printCheck() {

        System.out.println("********************************");
        System.out.println("Order: " + orderNumber);
        System.out.println("Client: " + customerNumber);

        for (int i = 0; i < pizzas.size(); i++) {

            PizzaOrder pizzaOrder =
                    (PizzaOrder) pizzas.get(i);

            Pizza pizza = pizzaOrder.getPizza();

            System.out.println("Name: " + pizza.getName());
            System.out.println("--------------------------------");

            double pizzaPrice =
                    getPizzaTypePrice(pizza.getType());

            System.out.printf(
                    "Pizza Base (%s) %.2f €%n",
                    pizza.getType(),
                    pizzaPrice
            );

            for (String ingredient : pizza.getIngredients()) {

                if (ingredient != null) {

                    double price =
                            getIngredientPrice(ingredient);

                    System.out.printf(
                            "%s %.2f €%n",
                            ingredient,
                            price
                    );
                }
            }

            System.out.println("--------------------------------");

            double onePizzaPrice = pizzaPrice;

            for (String ingredient : pizza.getIngredients()) {

                if (ingredient != null) {
                    onePizzaPrice += getIngredientPrice(ingredient);
                }
            }

            System.out.printf(
                    "Amount: %.2f €%n",
                    onePizzaPrice
            );

            System.out.println(
                    "Quantity: " + pizzaOrder.getQuantity()
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
                     new PrintWriter(new FileWriter(fileName))) {

            writer.println("********************************");
            writer.println("Order: " + orderNumber);
            writer.println("Client: " + customerNumber);
            writer.println("Order time: " + orderTime);
            writer.println();

            for (int i = 0; i < pizzas.size(); i++) {

                PizzaOrder pizzaOrder =
                        (PizzaOrder) pizzas.get(i);

                Pizza pizza = pizzaOrder.getPizza();

                writer.println("Name: " + pizza.getName());
                writer.println("--------------------------------");

                double pizzaBasePrice =
                        getPizzaTypePrice(pizza.getType());

                writer.printf(
                        "Pizza Base (%s) %.2f €%n",
                        pizza.getType(),
                        pizzaBasePrice
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

                double pizzaPrice = pizzaBasePrice;

                for (String ingredient : pizza.getIngredients()) {

                    if (ingredient != null) {
                        pizzaPrice += getIngredientPrice(ingredient);
                    }
                }

                writer.printf(
                        "Amount: %.2f €%n",
                        pizzaPrice
                );

                writer.println(
                        "Quantity: " + pizzaOrder.getQuantity()
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

    private double getPizzaTypePrice(String type) {

        if (type.equalsIgnoreCase("CALZONE")) {
            return PizzaType.CALZONE.getPrice();
        }

        return PizzaType.REGULAR.getPrice();
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

    private String normalizeType(String type) {

        if (type != null &&
                type.equalsIgnoreCase("CALZONE")) {

            return PizzaType.CALZONE.name();
        }

        return PizzaType.REGULAR.name();
    }

    private boolean isAllLatin(String name) {

        for (int i = 0; i < name.length(); i++) {

            char c = name.charAt(i);

            if (!isLatin(c)) {
                return false;
            }
        }

        return true;
    }

    private boolean isLatin(char c) {

        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z');
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


    private static class PizzaOrder {

        private final Pizza pizza;
        private final int quantity;

        public PizzaOrder(Pizza pizza, int quantity) {
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
