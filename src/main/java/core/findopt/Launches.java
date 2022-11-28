package core.findopt;

import geom.RectanglesComparator;
import core.findd.FindD;
import core.findx.FindX;
import geom.obj.Line;
import geom.obj.Point;
import geom.obj.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Launches {
    public List<Rectangle> launch(List<Point> polygon, double c1, double c2, int launchCount) {
        FindX findX = new FindX();
        FindD findD = new FindD();
        RectanglesComparator rectanglesComparator = new RectanglesComparator(c1, c2);
        List<Rectangle> bestRectangles = new ArrayList<>();

        for (int j = 0; j < launchCount; j++) {
            List<Line> lines = findX.find(polygon);
            List<Rectangle> allRectangles = new ArrayList<>();

            for (int i = 0; i < lines.size() - 1; i++) {
                List<Rectangle> rectangles = findD.findRectangles(polygon, lines.get(i), lines.get(i + 1), c1, c2);

                allRectangles.addAll(rectangles);
            }

            int res = rectanglesComparator.compare(bestRectangles, allRectangles);

            if (res > 0) {
                bestRectangles = allRectangles;
            }
        }

        return bestRectangles;
    }
}
