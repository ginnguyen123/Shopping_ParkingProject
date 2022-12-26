package project.shopping_system.services;

import project.shopping_system.models.Order;
import project.shopping_system.models.OrderItems;
import project.shopping_system.models.Product;
import project.shopping_system.utils.IOFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderItemServices {//implements AbstractServices<OrderItems>
    private static String pathFile = "src/main/java/project/shopping_system/data/orderItems.csv";
    private static String pathFileRemove = "src/main/java/project/shopping_system/data/data_removed/orderItems_removed.csv";
    private static OrderItemServices instance;
    private static OrderServices orderServices = OrderServices.getInstance();
    public OrderItemServices(){ }
    public static OrderItemServices getInstance(){
        if (instance == null){
            instance = new OrderItemServices();
        }
        return instance;
    }
    public static List<OrderItems> findAll() {
        List<String> stringOrderItems = IOFile.readFile(pathFile);
        List<OrderItems> orderItemsList = new ArrayList<>();
        for (String strOrderItem : stringOrderItems){
            orderItemsList.add(OrderItems.parseOrderItems(strOrderItem));
        }
        return orderItemsList;
    }
    public static List<OrderItems> findAllOrderItemsRemove() {
        List<String> stringOrderItems = IOFile.readFile(pathFileRemove);
        List<OrderItems> orderItemsList = new ArrayList<>();
        for (String strOrderItem : stringOrderItems){
            orderItemsList.add(OrderItems.parseOrderItems(strOrderItem));
        }
        return orderItemsList;
    }

    public void add(OrderItems newOrderItems) {
        List<OrderItems> orderItemsList = findAll();
        orderItemsList.add(newOrderItems);
        IOFile.writeFile(orderItemsList,pathFile);
    }
    public void edit(OrderItems orderItems){
        List<OrderItems> orderItemsList = findAll();
        for (OrderItems oldOrderItems : orderItemsList){
            if (oldOrderItems.getOrderItemsID() == orderItems.getOrderItemsID()) {
                Instant atUpdated = Instant.now();
                int quantitis = orderItems.getQuantitis();
                if (quantitis>=0){
                    oldOrderItems.setQuantitis(quantitis);
                }
                double totals = orderItems.getTotals();
                if (totals >= 0)
                    oldOrderItems.setTotals(totals);
            }
        }
        IOFile.writeFile(orderItemsList, pathFile);
    }
    public void remove(long orderItemID) {
        List<OrderItems> orderItemsList = findAll();
        List<OrderItems> orderItemsRemoveList = findAllOrderItemsRemove();
        for (OrderItems orderItems : orderItemsList){
            orderItemsRemoveList.add(orderItems);
            orderItemsList.remove(orderItems);
            break;
        }
        IOFile.writeFile(orderItemsRemoveList,pathFileRemove);
        IOFile.writeFile(orderItemsList,pathFile);
    }
    public void removeOrderItemNotWrited(long orderItemID){
        List<OrderItems> orderItemsList = findAll();
        for (OrderItems orderItems : orderItemsList){
            orderItemsList.remove(orderItems);
            break;
        }
        IOFile.writeFile(orderItemsList,pathFile);
    }
    public void removeAllOrderItemsInOrder(long orderID){
        List<OrderItems> orderItemsList = findAll();
        List<OrderItems> orderItemsRemoveList = findAllOrderItemsRemove();
        for (OrderItems orderItems : orderItemsList){
            if (orderItems.getOrderID() == orderID){
                remove(orderItems.getOrderItemsID());
            }
        }
    }
    public OrderItems findObject(long orderItemsID) {
        List<OrderItems> orderItemsList = findAll();
        for (OrderItems orderItems : orderItemsList){
            if (orderItems.getOrderItemsID() == orderItemsID)
                return orderItems;
        }
        return null;
    }
    public boolean isExistOrderItemsInOrder(long orderItemsID) {
        List<OrderItems> orderItemsList = findAll();
        for (OrderItems orderItems : orderItemsList){
            if (orderItems.getOrderItemsID() == orderItemsID)
                return true;
        }
        return false;
    }
    public OrderItems findOrderItems(long orderID, long productID){
        for (OrderItems orderItems : findAll()){
            if (orderItems.getOrderID() == orderID && orderItems.getProductID() == productID)
                return orderItems;
        }
        return null;
    }

    public boolean isExistOrderItems(long orderItemID){
        for (OrderItems orderItems : findAll()){
            if (orderItems.getOrderItemsID() == orderItemID)
                return true;
        }
        return false;
    }
    public boolean isExistOrderItems(long orderID, long productID){
        for (OrderItems orderItems : findAll()){
            if (orderItems.getOrderID() == orderID && orderItems.getProductID() == productID)
                return true;
        }
        return false;
    }
}
