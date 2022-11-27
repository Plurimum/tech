import geom.Point;
import geom.Rectangle;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(Files.newBufferedReader(Path.of("tests/01.txt")));
                Writer writer = Files.newBufferedWriter(Path.of("out"))
        ) {
            ReadWrite readWrite = new ReadWrite();
            List<Point> polygon = readWrite.readPolygon(scanner);

            Set<Rectangle> rectangles = Set.of(new Rectangle(new Point(1.2345, 6.789), new Point(6.789, 1.2345))); // get from solve method

            readWrite.writeAnswer(writer, rectangles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}