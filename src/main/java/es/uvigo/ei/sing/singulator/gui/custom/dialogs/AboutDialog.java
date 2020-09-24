package es.uvigo.ei.sing.singulator.gui.custom.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import com.alee.extended.image.DisplayType;
import com.alee.extended.image.WebImage;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

public class AboutDialog extends WebDialog {
	private static final long serialVersionUID = 1L;

	public AboutDialog() {
		init();
	}

	public void init() {
		{
			setTitle(Constants.ABOUT_TITLE);
			setShadeWidth(0);
			setResizable(false);
			setModal(true);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(new BorderLayout());
		}
		{
			{
				WebImage logo = new WebImage(Functions.loadIcon(Constants.ICON_SPLASH));
				logo.setDisplayType(DisplayType.fitComponent);

				getContentPane().add(logo, BorderLayout.NORTH);
			}
			{
				WebPanel panelCenter = new WebPanel();
				panelCenter.setMargin(20, 0, 15, 0);
				panelCenter.setLayout(new BorderLayout());
				{
					{
						WebPanel panelWest = new WebPanel();
						panelWest.setLayout(new GridLayout(2, 0));
						panelWest.setBorder(new TitledBorder(Constants.ABOUT_INSTITUTIONS));
						{
							WebImage uvigo = new WebImage(Functions.loadIcon(Constants.ICON_UVIGO));
							uvigo.setDisplayType(DisplayType.fitComponent);
							panelWest.add(uvigo);

							WebImage sing = new WebImage(Functions.loadIcon(Constants.ICON_SING));
							sing.setDisplayType(DisplayType.fitComponent);
							panelWest.add(sing);
						}
						panelCenter.add(panelWest, BorderLayout.WEST);
					}
					{
						WebPanel panelEast = new WebPanel();
						panelEast.setLayout(new GridLayout(2, 0));
						{
							WebPanel panelE1 = new WebPanel();
							panelE1.setLayout(new GridLayout(0, 1));
							panelE1.setBorder(new TitledBorder(Constants.ABOUT_AUTHORS));
							{
								GroupPanel groupAuthor;
								WebLabel lblAuthor;
								WebLinkLabel lblLinkAuthor;
								{
									lblAuthor = new WebLabel(Constants.ABOUT_AITOR);
									lblLinkAuthor = new WebLinkLabel();
									lblLinkAuthor.setHighlightVisited(false);
									lblLinkAuthor.setEmailLink("(" + Constants.ABOUT_AITOR_MAIL + ")",
											Constants.ABOUT_AITOR_MAIL);
									lblLinkAuthor.setIcon(null);
									groupAuthor = new GroupPanel(3, lblAuthor, lblLinkAuthor);

									panelE1.add(groupAuthor);
								}
								{
									lblAuthor = new WebLabel(Constants.ABOUT_ANALIA);
									lblLinkAuthor = new WebLinkLabel();
									lblLinkAuthor.setHighlightVisited(false);
									lblLinkAuthor.setEmailLink("(" + Constants.ABOUT_ANALIA_MAIL + ")",
											Constants.ABOUT_ANALIA_MAIL);
									lblLinkAuthor.setIcon(null);
									groupAuthor = new GroupPanel(3, lblAuthor, lblLinkAuthor);

									panelE1.add(groupAuthor);
								}
								{
									lblAuthor = new WebLabel(Constants.ABOUT_FLORO);
									lblLinkAuthor = new WebLinkLabel();
									lblLinkAuthor.setHighlightVisited(false);
									lblLinkAuthor.setEmailLink("(" + Constants.ABOUT_FLORO_MAIL + ")",
											Constants.ABOUT_FLORO_MAIL);
									lblLinkAuthor.setIcon(null);
									groupAuthor = new GroupPanel(3, lblAuthor, lblLinkAuthor);

									panelE1.add(groupAuthor);
								}
								{
									lblAuthor = new WebLabel(Constants.ABOUT_GAEL);
									lblLinkAuthor = new WebLinkLabel();
									lblLinkAuthor.setHighlightVisited(false);
									lblLinkAuthor.setEmailLink("(" + Constants.ABOUT_GAEL_MAIL + ")",
											Constants.ABOUT_GAEL_MAIL);
									lblLinkAuthor.setIcon(null);
									groupAuthor = new GroupPanel(3, lblAuthor, lblLinkAuthor);

									panelE1.add(groupAuthor);
								}
								{
									lblAuthor = new WebLabel(Constants.ABOUT_MARTIN);
									lblLinkAuthor = new WebLinkLabel();
									lblLinkAuthor.setHighlightVisited(false);
									lblLinkAuthor.setEmailLink("(" + Constants.ABOUT_MARTIN_MAIL + ")",
											Constants.ABOUT_MARTIN_MAIL);
									lblLinkAuthor.setIcon(null);
									groupAuthor = new GroupPanel(3, lblAuthor, lblLinkAuthor);

									panelE1.add(groupAuthor);
								}
							}
							panelEast.add(panelE1);

							WebPanel panelE2 = new WebPanel();
							panelE2.setMargin(0, 0, 15, 0);
							panelE2.setLayout(new GridLayout(0, 1));
							panelE2.setBorder(new TitledBorder(Constants.ABOUT_DEPENDENCES));
							{
								GroupPanel groupDependance;
								WebLabel lblDependance;
								WebLinkLabel lblLinkDependance;
								{
									lblDependance = new WebLabel(Constants.ABOUT_GSON);
									lblLinkDependance = new WebLinkLabel();
									lblLinkDependance.setHighlightVisited(false);
									lblLinkDependance.setLink("(" + Constants.ABOUT_GSON_WEB + ")",
											Constants.ABOUT_GSON_WEB);
									lblLinkDependance.setIcon(null);
									groupDependance = new GroupPanel(3, lblDependance, lblLinkDependance);

									panelE2.add(groupDependance);
								}
								{
									lblDependance = new WebLabel(Constants.ABOUT_WEBLAF);
									lblLinkDependance = new WebLinkLabel();
									lblLinkDependance.setHighlightVisited(false);
									lblLinkDependance.setLink("(" + Constants.ABOUT_WEBLAF_WEB + ")",
											Constants.ABOUT_WEBLAF_WEB);
									lblLinkDependance.setIcon(null);
									groupDependance = new GroupPanel(3, lblDependance, lblLinkDependance);

									panelE2.add(groupDependance);
								}
							}
							panelEast.add(panelE2);
						}
						panelCenter.add(panelEast, BorderLayout.EAST);
					}
				}
				getContentPane().add(panelCenter, BorderLayout.CENTER);
			}
			{
				WebButton btnCancel = new WebButton(Constants.INIT_DIALOG_CANCEL);
				btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
				btnCancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});

				getContentPane().add(btnCancel, BorderLayout.SOUTH);
			}
		}
		pack();
	}
}
