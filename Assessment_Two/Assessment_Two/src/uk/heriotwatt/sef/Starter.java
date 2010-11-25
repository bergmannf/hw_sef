package uk.heriotwatt.sef;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import uk.heriotwatt.sef.model.Cabin;
import uk.heriotwatt.sef.model.CabinManager;
import uk.heriotwatt.sef.model.GenericFileHandler;
import uk.heriotwatt.sef.model.Plot;
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
		          UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel");
		        } catch (Exception e) {
		          System.out.println("Substance Graphite failed to initialize");
		        }
		        GenericFileHandler<Cabin> cfh = new GenericFileHandler<Cabin>("cabins.csv");
		        GenericFileHandler<Plot> lfh = new GenericFileHandler<Plot>("plots.csv");
		        CabinManager manager = new CabinManager(cfh, lfh);
		        MainWindow mw = new MainWindow(manager);
				mw.setVisible(true);
		      }
		});
	}

}
