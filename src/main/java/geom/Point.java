package geom;

public record Point(double x, double y) {

    static Vector diff(final Point from, final Point to) {
        return new Vector(to.x() - from.x(), to.y() - from.y());
    }
}
