import java.util.Random;

public class BrickHouse extends House {

    boolean potBoiling = new Random().nextBoolean();

    String name = "Brick House";

    public Integer accept(VisitorInt visitor) {
        return visitor.visit(this);
    }

    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}