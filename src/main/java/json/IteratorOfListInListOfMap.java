package json;
import java.util.*;

public class IteratorOfListInListOfMap {
    public static void main(String[] args) {
        Iterator<Map<String, Object>> iterator = getIterator();
        List<Map<String, Object>> newList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map<String, Object> currentMap = iterator.next();
            Map<String, Object> newMap = new HashMap<>(currentMap);
            newList.add(newMap);
        }
        // Access the newList of maps
        for (Map<String, Object> map : newList) {
            // Process each map as needed
            System.out.println(map);
        }
    }

    private static Iterator<Map<String, Object>> getIterator() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = Map.of("key1", "value1");
        Map<String, Object> map2 = Map.of("key2", "value2");
        list.add(map1);
        list.add(map2);
        return list.iterator();
    }
}
