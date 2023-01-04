package project.shopping_system;

import project.shopping_system.models.AccountTypes;
import project.shopping_system.models.Order;
import project.shopping_system.models.Product;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static javafx.scene.input.KeyCode.Z;

public class Main {
    public static void main(String[] args) {
    LoginViews.Laucher();
//        Instant instant = Instant.now();
//        System.out.println(instant);
//        Instant year = AppUtils.retryYearInput();
//        System.out.println(year);
//        String strYear = DateTimeUtil.formatYearIntanceToString(year);
//        System.out.println(strYear);
    }
}
