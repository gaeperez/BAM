package es.uvigo.ei.sing.singulator.gui.constant;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Properties;

public class GUIProperties {

	private static Properties properties;

	static {
		properties = new Properties();
		try {
			InputStream is = GUIProperties.class.getClassLoader().getResourceAsStream(Constants.PROPERTIES);
			properties.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void store() {
		OutputStream out;
		try {
			// The decode is needed to avoid %20 in paths with spaces
			out = new FileOutputStream(URLDecoder
					.decode(GUIProperties.class.getClassLoader().getResource(Constants.PROPERTIES).getFile(), "UTF-8"));
			properties.store(out, "");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getAppName() {
		return properties.getProperty(Constants.PROPERTIES_NAME);
	}

	public static void setAppName(String value) {
		properties.setProperty(Constants.PROPERTIES_NAME, value);
	}

	public static String getAppVersion() {
		return properties.getProperty(Constants.PROPERTIES_VERSION);
	}

	public static void setAppVersion(String value) {
		properties.setProperty(Constants.PROPERTIES_VERSION, value);
	}

	public static String getAppPackage() {
		return properties.getProperty(Constants.PROPERTIES_PACKAGE);
	}

	public static void setAppPackage(String value) {
		properties.setProperty(Constants.PROPERTIES_PACKAGE, value);
	}

	public static String getAppOutput() {
		return properties.getProperty(Constants.PROPERTIES_OUTPUT);
	}

	public static void setAppOutput(String value) {
		properties.setProperty(Constants.PROPERTIES_OUTPUT, value);
	}

	public static String getAppShowDialog() {
		return properties.getProperty(Constants.PROPERTIES_SHOW_DIALOG);
	}

	public static void setAppShowDialog(String value) {
		properties.setProperty(Constants.PROPERTIES_SHOW_DIALOG, value);
	}

	public static String getAppShowDel() {
		return properties.getProperty(Constants.PROPERTIES_SHOW_DELETE);
	}

	public static void setAppShowDel(String value) {
		properties.setProperty(Constants.PROPERTIES_SHOW_DELETE, value);
	}

	public static String getAppJson() {
		return properties.getProperty(Constants.PROPERTIES_JSON);
	}

	public static void setAppShowJson(String value) {
		properties.setProperty(Constants.PROPERTIES_JSON, value);
	}

	public static String getAppJavaS() {
		return properties.getProperty(Constants.PROPERTIES_JAVA_SIMPLE);
	}

	public static void setAppShowJavaS(String value) {
		properties.setProperty(Constants.PROPERTIES_JAVA_SIMPLE, value);
	}

	public static String getAppJavaA() {
		return properties.getProperty(Constants.PROPERTIES_JAVA_ADVANCED);
	}

	public static void setAppShowJavaA(String value) {
		properties.setProperty(Constants.PROPERTIES_JAVA_ADVANCED, value);
	}
}
