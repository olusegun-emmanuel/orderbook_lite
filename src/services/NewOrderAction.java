package services;

import entities.Orders;

import java.util.LinkedList;

public class NewOrderAction {
    private final Orders firstDefaultOrder = new Orders("A647334", 100L, 2L, "BUY", "New");
    private final Orders secondDefaultOrder = new Orders("A647335", 200L, 1L, "BUY", "New");
    private final Orders thirdDefaultOrder = new Orders("A647336", 300L, 3L, "BUY", "New");
    private final Orders forthDefaultOrder = new Orders("A647337", 500L, 2L, "SELL", "New");
    private final Orders fifthDefaultOrder = new Orders("A647338", 600L, 3L, "SELL", "New");
    private final Orders sixthDefaultOrder = new Orders("A647339", 700L, 4L, "SELL", "New");

    public LinkedList<Orders> setDefaultOrders() {
        LinkedList<Orders> returnedOrderList = new LinkedList<>();
        returnedOrderList.add(firstDefaultOrder);
        returnedOrderList.add(secondDefaultOrder);
        returnedOrderList.add(thirdDefaultOrder);
        returnedOrderList.add(forthDefaultOrder);
        returnedOrderList.add(fifthDefaultOrder);
        returnedOrderList.add(sixthDefaultOrder);
        return returnedOrderList;
    }
}
