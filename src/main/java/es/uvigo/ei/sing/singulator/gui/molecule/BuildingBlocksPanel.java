package es.uvigo.ei.sing.singulator.gui.molecule;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.custom.tabbedpanes.ButtonTabComponent;
import es.uvigo.ei.sing.singulator.gui.custom.tabbedpanes.CustomTabbedPane;
import es.uvigo.ei.sing.singulator.gui.custom.validators.TabbedPaneValidator;
import es.uvigo.ei.sing.singulator.gui.interfaces.DataPanel;
import es.uvigo.ei.sing.singulator.gui.interfaces.MoleculePanel;
import es.uvigo.ei.sing.singulator.model.JsonBuildingblock;
import es.uvigo.ei.sing.singulator.model.JsonDiffusionRate;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public class BuildingBlocksPanel extends WebPanel implements DataPanel, MoleculePanel {

	private static final long serialVersionUID = 1L;

	private CustomTabbedPane tabbedData;

	protected WebPanel panelLayers;

	private int index;

	protected JsonSingulator jsonSingulator;

	public BuildingBlocksPanel(JsonSingulator jsonSingulator) {
		this.jsonSingulator = jsonSingulator;
		this.index = 0;

		init();
	}

	public void init() {
		{
			setLayout(new GridLayout(1, 0));
			setPaintFocus(true);
			setMargin(7);
		}
		{
			tabbedData = new CustomTabbedPane();
			tabbedData.setTabPlacement(SwingConstants.TOP);

			tabbedData.setActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					addTab(new MoleculesDataPanel(getMoleculePanel()));
				}
			});

			add(tabbedData);
		}
	}

	public BuildingBlocksPanel getMoleculesPanel() {
		return this;
	}

	public void addTab(MoleculesDataPanel mdp) {
		String name = Constants.TABBED_TAB_NAME + index;

		WebScrollPane scrollDataMolecule = new WebScrollPane(mdp);
		scrollDataMolecule.getVerticalScrollBar().setUnitIncrement(16);
		scrollDataMolecule.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDataMolecule.setBorder(null);

		ButtonTabComponent btc = new ButtonTabComponent(name, tabbedData);

		tabbedData.insertTab(name, null, scrollDataMolecule, null, tabbedData.getTabCount() - 1);
		tabbedData.putMapIdTab(name, btc);
		tabbedData.setTabComponentAt(tabbedData.getTabCount() - 2, btc);

		tabbedData.setSelectedIndex(tabbedData.getTabCount() - 2);

		index++;
	}

	public void removeLayer(Component comp) {
		panelLayers.remove(comp);
		repaint();
		revalidate();
	}

	@Override
	public void changeTabName(String name) {
		tabbedData.refreshMapLayerName(name);
	}

	@Override
	public Object[] getAllData() {
		int numberOfTabs = tabbedData.getTabCount();
		Object[] toRet = null;

		if (numberOfTabs > 1) {
			toRet = new Object[numberOfTabs - 1];
			JsonBuildingblock building;
			JsonDiffusionRate dr;
			MoleculesDataPanel mdp;
			WebScrollPane wsp;
			// Recorrer todas las tabs
			for (int i = 0; i < numberOfTabs - 1; i++) {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				mdp = (MoleculesDataPanel) wsp.getViewport().getView();

				building = new JsonBuildingblock();

				building.setName(Constants.JSON_BUILDINGBLOCK.concat(mdp.getMoleculeName()));
				building.setType(mdp.getMoleculeType());
				building.setMolecularWeight(mdp.getMoleculeMW());
				building.setRadius(mdp.getMoleculeRadius());

				// Guardar diffusionRate
				dr = new JsonDiffusionRate();
				dr.setExterior(mdp.getMoleculeDRExterior());
				dr.setOuterMembrane(mdp.getMoleculeDROMembrane());
				dr.setOuterPeriplasm(mdp.getMoleculeDROPeriplasm());
				dr.setPeptidoglycan(mdp.getMoleculeDRPeptidoglycan());
				dr.setInnerPeriplasm(mdp.getMoleculeDRIPeriplasm());
				dr.setInnerMembrane(mdp.getMoleculeDRIMembrane());
				dr.setCytoplasm(mdp.getMoleculeDRCytoplasm());
				building.setDiffusionRate(dr);

				building.setColor(mdp.getMoleculeColor());
				building.setNumber(mdp.getMoleculeNumber());
				building.setMaxLayer(mdp.getMoleculeMaxLayer());
				building.setMinLayer(mdp.getMoleculeMinLayer());
				if (!mdp.getMoleculeCellLocalization().equals(Constants.EXTRACELLULAR_CODE))
					building.setCellLocalization(Constants.JSON_CELL + mdp.getMoleculeCellLocalization());
				else
					building.setCellLocalization(mdp.getMoleculeCellLocalization());
				building.setLayerLocalization(mdp.getMoleculeLayerLocalization());
				building.setRadInfl(mdp.getMoleculeRadInfl());
				// molecule.setRadInflWith(mdp.getMoleculeRadInflWith());

				toRet[i] = building;
			}
		}

		return toRet;
	}

	@Override
	public boolean verifyAllData() {
		boolean toRet = true;
		MoleculesDataPanel mdp;
		WebScrollPane wsp;
		int tabCount = tabbedData.getTabCount();

		for (int i = 0; i < tabCount; i++) {
			try {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				if (wsp != null) {
					mdp = (MoleculesDataPanel) wsp.getViewport().getView();

					toRet &= mdp.verifyAllData();

					TabbedPaneValidator val = (TabbedPaneValidator) tabbedData.getInputVerifier();
					val.verify(toRet, Constants.VALIDATOR_TABBED_CODE_GENERAL);
					if (!toRet)
						break;
				}
			} catch (ClassCastException e) {
				toRet = true;
			}
		}
		// Comprobar errores de nombres duplicados
		Collection<String> mapValues = tabbedData.getMapIdName().values();
		Set<String> valuesSet = new HashSet<String>(mapValues);
		if (mapValues.size() != valuesSet.size()) {
			toRet = false;
			TabbedPaneValidator val = (TabbedPaneValidator) tabbedData.getInputVerifier();
			val.verify(toRet, Constants.VALIDATOR_TABBED_CODE_NAMES);
		}

		return toRet;
	}

	@Override
	public void loadData() {
		JsonBuildingblock[] buildings = jsonSingulator.getAgents().getMolecules().getBuildingBlocks();
		JsonDiffusionRate dr;
		MoleculesDataPanel mdp;

		for (JsonBuildingblock building : buildings) {
			// Añadir nueva tab para cargar los datos
			mdp = new MoleculesDataPanel(getMoleculePanel());
			addTab(mdp);

			mdp.setMoleculeName(building.getName().replaceFirst(Constants.JSON_BUILDINGBLOCK, ""));
			mdp.setMoleculeType(building.getType());
			mdp.setMoleculeMW(building.getMolecularWeight());
			mdp.setMoleculeRadius(building.getRadius());

			dr = building.getDiffusionRate();
			mdp.setMoleculeDRExterior(dr.getExterior());
			mdp.setMoleculeDROMembrane(dr.getOuterMembrane());
			mdp.setMoleculeDROPeriplasm(dr.getOuterPeriplasm());
			mdp.setMoleculeDRPeptidoglycan(dr.getPeptidoglycan());
			mdp.setMoleculeDRIPeriplasm(dr.getInnerPeriplasm());
			mdp.setMoleculeDRIMembrane(dr.getInnerMembrane());
			mdp.setMoleculeDRCytoplasm(dr.getCytoplasm());

			mdp.setMoleculeColor(building.getColor());
			mdp.setMoleculeNumber(building.getNumber());
			mdp.setMoleculeMaxLayer(building.getMaxLayer());
			mdp.setMoleculeMinLayer(building.getMinLayer());
			mdp.setMoleculeCellLocalization(building.getCellLocalization().replaceFirst(Constants.JSON_CELL, ""));
			mdp.setMoleculeLayerLocalization(building.getLayerLocalization());
			mdp.setMoleculeRadInfl(building.getRadInfl());
			// mdp.setMoleculeRadInflWith(molecule.getRadInflWith());
		}
	}

	@Override
	public void updateComponentsData(JsonSingulator jsonSingulator) {
		WebScrollPane wsp;
		MoleculesDataPanel mdp;

		for (int i = 0; i < tabbedData.getTabCount() - 1; i++) {
			wsp = (WebScrollPane) tabbedData.getComponentAt(i);
			mdp = (MoleculesDataPanel) wsp.getViewport().getView();

			// Insertar cbCellLocalization
			mdp.setCbCellLocalizationValues();
		}

		revalidate();
	}

	@Override
	public void setJsonSingulator(JsonSingulator jsonSingulator) {
		this.jsonSingulator = jsonSingulator;
	}

	@Override
	public void deleteAllData() {
		tabbedData.removelAllExceptButton();

		revalidate();
	}

	@Override
	public String getPanelName() {
		return Constants.BUILDINGBLOCKS_TITLE;
	}

	@Override
	public JsonSingulator getSingulator() {
		return jsonSingulator;
	}

	@Override
	public MoleculePanel getMoleculePanel() {
		return this;
	}
}
