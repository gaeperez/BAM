package es.uvigo.ei.sing.singulator.gui.custom.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.separator.WebSeparator;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

public class InitialDialog extends WebDialog {
	private static final long serialVersionUID = 1L;

	// 0: exit, 1: new, 2: load
	private int option = 0;

	public InitialDialog() {
		init();
	}

	private void init() {
		{
			setTitle(Constants.INIT_DIALOG_TITLE);
			setResizable(false);
			setModal(true);
			setShadeWidth(0);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			getContentPane().setLayout(new BorderLayout(0, 0));
		}
		{
			{
				WebPanel panelCenter = new WebPanel();
				panelCenter.setLayout(new BorderLayout());
				{
					WebPanel panelCWest = new WebPanel();
					panelCWest.setLayout(new GridLayout(2, 0));
					panelCWest.setBorder(new TitledBorder(Constants.INIT_DIALOG_TITLE_OPTIONS));
					{
						WebButton btnNew = new WebButton(Constants.INIT_DIALOG_NEW,
								Functions.loadIcon(Constants.ICON_NEW_16));
						btnNew.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								option = 1;
								dispose();
							}
						});
						panelCWest.add(btnNew);

						WebButton btnLoad = new WebButton(Constants.INIT_DIALOG_LOAD,
								Functions.loadIcon(Constants.ICON_LOAD_16));
						btnLoad.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								option = 2;
								dispose();
							}
						});
						panelCWest.add(btnLoad);
					}
					panelCenter.add(panelCWest, BorderLayout.WEST);
				}
				{
					WebPanel panelCEast = new WebPanel();
					panelCEast.setLayout(new GridLayout(2, 0));
					{
						WebPanel panelCEWelcome = new WebPanel();
						panelCEWelcome.setLayout(new GridLayout());
						panelCEWelcome.setBorder(new TitledBorder(Constants.INIT_DIALOG_TITLE_WELCOME));
						{
							panelCEWelcome.add(new WebLabel(Constants.INIT_DIALOG_WELCOME));
						}
						panelCEast.add(panelCEWelcome);
					}
					{
						WebPanel panelCENews = new WebPanel();
						panelCENews.setBorder(new TitledBorder(Constants.INIT_DIALOG_TITLE_NEWS));
						panelCENews.setLayout(new BoxLayout(panelCENews, BoxLayout.Y_AXIS));
						{
							panelCENews.add(new WebLabel(Constants.INIT_DIALOG_NEWS_1));
							WebSeparator webSeparator = new WebSeparator(false, true);
							panelCENews.add(webSeparator);
							panelCENews.add(new WebLabel(Constants.INIT_DIALOG_NEWS_2));
						}
						panelCEast.add(panelCENews);
					}
					panelCenter.add(panelCEast, BorderLayout.EAST);
				}
				getContentPane().add(panelCenter, BorderLayout.CENTER);
			}
			{
				WebPanel panelSouth = new WebPanel();
				panelSouth.setMargin(20, 0, 0, 0);
				{
					WebButton btnCancel = new WebButton(Constants.INIT_DIALOG_CANCEL);
					btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
					btnCancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							option = 0;
							dispose();
						}
					});
					panelSouth.setLayout(new GridLayout(0, 1, 0, 0));

					panelSouth.add(btnCancel);
				}
				getContentPane().add(panelSouth, BorderLayout.SOUTH);
			}
		}
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public int getPressed() {
		return option;
	}
}
