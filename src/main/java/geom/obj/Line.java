package geom.obj;

import java.util.Optional;

public record Line(Point from, Vector direction) {
    static final double EPS = 1e-5;

    public Optional<Point> intersect(Segment segment) {
        double x1 = from.x();
        double y1 = from.y();

        double x2 = from().x() + 1.0;
        double y2 = direction.y() * x2 / direction.x() - direction.y() * x1 / direction.x() + y1;

        double x3 = segment.a().x();
        double y3 = segment.a().y();

        double x4 = segment.b().x();
        double y4 = segment.b().y();

        double n;
        if (Math.abs(y2 - y1) >= EPS) {
            double q = (x2 - x1) / (y1 - y2);
            double sn = (x3 - x4) + (y3 - y4) * q;

            if (Math.abs(sn) < EPS) {
                return Optional.empty();
            }

            double fn = (x3 - x1) + (y3 - y1) * q;
            n = fn / sn;
        }
        else {
            if (Math.abs(y3 - y4) < EPS) {
                return Optional.empty();
            }

            n = (y3 - y1) / (y3 - y4);
        }

        Point point = new Point(x3 + (x4 - x3) * n, y3 + (y4 - y3) * n);

        double segMinX = Math.min(segment.a().x(), segment.b().x());
        double segMaxX = Math.max(segment.a().x(), segment.b().x());
        double segMinY = Math.min(segment.a().y(), segment.b().y());
        double segMaxY = Math.max(segment.a().y(), segment.b().y());

        if (
                point.x() < segMinX ||
                point.x() > segMaxX ||
                point.y() < segMinY ||
                point.y() > segMaxY
        ) {
            return Optional.empty();
        }

        return Optional.of(point);
    }
}
