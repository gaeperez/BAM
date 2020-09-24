package es.uvigo.ei.sing.singulator.gui.custom.tabbedpanes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.constant.GUIProperties;

public class ButtonTabComponent extends WebPanel {

	private static final long serialVersionUID = 1L;

	private WebLabel lblTitle;

	private WebButton btnClose;

	private CustomTabbedPane parent;

	private String uniqueName;

	private ImageIcon focused;
	private ImageIcon unfocused;

	public ButtonTabComponent(String title, CustomTabbedPane parent) {
		this.parent = parent;
		this.uniqueName = title;
		this.focused = Functions.loadIcon(Constants.ICON_REMOVE_FOCUSED_10);
		this.unfocused = Functions.loadIcon(Constants.ICON_REMOVE_UNFOCUSED_10);

		init(title);
	}

	public void init(String title) {
		{
			setOpaque(false);
			setLayout(new GridBagLayout());
		}
		{
			{
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;

				lblTitle = new WebLabel(title);
				add(lblTitle, gbc);
			}
			{
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 1;
				gbc.gridy = 0;
				gbc.weightx = 0;

				btnClose = new WebButton(unfocused);
				btnClose.setRolloverDecoratedOnly(true);
				btnClose.setDrawFocus(false);
				btnClose.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (Boolean.valueOf(GUIProperties.getAppShowDel())) {
							int confirmation = JOptionPane.showConfirmDialog(parent, Constants.TABBED_DELETE_TEXT,
									Constants.TABBED_DELETE_TITLE, JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE);

							if (confirmation == JOptionPane.OK_OPTION) {
								parent.removeFromMapAndFromTab(uniqueName);
							}
						} else {
							parent.removeFromMapAndFromTab(uniqueName);
						}
					}
				});
				btnClose.getModel().addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						ButtonModel model = (ButtonModel) e.getSource();
						if (model.isRollover()) {
							btnClose.setIcon(focused);
						} else {
							btnClose.setIcon(unfocused);
						}
					}
				});

				add(btnClose, gbc);
			}
		}
	}

	public void setTitle(String title) {
		lblTitle.setText(title);
		lblTitle.revalidate();
	}

	public String getTitle() {
		return lblTitle.getText();
	}
}