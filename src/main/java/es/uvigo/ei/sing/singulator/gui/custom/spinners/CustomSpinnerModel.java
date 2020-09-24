package es.uvigo.ei.sing.singulator.gui.custom.spinners;

import javax.swing.SpinnerNumberModel;

public class CustomSpinnerModel extends SpinnerNumberModel {

	private static final long serialVersionUID = 1L;

	public CustomSpinnerModel(int value, int minimum, int maximum, int step) {
		super(value, minimum, maximum, step);
	}

	public CustomSpinnerModel(int value, int minimum, int step) {
		super(value, minimum, null, step);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Object value) {
		if (getMaximum() != null && getMaximum().compareTo(value) < 0)
			super.setValue(getMaximum());
		else if (getMinimum() != null && getMinimum().compareTo(value) > 0)
			super.setValue(getMinimum());
		else
			super.setValue(value);
	}
}
