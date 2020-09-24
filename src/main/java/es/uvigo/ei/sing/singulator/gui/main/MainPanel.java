package es.uvigo.ei.sing.singulator.gui.main;

import static es.uvigo.ei.sing.singulator.generator.java.JavaAdvancedModelGenerator.javaAdvancedModelGenerator;
import static es.uvigo.ei.sing.singulator.generator.java.JavaSimpleModelGenerator.javaSimpleModelGenerator;
import static es.uvigo.ei.sing.singulator.generator.json.JsonModelGenerator.jsonModelGenerator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.apache.commons.io.FilenameUtils;

import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.google.gson.JsonSyntaxException;

import es.uvigo.ei.sing.singulator.generator.ModelGenerator;
import es.uvigo.ei.sing.singulator.gui.cell.CellsPanel;
import es.uvigo.ei.sing.singulator.gui.configuration.GeneralConfPanel;
import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.dialogs.AboutDialog;
import es.uvigo.ei.sing.singulator.gui.custom.dialogs.JsonVisualizer;
import es.uvigo.ei.sing.singulator.gui.custom.dialogs.PreferencesFrame;
import es.uvigo.ei.sing.singulator.gui.custom.filters.JsonFilesFilter;
import es.uvigo.ei.sing.singulator.gui.custom.labels.CustomImageLabel;
import es.uvigo.ei.sing.singulator.gui.custom.tabbedpanes.VetoableSingleSelectionModel;
import es.uvigo.ei.sing.singulator.gui.environment.EnvironmentPanel;
import es.uvigo.ei.sing.singulator.gui.event.KillPanel;
import es.uvigo.ei.sing.singulator.gui.event.ReactionPanel;
import es.uvigo.ei.sing.singulator.gui.event.TransformPanel;
import es.uvigo.ei.sing.singulator.gui.feeder.FeedersPanel;
import es.uvigo.ei.sing.singulator.gui.interfaces.DataPanel;
import es.uvigo.ei.sing.singulator.gui.molecule.BuildingBlocksPanel;
import es.uvigo.ei.sing.singulator.gui.molecule.LZWPanel;
import es.uvigo.ei.sing.singulator.gui.molecule.MacromoleculesPanel;
import es.uvigo.ei.sing.singulator.gui.ribosome.RibosomePanel;
import es.uvigo.ei.sing.singulator.gui.transporter.TransportersPanel;
import es.uvigo.ei.sing.singulator.gui.unit.UnitPanel;
import es.uvigo.ei.sing.singulator.model.JsonAgent;
import es.uvigo.ei.sing.singulator.model.JsonBuildingblock;
import es.uvigo.ei.sing.singulator.model.JsonCell;
import es.uvigo.ei.sing.singulator.model.JsonEnvironment;
import es.uvigo.ei.sing.singulator.model.JsonEvent;
import es.uvigo.ei.sing.singulator.model.JsonFeeder;
import es.uvigo.ei.sing.singulator.model.JsonGeneralConfiguration;
import es.uvigo.ei.sing.singulator.model.JsonKill;
import es.uvigo.ei.sing.singulator.model.JsonLzw;
import es.uvigo.ei.sing.singulator.model.JsonMacromolecule;
import es.uvigo.ei.sing.singulator.model.JsonMolecule;
import es.uvigo.ei.sing.singulator.model.JsonReaction;
import es.uvigo.ei.sing.singulator.model.JsonRibosome;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;
import es.uvigo.ei.sing.singulator.model.JsonTransform;
import es.uvigo.ei.sing.singulator.model.JsonTransporter;
import es.uvigo.ei.sing.singulator.model.JsonUnity;

public class MainPanel extends WebPanel {

	private static final long serialVersionUID = 1L;

	// Panels
	private MainPanelNorth panelNorth;
	private MainButtonsPanel panelMainButtons;
	public WebTabbedPane tabbedPaneContent;

	// External frames
	private JsonVisualizer jsonVisualizer;
	private PreferencesFrame preferencesDialog;
	private AboutDialog aboutDialog;

	// Class variables
	private int pointer;
	private int maxCardNumber;

	private Map<Integer, DataPanel> mapIndexPanel;
	private Map<Integer, CustomImageLabel> mapIndexLabel;

	// Model variables
	private JsonSingulator singulator;
	private JsonAgent agents;
	private JsonMolecule molecules;
	private JsonEvent events;

	final private static ModelGenerator[] generators = new ModelGenerator[] { jsonModelGenerator(),
			javaAdvancedModelGenerator(), javaSimpleModelGenerator() };

	public MainPanel() {
		this.pointer = 0;
		this.mapIndexPanel = new HashMap<Integer, DataPanel>();
		this.mapIndexLabel = new HashMap<Integer, CustomImageLabel>();
		this.singulator = new JsonSingulator();
		this.agents = new JsonAgent();
		this.molecules = new JsonMolecule();
		this.events = new JsonEvent();

		this.jsonVisualizer = new JsonVisualizer();
		this.preferencesDialog = new PreferencesFrame();
		this.aboutDialog = new AboutDialog();

		init();

		this.maxCardNumber = mapIndexPanel.size() - 1;
	}

	private void init() {
		{
			setLayout(new BorderLayout(0, 0));
			setBackground(Color.white);
		}
		{
			panelNorth = new MainPanelNorth(this);
			add(panelNorth, BorderLayout.NORTH);
		}
		{
			panelMainButtons = new MainButtonsPanel(this);
			add(panelMainButtons, BorderLayout.SOUTH);
		}
		{
			tabbedPaneContent = new WebTabbedPane();
			// Listener to change the current tab
			VetoableSingleSelectionModel model = new VetoableSingleSelectionModel();
			VetoableChangeListener validator = new VetoableChangeListener() {
				@Override
				public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
					int oldIndex = (int) evt.getOldValue();
					int newIndex = (int) evt.getNewValue();

					if (oldIndex == -1) {
						return;
					} else if (isValidTab(oldIndex, newIndex)) {
						CustomImageLabel label;

						label = mapIndexLabel.get(oldIndex);
						label.changeImage(false);

						label = mapIndexLabel.get(newIndex);
						label.setEnabled(true);
						label.changeImage(true);

						panelMainButtons.setLabelText(mapIndexPanel.get(pointer).getPanelName());

						return;
					}

					throw new PropertyVetoException("change not valid", evt);
				}

				private boolean isValidTab(int oldIndex, int newIndex) {
					boolean toRet = true;

					// Go next tab
					if (oldIndex <= newIndex) {
						// Get current panel
						DataPanel dp = mapIndexPanel.get(pointer);
						// Verify current panel data
						if (dp.verifyAllData()) {
							Object[] data = dp.getAllData();

							// Save panel data in the model
							setValueInSingulator(data, pointer);

							if (pointer <= maxCardNumber) {
								pointer = newIndex;

								// Update target panel information
								mapIndexPanel.get(pointer).updateComponentsData(singulator);
							}

							if (pointer == maxCardNumber)
								panelMainButtons.changeToFinish(true);
						} else {
							toRet = false;
						}
					}
					// Go previous tab
					else if (oldIndex > newIndex && newIndex >= 0) {
						// It is not necessary to do an update here because
						// data are not dependant on the prevs tabs
						pointer = newIndex;

						panelMainButtons.changeToFinish(false);
					} else {
						toRet = false;
					}

					return toRet;
				}
			};
			model.addVetoableChangeListener(validator);
			tabbedPaneContent.setModel(model);

			tabbedPaneContent.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			tabbedPaneContent.setTabPlacement(SwingConstants.LEFT);
			tabbedPaneContent.setTabInsets(new Insets(0, 0, 0, 0));

			setupTabbedPane();

			add(tabbedPaneContent, BorderLayout.CENTER);
		}
	}

	private void setupTabbedPane() {
		Component component;
		CustomImageLabel lbl;
		{
			// Create the custom component and add to tabbedPane
			component = new GeneralConfPanel();
			tabbedPaneContent.add(component, Constants.GENERAL_TITLE);

			// Set custom image label
			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_GENERAL_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_GENERAL_UNSELECTED));
			lbl.changeImage(true);
			tabbedPaneContent.setTabComponentAt(Constants.ID_GENERAL, lbl);

			// Add index info to maps
			mapIndexLabel.put(Constants.ID_GENERAL, lbl);
			mapIndexPanel.put(Constants.ID_GENERAL, (DataPanel) component);
		}
		{
			component = new EnvironmentPanel();
			tabbedPaneContent.add(component, Constants.ENVIRONMENT_TITLE);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_ENVIRONMENT_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_ENVIRONMENT_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_ENVIRONMENT, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_ENVIRONMENT, lbl);

			mapIndexLabel.put(Constants.ID_ENVIRONMENT, lbl);
			mapIndexPanel.put(Constants.ID_ENVIRONMENT, (DataPanel) component);
		}
		{
			component = new UnitPanel();
			tabbedPaneContent.add(component, Constants.UNITY_TITLE);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_UNIT_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_UNIT_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_UNITY, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_UNITY, lbl);

			mapIndexLabel.put(Constants.ID_UNITY, lbl);
			mapIndexPanel.put(Constants.ID_UNITY, (DataPanel) component);
		}
		{
			component = new CellsPanel();
			tabbedPaneContent.add(component, Constants.JSON_CELL);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_CELL_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_CELL_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_CELL, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_CELL, lbl);

			mapIndexLabel.put(Constants.ID_CELL, lbl);
			mapIndexPanel.put(Constants.ID_CELL, (DataPanel) component);
		}
		{
			component = new RibosomePanel(singulator);
			tabbedPaneContent.add(component, Constants.RIBOSOMES_TITLE);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_RIBOSOME_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_RIBOSOME_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_RIBOSOME, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_RIBOSOME, lbl);

			mapIndexLabel.put(Constants.ID_RIBOSOME, lbl);
			mapIndexPanel.put(Constants.ID_RIBOSOME, (DataPanel) component);
		}
		{
			component = new MacromoleculesPanel(singulator);
			tabbedPaneContent.add(component, Constants.MACROMOLECULES_TITLE);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_MACROMOLECULE_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_MACROMOLECULE_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_MACROMOLECULE, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_MACROMOLECULE, lbl);

			mapIndexLabel.put(Constants.ID_MACROMOLECULE, lbl);
			mapIndexPanel.put(Constants.ID_MACROMOLECULE, (DataPanel) component);
		}
		{
			component = new BuildingBlocksPanel(singulator);
			tabbedPaneContent.add(component, Constants.BUILDINGBLOCKS_TITLE);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_BUILDING_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_BUILDING_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_BUILDINGBLOCK, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_BUILDINGBLOCK, lbl);

			mapIndexLabel.put(Constants.ID_BUILDINGBLOCK, lbl);
			mapIndexPanel.put(Constants.ID_BUILDINGBLOCK, (DataPanel) component);
		}
		{
			component = new LZWPanel(singulator);
			tabbedPaneContent.add(component, Constants.LZW_TITLE);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_LZW_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_LZW_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_LZW, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_LZW, lbl);

			mapIndexLabel.put(Constants.ID_LZW, lbl);
			mapIndexPanel.put(Constants.ID_LZW, (DataPanel) component);
		}
		{
			component = new TransportersPanel(singulator);
			tabbedPaneContent.add(component, Constants.JSON_TRANSPORTER);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_TRANSPORTER_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_TRANSPORTER_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_TRANSPORTER, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_TRANSPORTER, lbl);

			mapIndexLabel.put(Constants.ID_TRANSPORTER, lbl);
			mapIndexPanel.put(Constants.ID_TRANSPORTER, (DataPanel) component);
		}
		{
			component = new FeedersPanel(singulator);
			tabbedPaneContent.add(component, Constants.FEEDER_TITLE);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_FEEDER_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_FEEDER_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_FEEDER, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_FEEDER, lbl);

			mapIndexLabel.put(Constants.ID_FEEDER, lbl);
			mapIndexPanel.put(Constants.ID_FEEDER, (DataPanel) component);
		}
		{
			component = new KillPanel(singulator);
			tabbedPaneContent.add(component, Constants.KILL_TITLE);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_KILL_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_KILL_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_KILL, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_KILL, lbl);

			mapIndexLabel.put(Constants.ID_KILL, lbl);
			mapIndexPanel.put(Constants.ID_KILL, (DataPanel) component);
		}
		{
			component = new ReactionPanel(singulator);
			tabbedPaneContent.add(component, Constants.REACTION_TITLE);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_REACTION_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_REACTION_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_REACTION, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_REACTION, lbl);

			mapIndexLabel.put(Constants.ID_REACTION, lbl);
			mapIndexPanel.put(Constants.ID_REACTION, (DataPanel) component);
		}
		{
			component = new TransformPanel(singulator);
			tabbedPaneContent.add(component, Constants.TRANSFORM_TITLE);

			lbl = new CustomImageLabel(Functions.loadIcon(Constants.ICON_TAB_TRANSFORM_SELECTED),
					Functions.loadIcon(Constants.ICON_TAB_TRANSFORM_UNSELECTED));
			lbl.changeImage(false);
			lbl.setEnabled(false);

			tabbedPaneContent.setEnabledAt(Constants.ID_TRANSFORM, false);
			tabbedPaneContent.setTabComponentAt(Constants.ID_TRANSFORM, lbl);

			mapIndexLabel.put(Constants.ID_TRANSFORM, lbl);
			mapIndexPanel.put(Constants.ID_TRANSFORM, (DataPanel) component);
		}
		// Set pointer in the first tab
		pointer = 0;
	}

	protected void showNextCard() {
		// Try to change to the next tab
		if (pointer < maxCardNumber)
			tabbedPaneContent.setSelectedIndex(pointer + 1);
		else
			tabbedPaneContent.setSelectedIndex(pointer);

		// Pointer has been updated inside the VetoableChangeListener
		tabbedPaneContent.setEnabledAt(pointer, true);
	}

	protected void showPreviousCard() {
		tabbedPaneContent.setSelectedIndex(pointer - 1);
	}

	protected boolean validateCurrentCard() {
		boolean toRet = false;

		// Get current data panel
		DataPanel dp = mapIndexPanel.get(pointer);

		if (dp.verifyAllData()) {
			Object[] data = dp.getAllData();
			if (data != null) {
				// Save data in the model
				setValueInSingulator(data, pointer);
			}
			toRet = true;
		}

		return toRet;
	}

	protected String validateAllCards() {
		String toRet = Constants.TOOLBAR_VALIDATE_ALL;

		// Validate current panel and propragate its data to the others
		// (dependant fields like cell in molecules)
		propagateData();

		// Validate all the panels
		Object[] data;
		DataPanel dp;
		for (int key : mapIndexPanel.keySet()) {
			dp = mapIndexPanel.get(key);

			if (dp.verifyAllData()) {
				data = dp.getAllData();
				// Save data in the model
				setValueInSingulator(data, key);
			} else {
				toRet += "- " + dp.getPanelName() + ".\n";
			}
		}

		return toRet;
	}

	private void setValueInSingulator(Object[] value, int pointer) {
		switch (pointer) {
		case Constants.ID_GENERAL:
			if (value != null)
				singulator.setGeneralConfiguration((JsonGeneralConfiguration) value[0]);
			break;
		case Constants.ID_ENVIRONMENT:
			if (value != null)
				singulator.setEnvironment((JsonEnvironment) value[0]);
			break;
		case Constants.ID_UNITY:
			if (value != null)
				singulator.setUnity((JsonUnity) value[0]);
			break;
		case Constants.ID_CELL:
			if (value != null) {
				JsonCell[] cells = new JsonCell[value.length];
				for (int i = 0; i < value.length; i++) {
					cells[i] = (JsonCell) value[i];
				}
				singulator.setCells(cells);
			} else {
				singulator.setCells(new JsonCell[0]);
			}
			break;
		case Constants.ID_RIBOSOME:
			if (value != null) {
				JsonRibosome[] ribosomes = new JsonRibosome[value.length];
				for (int i = 0; i < value.length; i++) {
					ribosomes[i] = (JsonRibosome) value[i];
				}
				agents.setRibosomes(ribosomes);
			} else {
				agents.setRibosomes(new JsonRibosome[0]);
			}
			singulator.setAgents(agents);

			break;
		case Constants.ID_MACROMOLECULE:
			if (value != null) {
				JsonMacromolecule[] macromolecules = new JsonMacromolecule[value.length];
				for (int i = 0; i < value.length; i++) {
					macromolecules[i] = (JsonMacromolecule) value[i];
				}
				molecules.setMacromolecules(macromolecules);
			} else {
				molecules.setMacromolecules(new JsonMacromolecule[0]);
			}
			agents.setMolecules(molecules);
			singulator.setAgents(agents);
			break;
		case Constants.ID_BUILDINGBLOCK:
			if (value != null) {
				JsonBuildingblock[] buildings = new JsonBuildingblock[value.length];
				for (int i = 0; i < value.length; i++) {
					buildings[i] = (JsonBuildingblock) value[i];
				}
				molecules.setBuildingBlocks(buildings);
			} else {
				molecules.setBuildingBlocks(new JsonBuildingblock[0]);
			}
			agents.setMolecules(molecules);
			singulator.setAgents(agents);
			break;
		case Constants.ID_LZW:
			if (value != null) {
				JsonLzw[] lzws = new JsonLzw[value.length];
				for (int i = 0; i < value.length; i++) {
					lzws[i] = (JsonLzw) value[i];
				}
				molecules.setLzw(lzws);
			} else {
				molecules.setLzw(new JsonLzw[0]);
			}
			agents.setMolecules(molecules);
			singulator.setAgents(agents);
			break;
		case Constants.ID_TRANSPORTER:
			if (value != null) {
				JsonTransporter[] transporters = new JsonTransporter[value.length];
				for (int i = 0; i < value.length; i++) {
					transporters[i] = (JsonTransporter) value[i];
				}
				singulator.setTransporters(transporters);
			} else {
				singulator.setTransporters(new JsonTransporter[0]);
			}
			break;
		case Constants.ID_FEEDER:
			if (value != null) {
				JsonFeeder[] feeders = new JsonFeeder[value.length];
				for (int i = 0; i < value.length; i++) {
					feeders[i] = (JsonFeeder) value[i];
				}
				singulator.setFeeder(feeders);
			} else {
				singulator.setFeeder(new JsonFeeder[0]);
			}
			break;
		case Constants.ID_KILL:
			if (value != null) {
				JsonKill[] kill = new JsonKill[value.length];
				for (int i = 0; i < value.length; i++) {
					kill[i] = (JsonKill) value[i];
				}
				events.setKill(kill);
			} else {
				events.setKill(new JsonKill[0]);
			}
			singulator.setEvents(events);
			break;
		case Constants.ID_REACTION:
			if (value != null) {
				JsonReaction[] reaction = new JsonReaction[value.length];
				for (int i = 0; i < value.length; i++) {
					reaction[i] = (JsonReaction) value[i];
				}
				events.setReaction(reaction);
				singulator.setEvents(events);
			} else {
				events.setReaction(new JsonReaction[0]);
			}
			singulator.setEvents(events);
			break;
		case Constants.ID_TRANSFORM:
			if (value != null) {
				JsonTransform[] transform = new JsonTransform[value.length];
				for (int i = 0; i < value.length; i++) {
					transform[i] = (JsonTransform) value[i];
				}
				events.setTransform(transform);
				singulator.setEvents(events);
			} else {
				events.setTransform(new JsonTransform[0]);
			}
			singulator.setEvents(events);
			break;
		}
	}

	protected int setSingulatorInValueOnLoad() {
		// 0: Only GenConf has data; 1: Gen+Env; 2: Gen+Env+Uni
		int toRet = 0;
		DataPanel dp;

		// Get over all the panels
		for (Integer index : mapIndexPanel.keySet()) {
			dp = mapIndexPanel.get(index);

			dp.setJsonSingulator(singulator);

			switch (index) {
			case Constants.ID_GENERAL:
				dp.deleteAllData();
				if (singulator.getGeneralConfiguration() != null)
					dp.loadData();
				break;
			case Constants.ID_ENVIRONMENT:
				dp.deleteAllData();
				if (singulator.getEnvironment() != null) {
					dp.loadData();
					toRet++;
				}
				break;
			case Constants.ID_UNITY:
				dp.deleteAllData();
				if (singulator.getUnity() != null) {
					dp.loadData();
					toRet++;
				}
				break;
			case Constants.ID_CELL:
				dp.deleteAllData();
				if (singulator.getCells() != null)
					dp.loadData();
				break;
			case Constants.ID_RIBOSOME:
				dp.deleteAllData();
				if (singulator.getAgents().getMolecules() != null && singulator.getAgents().getRibosomes() != null)
					dp.loadData();
				break;
			case Constants.ID_MACROMOLECULE:
				dp.deleteAllData();
				if (singulator.getAgents().getMolecules() != null
						&& singulator.getAgents().getMolecules().getMacromolecules() != null)
					dp.loadData();
				break;
			case Constants.ID_BUILDINGBLOCK:
				dp.deleteAllData();
				if (singulator.getAgents().getMolecules() != null
						&& singulator.getAgents().getMolecules().getBuildingBlocks() != null)
					dp.loadData();
				break;
			case Constants.ID_LZW:
				dp.deleteAllData();
				if (singulator.getAgents().getMolecules() != null
						&& singulator.getAgents().getMolecules().getLzw() != null)
					dp.loadData();
				break;
			case Constants.ID_TRANSPORTER:
				dp.deleteAllData();
				if (singulator.getTransporters() != null)
					dp.loadData();
				break;
			case Constants.ID_FEEDER:
				dp.deleteAllData();
				if (singulator.getFeeder() != null)
					dp.loadData();
				break;
			case Constants.ID_KILL:
				dp.deleteAllData();
				if (singulator.getEvents() != null && singulator.getEvents().getKill() != null)
					dp.loadData();
				break;
			case Constants.ID_REACTION:
				dp.deleteAllData();
				if (singulator.getEvents() != null && singulator.getEvents().getReaction() != null)
					dp.loadData();
				break;
			case Constants.ID_TRANSFORM:
				dp.deleteAllData();
				if (singulator.getEvents() != null && singulator.getEvents().getTransform() != null)
					dp.loadData();
				break;
			}
		}

		// Go to the first tab
		pointer = 0;
		tabbedPaneContent.setSelectedIndex(pointer);

		return toRet;
	}

	protected void deleteAllInformation() {
		this.singulator = new JsonSingulator();
		for (DataPanel dp : mapIndexPanel.values()) {
			dp.deleteAllData();
		}

		// Go to the first tab
		pointer = 0;
		tabbedPaneContent.setSelectedIndex(pointer);
	}

	public boolean finish(final String outputDir, final String packageName, final Boolean[] checkBoxes) {
		boolean doSomething = false;

		for (int i = 0; i < checkBoxes.length; i++) {
			if (checkBoxes[i]) {
				generators[i].generateModel(singulator, Paths.get(outputDir), packageName);
				doSomething = true;
			}
		}

		return doSomething;
	}

	protected boolean saveJson() {
		boolean toRet = false;

		File fileToSave = WebFileChooser.showSaveDialog(this, "");
		// Si selecciona algo
		if (fileToSave != null) {
			try {
				// Si tiene extension JSON guardar
				if (FilenameUtils.getExtension(fileToSave.getName()).equalsIgnoreCase(Constants.FILTER_JSON)) {
					Files.deleteIfExists(fileToSave.toPath());
					Files.write(fileToSave.toPath(), Functions.singulatorToJson(singulator).getBytes(),
							StandardOpenOption.CREATE);
				}
				// Si no la tiene eliminar la que hay y cambiarla
				else {
					fileToSave = new File(fileToSave.getParentFile(),
							FilenameUtils.getBaseName(fileToSave.getName()) + "." + Constants.FILTER_JSON);

					Files.deleteIfExists(fileToSave.toPath());
					Files.write(fileToSave.toPath(), Functions.singulatorToJson(singulator).getBytes(),
							StandardOpenOption.CREATE);
				}

				toRet = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return toRet;
	}

	protected File showChooser() {
		File toRet = null;
		JFileChooser jsonChooser = null;

		if (jsonChooser == null) {
			jsonChooser = new JFileChooser();
			jsonChooser.setMultiSelectionEnabled(false);
			jsonChooser.setAcceptAllFileFilterUsed(false);
			jsonChooser.resetChoosableFileFilters();
			jsonChooser.setFileFilter(new JsonFilesFilter());
		}
		if (jsonChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			toRet = jsonChooser.getSelectedFile();
		}

		return toRet;
	}

	protected void loadJSON(File loadedFile) throws JsonSyntaxException, FileNotFoundException {
		this.singulator = Functions.jsonToSingulator(Functions.readFileInString(new FileInputStream(loadedFile)));

		this.agents = singulator.getAgents();
		this.molecules = singulator.getAgents().getMolecules();
		this.events = singulator.getEvents();

		disableAllTabs();
		int index = setSingulatorInValueOnLoad();
		enableTabsFromIndex(index);
	}

	protected int getPointer() {
		return pointer;
	}

	protected int getMaxCardNumber() {
		return maxCardNumber;
	}

	protected void enableTabsFromIndex(int index) {
		tabbedPaneContent.setEnabledAt(Constants.ID_ENVIRONMENT, true);
		mapIndexLabel.get(Constants.ID_ENVIRONMENT).setEnabled(true);
		if (index == 2) {
			tabbedPaneContent.setEnabledAt(Constants.ID_UNITY, true);
			tabbedPaneContent.setEnabledAt(Constants.ID_CELL, true);
			tabbedPaneContent.setEnabledAt(Constants.ID_RIBOSOME, true);
			tabbedPaneContent.setEnabledAt(Constants.ID_MACROMOLECULE, true);
			tabbedPaneContent.setEnabledAt(Constants.ID_BUILDINGBLOCK, true);
			tabbedPaneContent.setEnabledAt(Constants.ID_LZW, true);
			tabbedPaneContent.setEnabledAt(Constants.ID_TRANSPORTER, true);
			tabbedPaneContent.setEnabledAt(Constants.ID_FEEDER, true);
			tabbedPaneContent.setEnabledAt(Constants.ID_KILL, true);
			tabbedPaneContent.setEnabledAt(Constants.ID_REACTION, true);
			tabbedPaneContent.setEnabledAt(Constants.ID_TRANSFORM, true);

			mapIndexLabel.get(Constants.ID_UNITY).setEnabled(true);
			mapIndexLabel.get(Constants.ID_CELL).setEnabled(true);
			mapIndexLabel.get(Constants.ID_RIBOSOME).setEnabled(true);
			mapIndexLabel.get(Constants.ID_MACROMOLECULE).setEnabled(true);
			mapIndexLabel.get(Constants.ID_BUILDINGBLOCK).setEnabled(true);
			mapIndexLabel.get(Constants.ID_LZW).setEnabled(true);
			mapIndexLabel.get(Constants.ID_TRANSPORTER).setEnabled(true);
			mapIndexLabel.get(Constants.ID_FEEDER).setEnabled(true);
			mapIndexLabel.get(Constants.ID_KILL).setEnabled(true);
			mapIndexLabel.get(Constants.ID_REACTION).setEnabled(true);
			mapIndexLabel.get(Constants.ID_TRANSFORM).setEnabled(true);
		}
	}

	protected void disableAllTabs() {
		int numTabs = tabbedPaneContent.getTabCount();

		for (int i = 1; i < numTabs; i++) {
			tabbedPaneContent.setEnabledAt(i, false);
			mapIndexLabel.get(i).setEnabled(false);
		}
	}

	private void propagateData() {
		// Get current panel
		DataPanel dp = mapIndexPanel.get(pointer);
		// Verify current panel data
		if (dp.verifyAllData()) {
			Object[] data = dp.getAllData();

			// Save panel data in the model
			setValueInSingulator(data, pointer);

			for (DataPanel dataPanel : mapIndexPanel.values()) {
				dataPanel.updateComponentsData(singulator);
			}
		}
	}

	protected void setTextInVisualizer() {
		jsonVisualizer.setText(Functions.singulatorToJson(singulator));
		jsonVisualizer.setLocationRelativeTo(this);
		jsonVisualizer.setVisible(true);
	}

	protected void showPreferences() {
		preferencesDialog.setLocationRelativeTo(this);
		preferencesDialog.setVisible(true);
	}

	protected void showAbout() {
		aboutDialog.setLocationRelativeTo(this);
		aboutDialog.setVisible(true);
	}

	public void setInitialOption(int initialOption) {
		// Execute load, if needed
		if (initialOption == 2)
			panelNorth.pressLoad(false);
	}
}
