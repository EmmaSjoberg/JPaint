package se.miun.emsj2403.dt187g.jpaint.geometry;

/**
 * En klass som representerar en cirkel och ärver från Shape.
 *
 * @author Emma Sjöberg (emsj2403)
 * @version 1.0
 */

public class Circle extends Shape {
    private static final double PI = 3.14159265;

    public Circle(Point p, String color) {
        super(p, color);
    }

    public Circle(double x, double y, String color) {
        this(new Point(x, y), color);
    }

    public double getRadius() {
        return hasEndpoint()
                ? Math.sqrt(
                        Math.pow(points.get(1).getX() - points.get(0).getX(), 2)
                                + Math.pow(points.get(1).getY() - points.get(0).getY(), 2))
                : 0;
    }

    @Override
    public double getCircumference() {
        return 2 * PI * getRadius();
    }

    @Override
    public double getArea() {
        return PI * getRadius() * getRadius();
    }

    @Override
    public void draw() {
        System.out.println("Drawing a " + toString());
    }

    @Override
    public void draw(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

        g2.setColor(java.awt.Color.decode(getColor()));
        g2.setRenderingHint(
                java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        double radius = getRadius();

        java.awt.Shape circle = new java.awt.geom.Ellipse2D.Double(
                points.get(0).getX() - radius,
                points.get(0).getY() - radius,
                radius * 2,
                radius * 2);

        g2.fill(circle);
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
        return "Circle[start=" + points.get(0)
                + " end=" + (hasEndpoint() ? points.get(1) : "N/A")
                + " radius=" + (hasEndpoint() ? getRadius() : "N/A")
                + " color=" + getColor() + "]";
    }
}