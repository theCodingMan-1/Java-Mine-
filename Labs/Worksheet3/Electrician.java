public class Electrician<T> implements House.Visitor<T> {
    public T visit(StrawHouse house) {return (T)(Object)100;} //converts Integer (100) to an Object which is then converted to the type T [generic]
    public T visit(StickHouse house) {return (T)(Object)20;}
    public T visit(BrickHouse house) {return (T)(Object)10;}

}