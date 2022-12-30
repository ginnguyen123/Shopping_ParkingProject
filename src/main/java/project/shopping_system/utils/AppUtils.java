package project.shopping_system.utils;

import project.shopping_system.views.Options;

import java.time.Instant;
import java.util.Scanner;

public class AppUtils {
    //check nhập liệu theo form được sử dụng ở lơớp validate
    static Scanner scanner = new Scanner(System.in);
    public static boolean isAcceptMenu(){
        System.out.println("1.Đồng ý.");
        System.out.println("0.Quay lại.");
        System.out.print(">Chọn chức năng: ");

        do {
            int acceptChoice = retryParseIntInput();
            switch (acceptChoice){
                case 1:
                    return true;
                case 0:
                    return false;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra lại");
                    System.out.print("Nhập lại: ");
                    break;
            }
        }while (true);
    }
    public static long retryParseLongInput(){
        //parse kiểm tra thông báo nhập liệu ID kiểu long cho người dùng
        long result;
        do {
            try {
                result = Long.parseLong(scanner.nextLine());
                return result;
            }catch (Exception e){
                System.out.println("Nhập sai dữ liệu.Kiểm tra lại.");
                System.out.print("Nhập lại: ");
            }
        }while (true);
    }
    public static int retryParseIntInput(){
        int result;
        do {
            try {
                result = Integer.parseInt(scanner.nextLine());
                return result;
            }catch (Exception e){
                System.out.println("Nhập sai dữ liệu.Kiểm tra lại.");
                System.out.print("Nhập lại: ");
            }
        }while (true);
    }
    public static boolean isRetry(Options option){
        do {
            switch (option){
                case SHOW:
                    System.out.println(">Chọn 'b' - back để trở lại \t|\t 'e' - exit để thoát.");
                    break;
                case ADD:
                    System.out.println(">Chọn 'c' - continus để tiếp tục\t|\t 'b' - back để trở lại \t|\t 'e' - exit để thoát.");
                    break;
                case EDIT:
                    System.out.println(">Chọn 'c' - continus để tiếp tục\t|\t 'b' - back để trở lại \t|\t 'e' - exit để thoát.");
                    break;
                case FIND:
                    System.out.println(">Chọn 'c' - continus để tiếp tục\t|\t 'b' - back để trở lại \t|\t 'e' - exit để thoát.");
                    break;
                case REMOVE:
                    System.out.println(">Chọn 'c' - continus để tiếp tục\t|\t 'b' - back để trở lại \t|\t 'e' - exit để thoát.");
                    break;
                case SORT:
                    System.out.println(">Chọn 'c' - continus để tiếp tục\t|\t 'b' - back để trở lại \t|\t 'e' - exit để thoát.");
                    break;
                case STATISTICAL:
                    System.out.println(">Chọn 'c' - continus để tiếp tục\t|\t 'b' - back để trở lại \t|\t 'e' - exit để thoát.");
                    break;
                default:
                    throw new IllegalStateException("Giá trị không đúng: " +option);
            }
            System.out.print(">Chọn chức năng: ");
            String choice = scanner.nextLine();
            switch (choice){
                case "c":
                    return true;
                case "b":
                    return false;
                case "e":
                    System.out.println("Thoát chương trình.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Chọn sai chức năng. Kiểm tra và nhập lại");
                    break;
            }
        }while (true);
    }
    public static String retryString(String string){
        while (string.trim().isEmpty()){
            System.out.println("Chuỗi rỗng hoặc chỉ chứa khoảng trống!");
            System.out.print("Vui lòng nhập lại: ");
            string = scanner.nextLine();
        }
        return string;
    }
    public static String retryEmailInput(){
        String email;
        boolean isEmail;
        System.out.print("Nhập Email (theo định dang abcABC123._%+-@exp.com hoặc dang abcABC123._%+-@exp.vn: ");
        do {
            email = scanner.nextLine();
            retryString(email);
            if (!ValidateUtils.isEmailValid(email)){
                System.out.println("Nhập sai định dạng.");
                System.out.print("Nhập lại: ");
                isEmail = true;
            }else{
                isEmail = false;
                return email;
            }
        }while (isEmail);
        return null;
    }
    public static String retryStreetAdressInput(){
        String streetAdress;
        boolean isStreetAdress;
        System.out.println("Nhập địa chỉ (theo định dạng: số nhà tên đường, tên phường, tên quận, thành phố. Ví dụ: 12/32, To Huu, Thua Thien Hue).");
        do {
            streetAdress = scanner.nextLine();
            retryString(streetAdress);
            if (!ValidateUtils.isAddressValid(streetAdress)){
                System.out.println("Nhập sai định dạng.");
                System.out.print("Nhập lại: ");
                isStreetAdress = true;
            }else{
                return streetAdress;
            }
        }while (isStreetAdress);
        return null;
    }
    public static String retryPhonenumberInput(){
        String phonenumber;
        boolean isPhonenumber;
        System.out.print("Nhập số điện thoại (ví dụ:(84|0)(3|5|7|8|9)87654321: ");
        do {
            phonenumber = scanner.nextLine();
            retryString(phonenumber);
            if (!ValidateUtils.isPhoneValid(phonenumber)){
                System.out.println("Nhập sai định dạng.");
                System.out.print("Nhập lại: ");
                isPhonenumber = true;
            }else{
                return phonenumber;
            }
        }while (isPhonenumber);
        return null;
    }
    public static String retryUserNameInput(){
        String userName;
        boolean isUserName;
        System.out.print("Nhập tên đăng nhập: ");
        do {
            userName = scanner.nextLine();
            retryString(userName);
            if (!ValidateUtils.isUsernameValid(userName)){
                System.out.println("Nhập sai định dạng.");
                System.out.print("Nhập lại: ");
                isUserName = true;
            }else
                return userName;
        }while (isUserName);
        return null;
    }
    public static String retryPassWordInput(){
        String passwords;
        boolean isPasswords;
        System.out.print("Nhập mật khẩu (Mật khẩu có tối thiểu 8 kí tự, trong đó tối thiểu 1 số và 1 chữ hoa): ");
        do {
            passwords = scanner.nextLine();
            retryString(passwords);
            if (!ValidateUtils.isPassWordValid(passwords)){
                System.out.println("Nhập sai định dạng.");
                System.out.print("Nhập lại: ");
                isPasswords = true;
            }else
                return passwords;
        }while (isPasswords);
        return null;
    }
    public static String retryFullNameInput(){
        String fullName;
        boolean isFullName;
        System.out.print("Nhập họ và tên (Viết hoa chữ cái đầu tiên, ví dụ: Nguyễn Văn A): ");
        do {
            fullName = scanner.nextLine();
            retryString(fullName);
            if (!ValidateUtils.isFullNameValid(fullName)){
                System.out.println("Nhập sai định dạng.");
                System.out.print("Nhập lại: ");
                isFullName = true;
            }else
                return fullName;
        }while (isFullName);
        return null;
    }
    public static Instant retryDayInput(){
        String dates;
        boolean isDates;
        System.out.print("Nhập ngày (theo định dạng dd-mm-yyy): ");
        do {
            dates = scanner.nextLine();
            retryString(dates);
            if (!ValidateUtils.isDateValid(dates)){
                System.out.println("Nhập sai định dạng.");
                System.out.print("Nhập lại: ");
                isDates = true;
            }else{
                String[] arrDay = dates.split("-");
                if (arrDay[0].length() == 1)
                    arrDay[0] = "0" + arrDay[0];
                if (arrDay[1].length() == 1)
                    arrDay[1] = "0" + arrDay[1];
                dates = arrDay[0] + "-" + arrDay[1] + "-" + arrDay[2];
                Instant instant = DateTimeUtil.formatStringToInstanAsDay(dates);
                return instant;
            }
        }while (isDates);
        return null;
    }
    public static Instant retryMonthInput(){
        String months;
        boolean isMonths;
        System.out.print("Nhập tháng (theo định dạng mm-yyyy): ");
        do {
            months = scanner.nextLine();
            retryString(months);
            if (!ValidateUtils.isMonthValid(months)){
                System.out.println("Nhập sai định dạng.");
                System.out.print("Nhập lại: ");
                isMonths = true;
            }else{
                String[] arrMonth = months.split("-");
                    if (arrMonth[0].length() == 1){
                        arrMonth[0] = "0" + arrMonth[0];
                        months = arrMonth[0] + "-" + arrMonth[1];
                    }
                    Instant instant = DateTimeUtil.formatStringToInstanAsMonth(months);
                    return instant;
            }
        }while (isMonths);
        return null;
    }
    public static Instant retryYearInput(){
        String years;
        boolean isYears;
        System.out.print("Nhập năm (theo định dạng yyyy): ");
        do {
            years = scanner.nextLine();
            retryString(years);
            if (!ValidateUtils.isYearValid(years)){
                System.out.println("Nhập sai định dạng.");
                System.out.print("Nhập lại: ");
                isYears = true;
            }else{
                Instant instant = DateTimeUtil.formatStringToInstanAsYear(years);
                return instant;
            }
        }while (isYears);
        return null;
    }

}
