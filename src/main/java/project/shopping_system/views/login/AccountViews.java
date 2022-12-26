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
    public void showAccountList(){
        //(long accountID, String userName, String passWord, String fullName, String phoneNumbers, String email,
        //                   String address, AccountTypes accountTypes, Instant atCreated, Instant atUpdated)
        System.out.printf("|%-8s| %-16s| %-16s| %-16s| %-5s| %-16s| %-16s|\n","ID","User name","Full name","Password"
                ,"Account Types", "At created", "At updated");
        for (Account account : AccountServices.findAll()){
            System.out.printf("|%-8s| %-16s| %-16s| %-16s| %-5s| %-16s| %-16s|\n",account.getAccountID(),account.getUserName(),
                    account.getFullName(),account.getPassWord() ,account.getAccountTypes(),
                    DateTimeUtil.formatIntanceToString(account.getAtCreated()),DateTimeUtil.formatIntanceToString(account.getAtUpdated()));
        }
    }
    public void addUser(){
        // //(long accountID, String userName, String passWord, String fullName, String phoneNumbers, String email,
        //        //                   String address, AccountTypes accountTypes, Instant atCreated, Instant atUpdated)
        boolean isRetry = false;
        do {
            long id = System.currentTimeMillis()%100000000;
            String userName = AppUtils.retryUserNameInput();
            String passWord = AppUtils.retryPassWordInput();
            String fullName = AppUtils.retryFullNameInput();
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
    public void editAdmin(){
        System.out.print("Nhập mã ID tài khoản: ");
        long userID = Long.parseLong(scanner.nextLine());
        if (accountServices.isExistObject(userID)){
            Account account = accountServices.findObject(userID);

        }else {}
    }
}
