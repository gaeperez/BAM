package es.uvigo.ei.sing.singulator.gui.custom.tabbedpanes;

import java.awt.GridLayout;

import javax.swing.SwingConstants;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;

public class CustomAddPanel extends WebPanel {

	private static final long serialVersionUID = 1L;

	public CustomAddPanel() {
		init();
	}

	public void init() {
		{
			setLayout(new GridLayout());
		}
		{
			add(new WebLabel(Constants.TABBED_ADD, SwingConstants.CENTER));
		}
	}
}
