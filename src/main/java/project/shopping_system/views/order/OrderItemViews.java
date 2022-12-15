package project.shopping_system.views.order;

import project.shopping_system.models.Order;
import project.shopping_system.models.OrderItems;
import project.shopping_system.models.Product;
import project.shopping_system.services.OrderItemServices;
import project.shopping_system.services.OrderServices;
import project.shopping_system.services.ProductServices;
import project.shopping_system.views.product.ProductViews;

import java.util.List;
import java.util.Scanner;

public class OrderItemViews {
    private OrderServices orderServices;
    private ProductServices productServices;
    Scanner scanner = new Scanner(System.in);
    public OrderItemViews(){
        orderServices = OrderServices.getInstance();
        productServices = ProductServices.getInstance();
    }
    public OrderItems add(Order order){
        List<OrderItems> orderItemsList = OrderItemServices.getCurrentList(); // lây danh sách item đang có
        List<Product> productList = ProductServices.getCurrentList(); //lấy danh sách sản phẩm đang có
        ProductViews productViews = new ProductViews();
        OrderItems newOrderItem = new OrderItems();
        boolean isProductIDCheck = false;
        productViews.showList(); // hiển thị danh sách sản phẩm đang có
        System.out.println(">Thêm sản phẩm vào hóa đơn.");
        do {
            System.out.print("Nhập ID sản phẩm muốn thêm: ");
            long productID = Long.parseLong(scanner.nextLine());
            if (productServices.isExistObject(productID)){
                Product product = productServices.findObject(productID);
                boolean isQuantitisCheck = false;
                do {
                    System.out.print("Nhập số lượng sản phẩm: ");
                    //(long orderItemsID, String productName, int quantitis,double prices,long orderID, long productID, double totals)
                    int quantitis = Integer.parseInt(scanner.nextLine());
                    if (quantitis <= product.getQuantitys()){
                        long orderItemID = System.currentTimeMillis() % 100000000;
                        newOrderItem.setOrderItemsID(orderItemID);
                        newOrderItem.setProductName(product.getTitles());
                        newOrderItem.setQuantitis(quantitis);
                        newOrderItem.setPrices(product.getPrices());
                        newOrderItem.setOrderID(order.getOrderID());
                        newOrderItem.setProductID(productID);
                        double totals = (double) quantitis * product.getPrices();
                        newOrderItem.setTotals(totals);
                        orderItemsList.add(newOrderItem);
                        System.out.println("Thêm sản phẩm thành công");
                        OrderItemServices.setCurrentList(orderItemsList);
                        for (Product p : productList){
                            if (p.getProductID() == productID){
                                p.setQuantitys(product.getQuantitys() - quantitis);
                                break;
                            }
                        }
                        ProductServices.setCurrentList(productList); // cập nhật lại list product nhưng không ghi xuống
                    }
                    else {
                        System.out.println("Số lượng sản phẩm hiện không đủ.");
                        System.out.printf("Số lượng sản phẩm hiện có %d.\n",product.getQuantitys());
                        System.out.println("Bạn muốn tiếp tục?");
                        System.out.println("1.Tiếp tục.");
                        System.out.println("0.Quay lại.");
                        System.out.print(">Chọn chức năng: ");
                        int choiceQuantitis = Integer.parseInt(scanner.nextLine());
                        switch (choiceQuantitis){
                            case 1:
                                isQuantitisCheck = true;
                                break;
                            case 0:
                                System.exit(5);
                                break;
                            default:
                                System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                                System.out.println("Bạn muốn tiếp tục?");
                                System.out.println("1.Tiếp tục.");
                                System.out.println("0.Quay lại.");
                                System.out.print(">Chọn chức năng: ");
                                int choiceDefault = Integer.parseInt(scanner.nextLine());
                                switch (choiceDefault){
                                    case 1:
                                        isQuantitisCheck = true;
                                        break;
                                    case 0:
                                        break;
                                    default:
                                        System.out.println("Chọn sai chức năng.");
                                        System.out.println("Thoát chương trình.");
                                        System.exit(5);
                                        break;
                                }
                                break;
                        }
                    }
                }while (isQuantitisCheck);
            }else {
                System.out.printf("Mã ID %s không tồn tại. Kiểm tra lại.\n",productID);
                System.out.println("Bạn muốn tiếp tục?");
                System.out.println("1.Tiếp tục.");
                System.out.println("0.Quay lại.");
                System.out.print(">Chọn chức năng: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice){
                    case 1:
                        isProductIDCheck = true;
                        break;
                    case 0:
                        System.exit(5);
                        break;
                    default:
                        System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                        System.out.println("Bạn muốn tiếp tục?");
                        System.out.println("1.Tiếp tục.");
                        System.out.println("0.Quay lại.");
                        System.out.print(">Chọn chức năng: ");
                        int choiceDefault = Integer.parseInt(scanner.nextLine());
                        switch (choiceDefault){
                            case 1:
                                isProductIDCheck = true;
                                break;
                            case 0:
                                isProductIDCheck = false;
                                System.exit(5);
                                break;
                            default:
                                System.out.println("Chọn sai chức năng.");
                                System.out.println("Thoát chương trình.");
                                System.exit(5);
                        }
                }
            }
        }while (isProductIDCheck);

         return newOrderItem;
    }
}
