package es.uvigo.ei.sing.singulator.gui.event;

import java.awt.BorderLayout;
import java.awt.Component;
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
import es.uvigo.ei.sing.singulator.model.JsonSingulator;
import es.uvigo.ei.sing.singulator.model.JsonTransform;

public class TransformPanel extends WebPanel implements DataPanel {

	private static final long serialVersionUID = 1L;

	private CustomTabbedPane tabbedData;

	protected WebPanel panelLayers;

	private int index;

	protected JsonSingulator jsonSingulator;

	public TransformPanel(JsonSingulator jsonSingulator) {
		this.index = 0;
		this.jsonSingulator = jsonSingulator;

		init();
	}

	public void init() {
		{
			setLayout(new BorderLayout(0, 0));
			setPaintFocus(true);

			setMargin(7);
		}
		{
			tabbedData = new CustomTabbedPane();
			tabbedData.setTabPlacement(SwingConstants.TOP);

			tabbedData.setActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					addTab(new TransformDataPanel(getTransformPanel()));
				}
			});

			add(tabbedData, BorderLayout.CENTER);
		}
	}

	public TransformPanel getTransformPanel() {
		return this;
	}

	public void addTab(TransformDataPanel tdp) {
		String name = Constants.TABBED_TAB_NAME + index;

		WebScrollPane scrollDataTransform = new WebScrollPane(tdp);
		scrollDataTransform.getVerticalScrollBar().setUnitIncrement(16);
		scrollDataTransform.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDataTransform.setBorder(null);

		ButtonTabComponent btc = new ButtonTabComponent(name, tabbedData);

		tabbedData.insertTab(name, null, scrollDataTransform, null, tabbedData.getTabCount() - 1);
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

			TransformDataPanel tdp;
			JsonTransform transform;
			WebScrollPane wsp;
			// Recorrer todas las tabs
			for (int i = 0; i < numberOfTabs - 1; i++) {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				tdp = (TransformDataPanel) wsp.getViewport().getView();

				transform = new JsonTransform();

				// From
				if (tdp.getTransformFrom().endsWith(Constants.GUI_MACROMOLECULE))
					transform.setFrom(Constants.JSON_MACROMOLECULE
							.concat(tdp.getTransformFrom().replace(Constants.GUI_MACROMOLECULE, "")));
				if (tdp.getTransformFrom().endsWith(Constants.GUI_LZW))
					transform.setFrom(Constants.JSON_LZW
							.concat(tdp.getTransformFrom().replace(Constants.GUI_LZW, "")));
				if (tdp.getTransformFrom().endsWith(Constants.GUI_BUILDINGBLOCK))
					transform.setFrom(Constants.JSON_BUILDINGBLOCK
							.concat(tdp.getTransformFrom().replace(Constants.GUI_BUILDINGBLOCK, "")));

				// To
				if (tdp.getTransformTo().endsWith(Constants.GUI_MACROMOLECULE))
					transform.setTo(Constants.JSON_MACROMOLECULE
							.concat(tdp.getTransformTo().replace(Constants.GUI_MACROMOLECULE, "")));
				if (tdp.getTransformTo().endsWith(Constants.GUI_LZW))
					transform.setTo(
							Constants.JSON_LZW.concat(tdp.getTransformTo().replace(Constants.GUI_LZW, "")));
				if (tdp.getTransformTo().endsWith(Constants.GUI_BUILDINGBLOCK))
					transform.setTo(Constants.JSON_BUILDINGBLOCK
							.concat(tdp.getTransformTo().replace(Constants.GUI_BUILDINGBLOCK, "")));

				// On Inner
				if (tdp.getTransformOnInnerWith().equals(Constants.GUI_NONE))
					transform.setOnInnerWith("");
				else
					transform.setOnInnerWith(tdp.getTransformOnInnerWith());

				// On Rebound
				transform.setOnReboundWith(tdp.getTransformOnReboundWith());
				if (tdp.getTransformOnReboundWith().equals(Constants.GUI_NONE))
					transform.setOnReboundWith("");
				if (tdp.getTransformOnReboundWith().endsWith(Constants.GUI_MACROMOLECULE))
					transform.setOnReboundWith(Constants.JSON_MACROMOLECULE
							.concat(tdp.getTransformOnReboundWith().replace(Constants.GUI_MACROMOLECULE, "")));
				if (tdp.getTransformOnReboundWith().endsWith(Constants.GUI_LZW))
					transform.setOnReboundWith(Constants.JSON_LZW
							.concat(tdp.getTransformOnReboundWith().replace(Constants.GUI_LZW, "")));
				if (tdp.getTransformOnReboundWith().endsWith(Constants.GUI_BUILDINGBLOCK))
					transform.setOnReboundWith(Constants.JSON_BUILDINGBLOCK
							.concat(tdp.getTransformOnReboundWith().replace(Constants.GUI_BUILDINGBLOCK, "")));
				if (tdp.getTransformOnReboundWith().endsWith(Constants.GUI_RIBOSOME))
					transform.setOnReboundWith(Constants.JSON_RIBOSOME
							.concat(tdp.getTransformOnReboundWith().replace(Constants.GUI_RIBOSOME, "")));

				toRet[i] = transform;
			}
		}

		return toRet;
	}

	@Override
	public boolean verifyAllData() {
		boolean toRet = true;
		TransformDataPanel tdp;
		WebScrollPane wsp;
		int tabCount = tabbedData.getTabCount();

		for (int i = 0; i < tabCount; i++) {
			try {
				wsp = (WebScrollPane) tabbedData.getComponentAt(i);
				if (wsp != null) {
					tdp = (TransformDataPanel) wsp.getViewport().getView();

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

		return toRet;
	}

	@Override
	public void loadData() {
		JsonTransform[] transforms = jsonSingulator.getEvents().getTransform();
		TransformDataPanel tdp;

		for (JsonTransform transform : transforms) {
			// Añadir nueva tab para cargar los datos
			tdp = new TransformDataPanel(getTransformPanel());
			addTab(tdp);

			// From
			if (transform.getFrom().startsWith(Constants.JSON_MACROMOLECULE))
				tdp.setTransformFrom(transform.getFrom().replaceFirst(Constants.JSON_MACROMOLECULE, "")
						.concat(Constants.GUI_MACROMOLECULE));
			if (transform.getFrom().startsWith(Constants.JSON_LZW))
				tdp.setTransformFrom(
						transform.getFrom().replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
			if (transform.getFrom().startsWith(Constants.JSON_BUILDINGBLOCK))
				tdp.setTransformFrom(transform.getFrom().replaceFirst(Constants.JSON_BUILDINGBLOCK, "")
						.concat(Constants.GUI_BUILDINGBLOCK));

			// To
			if (transform.getTo().startsWith(Constants.JSON_MACROMOLECULE))
				tdp.setTransformTo(transform.getTo().replaceFirst(Constants.JSON_MACROMOLECULE, "")
						.concat(Constants.GUI_MACROMOLECULE));
			if (transform.getTo().startsWith(Constants.JSON_LZW))
				tdp.setTransformTo(
						transform.getTo().replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
			if (transform.getTo().startsWith(Constants.JSON_BUILDINGBLOCK))
				tdp.setTransformTo(transform.getTo().replaceFirst(Constants.JSON_BUILDINGBLOCK, "")
						.concat(Constants.GUI_BUILDINGBLOCK));

			// OnInner
			if (transform.getOnInnerWith().equals(""))
				tdp.setTransformOnInnerWith(Constants.GUI_NONE);
			else
				tdp.setTransformOnInnerWith(transform.getOnInnerWith());

			// OnRebound
			tdp.setTransformOnReboundWith(transform.getOnReboundWith());
			if (transform.getOnReboundWith().equals(""))
				tdp.setTransformOnReboundWith(Constants.GUI_NONE);
			if (transform.getOnReboundWith().startsWith(Constants.JSON_MACROMOLECULE))
				tdp.setTransformOnReboundWith(
						transform.getOnReboundWith().replaceFirst(Constants.JSON_MACROMOLECULE, "")
								.concat(Constants.GUI_MACROMOLECULE));
			if (transform.getOnReboundWith().startsWith(Constants.JSON_LZW))
				tdp.setTransformOnReboundWith(transform.getOnReboundWith().replaceFirst(Constants.JSON_LZW, "")
						.concat(Constants.GUI_LZW));
			if (transform.getOnReboundWith().startsWith(Constants.JSON_BUILDINGBLOCK))
				tdp.setTransformOnReboundWith(
						transform.getOnReboundWith().replaceFirst(Constants.JSON_BUILDINGBLOCK, "")
								.concat(Constants.GUI_BUILDINGBLOCK));
			if (transform.getOnReboundWith().startsWith(Constants.JSON_RIBOSOME))
				tdp.setTransformOnReboundWith(transform.getOnReboundWith()
						.replaceFirst(Constants.JSON_RIBOSOME, "").concat(Constants.GUI_RIBOSOME));
		}
	}

	@Override
	public void updateComponentsData(JsonSingulator jsonSingulator) {
		WebScrollPane wsp;
		TransformDataPanel tdp;

		for (int i = 0; i < tabbedData.getTabCount() - 1; i++) {
			wsp = (WebScrollPane) tabbedData.getComponentAt(i);
			tdp = (TransformDataPanel) wsp.getViewport().getView();

			tdp.setCbInputValues();
			tdp.setCbOutputValues();
			tdp.setCbOnInnerWithValues();
			tdp.setCbOnReboundWithValues();
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
		return Constants.TRANSFORM_TITLE;
	}
}
