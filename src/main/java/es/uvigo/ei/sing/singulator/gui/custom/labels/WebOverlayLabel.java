package es.uvigo.ei.sing.singulator.gui.custom.labels;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.alee.extended.image.WebImage;
import com.alee.extended.panel.WebOverlay;
import com.alee.laf.label.WebLabel;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

public class WebOverlayLabel extends WebOverlay {

	private static final long serialVersionUID = 1L;

	private WebLabel label;
	private WebImage icon;

	public WebOverlayLabel(String lblText, String tooltipText) {
		this.icon = new WebImage(Functions.loadIcon(Constants.ICON_INFO_10));

		init(lblText, tooltipText);
	}

	public WebOverlayLabel(String lblText, String tooltipText, ImageIcon icon) {
		this.icon = new WebImage(icon);

		init(lblText, tooltipText);
	}

	public void init(String lblText, String tooltipText) {
		setBackground(Color.decode("#FFFFFF"));
		{
			label = new WebLabel(lblText);
			setComponent(label);
		}
		{
			TooltipManager.setTooltip(icon, tooltipText, TooltipWay.trailing, 0);
			addOverlay(icon, SwingConstants.TRAILING, SwingConstants.TOP);
			setComponentMargin(0, 0, 0, icon.getPreferredSize().width);
		}
	}
}
