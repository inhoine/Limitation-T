    package automation;

    import com.fasterxml.jackson.databind.JsonNode;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import java.io.File;
    import java.io.IOException;
    import java.util.Iterator;
    import java.util.Map;

    public class DataComparer {

        // Returns true if data is the same, false otherwise
        public static boolean compareJson(String jsonPath1, String jsonPath2) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode1 = mapper.readTree(new File(jsonPath1));
            JsonNode jsonNode2 = mapper.readTree(new File(jsonPath2));
            return compareJsonNodes(jsonNode1, jsonNode2, "");
        }

        private static boolean compareJsonNodes(JsonNode node1, JsonNode node2, String path) {
            if (node1.equals(node2)) {
                return true;
            }

            boolean allMatch = true;
            if (node1.isObject()) {
                Iterator<Map.Entry<String, JsonNode>> fields = node1.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    String newPath = path.isEmpty() ? entry.getKey() : path + "." + entry.getKey();
                    if (node2.has(entry.getKey())) {
                        allMatch &= compareJsonNodes(entry.getValue(), node2.get(entry.getKey()), newPath);
                    } else {
                        System.out.println("Missing in second file: " + newPath + " in first file with value: " + entry.getValue());
                        allMatch = false;
                    }
                }
                fields = node2.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    String newPath = path.isEmpty() ? entry.getKey() : path + "." + entry.getKey();
                    if (!node1.has(entry.getKey())) {
                        System.out.println("Missing in first file: " + newPath + " in second file with value: " + entry.getValue());
                        allMatch = false;
                    }
                }
            } else if (!node1.equals(node2)) {
                System.out.println("Difference at " + path + ": " + node1.asText() + " vs " + node2.asText());
                allMatch = false;
            }
            return allMatch;
        }
    }
