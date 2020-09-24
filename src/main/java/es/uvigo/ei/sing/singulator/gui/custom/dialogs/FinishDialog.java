package es.uvigo.ei.sing.singulator.gui.custom.dialogs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.WindowConstants;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.list.CheckBoxListModel;
import com.alee.extended.list.WebCheckBoxList;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.utils.FileUtils;
import com.alee.utils.swing.DialogOptions;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.constant.GUIProperties;
import es.uvigo.ei.sing.singulator.gui.main.MainPanel;

public class FinishDialog extends WebDialog {
	private static final long serialVersionUID = 1L;

	private WebCheckBoxList webCheckBoxList;
	private WebButton btnDirOutput;
	private WebTextField packageName;

	private WebLabel lblExports;
	private WebLabel lblOutput;
	private WebLabel lblPackage;

	private double maxLabelLenght = -1.0;

	private boolean isFinish;

	private MainPanel parent;

	public FinishDialog(MainPanel parent) {
		this.parent = parent;
		this.isFinish = false;

		init();
	}

	void init() {
		{
			setTitle(Constants.FINISH_TITLE);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setModal(true);
			setResizable(false);
			setShadeWidth(0);
			setLayout(new BorderLayout());
		}
		{
			{
				// Calculate label extensions (width)
				lblExports = new WebLabel(Constants.FINISH_EXPORTS);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblExports.getPreferredSize().getWidth());

				lblOutput = new WebLabel(Constants.FINISH_OUTPUT_FOLDER);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblOutput.getPreferredSize().getWidth());

				lblPackage = new WebLabel(Constants.FINISH_PACKAGE_NAME);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblPackage.getPreferredSize().getWidth());
			}
			{
				WebPanel panelCenter = new WebPanel(new VerticalFlowLayout(0, 10));
				panelCenter.setMargin(7);
				{
					{
						webCheckBoxList = new WebCheckBoxList(createCheckBoxListModel());
						webCheckBoxList.setVisibleRowCount(3);
						webCheckBoxList.setSelectedIndex(0);
						webCheckBoxList.setEditable(false);
						webCheckBoxList.setMultiplySelectionAllowed(false);
						webCheckBoxList.putClientProperty(GroupPanel.FILL_CELL, true);
						panelCenter
								.add(new GroupPanel(
										(50 + Functions.calculateLabelDifference(maxLabelLenght,
												lblExports.getPreferredSize().getWidth())),
										lblExports, webCheckBoxList));
					}
					{
						btnDirOutput = new WebButton(Constants.GENERAL_PROMPT_DIR);
						btnDirOutput.putClientProperty(GroupPanel.FILL_CELL, true);
						btnDirOutput.addActionListener(new ActionListener() {
							private WebDirectoryChooser directoryChooser = null;

							@Override
							public void actionPerformed(final ActionEvent e) {
								if (directoryChooser == null) {
									directoryChooser = new WebDirectoryChooser(null);
								}
								directoryChooser.setVisible(true);

								if (directoryChooser.getResult() == DialogOptions.OK_OPTION) {
									final File file = directoryChooser.getSelectedDirectory();
									btnDirOutput.setIcon(FileUtils.getFileIcon(file));
									btnDirOutput.setText(file.getAbsolutePath());
								}
							}
						});
						btnDirOutput.setText(GUIProperties.getAppOutput());
						panelCenter.add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
								lblOutput.getPreferredSize().getWidth())), lblOutput, btnDirOutput));
					}
					{
						packageName = new WebTextField(30);
						packageName.setText(GUIProperties.getAppPackage());
						panelCenter.add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
								lblPackage.getPreferredSize().getWidth())), lblPackage, packageName));
					}
				}
				add(panelCenter, BorderLayout.CENTER);
			}
			{
				WebPanel panelSouth = new WebPanel(new GridLayout());
				panelSouth.setMargin(7);
				{
					WebButton cancel = new WebButton(Constants.FINISH_CANCEL);
					cancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							setVisible(false);
							dispose();
						}
					});
					HotkeyManager.registerHotkey(this, cancel, Hotkey.ESCAPE);
					panelSouth.add(cancel);

					WebButton finish = new WebButton(Constants.FINISH_OK);
					finish.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							isFinish = true;
							setVisible(false);
							dispose();
						}
					});
					HotkeyManager.registerHotkey(this, finish, Hotkey.ENTER);
					panelSouth.add(finish);
				}
				add(panelSouth, BorderLayout.SOUTH);
			}

		}
		pack();

		setLocationRelativeTo(parent);
		setVisible(true);
	}

	private CheckBoxListModel createCheckBoxListModel() {
		final CheckBoxListModel model = new CheckBoxListModel();

		model.addCheckBoxElement(Constants.FINISH_CHECK_JSON, Boolean.valueOf(GUIProperties.getAppJson()));
		model.addCheckBoxElement(Constants.FINISH_CHECK_JAVA_SIMPLE, Boolean.valueOf(GUIProperties.getAppJavaS()));
		model.addCheckBoxElement(Constants.FINISH_CHECK_JAVA_ADVANCED, Boolean.valueOf(GUIProperties.getAppJavaA()));

		return model;
	}

	public String getOutput() {
		return btnDirOutput.getText();
	}

	public String getPackage() {
		return packageName.getText();
	}

	public Boolean[] getCheckBoxes() {
		CheckBoxListModel model = webCheckBoxList.getCheckBoxListModel();
		Boolean[] toRet = new Boolean[model.getSize()];

		for (int i = 0; i < model.getSize(); i++) {
			toRet[i] = model.get(i).isSelected();
		}

		return toRet;
	}

	public boolean isFinish() {
		return isFinish;
	}
}
