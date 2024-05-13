package automation;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ShopPackage {
    private WebDriver driver;
    private Connection conn;

    @BeforeClass
    public void setUp() throws SQLException {
        // Thiết lập WebDriver và kết nối cơ sở dữ liệu trước khi thực hiện các test
        driver = WebDriverSetup.setupDriver();
        conn = DatabaseConnection.connectToDatabase();
    }

    @Test
    public void testShopFunctions() {
        try {
            String accessToken = LoginActions.login(driver);
            String tokenParsed = TokenManager.decodeToken(accessToken);
            TokenManager.writeTokenParseToFile(tokenParsed, "src/test/java/files/infoPackageShop.json");
            JsonFileManager.printSpecificKeysAndValuesFromJsonFile("src/test/java/files/infoPackageShop.json");

            if (JsonFileManager.checkMissingKeys("src/test/java/files/infoPackageShop.json", "nextExpiryPackageTime", "bundleFeatureName", "bundleFeatureCode")) {
                OldShopHandler.handleOldShopScenario(driver);
            }

            // Lấy giới hạn tính năng từ Local Storage
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            String featureLimit = (String) jsExecutor.executeScript("return window.localStorage.getItem('featureLimit');");

            // Chuyển đổi Excel sang JSON và sắp xếp
            String excelPath = "src/file/GoSELL Pricing.xlsx";
            String excelJsonPath = "src/test/java/files/Excel.json";
            ExcelToJsonConverter.convertExcelToJson(excelPath, excelJsonPath);

            // Tải dữ liệu API và lưu vào Api.json
            String apiJsonPath = "src/test/java/files/Api.json";
            APIClient.fetchFeatureConfigurationsAndSave(accessToken, apiJsonPath);

            // Viết giới hạn tính năng vào LocalStore.json
            LocalFileWriter.writeLocalToFile(featureLimit, "src/test/java/files/LocalStore.json");

            // Đảm bảo tất cả các file JSON không trống và thực hiện so sánh
            String databaseJsonPath = "src/test/java/files/Database.json";
            String localStoreJsonPath = "src/test/java/files/LocalStore.json";
            if (JsonFileManager.areFilesReady(excelJsonPath, databaseJsonPath, apiJsonPath, localStoreJsonPath)) {
                // So sánh Excel.json với Database.json
                if (DataComparer.compareJson(excelJsonPath, databaseJsonPath)) {
                    System.out.println("II. So sánh dữ liệu với các file.");
                    System.out.println("1. Dữ liệu từ Excel.json và Database.json khớp.");

                    // So sánh Database.json với Api.json
                    if (DataComparer.compareJson(databaseJsonPath, apiJsonPath)) {
                        System.out.println("2. Dữ liệu từ Database.json và Api.json khớp.");

                        // So sánh Api.json với LocalStore.json
                        if (DataComparer.compareJson(apiJsonPath, localStoreJsonPath)) {
                            System.out.println("3. Dữ liệu từ Api.json và LocalStore.json khớp.");
                        } else {
                            System.out.println("3. Dữ liệu từ Api.json và LocalStore.json không khớp.");
                        }
                    } else {
                        System.out.println("2. Dữ liệu từ Database.json và Api.json không khớp.");
                    }
                } else {
                    System.out.println("II. So sánh dữ liệu với các file.");
                    System.out.println("1. Dữ liệu từ Excel.json và Database.json không khớp.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        // Dọn dẹp tài nguyên
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (driver != null) {
            driver.quit();
        }
    }
}
