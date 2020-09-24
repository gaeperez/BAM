package es.uvigo.ei.sing.singulator.gui.transporter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.alee.extended.colorchooser.WebColorChooserField;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.list.WebListModel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextField;
import com.alee.utils.ColorUtils;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.labels.WebOverlayLabel;
import es.uvigo.ei.sing.singulator.gui.custom.spinners.CustomAlignedSpinner;
import es.uvigo.ei.sing.singulator.gui.custom.spinners.CustomSpinnerModel;
import es.uvigo.ei.sing.singulator.gui.custom.validators.DoubleEmptyValidator;
import es.uvigo.ei.sing.singulator.gui.custom.validators.EmptyValidator;
import es.uvigo.ei.sing.singulator.model.JsonBuildingblock;
import es.uvigo.ei.sing.singulator.model.JsonCell;
import es.uvigo.ei.sing.singulator.model.JsonLayer;
import es.uvigo.ei.sing.singulator.model.JsonLzw;
import es.uvigo.ei.sing.singulator.model.JsonMacromolecule;
import es.uvigo.ei.sing.singulator.model.JsonMolecule;

public class TransportersDataPanel extends WebPanel {

	private static final long serialVersionUID = 1L;

	private WebTextField tfName;
	private WebTextField tfRadius;
	private WebTextField tfDR;

	private CustomAlignedSpinner spinnerNumber;

	private WebComboBox cbOuterLayer;
	private WebComboBox cbInnerLayer;
	private WebComboBox cbCellName;

	private WebOverlayLabel overlayCellName;
	private WebOverlayLabel overlayDR;
	private WebOverlayLabel overlayOuterLayer;
	private WebOverlayLabel overlayInnerLayer;

	private WebLabel lblOutputs;
	private WebLabel lblInputs;
	private WebLabel lblNumber;
	private WebLabel lblColor;
	private WebLabel lblRadius;
	private WebLabel lblName;
	private WebLabel lblType;

	private WebList listInputs;
	private WebList listOutputs;

	private WebToggleButton uniporter;
	private WebToggleButton symporter;
	private WebToggleButton antiporter;

	private WebColorChooserField simpleColorChooser;

	private String toGet;
	private String putTo;

	private double maxLabelLenght = -1.0;

	private List<String> layers;

	private TransportersPanel parent;

	public TransportersDataPanel(TransportersPanel parent) {
		this.parent = parent;
		this.layers = new ArrayList<String>();

		init();
	}

	@SuppressWarnings("rawtypes")
	private void init() {
		{
			setLayout(new VerticalFlowLayout(0, 10));
			setMargin(7);
			setBackground(Color.decode(Constants.COLOR_WHITE));
		}
		{
			lblName = new WebLabel(Constants.TRANSPORTERS_NAME);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblName.getPreferredSize().getWidth());

			overlayCellName = new WebOverlayLabel(Constants.TRANSPORTERS_CELL_INPUT,
					Constants.TRANSPORTERS_TIP_CELL_INPUT);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayCellName.getPreferredSize().getWidth());

			lblRadius = new WebLabel(Constants.TRANSPORTERS_RADIUS);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblRadius.getPreferredSize().getWidth());

			overlayDR = new WebOverlayLabel(Constants.TRANSPORTERS_DIFFRATE, Constants.TRANSPORTERS_TIP_DIFFRATE);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, overlayDR.getPreferredSize().getWidth());

			lblColor = new WebLabel(Constants.TRANSPORTERS_COLOR);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblColor.getPreferredSize().getWidth());

			lblNumber = new WebLabel(Constants.TRANSPORTERS_NUMBER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblNumber.getPreferredSize().getWidth());

			overlayOuterLayer = new WebOverlayLabel(Constants.TRANSPORTERS_OUTER_LAYER,
					Constants.TRANSPORTERS_TIP_OUTER_LAYER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					overlayOuterLayer.getPreferredSize().getWidth());

			overlayInnerLayer = new WebOverlayLabel(Constants.TRANSPORTERS_INNER_LAYER,
					Constants.TRANSPORTERS_TIP_INNER_LAYER);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					overlayInnerLayer.getPreferredSize().getWidth());

			lblType = new WebLabel(Constants.TRANSPORTERS_TYPE);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblType.getPreferredSize().getWidth());

			lblInputs = new WebLabel(Constants.TRANSPORTERS_INPUTS);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblInputs.getPreferredSize().getWidth());

			lblOutputs = new WebLabel(Constants.TRANSPORTERS_OUTPUTS);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblOutputs.getPreferredSize().getWidth());
		}
		{
			{
				tfName = new WebTextField(10);
				tfName.setInputVerifier(new EmptyValidator(tfName));
				tfName.putClientProperty(GroupPanel.FILL_CELL, true);
				tfName.setInputPrompt(Constants.TRANSPORTERS_PROMPT_NAME);
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
				// ComboBoxes instantiation
				cbCellName = new WebComboBox();
				cbOuterLayer = new WebComboBox();
				cbInnerLayer = new WebComboBox();
			}
			{
				cbCellName.setInputVerifier(new EmptyValidator(cbCellName));
				cbCellName.addItemListener(new ItemListener() {
					@SuppressWarnings("unchecked")
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED && cbOuterLayer != null && cbInnerLayer != null) {
							Object cellSelected = cbCellName.getSelectedItem();
							Object outerSelected = cbOuterLayer.getSelectedItem();
							Object innerSelected = cbInnerLayer.getSelectedItem();

							cbOuterLayer.removeAllItems();
							cbInnerLayer.removeAllItems();
							layers.clear();

							// Buscar célula seleccionada
							JsonCell[] cells = parent.jsonSingulator.getCells();

							JsonCell cell = null;
							if (cellSelected != null && cells != null && cells.length > 0) {
								for (JsonCell c : cells) {
									if (c.getCellName().equals(Constants.JSON_CELL + cellSelected))
										cell = c;
								}

								if (cell != null) {
									cbOuterLayer.addItem(cell.getLayerName());
									cbInnerLayer.addItem(cell.getLayerName());

									layers.add(Constants.EXTRACELLULAR_NAME);
									layers.add(cell.getLayerName());

									JsonLayer[] layers = cell.getLayers();
									for (JsonLayer layer : layers) {
										cbOuterLayer.addItem(layer.getName());
										cbInnerLayer.addItem(layer.getName());

										TransportersDataPanel.this.layers.add(layer.getName());
									}
								}
							}

							cbOuterLayer.setSelectedItem(outerSelected);
							cbInnerLayer.setSelectedItem(innerSelected);
						}
					}
				});
				cbCellName.putClientProperty(GroupPanel.FILL_CELL, true);
				setcbCellNameValues();
				cbCellName.setSelectedIndex(-1);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						overlayCellName.getPreferredSize().getWidth())), overlayCellName, cbCellName));
			}
			{
				// Iconed buttons group
				uniporter = new WebToggleButton(Constants.TRANSPORTERS_UNIPORTER,
						Functions.loadIcon(Constants.ICON_UNIPORTER_32), true);
				uniporter.setHorizontalTextPosition(SwingConstants.CENTER);
				uniporter.setVerticalTextPosition(SwingConstants.BOTTOM);
				uniporter.setMinimumWidth((int) uniporter.getPreferredSize().getWidth());
				uniporter.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							listInputs.clearSelection();
							listInputs.setMultiplySelectionAllowed(false);

							listOutputs.clearSelection();
							listOutputs.setEnabled(false);
						}
					}
				});

				antiporter = new WebToggleButton(Constants.TRANSPORTERS_ANTIPORTER,
						Functions.loadIcon(Constants.ICON_ANTIPORTER_32));
				antiporter.setHorizontalTextPosition(SwingConstants.CENTER);
				antiporter.setVerticalTextPosition(SwingConstants.BOTTOM);
				antiporter.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							listInputs.clearSelection();
							listInputs.setMultiplySelectionAllowed(false);

							listOutputs.setEnabled(true);
							listOutputs.setEnabled(true);
						}
					}
				});

				symporter = new WebToggleButton(Constants.TRANSPORTERS_SYMPORTER,
						Functions.loadIcon(Constants.ICON_SYMPORTER_32));
				symporter.setHorizontalTextPosition(SwingConstants.CENTER);
				symporter.setVerticalTextPosition(SwingConstants.BOTTOM);
				symporter.setMinimumWidth((int) symporter.getPreferredSize().getWidth());
				symporter.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							listInputs.clearSelection();
							listInputs.setMultiplySelectionAllowed(true);

							listOutputs.clearSelection();
							listOutputs.setEnabled(false);
						}
					}
				});

				WebButtonGroup iconsGroup = new WebButtonGroup(true, uniporter, symporter, antiporter);
				iconsGroup.setButtonsDrawFocus(false);

				// Grouping buttons with custom button group that allows
				// deselection
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblType.getPreferredSize().getWidth())),
						lblType, iconsGroup));
			}

			{
				tfRadius = new WebTextField(10);
				tfRadius.setInputVerifier(new DoubleEmptyValidator(tfRadius));
				tfRadius.putClientProperty(GroupPanel.FILL_CELL, true);
				tfRadius.setInputPrompt(Constants.TRANSPORTERS_PROMPT_RADIUS);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblRadius.getPreferredSize().getWidth())),
						lblRadius, tfRadius));
			}
			{
				tfDR = new WebTextField(10);
				tfDR.setInputVerifier(new DoubleEmptyValidator(tfDR));
				tfDR.putClientProperty(GroupPanel.FILL_CELL, true);
				tfDR.setInputPrompt(Constants.TRANSPORTERS_PROMPT_DIFFRATE);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, overlayDR.getPreferredSize().getWidth())),
						overlayDR, tfDR));
			}
			{
				cbOuterLayer.setInputVerifier(new EmptyValidator(cbOuterLayer));
				cbOuterLayer.setSelectedIndex(-1);
				cbOuterLayer.putClientProperty(GroupPanel.FILL_CELL, true);
				cbOuterLayer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Object selected = cbOuterLayer.getSelectedItem();
						if (selected != null)
							toGet = getContiguousLayer(cbOuterLayer.getSelectedItem().toString(), false);
					}
				});

				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						overlayOuterLayer.getPreferredSize().getWidth())), overlayOuterLayer, cbOuterLayer));
			}
			{
				cbInnerLayer.setInputVerifier(new EmptyValidator(cbInnerLayer));
				cbInnerLayer.setSelectedIndex(-1);
				cbInnerLayer.putClientProperty(GroupPanel.FILL_CELL, true);
				cbInnerLayer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Object selected = cbInnerLayer.getSelectedItem();
						if (selected != null)
							putTo = getContiguousLayer(cbInnerLayer.getSelectedItem().toString(), true);
					}
				});

				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						overlayInnerLayer.getPreferredSize().getWidth())), overlayInnerLayer, cbInnerLayer));
			}
			{
				simpleColorChooser = new WebColorChooserField();
				simpleColorChooser.putClientProperty(GroupPanel.FILL_CELL, true);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblColor.getPreferredSize().getWidth())),
						lblColor, simpleColorChooser));
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
				listInputs = new WebList(new WebListModel());
				listInputs.setInputVerifier(new EmptyValidator(listInputs));
				listInputs.setVisibleRowCount(4);
				listInputs.setSelectedIndex(0);
				listInputs.setMultiplySelectionAllowed(false);
				setListInputValues();
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblInputs.getPreferredSize().getWidth())),
						lblInputs, new WebScrollPane(listInputs) {
							private static final long serialVersionUID = 1L;
							{
								putClientProperty(GroupPanel.FILL_CELL, true);
							}
						}));
			}
			{
				listOutputs = new WebList(new WebListModel());
				listOutputs.setInputVerifier(new EmptyValidator(listOutputs));
				listOutputs.setVisibleRowCount(4);
				listOutputs.setSelectedIndex(0);
				listOutputs.setMultiplySelectionAllowed(false);
				listOutputs.setEnabled(false);
				setListOutputValues();
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblOutputs.getPreferredSize().getWidth())),
						lblOutputs, new WebScrollPane(listOutputs) {
							private static final long serialVersionUID = 1L;
							{
								putClientProperty(GroupPanel.FILL_CELL, true);
							}
						}));
			}
		}
	}

	private String getContiguousLayer(String currentLayer, boolean isNext) {
		String toRet = "";

		if (isNext) {
			boolean stop = false;
			for (int i = 0; i < layers.size(); i++) {
				toRet = layers.get(i);

				if (stop)
					break;
				if (toRet.equals(currentLayer))
					stop = true;
			}
		} else {
			for (int i = 0; i < layers.size(); i++) {
				if (layers.get(i).equals(currentLayer))
					break;

				toRet = layers.get(i);
			}
		}

		return toRet;
	}

	public String getTransporterName() {
		return tfName.getText();
	}

	public void setTransporterName(String name) {
		tfName.setText(name);
	}

	public String getTransporterCellName() {
		return cbCellName.getSelectedItem().toString();
	}

	public void setTransporterCellName(String cellName) {
		cbCellName.setSelectedItem(cellName);
	}

	public double getTransporterRadius() {
		return Double.parseDouble(tfRadius.getText());
	}

	public void setTransporterRadius(double radius) {
		tfRadius.setText(String.valueOf(radius));
	}

	public double getTransporterDR() {
		return Double.parseDouble(tfDR.getText());
	}

	public void setTransporterDR(double dr) {
		tfDR.setText(String.valueOf(dr));
	}

	public String getTransporterColor() {
		return ColorUtils.getHexColor(simpleColorChooser.getColor());
	}

	public void setTransporterColor(String color) {
		simpleColorChooser.setColor(Color.decode(color));
	}

	public int getTransporterNumber() {
		return (int) spinnerNumber.getValue();
	}

	public void setTransporterNumber(int number) {
		spinnerNumber.setValue(number);
	}

	public String getTransporterOuterLayer() {
		return cbOuterLayer.getSelectedItem().toString();
	}

	public void setTransporterOuterLayer(String outerLayer) {
		cbOuterLayer.setSelectedItem(outerLayer);
	}

	public String getTransporterInnerLayer() {
		return cbInnerLayer.getSelectedItem().toString();
	}

	public void setTransporterInnerLayer(String innerLayer) {
		cbInnerLayer.setSelectedItem(innerLayer);
	}

	public void setTransporterGetFrom(String toGet) {
		this.toGet = toGet;
	}

	public String getTransporterGetFrom() {
		return toGet;
	}

	public void setTransporterPutTo(String putTo) {
		this.putTo = putTo;
	}

	public String getTransporterPutTo() {
		return putTo;
	}

	public String getTransporterType() {
		if (uniporter.isSelected())
			return Constants.TRANSPORTERS_UNIPORTER.toLowerCase();
		else if (symporter.isSelected())
			return Constants.TRANSPORTERS_SYMPORTER.toLowerCase();
		else
			return Constants.TRANSPORTERS_ANTIPORTER.toLowerCase();
	}

	public void setTransporterType(String type) {
		if (type.equals(Constants.TRANSPORTERS_UNIPORTER)) {
			uniporter.setSelected(true);
		} else if (type.equals(Constants.TRANSPORTERS_SYMPORTER)) {
			symporter.setSelected(true);
		} else {
			antiporter.setSelected(true);
		}
	}

	@SuppressWarnings("rawtypes")
	public List getTransporterInputs() {
		return listInputs.getSelectedValuesList();
	}

	public void setTransporterInputs(String[] inputs) {
		listInputs.setSelectedValues(inputs);
	}

	@SuppressWarnings("rawtypes")
	public List getTransporterOutputs() {
		return listOutputs.getSelectedValuesList();
	}

	public void setTransporterOutputs(String[] outputs) {
		listOutputs.setSelectedValues(outputs);
	}

	@SuppressWarnings("unchecked")
	public void setcbCellNameValues() {
		Object selectedValue = cbCellName.getSelectedItem();

		cbCellName.removeAllItems();

		// Insertar cbCellLocalization
		JsonCell[] cells = parent.jsonSingulator.getCells();
		if (cells != null && cells.length > 0) {
			for (JsonCell cell : cells) {
				cbCellName.addItem(cell.getCellName().replaceFirst(Constants.JSON_CELL, ""));
			}
		} else {
			cbOuterLayer.removeAllItems();
			cbInnerLayer.removeAllItems();
		}

		cbCellName.setSelectedItem(selectedValue);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListInputValues() {
		List selectedValues = listInputs.getSelectedValuesList();

		WebListModel listModel = listInputs.getWebModel();
		listModel.removeAllElements();

		JsonMolecule jsonAgents = parent.jsonSingulator.getAgents().getMolecules();
		if (jsonAgents != null) {
			JsonMacromolecule[] molecules = jsonAgents.getMacromolecules();
			if (molecules != null && molecules.length > 0) {
				for (JsonMacromolecule molecule : molecules) {
					listModel.addElement(
							molecule.getName().replaceFirst(Constants.JSON_MACROMOLECULE, "").concat(Constants.GUI_MACROMOLECULE));
				}
			}
			JsonBuildingblock[] buildings = jsonAgents.getBuildingBlocks();
			if (buildings != null && buildings.length > 0) {
				for (JsonBuildingblock building : buildings) {
					listModel.addElement(
							building.getName().replaceFirst(Constants.JSON_BUILDINGBLOCK, "").concat(Constants.GUI_BUILDINGBLOCK));
				}
			}
			JsonLzw[] lzws = jsonAgents.getLzw();
			if (lzws != null && lzws.length > 0) {
				for (JsonLzw lzw : lzws) {
					listModel.addElement(lzw.getName().replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
				}
			}
		}
		listInputs.setModel(listModel);

		listInputs.setSelectedValues(selectedValues);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListOutputValues() {
		List selectedValues = listOutputs.getSelectedValuesList();

		WebListModel listModel = listOutputs.getWebModel();
		listModel.removeAllElements();

		// Insertar cbCellLocalization
		JsonMolecule jsonAgents = parent.jsonSingulator.getAgents().getMolecules();
		if (jsonAgents != null) {
			JsonMacromolecule[] molecules = jsonAgents.getMacromolecules();
			if (molecules != null && molecules.length > 0) {
				for (JsonMacromolecule molecule : molecules) {
					listModel.addElement(
							molecule.getName().replaceFirst(Constants.JSON_MACROMOLECULE, "").concat(Constants.GUI_MACROMOLECULE));
				}
			}
			JsonBuildingblock[] buildings = jsonAgents.getBuildingBlocks();
			if (buildings != null && buildings.length > 0) {
				for (JsonBuildingblock building : buildings) {
					listModel.addElement(
							building.getName().replaceFirst(Constants.JSON_BUILDINGBLOCK, "").concat(Constants.GUI_BUILDINGBLOCK));
				}
			}
			JsonLzw[] lzws = jsonAgents.getLzw();
			if (lzws != null && lzws.length > 0) {
				for (JsonLzw lzw : lzws) {
					listModel.addElement(lzw.getName().replaceFirst(Constants.JSON_LZW, "").concat(Constants.GUI_LZW));
				}
			}
		}
		listOutputs.setModel(listModel);

		listOutputs.setSelectedValues(selectedValues);
	}

	public boolean verifyAllData() {
		boolean toRet = true;

		toRet &= tfName.getInputVerifier().verify(null);
		toRet &= tfRadius.getInputVerifier().verify(null);
		toRet &= tfDR.getInputVerifier().verify(null);
		toRet &= cbCellName.getInputVerifier().verify(null);
		toRet &= cbOuterLayer.getInputVerifier().verify(null);
		toRet &= cbInnerLayer.getInputVerifier().verify(null);
		toRet &= listInputs.getInputVerifier().verify(null);
		toRet &= listOutputs.getInputVerifier().verify(null);

		return toRet;
	}
}
