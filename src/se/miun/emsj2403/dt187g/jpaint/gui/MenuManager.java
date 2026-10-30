package se.miun.emsj2403.dt187g.jpaint.gui;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import se.miun.emsj2403.dt187g.jpaint.Drawing;
import se.miun.emsj2403.dt187g.jpaint.DrawingException;
import se.miun.emsj2403.dt187g.jpaint.FileHandler;
import se.miun.emsj2403.dt187g.jpaint.geometry.Circle;
import se.miun.emsj2403.dt187g.jpaint.geometry.Rectangle;

/**
 * <h1>Menu</h1>
 *
 * @author Emma Sjöberg
 * @version 0.1
 * @since 2026-09-16
 */
public class MenuManager {

    private JPaintFrame frame;
    private DrawingPanel drawingPanel;
    private Menu menu;
    private final java.util.Deque<se.miun.emsj2403.dt187g.jpaint.geometry.Shape> redoStack = new java.util.ArrayDeque<>();

    public MenuManager(JPaintFrame frame, DrawingPanel drawingPanel) {
        this.frame = frame;
        this.drawingPanel = drawingPanel;
        this.menu = new Menu();
        createMenu();
    }

    public Menu getMenu() {
        return menu;
    }

    private void createMenu() {
        createFileMenu();
        createEditMenu();
        createFilterMenu();
    }

    private void createFileMenu() {
        String sFile = "File";
        menu.addJMenu(sFile);
        menu.getJMenu(0).setMnemonic(KeyEvent.VK_F);

        menu.addJMenuItem(
                sFile,
                "New...",
                createNewDrawingAction(),
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_N,
                        KeyEvent.CTRL_DOWN_MASK));

        menu.addJMenuItem(
                sFile,
                "Load from Database",
                createLoadAction());

        menu.addJMenuItem(
                sFile,
                "Save to Database",
                createSaveAction());

        menu.addJMenuItem(
                sFile,
                "Info",
                showInfoAction());

        menu.getJMenu(0).addSeparator();

        menu.addJMenuItem(
                sFile,
                "Exit",
                al -> System.exit(0));
    }

    private void createEditMenu() {
        String sEdit = "Edit";
        String sDrawing = "Drawing";

        menu.addJMenu(sEdit);
        menu.addSubJMenu(sEdit, sDrawing);
        menu.getJMenu(1).setMnemonic(KeyEvent.VK_E);

        menu.addJMenuItem(
                sEdit,
                "Undo",
                createUndoAction(),
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_Z,
                        KeyEvent.CTRL_DOWN_MASK));

        menu.addJMenuItem(
                sEdit,
                "Redo",
                createRedoAction(),
                KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));

        menu.addJMenuItem(
                sDrawing,
                "Name...",
                createChangeNameAction());

        menu.addJMenuItem(
                sDrawing,
                "Author...",
                createChangeAuthorAction());


    }

    private void createFilterMenu() {

        JRadioButtonMenuItem allButton = new JRadioButtonMenuItem("All");

        JRadioButtonMenuItem circleButton = new JRadioButtonMenuItem("Circle");

        JRadioButtonMenuItem rectangleButton = new JRadioButtonMenuItem("Rectangle");

        allButton.setSelected(true);

        drawingPanel.setShapeFilter(shape -> true);

        allButton.addActionListener(event -> drawingPanel.setShapeFilter(shape -> true));

        circleButton.addActionListener(event -> drawingPanel.setShapeFilter(
                shape -> shape instanceof Circle));

        rectangleButton.addActionListener(event -> drawingPanel.setShapeFilter(
                shape -> shape instanceof Rectangle));

        ButtonGroup group = new ButtonGroup();

        group.add(allButton);
        group.add(circleButton);
        group.add(rectangleButton);

        JMenu filterMenu = new JMenu("Filter");

        filterMenu.add(allButton);
        filterMenu.add(circleButton);
        filterMenu.add(rectangleButton);

        menu.add(filterMenu);
    }

    private ActionListener createNewDrawingAction() {
        return al -> {

            String name = JOptionPane.showInputDialog(
                    frame,
                    "Enter drawing name:");

            if (name == null) {
                return;
            }

            String author = JOptionPane.showInputDialog(
                    frame,
                    "Enter author:");

            if (author == null) {
                return;
            }

            try {
                Drawing drawing = new Drawing(name, author);

                drawingPanel.setDrawing(drawing);
                redoStack.clear();

                frame.setDrawingTitle(
                        name,
                        author);

                drawingPanel.repaint();

            } catch (DrawingException e) {
                JOptionPane.showMessageDialog(
                        frame,
                        e.getMessage());
            }
        };
    }

    private ActionListener createChangeNameAction() {
        return al -> {

            String name = JOptionPane.showInputDialog(
                    frame,
                    "Enter drawing name:");

            if (name != null) {

                Drawing drawing = drawingPanel.getDrawing();

                try {
                    drawing.setName(name);

                    frame.setDrawingTitle(
                            drawing.getName(),
                            drawing.getAuthor());

                } catch (DrawingException e) {
                    JOptionPane.showMessageDialog(
                            frame,
                            e.getMessage());
                }
            }
        };
    }

    private ActionListener createChangeAuthorAction() {
        return al -> {

            String author = JOptionPane.showInputDialog(
                    frame,
                    "Enter author:");

            if (author != null) {

                Drawing drawing = drawingPanel.getDrawing();

                try {
                    drawing.setAuthor(author);

                    frame.setDrawingTitle(
                            drawing.getName(),
                            drawing.getAuthor());

                } catch (DrawingException e) {
                    JOptionPane.showMessageDialog(
                            frame,
                            e.getMessage());
                }
            }
        };
    }

    private ActionListener createUndoAction() {
        return al -> {

            Drawing drawing = drawingPanel.getDrawing();

            if (drawing.getSize() > 0) {
                redoStack.push(drawing.removeShape(drawing.getSize() - 1));
                drawingPanel.repaint();
            }
        };
    }

    private ActionListener createRedoAction() {
        return event -> {
            if (!redoStack.isEmpty()) {
                drawingPanel.getDrawing().addShape(redoStack.pop());
                drawingPanel.repaint();
            }
        };
    }

    public void clearRedoHistory() {
        redoStack.clear();
    }

    private ActionListener showInfoAction() {
        return al -> {

            Drawing drawing = drawingPanel.getDrawing();

            String drawingTitle;

            if (drawing.getName().isEmpty()
                    && drawing.getAuthor().isEmpty()) {

                drawingTitle = "[Unnamed Drawing]";

            } else if (!drawing.getName().isEmpty()
                    && drawing.getAuthor().isEmpty()) {

                drawingTitle = drawing.getName();

            } else if (drawing.getName().isEmpty()
                    && !drawing.getAuthor().isEmpty()) {

                drawingTitle = "[untitled drawing] by "
                        + drawing.getAuthor();

            } else {

                drawingTitle = drawing.getName()
                        + " by "
                        + drawing.getAuthor();
            }

            JOptionPane.showMessageDialog(
                    frame,
                    drawingTitle
                            + "\nNumber of Shapes: "
                            + drawing.getSize()
                            + String.format(
                                    "\nTotal area: %.2f",
                                    drawing.getTotalArea())
                            + String.format(
                                    "\nTotal circumference: %.2f",
                                    drawing.getTotalCircumference()));
        };
    }

    private ActionListener createSaveAction() {
        return al -> {

            Drawing drawing = drawingPanel.getDrawing();

            String name = drawing.getName();
            String author = drawing.getAuthor();

            if (name == null || name.isEmpty()
                    || author == null || author.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Both name and author must be specified "
                                + "before saving.");

                return;
            }

            FileHandler.save(drawing);
        };
    }

    private ActionListener createLoadAction() {
        return al -> {

            String name = JOptionPane.showInputDialog(
                    frame,
                    "Enter drawing name:");

            if (name == null) {
                return;
            }

            String author = JOptionPane.showInputDialog(
                    frame,
                    "Enter author:");

            if (author == null) {
                return;
            }

            try {

                Drawing drawing = FileHandler.load(name, author);

                drawingPanel.setDrawing(drawing);
                redoStack.clear();

                frame.setDrawingTitle(
                        drawing.getName(),
                        drawing.getAuthor());

                drawingPanel.repaint();

            } catch (DrawingException e) {

                JOptionPane.showMessageDialog(
                        frame,
                        e.getMessage());
            }
        };
    }
}