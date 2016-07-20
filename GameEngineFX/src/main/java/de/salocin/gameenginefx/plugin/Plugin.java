package de.salocin.gameenginefx.plugin;

public interface Plugin {
	
	String getName();
	
	String getVersion();
	
	PluginDescriptionFile getDescription();
	
	boolean isEnabled();
	
	void enable();
	
	void disable();
	
}
