package json;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtility {

    static ObjectMapper objectMapper = new ObjectMapper();
    static Logger logger = LoggerFactory.getLogger(JsonUtility.class);

    public static JsonNode convertToJson(Iterator<List<Map<String, Object>>> data, JsonNode jsonSchema) throws IOException {
        logger.info("Executing :: JsonUtility.convertToJson");
        ArrayNode arrayNode = objectMapper.createArrayNode();

        while (data.hasNext()) {
            List<Map<String, Object>> dataList = data.next();
            for (Map<String, Object> dataMap : dataList) {
                ObjectNode node = objectMapper.createObjectNode();
                for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    JsonNode jsonNode = objectMapper.convertValue(value,JsonNode.class);
                    node.set(key,jsonNode);
                }
                JsonNode transformedNode = transformBySchema(node, jsonSchema);
                arrayNode.add(transformedNode);
            }
        }
        logger.info("Exiting :: JsonUtility.convertToJson");
        return objectMapper.readTree(arrayNode.toString());
    }
    private static JsonNode transformBySchema(ObjectNode inputNode, JsonNode jsonSchema) {
        logger.info("Executing :: JsonUtility.transformBySchema");
        if (jsonSchema.isEmpty()) {
            throw new IllegalArgumentException("Schema is Empty");
        }
        JsonNode propertiesNode = jsonSchema.get("properties");
        Iterator<Map.Entry<String, JsonNode>> properties = propertiesNode.fields();

        while (properties.hasNext()) {
            Map.Entry<String, JsonNode> property = properties.next();
            String propertyName = property.getKey();
            JsonNode propertyTypeNode = property.getValue().get("type");
            String propertyType = propertyTypeNode.asText();
            // Get the corresponding value node from the data node
            JsonNode dataValueNode = inputNode.get(propertyName);
            if (dataValueNode == null) {
                continue;
            }
            // Compare the property type with the value node type
            String dataNodeType = dataValueNode.getNodeType().toString().toLowerCase();
            if (!propertyType.equals(dataNodeType)) {
                switch (propertyType) {
                    case "string":
                        //when propertyType string
                        switch (dataNodeType) {
                            case "number", "object", "boolean", "array" -> {
                                String strValue = String.valueOf(dataValueNode);
                                inputNode.put(propertyName, strValue);
                                logger.trace("Property Type changed to String!");
                            }
                        }
                        break;
                    case "number":
                        //when propertyType number
                        if (dataNodeType.equals("string")) {
                            Integer intValue = Integer.parseInt(dataValueNode.asText());
                            inputNode.put(propertyName, intValue);
                            logger.trace("Property Type changed to Number!");
                        }
                        break;
                    case "boolean":
                        //when propertyType boolean
                        if (dataNodeType.equals("string")) {
                            if (("true".equalsIgnoreCase(dataValueNode.asText())) || ("false".equalsIgnoreCase(dataValueNode.asText()))) {
                                Boolean boolValue = Boolean.parseBoolean(dataValueNode.asText());
                                inputNode.put(propertyName, boolValue);
                                logger.trace("Property Type changed to boolean!");
                            }else if ("1".equals(dataValueNode.asText())){
                                Boolean boolValue = true;
                                inputNode.put(propertyName, boolValue);
                                logger.trace("Property Type changed to boolean!");
                            }else if ("0".equals(dataValueNode.asText())){
                                Boolean boolValue = false;
                                inputNode.put(propertyName, boolValue);
                                logger.trace("Property Type changed to boolean!");
                            }
                        } else if (dataNodeType.equals("number")) {
                            int value = dataValueNode.asInt();
                            if (value == 0) {
                                Boolean boolValue = false;
                                inputNode.put(propertyName, boolValue);
                                logger.trace("Property Type changed to boolean!");
                            } else if (value == 1) {
                                Boolean boolValue = true;
                                inputNode.put(propertyName, boolValue);
                                logger.trace("Property Type changed to boolean!");
                            } else {
                                logger.error("failed to parsing value in boolean");
                                throw new IllegalArgumentException("failed to parsing value in boolean");
                            }
                        }
                        break;
                }
            } else {
                logger.trace("Data node type is same as schema node type");
                inputNode.set(propertyName, dataValueNode);
            }
        }
        logger.info("Exiting :: JsonUtility.transformBySchema");
        return inputNode;
    }
}

