package geom;

import java.util.Optional;

public record Line(Point from, Vector direction) {
    static final double EPS = 1e-5;

    public Point apply(double t) {
        return from.add(direction.mul(t));
    }

    record LineIntersection(Point at, double t1, double t2) {

    }

    public Optional<LineIntersection> intersectsAt(Line other) {
        if (direction().dot(other.direction()) / (direction.size() * other.direction().size()) < EPS) {
            return Optional.empty();
        }
        final double px = from().x() - other.from().x();
        final double py = from().y() - other.from().y();
        final double t2 = (py * direction().x() - px * direction().y()) /
                (direction().x() * other.direction().y() * (other.direction().x() / direction().x() - 1));
        final double t1 = (px + t2 * other.direction().x()) / direction().x();

        assert (Point.diff(apply(t1), other.apply(t2)).size() < EPS);

        return Optional.of(new LineIntersection(apply(t1), t1, t2));
    }

    public Optional<Double> intersectsAt(Segment segment) {
        final Line other = new Line(segment.a(), segment.direction());
        final Optional<LineIntersection> intersection = intersectsAt(other);
        if (intersection.isEmpty() || intersection.get().t2 < 0 || intersection.get().t2 > 1) {
            return Optional.empty();
        }

        return Optional.of(intersection.get().t1);
    }
}
