package se.miun.emsj2403.dt187g.jpaint.gui;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * En klass som representerar en färgpanel.
 *
 * @author Emma Sjöberg (emsj2403)
 * @version 1.0
 */

public class ColorPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public ColorPanel(Color color) {
		this.setBackground(color);
	}
	
	public Color getColor() {
		return this.getBackground();
	}
	
	public String getColorAsHexString() {
		var color = this.getBackground();
		String red = Integer.toHexString(color.getRed());
		String green = Integer.toHexString(color.getGreen());
		String blue = Integer.toHexString(color.getBlue());
		red = red.length() == 1 ? "0"+red : red;
		green = green.length() == 1 ? "0"+green : green;
		blue = blue.length() == 1 ? "0"+blue : blue;

        return "#" + red + green + blue;
	}
		
}
