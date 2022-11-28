import core.findopt.Launches;
import core.ReadWrite;
import geom.obj.Point;
import geom.obj.Rectangle;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(Files.newBufferedReader(Path.of("tests/01.txt")));
                Writer writer = Files.newBufferedWriter(Path.of("out"))
        ) {
            ReadWrite readWrite = new ReadWrite();
            String[] firstLine = scanner.nextLine().split(" ");
            int n = Integer.parseInt(firstLine[0]);
            double c1 = Double.parseDouble(firstLine[1]);
            double c2 = Double.parseDouble(firstLine[2]);

            Launches launches = new Launches();

            List<Point> polygon = readWrite.readPolygon(scanner, n);
            List<Rectangle> best = launches.launch(polygon, c1, c2, 1_000);

            readWrite.writeAnswer(writer, best);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}