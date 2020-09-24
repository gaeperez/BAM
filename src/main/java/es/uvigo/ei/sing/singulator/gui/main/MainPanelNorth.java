package es.uvigo.ei.sing.singulator.gui.main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.alee.extended.window.WebProgressDialog;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.utils.ThreadUtils;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

public class MainPanelNorth extends WebPanel {

	private static final long serialVersionUID = 1L;

	private WebMenuBar menuBar;

	private WebToolBar toolBar;

	private MainPanel parent;

	public MainPanelNorth(MainPanel parent) {
		this.parent = parent;

		init();
	}

	public void init() {
		{
			setLayout(new GridLayout(2, 0));
		}
		{
			menuBar = new WebMenuBar();

			setupMenuBar();

			add(menuBar);
		}
		{
			toolBar = new WebToolBar(SwingConstants.HORIZONTAL);
			toolBar.setToolbarStyle(ToolbarStyle.attached);
			toolBar.setFloatable(false);

			setupToolBar();

			add(toolBar);
		}
	}

	private void setupMenuBar() {
		{
			WebMenu menuFile = new WebMenu(Constants.MENU_FILE);
			{
				WebMenuItem itemNew = new WebMenuItem(Constants.MENU_FILE_NEW,
						Functions.loadIcon(Constants.ICON_NEW_16), Hotkey.CTRL_N);
				itemNew.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pressNew();
					}
				});

				menuFile.add(itemNew);
			}
			menuFile.addSeparator();
			{
				WebMenuItem itemSave = new WebMenuItem(Constants.MENU_FILE_SAVE,
						Functions.loadIcon(Constants.ICON_SAVE_16), Hotkey.CTRL_S);
				itemSave.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pressSave();
					}
				});

				menuFile.add(itemSave);
			}
			{
				WebMenuItem itemLoad = new WebMenuItem(Constants.MENU_FILE_LOAD,
						Functions.loadIcon(Constants.ICON_LOAD_16), Hotkey.CTRL_L);
				itemLoad.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pressLoad(true);
					}
				});

				menuFile.add(itemLoad);
			}
			menuFile.addSeparator();
			{
				WebMenuItem itemVisualization = new WebMenuItem(Constants.MENU_FILE_VISUALIZATION,
						Functions.loadIcon(Constants.ICON_VISUALIZE_16));
				itemVisualization.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pressVisualize();
					}
				});

				menuFile.add(itemVisualization);
			}
			menuFile.addSeparator();
			{
				WebMenuItem itemExit = new WebMenuItem(Constants.MENU_FILE_EXIT,
						Functions.loadIcon(Constants.ICON_POWER_16), Hotkey.ALT_F4);
				itemExit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});

				menuFile.add(itemExit);
			}
			menuBar.add(menuFile);
		}
		{
			WebMenu menuValidation = new WebMenu(Constants.MENU_VALIDATION);
			{
				WebMenuItem itemValidationCurrent = new WebMenuItem(Constants.MENU_VALIDATION_CURRENT,
						Functions.loadIcon(Constants.ICON_VALIDATE_CURRENT_16), Hotkey.CTRL_SHIFT_V);
				itemValidationCurrent.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pressValidate();
					}
				});

				menuValidation.add(itemValidationCurrent);
			}
			{
				WebMenuItem itemValidationAll = new WebMenuItem(Constants.MENU_VALIDATION_ALL,
						Functions.loadIcon(Constants.ICON_VALIDATE_ALL_16));
				itemValidationAll.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pressValidateAll();
					}
				});

				menuValidation.add(itemValidationAll);
			}
			menuBar.add(menuValidation);
		}
		{
			WebMenu menuSettings = new WebMenu(Constants.MENU_SETTINGS);
			{
				WebMenuItem itemPreferences = new WebMenuItem(Constants.MENU_SETTINGS_PREFERENCES,
						Functions.loadIcon(Constants.ICON_SETTINGS_16));
				itemPreferences.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						parent.showPreferences();
					}
				});

				menuSettings.add(itemPreferences);
			}
			menuBar.add(menuSettings);
		}
		{
			WebMenu menuHelp = new WebMenu(Constants.MENU_HELP);
			{
				WebMenuItem itemContents = new WebMenuItem(Constants.MENU_HELP_CONTENTS,
						Functions.loadIcon(Constants.ICON_HELP_16));
				itemContents.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("itemContents");
					}
				});

				menuHelp.add(itemContents);
			}
			menuHelp.addSeparator();
			{
				WebMenuItem itemAbout = new WebMenuItem(Constants.MENU_HELP_ABOUT);
				itemAbout.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						parent.showAbout();
					}
				});

				menuHelp.add(itemAbout);
			}
			menuBar.add(menuHelp);
		}
	}

	private void setupToolBar() {
		{
			WebButton btnNew = WebButton.createIconWebButton(Functions.loadIcon(Constants.ICON_NEW_16),
					StyleConstants.smallRound, true);
			TooltipManager.setTooltip(btnNew, Constants.TOOL_NEW_TIP, TooltipWay.down, 1000);
			// Listener
			btnNew.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pressNew();
				}
			});

			toolBar.add(btnNew);
		}
		{
			WebButton btnSave = WebButton.createIconWebButton(Functions.loadIcon(Constants.ICON_SAVE_16),
					StyleConstants.smallRound, true);
			TooltipManager.setTooltip(btnSave, Constants.TOOL_SAVE_TIP, TooltipWay.down, 1000);
			// Listener
			btnSave.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pressSave();
				}
			});

			toolBar.add(btnSave);
		}
		{
			WebButton btnLoad = WebButton.createIconWebButton(Functions.loadIcon(Constants.ICON_LOAD_16),
					StyleConstants.smallRound, true);
			TooltipManager.setTooltip(btnLoad, Constants.TOOL_LOAD_TIP, TooltipWay.down, 1000);
			// Listener
			btnLoad.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pressLoad(true);
				}
			});

			toolBar.add(btnLoad);
		}
		toolBar.addSeparator(5);
		{
			WebButton btnValidateCurrent = WebButton.createIconWebButton(
					Functions.loadIcon(Constants.ICON_VALIDATE_CURRENT_16), StyleConstants.smallRound, true);
			TooltipManager.setTooltip(btnValidateCurrent, Constants.TOOL_VALIDATE_CURRENT_TIP, TooltipWay.down, 1000);
			// Listener
			btnValidateCurrent.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pressValidate();
				}
			});

			toolBar.add(btnValidateCurrent);
		}
		{
			WebButton bntValidateAll = WebButton.createIconWebButton(Functions.loadIcon(Constants.ICON_VALIDATE_ALL_16),
					StyleConstants.smallRound, true);
			TooltipManager.setTooltip(bntValidateAll, Constants.TOOL_VALIDATE_ALL_TIP, TooltipWay.down, 1000);
			// Listener
			bntValidateAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pressValidateAll();
				}
			});

			toolBar.add(bntValidateAll);
		}
		toolBar.addSeparator(5);
		{
			WebButton btnVisualizeJson = WebButton.createIconWebButton(Functions.loadIcon(Constants.ICON_VISUALIZE_16),
					StyleConstants.smallRound, true);
			TooltipManager.setTooltip(btnVisualizeJson, Constants.TOOL_VISUALIZE_JSON_TIP, TooltipWay.down, 1000);
			// Listener
			btnVisualizeJson.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pressVisualize();
				}
			});

			toolBar.add(btnVisualizeJson);
		}
	}

	public void pressNew() {
		int confirmation = JOptionPane.showConfirmDialog(parent, Constants.TOOLBAR_NEW_TEXT,
				Constants.TOOLBAR_NEW_TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirmation == JOptionPane.OK_OPTION) {
			parent.deleteAllInformation();
			parent.disableAllTabs();
		}
	}

	public void pressSave() {
		// Validate all cards
		String errors = parent.validateAllCards();

		// No errors on panels
		if (errors.equals(Constants.TOOLBAR_VALIDATE_ALL)) {
			int confirmation = JOptionPane.showConfirmDialog(parent, Constants.TOOLBAR_SAVE_TEXT,
					Constants.TOOLBAR_SAVE_TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (confirmation == JOptionPane.OK_OPTION) {
				// Load dialog
				final WebProgressDialog progress = new WebProgressDialog(SwingUtilities.getWindowAncestor(parent),
						Constants.SAVE_PROGRESS_TITLE);
				progress.setIndeterminate(true);
				progress.setShowProgressText(false);
				progress.setText(Constants.SAVE_PROGRESS_BODY_START);

				// Starting updater thread
				final Thread updater = new Thread(new Runnable() {
					@Override
					public void run() {
						boolean isSaved = parent.saveJson();
						progress.setText(Constants.SAVE_PROGRESS_BODY_END);
						ThreadUtils.sleepSafely(1000);
						progress.setVisible(false);

						if (isSaved) {
							Functions.showTimeNotification(parent, Functions.loadIcon(Constants.ICON_APPROVE_16), 5000,
									Constants.TOOLBAR_SAVE_NOTIFICATION);
						}
					}
				});
				updater.setDaemon(true);
				updater.start();

				// Displaying dialog
				progress.setModal(true);
				progress.setVisible(true);
			}
		} else {
			Functions.showErrorDialog(parent, Constants.TOOLBAR_VALIDATE_TITLE, errors);
		}
	}

	public void pressLoad(boolean needConfirmation) {
		int confirmation = -1;
		if (needConfirmation) {
			confirmation = JOptionPane.showConfirmDialog(parent, Constants.TOOLBAR_LOAD_TEXT,
					Constants.TOOLBAR_LOAD_TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		}

		if (confirmation == JOptionPane.OK_OPTION || !needConfirmation) {
			File selectedFile = parent.showChooser();

			if (selectedFile != null) {
				// Load dialog
				final WebProgressDialog progress = new WebProgressDialog(SwingUtilities.getWindowAncestor(parent),
						Constants.LOAD_PROGRESS_TITLE);
				progress.setIndeterminate(true);
				progress.setShowProgressText(false);
				progress.setText(Constants.LOAD_PROGRESS_BODY_START);

				// Starting updater thread
				final Thread updater = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							parent.loadJSON(selectedFile);
							progress.setText(Constants.LOAD_PROGRESS_BODY_END);
							ThreadUtils.sleepSafely(1000);
							progress.setVisible(false);

							Functions.showTimeNotification(parent, Functions.loadIcon(Constants.ICON_APPROVE_16), 5000,
									Constants.TOOLBAR_LOAD_NOTIFICATION);
						} catch (Exception e) {
							progress.setVisible(false);
							Functions.showErrorDialog(parent, Constants.LOAD_ERROR_TITLE,
									Constants.LOAD_ERROR_BODY + Functions.printStackTraceInString(e.getStackTrace()));
							e.printStackTrace();
						}
					}
				});
				updater.setDaemon(true);
				updater.start();

				// Displaying dialog
				progress.setModal(true);
				progress.setVisible(true);
			}
		}
	}

	public void pressValidateAll() {
		String errors = parent.validateAllCards();
		// No errors on panels
		if (!errors.equals(Constants.TOOLBAR_VALIDATE_ALL))
			Functions.showErrorDialog(parent, Constants.TOOLBAR_VALIDATE_TITLE, errors);
		else
			Functions.showTimeNotification(parent, Functions.loadIcon(Constants.ICON_APPROVE_16), 5000,
					Constants.TOOLBAR_VALIDATE_OK);
	}

	public void pressValidate() {
		if (parent.validateCurrentCard())
			Functions.showTimeNotification(parent, Functions.loadIcon(Constants.ICON_APPROVE_16), 5000,
					Constants.TOOLBAR_VALIDATE_OK);
	}

	public void pressVisualize() {
		String errors = parent.validateAllCards();
		// No errors on panels
		if (!errors.equals(Constants.TOOLBAR_VALIDATE_ALL))
			Functions.showErrorDialog(parent, Constants.TOOLBAR_VALIDATE_TITLE, errors);
		else {
			parent.setTextInVisualizer();
		}
	}
}
