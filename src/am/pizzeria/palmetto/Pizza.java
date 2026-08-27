package am.pizzeria.palmetto;

public class Pizza {

    private String name;
    private String type;
    private String[] ingredients;
    private int ingredientsCount;
    private int quantity;

    public Pizza(String name, String type, int quantity) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.ingredients = new String[7];
        this.ingredientsCount = 0;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public int getIngredientsCount() {
        return ingredientsCount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addIngredient(String ingredient) {

        if (ingredientsCount == 7) {
            System.out.println("Pizza is full.");
            return;
        }

        for (int i = 0; i < ingredientsCount; i++) {

            if (ingredients[i].equalsIgnoreCase(ingredient)) {
                System.out.println(
                        "Ingredient " + ingredient +
                                " already added, please check your order again."
                );
                return;
            }
        }

        ingredients[ingredientsCount] = ingredient;
        ingredientsCount++;
    }
}
