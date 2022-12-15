package project.shopping_system.services;

import project.shopping_system.models.Order;
import project.shopping_system.models.OrderItems;
import project.shopping_system.utils.IOFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderItemServices implements AbstractServices<OrderItems>{
    private static String pathFile = "src/main/java/project/shopping_system/data/orderItems.csv";
    private static OrderItemServices instance;
    private static List<OrderItems> currentList;
    public OrderItemServices(){}
    public static OrderItemServices getInstance(){
        if (instance == null){
            instance = new OrderItemServices();
        }
        return instance;
    }

    static List<OrderItems> findAll() {
        List<String> stringOrderItems = IOFile.readFile(pathFile);
        List<OrderItems> orderItemsList = new ArrayList<>();
        for (String strOrderItem : stringOrderItems){
            orderItemsList.add(OrderItems.parseOrderItems(strOrderItem));
        }
        return orderItemsList;
    }

    static {
        currentList = findAll();
    }

    public static List<OrderItems> getCurrentList() {
        return currentList;
    }

    public static void setCurrentList(List<OrderItems> currentList) {
        OrderItemServices.currentList = currentList;
    }

    @Override
    public void add(OrderItems newOrderItems) {
        List<OrderItems> orderItemsList = currentList;
        orderItemsList.add(newOrderItems);
        IOFile.writeFile(orderItemsList,pathFile);
    }

    @Override
    public void edit(OrderItems orderItem) {
        List<OrderItems> orderItemsList = currentList;
        for (OrderItems oldOrderItem : orderItemsList){
            if (oldOrderItem.getOrderID() == orderItem.getOrderID()){
                Instant atUpdated = Instant.now();
                //(long orderItemsID, String productName, int quantitis, double prices,
                // long orderID, long productID, double totals)
                String productName = orderItem.getProductName();
                if (productName == null && !productName.trim().isEmpty())
                    oldOrderItem.setProductName(productName);
                int quantitis = orderItem.getQuantitis();
                if (quantitis !=0 && quantitis>0)
                    oldOrderItem.setOrderItemsID(quantitis);
                double prices = orderItem.getPrices();
                if (prices != 0 && prices > 0)
                    oldOrderItem.setPrices(prices);
            }
        }
        IOFile.writeFile(orderItemsList,pathFile);
    }

    @Override
    public void remove(OrderItems orderItem) {
        List<OrderItems> orderItemsList = currentList;
        orderItemsList.remove(orderItem);
        IOFile.writeFile(orderItemsList,pathFile);
    }

    @Override
    public OrderItems findObject(long orderItemsID) {
        List<OrderItems> orderItemsList = currentList;
        for (OrderItems orderItems : orderItemsList){
            if (orderItems.getOrderItemsID() == orderItemsID)
                return orderItems;
        }
        return null;
    }

    @Override
    public boolean isExistObject(long orderItemsID) {
        List<OrderItems> orderItemsList = currentList;
        for (OrderItems orderItems : orderItemsList){
            if (orderItems.getOrderItemsID() == orderItemsID)
                return true;
        }
        return false;
    }
    @Override
    public List<OrderItems> sortByNameAToZ() {
        List<OrderItems> orderItemsList = currentList;
        Collections.sort(orderItemsList, new Comparator<OrderItems>() {
            @Override
            public int compare(OrderItems orderItems1, OrderItems orderItems2) {
                if (orderItems1.getProductName().compareTo(orderItems2.getProductName())>0){
                    return 1;
                } else if (orderItems1.getProductName().compareTo(orderItems2.getProductName())==0) {
                    return 0;
                }else
                    return -1;
            }
        });
        return orderItemsList;
    }

    @Override
    public List<OrderItems> sortByNameZToA() {
        List<OrderItems> orderItemsList = currentList;
        Collections.sort(orderItemsList, new Comparator<OrderItems>() {
            @Override
            public int compare(OrderItems orderItems1, OrderItems orderItems2) {
                if (orderItems1.getProductName().compareTo(orderItems2.getProductName())<0){
                    return 1;
                } else if (orderItems1.getProductName().compareTo(orderItems2.getProductName())==0) {
                    return 0;
                }else
                    return -1;
            }
        });
        return orderItemsList;
    }
}
