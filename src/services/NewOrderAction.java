package services;

import entities.Orders;

import java.util.LinkedList;

public class NewOrderAction {
    private final Orders firstDefaultOrder = new Orders("A647334", 100L, 2L, "BUY", "New");
    private final Orders secondDefaultOrder = new Orders("A647335", 100L, 4L, "BUY", "New");
    private final Orders thirdDefaultOrder = new Orders("A647336", 200L, 2L, "BUY", "New");
    private final Orders forthDefaultOrder = new Orders("A647347", 200L, 5L, "BUY", "New");
    private final Orders fifthDefaultOrder = new Orders("A647338", 300L, 3L, "BUY", "New");
    private final Orders sixthDefaultOrder = new Orders("A647339", 300L, 3L, "BUY", "New");
    private final Orders seventhDefaultOrder = new Orders("A647341", 500L, 2L, "SELL", "New");
    private final Orders eighthDefaultOrder = new Orders("A647342", 600L, 3L, "SELL", "New");
    private final Orders ninthDefaultOrder = new Orders("A647343", 700L, 4L, "SELL", "New");
    private final Orders tenthDefaultOrder = new Orders("A647344", 350L, 2L, "SELL", "New");


    public LinkedList<Orders> setDefaultOrders() {
        LinkedList<Orders> returnedOrderList = new LinkedList<>();
        returnedOrderList.add(firstDefaultOrder);
        returnedOrderList.add(secondDefaultOrder);
        returnedOrderList.add(thirdDefaultOrder);
        returnedOrderList.add(forthDefaultOrder);
        returnedOrderList.add(fifthDefaultOrder);
        returnedOrderList.add(sixthDefaultOrder);
        returnedOrderList.add(seventhDefaultOrder);
        returnedOrderList.add(eighthDefaultOrder);
        returnedOrderList.add(ninthDefaultOrder);
        returnedOrderList.add(tenthDefaultOrder);
        return returnedOrderList;
    }
}
