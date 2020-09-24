package es.uvigo.ei.sing.singulator.gui.feeder;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.SwingConstants;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.utils.swing.UnselectableButtonGroup;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.labels.WebOverlayLabel;
import es.uvigo.ei.sing.singulator.gui.custom.spinners.CustomAlignedSpinner;
import es.uvigo.ei.sing.singulator.gui.custom.spinners.CustomSpinnerModel;
import es.uvigo.ei.sing.singulator.gui.custom.validators.EmptyValidator;
import es.uvigo.ei.sing.singulator.model.JsonBuildingblock;
import es.uvigo.ei.sing.singulator.model.JsonCell;
import es.uvigo.ei.sing.singulator.model.JsonLzw;
import es.uvigo.ei.sing.singulator.model.JsonMacromolecule;
import es.uvigo.ei.sing.singulator.model.JsonMolecule;

public class FeedersDataPanel extends WebPanel {

	private static final long serialVersionUID = 1L;

	// private WebOverlayLabel overlayOnInnerCell;
	// private WebOverlayLabel overlayOnInnerLayer;
	private WebOverlayLabel overlayOnEveryStep;
	// private WebOverlayLabel overlayReaction;

	private WebLabel lblCreate;
	private WebLabel lblType;
	private WebLabel lblLocation;
	private WebLabel lblProdNumber;
	private WebLabel lblMaxConcentration;

	private WebComboBox cbCreate;
	private WebComboBox cbLocation;
	// private WebComboBox cbOnInnerCell;
	// private WebComboBox cbOnInnerLayer;

	private WebRadioButton continuous;
	private WebRadioButton nonContinuous;

	private CustomAlignedSpinner spinnerProdNumber;
	private CustomAlignedSpinner spinnerMaxConcentration;
	private CustomAlignedSpinner spinnerEveryStep;

	// private WebCheckBox checkReaction;

	private double maxLabelLenght = -1.0;

	private FeedersPanel parent;

	public FeedersDataPanel(FeedersPanel parent) {
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
			lblCreate = new WebLabel(Constants.FEEDER_INPUT);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblCreate.getPreferredSize().getWidth());

			lblType = new WebLabel(Constants.FEEDER_TYPE);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblType.getPreferredSize().getWidth());

			lblLocation = new WebLabel(Constants.FEEDER_LOCATION);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblLocation.getPreferredSize().getWidth());

			overlayOnEveryStep = new WebOverlayLabel(Constants.FEEDER_EVERY_STEP, Constants.FEEDER_TIP_EVERY_STEP);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					overlayOnEveryStep.getPreferredSize().getWidth());

			lblProdNumber = new WebLabel(Constants.FEEDER_PROD_NUMBER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblProdNumber.getPreferredSize().getWidth());

			lblMaxConcentration = new WebLabel(Constants.FEEDER_MAXCONC);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					lblMaxConcentration.getPreferredSize().getWidth());

			// overlayOnInnerCell = new
			// WebOverlayLabel(Constants.FEEDER_INNER_CELL,
			// Constants.FEEDER_TIP_INNER_CELL);
			// maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
			// overlayOnInnerCell.getPreferredSize().getWidth());

			// overlayOnInnerLayer = new
			// WebOverlayLabel(Constants.FEEDER_INNER_LAYER,
			// Constants.FEEDER_TIP_INNER_LAYER);
			// maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
			// overlayOnInnerLayer.getPreferredSize().getWidth());

			// overlayReaction = new WebOverlayLabel(Constants.FEEDER_REACTION,
			// Constants.FEEDER_TIP_REACTION);
			// maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
			// overlayReaction.getPreferredSize().getWidth());
		}
		{
			{
				cbCreate = new WebComboBox();
				cbCreate.setInputVerifier(new EmptyValidator(cbCreate));
				cbCreate.putClientProperty(GroupPanel.FILL_CELL, true);
				setCbCreateValues();
				cbCreate.setSelectedIndex(-1);
				cbCreate.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							parent.changeTabName(cbCreate.getSelectedItem().toString());
						}
					}
				});
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblCreate.getPreferredSize().getWidth())),
						lblCreate, cbCreate));
			}
			{
				continuous = new WebRadioButton(Constants.FEEDER_TYPE_CONTINUOUS);
				continuous.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (spinnerMaxConcentration != null) {
							if (e.getStateChange() == ItemEvent.SELECTED)
								spinnerMaxConcentration.setEnabled(false);
							else if (e.getStateChange() == ItemEvent.DESELECTED)
								spinnerMaxConcentration.setEnabled(true);
						}
					}
				});
				continuous.setSelected(true);

				nonContinuous = new WebRadioButton(Constants.FEEDER_TYPE_NONCONTINUOUS);

				// Grouping buttons with custom button group that allows
				// deselection
				UnselectableButtonGroup ubg = UnselectableButtonGroup.group(continuous, nonContinuous);
				ubg.setUnselectable(false);
				GroupPanel p1 = new GroupPanel(4, false, continuous, nonContinuous);
				p1.putClientProperty(GroupPanel.FILL_CELL, true);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblType.getPreferredSize().getWidth())),
						lblType, p1));
			}
			{
				spinnerEveryStep = new CustomAlignedSpinner();
				spinnerEveryStep.putClientProperty(GroupPanel.FILL_CELL, true);
				spinnerEveryStep.setModel(new CustomSpinnerModel(1, 1, 1));
				spinnerEveryStep.setTextAligment(SwingConstants.LEFT);
				add(new GroupPanel(
						(50 + Functions.calculateLabelDifference(maxLabelLenght,
								overlayOnEveryStep.getPreferredSize().getWidth())),
						overlayOnEveryStep, spinnerEveryStep));
			}
			{
				spinnerProdNumber = new CustomAlignedSpinner();
				spinnerProdNumber.putClientProperty(GroupPanel.FILL_CELL, true);
				spinnerProdNumber.setModel(new CustomSpinnerModel(1, 0, 10000, 1));
				spinnerProdNumber.setTextAligment(SwingConstants.LEFT);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						lblProdNumber.getPreferredSize().getWidth())), lblProdNumber, spinnerProdNumber));
			}
			{
				cbLocation = new WebComboBox(Constants.FEEDER_LOCATION_ARRAY);
				cbLocation.setInputVerifier(new EmptyValidator(cbLocation));
				cbLocation.putClientProperty(GroupPanel.FILL_CELL, true);
				setCbLocation();
				cbLocation.setSelectedIndex(-1);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						lblLocation.getPreferredSize().getWidth())), lblLocation, cbLocation));
			}
			{
				spinnerMaxConcentration = new CustomAlignedSpinner();
				spinnerMaxConcentration.setEnabled(false);
				spinnerMaxConcentration.putClientProperty(GroupPanel.FILL_CELL, true);
				spinnerMaxConcentration.setModel(new CustomSpinnerModel(0, 0, 10000, 10));
				spinnerMaxConcentration.setTextAligment(SwingConstants.LEFT);
				add(new GroupPanel(
						(50 + Functions.calculateLabelDifference(maxLabelLenght,
								lblMaxConcentration.getPreferredSize().getWidth())),
						lblMaxConcentration, spinnerMaxConcentration));
			}
			// {
			// cbOnInnerCell = new WebComboBox();
			// cbOnInnerLayer = new WebComboBox();
			// }
			// {
			// cbOnInnerCell.setInputVerifier(new
			// EmptyValidator(cbOnInnerCell));
			// cbOnInnerCell.putClientProperty(GroupPanel.FILL_CELL, true);
			// setCbOnInnerCellValues();
			// cbOnInnerCell.setSelectedIndex(-1);
			// cbOnInnerCell.addItemListener(new ItemListener() {
			// @Override
			// public void itemStateChanged(ItemEvent e) {
			// if (e.getStateChange() == ItemEvent.SELECTED && cbOnInnerLayer !=
			// null) {
			// Object selectedValue = cbOnInnerLayer.getSelectedItem();
			//
			// cbOnInnerLayer.removeAllItems();
			//
			// String selected = e.getItem().toString();
			//
			// // Buscar célula seleccionada
			// JsonCell[] cells = parent.jsonSingulator.getCells();
			// JsonCell cell = null;
			// if (cells != null) {
			// for (JsonCell c : cells) {
			// if (c.getCellName().equals(selected))
			// cell = c;
			// }
			//
			// if (cell != null) {
			// cbOnInnerLayer.addItem(cell.getLayerName());
			//
			// JsonLayer[] layers = cell.getLayers();
			// for (JsonLayer layer : layers) {
			// cbOnInnerLayer.addItem(layer.getName());
			// }
			// }
			// }
			//
			// cbOnInnerLayer.setSelectedItem(selectedValue);
			// }
			// }
			// });
			// add(new GroupPanel((50 +
			// Functions.calculateLabelDifference(maxLabelLenght,
			// overlayOnInnerCell.getPreferredSize().getWidth())),
			// overlayOnInnerCell, cbOnInnerCell));
			// }
			// {
			// cbOnInnerLayer.setInputVerifier(new
			// EmptyValidator(cbOnInnerLayer));
			// cbOnInnerLayer.setSelectedIndex(-1);
			// cbOnInnerLayer.putClientProperty(GroupPanel.FILL_CELL, true);
			// add(new GroupPanel(
			// (50 + Functions.calculateLabelDifference(maxLabelLenght,
			// overlayOnInnerLayer.getPreferredSize().getWidth())),
			// overlayOnInnerLayer, cbOnInnerLayer));
			// }
			// {
			// checkReaction = new WebCheckBox();
			// checkReaction.putClientProperty(GroupPanel.FILL_CELL, true);
			// add(new GroupPanel((50 +
			// Functions.calculateLabelDifference(maxLabelLenght,
			// overlayReaction.getPreferredSize().getWidth())), overlayReaction,
			// checkReaction));
			// }
		}
	}

	public String getFeederCreate() {
		return cbCreate.getSelectedItem().toString();
	}

	public void setFeederCreate(String create) {
		cbCreate.setSelectedItem(create);
	}

	public String getFeederType() {
		String toRet;
		if (continuous.isSelected()) {
			toRet = continuous.getText();
		} else {
			toRet = nonContinuous.getText();
		}
		return toRet;
	}

	public void setFeederType(String type) {
		if (type.equals(Constants.FEEDER_TYPE_CONTINUOUS)) {
			continuous.setSelected(true);
		} else if (type.equals(Constants.FEEDER_TYPE_NONCONTINUOUS)) {
			nonContinuous.setSelected(true);
		}
	}

	public String getFeederLocation() {
		return cbLocation.getSelectedItem().toString();
	}

	public void setFeederLocation(String location) {
		cbLocation.setSelectedItem(location);
	}

	public int getFeederEveryStep() {
		return (int) spinnerEveryStep.getValue();
	}

	public void setFeederEveryStep(int everyStep) {
		spinnerEveryStep.setValue(everyStep);
	}

	public int getFeederProductNumber() {
		return (int) spinnerProdNumber.getValue();
	}

	public void setFeederProductNumber(int prodNumber) {
		spinnerProdNumber.setValue(prodNumber);
	}

	public int getFeederMaxConcentration() {
		return (int) spinnerMaxConcentration.getValue();
	}

	public void setFeederMaxConcentration(int maxConcentration) {
		spinnerMaxConcentration.setValue(maxConcentration);
	}

	@SuppressWarnings("unchecked")
	public void setCbCreateValues() {
		Object selectedValue = cbCreate.getSelectedItem();

		cbCreate.removeAllItems();

		JsonMolecule jsonAgents = parent.jsonSingulator.getAgents().getMolecules();
		if (jsonAgents != null) {
			JsonMacromolecule[] molecules = jsonAgents.getMacromolecules();
			if (molecules != null && molecules.length > 0) {
				for (JsonMacromolecule molecule : molecules) {
					cbCreate.addItem(molecule.getName().replaceFirst(Constants.JSON_MACROMOLECULE, "").concat(Constants.GUI_MACROMOLECULE));
				}
			}
			JsonBuildingblock[] buildings = jsonAgents.getBuildingBlocks();
			if (buildings != null && buildings.length > 0) {
				for (JsonBuildingblock building : buildings) {
					cbCreate.addItem(building.getName().replaceFirst(Constants.JSON_BUILDINGBLOCK, "").concat(Constants.GUI_BUILDINGBLOCK));
				}
			}
			JsonLzw[] lzws = jsonAgents.getLzw();
			if (lzws != null && lzws.length > 0) {
				for (JsonLzw lzw : lzws) {
					cbCreate.addItem(lzw.getName().replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
				}
			}
		}

		cbCreate.setSelectedItem(selectedValue);
	}

	@SuppressWarnings("unchecked")
	public void setCbLocation() {
		Object selectedValue = cbLocation.getSelectedItem();

		cbLocation.removeAllItems();

		// Insertar cbCellLocalization
		JsonCell[] cells = parent.jsonSingulator.getCells();
		if (cells != null) {
			for (JsonCell cell : cells) {
				cbLocation.addItem(cell.getCellName().replaceFirst(Constants.JSON_CELL, ""));
			}
		}

		cbLocation.addItem(Constants.EXTRACELLULAR_NAME);

		cbLocation.setSelectedItem(selectedValue);
	}

	public boolean verifyAllData() {
		boolean toRet = true;

		toRet &= cbCreate.getInputVerifier().verify(null);
		toRet &= cbLocation.getInputVerifier().verify(null);

		return toRet;
	}

	// public String getFeederOnInner() {
	// String toRet = "";
	//
	// toRet += cbOnInnerCell.getSelectedItem().toString();
	// toRet += "," + cbOnInnerLayer.getSelectedItem().toString();
	//
	// return toRet;
	// }
	//
	// public void setFeederOnIneer(String onInner) {
	// String[] columns = onInner.split(",");
	//
	// cbOnInnerCell.setSelectedItem(columns[0]);
	// cbOnInnerLayer.setSelectedItem(columns[1]);
	// }
	// public boolean getFeederOnReaction() {
	// return checkReaction.isSelected();
	// }
	//
	// public void setFeederOnReaction(boolean onReaction) {
	// checkReaction.setSelected(onReaction);
	// }
	// public void setCbOnInnerCellValues() {
	// Object selectedValue = cbOnInnerCell.getSelectedItem();
	//
	// cbOnInnerCell.removeAllItems();
	//
	// // Insertar cbCellLocalization
	// JsonCell[] cells = parent.jsonSingulator.getCells();
	// if (cells != null) {
	// for (JsonCell cell : cells) {
	// cbOnInnerCell.addItem(cell.getCellName());
	// }
	// } else {
	// cbOnInnerLayer.removeAllItems();
	// }
	//
	// cbOnInnerCell.setSelectedItem(selectedValue);
	// }
}
