package iterator;

import java.util.*;

public class Practice {
    public static void main(String[] args) {
        List<Integer> map = new ArrayList<>();
        map.add(1);
        Iterator<Integer> mapIterator = map.iterator();
        while (mapIterator.hasNext()){
            System.out.println(mapIterator.next());
        }
    }
}
