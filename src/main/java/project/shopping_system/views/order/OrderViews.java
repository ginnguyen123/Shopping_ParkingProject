package project.shopping_system.views.order;

import project.shopping_system.models.Order;
import project.shopping_system.models.OrderItems;
import project.shopping_system.models.Product;
import project.shopping_system.services.AccountServices;
import project.shopping_system.services.OrderItemServices;
import project.shopping_system.services.OrderServices;
import project.shopping_system.services.ProductServices;
import project.shopping_system.utils.AppUtils;
import project.shopping_system.utils.DateTimeUtil;
import project.shopping_system.views.Options;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class OrderViews {
    Scanner scanner = new Scanner(System.in);
    private OrderServices orderServices;
    private ProductServices productServices;
    private OrderItemServices orderItemServices;
    private AccountServices accountServices = new AccountServices();
    private OrderItemViews orderItemViews;
    public OrderViews(){
        orderServices = OrderServices.getInstance();
        orderItemServices = OrderItemServices.getInstance();
        productServices = ProductServices.getInstance();
        orderItemViews = new OrderItemViews();
    }
    public void showOrderDetail(Order order,Options options){
        if(options != Options.EDIT || options != Options.ADD || options == Options.FIND){
            System.out.printf("|%-8s| %-16s| %-16s| %-10s| %-16s| %-16s| %-5s| %-20s| %-20s|\n","ID", "Full name","Phone Number",
                    "Address","Email","Grand total","ID employee","At created","At updated");
            System.out.printf("|%-8s| %-16s| %-16s| %-10s| %-16s| %-16s| %-5s| %-20s| %-20s|\n",order.getOrderID(),order.getCustomerFullName(),order.getCustomerPhoneNumber(),
                    order.getCustomerAddress(),order.getCustomerEmail(),order.getGrandTotal(),
                    accountServices.findObject(order.getUserID()).getFullName(),DateTimeUtil.formatIntanceToString(order.getAtCreated()),DateTimeUtil.formatIntanceToString(order.getAtUpdated()));
        }
        if (orderServices.isExistObject(order.getOrderID())){
            orderItemViews.showOrderItemExistList(order.getOrderID());
        }
        if (orderServices.isRemoveObject(order.getOrderID()) && options == Options.STATISTICAL) {
            System.out.printf("|%-8s| %-16s| %-16s| %-10s| %-16s| %-16s| %-5s| %-20s| %-20s|\n","ID", "Full name","Phone Number",
                    "Address","Email","Grand total","ID employee","At created","At updated");
            orderItemViews.showOrderItemRemovedList(order.getOrderID());

        }
        if (options == Options.SHOW || options == Options.FIND || options == Options.EDIT && options != Options.STATISTICAL
                && (orderItemViews.showOrderItemExistList(order.getOrderID()))==false ) {//|| orderItemViews.showOrderItemRemovedList(order.getOrderID()) == false
            System.out.println("1.Thêm sản phẩm\t\t2.In hóa đơn\t\t3.Sửa hóa đơn\t\t0.Quay lại");
            System.out.print(">Chọn chức năng: ");
            int choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    orderItemViews.add(order.getOrderID());
                    break;
                case 2:
                    remove(order.getOrderID());
                    break;
                case 3:
                    editByID(order.getOrderID());
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Chọn sai chức năng.");
                    break;
            }
        }
    }
    public void showOrderList(List<Order> orderList, Options options){
        int count = 0;
        if (options == Options.SHOW || options == Options.EDIT || options == Options.REMOVE || options == Options.STATISTICAL || options == Options.SORT) {
            System.out.printf("|%-8s| %-16s| %-16s| %-10s| %-16s| %-16s| %-5s| %-20s| %-20s|\n","ID", "Full name","Phone Number",
                    "Address","Email","Grand total","Employee","At created","At updated");
            for (Order order : orderList){
                ++count;
                System.out.printf("|%-8s| %-16s| %-16s| %-10s| %-16s| %-16s| %-5s| %-20s| %-20s|\n",order.getOrderID(),order.getCustomerFullName(),order.getCustomerPhoneNumber(),
                        order.getCustomerAddress(),order.getCustomerEmail(),order.getGrandTotal(),
                        accountServices.findObject(order.getUserID()).getFullName(),DateTimeUtil.formatIntanceToString(order.getAtCreated()),DateTimeUtil.formatIntanceToString(order.getAtUpdated()));

            }
        }
        boolean isContinus = false;
        if (options == Options.SORT || options == Options.SHOW || options == Options.STATISTICAL || options == Options.REMOVE){
            do {
                if (count == 0 && options != Options.STATISTICAL){
                    System.out.println("0.Quay lại.");
                    System.out.print(">Chọn chức năng: ");
                    int choice = AppUtils.retryParseIntInput();
                    switch (choice){
                        case 0:
                            isContinus = false;
                            break;
                        default:
                            isContinus = true;
                            System.out.println("Chọn sai chức năng.");
                    }
                }
                if (options != Options.REMOVE && options != Options.STATISTICAL){
                    System.out.println("1.Xem chi tiết hóa đơn.\t\t\t\t0.Quay lại.");
                    System.out.print(">Chọn chức năng: ");
                    int choice = AppUtils.retryParseIntInput();
                    switch (choice){
                        case 1:
                            findOrder(orderList, options);
                            break;
                        case 0:
                            isContinus = false;
                            break;
                        default:
                            isContinus = true;
                            System.out.println("Chọn sai chức năng.");
                    }
                }
            }while (isContinus);
        }
    }
    public void add(long userID){
        long orderID = System.currentTimeMillis() % 100000000;
        String customerFullName = AppUtils.retryFullNameInput();
        String customerPhoneNumber = AppUtils.retryPhonenumberInput();
        String customerAddress = AppUtils.retryStreetAdressInput();
        String customerEmail = AppUtils.retryEmailInput();
        double grandTotal = 0;
        Instant atCreated = Instant.now();
        Instant atUpdated = Instant.now();
        Order order = new Order(userID,orderID,customerFullName,customerPhoneNumber,
                customerAddress,customerEmail,grandTotal,atCreated,atUpdated);
        boolean isRetry = false;
        orderServices.add(order);
        showOrderDetail(order,Options.ADD);
        System.out.println("Tạo hóa đơn thành công!");
        System.out.println("1.Thêm sản phẩm\t\t\t\t2.Sửa hóa đơn\t\t\t\t0.Quay lại.");
        boolean isContinus = true;
        do {
            System.out.print(">Chọn chức năng: ");
            int choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    orderItemViews.add(orderID);
                    break;
                case 2:
                    editByID(orderID);
                    break;
                case 0:
                    isContinus = false;
                    break;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                    break;
            }
        }while (isContinus);
    }
    public void remove(){
        boolean isRetry = false;
        showOrderList(OrderServices.findAll(),Options.REMOVE);
        System.out.print("Nhập mã ID hóa đơn: ");
        long orderID = AppUtils.retryParseLongInput();
        do {
            if (orderServices.isExistObject(orderID)){
                showOrderDetail(orderServices.findObject(orderID),Options.REMOVE);
                System.out.println("Bạn đồng ý in hóa đơn này?");
                boolean isAccept = AppUtils.isAcceptMenu();
                if (isAccept){
                    orderServices.remove(orderID);
                    orderItemServices.removeAllOrderItemsInOrder(orderID);
                    System.out.println("Đã in hóa đơn thành công.");
                }
            }else {
                System.out.println("Mã ID này không tồn tại. Kiểm tra lại.");
                isRetry = AppUtils.isRetry(Options.REMOVE);
            }
        }while (isRetry);
    }
    private void remove(long orderID){
        if (orderServices.isExistObject(orderID)){
//            showOrderDetail(orderServices.findObject(orderID),Options.REMOVE);
            System.out.println("Bạn đồng ý in hóa đơn này?");
            if (AppUtils.isAcceptMenu()){
                orderServices.remove(orderID);
                orderItemServices.removeAllOrderItemsInOrder(orderID);
                System.out.println("Đã in hóa đơn thành công.");
            }
        }else {
            System.out.println("Mã ID này không tồn tại. Kiểm tra lại.");
        }
    }
    private void editMenu(){
        boolean isEmptyOrderItem = false;
        //String customerFullName,
        //                 String customerPhoneNumber, String customerAddress, String customerEmail,
        System.out.println(">Quản lý hóa đơn.");
        System.out.println("1.Sửa họ và tên.");
        System.out.println("2.Sửa số điện thoại.");
        System.out.println("3.Sửa địa chỉ.");
        System.out.println("4.Sửa email.");
        System.out.println("5.Sửa danh mục sản phẩm hóa đơn.");
        System.out.println("0.Quay lại.");
        System.out.print(">Chọn chức năng: ");
    }
    public void edit(){
        System.out.println(">Sửa thông tin hóa đơn.");
        showOrderList(OrderServices.findAll(), Options.EDIT);
//        boolean isRetry = false;
//        int choice ;
            System.out.print("Nhập mã ID đơn hàng: ");
            long orderID = AppUtils.retryParseLongInput();
        if (orderServices.isExistObject(orderID)){
            editMenu();
            Order newOrder = new Order();
            newOrder.setOrderID(orderID);
            int choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    System.out.print("Nhập họ và tên: ");
                    String customerFullName = AppUtils.retryFullNameInput();
                    newOrder.setCustomerFullName(customerFullName);
                    break;
                case 2:
                    System.out.print("Nhập số điện thoại: ");
                    String customerPhoneNumber = AppUtils.retryPhonenumberInput();
                    newOrder.setCustomerPhoneNumber(customerPhoneNumber);
                    break;
                case 3:
                    System.out.print("Nhập địa chỉ: ");
                    String customerAddress = AppUtils.retryStreetAdressInput();
                    newOrder.setCustomerAddress(customerAddress);
                    break;
                case 4:
                    System.out.print("Nhập email: ");
                    String customerEmail = AppUtils.retryEmailInput();
                    newOrder.setCustomerEmail(customerEmail);
                    break;
                case 5:
                    orderItemViews.edit(orderID);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Nhập sai chức năng. Kiểm tra lại.");
                    break;
            }
            if ((choice == 1 || choice == 2 || choice == 3 || choice == 4) && choice != 5){
                System.out.println("Bạn muốn cập nhập thông tin mới?");
                boolean isAccept = AppUtils.isAcceptMenu();
                if (isAccept){
                    orderServices.edit(newOrder);
                    System.out.println("Đã cập nhật thành công!");
                }
            }
        }else {
            System.out.println("Mã ID này không tồn tại. Kiểm tra lại.");
        }
    }
    public void editByID(long orderID){
        if (orderServices.isExistObject(orderID)){
            editMenu();
            Order newOrder = new Order();
            newOrder.setOrderID(orderID);
            int choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    System.out.print("Nhập họ và tên: ");
                    String customerFullName = AppUtils.retryFullNameInput();
                    newOrder.setCustomerFullName(customerFullName);
                    break;
                    case 2:
                        System.out.print("Nhập số điện thoại: ");
                        String customerPhoneNumber = AppUtils.retryPhonenumberInput();
                        newOrder.setCustomerPhoneNumber(customerPhoneNumber);
                        break;
                    case 3:
                        System.out.print("Nhập địa chỉ: ");
                        String customerAddress = AppUtils.retryStreetAdressInput();
                        newOrder.setCustomerAddress(customerAddress);
                        break;
                    case 4:
                        System.out.print("Nhập email: ");
                        String customerEmail = AppUtils.retryEmailInput();
                        newOrder.setCustomerEmail(customerEmail);
                        break;
                case 5:
                    orderItemViews.edit(orderID);
                    break;
                    case 0:
                        break;
                    default:
                        System.out.println("Nhập sai chức năng. Kiểm tra lại.");
                        break;
                }
                if (choice!=0){
                    System.out.println("Bạn muốn cập nhập thông tin mới?");
                    boolean isAccept = AppUtils.isAcceptMenu();
                    if (isAccept){
                        orderServices.edit(newOrder);
                    }
                }
            }else {
                System.out.println("Mã ID này không tồn tại. Kiểm tra lại.");
            }
    }
    public void findOrderAdminMenu(){
        boolean isRetry = false;
        do {
            System.out.println(">Tìm kiếm hóa đơn.");
            System.out.println("1.Hóa đơn chưa in.");
            System.out.println("2.Hóa đơn đã in.");
            System.out.println("0.Quay lại.");
            System.out.print(">Chọn chức năng: ");
            int choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    findExistOrder();
                    break;
                case 2:
                    findRemovedOrder();
                    break;
                case 0:
                    isRetry = false;
                    break;
                default:
                    isRetry = true;
                    System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                    break;
            }
            if (choice != 1 && choice != 2 && choice !=0)
                isRetry = AppUtils.isRetry(Options.FIND);
        }while (isRetry);
    }
    public void findOrder(List<Order> orderList, Options options){
        showOrderList(orderList, Options.FIND);
        boolean isContinus = false;
        do {
            System.out.print("Nhập mã ID hóa đơn: ");
            long orderID = AppUtils.retryParseLongInput();
            if (orderServices.isExistObject(orderID)){
                Order order = orderServices.findObject(orderID);
                showOrderDetail(order,Options.SHOW);
//                showOrderDetail(orderServices.findObject(orderID),Options.SHOW);
            } else if (orderServices.isRemoveObject(orderID) && options == Options.STATISTICAL) {
                orderItemViews.showOrderItemRemovedList(orderID);
                System.out.println("Nhập phím bất kì để tiếp tục!");
                String anyKey = scanner.nextLine();
                //Order order = orderServices.findRemovedObject(orderID);
                //showOrderDetail(order,Options.STATISTICAL);
//                showOrderDetail(orderServices.findRemovedObject(orderID),Options.STATISTICAL);
            } else{
                System.out.println("Mã ID hóa đơn này không tồn tại. Kiểm tra lại.");
                isContinus = AppUtils.isRetry(Options.FIND);
            }
        }while (isContinus);

    }
    public void findExistOrder(){
        showOrderList(OrderServices.findAll(),Options.STATISTICAL);
        System.out.print("Nhập mã ID hóa đơn: ");
        long orderID = AppUtils.retryParseLongInput();
        if (orderServices.isExistObject(orderID)){
            showOrderDetail(orderServices.findObject(orderID),Options.SHOW);
        }else
            System.out.println("Mã ID hóa đơn này không tồn tại. Kiểm tra lại.");
    }
    private void findRemovedOrder(){
        showOrderList(OrderServices.findAllOrdersRemoved(),Options.REMOVE);
        System.out.print("Nhập mã ID hóa đơn: ");
        long orderID = AppUtils.retryParseLongInput();
        if (orderServices.isRemoveObject(orderID)){
            showOrderDetail(orderServices.findRemovedObject(orderID),Options.STATISTICAL);
            //xem chi tiết hóa đơn, orderItemViews
        }else
            System.out.println("Mã ID hóa đơn này không tồn tại. Kiểm tra lại.");
    }
    public void sortOrderMenuAdmin(){
        int choice;
        boolean isRetry = true;
        do {
            System.out.println(">Sắp xếp hóa đơn.");
            System.out.println("1.Hóa đơn chưa in.");
            System.out.println("2.Hóa đơn đã in.");
            System.out.println("0.Quay lại.");
            System.out.print(">Chọn chức năng: ");
            choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    sortExistOrderMenu();
                    isRetry = false;
                    break;
                case 2:
                    sortRemovedOrderMenu();
                    isRetry = false;
                    break;
                case 0:
                    isRetry = false;
                    break;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                    break;
            }
        }while (isRetry);

    }
    public void sortExistOrderMenu(){
        boolean isRetry = true;
        int choice;
        do {
            System.out.println(">Sắp xếp hóa đơn.");
            System.out.println("1.Theo tổng tiền hóa đơn.");
            System.out.println("2.Theo họ tên khách hàng.");
            System.out.println("0.Quay lại.");
            System.out.print(">Chọn chức năng: ");
            choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    sortByGrandTotalOrderMenu();
                    isRetry = false;
                    break;
                case 2:
                    sortByCustomerFullNameOrderMenu();
                    isRetry = false;
                    break;
                case 0:
                    isRetry = false;
                    break;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                    System.out.print("Nhập lại: ");
                    break;
            }
        }while (isRetry);
    }
    private void sortRemovedOrderMenu(){
        boolean isRetry = false;
        int choice;
        do {
            System.out.println(">Menu sắp xếp hóa đơn.");
            System.out.println("1.Theo tổng tiền hóa đơn.");
            System.out.println("2.Theo họ tên khách hàng.");
            System.out.println("0.Quay lại.");
            System.out.print(">Chọn chức năng: ");
            choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    sortByGrandTotalOrderRemovedMenu();
                    isRetry = false;
                    break;
                case 2:
                    sortByCustomerFullNameOrderRemoveMenu();
                    isRetry = false;
                    break;
                case 0:
                    isRetry = false;
                    break;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                    isRetry = true;
                    break;
            }
        }while (isRetry);
    }
    private void sortByGrandTotalOrderRemovedMenu(){
        boolean isRetry = false;
        int choice;
        do {
            System.out.println(">Menu sắp xếp hóa đơn.");
            System.out.println("1.Theo tổng tiền tăng dần.");
            System.out.println("2.Theo tổng tiền giảm dần.");
            System.out.println("0.Quay lại.");
            System.out.print(">Chọn chức năng: ");
            choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    showOrderList(orderServices.sortByGrandTotalIncreaseOrderRemoved(),Options.STATISTICAL);
                    isRetry = false;
                    break;
                case 2:
                    showOrderList(orderServices.sortByGrandTotalDecreaseOrderRemoved(),Options.STATISTICAL);
                    isRetry = false;
                    break;
                case 0:
                    isRetry = false;
                    break;
                default:
                    System.out.println(">Chọn sai chức năng. Kiểm tra laị.");
                    System.out.print(">Nhập lại: ");
                    isRetry = true;
                    break;
            }
        }while (isRetry);
    }
    private void sortByCustomerFullNameOrderRemoveMenu(){
        boolean isRetry = true;
        int choice;
        do {
            System.out.println(">Theo họ tên khách hàng.");
            System.out.println("1.Sắp xếp từ A đến Z.");
            System.out.println("2.Sắp xếp từ Z đến A.");
            System.out.println("0.Quay lại.");
            System.out.print(">Chọn chức năng: ");
            choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    showOrderList(orderServices.sortByNameAToZOrderRemoved(),Options.STATISTICAL);
                    isRetry = false;
                    break;
                case 2:
                    showOrderList(orderServices.sortByNameZToAOrderRemoved(),Options.STATISTICAL);
                    isRetry = false;
                    break;
                case 0:
                    isRetry = false;
                    break;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                    break;
            }
        }while (isRetry);
    }
    private void sortByCustomerFullNameOrderMenu(){
        boolean isRetry = true;
        int choice;
        do {
            System.out.println(">Theo họ tên khách hàng.");
            System.out.println("1.Sắp xếp từ A đến Z.");
            System.out.println("2.Sắp xếp từ Z đến A.");
            System.out.println("0.Quay lại.");
            System.out.print(">Chọn chức năng: ");
            choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    showOrderList(orderServices.sortByNameAToZ(),Options.SORT);
                    isRetry = false;
                    break;
                case 2:
                    showOrderList(orderServices.sortByNameZToA(),Options.SORT);
                    isRetry = false;
                    break;
                case 0:
                    isRetry = false;
                    break;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                    System.out.print("Nhập lại: ");
                    break;
            }
        }while (isRetry);
    }
    private void sortByGrandTotalOrderMenu(){
        boolean isRetry = false;
        int choice;
            System.out.println(">Theo tổng tiền hóa đơn");
            System.out.println("1.Sắp xếp theo tổng tiền tăng dần.");
            System.out.println("2.Sắp xếp theo tổng tiền giảm dần.");
            System.out.println("0.Quay lại.");
            System.out.print(">Chọn chức năng: ");
        do {
            choice = AppUtils.retryParseIntInput();
            switch (choice){
                case 1:
                    showOrderList(orderServices.sortByGrandTotalIncrease(),Options.SORT);
                    isRetry = false;
                    break;
                case 2:
                    showOrderList(orderServices.sortByGrandTotalDecrease(),Options.SORT);
                    isRetry = false;
                    break;
                case 0:
                    isRetry = false;
                    break;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra laị.");
                    System.out.print(">Nhập lại: ");
                    isRetry = true;
                    break;
            }
        }while (isRetry);
    }
    public void statisticalByDay(){
        Instant days = AppUtils.retryDayInput();
        System.out.printf("═════════════════════════════════════════ DOANH THU NGÀY %s ════════════════════════════════════\n", DateTimeUtil.formatDayIntanceToString(days));
        double statisticalByDay = orderServices.statisticalByDay(days);
        showStatistical(statisticalByDay, orderServices.statisticalByDayList(days));
    }
    public void statisticalByMonth(){
        Instant months = AppUtils.retryMonthInput();
        System.out.printf("═════════════════════════════════════════ DOANH THU THÁNG %s ════════════════════════════════════\n", DateTimeUtil.formatMonthIntanceToString(months));
        double statisticalByMonth = orderServices.statisticalByMonth(months);
        showStatistical(statisticalByMonth,orderServices.statisticalByYearList(months));
    }
    public void statisticalByYear(){
        Instant years = AppUtils.retryYearInput();
        System.out.printf("═════════════════════════════════════════ DOANH THU NĂM %s ════════════════════════════════════\n", DateTimeUtil.formatYearIntanceToString(years));
        double statisticalByYear = orderServices.statisticalByYear(years);
        showStatistical(statisticalByYear,orderServices.statisticalByYearList(years));
    }
    private void showStatistical(double statistical, List<Order> orderList){
        showOrderList(orderList,Options.STATISTICAL);
        System.out.println("║-----------------------------------------------------------------------------------------------------║");
        System.out.printf("║                                                         TỔNG DOANH THU: %-20s  ║\n", statistical);
        System.out.println("║-----------------------------------------------------------------------------------------------------║");
        System.out.println("Nhập phím bất kì để tiếp tục.");
        String anyKey = scanner.nextLine();
    }
}

