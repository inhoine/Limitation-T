package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginActions {
    private static final long TIMEOUT = 10;

    public static String login(WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        driver.get("https://ca-gosell.mediastep.ca");

//        Acc shop cũ
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("clone.zone.zick995@gmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("1311518131Aa!");

//        Acc shop mới
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("bao.tran.quoc@gosell.vn");
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("Bao310194@");

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
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(username);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(password);
            wait.until(ExpectedConditions.elementToBeClickable(By.className("gs-button"))).click();
            System.out.println("b. Logged in with full permission limit feature.");
            return true;
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            return false;
        }
    }

    public static void logout(WebDriver driver) {
        try {
            driver.findElement(By.xpath("//span[contains(text(), 'Log out') or contains(text(), 'Đăng xuất')]")).click();
            System.out.println("a. Nếu là Shop cũ thì tiến thành logout owner account và tiến hành login account full permission limit feature.");
            System.out.println("Logged out successfully.");

        } catch (Exception e) {
            System.err.println("Logout failed: " + e.getMessage());
        }
    }
}
