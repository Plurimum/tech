package geom;

import java.util.Optional;

public record Line(Point from, Vector direction) {
    Optional<Point> intersectsAt(Line other) {
        final double px = from().x() - other.from().x();
        final double py = from().y() - other.from().y();
        final double t2 = (py * direction().x() - px * direction().y()) /
                (direction().x() * other.direction().y() * (other.direction().x() / direction().x() - 1));


    }
}
