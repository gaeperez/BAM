package es.uvigo.ei.sing.singulator.gui.custom.validators;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

import com.alee.laf.text.WebTextField;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

public class DoubleEmptyValidator extends InputVerifier {

	private WebTextField wtf;

	public DoubleEmptyValidator(WebTextField wtf) {
		this.wtf = wtf;
	}

	@Override
	public boolean shouldYieldFocus(JComponent input) {
		TooltipManager.removeTooltips(wtf);
		return super.shouldYieldFocus(input);
	}

	@Override
	public boolean verify(JComponent input) {
		boolean toRet = false;

		String text = wtf.getText();
		if (text.trim().isEmpty()) {
			TooltipManager.setTooltip(wtf, Functions.loadIcon(Constants.ICON_REMOVE_16), Constants.VALIDATOR_EMPTY,
					TooltipWay.up, 0);
			TooltipManager.showTooltips(wtf);

			toRet = false;
		} else {
			try {
				Double.parseDouble(text);
				toRet = true;
			} catch (NumberFormatException e) {
				TooltipManager.setTooltip(wtf, Functions.loadIcon(Constants.ICON_REMOVE_16),
						Constants.VALIDATOR_DOUBLE_WRONG, TooltipWay.up, 0);
				TooltipManager.showTooltips(wtf);

				toRet = false;
			}
		}

		return toRet;
	}
}
