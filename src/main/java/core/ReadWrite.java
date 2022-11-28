package core;

import geom.obj.Point;
import geom.obj.Rectangle;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

public class ReadWrite {
    public List<Point> readPolygon(Scanner scanner, int n) {
        List<Point> polygon = new ArrayList<>();

        Stream.generate(() -> {
                    String[] values = scanner.nextLine().split(" ");

                    return new Point(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
                })
                .limit(n - 1)
                .forEach(polygon::add);

        return polygon;
    }

    public void writeAnswer(Writer writer, List<Rectangle> rectangles) throws IOException {
        writer.write(String.valueOf(rectangles.size()) + '\n');

        rectangles.forEach(rectangle -> {
            try {
                Point a = rectangle.a();
                Point b = rectangle.b();

                String x1 = formatDouble(a.x());
                String y1 = formatDouble(a.y());
                String x2 = formatDouble(b.x());
                String y2 = formatDouble(b.y());

                writer.write(x1 + " " + y1 + " " + x2 + " " + y2 + "\n");
            } catch (IOException e) {
                throw new RuntimeException("poh", e);
            }
        });
    }

    private String formatDouble(double num) {
        return String.format(Locale.US, "%.11f", num);
    }
}
