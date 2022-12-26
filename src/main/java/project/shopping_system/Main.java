package project.shopping_system;

import project.shopping_system.models.AccountTypes;
import project.shopping_system.utils.AppUtils;
import project.shopping_system.utils.ValidateUtils;
import project.shopping_system.views.Options;
import project.shopping_system.views.order.OrderItemViews;
import project.shopping_system.views.order.OrderViewLauncher;
import project.shopping_system.views.order.OrderViews;
import project.shopping_system.views.product.ProductViewLauncher;

public class Main {
    public static void main(String[] args) {
//        Account account = new Account();
//        account.setAccountTypes(AccountTypes.USER);
//        ProductViewLauncher.productLaunchs(account);
//        AppUtils.acceptMenu();
//        AccountViewLauchers.lauch();
//        AccountViews accountViews = new AccountViews();
//        accountViews.showAccountList();
//        OrderViews orderViews = new OrderViews();
//        orderViews.orderActionsMenu(Options.ADD, 3175039);
        //orderViews.edit();
        OrderViewLauncher.oderLaunchs(AccountTypes.ADMIN);
    }
}
