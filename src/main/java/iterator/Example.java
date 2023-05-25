package iterator;

import java.util.*;

public class Example {
    public static void main(String[] args) {
        List<Map<String, Object>> data = getData();
        Iterator<List<Map<String, Object>>> iterator = processData(data);

        // Iterate over the data
        while (iterator.hasNext()) {
            List<Map<String, Object>> batch = iterator.next();
            // Process each batch
            for (Map<String, Object> map : batch) {
                // Process each map
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    // Do something with the key-value pair
                    System.out.println(key + ": " + value);
                }
            }
        }
    }

    public static Iterator<List<Map<String, Object>>> processData(List<Map<String, Object>> data) {
        // Your processing logic here
        List<List<Map<String, Object>>> batches = new ArrayList<>();

        // Split the data into batches
        // For example, let's assume each batch contains 10 elements
        int batchSize = 10;
        int dataSize = data.size();
        int startIndex = 0;

        while (startIndex < dataSize) {
            int endIndex = Math.min(startIndex + batchSize, dataSize);
            List<Map<String, Object>> batch = data.subList(startIndex, endIndex);
            batches.add(batch);
            startIndex += batchSize;
        }

        return batches.iterator();
    }

    public static List<Map<String, Object>> getData() {
        // Your data retrieval logic here
        List<Map<String, Object>> data = new ArrayList<>();

        // Add some sample data
        Map<String, Object> map1 = new HashMap<>();
        map1.put("key1", "value1");
        map1.put("key2", 123);
        data.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("key3", "value3");
        map2.put("key4", 456);
        data.add(map2);

        return data;
    }
}

