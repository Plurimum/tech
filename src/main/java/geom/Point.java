package geom;

public record Point(double x, double y) {

    static Vector diff(final Point from, final Point to) {
        return new Vector(to.x() - from.x(), to.y() - from.y());
    }

    Point add(Vector v) {
        return new Point(x + v.x(), y + v.y());
    }


}
