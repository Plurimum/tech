package core;

import geom.Place;
import geom.Rectangle;

import java.util.List;

public class RectanglesComparator {
    private final double c1;
    private final double c2;

    public RectanglesComparator(double c1, double c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public List<Rectangle> minList(List<Rectangle> rectangles1, List<Rectangle> rectangles2) {
        double cost1 = rectangles1.stream()
                .map(this::getCost)
                .reduce(Double::sum)
                .get(); // pohui
        double cost2 = rectangles2.stream()
                .map(this::getCost)
                .reduce(Double::sum)
                .get(); // pohui
        
        if (cost1 < cost2) {
            return rectangles1;
        } else {
            return rectangles2;
        }
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
