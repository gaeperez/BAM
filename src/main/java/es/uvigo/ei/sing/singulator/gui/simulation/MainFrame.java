package es.uvigo.ei.sing.singulator.gui.simulation;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.alee.laf.rootpane.WebFrame;
import com.alee.utils.ImageUtils;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.constant.GUIProperties;
import es.uvigo.ei.sing.singulator.gui.custom.dialogs.InitialDialog;
import es.uvigo.ei.sing.singulator.gui.main.MainPanel;

public class MainFrame extends WebFrame {

	private static final long serialVersionUID = 1L;

	private MainPanel mainPanel;

	public MainFrame() {
		init();

		// Show initial dialog
		int option = 1;
		if (Boolean.valueOf(GUIProperties.getAppShowDialog())) {
			option = new InitialDialog().getPressed();
		}

		mainPanel.setInitialOption(option);
	}

	public void init() {
		{
			setTitle(Constants.MAIN_TITLE);
			setIconImages(ImageUtils.toImagesList(Functions.getIconLogoImages()));
			setMinimumSize(new Dimension(1000, 813));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setShadeWidth(0);

			mainPanel = new MainPanel();

			setContentPane(mainPanel);

			pack();
			setLocationRelativeTo(null);
			setVisible(true);
		}
	}
}
