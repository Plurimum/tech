package geom;

public record Rectangle(Point a, Point b, Place place) {
    public double square() {
        return (Math.abs(b.x()) - Math.abs(a.x())) * (Math.abs(b.y()) - Math.abs(a.y()));
    }
}
