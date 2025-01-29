class TestUtils {
    static void assertEquals(String expected, String actual, String error) {
        if (expected.equals(actual)){
            System.out.println("The strings are equal!");
        }
        else {
            System.out.println(error +": Expecting " + expected +  " but was given " + actual);
        }
    }

    static void assertEquals(int expected, int actual, String error) {
        if (expected == actual){
            System.out.println("The numbers are equal!");
        }
        else {
            System.out.println(error +": Expecting " + expected +  " but was given " + actual);
        }

    }

    static void assertUniversalEquals(Object expected, Object actual, String error) {
        if (expected.equals(actual)){
            System.out.println("They are equal!");
        }
        else {
            System.out.println(error +": Expecting " + expected +  " but was given " + actual);
        }
    }
}

