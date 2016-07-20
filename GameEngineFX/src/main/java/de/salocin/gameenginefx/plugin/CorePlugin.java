package de.salocin.gameenginefx.plugin;

import de.salocin.gameenginefx.Scheduler;
import de.salocin.gameenginefx.core.AppletGameStarter;
import de.salocin.gameenginefx.core.ApplicationGameStarter;
import de.salocin.gameenginefx.core.ApplicationRepresenter;
import de.salocin.gameenginefx.core.Display;
import de.salocin.gameenginefx.core.GameStarter;
import de.salocin.gameenginefx.util.StartArguments;
import javafx.application.Platform;

public abstract class CorePlugin extends SimplePlugin implements Plugin {
	
	public Object applicationObject = new Object();
	private static CorePlugin instance;
	private String[] startArguments;
	
	protected CorePlugin() {
		instance = this;
	}
	
	protected CorePlugin(String[] startArguments) {
		instance = this;
		this.startArguments = startArguments == null ? new String[] { StartArguments.SCENE_WIDTH + "=800", StartArguments.SCENE_HEIGHT + "=800", StartArguments.CLEAR_EACH_FRAME + "=true" } : startArguments;
	}
	
	public static CorePlugin getInstance() {
		return instance;
	}
	
	public String[] getStartArguments() {
		return startArguments;
	}
	
	@Override
	public final boolean isEnabled() {
		return super.isEnabled();
	}
	
	@Override
	public final void enable() {
		if (isEnabled()) {
			throw new RuntimeException("CorePlugin already enabled.");
		}
		
		if (!Display.isInitialized()) {
			final GameStarter starter = PluginManager.getInstance().getStarter();
			
			try {
				switch (starter.getType()) {
				case APPLET:
					Display.initApplet((AppletGameStarter) starter);
					break;
				case APPLICATION:
					Scheduler.APPLICATION_THREAD.start();
					
					while (ApplicationRepresenter.isInitialized()) {
					}
					
					Display.initApplication((ApplicationGameStarter) starter);
					
					Platform.runLater(() -> {
						ApplicationRepresenter.getInstance().getStage().show();
					});
					
					break;
				case ANDROID_APP:
					throw new UnsupportedOperationException();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		super.enable();
	}
	
	@Override
	public final void disable() {
		disable(true);
	}
	
	public final void disable(boolean shutdownEngine) {
		for (Plugin plugin : PluginManager.getInstance().getPlugins()) {
			plugin.disable();
		}
		
		super.disable();
		
		if (shutdownEngine) {
			Scheduler.getInstance().shutdown(0);
		}
	}
	
}
