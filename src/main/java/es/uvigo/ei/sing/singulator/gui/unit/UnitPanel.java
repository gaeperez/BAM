package es.uvigo.ei.sing.singulator.gui.unit;

import java.awt.Color;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.text.WebTextField;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.validators.DoubleEmptyValidator;
import es.uvigo.ei.sing.singulator.gui.custom.validators.DoubleValidator;
import es.uvigo.ei.sing.singulator.gui.custom.validators.EmptyValidator;
import es.uvigo.ei.sing.singulator.gui.interfaces.DataPanel;
import es.uvigo.ei.sing.singulator.model.JsonDiffusionRate;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;
import es.uvigo.ei.sing.singulator.model.JsonUnity;

public class UnitPanel extends WebPanel implements DataPanel {

	private static final long serialVersionUID = 1L;

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

	private WebLabel lblDRCytoplasm;
	private WebLabel lblDRInnerMembrane;
	private WebLabel lblDRInnerPeriplasm;
	private WebLabel lblDRPeptidoglycan;
	private WebLabel lblDROuterPeriplasm;
	private WebLabel lblDROuterMembrane;
	private WebLabel lblDRExterior;
	private WebLabel lblRadius;
	private WebLabel lblMW;
	private WebLabel lblName;

	private double maxLabelLenght = -1.0;

	protected JsonSingulator jsonSingulator;

	public UnitPanel() {
		init();
	}

	public void init() {
		{
			setLayout(new VerticalFlowLayout(0, 10));
			setMargin(7);
			setBackground(Color.decode(Constants.COLOR_WHITE));
		}
		{
			lblName = new WebLabel(Constants.UNITY_NAME);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblName.getPreferredSize().getWidth());

			lblMW = new WebLabel(Constants.UNITY_MW);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblMW.getPreferredSize().getWidth());

			lblRadius = new WebLabel(Constants.UNITY_RADIUS);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblRadius.getPreferredSize().getWidth());

			lblDRExterior = new WebLabel(Constants.UNITY_DR_EXTRACELLULAR);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblDRExterior.getPreferredSize().getWidth());

			lblDROuterMembrane = new WebLabel(Constants.UNITY_DR_OMEMBRANE);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					lblDROuterMembrane.getPreferredSize().getWidth());

			lblDROuterPeriplasm = new WebLabel(Constants.UNITY_DR_OPERIPLASM);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					lblDROuterPeriplasm.getPreferredSize().getWidth());

			lblDRPeptidoglycan = new WebLabel(Constants.UNITY_DR_PEPTIDOGLYCAN);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					lblDRPeptidoglycan.getPreferredSize().getWidth());

			lblDRInnerPeriplasm = new WebLabel(Constants.UNITY_DR_IPERIPLASM);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					lblDRInnerPeriplasm.getPreferredSize().getWidth());

			lblDRInnerMembrane = new WebLabel(Constants.UNITY_DR_IMEMBRANE);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght,
					lblDRInnerMembrane.getPreferredSize().getWidth());

			lblDRCytoplasm = new WebLabel(Constants.UNITY_DR_CYTOPLASM);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblDRCytoplasm.getPreferredSize().getWidth());
		}
		{
			{
				tfName = new WebTextField(10);
				tfName.setInputVerifier(new EmptyValidator(tfName));
				tfName.putClientProperty(GroupPanel.FILL_CELL, true);
				tfName.setInputPrompt(Constants.UNITY_PROMPT_NAME);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblName.getPreferredSize().getWidth())),
						lblName, tfName));
			}
			{
				tfMW = new WebTextField(10);
				tfMW.setInputVerifier(new DoubleEmptyValidator(tfMW));
				tfMW.putClientProperty(GroupPanel.FILL_CELL, true);
				tfMW.setInputPrompt(Constants.UNITY_PROMPT_MW);
				add(new GroupPanel(
						(50 + Functions.calculateLabelDifference(maxLabelLenght, lblMW.getPreferredSize().getWidth())),
						lblMW, tfMW));
			}
			{
				tfRadius = new WebTextField(10);
				tfRadius.setInputVerifier(new DoubleEmptyValidator(tfRadius));
				tfRadius.putClientProperty(GroupPanel.FILL_CELL, true);
				tfRadius.setInputPrompt(Constants.UNITY_PROMPT_RADIUS);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblRadius.getPreferredSize().getWidth())),
						lblRadius, tfRadius));
			}
			{
				tfDRExterior = new WebTextField(10);
				tfDRExterior.setInputVerifier(new DoubleEmptyValidator(tfDRExterior));
				tfDRExterior.putClientProperty(GroupPanel.FILL_CELL, true);
				tfDRExterior.setInputPrompt(Constants.UNITY_PROMPT_DR_EXTRACELLULAR);
				add(new GroupPanel((50 + Functions.calculateLabelDifference(maxLabelLenght,
						lblDRExterior.getPreferredSize().getWidth())), lblDRExterior, tfDRExterior));
			}
			{
				tfDRCytoplasm = new WebTextField(10);
				tfDRCytoplasm.setInputVerifier(new DoubleEmptyValidator(tfDRCytoplasm));
				tfDRCytoplasm.putClientProperty(GroupPanel.FILL_CELL, true);
				tfDRCytoplasm.setInputPrompt(Constants.UNITY_PROMPT_DR_CYTOPLASM);
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
				add(new WebSeparator(false, true));

				WebCollapsiblePane topPane = new WebCollapsiblePane(null, Constants.GENERAL_ADVANCED, setupAdvanced());
				topPane.setExpanded(false);
				topPane.setPaintFocus(false);

				add(topPane);
			}
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
			tfDROuterMembrane.setInputPrompt(Constants.UNITY_PROMPT_DR_OMEMBRANE);
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
			tfDROuterPeriplasm.setInputPrompt(Constants.UNITY_PROMPT_DR_OPERIPLASM);
			toRet.add(new GroupPanel(
					(50 + Functions.calculateLabelDifference(maxLabelLenght,
							lblDROuterPeriplasm.getPreferredSize().getWidth())),
					lblDROuterPeriplasm, tfDROuterPeriplasm));
		}
		{
			tfDRPeptidoglycan = new WebTextField(10);
			tfDRPeptidoglycan.setInputVerifier(new DoubleValidator(tfDRPeptidoglycan));
			tfDRPeptidoglycan.putClientProperty(GroupPanel.FILL_CELL, true);
			tfDRPeptidoglycan.setInputPrompt(Constants.UNITY_PROMPT_DR_PEPTIDOGLYCAN);
			toRet.add(
					new GroupPanel(
							(50 + Functions.calculateLabelDifference(maxLabelLenght,
									lblDRPeptidoglycan.getPreferredSize().getWidth())),
							lblDRPeptidoglycan, tfDRPeptidoglycan));
		}
		{
			tfDRInnerPeriplasm = new WebTextField(10);
			tfDRInnerPeriplasm.setInputVerifier(new DoubleValidator(tfDRInnerPeriplasm));
			tfDRInnerPeriplasm.putClientProperty(GroupPanel.FILL_CELL, true);
			tfDRInnerPeriplasm.setInputPrompt(Constants.UNITY_PROMPT_DR_IPERIPLASM);
			toRet.add(new GroupPanel(
					(50 + Functions.calculateLabelDifference(maxLabelLenght,
							lblDRInnerPeriplasm.getPreferredSize().getWidth())),
					lblDRInnerPeriplasm, tfDRInnerPeriplasm));
		}
		{
			tfDRInnerMembrane = new WebTextField(10);
			tfDRInnerMembrane.setInputVerifier(new DoubleValidator(tfDRInnerMembrane));
			tfDRInnerMembrane.putClientProperty(GroupPanel.FILL_CELL, true);
			tfDRInnerMembrane.setInputPrompt(Constants.UNITY_PROMPT_DR_IMEMBRANE);
			toRet.add(
					new GroupPanel(
							(50 + Functions.calculateLabelDifference(maxLabelLenght,
									lblDRInnerMembrane.getPreferredSize().getWidth())),
							lblDRInnerMembrane, tfDRInnerMembrane));
		}
		return toRet;
	}

	public String getUnityName() {
		return tfName.getText();
	}

	public void setUnityName(String name) {
		tfName.setText(name);
	}

	public void setDefaultUnityName() {
		tfName.clear();
	}

	public Double getUnityMW() {
		String text = tfMW.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return 18.0;
		}
	}

	public void setUnityMW(double mW) {
		tfMW.setText(String.valueOf(mW));
	}

	public void setDefaultUnityMW() {
		tfMW.clear();
	}

	public Double getUnityRadius() {
		String text = tfRadius.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return 0.000162624;
		}
	}

	public void setUnityRadius(double radius) {
		tfRadius.setText(String.valueOf(radius));
	}

	public void setDefaultUnityRadius() {
		tfRadius.clear();
	}

	public Double getUnityDRExterior() {
		return Double.parseDouble(tfDRExterior.getText());
	}

	public void setUnityDRExterior(double exterior) {
		tfDRExterior.setText(String.valueOf(exterior));
	}

	public void setDefaultUnityDRExterior() {
		tfDRExterior.clear();
	}

	public Double getUnityDROMembrane() {
		String text = tfDROuterMembrane.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return Double.parseDouble(tfDRCytoplasm.getText());
		}
	}

	public void setUnityDROMembrane(double oMembrane) {
		tfDROuterMembrane.setText(String.valueOf(oMembrane));
	}

	public void setDefaultUnityDROMembrane() {
		tfDROuterMembrane.clear();
	}

	public Double getUnityDROPeriplasm() {
		String text = tfDROuterPeriplasm.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return Double.parseDouble(tfDRCytoplasm.getText());
		}
	}

	public void setUnityDROPeriplasm(double oPeriplasm) {
		tfDROuterPeriplasm.setText(String.valueOf(oPeriplasm));
	}

	public void setDefaultUnityDROPeriplasm() {
		tfDROuterPeriplasm.clear();
	}

	public Double getUnityDRPeptidoglycan() {
		String text = tfDRPeptidoglycan.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return Double.parseDouble(tfDRCytoplasm.getText());
		}
	}

	public void setUnityDRPeptidoglycan(double peptidoglycan) {
		tfDRPeptidoglycan.setText(String.valueOf(peptidoglycan));
	}

	public void setDefaultUnityDRPeptidoglycan() {
		tfDRPeptidoglycan.clear();
	}

	public Double getUnityDRIPeriplasm() {
		String text = tfDRInnerPeriplasm.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return Double.parseDouble(tfDRCytoplasm.getText());
		}
	}

	public void setUnityDRIPeriplasm(double iPeriplasm) {
		tfDRInnerPeriplasm.setText(String.valueOf(iPeriplasm));
	}

	public void setDefaultUnityDRIPeriplasm() {
		tfDRInnerPeriplasm.clear();
	}

	public Double getUnityDRIMembrane() {
		String text = tfDRInnerMembrane.getText();

		if (!text.isEmpty()) {
			return Double.parseDouble(text);
		} else {
			return Double.parseDouble(tfDRCytoplasm.getText());
		}
	}

	public void setUnityDRIMembrane(double iMembrane) {
		tfDRInnerMembrane.setText(String.valueOf(iMembrane));
	}

	public void setDefaultUnityDRIMembrane() {
		tfDRInnerMembrane.clear();
	}

	public Double getUnityDRCytoplasm() {
		return Double.parseDouble(tfDRCytoplasm.getText());
	}

	public void setUnityDRCytoplasm(double cytoplasm) {
		tfDRCytoplasm.setText(String.valueOf(cytoplasm));
	}

	public void setDefaultUnityDRCytoplasm() {
		tfDRCytoplasm.clear();
	}

	@Override
	public Object[] getAllData() {
		Object[] toRet = new Object[1];

		JsonUnity unity = new JsonUnity();

		unity.setName(getUnityName());
		unity.setMolecularWeight(getUnityMW());
		unity.setRadius(getUnityRadius());

		JsonDiffusionRate diffusionRate = new JsonDiffusionRate();
		diffusionRate.setExterior(getUnityDRExterior());
		diffusionRate.setOuterMembrane(getUnityDROMembrane());
		diffusionRate.setOuterPeriplasm(getUnityDROPeriplasm());
		diffusionRate.setPeptidoglycan(getUnityDRPeptidoglycan());
		diffusionRate.setInnerPeriplasm(getUnityDRIPeriplasm());
		diffusionRate.setInnerMembrane(getUnityDRIMembrane());
		diffusionRate.setCytoplasm(getUnityDRCytoplasm());

		unity.setDiffusionRate(diffusionRate);

		toRet[0] = unity;

		return toRet;
	}

	@Override
	public boolean verifyAllData() {
		boolean toRet = true;

		toRet &= tfMW.getInputVerifier().verify(null);
		toRet &= tfRadius.getInputVerifier().verify(null);
		toRet &= tfDRExterior.getInputVerifier().verify(null);
		toRet &= tfDROuterMembrane.getInputVerifier().verify(null);
		toRet &= tfDROuterPeriplasm.getInputVerifier().verify(null);
		toRet &= tfDRPeptidoglycan.getInputVerifier().verify(null);
		toRet &= tfDRInnerPeriplasm.getInputVerifier().verify(null);
		toRet &= tfDRInnerMembrane.getInputVerifier().verify(null);
		toRet &= tfDRCytoplasm.getInputVerifier().verify(null);

		return toRet;
	}

	@Override
	public void loadData() {
		JsonUnity unity = jsonSingulator.getUnity();
		JsonDiffusionRate dr = unity.getDiffusionRate();

		setUnityName(unity.getName());
		setUnityMW(unity.getMolecularWeight());
		setUnityRadius(unity.getRadius());
		setUnityDRExterior(dr.getExterior());
		setUnityDROMembrane(dr.getOuterMembrane());
		setUnityDROPeriplasm(dr.getOuterPeriplasm());
		setUnityDRPeptidoglycan(dr.getPeptidoglycan());
		setUnityDRIPeriplasm(dr.getInnerPeriplasm());
		setUnityDRIMembrane(dr.getInnerMembrane());
		setUnityDRCytoplasm(dr.getCytoplasm());

		revalidate();
	}

	@Override
	public void updateComponentsData(JsonSingulator jsonSingulator) {
	}

	@Override
	public void setJsonSingulator(JsonSingulator jsonSingulator) {
		this.jsonSingulator = jsonSingulator;
	}

	@Override
	public void deleteAllData() {
		setDefaultUnityName();
		setDefaultUnityMW();
		setDefaultUnityRadius();
		setDefaultUnityDRExterior();
		setDefaultUnityDROMembrane();
		setDefaultUnityDROPeriplasm();
		setDefaultUnityDRPeptidoglycan();
		setDefaultUnityDRIPeriplasm();
		setDefaultUnityDRIMembrane();
		setDefaultUnityDRCytoplasm();

		revalidate();
	}

	@Override
	public String getPanelName() {
		return Constants.UNITY_TITLE;
	}
}
