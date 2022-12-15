package project.shopping_system.views.order;

import project.shopping_system.models.Order;
import project.shopping_system.models.OrderItems;
import project.shopping_system.models.Product;
import project.shopping_system.services.OrderItemServices;
import project.shopping_system.services.OrderServices;
import project.shopping_system.services.ProductServices;
import project.shopping_system.utils.DateTimeUtil;
import project.shopping_system.views.order.OrderItemViews;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class OrderViews {
    Scanner scanner = new Scanner(System.in);
    private OrderServices orderServices;
    private ProductServices productServices;
    private OrderItemServices orderItemServices;
    public OrderViews(){
        orderServices = OrderServices.getInstance();
        orderItemServices = OrderItemServices.getInstance();
        productServices = ProductServices.getInstance();
    }
//    (long orderID, String customerFullName,
//    String customerPhoneNumber, String customerAddress, String customerEmail,
//    double grandTotal,Instant atCreated, java.time.Instant atUpdated)
    public void showOrder(Order order){
        //hiển thị thông tin hóa đơn khách hàng
        double grandTotal = 0; // tổng tiền hóa đơn
        int count = 0; // đếm số item trên hóa đơn
        List<OrderItems> orderItemsList = OrderItemServices.getCurrentList();
        System.out.println(">Hiển thị thông tin hóa đơn khách hàng.");
        System.out.printf("Họ và tên: %s\n", order.getCustomerFullName());
        System.out.printf("Địa chỉ: %s\n",order.getCustomerAddress());
        System.out.printf("Số điện thoại: %s\n",order.getCustomerPhoneNumber());
        System.out.printf("Email: %s\n",order.getCustomerEmail());
        System.out.printf("Ngày tạo: %s\n", DateTimeUtil.formatIntanceToString(order.getAtCreated()));
        System.out.printf("Ngày cập nhật: %s\n",DateTimeUtil.formatIntanceToString(order.getAtUpdated()));
        System.out.printf("%s %s %s %s %s \n","ID","Tên sản phẩm", "Số lượng", "Giá","Tổng tiền");
        for (OrderItems orderItem : orderItemsList){
            //(long orderItemsID, String productName, int quantitis,double prices,long orderID, long productID, double totals)
            if (orderItem.getOrderID() == order.getOrderID()){
                System.out.printf("%d %s %d %f %f",orderItem.getOrderItemsID(), orderItem.getProductName(),orderItem.getQuantitis(),
                        orderItem.getPrices(), orderItem.getTotals());
                grandTotal += orderItem.getTotals();
                count += count;
            }
        }
        if (count == 0){
            System.out.println(">Hóa đơn trống.");
        }else
            System.out.printf("Tổng tiền: %f\n",grandTotal);
    } // hiển thị thông tin từng hóa đơn
    //viết hiển thị hóa đơn theo ngày, theo tháng, theo năm.
    public void showOrderList(){
        List<Order> orderList = OrderServices.getCurrentList();
    }
    public void add(){
        //tạo 1 hóa đơn mới
        List<Order> orderList = OrderServices.getCurrentList();
        long orderID = System.currentTimeMillis()%100000000;
        //(long orderID, String customerFullName,
        //                 String customerPhoneNumber, String customerAddress, String customerEmail,
        //                 double grandTotal,Instant atCreated, Instant atUpdated)
        System.out.print("Nhập họ tên khách hàng: ");
        String customerFullName = scanner.nextLine();
        System.out.printf("\nNhập số điện thoại khách hàng: ");
        String customerPhoneNumber = scanner.nextLine();
        System.out.print("\nNhập địa chỉ khách hàng: ");
        String customerAddress = scanner.nextLine();
        System.out.print("\nNhập email khách hàng: ");
        String customerEmail = scanner.nextLine();
        Instant atCreated = Instant.now();
        Instant atUpdated = Instant.now();
        Order order = new Order(orderID,customerFullName,customerPhoneNumber,customerAddress,
                customerEmail,0.0,atCreated, atUpdated);
        OrderServices.setCurrentList(orderList);
        System.out.println("Tạo hóa đơn thành công!");
        showOrder(order); // báo hóa đơn trống
        List<OrderItems> orderItemsList = OrderItemServices.getCurrentList();
        List<Product> productList = ProductServices.getCurrentList();
        boolean flag = false;
        do {
            System.out.println("1.Thêm sản phẩm.");
            System.out.println("0.Quay lại.");
            System.out.print(">Chọn chức năng: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    OrderItemViews orderItemViews = new OrderItemViews();
                    OrderItems orderItems = orderItemViews.add(order);
                    for (Order o : orderList){
                        if (o.getOrderID() == orderItems.getOrderID()){
                            double grandTotal = o.getGrandTotal() + orderItems.getTotals();
                            o.setGrandTotal(grandTotal);
                        }
                    }
                    //gọi sang view thêm từng orderItem rồi trả về danh sách orderItem thuộc order
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                    System.out.println("1.Tiếp tục");
                    System.out.println("0.Quay lại");
                    System.out.print(">Chọn chức năng: ");
                    int checkDefault = Integer.parseInt(scanner.nextLine());
                    switch (checkDefault){
                        case 1:
                            flag = true;
                            break;
                        case 2:
                            flag = false;
                            break;
                        default:
                            System.out.println("Chọn sai chức năng.");
                            System.out.println("Thoát chương trình");
                            System.exit(2);
                    }
                    break;
            }
        }while (flag);
    }
    public void edit() {
        List<Order> orderList = OrderServices.getCurrentList();
        boolean flag = false;
        boolean isRetry = false;
        //boolean isChoiceContinus = false;
        showOrderList();
        System.out.println(">Sửa thông tin hóa đơn.");
        do {
            System.out.print("Nhập mã ID hóa đơn: ");
            long orderID = Long.parseLong(scanner.nextLine());
            if (orderServices.isExistObject(orderID)) {
                do {
                    System.out.println(">Quản lý hóa đơn khách hàng.");
                    showOrder(orderServices.findObject(orderID));
                    System.out.println();
                    System.out.println("1.Sửa họ và tên.");
                    System.out.println("2.Sửa số điện thoại.");
                    System.out.println("3.Sửa địa chỉ.");
                    System.out.println("4.Sửa Email.");
                    System.out.println("0.Quay lại.");
                    System.out.print(">Chọn chức năng: ");
                    Order newOrder = new Order();
                    newOrder.setOrderID(orderID);
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.print("Nhập họ và tên: ");
                            String customerFullName = scanner.nextLine();
                            newOrder.setCustomerFullName(customerFullName);
                            break;
                        case 2:
                            System.out.print("Nhập số điện thoại: ");
                            String customerPhoneNumber = scanner.nextLine();
                            newOrder.setCustomerPhoneNumber(customerPhoneNumber);
                            break;
                        case 3:
                            System.out.print("Nhập địa chỉ: ");
                            String customerAddress = scanner.nextLine();
                            newOrder.setCustomerAddress(customerAddress);
                            break;
                        case 4:
                            System.out.print("Nhập Email: ");
                            String customerEmail = scanner.nextLine();
                            newOrder.setCustomerEmail(customerEmail);
                            break;
                        case 0:
                            //thoát chương trình, quay về menu trước đó
                            System.exit(5);
                            break;
                        default:
                            //trường hợp nhập sai lần 1
                            System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                            System.out.println("Bạn muốn tiếp tục?");
                            System.out.println("1.Tiếp tục.");
                            System.out.println("0.Quay lại.");
                            System.out.print(">Chọn chức năng: ");
                            int choiceDefault = Integer.parseInt(scanner.nextLine());
                            switch (choiceDefault) {
                                case 1:
                                    //quay về menu quản lý hóa đơn
                                    flag = true;
                                    break;
                                case 0:
                                    //quay về nhập ID
                                    flag = false; // thoát vòng lặp menu
                                    isRetry = true;
                                    break;
                                default:
                                    //chọn sai chức năng lần 2 thì thoát, quay về menu trước đó
                                    System.out.println("Chọn sai chức năng.");
                                    System.out.println("Thoát chương trình.");
                                    System.exit(3);
                            }
                            break;
                    }
                    if (choice == 1 || choice == 2 || choice == 3 || choice == 4) {
                        boolean isChoiceAgree = false;
                        flag = false;
                        //showOrder(newOrder);
                        do {
                            System.out.printf("Bạn có muốn thay đổi thông tin hóa đơn %d này?\n", orderServices.findObject(orderID).getOrderID());
                            System.out.println("1.Đồng ý.");
                            System.out.println("0.Quay lại.");
                            System.out.print(">Chọn chức năng: ");
                            int choiceAgrees = Integer.parseInt(scanner.nextLine());
                            switch (choiceAgrees){
                                case 1:
                                    orderServices.edit(newOrder);
                                    System.out.println("Đã cập nhật thành công!");
                                    break;
                                case 0:
                                    flag = true;
                                    break;
                                default:
                                    System.out.println("Chọn sai chức năng. Kiếm tra lại.");
                                    isChoiceAgree = true;
                                    break;
                            }
                        } while (isChoiceAgree);
                    }
                }while (flag);
            }
            else {
                    System.out.println("Mã ID hóa đơn không tồn tại.");
                    System.out.println("Bạn muốn tiếp tục?");
                    System.out.println("1.Tiếp tục.");
                    System.out.println("0.Quay lại.");
                    System.out.print(">Chọn chức năng: ");
                    int choiceContinus = Integer.parseInt(scanner.nextLine());
                    switch (choiceContinus){
                        case 1:
                            isRetry = true;
                            break;
                        case 0:
                            System.exit(2);
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
                                    isRetry = true;
                                    break;
                                case 0:
                                    //thoát quay lại menu cũ không thông báo
                                    isRetry = false;
                                    break;
                                default:
                                    System.out.println("Chọn sai chức năng.");
                                    System.out.println("Thoát chương trình.");
                                    System.exit(4);
                            }
                            break;
                    }
                }
        }while (isRetry);
    }
    public void remove(){
        List<Order> orderList = OrderServices.getCurrentList();
        boolean isRetry = false;
        boolean isContinus = false;
        boolean isChoice = false; // biến check nhập id đúng nhưng chọn sai chức năng
        System.out.println(">Xóa hóa đơn.");
        do {
            System.out.print("Nhập mã ID hóa đơn: ");
            long orderID = Long.parseLong(scanner.nextLine());
            if (orderServices.isExistObject(orderID)){
                Order order = orderServices.findObject(orderID);
                showOrder(order);
                do {
                    System.out.printf("Bạn muốn xoá hóa đơn %s này?\n",orderID);
                    System.out.println("1.Đồng ý");
                    System.out.println("0.Quay lại.");
                    System.out.print(">Chọn chức năng: ");
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice){
                        case 1:
                            orderServices.remove(order);
                            System.out.println("Đã cập nhật thành công.");
                            break;
                        case 0:
                            //thoát remove quay về menu ban đầu
                            isRetry = false;
                            break;
                        default:
                            //chọn sai chức năng lần 1
                            System.out.println("Chọn sai chức năng. Kiểm tra lại");
                            System.out.println("Bạn muốn tiếp tục?");
                            System.out.println("1.Tiếp tục.");
                            System.out.println("0.Quay lại.");
                            System.out.print(">Chọn chức năng: ");
                            int choiceDefault = Integer.parseInt(scanner.nextLine());
                            switch (choiceDefault){
                                case 1:
                                    isChoice = true;
                                    break;
                                case 0:
                                    isChoice = false; // thoát vòng lặp accept
                                    isRetry = false; //thoát remove quay về menu đầu
                                    break;
                                default:
                                    //chọn sai chức năng lần 2, thoát quay về menu cũ
                                    System.out.println("Chọn sai chức năng.");
                                    System.out.println("Thoát chương trình.");
                                    System.exit(3);
                                    break;
                            }
                    }
                    if (choice == 1){
                        isChoice = false;
                        System.out.println("Bạn có muốn tiếp tục chương trình?");
                        System.out.println("1.Tiếp tục.");
                        System.out.println("0.Quay lại.");
                        System.out.print(">Chọn chức năng: ");
                        int choiceContinus = Integer.parseInt(scanner.nextLine());
                        switch (choiceContinus){
                            case 1:
                                isRetry = true;
                                break;
                            case 0:
                                isRetry = false;
                                break;
                            default:
                                System.out.println("Chọn sai chức năng. Kiểm tra lại");
                                System.out.println("Bạn muốn tiếp tục?");
                                System.out.println("1.Tiếp tục.");
                                System.out.println("0.Quay lại.");
                                System.out.print(">Chọn chức năng: ");
                                int choiceDefault = Integer.parseInt(scanner.nextLine());
                                switch (choiceDefault){
                                    case 1:
                                        isRetry = true;
                                        break;
                                    case 0:
                                        System.exit(3);
                                        break;
                                    default:
                                        System.out.println("Chọn sai chức năng.");
                                        System.out.println("Thoát chương trình.");
                                        System.exit(3);
                                        break;
                                }
                                break;
                        }
                    }
                }while (isChoice);
            }
            else {
                System.out.println("Mã ID hóa đơn không tồn tại.");
                System.out.println("Bạn muốn tiếp tục?");
                System.out.println("1.Tiếp tục.");
                System.out.println("0.Quay lại.");
                System.out.print(">Chọn chức năng: ");
                int choiceContinus = Integer.parseInt(scanner.nextLine());
                switch (choiceContinus){
                    case 1:
                        isRetry = true;
                        break;
                    case 0:
                        System.exit(2);
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
                                isRetry = true;
                                break;
                            case 0:
                                //thoát quay lại menu cũ không thông báo
                                isRetry = false;
                                break;
                            default:
                                System.out.println("Chọn sai chức năng.");
                                System.out.println("Thoát chương trình.");
                                System.exit(4);
                        }
                        break;
                }
            }
        }while (isRetry);
    }

}
