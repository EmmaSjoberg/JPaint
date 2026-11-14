package se.miun.emsj2403.dt187g.jpaint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import se.miun.emsj2403.dt187g.jpaint.geometry.Circle;
import se.miun.emsj2403.dt187g.jpaint.geometry.Point;
import se.miun.emsj2403.dt187g.jpaint.geometry.Rectangle;
import se.miun.emsj2403.dt187g.jpaint.geometry.Shape;

public class FileHandler {

        private static final String DB_URL = "jdbc:sqlite:jpaint.db";

        private FileHandler() {
        }

        public static void save(Drawing drawing) {

                createTables();

                String findDocument = "SELECT documentId FROM Document "
                                + "WHERE name = ? AND author = ?";

                String insertDocument = "INSERT INTO Document(name, author) "
                                + "VALUES(?, ?)";

                String deleteShapes = "DELETE FROM Shape WHERE documentId = ?";

                String insertShape = "INSERT INTO Shape("
                                + "documentId, type, x1, y1, x2, y2, color"
                                + ") VALUES(?, ?, ?, ?, ?, ?, ?)";

                try (Connection connection = DriverManager.getConnection(DB_URL)) {

                        connection.setAutoCommit(false);

                        try {

                                int documentId = -1;

                                try (PreparedStatement statement = connection.prepareStatement(findDocument)) {

                                        statement.setString(1, drawing.getName());
                                        statement.setString(2, drawing.getAuthor());

                                        try (ResultSet result = statement.executeQuery()) {

                                                if (result.next()) {
                                                        documentId = result.getInt("documentId");
                                                }
                                        }
                                }

                                if (documentId == -1) {

                                        try (PreparedStatement statement = connection.prepareStatement(
                                                        insertDocument,
                                                        Statement.RETURN_GENERATED_KEYS)) {

                                                statement.setString(
                                                                1,
                                                                drawing.getName());

                                                statement.setString(
                                                                2,
                                                                drawing.getAuthor());

                                                statement.executeUpdate();

                                                try (ResultSet keys = statement.getGeneratedKeys()) {

                                                        if (keys.next()) {
                                                                documentId = keys.getInt(1);
                                                        }
                                                }
                                        }

                                } else {

                                        try (PreparedStatement statement = connection.prepareStatement(
                                                        deleteShapes)) {

                                                statement.setInt(1, documentId);
                                                statement.executeUpdate();
                                        }
                                }

                                for (Shape shape : drawing.getShapes()) {

                                        Point start = shape.getPoints().get(0);

                                        Point end = shape.getPoints().get(1);

                                        try (PreparedStatement statement = connection.prepareStatement(
                                                        insertShape)) {

                                                statement.setInt(
                                                                1,
                                                                documentId);

                                                statement.setString(
                                                                2,
                                                                shape.getClass()
                                                                                .getSimpleName());

                                                statement.setInt(
                                                                3,
                                                                (int) start.getX());

                                                statement.setInt(
                                                                4,
                                                                (int) start.getY());

                                                statement.setInt(
                                                                5,
                                                                (int) end.getX());

                                                statement.setInt(
                                                                6,
                                                                (int) end.getY());

                                                statement.setString(
                                                                7,
                                                                shape.getColor());

                                                statement.executeUpdate();
                                        }
                                }

                                connection.commit();

                        } catch (SQLException e) {

                                connection.rollback();

                                JOptionPane.showMessageDialog(
                                                null,
                                                "Could not save drawing: "
                                                                + e.getMessage());

                        } finally {
                                connection.setAutoCommit(true);
                        }

                } catch (SQLException e) {

                        JOptionPane.showMessageDialog(
                                        null,
                                        "Could not connect to database: "
                                                        + e.getMessage());
                }
        }

        public static Drawing load(
                        String name,
                        String author) throws DrawingException {

                createTables();

                String findDocument = "SELECT documentId, name, author "
                                + "FROM Document "
                                + "WHERE name = ? AND author = ?";

                String findShapes = "SELECT type, x1, y1, x2, y2, color "
                                + "FROM Shape "
                                + "WHERE documentId = ?";

                Drawing drawing = new Drawing();

                try (Connection connection = DriverManager.getConnection(DB_URL);

                                PreparedStatement documentStatement = connection.prepareStatement(
                                                findDocument)) {

                        documentStatement.setString(1, name);
                        documentStatement.setString(2, author);

                        try (ResultSet documentResult = documentStatement.executeQuery()) {

                                if (!documentResult.next()) {
                                        throw new DrawingException(
                                                        "No drawing found with name '"
                                                                        + name
                                                                        + "' and author '"
                                                                        + author
                                                                        + "'.");
                                }

                                int documentId = documentResult.getInt(
                                                "documentId");

                                drawing.setName(
                                                documentResult.getString(
                                                                "name"));

                                drawing.setAuthor(
                                                documentResult.getString(
                                                                "author"));

                                try (PreparedStatement shapeStatement = connection.prepareStatement(
                                                findShapes)) {

                                        shapeStatement.setInt(
                                                        1,
                                                        documentId);

                                        try (ResultSet shapeResult = shapeStatement.executeQuery()) {

                                                while (shapeResult.next()) {

                                                        String type = shapeResult.getString(
                                                                        "type");

                                                        int x1 = shapeResult.getInt(
                                                                        "x1");

                                                        int y1 = shapeResult.getInt(
                                                                        "y1");

                                                        int x2 = shapeResult.getInt(
                                                                        "x2");

                                                        int y2 = shapeResult.getInt(
                                                                        "y2");

                                                        String color = shapeResult.getString(
                                                                        "color");

                                                        Shape shape = null;

                                                        if (type.equals("Rectangle")) {

                                                                shape = new Rectangle(
                                                                                new Point(x1, y1),
                                                                                color);

                                                        } else if (type.equals("Circle")) {

                                                                shape = new Circle(
                                                                                new Point(x1, y1),
                                                                                color);
                                                        }

                                                        if (shape != null) {

                                                                shape.addPoint(
                                                                                new Point(x2, y2));

                                                                drawing.addShape(shape);
                                                        }
                                                }
                                        }
                                }
                        }

                } catch (SQLException e) {

                        JOptionPane.showMessageDialog(
                                        null,
                                        "Could not load drawing: "
                                                        + e.getMessage());
                }

                return drawing;
        }

        private static void createTables() {

                String createDocumentTable = "CREATE TABLE IF NOT EXISTS Document ("
                                + "documentId INTEGER "
                                + "PRIMARY KEY AUTOINCREMENT, "
                                + "name TEXT NOT NULL, "
                                + "author TEXT NOT NULL, "
                                + "UNIQUE(name, author)"
                                + ")";

                String createShapeTable = "CREATE TABLE IF NOT EXISTS Shape ("
                                + "shapeId INTEGER "
                                + "PRIMARY KEY AUTOINCREMENT, "
                                + "documentId INTEGER NOT NULL, "
                                + "type TEXT NOT NULL, "
                                + "x1 INTEGER NOT NULL, "
                                + "y1 INTEGER NOT NULL, "
                                + "x2 INTEGER NOT NULL, "
                                + "y2 INTEGER NOT NULL, "
                                + "color TEXT NOT NULL, "
                                + "FOREIGN KEY(documentId) "
                                + "REFERENCES Document(documentId)"
                                + ")";

                try (Connection connection = DriverManager.getConnection(DB_URL);

                                Statement statement = connection.createStatement()) {

                        statement.execute(createDocumentTable);
                        statement.execute(createShapeTable);

                } catch (SQLException e) {

                        JOptionPane.showMessageDialog(
                                        null,
                                        "Could not create database tables: "
                                                        + e.getMessage());
                }
        }
}