package core.findD;

import core.RectanglesComparator;
import geom.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;

public class FindD {

    Random random = new Random(0);

    Rectangle getBounds(List<Point> list) {
        return new Rectangle(
                new Point(list.stream().map(Point::x).min(Double::compareTo).orElseThrow(), list.stream().map(Point::y).min(Double::compareTo).orElseThrow()),
                new Point(list.stream().map(Point::x).max(Double::compareTo).orElseThrow(), list.stream().map(Point::y).max(Double::compareTo).orElseThrow()),
                Place.IN
        );
    }

    Place and(Place a, Place b) {
        return a == Place.IN && b == Place.IN ? Place.IN : Place.OUT;
    }

    public List<Rectangle> findRectangles(List<Point> polygon, Line top, Line bottom) {
        assert top.direction().equals(bottom.direction());
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < polygon.size(); i++) {
            segments.add(new Segment(polygon.get(i), polygon.get((i + 1) % polygon.size())));
        }
        double[] top_intersections = segments.stream()
                .map(top::intersectsAt)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted()
                .map(top::apply)
                .mapToDouble(Point::x)
                .toArray();

        double[] bottom_intersections = segments.stream()
                .map(bottom::intersectsAt)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted()
                .map(bottom::apply)
                .mapToDouble(Point::x)
                .toArray();


        Rectangle bounds = getBounds(polygon.stream()
                .filter(point -> point.y() <= top.from().y() && point.y() >= bottom.from().y()).toList());

        return IntStream.range(0, 10).mapToObj(d -> {
            List<Double> delimiters = generateRectanglesFrom(bounds.a().x(), bounds.b().y(), d);
            List<Rectangle> rects = new ArrayList<>();
            double prev = bounds.a().x();
            for (double del : delimiters) {
                rects.add(new Rectangle(new Point(top.from().x(), prev), new Point(top.from().x(), del),
                        and(generatePlaceFrom(prev, top_intersections, bottom_intersections),
                                generatePlaceFrom(del, top_intersections, bottom_intersections))));
                prev = del;
            }
            return rects;
        }).min(RectanglesComparator::compare).get();
    }

    private List<Double> generateRectanglesFrom(double from, double to, int d) {
        return random.doubles(from, to).limit(d).sorted().boxed().toList();
    }

    private Place generatePlaceFrom(Double kek, double[] top_intersections, double[] bottom_intersections) {
        int indx1 = Arrays.binarySearch(top_intersections, kek);
        int indx2 = Arrays.binarySearch(bottom_intersections, kek);
        assert indx1 < 0 && indx2 < 0;

        return (-(indx1 + 1)) % 2 == 1 && (-(indx2 + 1)) % 2 == 1 ? Place.IN : Place.OUT;
    }

}
