package project.shopping_system.views.login;

import project.shopping_system.models.Account;
import project.shopping_system.models.AccountTypes;
import project.shopping_system.services.AccountServices;
import project.shopping_system.utils.AppUtils;
import project.shopping_system.utils.DateTimeUtil;
import project.shopping_system.views.Options;

import java.time.Instant;
import java.util.Scanner;

public class AccountViews {
    Scanner scanner = new Scanner(System.in);
    private AccountServices accountServices;
    public AccountViews(){
        accountServices = AccountServices.getInstance();
    }
    public void showAccountList(Options options){
        //(long accountID, String userName, String passWord, String fullName, String phoneNumbers, String email,
        //                   String address, AccountTypes accountTypes, Instant atCreated, Instant atUpdated)
        System.out.printf("|%-8s| %-16s| %-16s| %-16s| %-5s| %-16s| %-16s|\n","ID","User name","Full name","Password"
                ,"Account Types", "At created", "At updated");
        for (Account account : AccountServices.findAll()){
            System.out.printf("|%-8s| %-16s| %-16s| %-16s| %-5s| %-16s| %-16s|\n",account.getAccountID(),account.getUserName(),
                    account.getFullName(),account.getPassWord() ,account.getAccountTypes(),
                    DateTimeUtil.formatIntanceToString(account.getAtCreated()),DateTimeUtil.formatIntanceToString(account.getAtUpdated()));
        }
        if (options != Options.EDIT || options != Options.REMOVE){
            System.out.println("Ấn phím bất kì để tiếp tục.");
            String anyPush = scanner.nextLine();
        }
    }
    public void showUserAccount(Account account){
        System.out.printf("|%-8s| %-16s| %-16s| %-16s| %-5s| %-16s| %-16s|\n","ID","User name","Full name","Password"
                ,"Account Types", "At created", "At updated");
        System.out.printf("|%-8s| %-16s| %-16s| %-16s| %-5s| %-16s| %-16s|\n",account.getAccountID(),account.getUserName(),
                account.getFullName(),account.getPassWord() ,account.getAccountTypes(),
                DateTimeUtil.formatIntanceToString(account.getAtCreated()),DateTimeUtil.formatIntanceToString(account.getAtUpdated()));
        System.out.println("Ấn phím bất kì để tiếp tục.");
        String anyPush = scanner.nextLine();
    }
    public void addUser(){
        // //(long accountID, String userName, String passWord, String fullName, String phoneNumbers, String email,
        //        //                   String address, AccountTypes accountTypes, Instant atCreated, Instant atUpdated)
        boolean isRetry = false;
        boolean isUsername = false;
        do {
            long id = System.currentTimeMillis()%100000000;
            String passWord = AppUtils.retryPassWordInput();
            String fullName = AppUtils.retryFullNameInput();
            String userName;
            do {
                userName = AppUtils.retryUserNameInput();
                if (accountServices.isExistUserName(userName)) {
                    System.out.println(">Tên đăng nhập đã tồn tại.");
                    isUsername = true;
                }else
                    isUsername = false;
            }while (isUsername);
            Instant atCreated = Instant.now();
            Instant atUpdated = Instant.now();
            Account account = new Account(id,userName,passWord,fullName,AccountTypes.USER,atCreated,atUpdated);
            System.out.printf("Bạn muốn cập nhật tài khoản %s\n",userName);
            if (AppUtils.isAcceptMenu()){
                accountServices.add(account);
                System.out.println("Đã cập nhật thông tin thành công.");
                isRetry = AppUtils.isRetry(Options.SHOW);
            }else
                isRetry = false;
        }while (isRetry);
    }
    public void edit(Account account){
        showAccountList(Options.EDIT);
        System.out.print("Nhập mã ID user: ");
        long userID = AppUtils.retryParseLongInput();
        if (accountServices.isExistObject(userID)){
            Account accountFind = accountServices.findObject(userID);
            if (accountFind.getAccountID() == account.getAccountID()){
                String password = passwordInputView();
                if (password !=null){
                    account.setPassWord(password);
                    accountServices.edit(account);
                    System.out.println("Đã cập nhật mật khẩu thành công.");
                    System.out.println("Ấn phím bất kì để đăng nhập lại.");
                    String anyPush = scanner.nextLine();
                    LoginViews.Laucher();
                }
            }else {
                String password = passwordInputView();
                if (password != null){
                    accountFind.setPassWord(password);
                    accountServices.edit(accountFind);
                    System.out.println("Đã cập nhật mật khẩu thành công.");
                }
            }
        }else {
            System.out.println(">ID không tồn tại. Kiểm tra lại.");
        }
    }
    private String passwordInputView(){
        System.out.print("Nhập mật khẩu cũ: ");
        String oldPassword = scanner.nextLine();
        if(accountServices.isExistPassword(oldPassword)){
            System.out.print("Nhập mật khẩu mới: ");
            String newPass1 = scanner.nextLine();
            System.out.print("Nhập lại mật khẩu mới: ");
            String newPass2 = scanner.nextLine();
            if (newPass1.equals(newPass2)){
                return newPass1;
            }else{
                System.out.print("Mật khẩu mới không đúng. Kiểm tra lại.");
            }
        }else{
            System.out.println("Mật khẩu không đúng. Kiểm tra lại.");
        }
        return null;
    }
    public void remove(){
        showAccountList(Options.REMOVE);
        System.out.print("Nhập mã ID user: ");
        long userID = AppUtils.retryParseLongInput();
        if (accountServices.isExistObject(userID)){
            Account account = accountServices.findObject(userID);
            if (account.getAccountTypes() == AccountTypes.ADMIN){
                System.out.println("Tài khoản này không thể xóa!");
            }else {
                showUserAccount(account);
                System.out.printf("Bạn muốn xóa tài khoản %s này?\n",account.getUserName());
                if (AppUtils.isAcceptMenu()){
                    accountServices.remove(account.getAccountID());
                    System.out.println("Đã xóa tài khoản thành công!");
                }
            }
        }else {
            System.out.println("Mã ID user không tồn tại. Kiểm tra lại.");
        }
    }
    public void find(){
        System.out.print("Nhập mã ID user: ");
        long userID = AppUtils.retryParseLongInput();
        if (accountServices.isExistObject(userID)){
            showUserAccount(accountServices.findObject(userID));
        }else{
            System.out.println(">Mã ID user không tồn tại!");
            System.out.println("Ấn phím bất kì để tiếp tục.");
            String anyPush = scanner.nextLine();
        }

    }
}
