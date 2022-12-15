package project.shopping_system.services;

import project.shopping_system.models.Product;
import project.shopping_system.utils.IOFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductServices implements AbstractServices<Product>{
    private static String pathFile = "src/main/java/project/shopping_system/data/products.csv";
    private static ProductServices instance;
    private static List<Product> currentList;
    public ProductServices(){}
    public static ProductServices getInstance(){
        if (instance == null){
            instance = new ProductServices();
        }
        return instance;
    }
    static List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        List<String> stringList = IOFile.readFile(pathFile);
        for (String stringProduct : stringList){
            productList.add(Product.parseProduct(stringProduct));
        }
        return productList;
    }
    static {
        currentList = findAll();
    }
    public static List<Product> getCurrentList() {
        return currentList;
    }

    public static void setCurrentList(List<Product> currentList) {
        ProductServices.currentList = currentList;
    }

    @Override
    public void add(Product newProduct) {
        List<Product> productList = currentList;
        productList.add(newProduct);
        IOFile.writeFile(productList,pathFile);
    }

    @Override
    public void edit(Product product) {
        List<Product> productList = currentList;
        for (Product oldProduct : productList){
            if (product.getProductID() == product.getProductID()){
                Instant atUpdated = Instant.now();
                //(long productID, String titles, int quantitys, double prices, Instant atCreated, Instant atUpdated)
                String titles = product.getTitles();
                if (titles != null && !titles.trim().isEmpty()){
                    oldProduct.setTitles(titles);
                }
                int quantitys = product.getQuantitys();
                if (quantitys != 0 && quantitys > 0){
                    oldProduct.setQuantitys(quantitys);
                }
                double prices = product.getPrices();
                if (prices != 0 && prices > 0){
                    oldProduct.setPrices(prices);
                }
                oldProduct.setAtUpdated(atUpdated);
                break;
            }
        }
        IOFile.writeFile(productList,pathFile);
    }

    @Override
    public void remove(Product product) {
        List<Product> productList = currentList;
        for (Product oldProduct : productList){
            if (oldProduct.getProductID() == product.getProductID()){
                productList.remove(oldProduct);
                break;
            }
        }
        IOFile.writeFile(productList,pathFile);
    }

    @Override
    public Product findObject(long productID) {
        List<Product> productList = currentList;
        for (Product product : productList){
            if (product.getProductID() == productID){
                return product;
            }
        }
        return null;
    }

    @Override
    public boolean isExistObject(long productID) {
        List<Product> productList = currentList;
        for (Product product : productList) {
            if (product.getProductID() == productID) {
                return true;
            }
        }
        return false;
    }
    @Override
    public List<Product> sortByNameAToZ() {
        List<Product> productList = currentList;
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                if (product1.getTitles().compareTo(product2.getTitles())>0){
                    return 1;
                } else if (product1.getTitles().compareTo(product2.getTitles())==0) {
                    return 0;
                }else
                return -1;
            }
        });
        IOFile.writeFile(productList,pathFile);
        return productList;
    }
    @Override
    public List<Product> sortByNameZToA() {
        List<Product> productList = currentList;
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                if (product1.getTitles().compareTo(product2.getTitles())<0){
                    return 1;
                } else if (product1.getTitles().compareTo(product2.getTitles())==0) {
                    return 0;
                }else
                    return -1;
            }
        });
        return productList;
    }
    public List<Product> sortByPricesIncrease(){
        List<Product> productList = currentList;
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                if (product1.getPrices()>product2.getPrices()){
                    return 1;
                } else if (product1.getPrices()==product2.getPrices()) {
                    return 0;
                }else
                    return -1;
            }
        });
        return productList;
    }
    public List<Product> sortByPricesDecrease(){
        List<Product> productList = currentList;
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                if (product1.getPrices()<product2.getPrices()){
                    return 1;
                } else if (product1.getPrices()==product2.getPrices()) {
                    return 0;
                }else
                    return -1;
            }
        });
        return productList;
    }
}
