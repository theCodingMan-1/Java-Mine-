import java.util.*;



public class Generics<T> {
    static <T> boolean isEqual(T left, T right) {
        return left.equals(right);
    }

    static <T> List<T> reverse(List<T> list) {

        List<T> newlist = new ArrayList<T>();
        int length = list.size();
        for (int i = 1; i < length + 1; i++) {
            newlist.add(list.get(length - i));
        }

        return newlist;

    }

    public static void main(String[] args) {
        String string1 = "Yay";
        String string2 = "no";

        int number1 = 1;

        System.out.println(isEqual(string1, string2));

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println(reverse(list));
    }
}


// Subtype polymorphism -> just normal polymorphism
// 
//
