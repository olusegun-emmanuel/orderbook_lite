package services;

import constants.Constants;
import entities.Orders;
import entities.OrdersList;
import java.util.LinkedList;
import java.util.List;

public class OrderEntityMatchingAction {
    private final Constants constants = new Constants();
    private Long requestQuantity;
    private List<Orders> filledList = new LinkedList<>();

    public void implementOrderMatching(List<Orders> fillAbleList, Long quantity) {
        requestQuantity = quantity;
        for(Orders fillAbleOrder : fillAbleList) {
            if(requestQuantity > fillAbleOrder.getQuantity()){
                fillAbleOrder.setStatus(constants.fullyExecutedStatus);
                filledList.add(fillAbleOrder);
                requestQuantity = requestQuantity - fillAbleOrder.getQuantity();
            } else {
                fillAbleOrder.setStatus(constants.partiallyExecutedStatus);
                Long partialFill = fillAbleOrder.getQuantity() - requestQuantity;
                fillAbleOrder.setQuantity(partialFill);
                filledList.add(fillAbleOrder);
                break;
            }
        } //end of for loop
        List<Orders> updatedExistingOrdersList = this.modifyOrderList(filledList);
        updatedExistingOrdersList.addAll(filledList);
        OrdersList.setOrderList(updatedExistingOrdersList);
    }


    private List<Orders> modifyOrderList(List<Orders> ordersList){
        List<Orders> defaultOrdersList = new LinkedList<>(OrdersList.getOrderList());
        for(Orders OrderList : ordersList){
            int indexCounter = getOrderIndex(defaultOrdersList, OrderList.getOrderId());
            defaultOrdersList.remove(indexCounter);
        }
        return defaultOrdersList;
    }

    private int getOrderIndex(List<Orders> ordersLinkedList, String orderId) {
        int  counter = -1;
        for(Orders order : ordersLinkedList){
            counter++;
            if (order.getOrderId().equals(orderId)) {
                break;
            }
        }
        return counter;
    }
}
