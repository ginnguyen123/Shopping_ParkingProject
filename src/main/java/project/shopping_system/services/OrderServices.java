package project.shopping_system.services;

import project.shopping_system.models.Account;
import project.shopping_system.models.Order;
import project.shopping_system.models.OrderItems;
import project.shopping_system.utils.IOFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderServices implements AbstractServices<Order>{
    private static String pathFile = "src/main/java/project/shopping_system/data/orders.csv";
    private static OrderServices instance;
    private static List<Order> currentList;
    public OrderServices(){}
    public static OrderServices getInstance(){
        if (instance == null){
            instance = new OrderServices();
        }
        return instance;
    }
    static List<Order> findAll() {
        List<String> stringsOrder = IOFile.readFile(pathFile);
        List<Order> orderList = new ArrayList<>();
        for (String strOrder : stringsOrder){
            orderList.add(Order.parseOrder(strOrder));
        }
        return orderList;
    }
    static {
        currentList = findAll();
    }

    public static List<Order> getCurrentList() {
        return currentList;
    }

    public static void setCurrentList(List<Order> currentList) {
        OrderServices.currentList = currentList;
    }

    @Override
    public void add(Order newObject) {
        List<Order> orderList = currentList;
        orderList.add(newObject);
        IOFile.writeFile(orderList,pathFile);
    }

    @Override
    public void edit(Order order) {
        List<Order> orderList = currentList;
        for (Order oldOrder : orderList){
            //(long orderID, String customerFullName,
            //                 String customerPhoneNumber, String customerAddress, String customerEmail,
            //                 double grandTotal,Instant atCreated, Instant atUpdated)
            if (oldOrder.getOrderID() == order.getOrderID()){
                Instant atUpdated = Instant.now();
                String customerFullName = order.getCustomerFullName();
                if (customerFullName!=null && !customerFullName.trim().isEmpty())
                    oldOrder.setCustomerFullName(customerFullName);
                String customerPhoneNumber = order.getCustomerPhoneNumber();
                if (customerPhoneNumber!=null && !customerPhoneNumber.trim().isEmpty())
                    oldOrder.setCustomerPhoneNumber(customerPhoneNumber);
                String customerAddress = order.getCustomerAddress();
                if (customerAddress!=null && !customerAddress.trim().isEmpty())
                    oldOrder.setCustomerAddress(customerAddress);
                String customerEmail = order.getCustomerEmail();
                if(customerEmail!=null && !customerEmail.trim().isEmpty())
                    oldOrder.setCustomerEmail(customerEmail);
                oldOrder.setAtUpdated(atUpdated);
                break;
            }
        }
        IOFile.writeFile(orderList,pathFile);
    }

    @Override
    public void remove(Order order) {
        List<Order> orderList = currentList;
        orderList.remove(order);
        IOFile.writeFile(orderList,pathFile);
    }

    @Override
    public Order findObject(long id) {
        List<Order> orderList = currentList;
        for (Order order : orderList){
            if (order.getOrderID() == id)
                return order;
        }
        return null;
    }

    @Override
    public boolean isExistObject(long id) {
        List<Order> orderList = currentList;
        for (Order order : orderList){
            if (order.getOrderID() == id)
                return true;
        }
        return false;
    }

    @Override
    public List<Order> sortByNameAToZ() {
        List<Order> orderList = currentList;
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {
                if (order1.getCustomerFullName().compareTo(order1.getCustomerFullName())>0){
                    return 1;
                } else if (order1.getCustomerFullName().compareTo(order1.getCustomerFullName())==0) {
                    return 0;
                }else
                    return -1;
            }
        });
        return orderList;
    }

    @Override
    public List<Order> sortByNameZToA() {
        List<Order> orderList = currentList;
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {
                if (order1.getCustomerFullName().compareTo(order1.getCustomerFullName())<0){
                    return 1;
                } else if (order1.getCustomerFullName().compareTo(order1.getCustomerFullName())==0) {
                    return 0;
                }else
                    return -1;
            }
        });
        return orderList;
    }
    public List<Order> sortByOrderIDIncrease(List<Order> orderList){
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {
                if (order1.getOrderID() > order2.getOrderID()){
                    return 1;
                } else if (order1.getOrderID()==order2.getOrderID()) {
                    return 0;
                }else
                    return -1;
            }
        });
        return orderList;
    }
}
