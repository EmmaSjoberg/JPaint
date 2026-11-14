package se.miun.emsj2403.dt187g.jpaint.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class ColorPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Color color;

	public ColorPanel(Color color) {
		this.color = color;
		setOpaque(false);
		setPreferredSize(new Dimension(30, 30));
		setToolTipText(getColorAsHexString());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();

		try {
			g2.setRenderingHint(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			int size = 24;
			int x = (getWidth() - size) / 2;
			int y = (getHeight() - size) / 2;

			g2.setColor(color);
			g2.fillRoundRect(x, y, size, size, 7, 7);

			g2.setColor(new Color(200, 205, 215));
			g2.drawRoundRect(x, y, size, size, 7, 7);
		} finally {
			g2.dispose();
		}
	}

	public Color getColor() {
		return color;
	}

	public String getColorAsHexString() {
		return String.format(
				"#%02X%02X%02X",
				color.getRed(),
				color.getGreen(),
				color.getBlue());
	}
}
