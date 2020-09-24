package es.uvigo.ei.sing.singulator.gui.event;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.custom.tabbedpanes.ButtonTabComponent;
import es.uvigo.ei.sing.singulator.gui.custom.tabbedpanes.CustomTabbedPane;
import es.uvigo.ei.sing.singulator.gui.custom.validators.TabbedPaneValidator;
import es.uvigo.ei.sing.singulator.gui.interfaces.DataPanel;
import es.uvigo.ei.sing.singulator.model.JsonReaction;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public class ReactionPanel extends WebPanel implements DataPanel {

	private static final long serialVersionUID = 1L;

	private CustomTabbedPane tabbedData;

	protected WebPanel panelLayers;

	private int tabID;

	protected JsonSingulator jsonSingulator;

	public ReactionPanel(JsonSingulator jsonSingulator) {
		this.tabID = 0;
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
					addTab(new ReactionDataPanel(getReactionPanel()));
				}
			});

			add(tabbedData, BorderLayout.CENTER);
		}
	}

	public ReactionPanel getReactionPanel() {
		return this;
	}

	public void addTab(ReactionDataPanel rdp) {
		String name = Constants.TABBED_TAB_NAME + tabID;

		WebScrollPane scrollDataReaction = new WebScrollPane(rdp);
		scrollDataReaction.getVerticalScrollBar().setUnitIncrement(16);
		scrollDataReaction.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDataReaction.setBorder(null);

		ButtonTabComponent btc = new ButtonTabComponent(name, tabbedData);

		tabbedData.insertTab(name, null, scrollDataReaction, null, tabbedData.getTabCount() - 1);
		tabbedData.putMapIdTab(name, btc);
		tabbedData.setTabComponentAt(tabbedData.getTabCount() - 2, btc);

		tabbedData.setSelectedIndex(tabbedData.getTabCount() - 2);

		tabID++;
	}

	public void removeLayer(Component comp) {
		panelLayers.remove(comp);
		repaint();
		revalidate();
	}

	public void changeTabName(String name) {
		tabbedData.refreshMapLayerName(name);
	}

	public String getCurrentTabName() {
		return tabbedData.getCurrentTabName();
	}

	@Override
	public Object[] getAllData() {
		int numberOfTabs = tabbedData.getTabCount();
		Object[] toRet = null;

		if (numberOfTabs > 1) {
			toRet = new Object[numberOfTabs - 1];

			ReactionDataPanel rdp;
			JsonReaction reaction;
			WebScrollPane wsp;
			// Recorrer todas las tabs
			for (int i = 0; i < numberOfTabs - 1; i++) {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				rdp = (ReactionDataPanel) wsp.getViewport().getView();

				reaction = new JsonReaction();
				final List<String> inputs = new ArrayList<>();
				for (String input : rdp.getReactionInputs()) {
					inputs.add(Constants.JSON_MACROMOLECULE
							.concat(input.replace(Constants.GUI_MACROMOLECULE, "")));
				}
				reaction.setOnCollision(inputs.toArray(new String[inputs.size()]));

				final List<String> outputs = new ArrayList<>();
				for (String output : rdp.getReactionOutputs()) {
					outputs.add(Constants.JSON_MACROMOLECULE
							.concat(output.replace(Constants.GUI_MACROMOLECULE, "")));
				}
				reaction.setOutput(outputs.toArray(new String[outputs.size()]));

				reaction.setKm(rdp.getReactionKM());
				reaction.setKcat(rdp.getReactionKCAT());

				toRet[i] = reaction;
			}
		}

		return toRet;
	}

	@Override
	public boolean verifyAllData() {
		boolean toRet = true;
		ReactionDataPanel rdp;
		WebScrollPane wsp;
		int tabCount = tabbedData.getTabCount();

		for (int i = 0; i < tabCount; i++) {
			try {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				if (wsp != null) {
					rdp = (ReactionDataPanel) wsp.getViewport().getView();

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

		return toRet;
	}

	@Override
	public void loadData() {
		JsonReaction[] reactions = jsonSingulator.getEvents().getReaction();
		ReactionDataPanel rdp;

		for (JsonReaction reaction : reactions) {
			// Añadir nueva tab para cargar los datos
			rdp = new ReactionDataPanel(getReactionPanel());
			addTab(rdp);

			rdp.setReactionInput(reaction.getOnCollision()[0].replaceFirst(Constants.JSON_MACROMOLECULE, "")
					.concat(Constants.GUI_MACROMOLECULE));
			rdp.setReactionInput2(reaction.getOnCollision()[1].replaceFirst(Constants.JSON_MACROMOLECULE, "")
					.concat(Constants.GUI_MACROMOLECULE));
			final List<String> outputs = new ArrayList<>();
			for (String output : reaction.getOutput()) {
				outputs.add(output.replaceFirst(Constants.JSON_MACROMOLECULE, "")
						.concat(Constants.GUI_MACROMOLECULE));
			}
			rdp.setReactionOutputs(outputs.toArray(new String[outputs.size()]));
			rdp.setReactionKM(reaction.getKm());
			rdp.setReactionKCAT(reaction.getKcat());
		}
	}

	@Override
	public void updateComponentsData(JsonSingulator jsonSingulator) {
		WebScrollPane wsp;
		ReactionDataPanel rdp;

		for (int i = 0; i < tabbedData.getTabCount() - 1; i++) {
			wsp = (WebScrollPane) tabbedData.getComponentAt(i);
			rdp = (ReactionDataPanel) wsp.getViewport().getView();

			rdp.setCbInputValues();
			rdp.setCbInput2Values();
			rdp.setListOutputValues();
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
		return Constants.REACTION_TITLE;
	}
}
