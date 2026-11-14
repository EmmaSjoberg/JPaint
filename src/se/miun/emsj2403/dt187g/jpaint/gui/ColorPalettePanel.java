package se.miun.emsj2403.dt187g.jpaint.gui;

import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class ColorPalettePanel extends JPanel {

	private final ArrayList<ColorPanel> colorPanels;

	public ColorPalettePanel() {
		colorPanels = new ArrayList<>();
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		setOpaque(false);
	}

	public ColorPalettePanel(ArrayList<ColorPanel> colorPanels) {
		this.colorPanels = colorPanels;
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		setOpaque(false);

		for (ColorPanel cp : colorPanels) {
			add(cp);
		}
	}

	public void addColorPanel(ColorPanel cp) {
		colorPanels.add(cp);
		add(cp);
	}

	public void setMouseListenerForColorPanels(MouseListener listener) {
		for (ColorPanel cp : colorPanels) {
			cp.addMouseListener(listener);
		}
	}
}
