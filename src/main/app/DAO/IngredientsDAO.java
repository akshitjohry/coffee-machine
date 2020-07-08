package DAO;

import java.util.Optional;

import database.IngredientStoreDB;
import models.Ingredient;

/**
 * This is the data access layer for Ingredients type of objects.
 * It accesses the singleton IngredientStore and can be used
 *      * To add or refill ingredients
 *      * To return the current state for an ingredient
 *      * This also reduces the amount of ingredients when they are used in serving an order
 */
public class IngredientsDAO {

    public static void addIngredient(Ingredient ingredient) {
        IngredientStoreDB.getInstance().update(ingredient);
    }

    public static Optional<Ingredient> get(String ingredientKey) {
        return Optional.ofNullable(IngredientStoreDB.getInstance().get(ingredientKey));
    }

    public static void consumeIngredient(Ingredient ingredient) {
        //multiplying qty by -1 to consume or remove entry from DB
        ingredient.setIngredientQuantity(ingredient.getIngredientQuantity()*-1);
        addIngredient(ingredient);
    }

}
