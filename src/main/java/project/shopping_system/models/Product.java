package project.shopping_system.models;

import java.time.Instant;
import java.util.List;

public class Product {
    private long productID;
    private String titles;
    private int quantitys;
    private double prices;
    private Instant atCreated;
    private Instant atUpdated;

    public Product(){}

    public Product(long productID, String titles, int quantitys, double prices, Instant atCreated, Instant atUpdated) {
        this.productID = productID;
        this.titles = titles;
        this.quantitys = quantitys;
        this.prices = prices;
        this.atCreated = atCreated;
        this.atUpdated = atUpdated;
    }
    public static Product parseProduct(String raw){
        String[] fields = raw.split(",");
        long productID = Long.parseLong(fields[0]);
        String titles = fields[1];
        int quantitys = Integer.parseInt(fields[2]);
        double prices = Double.parseDouble(fields[3]);
        Instant atCreated = Instant.parse(fields[4]);
        Instant atUpdated = Instant.parse(fields[5]);
        return new Product(productID, titles, quantitys, prices, atCreated, atUpdated);
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public int getQuantitys() {
        return quantitys;
    }

    public void setQuantitys(int quantitys) {
        this.quantitys = quantitys;
    }

    public double getPrices() {
        return prices;
    }

    public void setPrices(double prices) {
        this.prices = prices;
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

    @Override
    public String toString() {
        //(long productID, String titles, int quantitys, double prices, Instant atCreated, Instant atUpdated)
        return String.format("%d,%s,%d,%f,%s,%s\n",
                productID,titles,quantitys,
                prices,atCreated,atUpdated );
    }
}
