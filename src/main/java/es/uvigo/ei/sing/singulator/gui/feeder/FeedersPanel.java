package es.uvigo.ei.sing.singulator.gui.feeder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.custom.tabbedpanes.ButtonTabComponent;
import es.uvigo.ei.sing.singulator.gui.custom.tabbedpanes.CustomTabbedPane;
import es.uvigo.ei.sing.singulator.gui.custom.validators.TabbedPaneValidator;
import es.uvigo.ei.sing.singulator.gui.interfaces.DataPanel;
import es.uvigo.ei.sing.singulator.model.JsonFeeder;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public class FeedersPanel extends WebPanel implements DataPanel {

	private static final long serialVersionUID = 1L;

	private CustomTabbedPane tabbedData;

	protected WebPanel panelLayers;

	private int index;

	protected JsonSingulator jsonSingulator;

	public FeedersPanel(JsonSingulator jsonSingulator) {
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
					addTab(new FeedersDataPanel(getFeedersPanel()));
				}
			});

			add(tabbedData, BorderLayout.CENTER);
		}
	}

	public FeedersPanel getFeedersPanel() {
		return this;
	}

	public void addTab(FeedersDataPanel fdp) {
		String name = Constants.TABBED_TAB_NAME + index;

		WebScrollPane scrollDataFeeder = new WebScrollPane(fdp);
		scrollDataFeeder.getVerticalScrollBar().setUnitIncrement(16);
		scrollDataFeeder.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDataFeeder.setBorder(null);

		ButtonTabComponent btc = new ButtonTabComponent(name, tabbedData);

		tabbedData.insertTab(name, null, scrollDataFeeder, null, tabbedData.getTabCount() - 1);
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

			JsonFeeder feeder;
			FeedersDataPanel fdp;
			WebScrollPane wsp;
			String type;
			// Recorrer todas las tabs
			for (int i = 0; i < numberOfTabs - 1; i++) {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				fdp = (FeedersDataPanel) wsp.getViewport().getView();
				feeder = new JsonFeeder();

				if (fdp.getFeederCreate().endsWith(Constants.GUI_MACROMOLECULE))
					feeder.setCreate(Constants.JSON_MACROMOLECULE.concat(fdp.getFeederCreate().replace(Constants.GUI_MACROMOLECULE, "")));
				if (fdp.getFeederCreate().endsWith(Constants.GUI_LZW))
					feeder.setCreate(Constants.JSON_LZW.concat(fdp.getFeederCreate().replace(Constants.GUI_LZW, "")));
				if (fdp.getFeederCreate().endsWith(Constants.GUI_BUILDINGBLOCK))
					feeder.setCreate(Constants.JSON_BUILDINGBLOCK.concat(fdp.getFeederCreate().replace(Constants.GUI_BUILDINGBLOCK, "")));

				type = fdp.getFeederType();
				feeder.setType(type);
				if (!fdp.getFeederLocation().equals(Constants.EXTRACELLULAR_NAME))
					feeder.setLocation(Constants.JSON_CELL + fdp.getFeederLocation());
				else
					feeder.setLocation(fdp.getFeederLocation());
				feeder.setEveryStep(fdp.getFeederEveryStep());
				feeder.setProductionNumber(fdp.getFeederProductNumber());
				if (type.equals(Constants.FEEDER_TYPE_NONCONTINUOUS)) {
					feeder.setMaxConcentration(fdp.getFeederMaxConcentration());
				} else {
					feeder.setMaxConcentration(0);
				}

				toRet[i] = feeder;
			}
		}

		return toRet;
	}

	@Override
	public boolean verifyAllData() {
		boolean toRet = true;
		FeedersDataPanel fdp;
		WebScrollPane wsp;
		int tabCount = tabbedData.getTabCount();

		for (int i = 0; i < tabCount; i++) {
			try {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				if (wsp != null) {
					fdp = (FeedersDataPanel) wsp.getViewport().getView();

					toRet &= fdp.verifyAllData();

					TabbedPaneValidator val = (TabbedPaneValidator) tabbedData.getInputVerifier();
					val.verify(toRet, Constants.VALIDATOR_TABBED_CODE_GENERAL);
					if (!toRet)
						break;
				}
			} catch (ClassCastException e) {
				toRet = true;
			}
		}

		return toRet;
	}

	@Override
	public void loadData() {
		JsonFeeder[] feeders = jsonSingulator.getFeeder();
		FeedersDataPanel fdp;

		for (JsonFeeder feeder : feeders) {
			// Añadir nueva tab para cargar los datos
			fdp = new FeedersDataPanel(getFeedersPanel());
			addTab(fdp);
			if (feeder.getCreate().startsWith(Constants.JSON_MACROMOLECULE))
				fdp.setFeederCreate(feeder.getCreate().replaceFirst(Constants.JSON_MACROMOLECULE, "").concat(Constants.GUI_MACROMOLECULE));
			if (feeder.getCreate().startsWith(Constants.JSON_LZW))
				fdp.setFeederCreate(feeder.getCreate().replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
			if (feeder.getCreate().startsWith(Constants.JSON_BUILDINGBLOCK))
				fdp.setFeederCreate(feeder.getCreate().replaceFirst(Constants.JSON_BUILDINGBLOCK, "").concat(Constants.GUI_BUILDINGBLOCK));
			fdp.setFeederType(feeder.getType());
			fdp.setFeederLocation(feeder.getLocation().replaceFirst(Constants.JSON_CELL, ""));
			fdp.setFeederEveryStep(feeder.getEveryStep());
			fdp.setFeederProductNumber(feeder.getProductionNumber());
			fdp.setFeederMaxConcentration(feeder.getMaxConcentration());
		}
	}

	@Override
	public void updateComponentsData(JsonSingulator jsonSingulator) {
		WebScrollPane wsp;
		FeedersDataPanel fdp;

		for (int i = 0; i < tabbedData.getTabCount() - 1; i++) {
			wsp = (WebScrollPane) tabbedData.getComponentAt(i);
			fdp = (FeedersDataPanel) wsp.getViewport().getView();

			fdp.setCbCreateValues();
			fdp.setCbLocation();
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
		return Constants.FEEDER_TITLE;
	}
}
