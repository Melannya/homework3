package am.pizzeria.palmetto;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

public class Order {

    private static int orderCounter = 10000;

    private int orderNumber;
    private int customerNumber;
    private Pizza[] pizzas;
    private int pizzaCount;
    private LocalTime orderTime;

    public Order(Customer customer) {

        orderNumber = ++orderCounter;
        customerNumber = customer.getCustomerNumber();

        pizzas = new Pizza[10];
        pizzaCount = 0;

        orderTime = LocalTime.now();
    }

    public void addPizza(String name, String type, int quantity) {

        if (pizzaCount == 10) {
            System.out.println("You cannot order more than 10 pizzas.");
            return;
        }

        if (name == null ||
                name.length() < 4 ||
                name.length() > 20 ||
                !isAllLatin(name)) {

            name = "customer_name_" + (pizzaCount + 1);
        }

        Pizza pizza =
                new Pizza(name, type, quantity);

        pizzas[pizzaCount] = pizza;
        pizzaCount++;
    }

    public void addIngredient(int pizzaIndex, String ingredient) {

        if (pizzaIndex < 0 || pizzaIndex >= pizzaCount) {
            System.out.println("Wrong pizza number.");
            return;
        }

        pizzas[pizzaIndex].addIngredient(ingredient);
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

    private double getPizzaPrice(Pizza pizza) {

        double price;

        if (pizza.getType().equalsIgnoreCase("CALZONE")) {
            price = 1.50;
        } else {
            price = 1.00;
        }

        for (String ingredient : pizza.getIngredients()) {

            if (ingredient != null) {
                price += getIngredientPrice(ingredient);
            }
        }

        return price;
    }

    public double getTotalAmount() {

        double total = 0;

        for (int i = 0; i < pizzaCount; i++) {

            total += getPizzaPrice(pizzas[i])
                    * pizzas[i].getQuantity();
        }

        return total;
    }

    public String getOrderDescription() {

        StringBuilder result =
                new StringBuilder();

        for (int i = 0; i < pizzaCount; i++) {

            Pizza pizza = pizzas[i];

            result.append("[")
                    .append(orderNumber)
                    .append(" : ")
                    .append(customerNumber)
                    .append(" : ")
                    .append(pizza.getName())
                    .append(" : ")
                    .append(pizza.getQuantity())
                    .append("]");

            result.append(System.lineSeparator());
        }

        return result.toString();
    }

    public void printCheck() {

        System.out.println("********************************");
        System.out.println("Order: " + orderNumber);
        System.out.println("Client: " + customerNumber);
        System.out.println("Order time: " + orderTime);

        for (int i = 0; i < pizzaCount; i++) {

            Pizza pizza = pizzas[i];

            System.out.println("Name: " + pizza.getName());
            System.out.println("--------------------------------");

            double basePrice =
                    pizza.getType().equalsIgnoreCase("CALZONE")
                            ? 1.50
                            : 1.00;

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

            System.out.printf(
                    "Amount: %.2f €%n",
                    getPizzaPrice(pizza)
            );

            System.out.println(
                    "Quantity: " + pizza.getQuantity()
            );

            System.out.println("--------------------------------");
        }

        System.out.printf(
                "Total amount: %.2f €%n",
                getTotalAmount()
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

            for (int i = 0; i < pizzaCount; i++) {

                Pizza pizza = pizzas[i];

                writer.println("Name: " + pizza.getName());
                writer.println("--------------------------------");

                double basePrice =
                        pizza.getType().equalsIgnoreCase("CALZONE")
                                ? 1.50
                                : 1.00;

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
                        getPizzaPrice(pizza)
                );

                writer.println(
                        "Quantity: " + pizza.getQuantity()
                );

                writer.println("--------------------------------");
            }

            writer.printf(
                    "Total amount: %.2f €%n",
                    getTotalAmount()
            );

            writer.println("********************************");

        } catch (IOException e) {

            System.out.println(
                    "Error while writing receipt."
            );
        }
    }
}

