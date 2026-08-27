package am.pizzeria.palmetto;

public enum PizzaType {

    REGULAR(1.00),
    CALZONE(1.50);

    private final double price;

    PizzaType(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

