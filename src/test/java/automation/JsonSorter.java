package automation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class JsonSorter {

    public static void sortJson(String sourceJsonPath, String templateJsonPath, String outputJsonPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode sourceJson = mapper.readTree(new File(sourceJsonPath));
        JsonNode templateJson = mapper.readTree(new File(templateJsonPath));

        JsonNode sortedJson = sortJsonByTemplate(sourceJson, templateJson);

        mapper.writeValue(new File(outputJsonPath), sortedJson);
    }

    private static JsonNode sortJsonByTemplate(JsonNode source, JsonNode template) {
        ObjectNode sortedJson = new ObjectMapper().createObjectNode();
        template.fieldNames().forEachRemaining(key -> {
            if (source.has(key)) {
                if (template.get(key).isContainerNode() && source.get(key).isContainerNode()) {
                    sortedJson.set(key, sortJsonByTemplate(source.get(key), template.get(key)));
                } else {
                    sortedJson.set(key, source.get(key));
                }
            }
        });
        return sortedJson;
    }
}
