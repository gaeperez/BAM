package es.uvigo.ei.sing.singulator.gui.event;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.labels.WebOverlayLabel;
import es.uvigo.ei.sing.singulator.gui.custom.validators.EmptyValidator;
import es.uvigo.ei.sing.singulator.model.JsonBuildingblock;
import es.uvigo.ei.sing.singulator.model.JsonLzw;
import es.uvigo.ei.sing.singulator.model.JsonMacromolecule;
import es.uvigo.ei.sing.singulator.model.JsonMolecule;
import es.uvigo.ei.sing.singulator.model.JsonRibosome;

public class TransformDataPanel extends WebPanel {

	private static final long serialVersionUID = 1L;

	private WebOverlayLabel overlayOnInnerWith;
	private WebOverlayLabel overlayOnReboundWith;

	private WebLabel lblInput;
	private WebLabel lblOutput;

	private WebComboBox cbInput;
	private WebComboBox cbOnInnerWith;
	private WebComboBox cbOnReboundWith;
	private WebComboBox cbOutput;

	private double maxLabelLenght = -1.0;

	final private TransformPanel parent;

	public TransformDataPanel(TransformPanel parent) {
		this.parent = parent;

		init();
	}

	private void init() {
		{
			setLayout(new VerticalFlowLayout(0, 10));
			setMargin(7);
			setBackground(Color.decode(Constants.COLOR_WHITE));
		}
		{
			{
				lblInput = new WebLabel(Constants.TRANSFORM_INPUT);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblInput.getPreferredSize().getWidth());

				lblOutput = new WebLabel(Constants.TRANSFORM_OUTPUT);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblOutput.getPreferredSize().getWidth());

				overlayOnInnerWith = new WebOverlayLabel(Constants.TRANSFORM_ON_INNER_WITH,
						Constants.TRANSFORM_TIP_ON_INNER_TRIGGER);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
						overlayOnInnerWith.getPreferredSize().getWidth());

				overlayOnReboundWith = new WebOverlayLabel(Constants.TRANSFORM_ON_REBOUND_WITH,
						Constants.TRANSFORM_TIP_ON_REBOUND_TRIGGER);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
						overlayOnReboundWith.getPreferredSize().getWidth());
			}
			{
				cbInput = new WebComboBox();
				cbOutput = new WebComboBox();
				cbOnInnerWith = new WebComboBox();
				cbOnReboundWith = new WebComboBox();
			}
			{
				cbInput.setInputVerifier(new EmptyValidator(cbInput));
				setCbInputValues();
				cbInput.setSelectedIndex(-1);
				cbInput.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							parent.changeTabName(cbInput.getSelectedItem().toString());
						}
					}
				});
				cbInput.putClientProperty(GroupPanel.FILL_CELL, true);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblInput.getPreferredSize().getWidth())),
						lblInput, cbInput));

			}
			{
				cbOutput.setInputVerifier(new EmptyValidator(cbOutput));
				setCbOutputValues();
				cbOutput.setSelectedIndex(-1);
				cbOutput.putClientProperty(GroupPanel.FILL_CELL, true);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblOutput.getPreferredSize().getWidth())),
						lblOutput, cbOutput));
			}
			{
				cbOnInnerWith.setInputVerifier(new EmptyValidator(cbOnInnerWith));
				setCbOnInnerWithValues();
				cbOnInnerWith.setSelectedIndex(-1);
				cbOnInnerWith.putClientProperty(GroupPanel.FILL_CELL, true);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						overlayOnInnerWith.getPreferredSize().getWidth())), overlayOnInnerWith, cbOnInnerWith));
			}
			{
				cbOnReboundWith.setInputVerifier(new EmptyValidator(cbOnReboundWith));
				setCbOnReboundWithValues();
				cbOnReboundWith.setSelectedIndex(-1);
				cbOnReboundWith.putClientProperty(GroupPanel.FILL_CELL, true);
				add(new GroupPanel(
						(50 + Functions.calculateLabelDifference(maxLabelLenght,
								overlayOnReboundWith.getPreferredSize().getWidth())),
						overlayOnReboundWith, cbOnReboundWith));
			}
		}
	}

	public String getTransformFrom() {
		return cbInput.getSelectedItem().toString();
	}

	public void setTransformFrom(String from) {
		cbInput.setSelectedItem(from);
	}

	public String getTransformTo() {
		return cbOutput.getSelectedItem().toString();
	}

	public void setTransformTo(String to) {
		cbOutput.setSelectedItem(to);
	}

	public String getTransformOnInnerWith() throws NullPointerException {
		return cbOnInnerWith.getSelectedItem().toString();
	}

	public void setTransformOnInnerWith(String with) {
		cbOnInnerWith.setSelectedItem(with);
	}

	public String getTransformOnReboundWith() throws NullPointerException {
		return cbOnReboundWith.getSelectedItem().toString();
	}

	public void setTransformOnReboundWith(String with) {
		cbOnReboundWith.setSelectedItem(with);
	}

	@SuppressWarnings("unchecked")
	public void setCbInputValues() {
		Object selectedValue = cbInput.getSelectedItem();

		cbInput.removeAllItems();

		JsonMolecule jsonAgents = parent.jsonSingulator.getAgents().getMolecules();
		if (jsonAgents != null) {
			JsonMacromolecule[] molecules = jsonAgents.getMacromolecules();
			if (molecules != null && molecules.length > 0) {
				for (JsonMacromolecule molecule : molecules) {
					cbInput.addItem(molecule.getName().replaceFirst(Constants.JSON_MACROMOLECULE, "")
							.concat(Constants.GUI_MACROMOLECULE));
				}
			}
			JsonBuildingblock[] buildings = jsonAgents.getBuildingBlocks();
			if (buildings != null && buildings.length > 0) {
				for (JsonBuildingblock building : buildings) {
					cbInput.addItem(building.getName().replaceFirst(Constants.JSON_BUILDINGBLOCK, "")
							.concat(Constants.GUI_BUILDINGBLOCK));
				}
			}
			JsonLzw[] lzws = jsonAgents.getLzw();
			if (lzws != null && lzws.length > 0) {
				for (JsonLzw lzw : lzws) {
					cbInput.addItem(
							lzw.getName().replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
				}
			}
		}

		cbInput.setSelectedItem(selectedValue);
	}

	@SuppressWarnings("unchecked")
	public void setCbOutputValues() {
		Object selectedValue = cbOutput.getSelectedItem();

		cbOutput.removeAllItems();

		JsonMolecule jsonAgents = parent.jsonSingulator.getAgents().getMolecules();
		if (jsonAgents != null) {
			JsonMacromolecule[] molecules = jsonAgents.getMacromolecules();
			if (molecules != null && molecules.length > 0) {
				for (JsonMacromolecule molecule : molecules) {
					cbOutput.addItem(molecule.getName().replaceFirst(Constants.JSON_MACROMOLECULE, "")
							.concat(Constants.GUI_MACROMOLECULE));
				}
			}
			JsonBuildingblock[] buildings = jsonAgents.getBuildingBlocks();
			if (buildings != null && buildings.length > 0) {
				for (JsonBuildingblock building : buildings) {
					cbOutput.addItem(building.getName().replaceFirst(Constants.JSON_BUILDINGBLOCK, "")
							.concat(Constants.GUI_BUILDINGBLOCK));
				}
			}
			JsonLzw[] lzws = jsonAgents.getLzw();
			if (lzws != null && lzws.length > 0) {
				for (JsonLzw lzw : lzws) {
					cbOutput.addItem(
							lzw.getName().replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
				}
			}
		}

		cbOutput.setSelectedItem(selectedValue);
	}

	@SuppressWarnings("unchecked")
	public void setCbOnInnerWithValues() {
		Object selectedValue = cbOnInnerWith.getSelectedItem();

		cbOnInnerWith.removeAllItems();
		// JsonCell[] cells;
		// JsonLayer[] layers;
		// cbOnInnerWith.addItem(Constants.EVENT_GUI_NONE);
		//
		// cells = parent.jsonSingulator.getCells();
		// // FIXME
		// if (cells != null && cells.length > 0) {
		// // for (JsonCell cell : cells) {
		// layers = cells[0].getLayers();
		// cbOnInnerWith.addItem(cells[0].getLayerName());
		//
		// for (JsonLayer layer : layers) {
		// cbOnInnerWith.addItem(layer.getName());
		// }
		// // }
		// }
		// cbOnInnerWith.addItem(Constants.EXTRACELLULAR_NAME);
		// TODO: PROVISIONAL
		cbOnInnerWith.addItem(Constants.GUI_NONE);
		for (String value : Constants.ARRAY_LAYERS) {
			cbOnInnerWith.addItem(value);
		}

		cbOnInnerWith.setSelectedItem(selectedValue);
	}

	@SuppressWarnings("unchecked")
	public void setCbOnReboundWithValues() {
		Object selectedValue = cbOnReboundWith.getSelectedItem();

		cbOnReboundWith.removeAllItems();
		// JsonCell[] cells;
		// JsonLayer[] layers;
		// cbOnReboundWith.addItem(Constants.EVENT_GUI_NONE);
		//
		// cells = parent.jsonSingulator.getCells();
		// // FIXME
		// if (cells != null && cells.length > 0) {
		// // for (JsonCell cell : cells) {
		// layers = cells[0].getLayers();
		// cbOnReboundWith.addItem(cells[0].getLayerName());
		//
		// for (JsonLayer layer : layers) {
		// cbOnReboundWith.addItem(layer.getName());
		// }
		// // }
		// }
		// TODO: PROVISIONAL
		cbOnReboundWith.addItem(Constants.GUI_NONE);
		for (String value : Constants.ARRAY_LAYERS) {
			cbOnReboundWith.addItem(value);
		}

		JsonMolecule jsonAgents = parent.jsonSingulator.getAgents().getMolecules();
		if (jsonAgents != null) {
			JsonMacromolecule[] molecules = jsonAgents.getMacromolecules();
			if (molecules != null && molecules.length > 0) {
				for (JsonMacromolecule molecule : molecules) {
					cbOnReboundWith.addItem(molecule.getName().replaceFirst(Constants.JSON_MACROMOLECULE, "")
							.concat(Constants.GUI_MACROMOLECULE));
				}
			}
			JsonBuildingblock[] buildings = jsonAgents.getBuildingBlocks();
			if (buildings != null && buildings.length > 0) {
				for (JsonBuildingblock building : buildings) {
					cbOnReboundWith.addItem(building.getName().replaceFirst(Constants.JSON_BUILDINGBLOCK, "")
							.concat(Constants.GUI_BUILDINGBLOCK));
				}
			}
			JsonLzw[] lzws = jsonAgents.getLzw();
			if (lzws != null && lzws.length > 0) {
				for (JsonLzw lzw : lzws) {
					cbOnReboundWith.addItem(
							lzw.getName().replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
				}
			}
			JsonRibosome[] ribosomes = parent.jsonSingulator.getAgents().getRibosomes();
			if (ribosomes != null && ribosomes.length > 0) {
				for (JsonRibosome ribosome : ribosomes) {
					cbOnReboundWith.addItem(ribosome.getName().replaceFirst(Constants.JSON_RIBOSOME, "")
							.concat(Constants.GUI_RIBOSOME));
				}
			}
		}
		// cbOnReboundWith.addItem(Constants.EXTRACELLULAR_NAME);

		cbOnReboundWith.setSelectedItem(selectedValue);
	}

	public boolean verifyAllData() {
		return cbInput.getInputVerifier().verify(null) && cbOutput.getInputVerifier().verify(null)
				&& cbOnInnerWith.getInputVerifier().verify(null) && cbOnReboundWith.getInputVerifier().verify(null)
				&& (!(cbOnReboundWith.getSelectedItem().toString().equals(Constants.GUI_NONE)
						&& cbOnInnerWith.getSelectedItem().toString().equals(Constants.GUI_NONE)));
	}
}
