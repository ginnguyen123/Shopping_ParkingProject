package project.shopping_system.services;

import project.shopping_system.models.Account;
import project.shopping_system.models.Product;
import project.shopping_system.utils.IOFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AccountServices implements AbstractServices<Account> {
    private static String pathFile = "src/main/java/project/shopping_system/data/accounts.csv";
    private static AccountServices instance;
    private static List<Account> currentList;
    public AccountServices(){}
    public static AccountServices getInstance(){
        if (instance == null){
            instance = new AccountServices();
        }
        return instance;
    }

    static List<Account> findAll() {
        List<String> stringList = IOFile.readFile(pathFile);
        List<Account> accountList = new ArrayList<>();
        for (String stringAccount : stringList){
            accountList.add(Account.parseAccount(stringAccount));
        }
        return accountList;
    }
    static {
        currentList = findAll();
    }

    public static List<Account> getCurrentList() {
        return currentList;
    }

    public static void setCurrentList(List<Account> currentList) {
        AccountServices.currentList = currentList;
    }

    @Override
    public void add(Account newAccount) {
        List<Account> accountList = currentList;
        accountList.add(newAccount);
        IOFile.writeFile(accountList,pathFile);
    }

    @Override
    public void edit(Account account) {
        List<Account> accountList = currentList;
        for (Account oldAccount : accountList){
            if (oldAccount.getAccountID() == account.getAccountID()){
                Instant atUpdated = Instant.now();
                //(long accountID, String userName, String passWord, String fullName, String phoneNumbers, String email,
                //                   String address, AccountTypes accountTypes, Instant atCreated, Instant atUpdated)
                String passWord = account.getPassWord();
                if (passWord!=null && !passWord.trim().isEmpty())
                    oldAccount.setPassWord(passWord);
                String fullName = account.getFullName();
                if (fullName!=null && !fullName.trim().isEmpty())
                    oldAccount.setFullName(fullName);
                String phoneNumbers = account.getPhoneNumbers();
                if (phoneNumbers!=null && !phoneNumbers.trim().isEmpty())
                    oldAccount.setPhoneNumbers(phoneNumbers);
                String email = account.getEmail();
                if (email!=null && !email.trim().isEmpty())
                    oldAccount.setEmail(email);
                String address = account.getAddress();
                if (address!=null && !address.trim().isEmpty())
                    oldAccount.setAddress(address);
                oldAccount.setAtUpdated(atUpdated);
                break;
            }
        }
    }

    @Override
    public void remove(Account account) {
        List<Account> accountList = currentList;
        accountList.remove(account);
        IOFile.writeFile(accountList,pathFile);
    }

    @Override
    public Account findObject(long id) {
        List<Account> accountList = currentList;
        for (Account account : accountList){
            if (account.getAccountID() == id){
                return account;
            }
        }
        return null;
    }

    @Override
    public boolean isExistObject(long id) {
        List<Account> accountList = currentList;
        for (Account account : accountList){
            if (account.getAccountID() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Account> sortByNameAToZ() {
        List<Account> accountList = currentList;
        Collections.sort(accountList, new Comparator<Account>() {
            @Override
            public int compare(Account account1, Account account2) {
                if (account1.getFullName().compareTo(account2.getFullName())>0){
                    return 1;
                } else if (account1.getFullName().compareTo(account2.getFullName())==0) {
                    return 0;
                }else
                    return -1;
            }
        });
        return accountList;
    }

    @Override
    public List<Account> sortByNameZToA() {
        List<Account> accountList = currentList;
        Collections.sort(accountList, new Comparator<Account>() {
            @Override
            public int compare(Account account1, Account account2) {
                if (account1.getFullName().compareTo(account2.getFullName())<0){
                    return 1;
                } else if (account1.getFullName().compareTo(account2.getFullName())==0) {
                    return 0;
                }else
                    return -1;
            }
        });
        return accountList;
    }
}
