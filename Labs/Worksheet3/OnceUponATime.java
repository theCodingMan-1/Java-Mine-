public class OnceUponATime {
    public static Integer estimateRentPCM(House house) {
        CharterSurveyor me = new CharterSurveyor();
        return house.accept(me);
    }

    public static Integer estimateHeatingBillPCM(House house) {
        Electrician<Integer> me = new Electrician<>();
        return house.accept(me)
    }

    public static String letMeComeIn(House house) {

        Storyteller<String> me = new Storyteller<>();


        return house.accept(me);
    }

    public static void main(String[] args) {

        House[] houses = new House[]{new StrawHouse(), new StickHouse(), new BrickHouse()};

        for (House house : houses) {
            System.out.println("Calling functions on: " + house.toString());
            System.out.println( "Estimating rent: " + estimateRentPCM(house) );
            System.out.println( "Estimating heating bill: " + estimateHeatingBillPCM(house) );
            System.out.println( "Here comes the Big Bad Wolf: " + letMeComeIn(house) );
        }

    }
}
