//Objects and Classes.

//1 (a)

//public class Worksheet1 {
//    public static void main(String[] args) {
//        System.out.println("Hello, world");
//    }
//}

// public -> can be accessed anywhere
// private ->  can only be accessed within the class
// static -> basically makes the class a function with a 'static' unchanging set of instructions.
//         -> associated to a class, not actual object instances
// dynamic -> (static)! can be set as an instance that are 'dynamic'


//2
//public class Worksheet1 {
//    public static void main(String[] args) {
//        Critter cow = new Critter("Cow");
//        cow.poke();
//        Critter tiger = new Critter("Tiger");
//        tiger.eat("cow");
//    }
//}
//
//
//class Critter {
//    static String name;
//
//    Critter(String name) {
//        this.name = name;
//    }
//
//    void poke() {
//        System.out.println(name + " was poked");
//    }
//
//    void eat(String animal) {
//        System.out.println("I am eating " + animal + ". It was scrumptious.");
//    }
//}

//3(a)
// - Class is a blueprint which you use to create objects
// - objects are instances of a class - a concrete thing that you made using a specific class

//3(b)
// Primitives are the most pasic data types available within java language;
// - Bools, Char, Short, Int, Long, Float, Double.
// They tell us what types the attributes hold within the classes.


// 3(c)
// null type represents nothing. it is an abscence of value.
// Int cannot support null value?



//Smart Data
//1(a)

public class Worksheet1 {
    public static void main(String[] args) {
        Vector2D original = new Vector2D(1.0, 2.0);
        Vector2D v = new Vector2D(4.0, 6.0);

        double distance = original.distance(v);
        System.out.println(distance);

        Vector2D newv = original.add(v);
        System.out.println(newv.x + ", " + newv.y);

        original.scale(3.0);
        System.out.println(original.x + ", " + original.y);
    }
}


class Vector2D {
    double x, y;

    Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double distance(Vector2D v) {
        return Math.sqrt(Math.pow(x - v.x, 2) + Math.pow(y - v.y, 2));
    }

    Vector2D add(Vector2D v) {
        double newx = x + v.x;
        double newy = y + v.y;
        Vector2D newv = new Vector2D(newx, newy);
        return newv;
    }

    void scale(double f) {
        x *= f;
        y *= f;
    }
    
}





