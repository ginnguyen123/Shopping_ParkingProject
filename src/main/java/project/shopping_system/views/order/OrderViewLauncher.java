package project.shopping_system.views.order;

import project.shopping_system.models.Account;
import project.shopping_system.models.AccountTypes;
import project.shopping_system.services.OrderServices;
import project.shopping_system.views.Options;

import java.util.Scanner;

public class OrderViewLauncher {
    static final Scanner scanner = new Scanner(System.in);
    private static final int SHOW_ORDER = 1;
    private static final int ADD_ORDER = 2;
    private static final int EDIT_ODER = 3;
    private static final int REMOVE_ORDER = 4;
    private static final int FIND_ORDER = 5;
    private static final int SORT_ORDER = 6;
    private static final int SHOW_ORDER_REMOVED = 7;
    //private static final int RESTOER_ORDER_REMOVED = 8;
    private static final int ADMIN_RETURN = 9;
    private static final int USER_RETURN = 7;
    private static final int EXIT = 0;
    private static final int STATISTICES_BY_DAYS = 1;
    private static final int STATISTICES_BY_MONTHS = 2;
    private static final int STATISTICES_BY_YEARS = 3;
    private static final int STATISTICES_RETURN = 4;
    private static final OrderViews orderViews = new OrderViews();
    public OrderViewLauncher(){}

    private static void adminManagerMenuOrder(){
        System.out.println(">Menu oder.");
        System.out.println("1.Hiển thị danh sách hóa đơn chưa in.");
        System.out.println("2.Thêm hóa đơn.");
        System.out.println("3.Sửa hóa đơn.");
        System.out.println("4.In hóa đơn.");
        System.out.println("5.Tìm kiếm hóa đơn.");
        System.out.println("6.Sắp xếp hóa đơn.");
        System.out.println("7.Hiển thị danh sách hóa đơn đã in.");
        System.out.println("9.Quay lại.");
        System.out.println("0.Thoát.");
        System.out.print(">Chọn chức năng: ");
    }
    private static void userManagerMenuOrder(){
        System.out.println(">Menu oder.");
        System.out.println("1.Hiển thị danh sách hóa đơn.");
        System.out.println("2.Thêm hóa đơn.");
        System.out.println("3.Sửa hóa đơn.");
        System.out.println("4.In hóa đơn.");
        System.out.println("5.Tìm kiếm hóa đơn.");
        System.out.println("6.Sắp xếp hóa đơn.");
        System.out.println("7.Quay lại.");
        System.out.println("0.Thoát.");
        System.out.print(">Chọn chức năng: ");
    }
    public static void statisticalManagerMenu(){
        System.out.println(">Thống kê.");
        System.out.println("1.Thống kê theo ngày.");
        System.out.println("2.Thống kê theo tháng.");
        System.out.println("3.Thống kê theo năm.");
        System.out.println("4.Quay lại.");
        System.out.println("0.Thoát.");
        System.out.print(">Chọn chức năng: ");
    }
    public static void  oderLaunchs (Account account){
        boolean isReturn = true;
        int options;
        if (account.getAccountTypes() == AccountTypes.ADMIN){
            do {
                adminManagerMenuOrder();
                try{
                    options = Integer.parseInt(scanner.nextLine());
                    switch (options){
                        case SHOW_ORDER:
                            orderViews.showOrderList(OrderServices.findAll(), Options.SHOW);
                            break;
                        case ADD_ORDER:
                            orderViews.add(account.getAccountID());
                            break;
                        case EDIT_ODER:
                            orderViews.edit();
                            break;
                        case REMOVE_ORDER:
                            //in hóa đơn = xóa
                            orderViews.remove();
                            break;
                        case FIND_ORDER:
                            orderViews.findOrderAdminMenu();
                            break;
                        case SORT_ORDER:
                            orderViews.sortOrderMenuAdmin();
                            break;
                        case SHOW_ORDER_REMOVED:
                            orderViews.showOrderList(OrderServices.findAllOrdersRemoved(),Options.STATISTICAL);
                            break;
                        case ADMIN_RETURN:
                            isReturn = false;
                            break;
                        case EXIT:
                            System.out.println("Thoát chương trình.");
                            System.exit(1);
                        default:
                            System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                            System.out.print("Nhập lại: ");
                    }
                }catch (Exception e){
                    System.out.println(">Nhập sai cú pháp. Kiểm tra lại.");
                    System.out.print("Nhập lại: ");
                }
        }while (isReturn);
        }
        if (account.getAccountTypes() == AccountTypes.USER){
            do {
                userManagerMenuOrder();
                try{
                    options = Integer.parseInt(scanner.nextLine());
                    switch (options){
                        case SHOW_ORDER:
                            orderViews.showOrderList(OrderServices.findAll(),Options.SHOW);
                            break;
                        case ADD_ORDER:
                            orderViews.add(account.getAccountID());
                            break;
                        case EDIT_ODER:
                            orderViews.edit();
                            break;
                        case REMOVE_ORDER:
                            orderViews.remove();
                            break;
                        case FIND_ORDER:
                            orderViews.findExistOrder();
                            break;
                        case SORT_ORDER:
                            orderViews.sortExistOrderMenu();
                            break;
                        case USER_RETURN:
                            isReturn = false;
                            break;
                        case EXIT:
                            System.out.println("Thoát chương trình");
                            System.exit(1);
                            break;
                        default:
                            System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                            System.out.print("Nhập lại: ");
                            isReturn = true;
                    }
                }catch (Exception e){
                    System.out.println(">Nhập sai cú pháp. Kiểm tra lại.");
                    System.out.print("Nhập lại: ");
                    isReturn = true;
                }
            }while (isReturn);
        }
    }
    public static void statistical(){
        boolean isReturn = false;
        statisticalManagerMenu();
        do {
                int options = Integer.parseInt(scanner.nextLine());
                switch (options){
                    case STATISTICES_BY_DAYS:
                        orderViews.statisticalByDay();
                        break;
                    case STATISTICES_BY_MONTHS:
                        orderViews.statisticalByMonth();
                        break;
                    case STATISTICES_BY_YEARS:
                        orderViews.statisticalByYear();
                        break;
                    case STATISTICES_RETURN:
                        break;
                    case EXIT:
                        System.out.println(">Thoát chương trình");
                        System.exit(1);
                    default:
                        System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                        System.out.print("Nhập lại: ");
                        isReturn = true;
                }

        }while (isReturn);
    }

}
