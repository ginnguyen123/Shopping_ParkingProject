package project.shopping_system.models;

public class OrderItems {
    private long orderItemsID;
    private int quantitis;
    private long orderID;
    private long productID;
    private double prices;
    private double totals;

    public OrderItems() {
    }

    public OrderItems(long orderItemsID,  int quantitis,double prices,long orderID, long productID) {
        this.orderItemsID = orderItemsID;
        this.quantitis = quantitis;
        this.orderID = orderID;
        this.productID = productID;
        this.totals = (double) quantitis *prices;
        this.prices = prices;
    }
    public static OrderItems parseOrderItems(String raw){
        String[] fields = raw.split(",");
        long orderItemsID = Long.parseLong(fields[0]);
        int quantitis = Integer.parseInt(fields[1]);
        double prices = Double.parseDouble(fields[2]);
        long orderID = Long.parseLong(fields[3]);
        long productID = Long.parseLong(fields[4]);
        double totals = Double.parseDouble(fields[5]);
        OrderItems orderItems = new OrderItems(orderItemsID, quantitis,prices, orderID, productID);
        orderItems.setTotals(totals);
        return orderItems;
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
        return String.format("%d,%d,%f,%d,%d,%f\n",
                orderItemsID,quantitis,prices,
                orderID,productID,totals);
    }
}
