package Map;

import java.util.*;

public class MapAccessByIndex {
    public static <K, V> V getValueByIndex(Map<K, V> map, int index) {
        if (index < 0 || index >= map.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Collection<V> values = map.values();
        List<V> valueList = new ArrayList<>(values);
        return valueList.get(index);
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
for (int index = 0; index<map.size();index++) {

    Integer value = getValueByIndex(map, index);
    System.out.println("Value at index " + index + " : " + value);
}
    }
}
