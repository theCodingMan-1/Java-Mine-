//single dispatch

mammal.makeNoise();
// Resolves mammal to its underlying type and calls its makeNoise method rather than mammal's

// basically its a child class inheriting from a parent to get its attribute but
// polymorphism is the act of overwritting the method it recieves





public class Runner {
    public static void main(String[] args) {
        Mammal mDolphin = new Dolphin;
        Mammal mLion = new Lion;
        Mammal mDog = new Dog

        mDolphin.makeNoise(); // "Squeak Click"
        mDog.makeNoise(); // "Woof Woof"
    }
}

