package se.miun.emsj2403.dt187g.jpaint;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import se.miun.emsj2403.dt187g.jpaint.gui.JPaintFrame;

public class AppStart {

	public static void main(String[] args) {
		
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
            // Use the system default look and feel.
        }
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new JPaintFrame().setVisible(true);
			}
		});
	}
}
