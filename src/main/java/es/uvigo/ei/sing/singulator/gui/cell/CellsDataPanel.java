package es.uvigo.ei.sing.singulator.gui.cell;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.alee.extended.colorchooser.WebColorChooserField;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.utils.ColorUtils;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.labels.WebOverlayLabel;
import es.uvigo.ei.sing.singulator.gui.custom.spinners.CustomAlignedSpinner;
import es.uvigo.ei.sing.singulator.gui.custom.spinners.CustomSpinnerModel;
import es.uvigo.ei.sing.singulator.gui.custom.validators.DoubleEmptyValidator;
import es.uvigo.ei.sing.singulator.gui.custom.validators.EmptyValidator;

public class CellsDataPanel extends WebPanel {

	private static final long serialVersionUID = 1L;

	private WebPanel panelNorth;
	private WebPanel panelCenter;

	private WebTextField tfName;
	private WebTextField tfRadius;
	private WebTextField tfHeight;

	private CustomAlignedSpinner spinnerNumber;

	private WebOverlayLabel overlayColor;

	private WebLabel lblName;
	// private WebLabel lblLayerName;
	private WebLabel lblRadius;
	private WebLabel lblHeight;
	private WebLabel lblNumber;
	private WebLabel lblForm;
	private WebLabel lblLayer;

	private List<LayersDataPanel> listLayersPanel;

	private CellsPanel parent;

	protected double maxLabelLenght = -1.0;

	private WebColorChooserField simpleColorChooser;

	private WebToggleButton capsule;
	private WebToggleButton sphere;
	private WebToggleButton hemisphere;

	private WebButton btnLayer;

	private int numberOfLayers;

	private Map<Integer, Boolean> mapIndexEnable;

	public CellsDataPanel(CellsPanel parent) {
		this.parent = parent;
		this.listLayersPanel = new ArrayList<LayersDataPanel>();
		this.numberOfLayers = 0;

		this.mapIndexEnable = new HashMap<Integer, Boolean>();
		this.mapIndexEnable.put(0, true);
		this.mapIndexEnable.put(1, true);
		this.mapIndexEnable.put(2, true);
		this.mapIndexEnable.put(3, true);
		this.mapIndexEnable.put(4, true);

		init();
	}

	private void init() {
		{
			setBackground(Color.decode(Constants.COLOR_WHITE));
			setLayout(new VerticalFlowLayout(0, 10));
			setMargin(7);
		}
		{
			panelNorth = new WebPanel();
			panelNorth.setBackground(Color.decode(Constants.COLOR_WHITE));
			panelNorth.setLayout(new VerticalFlowLayout(0, 10));
			add(panelNorth);
			{
				{
					lblName = new WebLabel(Constants.CELLS_NAME);
					maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblName.getPreferredSize().getWidth());

					lblRadius = new WebLabel(Constants.CELLS_RADIUS);
					maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
							lblRadius.getPreferredSize().getWidth());

					lblHeight = new WebLabel(Constants.CELLS_HEIGHT);
					maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
							lblHeight.getPreferredSize().getWidth());

					overlayColor = new WebOverlayLabel(Constants.CELLS_COLOR, Constants.CELLS_TIP_COLOR);
					maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
							overlayColor.getPreferredSize().getWidth());

					lblNumber = new WebLabel(Constants.CELLS_NUMBER);
					maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
							lblNumber.getPreferredSize().getWidth());

					lblForm = new WebLabel(Constants.CELLS_FORM);
					maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblForm.getPreferredSize().getWidth());

					lblLayer = new WebLabel(Constants.CELLS_ADD_LAYER);
					maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
							lblLayer.getPreferredSize().getWidth());
				}
				{
					{
						tfName = new WebTextField(10);
						tfName.setInputVerifier(new EmptyValidator(tfName));
						tfName.putClientProperty(GroupPanel.FILL_CELL, true);
						tfName.setInputPrompt(Constants.CELLS_PROMPT_NAME);
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
						panelNorth.add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
								lblName.getPreferredSize().getWidth())), lblName, tfName));
					}
					{
						// Iconed buttons group
						hemisphere = new WebToggleButton(Constants.CELLS_HEMISPHERE,
								Functions.loadIcon(Constants.ICON_HEMISPHERE_32));
						hemisphere.setHorizontalTextPosition(SwingConstants.CENTER);
						hemisphere.setVerticalTextPosition(SwingConstants.BOTTOM);

						capsule = new WebToggleButton(Constants.CELLS_CAPSULE,
								Functions.loadIcon(Constants.ICON_CAPSULE_32), true);
						capsule.setHorizontalTextPosition(SwingConstants.CENTER);
						capsule.setVerticalTextPosition(SwingConstants.BOTTOM);
						capsule.setMinimumWidth((int) hemisphere.getPreferredSize().getWidth());

						sphere = new WebToggleButton(Constants.CELLS_SPHERE,
								Functions.loadIcon(Constants.ICON_SPHERE_32));
						sphere.setHorizontalTextPosition(SwingConstants.CENTER);
						sphere.setVerticalTextPosition(SwingConstants.BOTTOM);
						sphere.setMinimumWidth((int) hemisphere.getPreferredSize().getWidth());

						WebButtonGroup iconsGroup = new WebButtonGroup(true, capsule, sphere, hemisphere);
						iconsGroup.setButtonsDrawFocus(false);

						// Grouping buttons with custom button group that allows
						// deselection
						panelNorth.add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
								lblForm.getPreferredSize().getWidth())), lblForm, iconsGroup));
					}
					{
						tfRadius = new WebTextField(10);
						tfRadius.setInputVerifier(new DoubleEmptyValidator(tfRadius));
						tfRadius.putClientProperty(GroupPanel.FILL_CELL, true);
						tfRadius.setInputPrompt(Constants.CELLS_PROMPT_RADIUS);
						panelNorth.add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
								lblRadius.getPreferredSize().getWidth())), lblRadius, tfRadius));
					}
					{
						tfHeight = new WebTextField(10);
						tfHeight.setInputVerifier(new DoubleEmptyValidator(tfHeight));
						tfHeight.putClientProperty(GroupPanel.FILL_CELL, true);
						tfHeight.setInputPrompt(Constants.CELLS_PROMPT_HEIGHT);
						panelNorth.add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
								lblHeight.getPreferredSize().getWidth())), lblHeight, tfHeight));
					}
					{
						// ColorUtils.getHexColor()
						simpleColorChooser = new WebColorChooserField();
						simpleColorChooser.putClientProperty(GroupPanel.FILL_CELL, true);
						panelNorth.add(new GroupPanel(
								(50 + Functions.calculateLabelDifference(maxLabelLenght,
										overlayColor.getPreferredSize().getWidth())),
								overlayColor, simpleColorChooser));
					}
					{
						spinnerNumber = new CustomAlignedSpinner();
						spinnerNumber.putClientProperty(GroupPanel.FILL_CELL, true);
						spinnerNumber.setModel(new CustomSpinnerModel(1, 1, 1));
						spinnerNumber.setTextAligment(SwingConstants.LEFT);
						panelNorth.add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
								lblNumber.getPreferredSize().getWidth())), lblNumber, spinnerNumber));
					}
					{
						btnLayer = new WebButton(Functions.loadIcon(Constants.ICON_ADD_16));
						btnLayer.setRolloverDecoratedOnly(true);
						btnLayer.setDrawFocus(false);
						btnLayer.putClientProperty(GroupPanel.FILL_CELL, true);
						btnLayer.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(final ActionEvent e) {
								addLayer(new LayersDataPanel(getCellsDataPanel()));
							}

						});
						panelNorth.add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
								lblLayer.getPreferredSize().getWidth())), lblLayer, btnLayer));
					}
				}
			}
		}
		{
			panelCenter = new WebPanel();
			panelCenter.setBackground(Color.decode(Constants.COLOR_WHITE));
			panelCenter.setLayout(new VerticalFlowLayout(0, 10));
			add(panelCenter);
		}
	}

	public CellsDataPanel getCellsDataPanel() {
		return this;
	}

	public void addLayer(LayersDataPanel panel) {
		if (numberOfLayers < Constants.CELLS_MAX_LAYERS) {
			listLayersPanel.add(panel);

			// Comprobar si no tiene nada seleccionado
			int selectedIndex = panel.getLayerNameIndex();
			if (selectedIndex == -1) {
				selectedIndex = findRadioButtonFree();
				panel.setLayerNameById(selectedIndex);
			}
			disableRadioButtons(selectedIndex);

			panelCenter.add(panel);
			repaint();
			revalidate();

			numberOfLayers++;
			if (numberOfLayers == Constants.CELLS_MAX_LAYERS) {
				btnLayer.setEnabled(false);
			}
		}
	}

	public void removeLayer(LayersDataPanel panel) {
		listLayersPanel.remove(panel);

		int index = panel.getIndexRadioButton();
		enableRadioButtons(index);

		panelCenter.remove(panel);
		repaint();
		revalidate();

		numberOfLayers--;
		btnLayer.setEnabled(true);
	}

	public void enableRadioButtons(int index) {
		mapIndexEnable.put(index, true);
		for (LayersDataPanel ldp : listLayersPanel) {
			for (int key : mapIndexEnable.keySet()) {
				if (mapIndexEnable.get(key)) {
					ldp.enableRadioButton(index);
				}
			}
		}
	}

	public void disableRadioButtons(int index) {
		mapIndexEnable.put(index, false);
		for (LayersDataPanel ldp : listLayersPanel) {
			for (int key : mapIndexEnable.keySet()) {
				if (!mapIndexEnable.get(key)) {
					ldp.disableRadioButton(key);
				}
			}
		}
	}

	public int findRadioButtonFree() {
		for (int key : mapIndexEnable.keySet()) {
			if (mapIndexEnable.get(key)) {
				return key;
			}
		}

		return -1;
	}

	public boolean isRadioButtonFree(int index) {
		return mapIndexEnable.get(index);
	}

	public boolean isListLayersPanelEmpty() {
		if (listLayersPanel.isEmpty())
			return true;
		else
			return false;
	}

	public Collection<LayersDataPanel> getListLayersPanel() {
		// Sort the layers based on its ID
		TreeMap<Integer, LayersDataPanel> sortedMap = new TreeMap<Integer, LayersDataPanel>();
		for (LayersDataPanel ldp : listLayersPanel) {
			sortedMap.put(ldp.getIndex(), ldp);
		}

		return sortedMap.values();
	}

	public String getCellName() {
		return tfName.getText();
	}

	public void setCellName(String cellName) {
		tfName.setText(cellName);
	}

	public String getCellLayerName() {
		return Constants.OUTER_MEMBRANE_NAME;
	}

	public double getCellRadius() {
		return Double.parseDouble(tfRadius.getText());
	}

	public void setCellRadius(double radius) {
		tfRadius.setText(String.valueOf(radius));
	}

	public double getCellHeight() {
		return Double.parseDouble(tfHeight.getText());
	}

	public void setCellHeight(double height) {
		tfHeight.setText(String.valueOf(height));
	}

	public String getCellColor() {
		return ColorUtils.getHexColor(simpleColorChooser.getColor());
	}

	public void setCellColor(String color) {
		simpleColorChooser.setColor(Color.decode(color));
	}

	public int getCellNumber() {
		return (int) spinnerNumber.getValue();
	}

	public void setCellNumber(int number) {
		spinnerNumber.setValue(number);
	}

	public String getCellForm() {
		if (capsule.isSelected())
			return Constants.CELLS_CAPSULE.toLowerCase();
		else if (sphere.isSelected())
			return Constants.CELLS_SPHERE.toLowerCase();
		else
			return Constants.CELLS_HEMISPHERE.toLowerCase();
	}

	public void setCellForm(String form) {
		if (form.equals(Constants.CELLS_CAPSULE)) {
			capsule.setSelected(true);
		} else if (form.equals(Constants.CELLS_SPHERE)) {
			sphere.setSelected(true);
		} else {
			hemisphere.setSelected(true);
		}
	}

	public boolean verifyAllData() {
		boolean toRet = true;

		toRet &= tfName.getInputVerifier().verify(null);
		toRet &= tfRadius.getInputVerifier().verify(null);
		toRet &= tfHeight.getInputVerifier().verify(null);

		return toRet;
	}
}
