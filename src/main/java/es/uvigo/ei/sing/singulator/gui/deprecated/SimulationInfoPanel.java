package es.uvigo.ei.sing.singulator.gui.deprecated;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.alee.extended.image.DisplayType;
import com.alee.extended.image.WebImage;
import com.alee.extended.image.WebImageGallery;
import com.alee.laf.text.WebEditorPane;

import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

@Deprecated
public class SimulationInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private String textSimulation;
	@SuppressWarnings("unused")
	private String pathVideo;

	@SuppressWarnings("unused")
	private EmbeddedMediaPlayerComponent component;
	@SuppressWarnings("unused")
	private EmbeddedMediaPlayer player;

	private EmbeddedMediaPlayer mediaPlayer;

	public SimulationInfoPanel(String pathVideo, String textSimulation) {
		this.pathVideo = pathVideo;
		this.textSimulation = textSimulation;

		init();
	}

	private void init() {
		{
			this.setLayout(new BorderLayout());
		}
		{
			// component = new EmbeddedMediaPlayerComponent();
			//
			// /* Set the canvas */
			// Canvas c = new Canvas();
			// c.setBackground(Color.black);
			//
			// /* Add the canvas */
			// this.add(c, BorderLayout.CENTER);
			// revalidate();
			// repaint();
			//
			// MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
			// CanvasVideoSurface videoSurface =
			// mediaPlayerFactory.newVideoSurface(c);
			// mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
			// mediaPlayer.setVideoSurface(videoSurface);
			//
			// c.setVisible(true);

			WebImageGallery wig = new WebImageGallery();
			wig.setPreferredColumnCount(3);

			wig.addImage(Functions.loadIcon("/imgs/16/7.png"));
			wig.addImage(Functions.loadIcon("/imgs/16/7.png"));
			wig.addImage(Functions.loadIcon("/imgs/16/7.png"));
			wig.addImage(Functions.loadIcon("/imgs/16/7.png"));
			wig.addImage(Functions.loadIcon("/imgs/16/7.png"));

			// Filling image
			WebImage webImage3 = new WebImage(Functions.loadIcon("/imgs/16/7.png"));
			webImage3.setDisplayType(DisplayType.fitComponent);
			webImage3.setPreferredSize(this.getPreferredSize());
			// TooltipManager.setTooltip(webImage3, "Scaled to fit component
			// image (" + webImage3.getImageWidth() + "x"
			// + webImage3.getImageHeight() + " px)", TooltipWay.up);

			add(webImage3, BorderLayout.CENTER);
			add(wig.getView(false), BorderLayout.SOUTH);
		}
		{
			WebEditorPane editorInfo = new WebEditorPane("text/html", textSimulation);
			editorInfo.setEditable(false);
			editorInfo.setBackground(getBackground());
			// add(editorInfo, BorderLayout.SOUTH);
		}
	}

	public void play() {
		mediaPlayer.playMedia("C:\\Users\\SING - XPS\\Videos\\Untitled.mp4");
	}
}
