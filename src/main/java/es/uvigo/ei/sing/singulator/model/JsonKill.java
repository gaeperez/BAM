package es.uvigo.ei.sing.singulator.model;

import java.io.Serializable;

public class JsonKill implements Serializable {

	private static final long serialVersionUID = 1L;

	private String input;
	private String onInnerWith;
	private String onReboundWith;

	public JsonKill() {

	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOnInnerWith() {
		return onInnerWith;
	}

	public void setOnInnerWith(String onInnerWith) {
		this.onInnerWith = onInnerWith;
	}

	public String getOnReboundWith() {
		return onReboundWith;
	}

	public void setOnReboundWith(String onReboundWith) {
		this.onReboundWith = onReboundWith;
	}
}
