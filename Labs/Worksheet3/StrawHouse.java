public class StrawHouse extends House {

    String name = "Straw House";

    public Integer accept(VisitorInt visitor) {
        return visitor.visit(this);
    }

    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
