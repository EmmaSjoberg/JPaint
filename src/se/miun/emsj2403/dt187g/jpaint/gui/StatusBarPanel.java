package se.miun.emsj2403.dt187g.jpaint.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * En klass som representerar statusfältet i JPaint.
 *
 * @author Emma Sjöberg (emsj2403)
 * @version 1.0
 */

public class StatusBarPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel coordinates;
    private JPanel selectedColor;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private OnChangeListener<StatusBarPanel> listener;

    public StatusBarPanel() {

        setBackground(Color.LIGHT_GRAY);

        coordinates = new JLabel("0, 0");
        selectedColor = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        setLayout(new BorderLayout());

        selectedColor.setBackground(Color.BLACK);

        leftPanel.setBackground(Color.LIGHT_GRAY);

        JLabel coordinatesLabel = new JLabel("Coordinates: ");

        leftPanel.add(coordinatesLabel, BorderLayout.LINE_START);
        leftPanel.add(coordinates, BorderLayout.LINE_END);

        rightPanel.setBackground(Color.LIGHT_GRAY);

        JLabel selectedColorLabel = new JLabel("Selected color: ");

        rightPanel.add(selectedColorLabel, BorderLayout.LINE_START);
        rightPanel.add(selectedColor, BorderLayout.LINE_END);

        this.add(leftPanel, BorderLayout.LINE_START);
        this.add(rightPanel, BorderLayout.LINE_END);
    }

    public void updateCoordinates(int x, int y) {
        coordinates.setText(x + ", " + y);
    }

    public void updateSelectedColor(Color color) {
        selectedColor.setBackground(color);

        if (listener != null) {
            listener.onChange(this);
        }
    }

    public Color getSelectedColor() {
        return selectedColor.getBackground();
    }

    public void setOnChangeListener(OnChangeListener<StatusBarPanel> listener) {
        this.listener = listener;
    }
}