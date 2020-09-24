package es.uvigo.ei.sing.singulator.gui.interfaces;

import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public interface MoleculePanel {
	public void changeTabName(String text);

	public JsonSingulator getSingulator();

	public MoleculePanel getMoleculePanel();
}
