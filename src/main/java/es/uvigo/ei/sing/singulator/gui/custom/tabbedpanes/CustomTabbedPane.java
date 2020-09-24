package es.uvigo.ei.sing.singulator.gui.custom.tabbedpanes;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.button.WebButton;
import com.alee.laf.tabbedpane.WebTabbedPane;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.gui.custom.validators.TabbedPaneValidator;

public class CustomTabbedPane extends WebTabbedPane {

	private static final long serialVersionUID = 1L;

	private Map<String, ButtonTabComponent> mapIdTab;
	private Map<String, String> mapIdName;

	private WebButton btnMore;

	private ImageIcon focused;
	private ImageIcon unfocused;

	public CustomTabbedPane() {
		this.mapIdTab = new HashMap<String, ButtonTabComponent>();
		this.mapIdName = new HashMap<String, String>();
		this.focused = Functions.loadIcon(Constants.ICON_ADD_FOCUSED_10);
		this.unfocused = Functions.loadIcon(Constants.ICON_ADD_UNFOCUSED_10);

		init();
	}

	public void init() {
		{
			setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			setInputVerifier(new TabbedPaneValidator(this));
			// Añadir primer tab con su contenido (sera vacio)
			addTab("+", new CustomAddPanel());
			// Substituir el primer tab por el botón con el +
			btnMore = new WebButton(unfocused);
			btnMore.setRolloverDecoratedOnly(true);
			btnMore.setDrawFocus(false);
			btnMore.getModel().addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					ButtonModel model = (ButtonModel) e.getSource();
					if (model.isRollover()) {
						btnMore.setIcon(focused);
					} else {
						btnMore.setIcon(unfocused);
					}
				}
			});

			setTabComponentAt(0, btnMore);
			setEnabledAt(0, false);
			setFocusable(false);
		}
	}

	public void setActionListener(ActionListener listener) {
		btnMore.addActionListener(listener);
	}

	public void putMapIdTab(String key, ButtonTabComponent value) {
		mapIdTab.put(key, value);
	}

	public void removeFromMapAndFromTab(String key) {
		remove(indexOfTab(key));

		mapIdTab.remove(key);
		mapIdName.remove(key);

		repaint();
		revalidate();
	}

	public void refreshMapLayerName(String newName) {
		int index = getSelectedIndex();
		String currentName = getTitleAt(index);
		// Coges el btc asociado
		ButtonTabComponent btc = mapIdTab.get(currentName);
		// Cambias el nombre
		btc.setTitle(newName);
		// Lo insertas actualizado
		mapIdTab.put(currentName, btc);
		mapIdName.put(currentName, newName);
	}

	public String getCurrentTabName() {
		return mapIdTab.get(getTitleAt(getSelectedIndex())).getTitle();
	}

	public void removelAllExceptButton() {
		int numberOfTabs = getTabCount() - 2;

		while (numberOfTabs >= 0) {
			removeTabAt(numberOfTabs);

			numberOfTabs--;
		}

		mapIdTab.clear();
		mapIdName.clear();
	}

	public Map<String, String> getMapIdName() {
		return mapIdName;
	}
}
