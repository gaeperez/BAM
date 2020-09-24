package es.uvigo.ei.sing.singulator.gui.event;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.list.WebListModel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.spinners.CustomAlignedSpinner;
import es.uvigo.ei.sing.singulator.gui.custom.validators.EmptyValidator;
import es.uvigo.ei.sing.singulator.model.JsonMacromolecule;
import es.uvigo.ei.sing.singulator.model.JsonMolecule;

public class ReactionDataPanel extends WebPanel {

	private static final long serialVersionUID = 1L;

	private WebLabel lblInput;
	private WebLabel lblInput2;
	private WebLabel lblOutput;
	private WebLabel lblKM;
	private WebLabel lblKCAT;

	private CustomAlignedSpinner spinnerKCAT;
	private CustomAlignedSpinner spinnerKM;

	private WebList listOutput;

	private WebComboBox cbInput;
	private WebComboBox cbInput2;

	private ReactionPanel parent;

	private double maxLabelLenght = -1.0;

	public ReactionDataPanel(ReactionPanel parent) {
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
				lblInput = new WebLabel(Constants.REACTION_INPUT);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblInput.getPreferredSize().getWidth());

				lblInput2 = new WebLabel(Constants.REACTION_INPUT_2);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblInput2.getPreferredSize().getWidth());

				lblOutput = new WebLabel(Constants.REACTION_OUTPUT);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblOutput.getPreferredSize().getWidth());

				lblKM = new WebLabel(Constants.REACTION_KM);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblKM.getPreferredSize().getWidth());

				lblKCAT = new WebLabel(Constants.REACTION_KCAT);
				maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblKCAT.getPreferredSize().getWidth());
			}
			{
				cbInput = new WebComboBox();
				cbInput2 = new WebComboBox();
			}
			{
				cbInput.setInputVerifier(new EmptyValidator(cbInput));
				setCbInputValues();
				cbInput.setSelectedIndex(-1);
				cbInput.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							String currentName = parent.getCurrentTabName();
							String newName = e.getItem().toString();

							if (!currentName.contains("&")) {
								parent.changeTabName(newName + "&");
							} else {
								int index = currentName.indexOf("&");

								newName = newName + currentName.substring(index);

								parent.changeTabName(newName);
							}
						}
					}
				});
				cbInput.putClientProperty(GroupPanel.FILL_CELL, true);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblInput.getPreferredSize().getWidth())),
						lblInput, cbInput));
			}
			{
				cbInput2.setInputVerifier(new EmptyValidator(cbInput2));
				setCbInput2Values();
				cbInput2.setSelectedIndex(-1);
				cbInput2.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							String currentName = parent.getCurrentTabName();
							String newName = e.getItem().toString();

							if (!currentName.contains("&")) {
								parent.changeTabName("&" + newName);
							} else {
								int index = currentName.indexOf("&");

								newName = currentName.substring(0, index + 1) + newName;

								parent.changeTabName(newName);
							}
						}
					}
				});
				cbInput2.putClientProperty(GroupPanel.FILL_CELL, true);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblInput2.getPreferredSize().getWidth())),
						lblInput2, cbInput2));
			}
			{
				listOutput = new WebList(new WebListModel<>());
				listOutput.setVisibleRowCount(4);
				listOutput.setSelectedIndex(0);
				listOutput.setMultiplySelectionAllowed(true);
				setListOutputValues();
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblOutput.getPreferredSize().getWidth())),
						lblOutput, new WebScrollPane(listOutput) {
							private static final long serialVersionUID = 1L;

							{
								putClientProperty(GroupPanel.FILL_CELL, true);
							}
						}));
			}
			{
				spinnerKM = new CustomAlignedSpinner();
				spinnerKM.putClientProperty(GroupPanel.FILL_CELL, true);
				spinnerKM.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.01));
				spinnerKM.setTextAligment(SwingConstants.LEFT);
				add(new GroupPanel(
						(50 + Functions.calculateLabelDifference(maxLabelLenght, lblKM.getPreferredSize().getWidth())),
						lblKM, spinnerKM));
			}
			{
				spinnerKCAT = new CustomAlignedSpinner();
				spinnerKCAT.putClientProperty(GroupPanel.FILL_CELL, true);
				spinnerKCAT.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(10)));
				spinnerKCAT.setTextAligment(SwingConstants.LEFT);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblKCAT.getPreferredSize().getWidth())),
						lblKCAT, spinnerKCAT));
			}
		}
	}

	public String[] getReactionInputs() {
		return new String[] { cbInput.getSelectedItem().toString(), cbInput2.getSelectedItem().toString() };
	}

	public void setReactionInput(String input) {
		cbInput.setSelectedItem(input);
	}

	public void setReactionInput2(String input) {
		cbInput2.setSelectedItem(input);
	}

	public String[] getReactionOutputs() {
		Object[] outputArray = listOutput.getSelectedValuesList().toArray();
		String[] toRet = new String[outputArray.length];

		int index = 0;
		for (Object obj : outputArray) {
			toRet[index] = obj.toString();

			index++;
		}

		return toRet;
	}

	public void setReactionOutputs(String[] output) {
		listOutput.setSelectedValues(output);
	}

	public double getReactionKM() {
		return (double) spinnerKM.getValue();
	}

	public void setReactionKM(double km) {
		spinnerKM.setValue(km);
	}

	public int getReactionKCAT() {
		return (int) spinnerKCAT.getValue();
	}

	public void setReactionKCAT(int kCat) {
		spinnerKCAT.setValue(kCat);
	}

	@SuppressWarnings("unchecked")
	public void setCbInputValues() {
		Object selectValue = cbInput.getSelectedItem();

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
		}

		cbInput.setSelectedItem(selectValue);
	}

	@SuppressWarnings("unchecked")
	public void setCbInput2Values() {
		Object selectValue = cbInput2.getSelectedItem();

		cbInput2.removeAllItems();

		JsonMolecule jsonAgents = parent.jsonSingulator.getAgents().getMolecules();
		if (jsonAgents != null) {
			JsonMacromolecule[] molecules = jsonAgents.getMacromolecules();
			if (molecules != null && molecules.length > 0) {
				for (JsonMacromolecule molecule : molecules) {
					cbInput2.addItem(molecule.getName().replaceFirst(Constants.JSON_MACROMOLECULE, "")
							.concat(Constants.GUI_MACROMOLECULE));
				}
			}
		}

		cbInput2.setSelectedItem(selectValue);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListOutputValues() {
		List selectedValues = listOutput.getSelectedValuesList();

		WebListModel listModel = listOutput.getWebModel();
		listModel.removeAllElements();

		// Insertar cbCellLocalization
		JsonMolecule jsonAgents = parent.jsonSingulator.getAgents().getMolecules();
		if (jsonAgents != null) {
			JsonMacromolecule[] molecules = jsonAgents.getMacromolecules();
			if (molecules != null && molecules.length > 0) {
				for (JsonMacromolecule molecule : molecules) {
					listModel.addElement(molecule.getName().replaceFirst(Constants.JSON_MACROMOLECULE, "")
							.concat(Constants.GUI_MACROMOLECULE));
				}
			}
		}

		listOutput.setModel(listModel);

		listOutput.setSelectedValues(selectedValues);
	}

	public boolean verifyAllData() {
		boolean toRet = true;

		toRet &= cbInput.getInputVerifier().verify(null);
		toRet &= cbInput2.getInputVerifier().verify(null);

		return toRet;
	}
}
