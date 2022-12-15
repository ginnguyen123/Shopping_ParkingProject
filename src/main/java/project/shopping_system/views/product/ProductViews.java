package project.shopping_system.views.product;

import project.shopping_system.models.Order;
import project.shopping_system.models.Product;
import project.shopping_system.services.OrderServices;
import project.shopping_system.services.ProductServices;
import project.shopping_system.utils.DateTimeUtil;
import project.shopping_system.utils.IOFile;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class ProductViews {
    Scanner scanner = new Scanner(System.in);
    private ProductServices productServices;
    public ProductViews(){
        productServices = ProductServices.getInstance();
    }
    public void showList(){
        List<Product> productList = ProductServices.getCurrentList();
        //(long productID, String titles, int quantitys, double prices, Instant atCreated, Instant atUpdated)
        System.out.printf("%s %s %s %s %s %s\n","ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
        System.out.println("------------------");
        for (Product product : productList){
            System.out.printf("%d %s %d %f %s %s\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
                    product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
                    DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
            System.out.println("------------------");
        }
    }
    public void showProduct(Product product){

    }

    public void add(){
        System.out.println(">Thêm sản phẩm.");
        List<Product> productList = ProductServices.getCurrentList();
        boolean choiceContinuesCheck = false;
        do {
            long idNewProduct = System.currentTimeMillis()%100000000;
            System.out.print("Nhập tên sản phẩm: ");
            String titles = scanner.nextLine();
            System.out.print("Nhập số lượng sản phẩm: ");
            int quantitys = Integer.parseInt(scanner.nextLine());
            System.out.print("Nhập giá sản phẩm: ");
            double prices = Double.parseDouble(scanner.nextLine());
            Instant atCreated = Instant.now();
            boolean flag = false;
            //show màn hình sản phẩm thêm ra -> hỏi Y/N
            do {
                System.out.println("Bạn muốn thêm sản phẩm này?");
                System.out.println("1.Đồng ý.");
                System.out.println("0.Quay lại.");
                System.out.print(">Chọn chức năng: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice){
                    case 1:
                        Instant atUpdated = Instant.now();
                        Product product = new Product(idNewProduct,titles,quantitys,prices,atCreated,atUpdated);
                        productServices.add(product);
                        System.out.println("Đã cập nhật sản phẩm thành công!");
                        break;
                    case 0:
                        //thoát funtione quay lại menu chính.
                        flag = false;
                        choiceContinuesCheck = false;
                        break;
                    default:
                        System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                        flag = true;
                        break;
                }
                if (choice == 1){
                    System.out.println("Bạn muốn tiếp tục thêm sản phẩm?");
                    System.out.println("1.Tiếp tục.");
                    System.out.println("0.Quay lại.");
                    System.out.print(">Chọn chức năng: ");
                    int choiceContinues = Integer.parseInt(scanner.nextLine());
                    switch (choiceContinues){
                        case 1:
                            choiceContinuesCheck = true;
                            break;
                        case 0:
                            choiceContinuesCheck = false;
                            break;
                        default:
                            System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                            choiceContinuesCheck =false;
                            break;
                    }
                }
            }while (flag);
        }while (choiceContinuesCheck);

    }
//    public void edit() {
//        List<Order> orderList = OrderServices.getCurrentList();
//        boolean flag = false;
//        boolean isRetry = false;
//        //boolean isChoiceContinus = false;
//        showOrderList();
//        System.out.println(">Sửa thông tin hóa đơn khách hàng.");
//        do {
//            System.out.print("Nhập mã ID hóa đơn: ");
//            long orderID = Long.parseLong(scanner.nextLine());
//            if (orderServices.isExistObject(orderID)) {
//                do {
//                    System.out.println(">Quản lý hóa đơn khách hàng.");
//                    showOrder(orderServices.findObject(orderID));
//                    System.out.println();
//                    System.out.println("1.Sửa họ và tên.");
//                    System.out.println("2.Sửa số điện thoại.");
//                    System.out.println("3.Sửa địa chỉ.");
//                    System.out.println("4.Sửa Email.");
//                    System.out.println("0.Quay lại.");
//                    System.out.print(">Chọn chức năng: ");
//                    Order newOrder = new Order();
//                    newOrder.setOrderID(orderID);
//                    int choice = Integer.parseInt(scanner.nextLine());
//                    switch (choice) {
//                        case 1:
//                            System.out.print("Nhập họ và tên: ");
//                            String customerFullName = scanner.nextLine();
//                            newOrder.setCustomerFullName(customerFullName);
//                            break;
//                        case 2:
//                            System.out.print("Nhập số điện thoại: ");
//                            String customerPhoneNumber = scanner.nextLine();
//                            newOrder.setCustomerPhoneNumber(customerPhoneNumber);
//                            break;
//                        case 3:
//                            System.out.print("Nhập địa chỉ: ");
//                            String customerAddress = scanner.nextLine();
//                            newOrder.setCustomerAddress(customerAddress);
//                            break;
//                        case 4:
//                            System.out.print("Nhập Email: ");
//                            String customerEmail = scanner.nextLine();
//                            newOrder.setCustomerEmail(customerEmail);
//                            break;
//                        case 0:
//                            //thoát chương trình, quay về menu trước đó
//                            System.exit(5);
//                            break;
//                        default:
//                            //trường hợp nhập sai lần 1
//                            System.out.println("Chọn sai chức năng. Kiểm tra lại.");
//                            System.out.println("Bạn muốn tiếp tục?");
//                            System.out.println("1.Tiếp tục.");
//                            System.out.println("0.Quay lại.");
//                            System.out.print(">Chọn chức năng: ");
//                            int choiceDefault = Integer.parseInt(scanner.nextLine());
//                            switch (choiceDefault) {
//                                case 1:
//                                    //quay về menu quản lý hóa đơn
//                                    flag = true;
//                                    break;
//                                case 0:
//                                    //quay về nhập ID
//                                    flag = false; // thoát vòng lặp menu
//                                    isRetry = true;
//                                    break;
//                                default:
//                                    //chọn sai chức năng lần 2 thì thoát, quay về menu trước đó
//                                    System.out.println("Chọn sai chức năng.");
//                                    System.out.println("Thoát chương trình.");
//                                    System.exit(3);
//                            }
//                            break;
//                    }
//                    if (choice == 1 || choice == 2 || choice == 3 || choice == 4) {
//                        boolean isChoiceAgree = false;
//                        flag = false;
//                        //showOrder(newOrder);
//                        do {
//                            System.out.printf("Bạn có muốn thay đổi thông tin hóa đơn %d này?\n", orderServices.findObject(orderID).getOrderID());
//                            System.out.println("1.Đồng ý.");
//                            System.out.println("0.Quay lại.");
//                            System.out.print(">Chọn chức năng: ");
//                            int choiceAgrees = Integer.parseInt(scanner.nextLine());
//                            switch (choiceAgrees){
//                                case 1:
//                                    orderServices.edit(newOrder);
//                                    System.out.println("Đã cập nhật thành công!");
//                                    break;
//                                case 0:
//                                    flag = true;
//                                    break;
//                                default:
//                                    System.out.println("Chọn sai chức năng. Kiếm tra lại.");
//                                    isChoiceAgree = true;
//                                    break;
//                            }
//                        } while (isChoiceAgree);
//                    }
//                }while (flag);
//            }
//            else {
//                System.out.println("Mã ID hóa đơn không tồn tại.");
//                System.out.println("Bạn muốn tiếp tục?");
//                System.out.println("1.Tiếp tục.");
//                System.out.println("0.Quay lại.");
//                System.out.print(">Chọn chức năng: ");
//                int choiceContinus = Integer.parseInt(scanner.nextLine());
//                switch (choiceContinus){
//                    case 1:
//                        isRetry = true;
//                        break;
//                    case 0:
//                        System.exit(2);
//                        break;
//                    default:
//                        System.out.println("Chọn sai chức năng. Kiểm tra lại.");
//                        System.out.println("Bạn muốn tiếp tục?");
//                        System.out.println("1.Tiếp tục.");
//                        System.out.println("0.Quay lại.");
//                        System.out.print(">Chọn chức năng: ");
//                        int choiceDefault = Integer.parseInt(scanner.nextLine());
//                        switch (choiceDefault){
//                            case 1:
//                                isRetry = true;
//                                break;
//                            case 0:
//                                //thoát quay lại menu cũ không thông báo
//                                isRetry = false;
//                                break;
//                            default:
//                                System.out.println("Chọn sai chức năng.");
//                                System.out.println("Thoát chương trình.");
//                                System.exit(4);
//                        }
//                        break;
//                }
//            }
//        }while (isRetry);
//    }
    public void remove(){
        System.out.println(">Xóa thông tin sản phẩm.");
        showList();
        boolean flag = false;
        do {
            System.out.print("Nhập ID sản phẩm: ");
            long productID = Long.parseLong(scanner.nextLine());
            if (productServices.isExistObject(productID)){
                Product product = productServices.findObject(productID);
                System.out.printf("%s %s %s %s %s %s\n","ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
                System.out.println("------------------");
                System.out.printf("%d %s %d %f %s %s\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
                        product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
                        DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
                System.out.println("------------------");
                System.out.printf("Bạn muốn xóa sản phẩm %s này?\n",product.getTitles());
                System.out.println("1.Đồng ý.");
                System.out.println("0.Quay lại.");
                System.out.print(">Chọn chức năng: ");
                int choice = Integer.parseInt(scanner.nextLine());
                //boolean checkChoice = false;
                switch (choice){
                    case 1:
                        productServices.remove(product);
                        System.out.println("Đã xóa sản phẩm thành công!");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Chọn sai chức năng!");
                        System.out.println("Thoát chương trình!");
                        break;
                }
            }else {
                System.out.printf("Sản phẩm có mã ID %d không tồn tại.",productID);
                System.out.println("Bạn muốn tiếp tục?");
                System.out.println("1.Đồng ý.");
                System.out.println("0.Quay lại.");
                System.out.print(">Chọn chức năng: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice){
                    case 1:
                        flag = true;
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Chọn sai chức năng!");
                        System.out.println("Thoát chương trình!");
                        System.exit(1);
                        break;
                }
            }
        }while (flag);
    }
    public void sortByNameAToZViews(){
        System.out.println(">Hiển thị danh sách sản phẩm theo thứ tự A -> Z.");
        List<Product> productList = productServices.sortByNameAToZ();
        System.out.printf("%s %s %s %s %s %s\n","ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
        System.out.println("------------------");
        for (Product product : productList){
            System.out.printf("%d %s %d %f %s %s\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
                    product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
                    DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
            System.out.println("------------------");
        }
    }
    public void sortByNameZToAViews(){
        System.out.println(">Hiển thị danh sách sản phẩm theo thứ tự Z -> A.");
        List<Product> productList = productServices.sortByNameZToA();
        System.out.printf("%s %s %s %s %s %s\n","ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
        System.out.println("------------------");
        for (Product product : productList){
            System.out.printf("%d %s %d %f %s %s\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
                    product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
                    DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
            System.out.println("------------------");
        }
    }
    public void sortByPricesIncreaseViews(){
        System.out.println(">Hiển thị danh sách theo giá tăng dần.");
        List<Product> productList = productServices.sortByPricesIncrease();
        System.out.printf("%s %s %s %s %s %s\n","ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
        System.out.println("------------------");
        for (Product product : productList){
            System.out.printf("%d %s %d %f %s %s\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
                    product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
                    DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
            System.out.println("------------------");
        }
    }
    public void sortByPricesDecreaseViews(){
        System.out.println(">Hiển thị danh sách theo giá giảm dần.");
        List<Product> productList = productServices.sortByPricesDecrease();
        System.out.printf("%s %s %s %s %s %s\n","ID", "Titles", "Quantitys", "Prices", "At created", "At updated");
        System.out.println("------------------");
        for (Product product : productList){
            System.out.printf("%d %s %d %f %s %s\n",product.getProductID(),product.getTitles(),product.getQuantitys(),
                    product.getPrices(),DateTimeUtil.formatIntanceToString(product.getAtCreated()),
                    DateTimeUtil.formatIntanceToString(product.getAtUpdated()));
            System.out.println("------------------");
        }
    }
}
