import entities.Orders;
import entities.OrdersList;
import route.FrondEndInitializer;
import services.NewOrderAction;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private List<Orders> orderList;
    private NewOrderAction newOrderAction;
    private OrdersList ordersListEntity;
    private final String topHeading = "\nWELCOME TO MARKET DISRUPTOR PLATFORM ";
    public Main() {
        newOrderAction = new NewOrderAction();
        orderList = new LinkedList<Orders>(newOrderAction.setDefaultOrders());
        ordersListEntity = new OrdersList();
        ordersListEntity.setOrderList(orderList);
        FrondEndInitializer initializePage =  new FrondEndInitializer();
        initializePage.displayInitialPage(topHeading);
    }

    public static void main(String[] args) {
        new Main();
    }
}