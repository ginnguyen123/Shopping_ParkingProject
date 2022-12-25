package project.shopping_system.views.product;

import project.shopping_system.models.Product;
import project.shopping_system.services.ProductServices;
import project.shopping_system.utils.AppUtils;
import project.shopping_system.utils.DateTimeUtil;
import project.shopping_system.views.Options;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class ProductViews {
    Scanner scanner = new Scanner(System.in);
    private ProductServices productServices;
    public ProductViews(){
        productServices = ProductServices.getInstance();
    }
    public void showProductList(Options options,List<Product> productList){
        System.out.printf("|%-8s| %-16s| %-16s| %-10s| %-20s| %-20s|\n",
                "ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
        for (Product product : productList){
            System.out.printf("|%-8s| %-16s| %-16s| %-10s| %-20s| %-20s|\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
                    product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
                    DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
        }
        if (options != Options.ADD && options != Options.EDIT && options != Options.REMOVE){
            System.out.println("Nhấn phím bất kì để tiếp tục.");
            String anyPress = scanner.nextLine();
        }
    }
    private int quantitys(Options option){
        switch (option){
            case EDIT:
                System.out.print("Nhập số lượng sản phẩm mới: ");
                break;
            case ADD:
                System.out.print("Nhập số lượng sản phẩm: ");
                break;
        }
        int quantitys;
        do {
            quantitys = AppUtils.retryParseIntInput();
            if(quantitys < 0){
                System.out.println("Nhập số lượng lớn hơn hoặc bằng 0. Vui lòng nhập lại.");
            }
        }while (quantitys < 0);
        return quantitys;
    }
    private long prices(Options option){
        switch (option){
            case EDIT:
                System.out.print("Nhập giá sản phẩm mới: ");
                break;
            case ADD:
                System.out.print("Nhập giá sản phẩm: ");
                break;
        }
        long prices;
        do {
            prices = AppUtils.retryParseLongInput();
            if(prices < 0){
                System.out.println("Nhập số lượng lớn hơn hoặc bằng 0. Vui lòng nhập lại.");
            }
        }while (prices < 0);
        return prices;
    }
    private String titles(Options option){
        switch (option){
            case ADD:
                System.out.print("Nhập tên sản phẩm: ");
                break;
            case EDIT:
                System.out.print("Nhập tên sản phẩm mới: ");
                break;
        }
        String titles = scanner.nextLine();
        AppUtils.retryString(titles);
        return titles;
    }
    public void showProduct(Product product, Options options){
        System.out.printf("|%-8s| %-16s| %-16s| %-10s| %-20s| %-20s|\n","ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
        System.out.println("------------------");
        System.out.printf("|%-8s| %-16s| %-16s| %-10s| %-20s| %-20s|\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
                product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
                DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
        System.out.println("------------------");
        if (options != Options.ADD && options != Options.EDIT && options != Options.REMOVE) {
            System.out.print("Nhấn phím bất kì để tiếp tục.\n");
            String anyPress = scanner.nextLine(); // chờ nhấn phím
        }
    }
    public void sortProductMenu(){
        boolean isRetry = false;
        do {
            System.out.println(">Sắp xếp sản phẩm theo:");
            System.out.println("1.Sắp xếp theo tên sản phẩm.");
            System.out.println("2.Sắp xếp theo giá sản phẩm.");
            System.out.println("0.Quay lại.");
            System.out.print(">Chọn chức năng: ");
            int choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    sortByTitlesProductMenu();
                    break;
                case 2:
                    sortByPricesProductMenu();
                    break;
                case 0:
                    isRetry = false;
                    break;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                    isRetry = AppUtils.isRetry(Options.SORT);
                    break;
            }
        }while (isRetry);
    }
    private void sortByTitlesProductMenu(){
        int choice;
        boolean flag = false;
        boolean isContinus = true;
        boolean isRetry = false;
        do {
            do {
                do {
                    System.out.println("1.Sắp xếp từ A -> Z.");
                    System.out.println("2.Sắp xếp từ Z -> A.");
                    System.out.println("0.Quay lại.");
                    System.out.print(">Chọn chức năng: ");
                    choice = AppUtils.retryParseIntInput();
                    switch (choice){
                        case 1:
                            sortByNameAToZViews();
                            break;
                        case 2:
                            sortByNameZToAViews();
                            break;
                        case 0:
                            flag = false;
                            isContinus = false;
                            isRetry = false;
                            break;
                        default:
                            flag = true;
                            System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                            break;
                    }
                }while (flag);
            }while (isContinus);
            if (choice == 1 || choice == 2){
                isRetry = AppUtils.isRetry(Options.SORT);
            }
        }while (isRetry);
    }
    private void sortByPricesProductMenu(){
        int choice;
        boolean flag = false;
        boolean isContinus = true;
        boolean isRetry = false;
        do {
            do {
                do {
                    System.out.println("1.Sắp xếp theo giá tăng dần.");
                    System.out.println("2.Sắp xếp theo giá giảm dần.");
                    System.out.println("0.Quay lại.");
                    System.out.print(">Chọn chức năng: ");
                    choice = AppUtils.retryParseIntInput();
                    switch (choice){
                        case 1:
                            sortByPricesIncreaseViews();
                            break;
                        case 2:
                            sortByPricesDecreaseViews();
                            break;
                        case 0:
                            flag = false;
                            isContinus = false;
                            isRetry = false;
                            break;
                        default:
                            flag = true;
                            System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                            break;
                    }
                }while (flag);
            }while (isContinus);
            if (choice == 1 || choice == 2){
                isRetry = AppUtils.isRetry(Options.SORT);
            }
        }while (isRetry);
    }
    private void editMenu(){
        System.out.println(">Quản lý sản phẩm.");
        System.out.println("1.Sửa tên sản phẩm.");
        System.out.println("2.Sửa số lượng sản phẩm.");
        System.out.println("3.Sửa giá sản phẩm.");
        System.out.println("0.Quay lại.");
        System.out.print(">Chọn chức năng: ");
    }
    public void add(){
        System.out.println(">Thêm sản phẩm.");
        do {
            long productID = System.currentTimeMillis() % 100000000;
            String titles = titles(Options.ADD);
            double prices = prices(Options.ADD);
            int quantitys = quantitys(Options.ADD);
            Instant atCreated = Instant.now();
            Instant atUpdated = Instant.now();
            productServices.add(new Product(productID,titles,quantitys,prices,atCreated,atUpdated));
            System.out.println("Đã cập nhật sản phẩm thành công.");
        }while (AppUtils.isRetry(Options.ADD));
    }
//    public void restoreProductRemoved(){
//        System.out.println(">Khôi phục sản phẩm đã xóa.");
//        boolean isRetry = false;
//        showProductList(Options.ADD,ProductServices.());
//        do {
//            System.out.print("Nhập mã ID sản phẩm: ");
//            long productID = AppUtils.retryParseLongInput();
//            if (productServices.isExistProductRemoved(productID)){
//                Product productRemoved = productServices.findProductRemoved(productID);
//                showProduct(productRemoved,Options.ADD);
//                System.out.println("Bạn muốn khôi phục sản phẩm này?");
//                if (AppUtils.isAcceptMenu()){
//                    productServices.restoreProductRemoved(productID);
//                    System.out.println("Đã cập nhật thành công.");
//                    isRetry = AppUtils.isRetry(Options.ADD);
//                }else
//                    isRetry = false;
//            }else{
//                System.out.printf("Mã ID %d sản phẩm không tồn tại\n",productID);
//                isRetry = AppUtils.isRetry(Options.ADD);
//            }
//        }while (isRetry);
//    }
    public void edit() {
        boolean isRetry = false;
        do {
            System.out.println(">Sửa thông tin sản phẩm");
            showProductList(Options.EDIT,ProductServices.findAll());
            System.out.print("Nhập mã ID sản phẩm: ");
            long productID = AppUtils.retryParseLongInput();
            if (productServices.isExistObject(productID)){
                Product product = productServices.findObject(productID);
                showProduct(product,Options.EDIT);
                Product newProduct = new Product();
                newProduct.setProductID(productID);
                editMenu();
                boolean flag = false;
                int choice;
                do {
                    choice = AppUtils.retryParseIntInput();
                    switch (choice){
                        case 1:
                            String titles = titles(Options.EDIT);
                            newProduct.setTitles(titles);
                            break;
                        case 2:
                            int quantitys = quantitys(Options.EDIT);
                            newProduct.setQuantitys(quantitys);
                            break;
                        case 3:
                            double prices = prices(Options.EDIT);
                            newProduct.setPrices(prices);
                            break;
                        case 0:
                            isRetry = false;
                            flag = false;
                            break;
                        default:
                            flag = true;
                            System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                            System.out.print("Nhập lại: ");
                    }
                }while (flag);
                if (choice == 1 || choice == 2 || choice == 3){
                    System.out.println("Bạn có muốn cập nhật thông tin này?");
                    boolean accept = AppUtils.isAcceptMenu();
                    if (accept){
                        productServices.edit(newProduct);
                        System.out.println("Cập nhật thành công!");
                        showProduct(productServices.findObject(newProduct.getProductID()),Options.SHOW);
                    }
                }
            }else{
                System.out.println("Mã ID sản phẩm không tồn tại. Kiểm tra lại.");
                isRetry = AppUtils.isRetry(Options.EDIT);
            }

        }while (isRetry);
    }
    public void remove(){
        System.out.println(">Xóa sản phẩm.");
        boolean isRetry = false;
        do {
            System.out.print("Nhập mã ID sản phẩm: ");
            long productID = AppUtils.retryParseLongInput();
            if (productServices.isExistObject(productID)){
                Product product = productServices.findObject(productID);
                showProduct(product,Options.SHOW);
                System.out.println("Bạn muốn xóa sản phẩm này?");
                if (AppUtils.isAcceptMenu()){
                    productServices.remove(productID);
                    System.out.println("Đã cập nhật thành công.");
                }
            } else if (productServices.isExistProductRemoved(productID)) {
                Product product = productServices.findProductRemoved(productID);
                showProduct(product,Options.SHOW);
                if (AppUtils.isAcceptMenu()){
                    productServices.deleteProductRemoved(product);
                    System.out.println("Đã cập nhật thành công.");
                }
            } else{
                System.out.println("Mã ID sản phẩm không tồn tại. Kiểm tra lại.");
            }
            isRetry = AppUtils.isRetry(Options.REMOVE);
        }while (isRetry);
    }
    public void findProductMenu(){
        boolean isRetry = false;
        System.out.println(">Tìm kiếm sản phẩm.");
        do {
            System.out.println("1.Sản phẩm hiện đang tồn tại.");
            System.out.println("2.Sản phẩm đã xóa.");
            System.out.println("0.Quay lại.");
            System.out.print(">Chọn chức năng: ");
            int choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    findExistProduct();
                    break;
                case 2:
                    findRemovedProduct();
                    break;
                case 0:
                    isRetry = false;
                    break;
                default:
                    System.out.println("Nhập sai chức năng. Kiểm tra lại.");
                    isRetry = AppUtils.isRetry(Options.FIND);
                    break;
            }
            if (choice == 1 || choice == 2){
                isRetry = AppUtils.isRetry(Options.FIND);
            }
        }while (isRetry);
    }
    public void findExistProduct(){
        showProductList(Options.FIND,ProductServices.findAll());
        System.out.print("Nhập mã ID sản phẩm: ");
        long productID = AppUtils.retryParseLongInput();
        if (productServices.isExistObject(productID)) {
            showProduct(productServices.findObject(productID),Options.FIND);
        }else
            System.out.printf("Mã ID %d không tồn tại.\n",productID);
    }
    private void findRemovedProduct(){
        showProductList(Options.FIND,ProductServices.findAllProductsRemoved());
        System.out.print("Nhập mã ID sản phẩm: ");
        long productID = AppUtils.retryParseLongInput();
        if (productServices.isExistProductRemoved(productID)) {
            showProduct(productServices.findProductRemoved(productID),Options.FIND);
        }else
            System.out.printf("Mã ID %d không tồn tại.\n",productID);
    }
    private void sortByNameAToZViews(){
        System.out.println(">Hiển thị danh sách sản phẩm theo thứ tự A -> Z.");
        List<Product> productList = productServices.sortByNameAToZ();
        showProductList(Options.SORT,productList);
//        System.out.printf("%s %s %s %s %s %s\n","ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
//        System.out.println("------------------");
//        for (Product product : productList){
//            System.out.printf("%d %s %d %f %s %s\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
//                    product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
//                    DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
//            System.out.println("------------------");
//        }
//        System.out.println("Nhậpphím bất kì để tiếp tục.");
//        scanner.nextLine();
    }
    private void sortByNameZToAViews(){
        System.out.println(">Hiển thị danh sách sản phẩm theo thứ tự Z -> A.");
        List<Product> productList = productServices.sortByNameZToA();
        showProductList(Options.SORT,productList);
//        System.out.printf("%s %s %s %s %s %s\n","ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
//        System.out.println("------------------");
//        for (Product product : productList){
//            System.out.printf("%d %s %d %f %s %s\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
//                    product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
//                    DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
//            System.out.println("------------------");
//        }
//        System.out.println("Nhập 1 phím bất kì để tiếp tục.");
//        scanner.nextLine();
    }
    private void sortByPricesIncreaseViews(){
        System.out.println(">Hiển thị danh sách theo giá tăng dần.");
        List<Product> productList = productServices.sortByPricesIncrease();
        showProductList(Options.SORT,productList);
//        System.out.printf("%s %s %s %s %s %s\n","ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
//        System.out.println("------------------");
//        for (Product product : productList){
//            System.out.printf("%d %s %d %f %s %s\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
//                    product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
//                    DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
//            System.out.println("------------------");
//        }
//        System.out.println("Nhập 1 phím bất kì để tiếp tục.");
//        scanner.nextLine();
    }
    private void sortByPricesDecreaseViews(){
        System.out.println(">Hiển thị danh sách theo giá giảm dần.");
        List<Product> productList = productServices.sortByPricesDecrease();
        showProductList(Options.SORT,productList);
//        System.out.printf("%s %s %s %s %s %s\n","ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
//        System.out.println("------------------");
//        for (Product product : productList){
//            System.out.printf("%d %s %d %f %s %s\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
//                    product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
//                    DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
//            System.out.println("------------------");
//        }
//        System.out.println("Nhập 1 phím bất kì để tiếp tục.");
//        scanner.nextLine();
    }
}
