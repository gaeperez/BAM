package es.uvigo.ei.sing.singulator.gui.custom.spinners;

import javax.swing.SpinnerModel;

import com.alee.laf.spinner.WebSpinner;

public class CustomAlignedSpinner extends WebSpinner {
	private static final long serialVersionUID = 1L;

	public CustomAlignedSpinner() {
		super();
	}

	public CustomAlignedSpinner(SpinnerModel model) {
		super(model);
	}

	public void setTextAligment(int aligment) {
		DefaultEditor spinnerEditor = (DefaultEditor) getEditor();
		spinnerEditor.getTextField().setHorizontalAlignment(aligment);
	}
}
