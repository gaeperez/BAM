package es.uvigo.ei.sing.singulator.gui.custom.labels;

import javax.swing.ImageIcon;

import com.alee.laf.label.WebLabel;

public class CustomImageLabel extends WebLabel {
	private static final long serialVersionUID = 1L;

	private ImageIcon selectedImage;
	private ImageIcon unselectedImage;

	public CustomImageLabel(ImageIcon selectedImage, ImageIcon unselectedImage) {
		this.selectedImage = selectedImage;
		this.unselectedImage = unselectedImage;
	}

	public void changeImage(boolean isSelected) {
		if (isSelected)
			setIcon(selectedImage);
		else
			setIcon(unselectedImage);
	}
}
