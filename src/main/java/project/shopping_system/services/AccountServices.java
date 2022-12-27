package project.shopping_system.services;

import project.shopping_system.models.Account;
import project.shopping_system.models.AccountTypes;
import project.shopping_system.utils.IOFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AccountServices implements AbstractServices<Account> {
    private static String pathFile = "src/main/java/project/shopping_system/data/accounts.csv";
    private static AccountServices instance;
    public AccountServices(){}
    public static AccountServices getInstance(){
        if (instance == null){
            instance = new AccountServices();
        }
        return instance;
    }

    public static List<Account> findAll() {
        List<String> stringList = IOFile.readFile(pathFile);
        List<Account> accountList = new ArrayList<>();
        for (String stringAccount : stringList){
            accountList.add(Account.parseAccount(stringAccount));
        }
        return accountList;
    }


    public Account login(String username, String password){
        List<Account> accountList = findAll();
        try {
            for (Account account : accountList){
                if (account.getUserName().equals(username) &&
                        account.getPassWord().equals(password)){
                    return account;
                }
            }
        }catch (NullPointerException nullPointerException){
            return null;
        }
        return null;
    }
    public boolean isLogin(String username, String password){
        List<Account> accountList = findAll();
        for (Account account : accountList){
            if (account.getUserName().equals(username) &&
                    account.getPassWord().equals(password)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(Account newAccount) {
        List<Account> accountList = findAll();
        accountList.add(newAccount);
        IOFile.writeFile(accountList,pathFile);
    }


    @Override
    public void edit(Account account) {
        List<Account> accountList = findAll();
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
//                String phoneNumbers = account.getPhoneNumbers();
//                if (phoneNumbers!=null && !phoneNumbers.trim().isEmpty())
//                    oldAccount.setPhoneNumbers(phoneNumbers);
//                String email = account.getEmail();
//                if (email!=null && !email.trim().isEmpty())
//                    oldAccount.setEmail(email);
//                String address = account.getAddress();
//                if (address!=null && !address.trim().isEmpty())
//                    oldAccount.setAddress(address);
                oldAccount.setAtUpdated(atUpdated);
                break;
            }
        }
        IOFile.writeFile(accountList,pathFile);
    }

    @Override
    public void remove(long accountID) {
        List<Account> accountList = findAll();
        for (Account account : accountList){
            accountList.remove(account);
        }
        IOFile.writeFile(accountList,pathFile);
    }

    @Override
    public Account findObject(long id) {
        List<Account> accountList = findAll();
        for (Account account : accountList){
            if (account.getAccountID() == id){
                return account;
            }
        }
        return null;
    }

    @Override
    public boolean isExistObject(long id) {
        List<Account> accountList = findAll();
        for (Account account : accountList){
            if (account.getAccountID() == id){
                return true;
            }
        }
        return false;
    }
    public boolean isExistPassword(String password){
        List<Account> accountList = findAll();
        for (Account account : accountList){
            if (account.getPassWord().equals(password))
                return true;
        }
        return false;
    }
    public boolean isExistUserName(String username){
        List<Account> accountList = findAll();
        for (Account account : accountList){
            if (account.getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Account> sortByNameAToZ() {
        List<Account> accountList = findAll();
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
        List<Account> accountList = findAll();
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
