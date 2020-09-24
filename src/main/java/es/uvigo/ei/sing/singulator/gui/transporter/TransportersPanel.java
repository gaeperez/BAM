package es.uvigo.ei.sing.singulator.gui.transporter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
import es.uvigo.ei.sing.singulator.model.JsonSingulator;
import es.uvigo.ei.sing.singulator.model.JsonTransporter;

public class TransportersPanel extends WebPanel implements DataPanel {

	private static final long serialVersionUID = 1L;

	private CustomTabbedPane tabbedData;

	protected WebPanel panelLayers;

	private int index;

	protected JsonSingulator jsonSingulator;

	public TransportersPanel(JsonSingulator jsonSingulator) {
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
					addTab(new TransportersDataPanel(getTransportersPanel()));
				}
			});

			add(tabbedData, BorderLayout.CENTER);
		}
	}

	public TransportersPanel getTransportersPanel() {
		return this;
	}

	public void addTab(TransportersDataPanel tdp) {
		String name = Constants.TABBED_TAB_NAME + index;

		WebScrollPane scrollDataTransporter = new WebScrollPane(tdp);
		scrollDataTransporter.getVerticalScrollBar().setUnitIncrement(16);
		scrollDataTransporter.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDataTransporter.setBorder(null);

		ButtonTabComponent btc = new ButtonTabComponent(name, tabbedData);

		tabbedData.insertTab(name, null, scrollDataTransporter, null, tabbedData.getTabCount() - 1);
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

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getAllData() {
		int numberOfTabs = tabbedData.getTabCount();
		Object[] toRet = null;

		if (numberOfTabs > 1) {
			toRet = new Object[numberOfTabs - 1];

			int index = 0;
			String[] arrayAux;
			List<Object> listAux;
			TransportersDataPanel tdp;
			JsonTransporter transporter;
			WebScrollPane wsp;
			// Recorrer todas las tabs
			for (int i = 0; i < numberOfTabs - 1; i++) {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				tdp = (TransportersDataPanel) wsp.getViewport().getView();
				transporter = new JsonTransporter();

				transporter.setName(Constants.JSON_TRANSPORTER.concat(tdp.getTransporterName()));
				transporter.setCellName(Constants.JSON_CELL.concat(tdp.getTransporterCellName()));
				transporter.setRadius(tdp.getTransporterRadius());
				transporter.setDiffusionRate(tdp.getTransporterDR());
				transporter.setColor(tdp.getTransporterColor());
				transporter.setNumber(tdp.getTransporterNumber());
				transporter.setOuterLayer(tdp.getTransporterOuterLayer());
				transporter.setInnerLayer(tdp.getTransporterInnerLayer());
				transporter.setGetFrom(tdp.getTransporterGetFrom());
				transporter.setPutTo(tdp.getTransporterPutTo());
				transporter.setType(tdp.getTransporterType());

				index = 0;
				listAux = tdp.getTransporterInputs();
				arrayAux = new String[listAux.size()];
				for (Object obj : listAux) {
					if (obj.toString().endsWith(Constants.GUI_MACROMOLECULE))
						arrayAux[index] = Constants.JSON_MACROMOLECULE.concat(obj.toString().replace(Constants.GUI_MACROMOLECULE, ""));
					if (obj.toString().endsWith(Constants.GUI_LZW))
						arrayAux[index] = Constants.JSON_LZW.concat(obj.toString().replace(Constants.GUI_LZW, ""));
					if (obj.toString().endsWith(Constants.GUI_BUILDINGBLOCK))
						arrayAux[index] = Constants.JSON_BUILDINGBLOCK.concat(obj.toString().replace(Constants.GUI_BUILDINGBLOCK, ""));
					index++;
				}
				transporter.setInputs(arrayAux);

				index = 0;
				listAux = tdp.getTransporterOutputs();
				arrayAux = new String[listAux.size()];
				for (Object obj : listAux) {
					if (obj.toString().endsWith(Constants.GUI_MACROMOLECULE))
						arrayAux[index] = Constants.JSON_MACROMOLECULE.concat(obj.toString().replace(Constants.GUI_MACROMOLECULE, ""));
					if (obj.toString().endsWith(Constants.GUI_LZW))
						arrayAux[index] = Constants.JSON_LZW.concat(obj.toString().replace(Constants.GUI_LZW, ""));
					if (obj.toString().endsWith(Constants.GUI_BUILDINGBLOCK))
						arrayAux[index] = Constants.JSON_BUILDINGBLOCK.concat(obj.toString().replace(Constants.GUI_BUILDINGBLOCK, ""));
					index++;
				}
				transporter.setOutputs(arrayAux);

				toRet[i] = transporter;
			}
		}

		return toRet;
	}

	@Override
	public boolean verifyAllData() {
		boolean toRet = true;
		TransportersDataPanel tdp;
		WebScrollPane wsp;
		int tabCount = tabbedData.getTabCount();

		for (int i = 0; i < tabCount; i++) {
			try {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				if (wsp != null) {
					tdp = (TransportersDataPanel) wsp.getViewport().getView();

					toRet &= tdp.verifyAllData();

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
		JsonTransporter[] transporters = jsonSingulator.getTransporters();
		TransportersDataPanel tdp;

		for (JsonTransporter transporter : transporters) {
			// Añadir nueva tab para cargar los datos
			tdp = new TransportersDataPanel(getTransportersPanel());
			addTab(tdp);

			tdp.setTransporterName(transporter.getName().replaceFirst(Constants.JSON_TRANSPORTER, ""));
			tdp.setTransporterCellName(transporter.getCellName().replaceFirst(Constants.JSON_CELL, ""));
			tdp.setTransporterRadius(transporter.getRadius());
			tdp.setTransporterDR(transporter.getDiffusionRate());
			tdp.setTransporterColor(transporter.getColor());
			tdp.setTransporterNumber(transporter.getNumber());
			tdp.setTransporterOuterLayer(transporter.getOuterLayer());
			tdp.setTransporterInnerLayer(transporter.getInnerLayer());
			tdp.setTransporterGetFrom(transporter.getGetFrom());
			tdp.setTransporterPutTo(transporter.getPutTo());
			tdp.setTransporterType(transporter.getType());
			final List<String> inputs = new ArrayList<>();
			for (String input : transporter.getInputs()) {
				if (input.startsWith(Constants.JSON_MACROMOLECULE))
					inputs.add(input.replaceFirst(Constants.JSON_MACROMOLECULE, "").concat(Constants.GUI_MACROMOLECULE));
				if (input.startsWith(Constants.JSON_LZW))
					inputs.add(input.replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
				if (input.startsWith(Constants.JSON_BUILDINGBLOCK))
					inputs.add(input.replaceFirst(Constants.JSON_BUILDINGBLOCK, "").concat(Constants.GUI_BUILDINGBLOCK));
			}
			tdp.setTransporterInputs(inputs.toArray(new String[inputs.size()]));
			final List<String> outputs = new ArrayList<>();
			for (String output : transporter.getOutputs()) {
				if (output.startsWith(Constants.JSON_MACROMOLECULE))
					outputs.add(output.replaceFirst(Constants.JSON_MACROMOLECULE, "").concat(Constants.GUI_MACROMOLECULE));
				if (output.startsWith(Constants.JSON_LZW))
					outputs.add(output.replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
				if (output.startsWith(Constants.JSON_BUILDINGBLOCK))
					outputs.add(output.replaceFirst(Constants.JSON_BUILDINGBLOCK, "").concat(Constants.GUI_BUILDINGBLOCK));
			}
			tdp.setTransporterOutputs(outputs.toArray(new String[outputs.size()]));
		}
	}

	@Override
	public void updateComponentsData(JsonSingulator jsonSingulator) {
		WebScrollPane wsp;
		TransportersDataPanel tdp;

		for (int i = 0; i < tabbedData.getTabCount() - 1; i++) {
			wsp = (WebScrollPane) tabbedData.getComponentAt(i);
			tdp = (TransportersDataPanel) wsp.getViewport().getView();

			tdp.setcbCellNameValues();
			tdp.setListInputValues();
			tdp.setListOutputValues();
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
		return Constants.TRANSPORTERS_TITLE;
	}
}
