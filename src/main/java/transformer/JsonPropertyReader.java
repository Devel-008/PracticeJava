package transformer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonPropertyReader {

    public static void main(String[] args) throws IOException {
        String jsonString = "{\"mfa_factors\": [{\"totp\": {\"secret\": \"36PW2G5\"}}, {\"phone\": {\"value\": \"+2518\"}}, {\"totp\": {\"secret\": \"QS2\"}}]}";

        // Read the JSON object properties and their types
        readObjectProperties(jsonString);
    }

    public static void readObjectProperties(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonString);

        if (jsonNode.isObject()) {
            for (JsonNode property : jsonNode) {
                String propertyName = property.fieldNames().next();
                JsonNode propertyValue = property.get(propertyName);
                System.out.println("Property: " + propertyName + ", Type: " + propertyValue.getNodeType());
            }
        }
    }
}

