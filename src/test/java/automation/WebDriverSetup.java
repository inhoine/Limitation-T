package automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WebDriverSetup {
    private static final long TIMEOUT = 10;
    public static WebDriver setupDriver() {
        System.setProperty("chrome.driver", "/Users/tranbao/Downloads");
        WebDriver driver = new ChromeDriver();
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        driver.manage().window().maximize();
        driver.get("https://ca-gosell.mediastep.ca");
        return driver;
    }
}
