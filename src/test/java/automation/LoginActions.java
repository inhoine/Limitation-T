package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import java.time.Duration;

public class LoginActions {
    private static final long TIMEOUT = 10;

    public static String login(WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
//        driver.get("https://ca-gosell.mediastep.ca");
        driver.get("https://ca-gosell.mediastep.ca/home");

        // Acc shop cũ
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("clone.zone.zick995@gmail.com");
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("1311518131Aa!");

        // Acc shop mới
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("EnterprisePlus1year@mailinator.com");
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("123456a@");

        wait.until(ExpectedConditions.elementToBeClickable(By.className("gs-button"))).click();
        System.out.println("I. Login with owner account Successfully!");
        Thread.sleep(3000);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return String.valueOf(jsExecutor.executeScript("return localStorage.getItem('accessToken')"));
    }

    public static boolean loginWithFullPermission(WebDriver driver, String username, String password) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
            driver.get("https://ca-gosell.mediastep.ca");
            WebElement staffButton = driver.findElement(By.xpath("//span[text()='Staff' or text()='Nhân viên']"));
            staffButton.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.uik-input__input[name='username']"))).sendKeys(username);
            // Sau khi nhập mật khẩu, gửi phím Enter
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.password.uik-input__input[name='password']")));
            passwordField.sendKeys(password + Keys.ENTER);

            System.out.println("b. Logged in with full permission limit feature.");
            // Do not close the browser here
            return true;
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            return false;
        }
    }

    public static void logout(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Log out') or contains(text(), 'Đăng xuất')]"))).click();
            System.out.println("Logged out successfully.");
        } catch (Exception e) {
            System.err.println("Logout failed: " + e.getMessage());
        }
    }
}
