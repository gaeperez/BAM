package es.uvigo.ei.sing.singulator.gui.custom.validators;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

public class TabbedPaneValidator extends InputVerifier {

	private WebTabbedPane wtp;

	public TabbedPaneValidator(WebTabbedPane wtp) {
		this.wtp = wtp;
	}

	@Override
	public boolean shouldYieldFocus(JComponent input) {
		TooltipManager.removeTooltips(wtp);
		return super.shouldYieldFocus(input);
	}

	@Override
	public boolean verify(JComponent input) {
		return true;
	}

	public boolean verify(boolean toRet, int errorCode) {
		if (!toRet) {
			if (Constants.VALIDATOR_TABBED_CODE_GENERAL == errorCode) {
				TooltipManager.setTooltip(wtp, Functions.loadIcon(Constants.ICON_REMOVE_16),
						Constants.VALIDATOR_TABBED_GENERAL, TooltipWay.up, 0);
				TooltipManager.showTooltips(wtp);
			} else if (Constants.VALIDATOR_TABBED_CODE_NAMES == errorCode) {
				TooltipManager.setTooltip(wtp, Functions.loadIcon(Constants.ICON_REMOVE_16),
						Constants.VALIDATOR_TABBED_NAMES, TooltipWay.up, 0);
				TooltipManager.showTooltips(wtp);
			}
		} else {
			TooltipManager.removeTooltips(wtp);
		}

		return toRet;
	}
}
