package core.findd;

import geom.RectanglesComparator;
import geom.*;
import geom.obj.Line;
import geom.obj.Point;
import geom.obj.Rectangle;
import geom.obj.Segment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class FindD {

    private static final Random RANDOM = new Random(); // seed 0

    private Rectangle getBounds(Line top, Line bottom, double[] topIntersections, double[] bottomIntersections) {
        double minXTop = Double.POSITIVE_INFINITY;
        double maxXTop = Double.NEGATIVE_INFINITY;
        double minXBot = Double.POSITIVE_INFINITY;
        double maxXBot = Double.NEGATIVE_INFINITY;

        for (double topIntersection : topIntersections) {
            minXTop = Math.min(minXTop, topIntersection);
            maxXTop = Math.max(maxXTop, topIntersection);
        }

        for (double bottomIntersection : bottomIntersections) {
            minXBot = Math.min(minXBot, bottomIntersection);
            maxXBot = Math.max(maxXBot, bottomIntersection);
        }

        return new Rectangle(
                new Point(Math.min(minXBot, minXTop), bottom.from().y()),
                new Point(Math.max(maxXBot, maxXTop), top.from().y()),
                Place.OUT
        );
    }

    private Place and(Place a, Place b) {
        return a == Place.IN && b == Place.IN ? Place.IN : Place.OUT;
    }

    public List<Rectangle> findRectangles(List<Point> polygon, Line top, Line bottom, double c1, double c2) {
        assert top.direction().equals(bottom.direction());

        List<Segment> segments = new ArrayList<>();

        for (int i = 0; i < polygon.size(); i++) {
            segments.add(new Segment(polygon.get(i), polygon.get((i + 1) % polygon.size())));
        }

        double[] top_intersections = segments.stream()
                .map(top::intersect)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .mapToDouble(Point::x)
                .sorted()
                .toArray();

        double[] bottom_intersections = segments.stream()
                .map(bottom::intersect)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .mapToDouble(Point::x)
                .sorted()
                .toArray();

        Rectangle bounds = getBounds(top, bottom, top_intersections, bottom_intersections);

        return IntStream.range(0, 100).mapToObj(d -> {
                    List<Double> delimiters = generateRectanglesFrom(bounds.a().x(), bounds.b().x(), d);
                    List<Rectangle> rects = new ArrayList<>();
                    double prev = bounds.a().x();

                    for (double del : delimiters) {
                        rects.add(
                                new Rectangle(
                                        new Point(prev, bottom.from().y()),
                                        new Point(del, top.from().y()),
                                        and(generatePlaceFrom(prev, top_intersections, bottom_intersections),
                                                generatePlaceFrom(del, top_intersections, bottom_intersections))
                                )
                        );
                        prev = del;
                    }

                    rects.add(
                            new Rectangle(
                                    new Point(prev, bottom.from().y()),
                                    new Point(bounds.b().x(), top.from().y()),
                                    and(generatePlaceFrom(prev, top_intersections, bottom_intersections),
                                            generatePlaceFrom(bounds.b().x(), top_intersections, bottom_intersections))
                            )
                    );

                    return rects;
                })
                .min(new RectanglesComparator(c1, c2)).get();
    }

    private List<Double> generateRectanglesFrom(double from, double to, int d) {
        return RANDOM.doubles(from, to).limit(d).sorted().boxed().toList();
    }

    private Place generatePlaceFrom(Double kek, double[] top_intersections, double[] bottom_intersections) {
        int indx1 = Arrays.binarySearch(top_intersections, kek);
        int indx2 = Arrays.binarySearch(bottom_intersections, kek);

        assert indx1 < 0 && indx2 < 0;

        return (-indx1 + 1) % 2 == 1 && (-indx2 + 1) % 2 == 1 ? Place.IN : Place.OUT;
    }

}
