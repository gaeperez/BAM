package es.uvigo.ei.sing.singulator.gui.configuration;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.SwingConstants;

import com.alee.extended.button.WebSwitch;
import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.button.WebButton;
import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.slider.WebSlider;
import com.alee.laf.text.WebTextField;
import com.alee.utils.FileUtils;
import com.alee.utils.swing.DialogOptions;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.filters.CheckpointFilesFilter;
import es.uvigo.ei.sing.singulator.gui.custom.labels.WebOverlayLabel;
import es.uvigo.ei.sing.singulator.gui.custom.spinners.CustomAlignedSpinner;
import es.uvigo.ei.sing.singulator.gui.custom.spinners.CustomSpinnerModel;
import es.uvigo.ei.sing.singulator.gui.custom.validators.EmailValidator;
import es.uvigo.ei.sing.singulator.gui.interfaces.DataPanel;
import es.uvigo.ei.sing.singulator.model.JsonGeneralConfiguration;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public class GeneralConfPanel extends WebPanel implements DataPanel {

	private static final long serialVersionUID = 1L;

	private WebTextField tfFileOutput;
	private WebTextField tfSimName;
	private WebTextField tfEmails;

	private CustomAlignedSpinner spinnerJobs;
	private CustomAlignedSpinner spinnerSteps;
	private CustomAlignedSpinner spinnerSaveSimEvery;
	private CustomAlignedSpinner spinnerWriteResEvery;

	private WebSlider sliderProcCreation;
	private WebSlider sliderProcSim;

	private WebButton btnLoadCheckpoint;
	private WebButton btnDirOutput;

	private WebSwitch chckbxActivateGui;

	private WebOverlayLabel overlayProcCreation;
	private WebOverlayLabel overlayProcSim;
	private WebOverlayLabel overlayActivateGui;

	private WebLabel lblTotalTries;
	private WebLabel lblDirOutput;
	private WebLabel lblFileOutput;
	private WebLabel lblSimName;
	private WebLabel lblEmails;
	private WebLabel lblLoadCheckpoint;
	private WebLabel lblJobs;
	private WebLabel lblSteps;
	private WebLabel lblSaveSimEvery;
	private WebLabel lblWriteResEvery;

	private double maxLabelLenght = -1.0;

	protected JsonSingulator jsonSingulator;

	public GeneralConfPanel() {
		init();
	}

	public void init() {
		{
			// General panel configuration
			setLayout(new VerticalFlowLayout(0, 10));
			setMargin(7);
			setBackground(Color.decode(Constants.COLOR_WHITE));
		}
		{
			// Label configuration (1st column)
			overlayProcCreation = new WebOverlayLabel(Constants.GENERAL_PROC_CREATION,
					Constants.GENERAL_TIP_PROC_CREATION);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					overlayProcCreation.getPreferredSize().getWidth());

			overlayProcSim = new WebOverlayLabel(Constants.GENERAL_PROC_SIM, Constants.GENERAL_TIP_PROC_SIM);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayProcSim.getPreferredSize().getWidth());

			lblTotalTries = new WebLabel(Constants.GENERAL_TRIES);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblTotalTries.getPreferredSize().getWidth());

			lblDirOutput = new WebLabel(Constants.GENERAL_OUTPUT_FOLDER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblDirOutput.getPreferredSize().getWidth());

			lblFileOutput = new WebLabel(Constants.GENERAL_OUTPUT_PREFIX);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblFileOutput.getPreferredSize().getWidth());

			lblSimName = new WebLabel(Constants.GENERAL_SIM_NAME);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblSimName.getPreferredSize().getWidth());

			lblEmails = new WebLabel(Constants.GENERAL_EMAILS);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblEmails.getPreferredSize().getWidth());

			overlayActivateGui = new WebOverlayLabel(Constants.GENERAL_GUI, Constants.GENERAL_TIP_GUI);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					overlayActivateGui.getPreferredSize().getWidth());

			lblLoadCheckpoint = new WebLabel(Constants.GENERAL_PATH_CHECKPOINT);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					lblLoadCheckpoint.getPreferredSize().getWidth());

			lblJobs = new WebLabel(Constants.GENERAL_NUMBER_JOBS);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblJobs.getPreferredSize().getWidth());

			lblSteps = new WebLabel(Constants.GENERAL_NUMBER_STEPS);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblSteps.getPreferredSize().getWidth());

			lblSaveSimEvery = new WebLabel(Constants.GENERAL_SAVE_EVERY);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblSaveSimEvery.getPreferredSize().getWidth());

			lblWriteResEvery = new WebLabel(Constants.GENERAL_WRITE_EVERY);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					lblWriteResEvery.getPreferredSize().getWidth());
		}
		{
			// Fields configuration (2nd column)
			{
				tfSimName = new WebTextField(10);
				tfSimName.putClientProperty(GroupPanel.FILL_CELL, true);
				tfSimName.setInputPrompt(Constants.GENERAL_PROMPT_SIM_NAME);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblSimName.getPreferredSize().getWidth())),
						lblSimName, tfSimName));
			}
			{
				spinnerSteps = new CustomAlignedSpinner();
				spinnerSteps.putClientProperty(GroupPanel.FILL_CELL, true);
				spinnerSteps.setModel(new CustomSpinnerModel(1000, 1, 100));
				spinnerSteps.setTextAligment(SwingConstants.LEFT);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblSteps.getPreferredSize().getWidth())),
						lblSteps, spinnerSteps));
			}
			{
				spinnerJobs = new CustomAlignedSpinner();
				spinnerJobs.putClientProperty(GroupPanel.FILL_CELL, true);
				spinnerJobs.setModel(new CustomSpinnerModel(1, 1, 100, 1));
				spinnerJobs.setTextAligment(SwingConstants.LEFT);

				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblJobs.getPreferredSize().getWidth())),
						lblJobs, spinnerJobs));
			}
			{
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
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						lblDirOutput.getPreferredSize().getWidth())), lblDirOutput, btnDirOutput));
			}
			{
				tfFileOutput = new WebTextField(10);
				tfFileOutput.putClientProperty(GroupPanel.FILL_CELL, true);
				tfFileOutput.setInputPrompt(Constants.GENERAL_PROMPT_OUTPUT);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						lblFileOutput.getPreferredSize().getWidth())), lblFileOutput, tfFileOutput));
			}
			{
				tfEmails = new WebTextField(10);
				tfEmails.putClientProperty(GroupPanel.FILL_CELL, true);
				tfEmails.setInputPrompt(Constants.GENERAL_PROMPT_EMAILS);
				tfEmails.setInputVerifier(new EmailValidator(tfEmails));
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblEmails.getPreferredSize().getWidth())),
						lblEmails, tfEmails));
			}
			{
				add(new WebSeparator(false, true));

				WebCollapsiblePane topPane = new WebCollapsiblePane(null, Constants.GENERAL_ADVANCED, setupAdvanced());
				topPane.setExpanded(false);
				topPane.setPaintFocus(false);

				add(topPane);
			}
		}
	}

	private WebPanel setupAdvanced() {
		WebPanel toRet = new WebPanel();
		toRet.setBackground(Color.decode(Constants.COLOR_WHITE));
		{
			toRet.setLayout(new VerticalFlowLayout(0, 10));
			toRet.setMargin(7);
		}
		{
			spinnerSaveSimEvery = new CustomAlignedSpinner();
			spinnerSaveSimEvery.putClientProperty(GroupPanel.FILL_CELL, true);
			spinnerSaveSimEvery.setModel(new CustomSpinnerModel(100, 1, 25));
			spinnerSaveSimEvery.setTextAligment(SwingConstants.LEFT);
			toRet.add(
					new GroupPanel(
							(50 + Functions.calculateLabelDifference(maxLabelLenght,
									lblSaveSimEvery.getPreferredSize().getWidth())),
							lblSaveSimEvery, spinnerSaveSimEvery));
		}
		{
			spinnerWriteResEvery = new CustomAlignedSpinner();
			spinnerWriteResEvery.putClientProperty(GroupPanel.FILL_CELL, true);
			spinnerWriteResEvery.setModel(new CustomSpinnerModel(100, 1, 25));
			spinnerWriteResEvery.setTextAligment(SwingConstants.LEFT);
			toRet.add(
					new GroupPanel(
							(50 + Functions.calculateLabelDifference(maxLabelLenght,
									lblWriteResEvery.getPreferredSize().getWidth())),
							lblWriteResEvery, spinnerWriteResEvery));
		}
		{
			btnLoadCheckpoint = new WebButton(Constants.GENERAL_PROMPT_CHECKPOINT);
			btnLoadCheckpoint.addActionListener(new ActionListener() {
				private WebFileChooser checkpointChooser = null;
				private File file = null;

				@Override
				public void actionPerformed(ActionEvent e) {
					if (checkpointChooser == null) {
						checkpointChooser = new WebFileChooser();
						checkpointChooser.setMultiSelectionEnabled(false);
						checkpointChooser.setAcceptAllFileFilterUsed(false);
						checkpointChooser.addChoosableFileFilter(new CheckpointFilesFilter());
						checkpointChooser.resetChoosableFileFilters();
					}
					if (file != null) {
						checkpointChooser.setSelectedFile(file);
					}
					if (checkpointChooser.showOpenDialog(btnLoadCheckpoint) == JFileChooser.APPROVE_OPTION) {
						file = checkpointChooser.getSelectedFile();
						btnLoadCheckpoint.setIcon(FileUtils.getFileIcon(file));
						btnLoadCheckpoint.setText(file.getAbsolutePath());
					}
				}
			});

			btnLoadCheckpoint.putClientProperty(GroupPanel.FILL_CELL, true);
			btnLoadCheckpoint.setPreferredWidth(200);
			toRet.add(
					new GroupPanel(
							(50 + Functions.calculateLabelDifference(maxLabelLenght,
									lblLoadCheckpoint.getPreferredSize().getWidth())),
							lblLoadCheckpoint, btnLoadCheckpoint));
		}
		{
			chckbxActivateGui = new WebSwitch(false);
			chckbxActivateGui.getLeftComponent().setForeground(Color.decode(Constants.COLOR_GREEN_LIGHT));
			chckbxActivateGui.getRightComponent().setForeground(Color.decode(Constants.COLOR_RED_LIGHT));

			toRet.add(
					new GroupPanel(
							(50 + Functions.calculateLabelDifference(maxLabelLenght,
									overlayActivateGui.getPreferredSize().getWidth())),
							overlayActivateGui, chckbxActivateGui));
		}
		{
			sliderProcCreation = new WebSlider(SwingConstants.HORIZONTAL);
			sliderProcCreation.setMinimum(0);
			sliderProcCreation.setMaximum(4);
			sliderProcCreation.setMinorTickSpacing(1);
			sliderProcCreation.setMajorTickSpacing(2);
			sliderProcCreation.setPaintTicks(true);
			sliderProcCreation.setPaintLabels(true);
			sliderProcCreation.putClientProperty(GroupPanel.FILL_CELL, true);

			toRet.add(new GroupPanel(
					(50 + Functions.calculateLabelDifference(maxLabelLenght,
							overlayProcCreation.getPreferredSize().getWidth())),
					overlayProcCreation, sliderProcCreation));
		}
		{
			sliderProcSim = new WebSlider(SwingConstants.HORIZONTAL);
			sliderProcSim.setMinimum(0);
			sliderProcSim.setMaximum(8);
			sliderProcSim.setMinorTickSpacing(1);
			sliderProcSim.setMajorTickSpacing(2);
			sliderProcSim.setPaintTicks(true);
			sliderProcSim.setPaintLabels(true);
			sliderProcSim.putClientProperty(GroupPanel.FILL_CELL, true);

			toRet.add(new GroupPanel((50
					+ Functions.calculateLabelDifference(maxLabelLenght, overlayProcSim.getPreferredSize().getWidth())),
					overlayProcSim, sliderProcSim));
		}

		return toRet;
	}

	public int getProcCreation() {
		return sliderProcCreation.getValue();
	}

	public void setProcCreation(int value) {
		sliderProcCreation.setValue(value);
	}

	public void setDefaultProcCreation() {
		sliderProcCreation.setValue(1);
	}

	public int getProcSim() {
		return sliderProcSim.getValue();
	}

	public void setProcSim(int value) {
		sliderProcSim.setValue(value);
	}

	public void setDefaultProcSim() {
		sliderProcSim.setValue(0);
	}

	public int getTotalTries() {
		// return (int) spinnerTotalTries.getValue();
		return 10000;
	}

	public void setTotalTries(int value) {
		// spinnerTotalTries.setValue(value);
	}

	public void setDefaultTotalTries() {
		// spinnerTotalTries.setValue(10000);
	}

	public boolean isActivateGUI() {
		return chckbxActivateGui.isSelected();
	}

	public void setActivateGUI(boolean selected) {
		chckbxActivateGui.setSelected(selected);
	}

	public void setDefaultActivateGUI() {
		chckbxActivateGui.setSelected(false);
	}

	public String getReadFromCheckpoint() {
		if (!btnLoadCheckpoint.getText().isEmpty()
				&& !btnLoadCheckpoint.getText().equals(Constants.GENERAL_PROMPT_CHECKPOINT))
			return btnLoadCheckpoint.getText();
		else
			return "";
	}

	public void setReadFromCheckpoint(String path) {
		if (path.isEmpty())
			btnLoadCheckpoint.setText(Constants.GENERAL_PROMPT_CHECKPOINT);
		else
			btnLoadCheckpoint.setText(path);
	}

	public void setDefaultReadFromCheckpoint() {
		btnLoadCheckpoint.setText(Constants.GENERAL_PROMPT_CHECKPOINT);
	}

	public int getJobs() {
		return (int) spinnerJobs.getValue();
	}

	public void setJobs(int value) {
		spinnerJobs.setValue(value);
	}

	public void setDefaultJobs() {
		spinnerJobs.setValue(1);
	}

	public int getSteps() {
		return (int) spinnerSteps.getValue();
	}

	public void setSteps(int value) {
		spinnerSteps.setValue(value);
	}

	public void setDefaultSteps() {
		spinnerSteps.setValue(1000);
	}

	public int getSaveSimEvery() {
		return (int) spinnerSaveSimEvery.getValue();
	}

	public void setSaveSimEvery(int value) {
		spinnerSaveSimEvery.setValue(value);
	}

	public void setDefaultSaveSimEvery() {
		spinnerSaveSimEvery.setValue(100);
	}

	public int getWriteResEvery() {
		return (int) spinnerWriteResEvery.getValue();
	}

	public void setWriteResEvery(int value) {
		spinnerWriteResEvery.setValue(value);
	}

	public void setDefaultWriteResEvery() {
		spinnerWriteResEvery.setValue(100);
	}

	public String getDirOutput() {
		if (!btnDirOutput.getText().isEmpty() && !btnDirOutput.getText().equals(Constants.GENERAL_PROMPT_DIR))
			return btnDirOutput.getText();
		else
			return "";
	}

	public void setDirOutput(String path) {
		if (path.isEmpty())
			btnDirOutput.setText(Constants.GENERAL_PROMPT_DIR);
		else
			btnDirOutput.setText(path);
	}

	public void setDefaultDirOutput() {
		btnDirOutput.setText(Constants.GENERAL_PROMPT_DIR);
	}

	public String getFileOutput() {
		return tfFileOutput.getText();
	}

	public void setFileOutput(String name) {
		tfFileOutput.setText(name);
	}

	public void setDefaultFileOutput() {
		tfFileOutput.clear();
	}

	public String getSimName() {
		return tfSimName.getText();
	}

	public void setSimName(String name) {
		tfSimName.setText(name);
	}

	public void setDefaultSimName() {
		tfSimName.clear();
	}

	public String[] getEmailTo() {
		return tfEmails.getText().split(",");
	}

	public void setEmailTo(String[] emails) {
		String text = "";

		for (String email : emails) {
			if (text.isEmpty()) {
				text = email;
			} else {
				text += "," + email;
			}
		}

		tfEmails.setText(text);
	}

	public void setDefaultEmailTo() {
		tfEmails.clear();
	}

	@Override
	public Object[] getAllData() {
		Object[] toRet = new Object[1];

		JsonGeneralConfiguration gcp = new JsonGeneralConfiguration();

		gcp.setProcCreation(getProcCreation());
		gcp.setProcSim(getProcSim());
		gcp.setTotalTries(getTotalTries());
		gcp.setActivateGUI(isActivateGUI());
		gcp.setReadFromCheckpoint(getReadFromCheckpoint());
		gcp.setNumberOfJobs(getJobs());
		gcp.setNumberOfSteps(getSteps());
		gcp.setSaveSimEvery(getSaveSimEvery());
		gcp.setWriteResultsEvery(getWriteResEvery());
		gcp.setDirOutput(getDirOutput());
		gcp.setFileOutput(getFileOutput());
		gcp.setSimName(getSimName());
		gcp.setEmailTo(getEmailTo());

		toRet[0] = gcp;

		return toRet;
	}

	@Override
	public boolean verifyAllData() {
		return true;
	}

	@Override
	public void loadData() {
		JsonGeneralConfiguration gc = jsonSingulator.getGeneralConfiguration();

		setProcCreation(gc.getProcCreation());
		setProcSim(gc.getProcSim());
		setTotalTries(gc.getTotalTries());
		setActivateGUI(gc.isActivateGUI());
		setReadFromCheckpoint(gc.getReadFromCheckpoint());
		setJobs(gc.getNumberOfJobs());
		setSteps(gc.getNumberOfSteps());
		setSaveSimEvery(gc.getSaveSimEvery());
		setWriteResEvery(gc.getWriteResultsEvery());
		setDirOutput(gc.getDirOutput());
		setFileOutput(gc.getFileOutput());
		setSimName(gc.getSimName());
		setEmailTo(gc.getEmailTo());

		revalidate();
	}

	@Override
	public void updateComponentsData(JsonSingulator jsonSingulator) {
	}

	@Override
	public void setJsonSingulator(JsonSingulator jsonSingulator) {
		this.jsonSingulator = jsonSingulator;
	}

	@Override
	public void deleteAllData() {
		setDefaultProcCreation();
		setDefaultProcSim();
		setDefaultTotalTries();
		setDefaultActivateGUI();
		setDefaultReadFromCheckpoint();
		setDefaultJobs();
		setDefaultSteps();
		setDefaultSaveSimEvery();
		setDefaultWriteResEvery();
		setDefaultDirOutput();
		setDefaultFileOutput();
		setDefaultSimName();
		setDefaultEmailTo();

		revalidate();
	}

	@Override
	public String getPanelName() {
		return Constants.GENERAL_TITLE;
	}
}
