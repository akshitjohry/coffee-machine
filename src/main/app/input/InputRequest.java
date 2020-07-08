package input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import models.Beverage;
import models.Ingredient;

/**
 * This is the input processor, it convert the input JSON to a valid initial configuration for the machine.
 * The static method is used to create an object.
 */
public class InputRequest {

    private ArrayList<Beverage>  allBeverages;
    private ArrayList<Ingredient> allIngredients;
    private int totalOutlets;

    public ArrayList<Beverage> getAllBeverages() {
        return allBeverages;
    }

    public ArrayList<Ingredient> getAllIngredients() {
        return allIngredients;
    }

    public int getTotalOutlets() {
        return totalOutlets;
    }

    public InputRequest(final ArrayList<Beverage> allBeverages, final ArrayList<Ingredient> allIngredients,
            final int totalOutlets) {
        this.allBeverages = allBeverages;
        this.allIngredients = allIngredients;
        this.totalOutlets = totalOutlets;
    }

    public static InputRequest parseJSON(String fileName) throws IOException {

        InputStream inputStream = new FileInputStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        JsonReader reader = new JsonReader(inputStreamReader);
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        JsonObject machine = jsonObject.getAsJsonObject(InputConstants.MACHINE);
        JsonObject outlets = machine.getAsJsonObject(InputConstants.OUTLETS);


        return new InputRequest( addBeverages(machine.getAsJsonObject(InputConstants.BEVERAGES)),
                getIngredientList(machine.getAsJsonObject(InputConstants.INGREDIENT_STORE)),
                outlets.get(InputConstants.NUMBER_OF_OUTLETS).getAsInt());

    }

    private static ArrayList<Ingredient> getIngredientList(JsonObject itemStore) {
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        itemStore.keySet().forEach(ingredientKey -> {
            ingredientList.add(new Ingredient(ingredientKey, itemStore.get(ingredientKey).getAsDouble()));
        });

        return  ingredientList;
    }

    private static ArrayList<Beverage> addBeverages(JsonObject beverages) {
        ArrayList<Beverage> beverageList = new ArrayList<>();
        beverages.keySet().forEach(beverageKey -> {
            beverageList.add(new Beverage(beverageKey, getIngredientList(beverages.getAsJsonObject(beverageKey))));
        });
        return beverageList;
    }

    @Override
    public String toString() {
        return "InputRequest{" +
                "\nallBeverages = " + allBeverages.toString() +
                ", \nallIngredients=" + allIngredients.toString() +
                ", \ntotalOutlets=" + totalOutlets +
                '}';
    }
}
