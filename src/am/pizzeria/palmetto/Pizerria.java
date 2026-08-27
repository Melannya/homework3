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

            System.out.print("Customer number: ");
            int customerNumber =
                    Integer.parseInt(reader.readLine());

            System.out.print("Customer name: ");
            String customerName =
                    reader.readLine();

            Customer customer =
                    new Customer(
                            customerNumber,
                            customerName
                    );

            Order order =
                    new Order(customer);

            System.out.print("Number of pizzas: ");
            int pizzaCount =
                    Integer.parseInt(reader.readLine());

            if (pizzaCount > 10) {
                System.out.println(
                        "Maximum number of pizzas is 10."
                );
                pizzaCount = 10;
            }

            for (int i = 0; i < pizzaCount; i++) {

                System.out.println();
                System.out.println(
                        "----- Pizza " + (i + 1) + " -----"
                );

                System.out.print("Pizza name: ");
                String pizzaName =
                        reader.readLine();

                System.out.print(
                        "Pizza type (REGULAR/CALZONE): "
                );

                String pizzaType =
                        reader.readLine();

                System.out.print("Quantity: ");
                int quantity =
                        Integer.parseInt(
                                reader.readLine()
                        );

                order.addPizza(
                        pizzaName,
                        pizzaType,
                        quantity
                );

                System.out.println(
                        "Enter ingredients one by one."
                );

                System.out.println(
                        "Type 'done' when finished."
                );

                while (true) {

                    System.out.print("Ingredient: ");

                    String ingredient =
                            reader.readLine();

                    if (ingredient.equalsIgnoreCase("done")) {
                        break;
                    }

                    order.addIngredient(
                            i,
                            ingredient
                    );
                }
            }

            System.out.println();
            System.out.println("===== ORDER =====");

            System.out.println(
                    order.getOrderDescription()
            );

            System.out.println("===== CHECK =====");

            order.printCheck();

            order.printCheckToFile(
                    "receipt.txt"
            );

            System.out.println(
                    "Receipt saved to receipt.txt"
            );

        } catch (IOException e) {

            System.out.println(
                    "Input/output error."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Please enter a valid number."
            );
        }
    }
}



