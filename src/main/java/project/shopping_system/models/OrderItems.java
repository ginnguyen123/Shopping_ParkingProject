package project.shopping_system.models;

public class OrderItems {
    private long orderItemsID;
    private String productName;
    private int quantitis;
    private long orderID;
    private long productID;
    private double prices;
    double totals;

    public OrderItems() {
    }

    public OrderItems(long orderItemsID, String productName, int quantitis,double prices,long orderID, long productID, double totals) {
        orderItemsID = orderItemsID;
        this.productName = productName;
        this.quantitis = quantitis;
        this.orderID = orderID;
        this.productID = productID;
        this.totals = totals;
        this.prices = prices;
    }
    public static OrderItems parseOrderItems(String raw){
        String[] fields = raw.split(",");
        long orderItemsID = Long.parseLong(fields[0]);
        String productName = fields[1];
        int quantitis = Integer.parseInt(fields[2]);
        double prices = Double.parseDouble(fields[3]);
        long orderID = Long.parseLong(fields[4]);
        long productID = Long.parseLong(fields[5]);
        double totals = Double.parseDouble(fields[6]);
        return new OrderItems(orderItemsID,productName, quantitis,prices, orderID, productID, totals);
    }

    public double getPrices() {
        return prices;
    }

    public void setPrices(double prices) {
        this.prices = prices;
    }

    public long getOrderItemsID() {
        return orderItemsID;
    }

    public void setOrderItemsID(long orderItemsID) {
        orderItemsID = orderItemsID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantitis() {
        return quantitis;
    }

    public void setQuantitis(int quantitis) {
        this.quantitis = quantitis;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public double getTotals() {
        return totals;
    }

    public void setTotals(double totals) {
        this.totals = totals;
    }
    public String toString(){
        //(long orderItemsID, String productName, int quantitis, double prices,long orderID, long productID, double totals)
        return String.format("%d,%s,%d,%f,%d,%d,%f",
                orderItemsID,productName,quantitis,prices,
                orderID,productID,totals);
    }
}
