package de.salocin.gameenginefx.core;

import de.salocin.gameenginefx.core.GameStarter.Type;
import de.salocin.gameenginefx.plugin.PluginManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationRepresenter extends Application {
	
	private static ApplicationRepresenter instance;
	private Stage stage;
	
	public ApplicationRepresenter() {
		if (PluginManager.getInstance().getStarter().getType() == Type.APPLICATION) {
			instance = this;
		}
	}
	
	public static ApplicationRepresenter getInstance() {
		return instance;
	}
	
	public static boolean isInitialized() {
		return instance != null && instance.stage != null;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
	}
	
	public Stage getStage() {
		return stage;
	}
	
}
