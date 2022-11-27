package geom;

public record Vector(double x, double y) {
    double qsize() {
        return x * x + y * y;
    }
    double size() {
        return Math.sqrt(qsize());
    }

    Vector mul(double t) {
        return new Vector(x * t, y * t);
    }

    double dot(Vector other) {
        return x * other.x + y * other.y;
    }
}
