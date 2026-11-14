package se.miun.emsj2403.dt187g.jpaint.geometry;

import java.util.ArrayList;
import se.miun.emsj2403.dt187g.jpaint.Drawable;

public abstract class Shape implements Drawable {

    protected String color;
    protected ArrayList<Point> points;

    public Shape(Point p, String color) {
        this.color = color;
        points = new ArrayList<>();
        points.add(p);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public abstract double getCircumference();

    public abstract double getArea();

    public void addPoint(Point p) {
        points.add(p);
    }

    public void addPoint(double x, double y) {
        addPoint(new Point(x, y));
    }

    public abstract boolean hasEndpoint();
}