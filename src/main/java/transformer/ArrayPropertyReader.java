package transformer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ArrayPropertyReader {
    static ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) {
        String jsonObject = "{\n" +
                "\t\"mfa_factors\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"totp\": {\n" +
                "\t\t\t\t\"secret\": \"36PW2G5\"\n" +
                "\t\t\t}\n" +
                "\t\t}, {\n" +
                "\t\t\t\"phone\": {\n" +
                "\t\t\t\t\"value\": \"+2518\"\n" +
                "\t\t\t}\n" +
                "\t\t}, {\n" +
                "\t\t\t\"totp\": {\n" +
                "\t\t\t\t\"secret\": \"QS2\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonObject);
            System.out.println(rootNode.toPrettyString());
            Iterator<Map.Entry<String, JsonNode>> properties = rootNode.fields();

            while (properties.hasNext()) {
                Map.Entry<String, JsonNode> property = properties.next();
                String propertyName = property.getKey();
                JsonNode dataArray = rootNode.get(propertyName);
                if (dataArray != null && dataArray.isArray()) {
                    for (JsonNode item : dataArray) {
                        System.out.println(item);
                        printNodeDetails(item);
                    }
                } else {
                    System.out.println("The 'data' field does not contain a JSON array.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error while parsing JSON object: " + e.getMessage());
        }
    }
    private static void printNodeDetails(JsonNode node) {
        if (node.isObject()) {
            System.out.println("Node type: Object");
        } else if (node.isTextual()) {
            System.out.println("Node type: String");
        } else if (node.isBoolean()) {
            System.out.println("Node type: Boolean");
        } else if (node.isNumber()) {
            System.out.println("Node type: Number");
        } else {
            System.out.println("Node type: Unknown");
        }
    }
}





