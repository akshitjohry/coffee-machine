package services;

import java.util.ArrayList;
import java.util.Optional;

import DAO.BeverageDAO;
import enums.ORDER_STATUS;
import models.*;

/**
 * This is Service class for Order. It is executed by a thread to serve the order. It talks to the IngredientService
 * and calls display Service for appropriate message.
 */
public class OrderService implements Runnable {

    private Order order;

    OrderService(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        String orderedBeverageKey = order.getBeverageKey();
        ORDER_STATUS currentStatus;
        Optional<Beverage> beverageOptional = BeverageDAO.getBeverage(orderedBeverageKey);

        if(!beverageOptional.isPresent()){
            currentStatus = ORDER_STATUS.INVALID;
        } else {
            Beverage beverage = beverageOptional.get();
            ArrayList<Ingredient> ingredientList = new ArrayList<>();

            //creating a deep-copy so that original instance from DB is not changed
            beverage.getIngredientList().forEach(ingredient -> ingredientList.add(ingredient.clone()));

            //check and hold the inventory
            currentStatus = IngredientService.checkAndHold(ingredientList);
        }
        order.setOrderStatus(currentStatus);
        DisplayService.display(order);

    }
}
