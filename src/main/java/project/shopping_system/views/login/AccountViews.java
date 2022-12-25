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
        System.out.printf("|%-8s| %-16s| %-16s| %-10s| %-16s| %-16s| %-5s| %-16s| %-16s|\n","ID","User name","Full name",
                "Phone numbers","Email","Address","Account Types", "At created", "At updated");
        for (Account account : AccountServices.findAll()){
            System.out.printf("|%-8s| %-16s| %-16s| %-10s| %-16s| %-16s| %-5s| %-16s| %-16s|\n",account.getAccountID(),account.getUserName(),account.getFullName(),
                    account.getPhoneNumbers(),account.getEmail(),account.getAddress(),account.getAccountTypes(),
                    DateTimeUtil.formatIntanceToString(account.getAtCreated()),DateTimeUtil.formatIntanceToString(account.getAtUpdated()));
        }
        System.out.println("Nhập phím bất kì để tiếp tục.");
        scanner.nextLine();
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
            String phoneNumbers = AppUtils.retryPhonenumberInput();
            String email = AppUtils.retryEmailInput();
            String address = AppUtils.retryStreetAdressInput();
            Instant atCreated = Instant.now();
            Instant atUpdated = Instant.now();
            Account account = new Account(id,userName,passWord,fullName,phoneNumbers,email,address, AccountTypes.USER,atCreated,atUpdated);
            System.out.printf("Bạn muốn cập nhật tài khoản %s\n",userName);
            if (AppUtils.isAcceptMenu()){
                accountServices.add(account);
                System.out.println("Đã cập nhật thông tin thành công.");
                isRetry = AppUtils.isRetry(Options.FIND);
            }else
                isRetry = AppUtils.isRetry(Options.SHOW);
        }while (isRetry);
    }
}