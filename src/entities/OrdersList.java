package entities;

import java.util.List;

public class OrdersList {
    private static List<Orders> orderList;

    public static List<Orders> getOrderList() {
        return orderList;
    }

    public static void setOrderList(List<Orders> orderList) {
        OrdersList.orderList = orderList;
    }

    @Override
    public String toString() {
        return "OrdersList{" +
                "orderList=" + orderList +
                '}';
    }
}
