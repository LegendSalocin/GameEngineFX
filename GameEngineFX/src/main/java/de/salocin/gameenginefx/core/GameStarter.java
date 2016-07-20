package de.salocin.gameenginefx.core;

import de.salocin.gameenginefx.plugin.CorePlugin;

public interface GameStarter {
	
	Class<? extends CorePlugin> getCorePluginClass();
	
	Type getType();
	
	public enum Type {
		APPLET, APPLICATION,
		/** Not supported yet */
		ANDROID_APP;
		
		private String description;
		
		public void setDescription(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return description;
		}
	}
	
}
