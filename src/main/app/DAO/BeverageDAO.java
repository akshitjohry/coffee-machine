package DAO;

import java.util.Optional;

import database.BeverageDB;
import models.Beverage;

/**
 * This is the data access layer for Beverage type of objects.
 * It accesses the singleton BeverageDB and can be used
 *      * To add new Beverage
 *      * To getBeverageDetails
 */
public class BeverageDAO {
    public static void addBeverage(Beverage beverage) {
        BeverageDB.getInstance().add(beverage);
    }
    public static Optional<Beverage> getBeverage(String beverageKey) {
        return Optional.ofNullable(BeverageDB.getInstance().get(beverageKey));
    }
}
