package es.uvigo.ei.sing.singulator.gui.ribosome;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.alee.extended.colorchooser.WebColorChooserField;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.filechooser.WebFileChooser;
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
import es.uvigo.ei.sing.singulator.gui.custom.validators.EmptyValidator;
import es.uvigo.ei.sing.singulator.model.JsonCell;
import es.uvigo.ei.sing.singulator.model.JsonLayer;

public class RibosomeDataPanel extends WebPanel {

	private static final long serialVersionUID = 1L;

	private WebOverlayLabel overlayMRNA;
	private WebOverlayLabel overlayColor;
	private WebOverlayLabel overlayMaxLayer;
	private WebOverlayLabel overlayMinLayer;
	private WebOverlayLabel overlayCellLocalization;
	private WebOverlayLabel overlayLayerLocalization;
	private WebOverlayLabel overlayRadInfl;

	private WebLabel lblName;
	private WebLabel lblMW;
	private WebLabel lblRadius;
	private WebLabel lblDRExterior;
	private WebLabel lblNumber;
	private WebLabel lblRadInflWith;

	private WebTextField tfName;
	private WebTextField tfMW;
	private WebTextField tfRadius;
	private WebTextField tfDRExterior;
	private WebTextField tfMRNA;
	// private WebTextField tfRadInflWith;

	private CustomAlignedSpinner spinnerNumber;
	private CustomAlignedSpinner spinnerRadInfl;

	private WebComboBox cbMaxLayer;
	private WebComboBox cbMinLayer;
	private WebComboBox cbCellLocalization;
	private WebComboBox cbLayerLocalization;

	private WebColorChooserField simpleColorChooser;
	private WebFileChooser fileChooser;

	private double maxLabelLenght = -1.0;

	private RibosomePanel parent;

	public RibosomeDataPanel(RibosomePanel parent) {
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
			lblName = new WebLabel(Constants.RIBOSOMES_NAME);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblName.getPreferredSize().getWidth());

			overlayMRNA = new WebOverlayLabel(Constants.RIBOSOMES_MRNA, Constants.RIBOSOMES_TIP_MRNA);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayMRNA.getPreferredSize().getWidth());

			lblMW = new WebLabel(Constants.RIBOSOMES_MW);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblMW.getPreferredSize().getWidth());

			lblRadius = new WebLabel(Constants.RIBOSOMES_RADIUS);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblRadius.getPreferredSize().getWidth());

			lblDRExterior = new WebLabel(Constants.RIBOSOMES_DR);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblDRExterior.getPreferredSize().getWidth());

			overlayColor = new WebOverlayLabel(Constants.RIBOSOMES_COLOR, Constants.RIBOSOMES_TIP_COLOR);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayColor.getPreferredSize().getWidth());

			lblNumber = new WebLabel(Constants.RIBOSOMES_NUMBER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblNumber.getPreferredSize().getWidth());

			overlayMaxLayer = new WebOverlayLabel(Constants.RIBOSOMES_MAX_LAYER, Constants.RIBOSOMES_TIP_MAX_LAYER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayMaxLayer.getPreferredSize().getWidth());

			overlayMinLayer = new WebOverlayLabel(Constants.RIBOSOMES_MIN_LAYER, Constants.RIBOSOMES_TIP_MIN_LAYER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayMinLayer.getPreferredSize().getWidth());

			overlayCellLocalization = new WebOverlayLabel(Constants.RIBOSOMES_CELL, Constants.RIBOSOMES_TIP_CELL);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					overlayCellLocalization.getPreferredSize().getWidth());

			overlayLayerLocalization = new WebOverlayLabel(Constants.RIBOSOMES_LAYER, Constants.RIBOSOMES_TIP_LAYER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					overlayLayerLocalization.getPreferredSize().getWidth());

			overlayRadInfl = new WebOverlayLabel(Constants.RIBOSOMES_RADIUS_INFL, Constants.RIBOSOMES_TIP_RADIUS_INFL);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayRadInfl.getPreferredSize().getWidth());

			lblRadInflWith = new WebLabel(Constants.RIBOSOMES_RADIUS_INFL_WITH);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblRadInflWith.getPreferredSize().getWidth());
		}
		{
			{
				tfName = new WebTextField(10);
				tfName.setInputVerifier(new EmptyValidator(tfName));
				tfName.putClientProperty(GroupPanel.FILL_CELL, true);
				tfName.setInputPrompt(Constants.RIBOSOMES_PROMPT_NAME);
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
				tfMRNA = new WebTextField(25);
				tfMRNA.setInputVerifier(new EmptyValidator(tfMRNA));
				tfMRNA.putClientProperty(GroupPanel.FILL_CELL, true);
				tfMRNA.setInputPrompt(Constants.RIBOSOMES_PROMPT_MRNA);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						overlayMRNA.getPreferredSize().getWidth())), overlayMRNA, tfMRNA));

				WebButton btnMRNA = new WebButton(Constants.RIBOSOMES_MRNA_BUTTON);
				btnMRNA.setCursor(Cursor.getDefaultCursor());
				btnMRNA.setFocusable(false);
				btnMRNA.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (fileChooser == null) {
							fileChooser = new WebFileChooser();
							fileChooser.setMultiSelectionEnabled(false);
						}
						if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
							try {
								FileInputStream stream = new FileInputStream(fileChooser.getSelectedFile());
								String text = Functions.readFirstLine(stream);
								stream.close();

								tfMRNA.setText(text);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
				tfMRNA.setTrailingComponent(btnMRNA);
			}
			{
				tfMW = new WebTextField(10);
				tfMW.setInputVerifier(new DoubleEmptyValidator(tfMW));
				tfMW.putClientProperty(GroupPanel.FILL_CELL, true);
				tfMW.setInputPrompt(Constants.RIBOSOMES_PROMPT_MW);
				add(new GroupPanel(
						(50 + Functions.calculateLabelDifference(maxLabelLenght, lblMW.getPreferredSize().getWidth())),
						lblMW, tfMW));
			}
			{
				tfRadius = new WebTextField(10);
				tfRadius.setInputVerifier(new DoubleEmptyValidator(tfRadius));
				tfRadius.putClientProperty(GroupPanel.FILL_CELL, true);
				tfRadius.setInputPrompt(Constants.RIBOSOMES_PROMPT_RADIUS);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblRadius.getPreferredSize().getWidth())),
						lblRadius, tfRadius));
			}
			{
				tfDRExterior = new WebTextField(10);
				tfDRExterior.setInputVerifier(new DoubleEmptyValidator(tfDRExterior));
				tfDRExterior.putClientProperty(GroupPanel.FILL_CELL, true);
				tfDRExterior.setInputPrompt(Constants.RIBOSOMES_PROMPT_DR);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						lblDRExterior.getPreferredSize().getWidth())), lblDRExterior, tfDRExterior));
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
				spinnerNumber.setModel(new CustomSpinnerModel(1, 1, 1));
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
							Object selectedValue = cbLayerLocalization.getSelectedItem();

							cbLayerLocalization.removeAllItems();
							String selected = e.getItem().toString();

							// Buscar célula seleccionada
							JsonCell[] cells = parent.jsonSingulator.getCells();
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

							cbLayerLocalization.setSelectedItem(selectedValue);
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
				cbLayerLocalization.setSelectedIndex(-1);
				cbLayerLocalization.putClientProperty(GroupPanel.FILL_CELL, true);
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
			// tfRadInflWith.setInputPrompt(Constants.RIBOSOMES_PROMPT_RADIUS_INFL_WITH);
			// add(new GroupPanel((50 +
			// Functions.calculateLabelDifference(maxLabelLenght,
			// lblRadInflWith.getPreferredSize().getWidth())), lblRadInflWith,
			// tfRadInflWith));
			// }
		}
	}

	private WebPanel setupAdvanced() {
		WebPanel toRet = new WebPanel();
		toRet.setBackground(Color.decode(Constants.COLOR_WHITE));
		{
			toRet.setLayout(new VerticalFlowLayout(0, 10));
			toRet.setMargin(7);
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

	public String getRibosomeName() {
		return tfName.getText();
	}

	public void setRibosomeName(String name) {
		tfName.setText(name);
	}

	public double getRibosomeMW() {
		return Double.parseDouble(tfMW.getText());
	}

	public void setRibosomeMW(double mW) {
		tfMW.setText(String.valueOf(mW));
	}

	public double getRibosomeRadius() {
		return Double.parseDouble(tfRadius.getText());
	}

	public void setRibosomeRadius(double radius) {
		tfRadius.setText(String.valueOf(radius));
	}

	public double getRibosomeDRExterior() {
		return Double.parseDouble(tfDRExterior.getText());
	}

	public void setRibosomeDRExterior(double exterior) {
		tfDRExterior.setText(String.valueOf(exterior));
	}

	public String getRibosomeColor() {
		return ColorUtils.getHexColor(simpleColorChooser.getColor());
	}

	public void setRibosomeColor(String color) {
		simpleColorChooser.setColor(Color.decode(color));
	}

	public int getRibosomeNumber() {
		return (int) spinnerNumber.getValue();
	}

	public void setRibosomeNumber(int number) {
		spinnerNumber.setValue(number);
	}

	public String getRibosomeMaxLayer() {
		return cbMaxLayer.getSelectedItem().toString();
	}

	public void setRibosomeMaxLayer(String maxLayer) {
		cbMaxLayer.setSelectedItem(maxLayer);
	}

	public String getRibosomeMinLayer() {
		return cbMinLayer.getSelectedItem().toString();
	}

	public void setRibosomeMinLayer(String minLayer) {
		cbMinLayer.setSelectedItem(minLayer);
	}

	public String getRibosomeCellLocalization() {
		return cbCellLocalization.getSelectedItem().toString();
	}

	public void setRibosomeCellLocalization(String cellLoc) {
		cbCellLocalization.setSelectedItem(cellLoc);
	}

	public String getRibosomeLayerLocalization() {
		return cbLayerLocalization.getSelectedItem().toString();
	}

	public void setRibosomeLayerLocalization(String layerLoc) {
		cbLayerLocalization.setSelectedItem(layerLoc);
	}

	public int getRibosomeRadInfl() {
		return (int) spinnerRadInfl.getValue();
	}

	public void setRibosomeRadInfl(int radInfl) {
		spinnerRadInfl.setValue(radInfl);
	}

	// public String getRibosomeRadInflWith() {
	// return tfRadInflWith.getText();
	// }
	// public void setRibosomeRadInflWith(String radInflWith) {
	// tfRadInflWith.setText(radInflWith);
	// }

	public String getRibosomeMRNA() {
		return tfMRNA.getText();
	}

	public void setRibosomeMRNA(String mRNA) {
		tfMRNA.setText(mRNA);
	}

	@SuppressWarnings("unchecked")
	public void setCbCellLocalizationValues() {
		Object selectedValue = cbCellLocalization.getSelectedItem();

		cbCellLocalization.removeAllItems();

		// Insertar cbCellLocalization
		JsonCell[] cells = parent.jsonSingulator.getCells();
		if (cells != null && cells.length > 0) {
			for (JsonCell cell : cells) {
				this.cbCellLocalization.addItem(cell.getCellName().replaceFirst(Constants.JSON_CELL, ""));
			}
		}
		this.cbCellLocalization.addItem(Constants.EXTRACELLULAR_NAME);

		cbCellLocalization.setSelectedItem(selectedValue);
	}

	public boolean verifyAllData() {
		boolean toRet = true;

		toRet &= tfName.getInputVerifier().verify(null);
		toRet &= tfMW.getInputVerifier().verify(null);
		toRet &= tfRadius.getInputVerifier().verify(null);
		toRet &= tfDRExterior.getInputVerifier().verify(null);
		toRet &= cbCellLocalization.getInputVerifier().verify(null);
		toRet &= cbLayerLocalization.getInputVerifier().verify(null);
		toRet &= cbMaxLayer.getInputVerifier().verify(null);
		toRet &= cbMinLayer.getInputVerifier().verify(null);
		toRet &= tfMRNA.getInputVerifier().verify(null);

		return toRet;
	}
}
