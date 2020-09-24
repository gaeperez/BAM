package es.uvigo.ei.sing.singulator.model;

import java.io.Serializable;

public class JsonEnvironment implements Serializable {

	private static final long serialVersionUID = 1L;

	private double width;
	private double height;
	private double lenght;

	public JsonEnvironment() {

	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getLenght() {
		return lenght;
	}

	public void setLenght(double lenght) {
		this.lenght = lenght;
	}
}
