package es.uvigo.ei.sing.singulator.gui.custom.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.WindowConstants;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.alee.laf.rootpane.WebDialog;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;

public class JsonVisualizer extends WebDialog {
	private static final long serialVersionUID = 1L;

	private RSyntaxTextArea textArea;

	public JsonVisualizer() {
		init();
	}

	public void init() {
		{
			setTitle(Constants.VISUALIZER_TITLE);
			setModal(true);
			setResizable(true);
			setShadeWidth(0);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setLayout(new BorderLayout());
		}
		{
			textArea = new RSyntaxTextArea();
			textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
			textArea.setCodeFoldingEnabled(true);
			textArea.setAnimateBracketMatching(true);
			textArea.setAutoIndentEnabled(true);
			textArea.setBracketMatchingEnabled(true);
			textArea.setEditable(false);

			RTextScrollPane sp = new RTextScrollPane(textArea);
			sp.setPreferredSize(new Dimension(600, 600));

			add(sp, BorderLayout.CENTER);
		}
		pack();
	}

	public void setText(String text) {
		textArea.setText(text);
	}
}
