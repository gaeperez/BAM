package es.uvigo.ei.sing.singulator.gui.cell;

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
import es.uvigo.ei.sing.singulator.model.JsonCell;
import es.uvigo.ei.sing.singulator.model.JsonLayer;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public class CellsPanel extends WebPanel implements DataPanel {

	private static final long serialVersionUID = 1L;

	private CustomTabbedPane tabbedData;
	protected WebPanel panelLayers;

	private int index;

	protected JsonSingulator jsonSingulator;

	public CellsPanel() {
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
					addTab(new CellsDataPanel(getCellsPanel()));
				}
			});

			add(tabbedData);
		}
	}

	public CellsPanel getCellsPanel() {
		return this;
	}

	public void addTab(CellsDataPanel cdp) {
		String name = Constants.TABBED_TAB_NAME + index;

		WebScrollPane scrollDataCell = new WebScrollPane(cdp);
		scrollDataCell.getVerticalScrollBar().setUnitIncrement(16);
		scrollDataCell.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDataCell.setBorder(null);

		ButtonTabComponent btc = new ButtonTabComponent(name, tabbedData);

		tabbedData.insertTab(name, null, scrollDataCell, null, tabbedData.getTabCount() - 1);
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
			JsonCell cell;
			JsonLayer layer;
			JsonLayer[] layers;
			Collection<LayersDataPanel> listLayers;
			CellsDataPanel cdp;
			WebScrollPane wsp;
			int index = 0;
			Double aux;
			// Recorrer todas las tabs
			for (int i = 0; i < numberOfTabs - 1; i++) {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				cdp = (CellsDataPanel) wsp.getViewport().getView();
				cell = new JsonCell();

				// Setear valores basicos de la celula
				cell.setCellName(Constants.JSON_CELL.concat(cdp.getCellName()));
				cell.setLayerName(cdp.getCellLayerName());
				aux = cdp.getCellRadius();
				if (aux != null)
					cell.setRadius(aux);
				aux = cdp.getCellHeight();
				if (aux != null)
					cell.setHeight(aux);
				cell.setColor(cdp.getCellColor());
				cell.setNumber(cdp.getCellNumber());
				cell.setForm(cdp.getCellForm());

				listLayers = cdp.getListLayersPanel();
				layers = new JsonLayer[listLayers.size()];

				// Recorrer capas si hay alguna
				for (LayersDataPanel layerPanel : listLayers) {
					layer = new JsonLayer();

					layer.setName(layerPanel.getLayerName());
					layer.setRadius(layerPanel.getLayerRadius());
					layer.setHeight(layerPanel.getLayerHeight());
					layer.setColor(layerPanel.getLayerColor());

					layers[index] = layer;

					index++;
				}

				// Asignar capas a la célula
				cell.setLayers(layers);

				// Guardar cell en array
				toRet[i] = cell;

				// Resetear variables
				index = 0;
			}
		}

		return toRet;
	}

	@Override
	public boolean verifyAllData() {
		boolean toRet = true;
		Collection<LayersDataPanel> listLayers;
		CellsDataPanel cdp;
		WebScrollPane wsp;
		int tabCount = tabbedData.getTabCount();

		// Comprobar errores en los contenidos de las tabs
		for (int i = 0; i < tabCount; i++) {
			try {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				if (wsp != null) {
					cdp = (CellsDataPanel) wsp.getViewport().getView();

					toRet &= cdp.verifyAllData();

					listLayers = cdp.getListLayersPanel();
					// Recorrer capas si hay alguna
					for (LayersDataPanel layerPanel : listLayers) {
						toRet &= layerPanel.verifyAllData();
					}

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
		JsonCell[] cells = jsonSingulator.getCells();
		CellsDataPanel cdp;
		LayersDataPanel ldp;

		for (JsonCell cell : cells) {
			// Añadir nueva tab para cargar los datos
			cdp = new CellsDataPanel(getCellsPanel());
			addTab(cdp);

			cdp.setCellName(cell.getCellName().replaceFirst(Constants.JSON_CELL, ""));
			cdp.setCellRadius(cell.getRadius());
			cdp.setCellHeight(cell.getHeight());
			cdp.setCellColor(cell.getColor());
			cdp.setCellNumber(cell.getNumber());
			cdp.setCellForm(cell.getForm());

			// Crear panel para las capas
			for (JsonLayer layer : cell.getLayers()) {
				ldp = new LayersDataPanel(cdp);

				ldp.setLayerName(layer.getName());
				ldp.setLayerRadius(layer.getRadius());
				ldp.setLayerHeight(layer.getHeight());
				ldp.setLayerColor(layer.getColor());

				cdp.addLayer(ldp);
			}
		}

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
		tabbedData.removelAllExceptButton();

		revalidate();
	}

	@Override
	public String getPanelName() {
		return Constants.CELLS_TITLE;
	}
}
