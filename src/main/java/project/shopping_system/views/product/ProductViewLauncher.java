package project.shopping_system.views.product;

import project.shopping_system.models.Account;
import project.shopping_system.models.AccountTypes;
import project.shopping_system.services.ProductServices;
import project.shopping_system.utils.AppUtils;
import project.shopping_system.views.Options;

import java.util.Scanner;

public class ProductViewLauncher {
    static Scanner scanner = new Scanner(System.in);
    private static final int SHOW = 1;
    private static final int EXIT = 0;
    private static final int ADMIN_ADD = 2;
    private static final int ADMIN_EDIT = 3;
    private static final int ADMIN_REMOVE = 4;
    private static final int ADMIN_FIND = 5;
    private static final int ADMIN_SORT = 6;
    private static final int SHOW_REMOVE = 7;
    private static final int RESTORE_REMOVE = 8;
    private static final int ADMIN_RETURN = 9;
    private static final int USER_FIND = 2;
    private static final int USER_SORT = 3;
    private static final int USER_RETURN = 4;
    private static final ProductViews productViews = new ProductViews();
    private static void adminManagerMenuProduct(){
        System.out.println(">Quản lí sản phẩm.");
        System.out.println("1.Hiển thị danh sách sản phẩm.");
        System.out.println("2.Thêm sản phẩm.");
        System.out.println("3.Sửa sản phẩm.");
        System.out.println("4.Xóa sản phẩm.");
        System.out.println("5.Tìm kiếm sản phẩm.");
        System.out.println("6.Sắp xếp sản phẩm.");
        System.out.println("7.Hiển thị danh sách sản phẩm đã xóa.");
//        System.out.println("8.Khôi phục sản phẩm đã xóa.");
        System.out.println("9.Quay lại.");
        System.out.println("0.Thoát.");
        System.out.print(">Chọn chức năng: ");
    }
    private static void userManagerMenuProduct(){
        System.out.println(">Quản lí sản phẩm.");
        System.out.println("1.Hiển thị danh sách sản phẩm.");
        System.out.println("2.Tìm kiếm sản phẩm.");
        System.out.println("3.Sắp xếp sản phẩm.");
        System.out.println("4.Quay lại.");
        System.out.println("0.Thoát.");
        System.out.print(">Chọn chức năng: ");
    }
    public static void productLaunchs (Account account){
        boolean isReturn = true;
        if (account.getAccountTypes() == AccountTypes.ADMIN){
            do {
            adminManagerMenuProduct();
            int choice = AppUtils.retryParseIntInput();
            switch (choice){
                case SHOW:
                    productViews.showProductList(Options.SHOW, ProductServices.findAll());
                    break;
                    case ADMIN_ADD: productViews.add();
                    break;
                    case ADMIN_EDIT:
                        productViews.edit();
                        break;
                        case ADMIN_REMOVE:
                            productViews.remove();
                            break;
                        case ADMIN_FIND:
                            productViews.findProductMenu();
                            break;
                        case ADMIN_SORT:
                            productViews.sortProductMenu();
                            break;
                        case SHOW_REMOVE:
                            productViews.showProductList(Options.SHOW,ProductServices.findAllProductsRemoved());
                            break;
//                        case RESTORE_REMOVE:
//                            productViews.restoreProductRemoved();
//                            break;
                        case ADMIN_RETURN:
                            isReturn = false;
                            break;
                        case EXIT:
                            System.out.println(">Thoát chương trình.");
                            System.exit(1);
                            break;
                        default:
                            System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                            System.out.print("Nhập lại: ");
                            isReturn = true;
                            break;
                    }
            }while (isReturn);
        }
        if (account.getAccountTypes() == AccountTypes.USER) {
            int choice = 0;
            do {
                userManagerMenuProduct();
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice){
                        case SHOW:
                            productViews.showProductList(Options.SHOW,ProductServices.findAll());
                            break;
                        case USER_FIND:
                            productViews.findExistProduct();
                            break;
                        case USER_SORT:
                            productViews.sortProductMenu();
                            break;
                        case USER_RETURN:
                            isReturn = false;
                            break;
                        case EXIT:
                            System.out.println(">Thoát chương trình.");
                            System.exit(1);
                            break;
                        default:
                            System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                            isReturn = AppUtils.isRetry(Options.FIND);
                            break;
                    }
                }catch (Exception e){
                    System.out.println(">Nhập sai cú pháp. Kiểm tra lại.");
                    isReturn = AppUtils.isRetry(Options.FIND);
                }
            }while (isReturn);
        }
    }
}
