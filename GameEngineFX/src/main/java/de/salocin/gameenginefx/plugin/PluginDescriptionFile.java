package de.salocin.gameenginefx.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PluginDescriptionFile {
	
	private String mainClassName;
	private Class<?> mainClass;
	private String pluginName;
	private String pluginVersion;
	
	private PluginDescriptionFile() {
	}
	
	public static PluginDescriptionFile load(File file) throws IOException {
		return load(new FileInputStream(file));
	}
	
	public static PluginDescriptionFile load(InputStream in) throws IOException {
		PluginDescriptionFile pdf = new PluginDescriptionFile();
		Properties properties = new Properties();
		properties.load(in);
		
		pdf.mainClassName = properties.getProperty("main");
		
		try {
			pdf.mainClass = Class.forName(pdf.mainClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			pdf.mainClass = null;
		}
		
		pdf.pluginName = properties.getProperty("name");
		pdf.pluginVersion = properties.getProperty("version");
		return pdf;
	}
	
	public Class<?> getMainClass() {
		return mainClass;
	}
	
	public String getMainClassName() {
		return mainClassName;
	}
	
	public String getPluginName() {
		return pluginName;
	}
	
	public String getPluginVersion() {
		return pluginVersion;
	}
	
}
