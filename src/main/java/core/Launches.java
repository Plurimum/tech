package core;

import core.findD.FindD;
import core.findx.FindX;
import geom.Line;
import geom.Point;
import geom.Rectangle;

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
                List<Rectangle> rectangles = findD.findRectangles(polygon, lines.get(i), lines.get(i + 1));

                allRectangles.addAll(rectangles);
            }

            bestRectangles = rectanglesComparator.minList(bestRectangles, allRectangles);
        }

        return bestRectangles;
    }
}
