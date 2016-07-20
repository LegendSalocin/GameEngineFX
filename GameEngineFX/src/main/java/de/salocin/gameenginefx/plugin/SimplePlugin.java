package de.salocin.gameenginefx.plugin;

public abstract class SimplePlugin implements Plugin {
	
	protected PluginDescriptionFile description;
	protected boolean enabled;
	
	@Override
	public String getName() {
		return description.getPluginName();
	}
	
	@Override
	public String getVersion() {
		return description.getPluginVersion();
	}
	
	@Override
	public PluginDescriptionFile getDescription() {
		return description;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public void enable() {
		if (enabled == true) {
			return;
		}
		
		enabled = true;
		onEnable();
	}
	
	protected abstract void onEnable();
	
	@Override
	public void disable() {
		if (enabled == false) {
			return;
		}
		
		enabled = false;
		onDisable();
	}
	
	protected abstract void onDisable();
	
}
