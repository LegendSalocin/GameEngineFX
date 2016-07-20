package de.salocin.gameenginefx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.salocin.gameenginefx.core.ApplicationRepresenter;
import de.salocin.gameenginefx.core.Display;
import de.salocin.gameenginefx.core.GameStarter.Type;
import de.salocin.gameenginefx.plugin.PluginManager;
import de.salocin.gameenginefx.render.RenderState;
import de.salocin.gameenginefx.util.StartArguments;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Scheduler {
	
	public static final Thread APPLICATION_THREAD = new Thread() {
		@Override
		public void run() {
			if (PluginManager.getInstance().getStarter().getType() == Type.APPLICATION) {
				Application.launch(ApplicationRepresenter.class, PluginManager.getCorePlugin().getStartArguments());
			}
		}
	};
	private static final Scheduler instance = new Scheduler();
	private final AnimationTimer loop;
	private final List<Runnable> queue = Collections.synchronizedList(new ArrayList<Runnable>());
	private Canvas canvas;
	private GraphicsContext gc;
	private RenderState state;
	
	private Scheduler() {
		loop = new AnimationTimer() {
			private long last;
			private long fpsDetectStart;
			private int fpsCount;
			private int fps;
			
			@Override
			public void handle(long now) {
				if (Display.isInitialized()) {
					long delta = now - last;
					last = now;
					
					countFps(now);
					
					if (state != null) {
						if (StartArguments.getBoolean(StartArguments.CLEAR_EACH_FRAME)) {
							gc.setFill(Color.BLACK);
							gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
						}
						
						state.update(canvas, delta, fps);
						state.render(canvas, gc);
					}
					
					ArrayList<Runnable> queue = new ArrayList<Runnable>();
					
					synchronized (Scheduler.this.queue) {
						queue.addAll(Scheduler.this.queue);
						Scheduler.this.queue.clear();
					}
					
					Runnable runnable;
					
					for (Iterator<Runnable> it = queue.iterator(); it.hasNext();) {
						runnable = it.next();
						
						if (runnable != null) {
							runnable.run();
						}
					}
					
					queue.clear();
					queue = null;
				}
			}
			
			private void countFps(long now) {
				if (fpsDetectStart == 0) {
					fpsDetectStart = now;
				}
				
				if (now - fpsDetectStart >= Math.pow(10, 9)) {
					fpsDetectStart = now;
					fps = fpsCount;
					fpsCount = 0;
				} else {
					fpsCount++;
				}
			}
		};
		
		loop.start();
	}
	
	public static Scheduler getInstance() {
		return instance;
	}
	
	public void runLater(Runnable run) {
		synchronized (queue) {
			queue.add(run);
		}
	}
	
	public void stopLoop() {
		loop.stop();
	}
	
	public void shutdown(int status) {
		loop.stop();
		Platform.exit();
		System.exit(status);
	}
	
	public void setState(RenderState state) {
		Scheduler.this.canvas = Display.getInstance().getCanvas();
		Scheduler.this.gc = canvas.getGraphicsContext2D();
		Scheduler.this.state = state;
		Scheduler.this.state.init(Display.getInstance().getCanvas());
		
		// Clearing old state
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	public RenderState getState() {
		return state;
	}
	
	public GraphicsContext getCurrentGraphicsContext() {
		return gc;
	}
	
}
