package automation;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.sql.Connection;

public class ShopPackage {
    public static void main(String[] args) {
        WebDriver driver = null;
        Connection conn = null;
        try {
            driver = WebDriverSetup.setupDriver();
            String accessToken = LoginActions.login(driver);
            String tokenParsed = TokenManager.decodeToken(accessToken);
            TokenManager.writeTokenParseToFile(tokenParsed, "src/test/java/files/infoPackageShop.json");
            JsonFileManager.printSpecificKeysAndValuesFromJsonFile("src/test/java/files/infoPackageShop.json");

            if (JsonFileManager.checkMissingKeys("src/test/java/files/infoPackageShop.json", "nextExpiryPackageTime", "bundleFeatureName", "bundleFeatureCode")) {
                OldShopHandler.handleOldShopScenario(driver);
            }

            // Get featureLimit from Local Storage
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            String featureLimit = (String) jsExecutor.executeScript("return window.localStorage.getItem('featureLimit');");

            // Convert Excel to JSON and sort it
            String excelPath = "src/file/GoSELL Pricing.xlsx";
            String excelJsonPath = "src/test/java/files/Excel.json";
            ExcelToJsonConverter.convertExcelToJson(excelPath, excelJsonPath);

            // Fetch API data and save it to Api.json
            String apiJsonPath = "src/test/java/files/Api.json";
            APIClient.fetchFeatureConfigurationsAndSave(accessToken, apiJsonPath);

            // Write featureLimit to LocalStore.json
            LocalFileWriter.writeLocalToFile(featureLimit, "src/test/java/files/LocalStore.json");
            // Ensure all JSON files are not empty and then perform comparisons
            String databaseJsonPath = "src/test/java/files/Database.json";
            String localStoreJsonPath = "/src/test/java/files/LocalStore.json";
            if (JsonFileManager.areFilesReady(excelJsonPath, databaseJsonPath, apiJsonPath, localStoreJsonPath)) {
                // Compare Excel.json with Database.json
                if (DataComparer.compareJson(excelJsonPath, databaseJsonPath)) {
                    System.out.println("II. Compare data with files.");
                    System.out.println("1. Data from Excel.json and Database.json match.");

                    // Compare Database.json with Api.json
                    if (DataComparer.compareJson(databaseJsonPath, apiJsonPath)) {
                        System.out.println("2. Data from Database.json and Api.json match.");

                        // Compare Api.json with LocalStore.json
                        if (DataComparer.compareJson(apiJsonPath, localStoreJsonPath)) {
                            System.out.println("3. Data from Api.json and LocalStore.json match.");
                        } else {
                            System.out.println("3. Data from Api.json and LocalStore.json do not match.");
                        }
                    } else {
                        System.out.println("2. Data from Database.json and Api.json do not match.");
                    }
                } else {
                    System.out.println("II. Compare data with files.");
                    System.out.println("1. Data from Excel.json and Database.json do not match.");
                }
            }

            conn = DatabaseConnection.connectToDatabase();
            DatabaseManager.fetchAndSaveFeatureLimitation(conn);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
}
