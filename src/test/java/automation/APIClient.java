package automation;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileWriter;
import java.io.IOException;

public class APIClient {

    public static void fetchFeatureConfigurationsAndSave(String accessToken, String outputPath) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://ca-api.mediastep.ca/beehiveservices/api/feature-limitation-configs")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                // Write response to JSON file
                try (FileWriter fileWriter = new FileWriter(outputPath)) {
                    fileWriter.write(responseBody);
                }
            } else {
                System.out.println("Failed to fetch data from API: HTTP " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Error fetching API data: " + e.getMessage());
        }
    }
}
