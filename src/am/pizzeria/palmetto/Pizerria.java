package am.pizzeria.palmetto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Pizerria {

    public static void main(String[] args) {

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(System.in))) {

            System.out.println("===== PIZZERIA PALMETTO =====");

            System.out.print("Enter customer number: ");
            int customerNumber =
                    Integer.parseInt(reader.readLine());

            System.out.print("Enter customer name: ");
            String customerName = reader.readLine();

            Customer customer =
                    new Customer(customerNumber, customerName);

            System.out.print("Enter pizza name: ");
            String pizzaName = reader.readLine();

            System.out.print(
                    "Enter pizza type (REGULAR/CALZONE): "
            );
            String pizzaType = reader.readLine();

            System.out.print("Enter quantity: ");
            int quantity =
                    Integer.parseInt(reader.readLine());

            Order order =
                    new Order(
                            customer,
                            pizzaType,
                            pizzaName,
                            quantity
                    );

            System.out.println();
            System.out.println("Add ingredients.");
            System.out.println(
                    "Available: Tomato paste, Cheese, Salami, " +
                            "Bacon, Garlic, Corn, Pepperoni, Pepper, Olives"
            );
            System.out.println(
                    "Type 'done' when finished."
            );

            while (true) {

                System.out.print("Ingredient: ");

                String ingredient =
                        reader.readLine();

                if (ingredient == null ||
                        ingredient.equalsIgnoreCase("done")) {
                    break;
                }

                order.addIngredient(ingredient);
            }

            System.out.println();
            System.out.println("===== ORDER =====");

            System.out.println(
                    order.getOrderDescription()
            );

            System.out.println();
            System.out.println("===== CHECK =====");

            order.printCheck();

            order.printCheckToFile("receipt.txt");

            System.out.println();
            System.out.println(
                    "Receipt was saved to receipt.txt"
            );

        } catch (IOException e) {

            System.out.println(
                    "Input/output error: " +
                            e.getMessage()
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Please enter a valid number."
            );
        }
    }
}

