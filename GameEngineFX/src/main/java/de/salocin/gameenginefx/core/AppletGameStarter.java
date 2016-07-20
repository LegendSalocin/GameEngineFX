package de.salocin.gameenginefx.core;

import de.salocin.gameenginefx.plugin.PluginManager;

public abstract class AppletGameStarter extends GameApplet implements GameStarter {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void start() {
		PluginManager.init(this);
	}
	
	@Override
	public Type getType() {
		return Type.APPLET;
	}
	
}
