package es.uvigo.ei.sing.singulator.gui.constant;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.label.WebLabel;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;

import es.uvigo.ei.sing.singulator.model.JsonSingulator;

@SuppressWarnings("restriction")
public class Functions {

	public static double setMaxLabelLenght(double maxLabelLenght, double lenght) {
		if (maxLabelLenght < lenght)
			return lenght;
		else
			return maxLabelLenght;
	}

	public static int calculateLabelDifference(double maxLabelLenght, double lenght) {
		return (int) (maxLabelLenght - lenght);
	}

	public static String singulatorToJson(JsonSingulator singulator) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		return gson.toJson(singulator);
	}

	public static JsonSingulator jsonToSingulator(String json) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		return gson.fromJson(json, JsonSingulator.class);
	}

	public static ImageIcon loadIcon(String relativePath) {
		return new ImageIcon(Functions.class.getClassLoader().getResource(relativePath));
	}

	public static List<String> readFileInList(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String sCurrentLine;

		List<String> toRet = new ArrayList<String>();
		while ((sCurrentLine = reader.readLine()) != null) {
			toRet.add(sCurrentLine);

		}

		return toRet;
	}

	public static String readFileInString(InputStream stream) {
		String toRet = "";

		try {
			UTF8Reader ur = new UTF8Reader(stream);
			BufferedReader in = new BufferedReader(ur);

			String str;
			while ((str = in.readLine()) != null) {
				if (!str.isEmpty()) {
					toRet += str + "\n";
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return toRet;
	}

	public static String readFirstLine(InputStream stream) {
		String toRet = "";

		try {
			UTF8Reader ur = new UTF8Reader(stream);
			BufferedReader in = new BufferedReader(ur);

			String str;
			while ((str = in.readLine()) != null) {
				if (!str.isEmpty()) {
					toRet = str;
					break;
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return toRet;
	}

	public static void showTimeNotification(Component parent, ImageIcon icon, int time, String text) {
		final WebNotification notificationPopup = new WebNotification();

		notificationPopup.setIcon(icon);
		notificationPopup.setDisplayTime(time);
		notificationPopup.setContent(new GroupPanel(new WebLabel(text)));

		NotificationManager.showNotification(parent, notificationPopup);
	}

	public static void showErrorDialog(Component owner, String title, String message) {
		JOptionPane.showMessageDialog(owner, message, title, JOptionPane.ERROR_MESSAGE);
	}

	public static String printStackTraceInString(StackTraceElement[] elements) {
		String toRet = "";

		for (StackTraceElement element : elements) {
			toRet += element + "\n";
		}

		return toRet;
	}

	public static List<ImageIcon> getIconLogoImages() {
		List<ImageIcon> toRet = new ArrayList<ImageIcon>();

		toRet.add(Functions.loadIcon(Constants.ICON_LOGO_16));
		toRet.add(Functions.loadIcon(Constants.ICON_LOGO_24));
		toRet.add(Functions.loadIcon(Constants.ICON_LOGO_32));
		toRet.add(Functions.loadIcon(Constants.ICON_LOGO_64));
		toRet.add(Functions.loadIcon(Constants.ICON_LOGO_128));
		toRet.add(Functions.loadIcon(Constants.ICON_LOGO_256));
		toRet.add(Functions.loadIcon(Constants.ICON_LOGO_512));

		return toRet;
	}
}
