package es.uvigo.ei.sing.singulator.gui.custom.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.alee.extended.image.DisplayType;
import com.alee.extended.image.WebImage;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebFrame;
import com.alee.utils.ImageUtils;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

public class SplashFrame extends WebFrame {
	private static final long serialVersionUID = 1L;

	private WebProgressBar progressBar;
	private ImageIcon imageIcon;
	private WebImage logo;

	public SplashFrame(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;

		init();
	}

	void init() {
		{
			setIconImages(ImageUtils.toImagesList(Functions.getIconLogoImages()));
			setShowWindowButtons(false);
			setShowTitleComponent(false);
			setShowResizeCorner(false);
			setShowMinimizeButton(false);
			setShowMenuBar(false);
			setShowMaximizeButton(false);
			setShowCloseButton(false);
			setResizable(false);
			setAlwaysOnTop(true);
			getContentPane().setLayout(new BorderLayout());
		}
		{
			WebPanel contentPanel = new WebPanel();
			contentPanel.setBorder(new LineBorder(Color.decode(Constants.COLOR_ORANGE_TABS), 5, false));
			setContentPane(contentPanel);
			{
				WebPanel centerPanel = new WebPanel();
				centerPanel.setMargin(10, 5, 10, 0);
				centerPanel.setBackground(Color.decode(Constants.COLOR_WHITE));
				centerPanel.setLayout(new GridLayout());
				{
					logo = new WebImage(imageIcon);
					logo.setDisplayType(DisplayType.fitComponent);

					centerPanel.add(logo);
				}
				getContentPane().add(centerPanel, BorderLayout.CENTER);
			}
			{
				WebPanel eastPanel = new WebPanel();
				eastPanel.setMargin(10, 0, 10, 5);
				eastPanel.setBackground(Color.decode(Constants.COLOR_WHITE));
				eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
				{
					WebLabel lblTitle = new WebLabel(Constants.INIT_DIALOG_TITLE);
					lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
					lblTitle.setBoldFont();
					lblTitle.setFontSize(16);
					lblTitle.setMargin(0, 0, 10, 0);

					eastPanel.add(lblTitle);
				}
				{
					WebLabel lblVersion = new WebLabel(Constants.INIT_SPLASH_VERSION);

					eastPanel.add(lblVersion);
				}
				{
					WebLabel lblJava = new WebLabel(Constants.INIT_SPLASH_JAVA);
					eastPanel.add(lblJava);
				}
				{
					WebImage iconUvigo = new WebImage(Functions.loadIcon(Constants.ICON_UVIGO));
					iconUvigo.setMargin(0, 0, 0, 80);
					iconUvigo.setDisplayType(DisplayType.fitComponent);

					WebImage iconSing = new WebImage(Functions.loadIcon(Constants.ICON_SING));
					iconSing.setDisplayType(DisplayType.fitComponent);

					GroupPanel groupPanel = new GroupPanel(iconUvigo, iconSing);
					groupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
					eastPanel.add(groupPanel);
				}

				getContentPane().add(eastPanel, BorderLayout.EAST);
			}
			{
				progressBar = new WebProgressBar();

				getContentPane().add(progressBar, BorderLayout.SOUTH);
			}
		}
		pack();
	}

	public void setProgressMax(int maxProgress) {
		progressBar.setMaximum(maxProgress);
	}

	public void setProgress(int progress) {
		final int theProgress = progress;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				progressBar.setValue(theProgress);
			}
		});
	}

	public void setProgress(String message, int progress) {
		final int theProgress = progress;
		final String theMessage = message;
		setProgress(progress);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				progressBar.setValue(theProgress);
				setMessage(theMessage);
			}
		});
	}

	public void setScreenVisible(boolean b) {
		final boolean boo = b;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVisible(boo);
			}
		});
	}

	private void setMessage(String message) {
		if (message == null) {
			message = "";
			progressBar.setStringPainted(false);
		} else {
			progressBar.setStringPainted(true);
		}
		progressBar.setString(message);
	}
}