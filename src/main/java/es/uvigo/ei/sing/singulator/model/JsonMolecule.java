package es.uvigo.ei.sing.singulator.model;

import java.io.Serializable;

public class JsonMolecule implements Serializable {

	private static final long serialVersionUID = 1L;

	private JsonMacromolecule[] macromolecules;
	private JsonBuildingblock[] buildingBlocks;
	private JsonLzw[] lzw;

	public JsonMolecule() {

	}

	public JsonMacromolecule[] getMacromolecules() {
		return macromolecules;
	}

	public void setMacromolecules(JsonMacromolecule[] macromolecules) {
		this.macromolecules = macromolecules;
	}

	public JsonBuildingblock[] getBuildingBlocks() {
		return buildingBlocks;
	}

	public void setBuildingBlocks(JsonBuildingblock[] buildingBlocks) {
		this.buildingBlocks = buildingBlocks;
	}

	public JsonLzw[] getLzw() {
		return lzw;
	}

	public void setLzw(JsonLzw[] lzw) {
		this.lzw = lzw;
	}
}
