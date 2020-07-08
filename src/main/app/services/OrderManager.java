package services;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import models.Order;

/**
 * This is order manager. It allots the number of threads using ExecutorService. Each thread corresponds to an outlet
 * which is used to process an Order.
 * It receives the list of orders and serves them.
 */
public class OrderManager {
    private ExecutorService executorService;

    public OrderManager (int numberOfOutlets){
        this.executorService = Executors.newFixedThreadPool(numberOfOutlets);
    }

    public void serve(ArrayList<Order> orderList) {
        orderList.forEach(order -> executorService.submit(new OrderService(order)));
    }
}
