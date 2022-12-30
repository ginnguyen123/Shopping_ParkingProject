package project.shopping_system.views.login;

import project.shopping_system.models.Account;
import project.shopping_system.models.AccountTypes;
import project.shopping_system.services.AccountServices;
import project.shopping_system.utils.AppUtils;
import project.shopping_system.views.Options;
import project.shopping_system.views.order.OrderViewLauncher;
import project.shopping_system.views.product.ProductViewLauncher;

import java.util.Scanner;

public class LoginViews {
    static Scanner scanner = new Scanner(System.in);
    private static AccountServices accountServices = new AccountServices();
    public LoginViews(){}
    private static Account loginAcount(){
        boolean isContinus = false;
        System.out.println(">Đăng nhập tài khoản.");
        do {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            if (accountServices.isLogin(username,password)) {
                System.out.println(">Đăng nhập thành công!");
                Account account = accountServices.login(username, password);
                return account;
            }
            else {
                System.out.println(">Tài khoản không tồn tại.");
                System.out.println(">Kiểm tra lại username hoặc password!");
                System.out.println("Bạn muốn tiếp tục đăng nhập?");
                isContinus = AppUtils.isAcceptMenu();
                if (isContinus == false){
                    System.out.println(">Thoát chương trình");
                    System.exit(0);
                }
            }
        }while (isContinus);
        return null;
    }
    private static void menuAdminAccount(){
        System.out.println(">Quản lí tài khoản.");
        System.out.println("1.Quản lí tài khoản.");
        System.out.println("2.Quản lí hóa đơn.");
        System.out.println("3.Quản lí kho.");
        System.out.println("4.Thống kê.");
        System.out.println("0.Thoát.");
        System.out.print(">Chọn chức năng: ");
    }
    private static void menuUserAccount(){
        System.out.println(">Quản lí tài khoản.");
        System.out.println("1.Quản lí hóa đơn.");
        System.out.println("2.Quản lí sản phẩm.");
        System.out.println("0.Thoát.");
        System.out.print(">Chọn chức năng: ");
    }
    private static void accountLaucher(){
        boolean isChoice = true;
        Account account = null;
        try {
            account = loginAcount();
        }catch (NullPointerException nullPointerException){
            if (account == null){
                System.out.println(">Thoát chương trình.");
                System.exit(0);
            }
        }
        if (account.getAccountTypes() == AccountTypes.ADMIN){
            do {
                menuAdminAccount();
                String choice = scanner.nextLine();
                switch (choice){
                    case "1":
                        AccountViewLauchers.lauch(account);
                        break;
                    case "2":
                        OrderViewLauncher.oderLaunchs(account);
                        break;
                    case "3":
                        ProductViewLauncher.productLaunchs(account);
                        break;
                    case "4":
                        OrderViewLauncher.statistical();
                        break;
                    case "0":
                        System.out.println(">Thoát chương trình.");
                        isChoice = false;
                        break;
                    default:
                        System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                        System.out.print(">Nhập lại: ");
                }
            }while (isChoice);
        }
        if (account.getAccountTypes() == AccountTypes.USER){
                do {
                menuUserAccount();
                String choice = scanner.nextLine();
                switch (choice){
                    case "1":
                        ProductViewLauncher.productLaunchs(account);
                        break;
                    case "2":
                        OrderViewLauncher.oderLaunchs(account);
                        break;
                    case "0":
                        System.out.println(">Thoát chương trình.");
                        isChoice = false;
                        break;
                    default:
                        System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                        System.out.print(">Nhập lại: ");
                        break;
                }
            }while (isChoice);
        }
    }
    private static boolean isChoice(){
        System.out.println(">Chọn 'c' - continus để tiếp tục\t|\t'e' - exit để thoát.");
        System.out.print(">Chọn chức năng: ");
        String choice = scanner.nextLine();
        switch (choice){
            case "c":
                return true;
            case "e":
                System.out.println(">Thoát chương trình");
                System.exit(0);
                break;
            default:
                System.out.println("Chọn sai chức năng.");
        }
        return false;
    }
    public static void Laucher(){
        boolean isChoice = false;
        System.out.println(">Đăng nhập tài khoản.");
        System.out.println("1.Đăng nhập.");
        System.out.println("0.Thoát chương trình.");
        System.out.print(">Chọn chức năng: ");
        do {
            String choice = scanner.nextLine();
            switch (choice){
                case "1":
                    accountLaucher();
                    break;
                case "0":
                    System.out.println(">Thoát chương trình.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                    isChoice = isChoice();
                    if (isChoice)
                        System.out.print(">Nhập lại: ");
                    else {
                        System.out.println(">Thoát chương trình.");
                    }
                    break;
            }
        }while (isChoice);
    }
}
