package project.shopping_system.models;

import java.time.Instant;

public class Account {
    private long accountID;
    private String userName;
    private String passWord;
    private String fullName;
//    private String phoneNumbers;
//    private String email;
//    private String address;
    private AccountTypes accountTypes;
    private Instant atCreated;
    private Instant atUpdated;

    public Account() {
    }

    public Account(long accountID, String userName, String passWord, String fullName, AccountTypes accountTypes, Instant atCreated, Instant atUpdated) {
        this.accountID = accountID;
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
//        this.phoneNumbers = phoneNumbers;
//        this.email = email;
//        this.address = address;
        this.accountTypes = accountTypes;
        this.atCreated = atCreated;
        this.atUpdated = atUpdated;
    }
    public static Account parseAccount(String raw){
        String[] fields = raw.split(",");
        //(long accountID, String userName, String passWord, String fullName, String phoneNumbers, String email,
        //                   String address, AccountTypes accountTypes, Instant atCreated, Instant atUpdated)
        long accountID = Long.parseLong(fields[0]);
        String userName = fields[1];
        String passWord = fields[2];
        String fullName = fields[3];
//        String phoneNumbers = fields[4];
//        String email = fields[5];
//        String address = fields[6];
        AccountTypes accountTypes = AccountTypes.getAccountTypes(fields[4]);
        Instant atCreated = Instant.parse(fields[5]);
        Instant atUpdated = Instant.parse(fields[6]);
        return new Account(accountID, userName, passWord,fullName,accountTypes,atCreated,atUpdated);
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

//    public String getPhoneNumbers() {
//        return phoneNumbers;
//    }
//
//    public void setPhoneNumbers(String phoneNumbers) {
//        this.phoneNumbers = phoneNumbers;
//    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }

    public AccountTypes getAccountTypes() {
        return accountTypes;
    }

    public void setAccountTypes(AccountTypes accountTypes) {
        this.accountTypes = accountTypes;
    }

    public Instant getAtCreated() {
        return atCreated;
    }

    public void setAtCreated(Instant atCreated) {
        this.atCreated = atCreated;
    }

    public Instant getAtUpdated() {
        return atUpdated;
    }

    public void setAtUpdated(Instant atUpdated) {
        this.atUpdated = atUpdated;
    }

    @Override
    public String toString() {
        //(long accountID, String userName, String passWord, String fullName, String phoneNumbers, String email,
        //                   String address, AccountTypes accountTypes, Instant atCreated, Instant atUpdated)
        return String.format("%d,%s,%s,%s,%s,%s,%s\n",
                accountID,userName,passWord,fullName,accountTypes.getValue(),atCreated,atUpdated);
    }
}
