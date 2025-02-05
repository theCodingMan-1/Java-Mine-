public class ChartedSurveyor implements House.VisitorInt {
    public Integer visit(StrawHouse house) {return 1;}
    public Integer visit(StickHouse house) {return 5;}
    public Integer visit(BrickHouse house) {return 10;}
}