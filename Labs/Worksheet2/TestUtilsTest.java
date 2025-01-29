public class TestUtilsTest {
    public static void main(String[] args) {
        TestUtils.assertEquals("TEST", "TEST", "Oh Jeeebz");
        TestUtils.assertEquals("TEST", "NonTest", "Oh Jeeebz");

        TestUtils.assertEquals(5, 5, "Oh Jeeebz");
        TestUtils.assertEquals(5, 6, "Oh Jeeebz");

        TestUtils.assertUniversalEquals(5, null, "Oh Jeeebz");
        TestUtils.assertUniversalEquals(5, "hello", "Oh Jeeebz");





    }


}