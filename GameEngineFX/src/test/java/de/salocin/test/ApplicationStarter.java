package de.salocin.test;

import de.salocin.gameenginefx.core.ApplicationGameStarter;
import de.salocin.gameenginefx.plugin.CorePlugin;
import de.salocin.gameenginefx.plugin.PluginManager;

public class ApplicationStarter extends ApplicationGameStarter {
	
	public static void main(String[] args) {
		PluginManager.init(new ApplicationStarter());
	}
	
	@Override
	public Class<? extends CorePlugin> getCorePluginClass() {
		return TestGame.class;
	}
	
}
