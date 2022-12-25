package project.shopping_system.services;

import project.shopping_system.models.Product;
import project.shopping_system.utils.IOFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductServices implements AbstractServices<Product>{
    private static String pathProductFile = "src/main/java/project/shopping_system/data/products.csv";
    private static String pathProductRemovedFile ="src/main/java/project/shopping_system/data/data_removed/products_removed.csv";
    private static ProductServices instance;
    public ProductServices(){}
    public static ProductServices getInstance(){
        if (instance == null){
            instance = new ProductServices();
        }
        return instance;
    }
    public static List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        List<String> stringList = IOFile.readFile(pathProductFile);
        for (String stringProduct : stringList){
            productList.add(Product.parseProduct(stringProduct));
        }
        return productList;
    }
    public static List<Product> findAllProductsRemoved(){
        List<Product> productRemovedList = new ArrayList<>();
        List<String> stringList = IOFile.readFile(pathProductRemovedFile);
        for (String stringProduct : stringList){
            productRemovedList.add(Product.parseProduct(stringProduct));
        }
        return productRemovedList;
    }

    @Override
    public void add(Product newProduct) {
        List<Product> productList = findAll();
        productList.add(newProduct);
        IOFile.writeFile(productList,pathProductFile);
    }
    public void addProductRemoved(Product newProductRemoved) {
        List<Product> productList = findAllProductsRemoved();
        productList.add(newProductRemoved);
        IOFile.writeFile(productList,pathProductRemovedFile);
    }
    @Override
    public void edit(Product product) {
        List<Product> productList = findAll();
        for (Product oldProduct : productList){
            if (product.getProductID() == oldProduct.getProductID()){
                Instant atUpdated = Instant.now();
                //(long productID, String titles, int quantitys, double prices, Instant atCreated, Instant atUpdated)
                String titles = product.getTitles();
                if (titles != null && !titles.trim().isEmpty()){
                    oldProduct.setTitles(titles);
                }
                int quantitys = product.getQuantitys();
                if (quantitys > 0){
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
        IOFile.writeFile(productList,pathProductFile);
    }
    public void editProductRemoved(Product productRemoved) {
        List<Product> productList = findAllProductsRemoved();
        for (Product oldProduct : productList){
            if (productRemoved.getProductID() == oldProduct.getProductID()){
                Instant atUpdated = Instant.now();
                //(long productID, String titles, int quantitys, double prices, Instant atCreated, Instant atUpdated)
                String titles = productRemoved.getTitles();
                if (titles != null && !titles.trim().isEmpty()){
                    oldProduct.setTitles(titles);
                }
                int quantitys = productRemoved.getQuantitys();
                if (quantitys > 0){
                    oldProduct.setQuantitys(quantitys);
                }
                double prices = productRemoved.getPrices();
                if (prices != 0 && prices > 0){
                    oldProduct.setPrices(prices);
                }
                oldProduct.setAtUpdated(atUpdated);
                break;
            }
        }
        IOFile.writeFile(productList,pathProductRemovedFile);
    }
    @Override
    public void remove(long productID) {
        List<Product> productList = findAll();
        List<Product> productRemovedList = findAllProductsRemoved();
        for (Product oldProduct : productList){
            if (oldProduct.getProductID() == productID){
                productList.remove(oldProduct);
                productRemovedList.add(oldProduct);
                break;
            }
        }
        IOFile.writeFile(productRemovedList,pathProductRemovedFile);
        IOFile.writeFile(productList,pathProductFile);
    }
    public void deleteProductRemoved(Product product){
        List<Product> productList = findAllProductsRemoved();
        for (Product productRemoved : productList){
            if (productRemoved.getProductID() == product.getProductID()){
                productList.remove(product);
                break;
            }
        }
        IOFile.writeFile(productList,pathProductRemovedFile);
    }
    @Override
    public Product findObject(long productID)   {
        List<Product> productList = findAll();
        for (Product product : productList){
            if (product.getProductID() == productID){
                return product;
            }
        }
        return null;
    }
    @Override
    public boolean isExistObject(long productID) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (product.getProductID() == productID) {
                return true;
            }
        }
        return false;
    }
    public boolean isExistProductRemoved(long productRemovedID){
        List<Product> productList = findAllProductsRemoved();
        for (Product product : productList) {
            if (product.getProductID() == productRemovedID) {
                return true;
            }
        }
        return false;
    }
    public Product findProductRemoved(long productRemovedID){
        List<Product> productList = findAllProductsRemoved();
        for (Product product : productList){
            if (product.getProductID() == productRemovedID){
                return product;
            }
        }
        return null;
    }
    public void removeProductRemoved(long productRemovedID){
        List<Product> productRemovedList = findAllProductsRemoved();
        for (Product product : productRemovedList){
            if (productRemovedID == product.getProductID()){
                productRemovedList.remove(product);
                break;
            }
        }
        IOFile.writeFile(productRemovedList,pathProductRemovedFile);
    }
//    public void restoreProductRemoved(long productRemovedID){
//        List<Product> productList = f;
//        List<Product> productRemovedList = currentProductRemovedList;
//        for (Product productRemoved : productRemovedList){
//            if (productRemoved.getProductID() == productRemovedID){
//                productList.add(productRemoved);
//                productRemovedList.remove(productRemoved);
//                break;
//            }
//        }
//        IOFile.writeFile(productRemovedList,pathProductRemovedFile);
//        IOFile.writeFile(productList,pathProductFile);
//    }
    @Override
    public List<Product> sortByNameAToZ() {
        List<Product> productList = findAll();
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
        IOFile.writeFile(productList,pathProductFile);
        return productList;
    }
    @Override
    public List<Product> sortByNameZToA() {
        List<Product> productList = findAll();
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
        List<Product> productList = findAll();
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
        List<Product> productList = findAll();
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
