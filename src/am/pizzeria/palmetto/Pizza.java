package am.pizzeria.palmetto;

public class Pizza {

    private static final int MAXIMUM_NUMBER_OF_INGREDIENTS = 7;

    private String name;
    private String type;
    private String[] ingredients;
    private int ingredientsCount;

    public Pizza(String name, String type) {
        this.name = name;
        this.type = type;
        this.ingredients = new String[MAXIMUM_NUMBER_OF_INGREDIENTS];
        this.ingredientsCount = 0;
    }

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

        ingredient = normalizeIngredient(ingredient);

        if (ingredientsCount >= MAXIMUM_NUMBER_OF_INGREDIENTS) {
            System.out.println("Pizza is full.");
            return;
        }

        for (String existing : ingredients) {

            if (existing != null &&
                    existing.equalsIgnoreCase(ingredient)) {

                System.out.println(
                        "Ingredient " + ingredient +
                                " already added to pizza. Please check your order again."
                );

                return;
            }
        }

        ingredients[ingredientsCount] = ingredient;
        ingredientsCount++;
    }

    private String normalizeIngredient(String ingredient) {

        String value = ingredient.trim().toLowerCase();

        switch (value) {

            case "tomato paste":
                return "Tomato paste";

            case "cheese":
                return "Cheese";

            case "salami":
                return "Salami";

            case "bacon":
                return "Bacon";

            case "garlic":
                return "Garlic";

            case "corn":
                return "Corn";

            case "pepperoni":
                return "Pepperoni";

            case "pepper":
                return "Pepper";

            case "olives":
                return "Olives";

            default:
                return ingredient.trim();
        }
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
