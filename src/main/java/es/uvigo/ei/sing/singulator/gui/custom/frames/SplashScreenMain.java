package es.uvigo.ei.sing.singulator.gui.custom.frames;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

public class SplashScreenMain {
	private SplashFrame screen;

	public SplashScreenMain() {
		// initialize the splash screen
		init();
		// do something here to simulate the program doing something that
		// is time consuming
		for (int i = 0; i <= 100; i++) {
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			screen.setProgress(Constants.INIT_SPLASH_LOADING + i + "%", i);
		}
		splashScreenDestruct();
	}

	private void splashScreenDestruct() {
		screen.setScreenVisible(false);
	}

	private void init() {
		screen = new SplashFrame(Functions.loadIcon(Constants.ICON_SPLASH));
		screen.setLocationRelativeTo(null);
		screen.setProgressMax(100);
		screen.setScreenVisible(true);
	}
}
