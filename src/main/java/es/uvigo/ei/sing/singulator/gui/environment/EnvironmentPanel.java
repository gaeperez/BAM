package es.uvigo.ei.sing.singulator.gui.environment;

import java.awt.Color;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.validators.DoubleEmptyValidator;
import es.uvigo.ei.sing.singulator.gui.interfaces.DataPanel;
import es.uvigo.ei.sing.singulator.model.JsonEnvironment;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public class EnvironmentPanel extends WebPanel implements DataPanel {

	private static final long serialVersionUID = 1L;

	private WebTextField tfWidth;
	private WebTextField tfHeight;
	private WebTextField tfLenght;

	private WebLabel lblWidth;
	private WebLabel lblHeight;
	private WebLabel lblLenght;

	private double maxLabelLenght = -1.0;

	protected JsonSingulator jsonSingulator;

	public EnvironmentPanel() {
		init();
	}

	public void init() {
		{
			setLayout(new VerticalFlowLayout(0, 10));
			setMargin(7);
			setBackground(Color.decode(Constants.COLOR_WHITE));
		}
		{
			lblWidth = new WebLabel(Constants.ENVIRONMENT_WIDTH);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblWidth.getPreferredSize().getWidth());

			lblHeight = new WebLabel(Constants.ENVIRONMENT_HEIGHT);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblHeight.getPreferredSize().getWidth());

			lblLenght = new WebLabel(Constants.ENVIRONMENT_LENGTH);
			maxLabelLenght = Functions.setMaxLabelLenght(maxLabelLenght, lblLenght.getPreferredSize().getWidth());
		}
		{
			{
				tfWidth = new WebTextField(10);
				tfWidth.setInputVerifier(new DoubleEmptyValidator(tfWidth));
				tfWidth.putClientProperty(GroupPanel.FILL_CELL, true);
				tfWidth.setInputPrompt(Constants.ENVIRONMENT_PROMPT_WIDTH);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblWidth.getPreferredSize().getWidth())),
						lblWidth, tfWidth));
			}
			{
				tfHeight = new WebTextField(10);
				tfHeight.setInputVerifier(new DoubleEmptyValidator(tfHeight));
				tfHeight.putClientProperty(GroupPanel.FILL_CELL, true);
				tfHeight.setInputPrompt(Constants.ENVIRONMENT_PROMPT_HEIGHT);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblHeight.getPreferredSize().getWidth())),
						lblHeight, tfHeight));
			}
			{
				tfLenght = new WebTextField(10);
				tfLenght.setInputVerifier(new DoubleEmptyValidator(tfLenght));
				tfLenght.putClientProperty(GroupPanel.FILL_CELL, true);
				tfLenght.setInputPrompt(Constants.ENVIRONMENT_PROMPT_LENGTH);
				add(new GroupPanel((50
						+ Functions.calculateLabelDifference(maxLabelLenght, lblLenght.getPreferredSize().getWidth())),
						lblLenght, tfLenght));
			}
		}
	}

	public Double getEnvironmentWidth() {
		return Double.parseDouble(tfWidth.getText());
	}

	public void setEnvironmentWidth(double width) {
		tfWidth.setText(String.valueOf(width));
	}

	public void setDefaultEnvironmentWidth() {
		tfWidth.clear();
	}

	public Double getEnvironmentHeight() {
		return Double.parseDouble(tfHeight.getText());
	}

	public void setEnvironmentHeight(double height) {
		tfHeight.setText(String.valueOf(height));
	}

	public void setDefaultEnvironmentHeight() {
		tfHeight.clear();
	}

	public Double getEnvironmentLenght() {
		return Double.parseDouble(tfLenght.getText());
	}

	public void setEnvironmentLenght(double lenght) {
		tfLenght.setText(String.valueOf(lenght));
	}

	public void setDefaultEnvironmentLenght() {
		tfLenght.clear();
	}

	@Override
	public Object[] getAllData() {
		Object[] toRet = new Object[1];

		JsonEnvironment environment = new JsonEnvironment();

		environment.setWidth(getEnvironmentWidth());
		environment.setHeight(getEnvironmentHeight());
		environment.setLenght(getEnvironmentLenght());

		toRet[0] = environment;

		return toRet;
	}

	@Override
	public boolean verifyAllData() {
		boolean toRet = true;

		toRet &= tfWidth.getInputVerifier().verify(null);
		toRet &= tfHeight.getInputVerifier().verify(null);
		toRet &= tfLenght.getInputVerifier().verify(null);

		return toRet;
	}

	@Override
	public void loadData() {
		JsonEnvironment env = jsonSingulator.getEnvironment();

		setEnvironmentWidth(env.getWidth());
		setEnvironmentHeight(env.getHeight());
		setEnvironmentLenght(env.getLenght());

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
		setDefaultEnvironmentWidth();
		setDefaultEnvironmentHeight();
		setDefaultEnvironmentLenght();

		revalidate();
	}

	@Override
	public String getPanelName() {
		return Constants.ENVIRONMENT_TITLE;
	}
}
