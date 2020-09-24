package es.uvigo.ei.sing.singulator.gui;

import com.alee.laf.WebLookAndFeel;

import es.uvigo.ei.sing.singulator.gui.custom.frames.SplashScreenMain;
import es.uvigo.ei.sing.singulator.gui.simulation.MainFrame;

public class Launcher {
	public static void main(String[] args) {
		// Install WebLaF as application L&F
		WebLookAndFeel.install();
		WebLookAndFeel.setDecorateAllWindows(true);

		// Create initial dialog
		new SplashScreenMain();
		// Create you Swing application here
		new MainFrame();
	}
}
