package es.uvigo.ei.sing.singulator.gui.main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.bars.WebCpuBar;
import es.uvigo.ei.sing.singulator.gui.custom.bars.WebMemoryBar;
import es.uvigo.ei.sing.singulator.gui.custom.dialogs.FinishDialog;

public class MainButtonsPanel extends WebPanel {

	private static final long serialVersionUID = 1L;

	private WebButton btnNextPage;

	private ImageIcon next;
	private ImageIcon back;
	private ImageIcon finish;

	private WebLabel lblPointer;

	private MainPanel parent;

	public MainButtonsPanel(MainPanel parent) {
		this.parent = parent;

		this.next = Functions.loadIcon(Constants.ICON_NEXT_32);
		this.back = Functions.loadIcon(Constants.ICON_BACK_32);
		this.finish = Functions.loadIcon(Constants.ICON_APPROVE_32);

		init();
	}

	public void init() {
		{
			setLayout(new GridLayout(1, 2, 10, 0));
			setMargin(10, 5, 5, 5);
			setBackground(Color.white);
		}
		{
			WebPanel panelWest = new WebPanel();
			panelWest.setBackground(Color.WHITE);

			add(panelWest);
			panelWest.setLayout(new GridLayout(0, 1, 0, 0));
			{
				WebStatusBar wsb = new WebStatusBar();
				wsb.setLayout(new GridLayout(0, 2));
				wsb.setUndecorated(true);
				wsb.add(new WebCpuBar());
				wsb.add(new WebMemoryBar());
				panelWest.add(wsb);
			}
		}
		{
			WebPanel panelEast = new WebPanel();
			panelEast.setBackground(Color.WHITE);
			panelEast.setLayout(new GridLayout(0, 3));
			add(panelEast);
			{
				{
					WebButton btnPrevPage = new WebButton(back);
					btnPrevPage.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							parent.showPreviousCard();
						}
					});
					panelEast.add(btnPrevPage);
				}
				{
					lblPointer = new WebLabel(Constants.GENERAL_TITLE);
					lblPointer.setHorizontalAlignment(SwingConstants.CENTER);

					panelEast.add(lblPointer);
				}
				{
					btnNextPage = new WebButton(next);
					btnNextPage.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (btnNextPage.getIcon() == next) {
								parent.showNextCard();
							} else {
								String errors = parent.validateAllCards();
								// No errors on panels
								if (errors.equals(Constants.TOOLBAR_VALIDATE_ALL)) {
									FinishDialog finishDialog = new FinishDialog(parent);
									if (finishDialog.isFinish()) {
										boolean doSomething = parent.finish(finishDialog.getOutput(),
												finishDialog.getPackage(), finishDialog.getCheckBoxes());

										if (doSomething)
											Functions.showTimeNotification(parent,
													Functions.loadIcon(Constants.ICON_APPROVE_16), 5000,
													Constants.FINISH_NOTIFICATION_OK);
										else
											Functions.showTimeNotification(parent,
													Functions.loadIcon(Constants.ICON_REMOVE_16), 5000,
													Constants.FINISH_NOTIFICATION_NOTHING);
									}
								} else {
									Functions.showErrorDialog(parent, Constants.TOOLBAR_VALIDATE_TITLE, errors);
								}
							}
						}
					});
					panelEast.add(btnNextPage);
				}
			}
		}
	}

	public void setLabelText(String text) {
		lblPointer.setText(text);
	}

	public void changeToFinish(boolean toFinish) {
		if (toFinish)
			btnNextPage.setIcon(finish);
		else
			btnNextPage.setIcon(next);
	}
}
