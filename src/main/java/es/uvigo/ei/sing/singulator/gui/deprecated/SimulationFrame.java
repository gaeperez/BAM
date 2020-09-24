package es.uvigo.ei.sing.singulator.gui.deprecated;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.WindowConstants;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

@Deprecated
public class SimulationFrame extends WebFrame {

	private static final long serialVersionUID = 1L;

	private CardLayout cardLayout;
	private WebPanel panelCenter;

	private int maxCardNumber;
	private int pointer;

	private Map<Integer, SimulationInfoPanel> mapIdSimPanel;

	private SimulationInfoPanel sip;

	public SimulationFrame() {
		this.pointer = 0;
		this.maxCardNumber = 0;

		this.mapIdSimPanel = new HashMap<Integer, SimulationInfoPanel>();

		init();
		pack();
	}

	public void init() {
		{
			setTitle("SINGulator GUI");
			setIconImages(WebLookAndFeel.getImages());
			setLocationRelativeTo(null);
			setMinimumSize(new Dimension(800, 600));
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		}
		{
			panelCenter = new WebPanel();

			cardLayout = new CardLayout();
			cardLayout.setVgap(5);
			cardLayout.setHgap(5);
			panelCenter.setLayout(cardLayout);

			introduceCards();

			getContentPane().add(panelCenter, BorderLayout.CENTER);
		}
		{
			WebButton btnWest = new WebButton(Functions.loadIcon("/imgs/16/a.png"));
			btnWest.setDrawFocus(false);
			btnWest.setDrawShade(false);
			btnWest.setDrawSides(false, false, false, false);
			btnWest.setBottomBgColor(panelCenter.getBackground());
			btnWest.setTopBgColor(panelCenter.getBackground());
			btnWest.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pointer--;

					if (pointer < 0) {
						pointer = maxCardNumber - 1;
					}

					cardLayout.show(panelCenter, String.valueOf(pointer));

					sip.play();
				}
			});

			getContentPane().add(btnWest, BorderLayout.WEST);
		}
		{
			WebButton btnEast = new WebButton(Functions.loadIcon("/imgs/16/a.png"));
			btnEast.setDrawFocus(false);
			btnEast.setDrawShade(false);
			btnEast.setDrawSides(false, false, false, false);
			btnEast.setBottomBgColor(panelCenter.getBackground());
			btnEast.setTopBgColor(panelCenter.getBackground());
			btnEast.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pointer++;

					if (pointer >= maxCardNumber) {
						pointer = 0;
					}

					cardLayout.show(panelCenter, String.valueOf(pointer));
				}
			});

			getContentPane().add(btnEast, BorderLayout.EAST);
		}
		{
			WebPanel panelButton = new WebPanel();

			getContentPane().add(panelButton, BorderLayout.SOUTH);
		}
	}

	private void introduceCards() {
		sip = new SimulationInfoPanel("C:\\Users\\SING - XPS\\Videos\\Untitled.mp4", Constants.CELLS_ADD_LAYER);
		panelCenter.add(sip, "0");
		mapIdSimPanel.put(0, sip);

		// sip = new SimulationInfoPanel("C:\\Users\\SING -
		// XPS\\Videos\\Untitled.mp4", Constants.CELLS_CAPSULE);
		// panelCenter.add(sip, "1");
		// mapIdSimPanel.put(1, sip);
		//
		// sip = new SimulationInfoPanel("C:\\Users\\SING -
		// XPS\\Videos\\Untitled.mp4", Constants.CELLS_COLOR);
		// panelCenter.add(sip, "2");
		// mapIdSimPanel.put(2, sip);

		maxCardNumber = mapIdSimPanel.size();

		cardLayout.show(panelCenter, String.valueOf(pointer));
	}

	public void play() {
		sip.play();
	}
}
