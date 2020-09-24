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
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.text.WebTextField;
import com.alee.utils.FileUtils;
import com.alee.utils.swing.DialogOptions;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.GUIProperties;

public class PreferencesFrame extends WebDialog {
	private static final long serialVersionUID = 1L;

	private WebCheckBox initDialog;
	private WebCheckBox delConfirmation;
	private WebCheckBox json;
	private WebCheckBox javaS;
	private WebCheckBox javaA;

	private WebTextField packageName;

	private WebButton btnDirOutput;

	public PreferencesFrame() {
		init();
	}

	public void init() {
		{
			setTitle(Constants.PREFERENCES_TITLE);
			setShadeWidth(0);
			setResizable(false);
			setModal(true);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(new BorderLayout());
		}
		{
			WebPanel panelContent = new WebPanel();
			panelContent.setMargin(15);
			panelContent.setLayout(new VerticalFlowLayout(0, 10));
			{
				{
					initDialog = new WebCheckBox(Constants.PREFERENCES_INITIAL_DIALOG);
					initDialog.setSelected(Boolean.valueOf(GUIProperties.getAppShowDialog()));
					panelContent.add(initDialog);

					delConfirmation = new WebCheckBox(Constants.PREFERENCES_DEL_CONFIRMATION);
					delConfirmation.setSelected(Boolean.valueOf(GUIProperties.getAppShowDel()));
					panelContent.add(delConfirmation);
				}
				panelContent.add(new WebSeparator(false, true));
				{
					panelContent.add(new WebLabel(Constants.PREFERENCES_EXPORT_LABEL));

					WebCheckBoxList webCheckBoxList = new WebCheckBoxList(createCheckBoxListModel());
					webCheckBoxList.setVisibleRowCount(3);
					webCheckBoxList.setSelectedIndex(0);
					webCheckBoxList.setEditable(false);
					webCheckBoxList.setMultiplySelectionAllowed(false);
					webCheckBoxList.putClientProperty(GroupPanel.FILL_CELL, true);

					panelContent.add(webCheckBoxList);
				}
				panelContent.add(new WebSeparator(false, true));
				{
					panelContent.add(new WebLabel(Constants.PREFERENCES_DEFAULT_OUTPUT));

					// Simple directory chooser
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
					panelContent.add(btnDirOutput);

					panelContent.add(new WebLabel(Constants.PREFERENCES_DEFAULT_PACKAGE));
					packageName = new WebTextField(30);
					packageName.setText(GUIProperties.getAppPackage());
					panelContent.add(packageName);
				}
			}
			getContentPane().add(panelContent, BorderLayout.CENTER);
		}
		{
			WebPanel buttonPanel = new WebPanel();
			buttonPanel.setLayout(new GridLayout());
			buttonPanel.setMargin(15, 0, 0, 0);
			{
				WebButton cancel = new WebButton(Constants.PREFERENCES_CANCEL);
				cancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				buttonPanel.add(cancel);

				WebButton finish = new WebButton(Constants.PREFERENCES_SAVE);
				finish.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						GUIProperties.setAppPackage(packageName.getText());
						GUIProperties.setAppOutput(btnDirOutput.getText());
						GUIProperties.setAppShowDialog(String.valueOf(initDialog.isSelected()));
						GUIProperties.setAppShowDel(String.valueOf(delConfirmation.isSelected()));
						GUIProperties.setAppShowJson(String.valueOf(json.isSelected()));
						GUIProperties.setAppShowJavaS(String.valueOf(javaS.isSelected()));
						GUIProperties.setAppShowJavaA(String.valueOf(javaA.isSelected()));

						GUIProperties.store();

						setVisible(false);
						dispose();
					}
				});
				buttonPanel.add(finish);
			}
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		}
		pack();
	}

	private CheckBoxListModel createCheckBoxListModel() {
		final CheckBoxListModel model = new CheckBoxListModel();

		json = new WebCheckBox(Constants.PREFERENCES_JSON);
		json.setSelected(Boolean.valueOf(GUIProperties.getAppJson()));
		model.addCheckBoxElement(Constants.FINISH_CHECK_JSON, Boolean.valueOf(GUIProperties.getAppJson()));

		javaS = new WebCheckBox(Constants.PREFERENCES_JAVA_SIMPLE);
		javaS.setSelected(Boolean.valueOf(GUIProperties.getAppJavaS()));
		model.addCheckBoxElement(Constants.FINISH_CHECK_JAVA_SIMPLE, Boolean.valueOf(GUIProperties.getAppJavaS()));

		javaA = new WebCheckBox(Constants.PREFERENCES_JAVA_ADVANCED);
		javaA.setSelected(Boolean.valueOf(GUIProperties.getAppJavaA()));
		model.addCheckBoxElement(Constants.FINISH_CHECK_JAVA_ADVANCED, Boolean.valueOf(GUIProperties.getAppJavaA()));

		return model;
	}
}
