package es.uvigo.ei.sing.singulator.gui.custom.validators;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.list.WebList;
import com.alee.laf.text.WebTextField;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

public class EmptyValidator extends InputVerifier {

	private JComponent component;

	public EmptyValidator(JComponent component) {
		this.component = component;
	}

	@Override
	public boolean shouldYieldFocus(JComponent input) {
		TooltipManager.removeTooltips(component);
		return super.shouldYieldFocus(input);
	}

	@Override
	public boolean verify(JComponent input) {
		boolean toRet = true;

		if (component instanceof WebTextField) {
			WebTextField wtf = (WebTextField) component;
			String text = wtf.getText();
			if (text.trim().isEmpty())
				toRet = false;

		} else if (component instanceof WebComboBox) {
			WebComboBox wcb = (WebComboBox) component;
			if (wcb.getSelectedIndex() == -1)
				toRet = false;
		} else if (component instanceof WebList) {
			WebList wl = (WebList) component;
			if (wl.isEnabled() && wl.getSelectedIndex() == -1)
				toRet = false;
		}

		if (!toRet) {
			TooltipManager.setTooltip(component, Functions.loadIcon(Constants.ICON_REMOVE_16),
					Constants.VALIDATOR_EMPTY, TooltipWay.up, 0);
			TooltipManager.showTooltips(component);
		}

		return toRet;
	}
}
