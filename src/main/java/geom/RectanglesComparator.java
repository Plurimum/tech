package geom;

import geom.obj.Rectangle;

import java.util.Comparator;
import java.util.List;

public class RectanglesComparator implements Comparator<List<Rectangle>> {
    private final double c1;
    private final double c2;

    public RectanglesComparator(double c1, double c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public int compare(List<Rectangle> rectangles1, List<Rectangle> rectangles2) {
        if (rectangles1.isEmpty()) {
            if (rectangles2.isEmpty()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (rectangles2.isEmpty()) {
                return -1;
            }
        }

        double cost1 = rectangles1.stream()
                .map(this::getCost)
                .reduce(Double::sum)
                .orElseThrow(); // pohui
        double cost2 = rectangles2.stream()
                .map(this::getCost)
                .reduce(Double::sum)
                .orElseThrow(); // pohui

        return Double.compare(cost1, cost2);
    }

    private double getCost(Rectangle rectangle) {
        double cost;

        if (rectangle.place() == Place.IN) {
            cost = 1.0 + c1;
        } else {
            cost = 1.0 + rectangle.square() * c2;
        }

        return cost;
    }
}
