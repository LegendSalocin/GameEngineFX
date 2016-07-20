package de.salocin.test;

import de.salocin.gameenginefx.core.AppletGameStarter;
import de.salocin.gameenginefx.plugin.CorePlugin;

public class AppletStarter extends AppletGameStarter {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public Class<? extends CorePlugin> getCorePluginClass() {
		return TestGame.class;
	}
	
}
