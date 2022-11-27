package core.findD;

import geom.Line;
import geom.Point;
import geom.Rectangle;
import geom.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class FindD {

    Random random = new Random(0);

    Rectangle getBounds(List<Point> list) {
        return new Rectangle(
                new Point(list.stream().map(Point::x).min(Double::compareTo).orElseThrow(), list.stream().map(Point::y).min(Double::compareTo).orElseThrow()),
                new Point(list.stream().map(Point::x).max(Double::compareTo).orElseThrow(), list.stream().map(Point::y).max(Double::compareTo).orElseThrow()), );
    }

    List<Rectangle> findRectangles(List<Point> polygon, Line top, Line bottom) {
        assert top.direction().equals(bottom.direction());
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < polygon.size(); i++) {
            segments.add(new Segment(polygon.get(i), polygon.get((i + 1) % polygon.size())));
        }
        List<Point> top_intersections = segments.stream()
                .map(top::intersectsAt)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted()
                .map(top::apply)
                .toList();

        List<Point> bottom_intersections = segments.stream()
                .map(bottom::intersectsAt)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted()
                .map(bottom::apply)
                .toList();


        Rectangle bounds =

        for (int d = 0; d < 10; d++) {
            List<Double> delimiters = generateRectanglesFrom(bounds.a().x(), bounds.b().y(), d);

        }
    }

    private List<Double> generateRectanglesFrom(double from, double to, int d) {
        return random.doubles(from, to).limit(d).sorted().boxed().toList();
    }

}
