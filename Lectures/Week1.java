/*
* Classes are blueprints for a certain objects.
* Objects are dynamic entities that hold an instance of the class.
*
*
* */

class Robot {
    String name;
    int numLegs;
    float powerLevel;

    Robot(String productName) {
        name = productName;
        numLegs = 2;
        powerLevel = 2.0f;
    }
}

