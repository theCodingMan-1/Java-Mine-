// 6, 5, 1, 2,, 3, 4


// Part 1


//1. (a)

public class Worksheet2 {

}

class TestUtils {
    static void assertEquals(String expected, String actual, String error) {
        assert expected.equals(actual) : " " + error +": Expecting " + expected +  "but was " + actual
        System.out.println("The strings are equal!");



    }
}
