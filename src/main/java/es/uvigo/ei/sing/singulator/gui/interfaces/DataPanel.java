package es.uvigo.ei.sing.singulator.gui.interfaces;

import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public interface DataPanel {
	public String getPanelName();

	public Object[] getAllData();

	public boolean verifyAllData();

	public void loadData();

	public void updateComponentsData(JsonSingulator jsonSingulator);

	public void setJsonSingulator(JsonSingulator jsonSingulator);

	public void deleteAllData();
}
