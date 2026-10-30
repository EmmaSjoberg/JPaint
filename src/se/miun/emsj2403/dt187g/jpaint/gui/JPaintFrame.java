package se.miun.emsj2403.dt187g.jpaint.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * En klass som representerar huvudfönstret för JPaint och hanterar
 * programmets grafiska komponenter och mushändelser.
 *
 * @author Emma Sjöberg (emsj2403)
 * @version 1.0
 */

public class JPaintFrame extends JFrame {

    public static final String APP_NAME = "JPaint";
    private String drawingTitle;
    private Container c = this.getContentPane();
    private StatusBarPanel statusBarPanel;
    private DrawingPanel drawingPanel;
    private ColorPalettePanel colorPalettePanel;
    private MenuManager menuManager;

    public JPaintFrame() {
        init();
    }

    private void init() {

        setSize(980, 680);
        setMinimumSize(new Dimension(700, 480));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIconImage(new ImageIcon("img/pen.png").getImage());

        drawingTitle = APP_NAME;
        setTitle(drawingTitle);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(0, 58));
        topPanel.setBackground(new Color(245, 247, 250));
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        colorPalettePanel = new ColorPalettePanel();

        colorPalettePanel.addColorPanel(new ColorPanel(Color.BLACK));
        colorPalettePanel.addColorPanel(new ColorPanel(Color.RED));
        colorPalettePanel.addColorPanel(new ColorPanel(Color.BLUE));
        colorPalettePanel.addColorPanel(new ColorPanel(Color.GREEN));
        colorPalettePanel.addColorPanel(new ColorPanel(Color.YELLOW));
        colorPalettePanel.addColorPanel(new ColorPanel(Color.ORANGE));
        colorPalettePanel.addColorPanel(new ColorPanel(Color.PINK));
        colorPalettePanel.addColorPanel(new ColorPanel(Color.CYAN));
        colorPalettePanel.addColorPanel(new ColorPanel(Color.MAGENTA));

        String[] shapes = { "Rectangle", "Circle" };

        JComboBox<String> shapeComboBox = new JComboBox<>(shapes);
        shapeComboBox.setSelectedItem("Rectangle");
        shapeComboBox.setPreferredSize(new Dimension(140, 36));
        shapeComboBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        drawingPanel = new DrawingPanel();

        drawingPanel.setDrawColor(Color.BLACK);
        drawingPanel.setActiveShape("Rectangle");

        shapeComboBox.addActionListener(e -> {
            drawingPanel.setActiveShape((String) shapeComboBox.getSelectedItem());
        });

        menuManager = new MenuManager(this, drawingPanel);
        setJMenuBar(menuManager.getMenu());

        CustomMouseAdapter mouseAdapter = new CustomMouseAdapter();

        drawingPanel.addMouseListener(mouseAdapter);
        drawingPanel.addMouseMotionListener(mouseAdapter);

        statusBarPanel = new StatusBarPanel();
        statusBarPanel.setPreferredSize(new Dimension(0, 30));

        OnChangeListener<StatusBarPanel> listener = new OnChangeListener<StatusBarPanel>() {

            @Override
            public void onChange(StatusBarPanel object) {
                drawingPanel.setDrawColor(object.getSelectedColor());
            }
        };

        statusBarPanel.setOnChangeListener(listener);

        colorPalettePanel.setMouseListenerForColorPanels(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ColorPanel colorPanel = (ColorPanel) e.getSource();
                statusBarPanel.updateSelectedColor(colorPanel.getColor());
            }
        });

        topPanel.setLayout(new BorderLayout());
        topPanel.add(colorPalettePanel, BorderLayout.CENTER);
        topPanel.add(shapeComboBox, BorderLayout.LINE_END);

        c.add(topPanel, BorderLayout.PAGE_START);
        c.add(drawingPanel, BorderLayout.CENTER);
        c.add(statusBarPanel, BorderLayout.PAGE_END);
    }

    public void setDrawingTitle(String name, String author) {

        if (name.isEmpty() && author.isEmpty()) {
            drawingTitle = APP_NAME;

        } else if (!name.isEmpty() && author.isEmpty()) {
            drawingTitle = APP_NAME + " - " + name;

        } else if (name.isEmpty() && !author.isEmpty()) {
            drawingTitle = APP_NAME + " - [untitled drawing] by " + author;

        } else {
            drawingTitle = APP_NAME + " - " + name + " by " + author;
        }

        updateHeader();
    }

    public void updateHeader() {
        setTitle(drawingTitle);
    }

    public String getDrawingTitle() {
        return drawingTitle;
    }

    class CustomMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            drawingPanel.setStartPoint(e.getX(), e.getY());
            drawingPanel.setEndPoint(e.getX(), e.getY());
            drawingPanel.setDrawIsActive(true);
            drawingPanel.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (((Component) e.getSource()).getMousePosition() != null) {
                statusBarPanel.updateCoordinates(e.getX(), e.getY());

                drawingPanel.setEndPoint(e.getX(), e.getY());
                drawingPanel.repaint();
            } else {
                statusBarPanel.updateCoordinates(0, 0);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            drawingPanel.setEndPoint(e.getX(), e.getY());
            drawingPanel.addShape();
            menuManager.clearRedoHistory();
            drawingPanel.setDrawIsActive(false);
            drawingPanel.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            statusBarPanel.updateCoordinates(0, 0);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            statusBarPanel.updateCoordinates(e.getX(), e.getY());
        }
    }
}