import geom.Point;
import geom.Rectangle;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class ReadWrite {
    public List<Point> readPolygon(Scanner scanner) {
        String[] firstLine = scanner.nextLine().split(" ");
        int n = Integer.parseInt(firstLine[0]);
        List<Point> polygon = new ArrayList<>();

        polygon.add(new Point(Double.parseDouble(firstLine[1]), Double.parseDouble(firstLine[2])));
        Stream.generate(() -> {
                    String[] values = scanner.nextLine().split(" ");

                    return new Point(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
                })
                .limit(n - 1)
                .forEach(polygon::add);

        return polygon;
    }

    public void writeAnswer(Writer writer, Set<Rectangle> rectangles) throws IOException {
        writer.write(String.valueOf(rectangles.size()) + '\n');

        rectangles.forEach(rectangle -> {
            try {
                Point a = rectangle.a();
                Point b = rectangle.b();

                writer.write(a.x() + " " + a.y() + " " + b.x() + " " + b.y());
            } catch (IOException e) {
                throw new RuntimeException("poh", e);
            }
        });
    }
}
