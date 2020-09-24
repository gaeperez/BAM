package es.uvigo.ei.sing.singulator.gui.custom.validators;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.InputVerifier;
import javax.swing.JComponent;

import org.apache.commons.lang.StringUtils;

import com.alee.laf.text.WebTextField;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

public class EmailValidator extends InputVerifier {

	private WebTextField wtf;

	public EmailValidator(WebTextField wtf) {
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
		int countChars = 0;
		String strAux = "";
		InternetAddress emailAddr;

		String text = wtf.getText();
		if (text.trim().isEmpty()) {
			toRet = true;
		} else {
			// Contamos comas
			countChars = StringUtils.countMatches(text, ",");

			// Comma separated
			String[] emails = text.split(",");

			// Quitamos comas sobrantes
			if (emails.length < countChars) {
				for (String email : emails) {
					if (strAux.isEmpty()) {
						strAux += email;
					} else {
						strAux += "," + email;
					}
				}

				wtf.setText(strAux);
			}

			// Comprobamos todos los emails
			for (String email : emails) {
				try {
					emailAddr = new InternetAddress(email);
					emailAddr.validate();

					toRet = true;
				} catch (AddressException e) {
					TooltipManager.setTooltip(wtf, Functions.loadIcon(Constants.ICON_REMOVE_16),
							Constants.VALIDATOR_EMAIL_WRONG, TooltipWay.up, 0);
					TooltipManager.showTooltips(wtf);

					toRet = false;
					break;
				}
			}
		}

		return toRet;
	}
}
