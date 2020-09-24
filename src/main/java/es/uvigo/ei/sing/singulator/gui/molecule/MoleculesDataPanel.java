package es.uvigo.ei.sing.singulator.gui.molecule;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.alee.extended.colorchooser.WebColorChooserField;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.text.WebTextField;
import com.alee.utils.ColorUtils;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.labels.WebOverlayLabel;
import es.uvigo.ei.sing.singulator.gui.custom.spinners.CustomAlignedSpinner;
import es.uvigo.ei.sing.singulator.gui.custom.spinners.CustomSpinnerModel;
import es.uvigo.ei.sing.singulator.gui.custom.validators.DoubleEmptyValidator;
import es.uvigo.ei.sing.singulator.gui.custom.validators.DoubleValidator;
import es.uvigo.ei.sing.singulator.gui.custom.validators.EmptyValidator;
import es.uvigo.ei.sing.singulator.gui.interfaces.MoleculePanel;
import es.uvigo.ei.sing.singulator.model.JsonCell;
import es.uvigo.ei.sing.singulator.model.JsonLayer;

public class MoleculesDataPanel extends WebPanel {

	private static final long serialVersionUID = 1L;

	private WebOverlayLabel overlayColor;
	private WebOverlayLabel overlayMaxLayer;
	private WebOverlayLabel overlayMinLayer;
	private WebOverlayLabel overlayCellLocalization;
	private WebOverlayLabel overlayLayerLocalization;
	private WebOverlayLabel overlayRadInfl;

	private WebLabel lblName;
	private WebLabel lblType;
	private WebLabel lblMW;
	private WebLabel lblRadius;
	private WebLabel lblDRExterior;
	private WebLabel lblDROuterMembrane;
	private WebLabel lblDROuterPeriplasm;
	private WebLabel lblDRPeptido;
	private WebLabel lblDRInnerPeri;
	private WebLabel lblDRInnerMembrane;
	private WebLabel lblDRCytoplasm;
	private WebLabel lblNumber;
	private WebLabel lblRadInflWith;

	private WebTextField tfName;
	private WebTextField tfMW;
	private WebTextField tfRadius;
	private WebTextField tfDRExterior;
	private WebTextField tfDROuterMembrane;
	private WebTextField tfDROuterPeriplasm;
	private WebTextField tfDRPeptidoglycan;
	private WebTextField tfDRInnerPeriplasm;
	private WebTextField tfDRInnerMembrane;
	private WebTextField tfDRCytoplasm;
	// private WebTextField tfRadInflWith;

	private CustomAlignedSpinner spinnerNumber;
	private CustomAlignedSpinner spinnerRadInfl;

	private WebComboBox cbType;
	private WebComboBox cbMaxLayer;
	private WebComboBox cbMinLayer;
	private WebComboBox cbCellLocalization;
	private WebComboBox cbLayerLocalization;

	private WebColorChooserField simpleColorChooser;

	private double maxLabelLenght = -1.0;

	private MoleculePanel parent;

	public MoleculesDataPanel(MoleculePanel parent) {
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
			if (parent instanceof MacromoleculesPanel)
				lblName = new WebLabel(Constants.MACROMOLECULES_NAME);
			else if (parent instanceof BuildingBlocksPanel)
				lblName = new WebLabel(Constants.BUILDINGBLOCKS_NAME);
			else
				lblName = new WebLabel(Constants.LZW_NAME);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblName.getPreferredSize().getWidth());

			lblType = new WebLabel(Constants.MOLECULES_TYPE);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblType.getPreferredSize().getWidth());

			lblMW = new WebLabel(Constants.MOLECULES_MW);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblMW.getPreferredSize().getWidth());

			lblRadius = new WebLabel(Constants.MOLECULES_RADIUS);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblRadius.getPreferredSize().getWidth());

			lblDRExterior = new WebLabel(Constants.MOLECULES_DR_EXTRACELLULAR);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblDRExterior.getPreferredSize().getWidth());

			lblDROuterMembrane = new WebLabel(Constants.MOLECULES_DR_OMEMBRANE);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					lblDROuterMembrane.getPreferredSize().getWidth());

			lblDROuterPeriplasm = new WebLabel(Constants.MOLECULES_DR_OPERIPLASM);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					lblDROuterPeriplasm.getPreferredSize().getWidth());

			lblDRPeptido = new WebLabel(Constants.MOLECULES_DR_PEPTIDOGLYCAN);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblDRPeptido.getPreferredSize().getWidth());

			lblDRInnerPeri = new WebLabel(Constants.MOLECULES_DR_IPERIPLASM);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblDRInnerPeri.getPreferredSize().getWidth());

			lblDRInnerMembrane = new WebLabel(Constants.MOLECULES_DR_IMEMBRANE);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					lblDRInnerMembrane.getPreferredSize().getWidth());

			lblDRCytoplasm = new WebLabel(Constants.MOLECULES_DR_CYTOPLASM);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblDRCytoplasm.getPreferredSize().getWidth());

			overlayColor = new WebOverlayLabel(Constants.MOLECULES_COLOR, Constants.MOLECULES_TIP_COLOR);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayColor.getPreferredSize().getWidth());

			lblNumber = new WebLabel(Constants.MOLECULES_NUMBER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblNumber.getPreferredSize().getWidth());

			overlayMaxLayer = new WebOverlayLabel(Constants.MOLECULES_MAX_LAYER, Constants.MOLECULES_TIP_MAX_LAYER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayMaxLayer.getPreferredSize().getWidth());

			overlayMinLayer = new WebOverlayLabel(Constants.MOLECULES_MIN_LAYER, Constants.MOLECULES_TIP_MIN_LAYER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayMinLayer.getPreferredSize().getWidth());

			overlayCellLocalization = new WebOverlayLabel(Constants.MOLECULES_CELL, Constants.MOLECULES_TIP_CELL);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					overlayCellLocalization.getPreferredSize().getWidth());

			overlayLayerLocalization = new WebOverlayLabel(Constants.MOLECULES_LAYER, Constants.MOLECULES_TIP_LAYER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					overlayLayerLocalization.getPreferredSize().getWidth());

			overlayRadInfl = new WebOverlayLabel(Constants.MOLECULES_RADIUS_INFL, Constants.MOLECULES_TIP_RADIUS_INFL);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayRadInfl.getPreferredSize().getWidth());

			lblRadInflWith = new WebLabel(Constants.MOLECULES_RADIUS_INFL_WITH);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblRadInflWith.getPreferredSize().getWidth());
		}
		{
			{
				tfName = new WebTextField(10);
				tfName.setInputVerifier(new EmptyValidator(tfName));
				tfName.putClientProperty(GroupPanel.FILL_CELL, true);
				tfName.setInputPrompt(Constants.MOLECULES_PROMPT_NAME);
				tfName.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void changedUpdate(DocumentEvent e) {
						warn();
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						warn();
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						warn();
					}

					public void warn() {
						parent.changeTabName(tfName.getText());
					}
				});
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblName.getPreferredSize().getWidth())),
						lblName, tfName));
			}
			{
				if (parent instanceof MacromoleculesPanel)
					cbType = new WebComboBox(Constants.MACROMOLECULES_TYPES);
				else if (parent instanceof BuildingBlocksPanel)
					cbType = new WebComboBox(Constants.BUILDINGBLOCS_TYPES);
				else
					cbType = new WebComboBox(Constants.LZW_TYPES);
				cbType.setInputVerifier(new EmptyValidator(cbType));
				cbType.putClientProperty(GroupPanel.FILL_CELL, true);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblType.getPreferredSize().getWidth())),
						lblType, cbType));
			}
			{
				tfMW = new WebTextField(10);
				tfMW.setInputVerifier(new DoubleEmptyValidator(tfMW));
				tfMW.putClientProperty(GroupPanel.FILL_CELL, true);
				tfMW.setInputPrompt(Constants.MOLECULES_PROMPT_MW);
				add(new GroupPanel(
						(50 + Functions.calculateLabelDifference(maxLabelLenght, lblMW.getPreferredSize().getWidth())),
						lblMW, tfMW));
			}
			{
				tfRadius = new WebTextField(10);
				tfRadius.setInputVerifier(new DoubleEmptyValidator(tfRadius));
				tfRadius.putClientProperty(GroupPanel.FILL_CELL, true);
				tfRadius.setInputPrompt(Constants.MOLECULES_PROMPT_RADIUS);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblRadius.getPreferredSize().getWidth())),
						lblRadius, tfRadius));
			}
			{
				tfDRExterior = new WebTextField(10);
				tfDRExterior.setInputVerifier(new DoubleEmptyValidator(tfDRExterior));
				tfDRExterior.putClientProperty(GroupPanel.FILL_CELL, true);
				tfDRExterior.setInputPrompt(Constants.MOLECULES_PROMPT_DR_EXTRACELLULAR);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						lblDRExterior.getPreferredSize().getWidth())), lblDRExterior, tfDRExterior));
			}
			{
				tfDRCytoplasm = new WebTextField(10);
				tfDRCytoplasm.setInputVerifier(new DoubleEmptyValidator(tfDRCytoplasm));
				tfDRCytoplasm.putClientProperty(GroupPanel.FILL_CELL, true);
				tfDRCytoplasm.setInputPrompt(Constants.MOLECULES_PROMPT_DR_CYTOPLASM);
				// Modify the other textFields placeholder according to the
				// cytoplasm
				tfDRCytoplasm.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void changedUpdate(DocumentEvent e) {
						warn();
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						warn();
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						warn();
					}

					public void warn() {
						changePlaceholder(tfDRCytoplasm.getText());
					}
				});
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						lblDRCytoplasm.getPreferredSize().getWidth())), lblDRCytoplasm, tfDRCytoplasm));
			}
			{
				simpleColorChooser = new WebColorChooserField();
				simpleColorChooser.putClientProperty(GroupPanel.FILL_CELL, true);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						overlayColor.getPreferredSize().getWidth())), overlayColor, simpleColorChooser));
			}
			{
				spinnerNumber = new CustomAlignedSpinner();
				spinnerNumber.putClientProperty(GroupPanel.FILL_CELL, true);
				spinnerNumber.setModel(new CustomSpinnerModel(0, 0, 1));
				spinnerNumber.setTextAligment(SwingConstants.LEFT);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblNumber.getPreferredSize().getWidth())),
						lblNumber, spinnerNumber));
			}
			{
				cbCellLocalization = new WebComboBox();
				cbCellLocalization.setInputVerifier(new EmptyValidator(cbCellLocalization));
				cbCellLocalization.addItemListener(new ItemListener() {
					@SuppressWarnings("unchecked")
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED && cbLayerLocalization != null) {
							Object currentValue = cbLayerLocalization.getSelectedItem();

							cbLayerLocalization.removeAllItems();
							String selected = e.getItem().toString();

							// Buscar célula seleccionada
							JsonCell[] cells = parent.getSingulator().getCells();
							JsonCell cell = null;
							if (cells != null && cells.length > 0) {
								for (JsonCell c : cells) {
									if (c.getCellName().equals(Constants.JSON_CELL + selected))
										cell = c;
								}

								if (cell != null) {
									cbLayerLocalization.addItem(cell.getLayerName());

									JsonLayer[] layers = cell.getLayers();
									for (JsonLayer layer : layers) {
										cbLayerLocalization.addItem(layer.getName());
									}
								} else {
									cbLayerLocalization.addItem(Constants.EXTRACELLULAR_NAME);
								}
							} else {
								cbLayerLocalization.addItem(Constants.EXTRACELLULAR_NAME);
							}

							cbLayerLocalization.setSelectedItem(currentValue);
						}
					}
				});
				cbCellLocalization.putClientProperty(GroupPanel.FILL_CELL, true);
				setCbCellLocalizationValues();
				cbCellLocalization.setSelectedIndex(-1);

				add(new GroupPanel(
						(50 + Functions.calculateLabelDifference(maxLabelLenght,
								overlayCellLocalization.getPreferredSize().getWidth())),
						overlayCellLocalization, cbCellLocalization));
			}
			{
				cbLayerLocalization = new WebComboBox();
				cbLayerLocalization.setInputVerifier(new EmptyValidator(cbLayerLocalization));
				cbLayerLocalization.putClientProperty(GroupPanel.FILL_CELL, true);
				cbLayerLocalization.setSelectedIndex(-1);
				add(new GroupPanel(
						(50 + Functions.calculateLabelDifference(maxLabelLenght,
								overlayLayerLocalization.getPreferredSize().getWidth())),
						overlayLayerLocalization, cbLayerLocalization));
			}
			{
				add(new WebSeparator(false, true));

				WebCollapsiblePane topPane = new WebCollapsiblePane(null, Constants.GENERAL_ADVANCED, setupAdvanced());
				topPane.setExpanded(false);
				topPane.setPaintFocus(false);

				add(topPane);
			}
			// {
			// tfRadInflWith = new WebTextField(10);
			// tfRadInflWith.putClientProperty(GroupPanel.FILL_CELL, true);
			// tfRadInflWith.setInputPrompt(Constants.MOLECULES_PROMPT_RADIUS_INFL_WITH);
			// add(new GroupPanel((50 +
			// Functions.calculateLabelDifference(maxLabelLenght,
			// lblRadInflWith.getPreferredSize().getWidth())), lblRadInflWith,
			// tfRadInflWith));
			// }
		}
	}

	private void changePlaceholder(String text) {
		tfDROuterMembrane.setInputPrompt(text);
		tfDROuterPeriplasm.setInputPrompt(text);
		tfDRPeptidoglycan.setInputPrompt(text);
		tfDRInnerPeriplasm.setInputPrompt(text);
		tfDRInnerMembrane.setInputPrompt(text);
	}

	private WebPanel setupAdvanced() {
		WebPanel toRet = new WebPanel();
		toRet.setBackground(Color.decode(Constants.COLOR_WHITE));
		{
			toRet.setLayout(new VerticalFlowLayout(0, 10));
			toRet.setMargin(7);
		}
		{
			tfDROuterMembrane = new WebTextField(10);
			tfDROuterMembrane.setInputVerifier(new DoubleValidator(tfDROuterMembrane));
			tfDROuterMembrane.putClientProperty(GroupPanel.FILL_CELL, true);
			tfDROuterMembrane.setInputPrompt(Constants.MOLECULES_PROMPT_DR_OMEMBRANE);
			toRet.add(
					new GroupPanel(
							(50 + Functions.calculateLabelDifference(maxLabelLenght,
									lblDROuterMembrane.getPreferredSize().getWidth())),
							lblDROuterMembrane, tfDROuterMembrane));
		}
		{
			tfDROuterPeriplasm = new WebTextField(10);
			tfDROuterPeriplasm.setInputVerifier(new DoubleValidator(tfDROuterPeriplasm));
			tfDROuterPeriplasm.putClientProperty(GroupPanel.FILL_CELL, true);
			tfDROuterPeriplasm.setInputPrompt(Constants.MOLECULES_PROMPT_DR_OPERIPLASM);
			toRet.add(new GroupPanel(
					(50 + Functions.calculateLabelDifference(maxLabelLenght,
							lblDROuterPeriplasm.getPreferredSize().getWidth())),
					lblDROuterPeriplasm, tfDROuterPeriplasm));
		}
		{
			tfDRPeptidoglycan = new WebTextField(10);
			tfDRPeptidoglycan.setInputVerifier(new DoubleValidator(tfDRPeptidoglycan));
			tfDRPeptidoglycan.putClientProperty(GroupPanel.FILL_CELL, true);
			tfDRPeptidoglycan.setInputPrompt(Constants.MOLECULES_PROMPT_DR_PEPTIDOGLYCAN);
			toRet.add(new GroupPanel((50
					+ Functions.calculateLabelDifference(maxLabelLenght, lblDRPeptido.getPreferredSize().getWidth())),
					lblDRPeptido, tfDRPeptidoglycan));
		}
		{
			tfDRInnerPeriplasm = new WebTextField(10);
			tfDRInnerPeriplasm.setInputVerifier(new DoubleValidator(tfDRInnerPeriplasm));
			tfDRInnerPeriplasm.putClientProperty(GroupPanel.FILL_CELL, true);
			tfDRInnerPeriplasm.setInputPrompt(Constants.MOLECULES_PROMPT_DR_IPERIPLASM);
			toRet.add(
					new GroupPanel(
							(50 + Functions.calculateLabelDifference(maxLabelLenght,
									lblDRInnerPeri.getPreferredSize().getWidth())),
							lblDRInnerPeri, tfDRInnerPeriplasm));
		}
		{
			tfDRInnerMembrane = new WebTextField(10);
			tfDRInnerMembrane.setInputVerifier(new DoubleValidator(tfDRInnerMembrane));
			tfDRInnerMembrane.putClientProperty(GroupPanel.FILL_CELL, true);
			tfDRInnerMembrane.setInputPrompt(Constants.MOLECULES_PROMPT_DR_IMEMBRANE);
			toRet.add(
					new GroupPanel(
							(50 + Functions.calculateLabelDifference(maxLabelLenght,
									lblDRInnerMembrane.getPreferredSize().getWidth())),
							lblDRInnerMembrane, tfDRInnerMembrane));
		}
		{
			toRet.add(new WebSeparator(false, true));
		}
		{
			cbMaxLayer = new WebComboBox(Constants.ARRAY_LAYERS);
			cbMaxLayer.setInputVerifier(new EmptyValidator(cbMaxLayer));
			cbMaxLayer.putClientProperty(GroupPanel.FILL_CELL, true);
			cbMaxLayer.setSelectedItem(Constants.EXTRACELLULAR_NAME);
			toRet.add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
					overlayMaxLayer.getPreferredSize().getWidth())), overlayMaxLayer, cbMaxLayer));
		}
		{
			cbMinLayer = new WebComboBox(Constants.ARRAY_LAYERS);
			cbMinLayer.setInputVerifier(new EmptyValidator(cbMinLayer));
			cbMinLayer.putClientProperty(GroupPanel.FILL_CELL, true);
			cbMinLayer.setSelectedItem(Constants.EXTRACELLULAR_NAME);
			toRet.add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
					overlayMinLayer.getPreferredSize().getWidth())), overlayMinLayer, cbMinLayer));
		}
		{
			spinnerRadInfl = new CustomAlignedSpinner();
			spinnerRadInfl.putClientProperty(GroupPanel.FILL_CELL, true);
			spinnerRadInfl.setModel(new CustomSpinnerModel(1, 1, 4, 1));
			spinnerRadInfl.setTextAligment(SwingConstants.LEFT);
			toRet.add(new GroupPanel((50
					+ Functions.calculateLabelDifference(maxLabelLenght, overlayRadInfl.getPreferredSize().getWidth())),
					overlayRadInfl, spinnerRadInfl));
		}
		return toRet;
	}

	public String getMoleculeName() {
		return tfName.getText();
	}

	public void setMoleculeName(String name) {
		tfName.setText(name);
	}

	public String getMoleculeType() {
		return cbType.getSelectedItem().toString();
	}

	public void setMoleculeType(String type) {
		cbType.setSelectedItem(type);
	}

	public double getMoleculeMW() {
		return Double.parseDouble(tfMW.getText());
	}

	public void setMoleculeMW(double mW) {
		tfMW.setText(String.valueOf(mW));
	}

	public double getMoleculeRadius() {
		return Double.parseDouble(tfRadius.getText());
	}

	public void setMoleculeRadius(double radius) {
		tfRadius.setText(String.valueOf(radius));
	}

	public double getMoleculeDRExterior() {
		return Double.parseDouble(tfDRExterior.getText());
	}

	public void setMoleculeDRExterior(double exterior) {
		tfDRExterior.setText(String.valueOf(exterior));
	}

	public double getMoleculeDROMembrane() {
		String text = tfDROuterMembrane.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return Double.parseDouble(tfDRCytoplasm.getText());
		}
	}

	public void setMoleculeDROMembrane(double oMembrane) {
		tfDROuterMembrane.setText(String.valueOf(oMembrane));
	}

	public double getMoleculeDROPeriplasm() {
		String text = tfDROuterPeriplasm.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return Double.parseDouble(tfDRCytoplasm.getText());
		}
	}

	public void setMoleculeDROPeriplasm(double oPeriplasm) {
		tfDROuterPeriplasm.setText(String.valueOf(oPeriplasm));
	}

	public double getMoleculeDRPeptidoglycan() {
		String text = tfDRPeptidoglycan.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return Double.parseDouble(tfDRCytoplasm.getText());
		}
	}

	public void setMoleculeDRPeptidoglycan(double peptidoglycan) {
		tfDRPeptidoglycan.setText(String.valueOf(peptidoglycan));
	}

	public double getMoleculeDRIPeriplasm() {
		String text = tfDRInnerPeriplasm.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return Double.parseDouble(tfDRCytoplasm.getText());
		}
	}

	public void setMoleculeDRIPeriplasm(double iPeriplasm) {
		tfDRInnerPeriplasm.setText(String.valueOf(iPeriplasm));
	}

	public double getMoleculeDRIMembrane() {
		String text = tfDRInnerMembrane.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return Double.parseDouble(tfDRCytoplasm.getText());
		}
	}

	public void setMoleculeDRIMembrane(double iMembrane) {
		tfDRInnerMembrane.setText(String.valueOf(iMembrane));
	}

	public double getMoleculeDRCytoplasm() {
		return Double.parseDouble(tfDRCytoplasm.getText());
	}

	public void setMoleculeDRCytoplasm(double cytoplasm) {
		tfDRCytoplasm.setText(String.valueOf(cytoplasm));
	}

	public String getMoleculeColor() {
		return ColorUtils.getHexColor(simpleColorChooser.getColor());
	}

	public void setMoleculeColor(String color) {
		simpleColorChooser.setColor(Color.decode(color));
	}

	public int getMoleculeNumber() {
		return (int) spinnerNumber.getValue();
	}

	public void setMoleculeNumber(int number) {
		spinnerNumber.setValue(number);
	}

	public String getMoleculeMaxLayer() {
		return cbMaxLayer.getSelectedItem().toString();
	}

	public void setMoleculeMaxLayer(String maxLayer) {
		cbMaxLayer.setSelectedItem(maxLayer);
	}

	public String getMoleculeMinLayer() {
		return cbMinLayer.getSelectedItem().toString();
	}

	public void setMoleculeMinLayer(String minLayer) {
		cbMinLayer.setSelectedItem(minLayer);
	}

	public String getMoleculeCellLocalization() {
		return cbCellLocalization.getSelectedItem().toString();
	}

	public void setMoleculeCellLocalization(String cellLoc) {
		cbCellLocalization.setSelectedItem(cellLoc);
	}

	public String getMoleculeLayerLocalization() {
		return cbLayerLocalization.getSelectedItem().toString();
	}

	public void setMoleculeLayerLocalization(String layerLoc) {
		cbLayerLocalization.setSelectedItem(layerLoc);
	}

	public int getMoleculeRadInfl() {
		return (int) spinnerRadInfl.getValue();
	}

	public void setMoleculeRadInfl(int radInfl) {
		spinnerRadInfl.setValue(radInfl);
	}

	// public String getMoleculeRadInflWith() {
	// return tfRadInflWith.getText();
	// }
	// public void setMoleculeRadInflWith(String radInflWith) {
	// tfRadInflWith.setText(radInflWith);
	// }

	@SuppressWarnings("unchecked")
	public void setCbCellLocalizationValues() {
		Object currentValue = cbCellLocalization.getSelectedItem();

		cbCellLocalization.removeAllItems();

		// Insertar cbCellLocalization
		JsonCell[] cells = parent.getSingulator().getCells();
		if (cells != null && cells.length > 0) {
			for (JsonCell cell : cells) {
				this.cbCellLocalization.addItem(cell.getCellName().replaceFirst(Constants.JSON_CELL, ""));
			}
		}

		this.cbCellLocalization.addItem(Constants.EXTRACELLULAR_NAME);

		cbCellLocalization.setSelectedItem(currentValue);
	}

	public boolean verifyAllData() {
		boolean toRet = true;

		toRet &= tfName.getInputVerifier().verify(null);
		toRet &= cbType.getInputVerifier().verify(null);
		toRet &= tfMW.getInputVerifier().verify(null);
		toRet &= tfRadius.getInputVerifier().verify(null);
		toRet &= tfDRExterior.getInputVerifier().verify(null);
		toRet &= tfDROuterMembrane.getInputVerifier().verify(null);
		toRet &= tfDROuterPeriplasm.getInputVerifier().verify(null);
		toRet &= tfDRPeptidoglycan.getInputVerifier().verify(null);
		toRet &= tfDRInnerPeriplasm.getInputVerifier().verify(null);
		toRet &= tfDRInnerMembrane.getInputVerifier().verify(null);
		toRet &= tfDRCytoplasm.getInputVerifier().verify(null);
		toRet &= cbCellLocalization.getInputVerifier().verify(null);
		toRet &= cbLayerLocalization.getInputVerifier().verify(null);
		toRet &= cbMaxLayer.getInputVerifier().verify(null);
		toRet &= cbMinLayer.getInputVerifier().verify(null);

		return toRet;
	}
}
