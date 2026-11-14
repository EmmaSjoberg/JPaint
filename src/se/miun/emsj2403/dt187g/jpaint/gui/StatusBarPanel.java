package se.miun.emsj2403.dt187g.jpaint.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBarPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel coordinates;
    private JPanel selectedColor;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private OnChangeListener<StatusBarPanel> listener;

    public StatusBarPanel() {

        Color background = new Color(248, 250, 252);
        Color textColor = new Color(100, 116, 139);
        Font font = new Font("SansSerif", Font.PLAIN, 12);

        setLayout(new BorderLayout());
        setBackground(background);
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Coordinates
        coordinates = new JLabel("0, 0");
        coordinates.setFont(font);

        JLabel coordinatesLabel = new JLabel("Coordinates: ");
        coordinatesLabel.setForeground(textColor);
        coordinatesLabel.setFont(font);

        leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        leftPanel.setBackground(background);
        leftPanel.add(coordinatesLabel);
        leftPanel.add(coordinates);

        // Selected color
        JLabel selectedColorLabel = new JLabel("Selected color: ");
        selectedColorLabel.setForeground(textColor);
        selectedColorLabel.setFont(font);

        selectedColor = new JPanel();
        selectedColor.setBackground(Color.BLACK);
        selectedColor.setPreferredSize(new Dimension(14, 14));
        selectedColor.setBorder(
                BorderFactory.createLineBorder(new Color(200, 205, 215)));

        rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightPanel.setBackground(background);
        rightPanel.add(selectedColorLabel);
        rightPanel.add(selectedColor);

        add(leftPanel, BorderLayout.LINE_START);
        add(rightPanel, BorderLayout.LINE_END);
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
