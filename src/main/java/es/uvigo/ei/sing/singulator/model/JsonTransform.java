package es.uvigo.ei.sing.singulator.model;

import java.io.Serializable;

public class JsonTransform implements Serializable {

	private static final long serialVersionUID = 1L;

	private String from;
	private String to;
	private String onInnerWith;
	private String onReboundWith;

	public JsonTransform() {

	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
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
