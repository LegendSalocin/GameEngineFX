package de.salocin.gameenginefx.core;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

public abstract class GameApplet extends JApplet {
	
	private static final long serialVersionUID = 1L;
	
	private JFXPanel panel;
	
	@Override
	public void init() {
		setIgnoreRepaint(true);
		SwingUtilities.invokeLater(() -> {
			panel = new JFXPanel();
			add(panel);
		});
	}
	
	public void setScene(Scene scene) {
		Platform.runLater(() -> {
			panel.setScene(scene);
		});
	}
	
	public Scene getScene() {
		return panel.getScene();
	}
	
}
