package es.uvigo.ei.sing.singulator.gui.ribosome;

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
import es.uvigo.ei.sing.singulator.model.JsonRibosome;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public class RibosomePanel extends WebPanel implements DataPanel {

	private static final long serialVersionUID = 1L;

	private CustomTabbedPane tabbedData;

	protected WebPanel panelLayers;

	private int index;

	protected JsonSingulator jsonSingulator;

	public RibosomePanel(JsonSingulator jsonSingulator) {
		this.index = 0;
		this.jsonSingulator = jsonSingulator;

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
					addTab(new RibosomeDataPanel(getRibosomesPanel()));
				}
			});

			add(tabbedData);
		}
	}

	public RibosomePanel getRibosomesPanel() {
		return this;
	}

	public void addTab(RibosomeDataPanel rdp) {
		String name = Constants.TABBED_TAB_NAME + index;

		WebScrollPane scrollDataRibosome = new WebScrollPane(rdp);
		scrollDataRibosome.getVerticalScrollBar().setUnitIncrement(16);
		scrollDataRibosome.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDataRibosome.setBorder(null);

		ButtonTabComponent btc = new ButtonTabComponent(name, tabbedData);

		tabbedData.insertTab(name, null, scrollDataRibosome, null, tabbedData.getTabCount() - 1);
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

	public void changeTabName(String name) {
		tabbedData.refreshMapLayerName(name);
	}

	@Override
	public Object[] getAllData() {
		int numberOfTabs = tabbedData.getTabCount();
		Object[] toRet = null;

		if (numberOfTabs > 1) {
			toRet = new Object[numberOfTabs - 1];
			JsonRibosome ribosome;
			// JsonDiffusionRate dr;
			RibosomeDataPanel rdp;
			WebScrollPane wsp;
			// Recorrer todas las tabs
			for (int i = 0; i < numberOfTabs - 1; i++) {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				rdp = (RibosomeDataPanel) wsp.getViewport().getView();

				ribosome = new JsonRibosome();

				ribosome.setName(Constants.JSON_RIBOSOME.concat(rdp.getRibosomeName()));
				
				ribosome.setMolecularWeight(rdp.getRibosomeMW());
				ribosome.setRadius(rdp.getRibosomeRadius());

				// Guardar diffusionRate
				// dr = new JsonDiffusionRate();
				// dr.setExterior(rdp.getMoleculeDRExterior());
				// dr.setOuterMembrane(rdp.getMoleculeDROMembrane());
				// dr.setOuterPeriplasm(rdp.getMoleculeDROPeriplasm());
				// dr.setPeptidoglycan(rdp.getMoleculeDRPeptidoglycan());
				// dr.setInnerPeriplasm(rdp.getMoleculeDRIPeriplasm());
				// dr.setInnerMembrane(rdp.getMoleculeDRIMembrane());
				// dr.setCytoplasm(rdp.getMoleculeDRCytoplasm());
				ribosome.setDiffusionRate(rdp.getRibosomeDRExterior());

				ribosome.setColor(rdp.getRibosomeColor());
				ribosome.setNumber(rdp.getRibosomeNumber());
				ribosome.setMaxLayer(rdp.getRibosomeMaxLayer());
				ribosome.setMinLayer(rdp.getRibosomeMinLayer());
				if (!rdp.getRibosomeCellLocalization().equals(Constants.EXTRACELLULAR_NAME))
					ribosome.setCellLocalization(Constants.JSON_CELL + rdp.getRibosomeCellLocalization());
				else
					ribosome.setCellLocalization(rdp.getRibosomeCellLocalization());
				ribosome.setLayerLocalization(rdp.getRibosomeLayerLocalization());
				ribosome.setRadInfl(rdp.getRibosomeRadInfl());
				// ribosome.setRadInflWith(rdp.getRibosomeRadInflWith());
				ribosome.setmRNA(rdp.getRibosomeMRNA());

				toRet[i] = ribosome;
			}
		}

		return toRet;
	}

	@Override
	public boolean verifyAllData() {
		boolean toRet = true;
		RibosomeDataPanel rdp;
		WebScrollPane wsp;
		int tabCount = tabbedData.getTabCount();

		for (int i = 0; i < tabCount; i++) {
			try {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				if (wsp != null) {
					rdp = (RibosomeDataPanel) wsp.getViewport().getView();

					toRet &= rdp.verifyAllData();

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
		JsonRibosome[] ribosomes = jsonSingulator.getAgents().getRibosomes();
		// JsonDiffusionRate dr;
		RibosomeDataPanel rdp;

		for (JsonRibosome ribosome : ribosomes) {
			// Añadir nueva tab para cargar los datos
			rdp = new RibosomeDataPanel(getRibosomesPanel());
			addTab(rdp);

			rdp.setRibosomeName(ribosome.getName().replaceFirst(Constants.JSON_RIBOSOME, ""));
			rdp.setRibosomeMW(ribosome.getMolecularWeight());
			rdp.setRibosomeRadius(ribosome.getRadius());

			// dr = ribosome.getDiffusionRate();
			// rdp.setRibosomeDRExterior(dr.getExterior());
			// rdp.setRibosomeDROMembrane(dr.getOuterMembrane());
			// rdp.setRibosomeDROPeriplasm(dr.getOuterPeriplasm());
			// rdp.setRibosomeDRPeptidoglycan(dr.getPeptidoglycan());
			// rdp.setRibosomeDRIPeriplasm(dr.getInnerPeriplasm());
			// rdp.setRibosomeDRIMembrane(dr.getInnerMembrane());
			// rdp.setRibosomeDRCytoplasm(dr.getCytoplasm());
			rdp.setRibosomeDRExterior(ribosome.getDiffusionRate());

			rdp.setRibosomeColor(ribosome.getColor());
			rdp.setRibosomeNumber(ribosome.getNumber());
			rdp.setRibosomeMaxLayer(ribosome.getMaxLayer());
			rdp.setRibosomeMinLayer(ribosome.getMinLayer());
			rdp.setRibosomeCellLocalization(ribosome.getCellLocalization().replaceFirst(Constants.JSON_CELL, ""));
			rdp.setRibosomeLayerLocalization(ribosome.getLayerLocalization());
			rdp.setRibosomeRadInfl(ribosome.getRadInfl());
			// rdp.setRibosomeRadInflWith(ribosome.getRadInflWith());
			rdp.setRibosomeMRNA(ribosome.getmRNA());
		}
	}

	@Override
	public void updateComponentsData(JsonSingulator jsonSingulator) {
		WebScrollPane wsp;
		RibosomeDataPanel rdp;

		for (int i = 0; i < tabbedData.getTabCount() - 1; i++) {
			wsp = (WebScrollPane) tabbedData.getComponentAt(i);
			rdp = (RibosomeDataPanel) wsp.getViewport().getView();

			// Insertar cbCellLocalization
			rdp.setCbCellLocalizationValues();
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
		return Constants.RIBOSOMES_TITLE;
	}
}
