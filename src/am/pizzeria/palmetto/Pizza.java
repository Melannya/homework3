package am.pizzeria.palmetto;

public class Pizza {

    public static final Pizza REGULAR =
            new Pizza("Regular", PizzaType.REGULAR.name(), new String[7]);

    private String name;
    private String type;
    private String[] ingredients;
    private int ingredientsCount;

    public Pizza(String name, String type, String[] ingredients) {
        this.name = name;
        this.type = type;
        this.ingredients = ingredients;

        for (String ingredient : ingredients) {
            if (ingredient != null) {
                ingredientsCount++;
            }
        }
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

    public void addIngredient(String ingredient) {

        if (ingredient == null || ingredient.trim().isEmpty()) {
            return;
        }

        if (ingredientsCount >= ingredients.length) {
            System.out.println("Pizza is full.");
            return;
        }

        for (String existingIngredient : ingredients) {
            if (ingredient.equalsIgnoreCase(existingIngredient)) {
                System.out.println(
                        "Ingredient " + ingredient +
                                " already added to pizza, please check your order."
                );
                return;
            }
        }

        ingredients[ingredientsCount] = ingredient;
        ingredientsCount++;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", ingredients=" + String.join(", ", ingredients) +
                ", ingredientsCount=" + ingredientsCount +
                '}';
    }
}
