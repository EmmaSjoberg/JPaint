package se.miun.emsj2403.dt187g.jpaint;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import se.miun.emsj2403.dt187g.jpaint.geometry.Shape;

/**
 * En klass som representerar en teckning som innehåller flera geometriska
 * figurer.
 *
 * @author Emma Sjöberg (emsj2403)
 * @version 1.0
 */

public class Drawing implements Drawable {

    private String name;
    private String author;
    private List<Shape> shapes;

    public Drawing() {
        name = "";
        author = "";
        shapes = new ArrayList<>();
    }

    public Drawing(String name, String author) throws DrawingException {
        if (name == null || name.isEmpty()
                || author == null || author.isEmpty()) {
            throw new DrawingException(
                    "name and author can't be null or empty");
        }

        this.name = name;
        this.author = author;
        shapes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws DrawingException {
        if (name == null) {
            throw new DrawingException("name can't be null");
        }

        if (name.isEmpty()) {
            throw new DrawingException("name can't be empty");
        }

        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) throws DrawingException {
        if (author == null) {
            throw new DrawingException("author can't be null");
        }

        if (author.isEmpty()) {
            throw new DrawingException("author can't be empty");
        }

        this.author = author;
    }

    public void addShape(Shape shape) {
        if (shape != null) {
            shapes.add(shape);
        }
    }

    public Shape removeShape(int index) {
        return shapes.remove(index);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public int getSize() {
        return shapes.size();
    }

    public double getTotalCircumference() {
        double total = 0;

        for (Shape shape : shapes) {
            total += shape.getCircumference();
        }

        return total;
    }

    public double getTotalArea() {
        double total = 0;

        for (Shape shape : shapes) {
            total += shape.getArea();
        }

        return total;
    }

    @Override
    public void draw() {
        System.out.println("A drawing by " + author + " called " + name);

        for (Shape shape : shapes) {
            shape.draw();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }

    @Override
    public String toString() {
        return "Drawing[name: " + name
                + "; author: " + author
                + "; size: " + getSize()
                + "; circumference: " + getTotalCircumference()
                + "; area: " + getTotalArea()
                + "]";
    }
}