package core.findx;

import geom.Line;
import geom.Point;
import geom.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FindX {
    public List<Line> find(List<Point> polygon, Mode mode) {
        if (mode == Mode.HORIZONTAL) {
            return find(polygon, )
        }

    }

    private List<Line> find(
            List<Point> polygon,
            Function<Point, Double> getter,
            Function<Double, Point> creator,
            Vector vector
    ) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_NORMAL;

        for (Point point : polygon) {
            double val = getter.apply(point);

            if (val > max) {
                max = val;
            }

            if (val < min) {
                min = val;
            }
        }

        Random random = new Random();
        List<Line> lines = new ArrayList<>();
        double x = (Math.abs(max) - Math.abs(min)) / random.nextInt(10, 10_000);
        int prevIdx = 0;

        lines.add(new Line(creator.apply(max), vector));

        while (true) {
            double prev = getter.apply(lines.get(prevIdx).from());
            double newPrev = prev - x;

            if (newPrev < min) {
                lines.add(new Line(creator.apply(min), vector));
                break;
            }

            lines.add(new Line(creator.apply(newPrev), vector));
            prevIdx++;
        }

        return lines;
    }
}
