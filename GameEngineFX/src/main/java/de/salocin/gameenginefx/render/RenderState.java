package de.salocin.gameenginefx.render;

import de.salocin.gameenginefx.handler.ControllerHandler;
import de.salocin.gameenginefx.handler.KeyHandler;
import de.salocin.gameenginefx.handler.MouseHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public interface RenderState extends MouseHandler, KeyHandler, ControllerHandler {
	
	void init(Canvas canvas);
	
	void update(Canvas canvas, long delta, long fps);
	
	void render(Canvas canvas, GraphicsContext gc);
	
}
