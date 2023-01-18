package route;

import constants.Constants;
import entities.Orders;
import entities.OrdersList;
import services.OrderEntityMatchingAction;

import java.util.*;

public class FrondEndInitializer {
    private  Scanner userInput;
    private final Constants constants = new Constants();
    private Orders orders = new Orders();
    public void displayInitialPage(String topHeading){
        System.out.println(topHeading);
        String instructions = "========================================================================================\n" +
                "INSTRUCTIONS: \n" +
                "Enter : ADD to create new Order \n" +
                "Enter : EDIT to modify new Order \n" +
                "Enter : REMOVE to delete an Order \n" +
                "=========================================================================================\n";
        System.out.println(instructions);

        try {
            userInput = new Scanner(System.in);
            String enteredString = userInput.nextLine();
            this.validateInstructionInput(enteredString);

        } catch (InputMismatchException inputMismatchException) {
            System.out.println("DISRUPTOR encountered invalid input, Please enter correct input.");
            userInput.nextLine();
        } catch(Exception e) {
            System.out.println("System Error Occurred, please retry" + e);
            userInput.nextLine();
        }
    }

    private void validateInstructionInput(String input) {
        String enteredInput = input.toUpperCase();
        switch (enteredInput){
            case "ADD" :
                this.displayAddNewOrder();
                break;
            case "EDIT" :
                this.displayEditNewOrder();
                break;
            case "REMOVE" :
                this.displayRemoveNewOrder();
                break;
            default:
                System.out.println("DISRUPTOR is unable to process your input, Please enter correct input.");
                displayInitialPage("");
                break;
        }
    }



    private void displayAddNewOrder(){
        System.out.println(constants.newOrderHeading);
        System.out.println(constants.generalInstructions);
        String requestPriceLabel = "Please Enter Price : ";
        System.out.println(requestPriceLabel);
        userInput = new Scanner(System.in);
        String enteredPrice = userInput.nextLine();
        boolean isLong = validateLongInput(enteredPrice, "Price");
        if (isLong) {
            orders.setPrice(Long.parseLong(enteredPrice));
            this.displayAddOrderQuantity();
        }
    }

    private void displayAddOrderQuantity() {
        String requestQuantityLabel = "Please Enter Quantity : ";
        System.out.println(requestQuantityLabel);
        userInput = new Scanner(System.in);
        String enteredQuantity = userInput.nextLine();
        boolean isLong = validateLongInput(enteredQuantity, "Quantity");
        if (isLong) {
            orders.setQuantity(Long.parseLong(enteredQuantity));
            this.displayAddOrderSide();
        }
    }


    private void displayAddOrderSide() {
        String requestSideLabel = "Please Enter Side : ";
        System.out.println(requestSideLabel);
        userInput = new Scanner(System.in);
        String enteredSide = userInput.nextLine();
        boolean isString = validateStringInput(enteredSide);
        if (isString) {
            List<Orders> existingOrdersList = new LinkedList<>(OrdersList.getOrderList());
            System.out.println( "\n=========================================================================================");
            System.out.println("Existing orders :");
            System.out.println(existingOrdersList);
            orders.setSide(enteredSide.toUpperCase());
            orders.setOrderId(generateUniqueOrderId(enteredSide));
            orders.setStatus(constants.initialStatus);
            System.out.println("Order to be added :");
            System.out.println(orders.toString());
            System.out.println("Order after addition :");
            if(orders.getSide().equals(constants.buySide)) {
                displayUpdatedOrder(existingOrdersList, orders);
            } else {
                //check for existing orders that matches new sell order
                List<Orders> fillAbleList =  this.getMatchingOrders(existingOrdersList, orders.getPrice());
                if (fillAbleList.isEmpty()) {
                    displayUpdatedOrder(existingOrdersList, orders);
                } else {
                    //implement matching on sell with buy orders
                    // reverse matching data to ensure the most reset data is filled first
                    Collections.reverse(fillAbleList);
                    OrderEntityMatchingAction orderEntityMatchingAction = new OrderEntityMatchingAction();
                    orderEntityMatchingAction.implementOrderMatching(fillAbleList, orders.getQuantity());
                    List<Orders> matchedOrdersList = new LinkedList<>(OrdersList.getOrderList());
                    orders.setStatus(constants.fullyExecutedStatus);
                    displayUpdatedOrder(matchedOrdersList, orders);
                }

            }

        }
    }


    private boolean validateLongInput(String input, String description) {
        boolean isLongValue = false;
        try {
            Long enteredValue  = Long.parseLong(input);
            if(enteredValue == 0L){
                System.out.println("DISRUPTOR is unable to process your price input.\"");
                if(description.equals("Price") ){
                    displayAddNewOrder();displayAddNewOrder();
                } else {
                    displayAddOrderQuantity();
                }
            } else {
                isLongValue = true;
            }
        } catch (NumberFormatException e) {
            System.out.println("DISRUPTOR is unable to process your price input.\"");
            if(description.equals("Price")){
                displayAddNewOrder();
            } else {
                displayAddOrderQuantity();
            }
        }
        return isLongValue;
    }

    private boolean validateStringInput(String input) {
        boolean isStringValue = false;
        String enteredInput = input.toUpperCase();
        try {
            if((enteredInput.equals(constants.buySide)) || (enteredInput.equals(constants.sellSide)) ){
                isStringValue = true;
            } else {
                System.out.println("DISRUPTOR is unable to process side input.");
                displayAddOrderSide();
            }
        } catch (NumberFormatException e) {
            System.out.println("DISRUPTOR is unable to process side input.\"");
            displayAddOrderSide();
        }
        return isStringValue;
    }




    private void displayEditNewOrder(){
        List<Orders> existingOrdersList = new LinkedList<>(OrdersList.getOrderList());
        System.out.println(constants.editOrderHeading);
        System.out.println(constants.generalInstructions);
        String requestOderIdLabel = "Please Enter OrderId : ";
        System.out.println(requestOderIdLabel);
        userInput = new Scanner(System.in);
        String enteredOrderId = userInput.nextLine();
        boolean isOrderValid = validateOrderId(existingOrdersList, enteredOrderId);
        if(isOrderValid) {
            int orderIndex = getOrderIndex(existingOrdersList, enteredOrderId);
            orders.setOrderId(existingOrdersList.get(orderIndex).getOrderId());
            orders.setPrice(existingOrdersList.get(orderIndex).getPrice());
            orders.setSide(existingOrdersList.get(orderIndex).getSide());
            orders.setStatus(existingOrdersList.get(orderIndex).getStatus());
            displayEditOrderQuantity();
        } else {
                System.out.println("DISRUPTOR encountered invalid order, process try again");
                displayInitialPage("");
        }
    }

    private void displayEditOrderQuantity() {
        List<Orders> existingOrdersList = new LinkedList<>(OrdersList.getOrderList());
        String requestQuantityLabel = "Please Enter New Quantity : ";
        System.out.println(requestQuantityLabel);
        userInput = new Scanner(System.in);
        String enteredQuantity = userInput.nextLine();
        boolean isLong = validateLongInput(enteredQuantity, "Quantity");
        if (isLong) {
            orders.setQuantity(Long.parseLong(enteredQuantity));
            System.out.println("Orders before update:");
            System.out.println(existingOrdersList);
            int orderIndex = getOrderIndex(existingOrdersList, orders.getOrderId());
            existingOrdersList.remove(orderIndex);
            existingOrdersList.add(orders);
            OrdersList.setOrderList(existingOrdersList);
            System.out.println("Orders after update:");
            System.out.println(OrdersList.getOrderList());
            displayInitialPage("");
        }
    }

    private void displayRemoveNewOrder(){
        List<Orders> existingOrdersList = new LinkedList<>(OrdersList.getOrderList());
        System.out.println(constants.removeOrderHeading);
        System.out.println(constants.generalInstructions);
        String requestOderIdLabel = "Please Enter OrderId : ";
        System.out.println(requestOderIdLabel);
        userInput = new Scanner(System.in);
        String enteredOrderId = userInput.nextLine();
        boolean isOrderValid = validateOrderId(existingOrdersList, enteredOrderId);
        if(isOrderValid) {
            System.out.println("Orders before remove operation:");
            System.out.println(existingOrdersList);
            int orderIndex = getOrderIndex(existingOrdersList, orders.getOrderId());
            existingOrdersList.remove(orderIndex);
            OrdersList.setOrderList(existingOrdersList);
            System.out.println("Orders after remove operation:");
            System.out.println(OrdersList.getOrderList());
            displayInitialPage("");
        } else {
            System.out.println("DISRUPTOR encountered invalid order, process try again");
            displayInitialPage("");
        }

    }

    private  void displayUpdatedOrder( List<Orders> existingOrdersList, Orders orders){
        existingOrdersList.add(orders);
        OrdersList.setOrderList(existingOrdersList);
        List<Orders> newOrdersList = new LinkedList<>(OrdersList.getOrderList());
        System.out.println(newOrdersList);
        displayInitialPage("");
    }


    public String generateUniqueOrderId(String side) {
        int min = 100000;
        int max = 999999;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        return side.equalsIgnoreCase(constants.buySide) ? constants.buyAppend + Integer.toString(random_int) : constants.sellAppend + Integer.toString(random_int);
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

    private boolean validateOrderId(List<Orders> ordersLinkedList, String orderId) {
        boolean isOrderValid = false;
        for(Orders order : ordersLinkedList){
            if (order.getOrderId().equals(orderId)) {
                isOrderValid = true;
            }
        }
        return isOrderValid;
    }

    private List<Orders> getMatchingOrders( List<Orders> persistedOrdersList, Long price){
        List<Orders> matchingOrders = new LinkedList<>();
        for (Orders value : persistedOrdersList) {
            if ((Objects.equals(value.getPrice(), price)) & (value.getSide().equals(constants.buySide)) &
                    (value.getStatus().equals(constants.initialStatus) |
                    (value.getStatus().equals(constants.partiallyExecutedStatus)))) {
                matchingOrders.add(value);
            }
        }
        return matchingOrders;
    }

}