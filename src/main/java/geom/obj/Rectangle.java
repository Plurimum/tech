package geom.obj;

import geom.Place;

public record Rectangle(Point a, Point b, Place place) {
    public double square() {
        return (b.x() - a.x()) * (b.y() - a.y());
    }
}
