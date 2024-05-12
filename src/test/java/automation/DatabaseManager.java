package automation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.io.IOException;

public class DatabaseManager {

    public static void fetchAndSaveFeatureLimitation(Connection connection) throws SQLException, IOException {
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = "SELECT feature_code, label, value_config FROM \"beehive-services\".\"feature_limitation_config\"";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            LinkedHashMap<String, LinkedHashMap<String, String>> featureLimitationMap = new LinkedHashMap<>();

            while (resultSet.next()) {
                String featureCode = resultSet.getString("feature_code");
                String label = resultSet.getString("label");
                String valueConfig = resultSet.getString("value_config");

                featureLimitationMap.putIfAbsent(featureCode, new LinkedHashMap<>());
                featureLimitationMap.get(featureCode).put(label, valueConfig);
            }

            JsonFileManager.saveToJsonFile(featureLimitationMap);  // Assuming JsonFileManager can handle this
        }
    }
}
