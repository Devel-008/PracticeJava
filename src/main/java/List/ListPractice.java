package List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListPractice {

    public static void main(String[] args) {
        System.out.println("Search Result==>>>>>>>>");
        search();
        System.out.println("Sort Result==>>>>>>>>");
        sort();
        System.out.println("GetAndSet Result==>>>>>>>>");
        getAndSetElement();
    }
    public static void search(){
        List<Integer> numbers = new ArrayList<>();

        // add some integers to the list
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(2);

        // use indexOf() to find the first occurrence of an
        // element in the list
        int index = numbers.indexOf(2);
        System.out.println(
                "The first occurrence of 2 is at index "
                        + index);

        // use lastIndexOf() to find the last occurrence of
        // an element in the list
        int lastIndex = numbers.lastIndexOf(2);
        System.out.println(
                "The last occurrence of 2 is at index "
                        + lastIndex);
    }
    public static void sort(){
        List<String> list1=new ArrayList<>();
        list1.add("Mango");
        list1.add("Apple");
        list1.add("Banana");
        list1.add("Grapes");
        //Sorting the list
        Collections.sort(list1);
        //Traversing list through the for-each loop
        for(String fruit:list1)
            System.out.println(fruit);

        System.out.println("Sorting numbers...");
        //Creating a list of numbers
        List<Integer> list2=new ArrayList<>();
        list2.add(21);
        list2.add(11);
        list2.add(51);
        list2.add(1);
        //Sorting the list
        Collections.sort(list2);
        //Traversing list through the for-each loop
        for(Integer number:list2)
            System.out.println(number);
    }
    public static void getAndSetElement(){
        List<String> list=new ArrayList<>();
        //Adding elements in the List
        list.add("Mango");
        list.add("Apple");
        list.add("Banana");
        list.add("Grapes");
        //accessing the element
        System.out.println("Returning element: "+list.get(1));//it will return the 2nd element, because index starts from 0
        //changing the element
        list.set(1,"Dates");
        //Iterating the List element using for-each loop
        for(String fruit:list)
            System.out.println(fruit);
    }
}

