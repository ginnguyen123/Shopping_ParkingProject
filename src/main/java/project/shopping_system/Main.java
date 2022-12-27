package project.shopping_system;

import project.shopping_system.models.AccountTypes;
import project.shopping_system.utils.AppUtils;
import project.shopping_system.utils.DateTimeUtil;
import project.shopping_system.utils.ValidateUtils;
import project.shopping_system.views.Options;
import project.shopping_system.views.login.LoginViews;
import project.shopping_system.views.order.OrderItemViews;
import project.shopping_system.views.order.OrderViewLauncher;
import project.shopping_system.views.order.OrderViews;
import project.shopping_system.views.product.ProductViewLauncher;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
//    LoginViews.Laucher();
        Instant testTime = Instant.now();
        String stringInstant = DateTimeUtil.formatIntanceToString(testTime);
        System.out.println(testTime);
        System.out.println("...........");
        System.out.println(stringInstant);
        String year = stringInstant.split(" ")[1];
        System.out.println(year);
        System.out.println("...........");
        String[] time = year.split("-");
        String day = time[0];
        String month = time[1];
        String years = time[2];
        System.out.println("ngày " + day);
        System.out.println("tháng " + month);
        System.out.println("năm " + years);

    }
}
