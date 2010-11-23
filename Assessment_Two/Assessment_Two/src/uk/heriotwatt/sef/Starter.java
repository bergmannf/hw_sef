package uk.heriotwatt.sef;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import uk.heriotwatt.sef.view.MainWindow;

public class Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        try {
		          UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel");
		        } catch (Exception e) {
		          System.out.println("Substance Graphite failed to initialize");
		        }
		        MainWindow mw = new MainWindow();
				mw.setVisible(true);
		      }
		});
	}

}
