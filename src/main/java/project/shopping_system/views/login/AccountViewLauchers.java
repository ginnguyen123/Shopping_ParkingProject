package project.shopping_system.views.login;

import project.shopping_system.models.Account;
import project.shopping_system.utils.AppUtils;
import project.shopping_system.views.Options;

import java.util.Scanner;

public class AccountViewLauchers {
    static Scanner scanner = new Scanner(System.in);
    private static final int SHOW = 1;
    private static final int ADD = 2;
    private static final int EDIT = 3;
    private static final int REMOVE = 4;
    private static final int FINE = 5;
    private static final int RETURN = 6;
    private static final int EXIT = 0;
    private static final AccountViews accountViews = new AccountViews();
    private static void accountManager(){
        System.out.println(">Quản lý tài khoản.");
        System.out.println("1.Hiển thị danh sách tài khoản.");
        System.out.println("2.Thêm tài khoản.");
        System.out.println("3.Đổi mật khẩu.");
        System.out.println("4.Xóa tài khoản.");
        System.out.println("5.Tìm tài khoản.");
        System.out.println("6.Quay lại.");
        System.out.println("0.Thoát chương trình.");
        System.out.print(">Chọn chức năng: ");
    }
    public static void lauch(Account account){
        boolean isRetry = true;
        do {
            try {
                accountManager();
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice){
                    case SHOW:
                        accountViews.showAccountList(Options.SHOW);
                        break;
                    case ADD:
                        accountViews.addUser();
                        break;
                    case EDIT:
                        accountViews.edit(account);
                        break;
                    case REMOVE:
                        accountViews.remove();
                        break;
                    case FINE:
                        accountViews.find();
                        break;
                    case RETURN:
                        isRetry = false;
                        break;
                    case EXIT:
                        System.out.println("Thoát chương trình.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Chọn sai chức năng. Kiểm tra lại.");
                        break;
                }
            }catch (Exception e){
                System.out.println("Nhập sai. Kiểm tra lại.");
            }
        }while (isRetry);
    }
}
