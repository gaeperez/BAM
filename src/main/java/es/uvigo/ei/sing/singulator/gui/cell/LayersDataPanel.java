package es.uvigo.ei.sing.singulator.gui.cell;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import com.alee.extended.colorchooser.WebColorChooserField;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.text.WebTextField;
import com.alee.utils.ColorUtils;
import com.alee.utils.swing.UnselectableButtonGroup;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.labels.WebOverlayLabel;
import es.uvigo.ei.sing.singulator.gui.custom.validators.DoubleEmptyValidator;

public class LayersDataPanel extends WebPanel {

	private static final long serialVersionUID = 1L;

	private WebTextField tfRadius;
	private WebTextField tfHeight;

	private WebOverlayLabel overlayColor;

	private WebLabel lblDelete;
	private WebLabel lblName;
	private WebLabel lblRadius;
	private WebLabel lblHeight;

	private WebRadioButton outerPeriplasm;
	private WebRadioButton peptidoglycan;
	private WebRadioButton innerMembrane;
	private WebRadioButton innerPeriplasm;
	private WebRadioButton cytoplasm;

	private WebColorChooserField simpleColorChooser;

	private CellsDataPanel parent;

	private Map<Integer, WebRadioButton> mapIndexRadioButton;
	private int index;

	public LayersDataPanel(CellsDataPanel parent) {
		this.parent = parent;
		this.mapIndexRadioButton = new HashMap<Integer, WebRadioButton>();

		init();
	}

	private void init() {
		{
			setLayout(new VerticalFlowLayout(0, 10));
			setBackground(Color.decode(Constants.COLOR_WHITE));
			add(new WebSeparator(false, true));
		}
		{
			lblDelete = new WebLabel(Constants.LAYERS_DELETE);

			lblName = new WebLabel(Constants.LAYERS_NAME);

			lblRadius = new WebLabel(Constants.LAYERS_RADIUS);

			lblHeight = new WebLabel(Constants.LAYERS_HEIGHT);

			overlayColor = new WebOverlayLabel(Constants.LAYERS_COLOR, Constants.LAYERS_TIP_COLOR);
		}
		{
			{
				WebButton btnDelete = new WebButton(Functions.loadIcon(Constants.ICON_REMOVE_16));
				btnDelete.setRolloverDecoratedOnly(true);
				btnDelete.setDrawFocus(false);
				btnDelete.putClientProperty(GroupPanel.FILL_CELL, true);
				btnDelete.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						removeAll();
						parent.removeLayer(getLayersPanel());
					}
				});
				add(new GroupPanel((50 + Functions.calculateLabelDifference(parent.maxLabelLenght,
						lblDelete.getPreferredSize().getWidth())), lblDelete, btnDelete));
			}
			{
				outerPeriplasm = new WebRadioButton(Constants.OUTER_PERIPLASM_NAME);
				outerPeriplasm.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							parent.disableRadioButtons(0);
							index = Constants.OUTER_PERIPLASM_CODE;
						} else if (e.getStateChange() == ItemEvent.DESELECTED) {
							parent.enableRadioButtons(0);
						}
					}
				});
				mapIndexRadioButton.put(0, outerPeriplasm);

				peptidoglycan = new WebRadioButton(Constants.PEPTIDOGLYCAN_NAME);
				peptidoglycan.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							parent.disableRadioButtons(1);
							index = Constants.PEPTIDOGLYCAN_CODE;
						} else if (e.getStateChange() == ItemEvent.DESELECTED) {
							parent.enableRadioButtons(1);
						}
					}
				});
				mapIndexRadioButton.put(1, peptidoglycan);

				innerPeriplasm = new WebRadioButton(Constants.INNER_PERIPLASM_NAME);
				innerPeriplasm.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							parent.disableRadioButtons(2);
							index = Constants.INNER_PERIPLASM_CODE;
						} else if (e.getStateChange() == ItemEvent.DESELECTED) {
							parent.enableRadioButtons(2);
						}
					}
				});
				mapIndexRadioButton.put(2, innerPeriplasm);

				innerMembrane = new WebRadioButton(Constants.INNER_MEMBRANE_NAME);
				innerMembrane.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							parent.disableRadioButtons(3);
							index = Constants.INNER_MEMBRANE_CODE;
						} else if (e.getStateChange() == ItemEvent.DESELECTED) {
							parent.enableRadioButtons(3);
						}
					}
				});
				mapIndexRadioButton.put(3, innerMembrane);

				cytoplasm = new WebRadioButton(Constants.CYTOPLASM_NAME);
				cytoplasm.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							parent.disableRadioButtons(4);
							index = Constants.CYTOPLASM_CODE;
						} else if (e.getStateChange() == ItemEvent.DESELECTED) {
							parent.enableRadioButtons(4);
						}
					}
				});
				mapIndexRadioButton.put(4, cytoplasm);

				// Grouping buttons with custom button group that allows
				// deselection
				UnselectableButtonGroup ubg = UnselectableButtonGroup.group(outerPeriplasm, peptidoglycan,
						innerPeriplasm, innerMembrane, cytoplasm);
				ubg.setUnselectable(false);
				GroupPanel p1 = new GroupPanel(4, false, outerPeriplasm, peptidoglycan, innerPeriplasm);
				GroupPanel p2 = new GroupPanel(4, false, innerMembrane, cytoplasm);
				add(new GroupPanel(
						(50 + Functions.calculateLabelDifference(parent.maxLabelLenght,
								lblName.getPreferredSize().getWidth())),
						lblName, new GroupPanel(GroupingType.fillAll, p1, p2) {
							private static final long serialVersionUID = 1L;

							{
								putClientProperty(GroupPanel.FILL_CELL, true);
							}
						}));
			}
			{
				tfRadius = new WebTextField(10);
				tfRadius.setInputVerifier(new DoubleEmptyValidator(tfRadius));
				tfRadius.putClientProperty(GroupPanel.FILL_CELL, true);
				tfRadius.setInputPrompt(Constants.LAYERS_PROMPT_RADIUS);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(parent.maxLabelLenght,
						lblRadius.getPreferredSize().getWidth())), lblRadius, tfRadius));
			}
			{
				tfHeight = new WebTextField(10);
				tfHeight.setInputVerifier(new DoubleEmptyValidator(tfHeight));
				tfHeight.putClientProperty(GroupPanel.FILL_CELL, true);
				tfHeight.setInputPrompt(Constants.LAYERS_PROMPT_HEIGHT);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(parent.maxLabelLenght,
						lblHeight.getPreferredSize().getWidth())), lblHeight, tfHeight));
			}
			{
				// ColorUtils.getHexColor()
				simpleColorChooser = new WebColorChooserField();
				simpleColorChooser.putClientProperty(GroupPanel.FILL_CELL, true);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(parent.maxLabelLenght,
						overlayColor.getPreferredSize().getWidth())), overlayColor, simpleColorChooser));
			}
		}
	}

	public LayersDataPanel getLayersPanel() {
		return this;
	}

	public int getLayerNameIndex() {
		if (outerPeriplasm.isSelected())
			return 0;
		else if (peptidoglycan.isSelected())
			return 1;
		else if (innerPeriplasm.isSelected())
			return 2;
		else if (innerMembrane.isSelected())
			return 3;
		else if (cytoplasm.isSelected())
			return 4;
		else
			return -1;
	}

	public String getLayerName() {
		if (outerPeriplasm.isSelected())
			return outerPeriplasm.getText();
		else if (peptidoglycan.isSelected())
			return peptidoglycan.getText();
		else if (innerPeriplasm.isSelected())
			return innerPeriplasm.getText();
		else if (innerMembrane.isSelected())
			return innerMembrane.getText();
		else
			return cytoplasm.getText();
	}

	public void setLayerName(String layerName) {
		switch (layerName) {
		case Constants.OUTER_PERIPLASM_NAME:
			if (parent.isRadioButtonFree(0))
				outerPeriplasm.setSelected(true);
			break;
		case Constants.PEPTIDOGLYCAN_NAME:
			if (parent.isRadioButtonFree(1))
				peptidoglycan.setSelected(true);
			break;
		case Constants.INNER_PERIPLASM_NAME:
			if (parent.isRadioButtonFree(2))
				innerPeriplasm.setSelected(true);
			break;
		case Constants.INNER_MEMBRANE_NAME:
			if (parent.isRadioButtonFree(3))
				innerMembrane.setSelected(true);
			break;
		case Constants.CYTOPLASM_NAME:
			if (parent.isRadioButtonFree(4))
				cytoplasm.setSelected(true);
			break;
		default:
			break;
		}
	}

	public void setLayerNameById(int index) {
		mapIndexRadioButton.get(index).setSelected(true);
	}

	public double getLayerRadius() {
		return Double.parseDouble(tfRadius.getText());
	}

	public void setLayerRadius(double radius) {
		tfRadius.setText(String.valueOf(radius));
	}

	public double getLayerHeight() {
		return Double.parseDouble(tfHeight.getText());
	}

	public void setLayerHeight(double height) {
		tfHeight.setText(String.valueOf(height));
	}

	public String getLayerColor() {
		return ColorUtils.getHexColor(simpleColorChooser.getColor());
	}

	public void setLayerColor(String color) {
		simpleColorChooser.setColor(Color.decode(color));
	}

	public int getIndex() {
		return index;
	}

	public void disableRadioButton(int index) {
		mapIndexRadioButton.get(index).setEnabled(false);
	}

	public void enableRadioButton(int index) {
		mapIndexRadioButton.get(index).setEnabled(true);
	}

	public int getIndexRadioButton() {
		for (int key : mapIndexRadioButton.keySet()) {
			if (mapIndexRadioButton.get(key).isSelected()) {
				return key;
			}
		}
		return -1;
	}

	public boolean verifyAllData() {
		boolean toRet = true;

		toRet &= tfRadius.getInputVerifier().verify(null);
		toRet &= tfHeight.getInputVerifier().verify(null);

		return toRet;
	}
}
