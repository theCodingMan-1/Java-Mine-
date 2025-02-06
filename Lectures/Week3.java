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


        mLion.acceptMammal(mDog);
        mDog.acceptMamal(mLion);
    }
}


public abstract class Mammal {
    public abstract void makeNoise();
    public abstract void acceptMamal(Mammal m);
    public abstract makeNoise(Dog d);
    public abstract makeNoise(Lion l);
    public abstract makeNoise(Dolphin d);

}


public class Dog extends Mammal {
    public void makeNoise() {System.out.println("Woof")}

    public void acceptMamal(Mammal m) {m.makeNoise(this);}
    // calls the make noise method of the underlying object on the heap
}

