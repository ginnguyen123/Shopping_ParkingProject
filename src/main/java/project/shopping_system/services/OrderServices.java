package project.shopping_system.services;

import project.shopping_system.models.Order;
import project.shopping_system.models.Product;
import project.shopping_system.utils.DateTimeUtil;
import project.shopping_system.utils.IOFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderServices implements AbstractServices<Order>{
    private static String pathOrderFile = "src/main/java/project/shopping_system/data/orders.csv";
    private static String pathOrderRemovedFile = "src/main/java/project/shopping_system/data/data_removed/orders_removed.csv";
    private static OrderServices instance;
    public OrderServices(){}
    public static OrderServices getInstance(){
        if (instance == null){
            instance = new OrderServices();
        }
        return instance;
    }
    public static List<Order> findAll() {
        List<String> stringsOrder = IOFile.readFile(pathOrderFile);
        List<Order> orderList = new ArrayList<>();
        for (String strOrder : stringsOrder){
            orderList.add(Order.parseOrder(strOrder));
        }
        return orderList;
    }
    public static List<Order> findAllOrdersRemoved(){
        List<String> stringsOrderRemove = IOFile.readFile(pathOrderRemovedFile);
        List<Order> orderList = new ArrayList<>();
        for (String strOrder : stringsOrderRemove){
            orderList.add(Order.parseOrder(strOrder));
        }
        return orderList;
    }
    @Override
    public void add(Order newObject) {
        List<Order> orderList = findAll();
        orderList.add(newObject);
        IOFile.writeFile(orderList, pathOrderFile);
    }
    @Override
    public void edit(Order order) {
        List<Order> orderList = findAll();
        for (Order oldOrder : orderList){
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
                double grandTotal = order.getGrandTotal();
                if (grandTotal > 0)
                    oldOrder.setGrandTotal(grandTotal);
                oldOrder.setAtUpdated(atUpdated);
                break;
            }
        }
        IOFile.writeFile(orderList, pathOrderFile);
    }
    @Override
    public void remove(long orderID) {
        List<Order> orderList = findAll();
        List<Order> orderRemovedList = findAllOrdersRemoved();
        for (Order order : orderList){
            if (order.getOrderID() == orderID){
                orderRemovedList.add(order);
                orderList.remove(order);
                break;
            }
        }
        IOFile.writeFile(orderList, pathOrderFile);
        IOFile.writeFile(orderRemovedList, pathOrderRemovedFile);
    }
    @Override
    public Order findObject(long id) {
        List<Order> orderList = findAll();
        for (Order order : orderList){
            if (order.getOrderID() == id)
                return order;
        }
        return null;
    }
    public Order findRemovedObject(long id) {
        List<Order> orderList = findAllOrdersRemoved();
        for (Order order : orderList){
            if (order.getOrderID() == id)
                return order;
        }
        return null;
    }
    @Override
    public boolean isExistObject(long id) {
        List<Order> orderList = findAll();
        for (Order order : orderList){
            if (order.getOrderID() == id)
                return true;
        }
        return false;
    }
    public boolean isRemoveObject(long id) {
        List<Order> orderList = findAllOrdersRemoved();
        for (Order order : orderList){
            if (order.getOrderID() == id)
                return true;
        }
        return false;
    }
    @Override
    public List<Order> sortByNameAToZ() {
        List<Order> orderList = findAll();
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
        List<Order> orderList = findAll();
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

    public List<Order> sortByNameZToAOrderRemoved() {
        List<Order> orderList = findAllOrdersRemoved();
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
    public List<Order> sortByNameAToZOrderRemoved() {
        List<Order> orderList = findAllOrdersRemoved();
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
    public List<Order> sortByGrandTotalIncrease(){
        List<Order> orderList = findAll();
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order product2) {
                if (order1.getGrandTotal()>order1.getGrandTotal()){
                    return 1;
                } else if (order1.getGrandTotal()==order1.getGrandTotal()) {
                    return 0;
                }else
                    return -1;
            }
        });
        return orderList;
    }
    public List<Order> sortByGrandTotalDecrease(){
        List<Order> orderList = findAll();
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order product2) {
                if (order1.getGrandTotal()<order1.getGrandTotal()){
                    return 1;
                } else if (order1.getGrandTotal()==order1.getGrandTotal()) {
                    return 0;
                }else
                    return -1;
            }
        });
        return orderList;
    }
    public List<Order> sortByGrandTotalIncreaseOrderRemoved(){
        List<Order> orderList = findAllOrdersRemoved();
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order product2) {
                if (order1.getGrandTotal()>order1.getGrandTotal()){
                    return 1;
                } else if (order1.getGrandTotal()==order1.getGrandTotal()) {
                    return 0;
                }else
                    return -1;
            }
        });
        return orderList;
    }
    public List<Order> sortByGrandTotalDecreaseOrderRemoved() {
        List<Order> orderList = findAllOrdersRemoved();
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order product2) {
                if (order1.getGrandTotal() < order1.getGrandTotal()) {
                    return 1;
                } else if (order1.getGrandTotal() == order1.getGrandTotal()) {
                    return 0;
                } else
                    return -1;
            }
        });
        return orderList;
    }
    public double statisticalByDay(Instant day){
        List<Order> orderList = findAllOrdersRemoved();
        String strDay = DateTimeUtil.formatDayIntanceToString(day);
        double statisticalByDay = 0;
        for (Order order : orderList){
            if (strDay.equals(DateTimeUtil.formatDayIntanceToString(order.getAtUpdated()))){
                statisticalByDay += order.getGrandTotal();
            }
        }
        return statisticalByDay;
    }
    public double statisticalByMonth(Instant month){
        List<Order> orderList = findAllOrdersRemoved();
        String strMonth = DateTimeUtil.formatMonthIntanceToString(month);
        double statisticalByMonth = 0;
        for (Order order : orderList){
            if (strMonth.equals(DateTimeUtil.formatMonthIntanceToString(order.getAtUpdated())))
                statisticalByMonth += order.getGrandTotal();
        }
        return statisticalByMonth;
    }
    public double statisticalByYear(Instant year){
        List<Order> orderList = findAllOrdersRemoved();
        String strYear = DateTimeUtil.formatYearIntanceToString(year);
        double statisticalByYear = 0;
        for (Order order : orderList){
            if (strYear.equals(DateTimeUtil.formatYearIntanceToString(order.getAtUpdated())))
                statisticalByYear += order.getGrandTotal();
        }
        return statisticalByYear;
    }
    public List<Order> statisticalByDayList(Instant day){
        List<Order> orderList = findAllOrdersRemoved();
        List<Order> statisticalByDayList = new ArrayList<>();
        String strDay = DateTimeUtil.formatDayIntanceToString(day);
        for (Order order : orderList){
           if (strDay.equals(DateTimeUtil.formatDayIntanceToString(order.getAtUpdated()))){
               statisticalByDayList.add(order);
           }
        }
        return statisticalByDayList;
    }
    public List<Order> statisticalByMonthList(Instant month){
        List<Order> orderList = findAllOrdersRemoved();
        List<Order> statisticalByMonthList = new ArrayList<>();
        String strDay = DateTimeUtil.formatMonthIntanceToString(month);
        for (Order order : orderList){
            if (strDay.equals(DateTimeUtil.formatMonthIntanceToString(order.getAtUpdated())))
                statisticalByMonthList.add(order);
        }
        return statisticalByMonthList;
    }
    public List<Order> statisticalByYearList(Instant year){
        List<Order> orderList = findAllOrdersRemoved();
        List<Order> statisticalByYearList = new ArrayList<>();
        String strDay = DateTimeUtil.formatYearIntanceToString(year);
        for (Order order : orderList){
            if (strDay.equals(DateTimeUtil.formatYearIntanceToString(order.getAtUpdated())))
                statisticalByYearList.add(order);
        }
        return statisticalByYearList;
    }
}
