package es.uvigo.ei.sing.singulator.gui.event;

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
import es.uvigo.ei.sing.singulator.model.JsonKill;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public class KillPanel extends WebPanel implements DataPanel {

	private static final long serialVersionUID = 1L;

	private CustomTabbedPane tabbedData;

	protected WebPanel panelLayers;

	private int index;

	protected JsonSingulator jsonSingulator;

	public KillPanel(JsonSingulator jsonSingulator) {
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
					addTab(new KillDataPanel(getKillPanel()));
				}
			});

			add(tabbedData, BorderLayout.CENTER);
		}
	}

	public KillPanel getKillPanel() {
		return this;
	}

	public void addTab(KillDataPanel kdp) {
		String name = Constants.TABBED_TAB_NAME + index;

		WebScrollPane scrollDataKill = new WebScrollPane(kdp);
		scrollDataKill.getVerticalScrollBar().setUnitIncrement(16);
		scrollDataKill.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDataKill.setBorder(null);

		ButtonTabComponent btc = new ButtonTabComponent(name, tabbedData);

		tabbedData.insertTab(name, null, scrollDataKill, null, tabbedData.getTabCount() - 1);
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

			KillDataPanel kdp;
			JsonKill kill;
			WebScrollPane wsp;
			// Recorrer todas las tabs
			for (int i = 0; i < numberOfTabs - 1; i++) {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				kdp = (KillDataPanel) wsp.getViewport().getView();

				kill = new JsonKill();

				// Input
				if (kdp.getKillInput().endsWith(Constants.GUI_MACROMOLECULE))
					kill.setInput(Constants.JSON_MACROMOLECULE
							.concat(kdp.getKillInput().replace(Constants.GUI_MACROMOLECULE, "")));
				if (kdp.getKillInput().endsWith(Constants.GUI_LZW))
					kill.setInput(
							Constants.JSON_LZW.concat(kdp.getKillInput().replace(Constants.GUI_LZW, "")));
				if (kdp.getKillInput().endsWith(Constants.GUI_BUILDINGBLOCK))
					kill.setInput(Constants.JSON_BUILDINGBLOCK
							.concat(kdp.getKillInput().replace(Constants.GUI_BUILDINGBLOCK, "")));

				// OnInnner
				if (kdp.getKillOnInnerWith().equals(Constants.GUI_NONE))
					kill.setOnInnerWith("");
				else
					kill.setOnInnerWith(kdp.getKillOnInnerWith());

				// OnRebound
				kill.setOnReboundWith(kdp.getKillOnReboundWith());
				if (kdp.getKillOnReboundWith().equals(Constants.GUI_NONE))
					kill.setOnReboundWith("");
				if (kdp.getKillOnReboundWith().endsWith(Constants.GUI_MACROMOLECULE))
					kill.setOnReboundWith(Constants.JSON_MACROMOLECULE
							.concat(kdp.getKillOnReboundWith().replace(Constants.GUI_MACROMOLECULE, "")));
				if (kdp.getKillOnReboundWith().endsWith(Constants.GUI_LZW))
					kill.setOnReboundWith(Constants.JSON_LZW
							.concat(kdp.getKillOnReboundWith().replace(Constants.GUI_LZW, "")));
				if (kdp.getKillOnReboundWith().endsWith(Constants.GUI_BUILDINGBLOCK))
					kill.setOnReboundWith(Constants.JSON_BUILDINGBLOCK
							.concat(kdp.getKillOnReboundWith().replace(Constants.GUI_BUILDINGBLOCK, "")));
				if (kdp.getKillOnReboundWith().endsWith(Constants.GUI_RIBOSOME))
					kill.setOnReboundWith(Constants.JSON_RIBOSOME
							.concat(kdp.getKillOnReboundWith().replace(Constants.GUI_RIBOSOME, "")));
				toRet[i] = kill;
			}
		}

		return toRet;
	}

	@Override
	public boolean verifyAllData() {
		boolean toRet = true;
		KillDataPanel kdp;
		WebScrollPane wsp;
		int tabCount = tabbedData.getTabCount();

		for (int i = 0; i < tabCount; i++) {
			try {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				if (wsp != null) {
					kdp = (KillDataPanel) wsp.getViewport().getView();

					toRet &= kdp.verifyAllData();

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
		JsonKill[] kills = jsonSingulator.getEvents().getKill();
		KillDataPanel kdp;

		for (JsonKill kill : kills) {
			// Añadir nueva tab para cargar los datos
			kdp = new KillDataPanel(getKillPanel());
			addTab(kdp);

			// Input
			if (kill.getInput().startsWith(Constants.JSON_MACROMOLECULE))
				kdp.setKillInput(kill.getInput().replaceFirst(Constants.JSON_MACROMOLECULE, "")
						.concat(Constants.GUI_MACROMOLECULE));
			if (kill.getInput().startsWith(Constants.JSON_LZW))
				kdp.setKillInput(
						kill.getInput().replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
			if (kill.getInput().startsWith(Constants.JSON_BUILDINGBLOCK))
				kdp.setKillInput(kill.getInput().replaceFirst(Constants.JSON_BUILDINGBLOCK, "")
						.concat(Constants.GUI_BUILDINGBLOCK));

			// OnInner
			if (kill.getOnInnerWith().equals(""))
				kdp.setKillOnInnerWith(Constants.GUI_NONE);
			else
				kdp.setKillOnInnerWith(kill.getOnInnerWith());

			// OnRebound
			kdp.setKillOnReboundWith(kill.getOnReboundWith());
			if (kill.getOnReboundWith().equals(""))
				kdp.setKillOnReboundWith(Constants.GUI_NONE);
			if (kill.getOnReboundWith().startsWith(Constants.JSON_MACROMOLECULE))
				kdp.setKillOnReboundWith(kill.getOnReboundWith().replaceFirst(Constants.JSON_MACROMOLECULE, "")
						.concat(Constants.GUI_MACROMOLECULE));
			if (kill.getOnReboundWith().startsWith(Constants.JSON_LZW))
				kdp.setKillOnReboundWith(kill.getOnReboundWith().replaceFirst(Constants.JSON_LZW, "")
						.concat(Constants.GUI_LZW));
			if (kill.getOnReboundWith().startsWith(Constants.JSON_BUILDINGBLOCK))
				kdp.setKillOnReboundWith(kill.getOnReboundWith().replaceFirst(Constants.JSON_BUILDINGBLOCK, "")
						.concat(Constants.GUI_BUILDINGBLOCK));
			if (kill.getOnReboundWith().startsWith(Constants.JSON_RIBOSOME))
				kdp.setKillOnReboundWith(kill.getOnReboundWith().replaceFirst(Constants.JSON_RIBOSOME, "")
						.concat(Constants.GUI_RIBOSOME));
		}
	}

	@Override
	public void updateComponentsData(JsonSingulator jsonSingulator) {
		WebScrollPane wsp;
		KillDataPanel kdp;

		for (int i = 0; i < tabbedData.getTabCount() - 1; i++) {
			wsp = (WebScrollPane) tabbedData.getComponentAt(i);
			kdp = (KillDataPanel) wsp.getViewport().getView();

			kdp.setCbInputValues();
			kdp.setCbOnInnerWithValues();
			kdp.setCbOnReboundWithValues();
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
		return Constants.KILL_TITLE;
	}
}
