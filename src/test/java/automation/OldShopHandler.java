package automation;

import org.openqa.selenium.WebDriver;

public class OldShopHandler {

    public static void handleOldShopScenario(WebDriver driver) {
        System.out.println("");
        System.out.println("II. Shop này là shop cũ.");
        LoginActions.logout(driver);
        boolean loginSuccess = LoginActions.loginWithFullPermission(driver, "stafffullquyen@mailinator.com", "Bao310194@");
        if (loginSuccess) {
            System.out.println("Đăng nhập thành công.");
        } else {
            System.out.println("Đăng nhập thất bại.");
        }
    }
}
