package se.miun.emsj2403.dt187g.jpaint.geometry;

public class Rectangle extends Shape {

    public Rectangle(Point p, String color) {
        super(p, color);
    }

    public Rectangle(double x, double y, String color) {
        this(new Point(x, y), color);
    }

    public double getWidth() {
        return hasEndpoint()
                ? Math.abs(points.get(1).getX() - points.get(0).getX())
                : 0;
    }

    public double getHeight() {
        return hasEndpoint()
                ? Math.abs(points.get(1).getY() - points.get(0).getY())
                : 0;
    }

    @Override
    public double getCircumference() {
        return 2 * getWidth() + 2 * getHeight();
    }

    @Override
    public double getArea() {
        return getWidth() * getHeight();
    }

    @Override
    public void draw() {
        System.out.println("Drawing a " + toString());
    }

    @Override
    public void draw(java.awt.Graphics g) {
        g.setColor(java.awt.Color.decode(getColor()));

        g.fillRect(
                (int) points.get(0).getX(),
                (int) points.get(0).getY(),
                (int) getWidth(),
                (int) getHeight());
    }

    @Override
    public void addPoint(Point p) {
        if (hasEndpoint()) {
            points.set(1, p);
        } else {
            points.add(p);
        }
    }

    @Override
    public void addPoint(double x, double y) {
        addPoint(new Point(x, y));
    }

    @Override
    public boolean hasEndpoint() {
        return points.size() > 1;
    }

    @Override
    public String toString() {
        return "Rectangle[start=" + points.get(0)
                + " end=" + (hasEndpoint() ? points.get(1) : "N/A")
                + " width=" + (hasEndpoint() ? getWidth() : "N/A")
                + " height=" + (hasEndpoint() ? getHeight() : "N/A")
                + " color=" + getColor() + "]";
    }
}