// to prevent us from making instaces of a class we apply the 'abstract' keyword.


// if a method overrides one of its parent class's methods,
// one can invoke the overridden methdo through the use of super.

class Parent {
    String name;
    Parent () {}

    Parent(String name) {
        this.name = "Parent Attribute " + name;
    }

    void saySomething() {
        System.out.println("From ParentMethod");
    }
}


class Child extends Parent {
    String name;
    Child (String name) {
        super(name); // call constructor of parent
        this.name = "Child Attribute " +  name;
    }

    String getParentName() {
        return super.name; // allows us to access the Parent's name
    }

    void saySomething() {
        System.out.println("From ChildMethod");
    }

    void delegate() {
        super.saySomething();
    }
}

// multiple inheritence is not allowed in Java :(

:o :-) :D :O

// an interface is a a reference type in Java, a set of only abstract methods.
// when a class implements and interface
// it inherits all of the (implicitly abstract) methods of the interface


abstract class Animal {
    abstract void makeNoise();
}


interface Pet {

}


class Dog extends Animal implements Pet {

}


























//generics were introduced to provide tighter type checks at compile time and to support generic programming
// however you can inherit from generic classes too
// children are allowed to use all the type perameters of the parent and define their own ones.

class MyParent<T> {

}

class MyChild<T, V> extends MyParent<T> {

}

//
// (._.)  <(Hello!)
//  -|-
//  / \
