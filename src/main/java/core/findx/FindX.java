package core.findx;

import geom.obj.Line;
import geom.obj.Point;
import geom.obj.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindX {
    public List<Line> find(List<Point> polygon) {
        List<Line> increasedOrder = polygon.stream()
                .mapToDouble(Point::y)
                .distinct()
                .sorted()
                .mapToObj(y -> new Point(-Double.MAX_VALUE, y))
                .map(point -> new Line(point, new Vector(1.0, 0.0)))
                .toList();

        List<Line> reverseOrder = new ArrayList<>(increasedOrder);
        Collections.reverse(reverseOrder);

        //System.out.println("lines:");
        //reverseOrder.forEach(line -> System.out.println("y: " + line.from().y()));

        return reverseOrder;
    }
}
