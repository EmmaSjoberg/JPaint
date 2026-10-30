package se.miun.emsj2403.dt187g.jpaint.gui;

import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * En klass som representerar en färgpalett med flera färgpaneler.
 *
 * @author Emma Sjöberg (emsj2403)
 * @version 1.0
 */

public class ColorPalettePanel extends JPanel {

	private ArrayList<ColorPanel> colorPanels; 
	
	public ColorPalettePanel() {
		colorPanels = new ArrayList<>();
        setLayout(new GridLayout());
	}
	
	public ColorPalettePanel(ArrayList<ColorPanel> colorPanels) {
		this.colorPanels = colorPanels;
        setLayout(new GridLayout());
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
