package geom;

public record Segment(Point a, Point b) {
    Vector direction() {
        return Point.diff(a, b);
    }
}
