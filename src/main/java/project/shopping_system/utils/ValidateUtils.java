package project.shopping_system.utils;

import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    public static final String FULL_NAME = "^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
    public static final String PHONE_NUMBER = "^(0|84)(3|5|7|8|9)([0987654321]{8})$";
    public static final String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$";
    public static final String ADRESS = "^([a-zA-Z0-9\\s;./]){1,}$";
    private static final String DATE_PATTERN = "^(0?[1-9]|[12][0-9]|3[01])[\\-](0?[1-9]|1[012])[\\-]\\d{4}$";
    private static final String MONTH_PATTERN = "^(0?[1-9]|1[012])[\\-]\\d{4}$";
    private static final String YEAR_PATTERN = "\\d{4}$";

    public static boolean isUsernameValid(String userName) {
        return Pattern.compile(USERNAME_PATTERN).matcher(userName).matches();
    }
    public static boolean isPassWordValid(String password) {
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }
    public static boolean isEmailValid(String email) {
        return Pattern.compile(EMAIL).matcher(email).matches();
    }
    public static boolean isPhoneValid(String phone) {
        return Pattern.compile(PHONE_NUMBER).matcher(phone).matches();
    }
    public static boolean isAddressValid(String address) {
        return Pattern.compile(ADRESS).matcher(address).matches();
    }
    public static boolean isDateValid(String day) {
        return Pattern.compile(DATE_PATTERN).matcher(day).matches();
    }
    public static boolean isMonthValid(String month) {
        return Pattern.compile(MONTH_PATTERN).matcher(month).matches();
    }
    public static boolean isYearValid(String year) {
        return Pattern.compile(YEAR_PATTERN).matcher(year).matches();
    }
    public static boolean isFullNameValid(String fullName) {
        return Pattern.compile(FULL_NAME).matcher(fullName).matches();
    }
}
