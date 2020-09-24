package es.uvigo.ei.sing.singulator.model;

import java.io.Serializable;

public class JsonAgent implements Serializable {
	private static final long serialVersionUID = 1L;

	private JsonRibosome[] ribosomes;
	private JsonMolecule molecules;

	public JsonAgent() {

	}

	public JsonRibosome[] getRibosomes() {
		return ribosomes;
	}

	public void setRibosomes(JsonRibosome[] ribosomes) {
		this.ribosomes = ribosomes;
	}

	public JsonMolecule getMolecules() {
		return molecules;
	}

	public void setMolecules(JsonMolecule molecules) {
		this.molecules = molecules;
	}
}
