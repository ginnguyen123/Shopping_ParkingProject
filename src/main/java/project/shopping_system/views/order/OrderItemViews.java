package project.shopping_system.views.order;

import project.shopping_system.models.Order;
import project.shopping_system.models.OrderItems;
import project.shopping_system.models.Product;
import project.shopping_system.services.OrderItemServices;
import project.shopping_system.services.OrderServices;
import project.shopping_system.services.ProductServices;
import project.shopping_system.utils.AppUtils;
import project.shopping_system.views.Options;
import project.shopping_system.views.product.ProductViews;

import java.sql.Struct;
import java.util.List;
import java.util.Scanner;

public class OrderItemViews {
    private OrderServices orderServices;
    private ProductServices productServices;
    private OrderItemServices orderItemServices;
    private ProductViews productViews = new ProductViews();

    Scanner scanner = new Scanner(System.in);
    public OrderItemViews(){
        orderServices = OrderServices.getInstance();
        productServices = ProductServices.getInstance();
        orderItemServices = OrderItemServices.getInstance();
    }
    public boolean showOrderItemExistList(long existOrderID){
        List<OrderItems> orderItemsList = OrderItemServices.findAll();
        int count = 0;
        //(long orderItemsID,  int quantitis,double prices,long orderID, long productID)
        System.out.printf("%s %s %s %s %s\n","ID","Titles","Quantitis","Prices","Totals");
        for (OrderItems orderItems : orderItemsList){
            if (orderItems.getOrderID() == existOrderID){
                ++count;
                Product product = productServices.findObject(orderItems.getProductID());
                System.out.printf("%s %s %s %s %s\n",orderItems.getOrderItemsID(),product.getTitles(),orderItems.getQuantitis(),
                        orderItems.getPrices(),orderItems.getTotals());
            }
        }
        if (count == 0){
//            boolean isContinus = true;
            System.out.println(">Danh sách hóa đơn trống!");
//            System.out.println("1.Thêm sản phẩm\t\t\t\t0.Quay lại.");
//            System.out.print(">Chọn chức năng: ");
//            do {
//                int choice1 = AppUtils.retryParseIntInput();
//                switch (choice1){
//                    case 1:
//                        add(existOrderID);
//                        return true;
//                    case 0:
//                        return false;
//                    default:
//                        System.out.println("Chọn sai chức năng. Kiểm tra lại.");
//                        System.out.print(">Nhập lại: ");
//                        break;
//                }
//            }while (isContinus);
            return false;
        } else
            return true;
    }
    public boolean showOrderItemRemovedList(long removedOrderID){
        List<OrderItems> orderItemsList = OrderItemServices.findAllOrderItemsRemove();
        int count = 0;
        //(long orderItemsID,  int quantitis,double prices,long orderID, long productID)
        //System.out.printf("%s %s %s %s %s\n","ID","Titles","Quantitis","Prices","Totals");
        for (OrderItems orderItems : orderItemsList){
            if (orderItems.getOrderID() == removedOrderID){
                ++count;
                if (count!=0){
                    Product product = productServices.findProductRemoved(orderItems.getProductID());
                    System.out.printf("%s %s %s %s %s\n",orderItems.getOrderItemsID(),product.getTitles(),orderItems.getQuantitis(),
                            orderItems.getPrices(),orderItems.getTotals());
                }
            }
        }
        if (count == 0){
            System.out.println(">Danh sách hóa đơn trống");
            System.out.println(">Nhập phím bất kì để tiếp tục.");
            String anyPush = scanner.nextLine();
            return false;
        }
        else{
            return true;
        }
    }
    public void add(long orderID) {
        boolean isRetry;
        double totals = 0;
        Order order = orderServices.findObject(orderID);
        productViews.showProductList(Options.ADD, ProductServices.findAll());
        //
            System.out.print("Nhập mã ID sản phẩm: ");
            long productID = AppUtils.retryParseLongInput();
            if (productServices.isExistObject(productID)) {
                Product product = productServices.findObject(productID);
                int quantitis = quantitisOrderItemInput(productID); // nhập đúng số lượng =>set lại số lượng product trong list nhưng k ghi xuống
                if (quantitis == -1){
                    //System.out.println("Số lượng đã hết! Nhập lại mã sản phẩm khác.");
                    isRetry = true;
                }else {
                    // nếu sản phầm còn hàng
                    if (orderItemServices.isExistOrderItems(orderID, productID)) {
                        //System.out.println("orderItem đã tồn tại trên order trước đó.");
                        OrderItems orderItems = orderItemServices.findOrderItems(orderID, productID); // orderItems đã có trước đó trên order
                        orderItems.setQuantitis(orderItems.getQuantitis() + quantitis);
                        totals = (double) quantitis * product.getPrices();
                        orderItems.setTotals(totals);
                        for (OrderItems orderItems1 : OrderItemServices.findAll()) {
                            if (orderItems1.getOrderItemsID() == orderItems.getOrderItemsID()) {
                                orderItemServices.add(orderItems);
                                orderItemServices.remove(orderItems1.getOrderItemsID());
                                break;
                            }
                        }
//                        OrderItemServices.setCurrentOrderItemsList(orderItemsList);
                    } else {
                        //System.out.println("orderItem không tồn tại trước đó. orderItem được tạo mới!");
                        long orderItemID = System.currentTimeMillis() % 100000000;
                        Product product1 = productServices.findObject(productID);
                        double prices = product1.getPrices();
                        orderItemServices.add(new OrderItems(orderItemID, quantitis, prices, orderID, productID));
                        totals = (double) quantitis * prices;
                    }
                    System.out.println("Đã cập nhật orderItem thành công.");
//                    orderViews.showOrder(orderServices.findObject(orderID), Options.ADD);
//                    orderViews.showOderDetails(orderID,Options.ADD);
                }
                order.setGrandTotal(order.getGrandTotal() + totals);
                orderServices.edit(order);

            } else {
                System.out.println("Mã ID sản phẩm không tồn tại. Kiểm tra lại.");
            }
    }
    private int quantitisOrderItemInput(long productID){
        Product product = productServices.findObject(productID);
        System.out.print("Nhập số lượng sản phẩm: ");
        if (product.getQuantitys()>0){
                do {
                int quantitis = AppUtils.retryParseIntInput();
                if (quantitis<0){
                    System.out.println("Số lượng sản phẩm cần lớn hơn 0.");
                    System.out.print("Nhập lại: ");
                } else if (quantitis>product.getQuantitys()) {
                    System.out.println("Số lượng sản phẩm nhập vào lớn hơn số lượng sản phẩm đang có.");
                    System.out.printf("Số lượng sản phẩm đang có: %d\n",product.getQuantitys());
                    System.out.print("Nhập lại: ");
                }else {
                    List<Product> productList = ProductServices.findAll();
                    for (Product product1 : productList){
                        if (product1.getProductID() == productID){
                            product1.setQuantitys(product1.getQuantitys() - quantitis);
                            productServices.edit(product1);
                            product1.setQuantitys(quantitis);
                            productServices.addProductRemoved(product1);
                            break;
                        }
                    }
                    return quantitis;
                }
            }while (true);
        }else {
            System.out.println("\nSản phẩm đã hết!");
            System.out.println("Xin chọn lại sản phẩm khác.");
            return -1;
        }
    }
    public void edit(long orderID){
        if (showOrderItemExistList(orderID)){
            System.out.print("Nhập mã ID mục sản phẩm cần thay đổi: ");
            int choice = -1;
            boolean isRetry = false;
            do {
                long orderItemID = AppUtils.retryParseLongInput();
                if (orderItemServices.isExistOrderItems(orderItemID)){
                    System.out.println(">Quản lý hóa đơn.");
                    System.out.println("1.Thay đổi số lượng");
                    System.out.println("2.Xóa mục sản phẩm.");
                    System.out.println("0.Quay lại.");
                    System.out.print(">Chọn chức năng: ");
                    choice = AppUtils.retryParseIntInput();
                    switch (choice){
                        case 1:
                            editQuantitis(orderItemID);
                            break;
                        case 2:
                            editByRemoveOrderItem(orderItemID);
                            break;
                        case 0:
                            isRetry = false;
                            break;
                        default:
                            System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                            System.out.print(">Nhập lại: ");
                            isRetry = true;
                            break;
                    }
                }else{
                    System.out.println("Mã ID mục sản phẩm không tồn tại. Kiểm tra lại.");
                    System.out.print("Nhập lại: ");
                    isRetry = true;
                }
            }while (isRetry);
        }
    }

    private void editQuantitis(long orderItemID){
        System.out.print("Nhập số lượng muốn thay đổi: ");
        int quantitis = AppUtils.retryParseIntInput(); // số lượng nhập vào
        if (quantitis<=0){
            System.out.println("Nhập số lượng cần lớn hơn 0. Kiểm tra lại.");
        }else {
            OrderItems orderItems = orderItemServices.findObject(orderItemID);
            long productID = orderItems.getProductID();
            Product product = productServices.findObject(productID);
            Order order = orderServices.findObject(orderItems.getOrderID());
            System.out.printf("Bạn muốn thay đổi số lượng %d sản phẩm thành %d sản phẩm?\n",orderItems.getQuantitis(),quantitis);
            int deltaQuantitys = quantitis - orderItems.getQuantitis(); // khoảng tăng(hoặc giảm) = số lượng ban đầu - số lượng hiện orderItem đang có
            boolean isAccept = AppUtils.isAcceptMenu(); //nếu deltaQuantitys > tăng số lượng || deltaQuantitys < 0 giảm số lượng
            if (isAccept){
                if (deltaQuantitys > 0) {
                    //trường hợp tăng số lượng
                    deltaQuantitys = Math.abs(quantitis - orderItems.getQuantitis());
                    System.out.println("TH tăng số lượng.");
                    if (deltaQuantitys>product.getQuantitys()){
                        System.out.printf("Số lượng sản phẩm hiện có %d không đủ. Cần thay đổi số lượng nhỏ hơn.\n",product.getQuantitys());
                    }else {
                        product.setQuantitys(product.getQuantitys() - deltaQuantitys);
                        orderItems.setQuantitis(quantitis);
                        double deltaTotals = (double) deltaQuantitys * product.getPrices();
//                        Order order = orderServices.findObject(orderItems.getOrderID());
//                        order.setGrandTotal(order.getGrandTotal() + deltaTotals);
//                        orderServices.edit(order);
                        double totals = product.getPrices() * (double) quantitis;
                        orderItems.setTotals(totals);
                        orderItemServices.edit(orderItems);
//                        Order order = orderServices.findObject(orderItems.getOrderID());
                        order.setGrandTotal(order.getGrandTotal()+deltaTotals);
//                        orderServices.edit(order);
//                        Product product1 = productServices.findProductRemoved(product.getProductID()); // lấy product ở file đã xóa rồi set lại số lượng
//                        product1.setQuantitys(quantitis);
//                        productServices.editProductRemoved(product1);
                    }
                }
                else {
                    System.out.println("TH giảm số lượng.");
                    deltaQuantitys = Math.abs(quantitis - orderItems.getQuantitis());
                    product.setQuantitys(product.getQuantitys() + deltaQuantitys);
                    orderItems.setQuantitis(quantitis);
                    double deltaTotals = (double) deltaQuantitys * product.getPrices();
                    orderItems.setTotals(orderItems.getTotals() - deltaTotals);
                    orderItemServices.edit(orderItems);
//                    Order order = orderServices.findObject(orderItems.getOrderID());
                    order.setGrandTotal(order.getGrandTotal() - deltaTotals);
//                    orderServices.edit(order);
//                    productServices.deleteProductRemoved(product);
                }

                Product product1 = productServices.findProductRemoved(product.getProductID()); // lấy product ở file đã xóa rồi set lại số lượng
                product1.setQuantitys(quantitis);
                productServices.editProductRemoved(product1);
                productServices.edit(product);
                orderServices.edit(order);
                System.out.println("Đã cập nhật thành công!");
            }
        }
    }
    private void editByRemoveOrderItem(long orderItemID){
        OrderItems orderItems = orderItemServices.findObject(orderItemID);
        Product product = productServices.findObject(orderItems.getProductID());
        Order order = orderServices.findObject(orderItems.getOrderID());
        showOrderItem(orderItems,Options.REMOVE);
        order.setGrandTotal(order.getGrandTotal() - orderItems.getTotals());
        product.setQuantitys(product.getQuantitys() + orderItems.getQuantitis());
        productServices.edit(product);
        orderItemServices.removeOrderItemNotWrited(orderItemID);
        productServices.removeProductRemoved(product.getProductID());
        orderServices.edit(order);
        System.out.println("Đã xóa mục sản phẩm thành công");
    }
    public void showOrderItem(OrderItems orderItems,Options options){
        System.out.printf("%s %s %s %s %s\n","ID","Titles","Quantitis","Prices","Totals");
        Product product = productServices.findObject(orderItems.getProductID());
        System.out.printf("%s %s %s %s %s\n",orderItems.getOrderItemsID(),product.getTitles(),orderItems.getQuantitis(),
                    orderItems.getPrices(),orderItems.getTotals());
    }
}
