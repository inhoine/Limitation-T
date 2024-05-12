package automation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonFileManager {
    public static void printSpecificKeysAndValuesFromJsonFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new File(filePath));

        String bundleFeatureName = jsonNode.has("bundleFeatureName") ? jsonNode.get("bundleFeatureName").asText("Unavailable") : "Không có";
        String bundleFeatureCode = jsonNode.has("bundleFeatureCode") ? jsonNode.get("bundleFeatureCode").asText("Unavailable") : "Không có";
        int yearsOfFeatureLimitation = jsonNode.has("yearsOfFeatureLimitation") ? jsonNode.get("yearsOfFeatureLimitation").asInt(-1) : -1;
        String labelFeatureLimitation = jsonNode.has("labelFeatureLimitation") ? jsonNode.get("labelFeatureLimitation").asText("Không có") : "Unavailable";

        // Kiểm tra nếu không phải là shop cũ thì mới in thông tin
        if (!(bundleFeatureName.equals("Không có") && bundleFeatureCode.equals("Không có") && yearsOfFeatureLimitation == -1 && labelFeatureLimitation.equals("Unavailable"))) {
            System.out.println("b. Các key và value từ file infoPackageShop:");
            System.out.println("bundleFeatureName: " + bundleFeatureName);
            System.out.println("bundleFeatureCode: " + bundleFeatureCode);
            System.out.println("yearsOfFeatureLimitation: " + yearsOfFeatureLimitation);
            System.out.println("labelFeatureLimitation: " + labelFeatureLimitation);
            System.out.println("");
        }
    }
    public static void updateLocalStoreJson(String key, String value, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);
        ObjectNode jsonNode = file.exists() ? (ObjectNode) mapper.readTree(file) : mapper.createObjectNode();

        jsonNode.put(key, value);
        mapper.writeValue(file, jsonNode);
        System.out.println("Updated " + key + " in " + filePath);
    }
    public static boolean areFilesReady(String... filePaths) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (!file.exists() || file.length() == 0) {
                System.out.println("File not ready: " + filePath);
                return false; // File does not exist or is empty
            }
            // Optionally check if the file contains valid JSON
            try {
                JsonNode content = mapper.readTree(file);
                if (content == null || content.isEmpty()) {
                    System.out.println("JSON is empty in file: " + filePath);
                    return false;
                }
            } catch (IOException e) {
                System.out.println("Error reading JSON from file: " + filePath);
                return false;
            }
        }
        return true;
    }
    public static boolean isJsonNotEmpty(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);
        JsonNode jsonNode = mapper.readTree(file);
        return jsonNode != null && jsonNode.size() > 0;
    }


    public static void saveToJsonFile(LinkedHashMap<String, LinkedHashMap<String, String>> data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("/Users/tranbao/limitationProject/src/test/java/files/Database.json");
        file.getParentFile().mkdirs();  // Đảm bảo thư mục tồn tại
        mapper.writeValue(file, data);
        System.out.println("b. Dữ liệu đã được lưu vào file Database.json thành công!");
    }

    public static void sortAndSaveJsonFile(String inputFilePath, String outputFilePath) throws IOException {
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            System.err.println("File không tồn tại: " + inputFilePath);
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, LinkedHashMap<String, String>> data = mapper.readValue(inputFile, LinkedHashMap.class);
        LinkedHashMap<String, LinkedHashMap<String, String>> sortedData = new LinkedHashMap<>();

        data.keySet().stream().sorted().forEach(key -> {
            LinkedHashMap<String, String> sortedSubData = new LinkedHashMap<>();
            data.get(key).entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEachOrdered(x -> sortedSubData.put(x.getKey(), x.getValue()));
            sortedData.put(key, sortedSubData);
        });

        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.writeValue(new File(outputFilePath), sortedData);
    }

    public static boolean checkMissingKeys(String filePath, String... keys) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File không tồn tại: " + filePath);
            return false;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(file);
        for (String key : keys) {
            if (!jsonNode.has(key)) {
                return true;
            }
        }
        return false;
    }
}
