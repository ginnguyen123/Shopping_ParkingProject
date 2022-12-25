package project.shopping_system.models;

import java.time.Instant;

public class Order {
    private long orderID;
    private String customerFullName;
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerEmail;
    private long userID;
    private Instant atCreated;
    private Instant atUpdated;
    private double grandTotal;

    public Order() {
    }

    public Order(long userID,long orderID, String customerFullName,
                 String customerPhoneNumber, String customerAddress, String customerEmail,
                 double grandTotal,Instant atCreated, Instant atUpdated) {
        this.orderID = orderID;
        this.customerFullName = customerFullName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.grandTotal = grandTotal;
        this.atCreated = atCreated;
        this.atUpdated = atUpdated;
        this.userID = userID;
    }

    public static Order parseOrder(String raw){
        String[] fields = raw.split(",");
        long userID = Long.parseLong(fields[0]);
        long orderID = Long.parseLong(fields[1]);
        String customerFullName = fields[2];
        String customerPhoneNumber = fields[3];
        String customerAddress = fields[4];
        String customerEmail = fields[5];
        double grandTotal = Double.parseDouble(fields[6]);
        Instant atCreated = Instant.parse(fields[7]);
        Instant atUpdated = Instant.parse(fields[8]);
        return new Order(userID,orderID,customerFullName,customerPhoneNumber, customerAddress,customerEmail,
                grandTotal,atCreated, atUpdated);
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Instant getAtCreated() {
        return atCreated;
    }

    public void setAtCreated(Instant atCreated) {
        this.atCreated = atCreated;
    }

    public Instant getAtUpdated() {
        return atUpdated;
    }

    public void setAtUpdated(Instant atUpdated) {
        this.atUpdated = atUpdated;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    @Override
    public String toString() {
        //(long orderID, String customerFullName,
        //                 String customerPhoneNumber, String customerAddress, String customerEmail, double grandTotal)
        return String.format("%d,%d,%s,%s,%s,%s,%f,%s,%s\n",
                userID,orderID,customerFullName,customerPhoneNumber,customerAddress,customerEmail,grandTotal,atCreated,atUpdated);
    }
}
