package de.salocin.gameenginefx.handler;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.salocin.gameenginefx.Scheduler;
import de.salocin.gameenginefx.core.Display;
import de.salocin.gameenginefx.render.RenderState;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class HandlerManager {
	
	private static final HandlerManager instance = new HandlerManager();
	private final Map<KeyHandler, RenderState> keyHandler = Collections.synchronizedMap(new HashMap<KeyHandler, RenderState>());
	private final Map<MouseHandler, RenderState> mouseHandler = Collections.synchronizedMap(new HashMap<MouseHandler, RenderState>());
	private final Map<ControllerHandler, RenderState> controllerHandler = Collections.synchronizedMap(new HashMap<ControllerHandler, RenderState>());
	private Point2D mousePos = new Point2D(0, 0);
	
	private HandlerManager() {
		if (!Display.isInitialized()) {
			throw new RuntimeException("Display not initialized!");
		}
		
		final Scene scene = Display.getInstance().getScene();
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			Scheduler.getInstance().runLater(() -> {
				Iterator<Entry<KeyHandler, RenderState>> it = keyHandler.entrySet().iterator();
				
				while (it.hasNext()) {
					Entry<KeyHandler, RenderState> entry = it.next();
					
					if (entry.getValue() == null || entry.getValue().equals(Scheduler.getInstance().getState())) {
						entry.getKey().keyPressed(e);
					}
				}
			});
		});
		
		scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
			Scheduler.getInstance().runLater(() -> {
				Iterator<Entry<KeyHandler, RenderState>> it = keyHandler.entrySet().iterator();
				
				while (it.hasNext()) {
					Entry<KeyHandler, RenderState> entry = it.next();
					
					if (entry.getValue() == null || entry.getValue().equals(Scheduler.getInstance().getState())) {
						entry.getKey().keyReleased(e);
					}
				}
			});
		});
		
		scene.addEventHandler(KeyEvent.KEY_TYPED, e -> {
			Scheduler.getInstance().runLater(() -> {
				Iterator<Entry<KeyHandler, RenderState>> it = keyHandler.entrySet().iterator();
				
				while (it.hasNext()) {
					Entry<KeyHandler, RenderState> entry = it.next();
					
					if (entry.getValue() == null || entry.getValue().equals(Scheduler.getInstance().getState())) {
						entry.getKey().keyTyped(e);
					}
				}
			});
		});
		
		scene.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
			Scheduler.getInstance().runLater(() -> {
				Iterator<Entry<MouseHandler, RenderState>> it = mouseHandler.entrySet().iterator();
				
				while (it.hasNext()) {
					Entry<MouseHandler, RenderState> entry = it.next();
					
					if (entry.getValue() == null || entry.getValue().equals(Scheduler.getInstance().getState())) {
						entry.getKey().mouseMoved(e);
					}
				}
			});
		});
		
		scene.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			Scheduler.getInstance().runLater(() -> {
				Iterator<Entry<MouseHandler, RenderState>> it = mouseHandler.entrySet().iterator();
				
				while (it.hasNext()) {
					Entry<MouseHandler, RenderState> entry = it.next();
					
					if (entry.getValue() == null || entry.getValue().equals(Scheduler.getInstance().getState())) {
						entry.getKey().mousePressed(e);
					}
				}
			});
		});
		
		scene.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
			try {
				Scheduler.getInstance().runLater(() -> {
					Iterator<Entry<MouseHandler, RenderState>> it = mouseHandler.entrySet().iterator();
					
					while (it.hasNext()) {
						Entry<MouseHandler, RenderState> entry = it.next();
						
						if (entry.getValue() == null || entry.getValue().equals(Scheduler.getInstance().getState())) {
							entry.getKey().mouseReleased(e);
						}
					}
				});
			} catch (ConcurrentModificationException exc) {
				System.exit(0);
			}
		});
	}
	
	public static HandlerManager getInstance() {
		return instance;
	}
	
	public synchronized Point2D getMousePosition() {
		return mousePos;
	}
	
	public synchronized void addKeyHandler(KeyHandler handler) {
		addKeyHandler(handler, null);
	}
	
	public synchronized void addKeyHandler(KeyHandler handler, RenderState state) {
		Scheduler.getInstance().runLater(() -> {
			keyHandler.put(handler, state);
		});
	}
	
	public synchronized boolean isKeyHandlerRegistered(KeyHandler handler) {
		return keyHandler.containsKey(handler);
	}
	
	public synchronized Set<KeyHandler> getKeyHandler() {
		return keyHandler.keySet();
	}
	
	public void addMouseHandler(MouseHandler handler) {
		addMouseHandler(handler, null);
	}
	
	public void addMouseHandler(MouseHandler handler, RenderState state) {
		Scheduler.getInstance().runLater(() -> {
			mouseHandler.put(handler, state);
		});
	}
	
	public synchronized boolean isMouseHandlerRegistered(KeyHandler handler) {
		return mouseHandler.containsKey(handler);
	}
	
	public synchronized Set<MouseHandler> getMouseHandler() {
		return mouseHandler.keySet();
	}
	
	public synchronized void addControllerHandler(ControllerHandler handler) {
		addControllerHandler(handler, null);
	}
	
	public synchronized void addControllerHandler(ControllerHandler handler, RenderState state) {
		Scheduler.getInstance().runLater(() -> {
			controllerHandler.put(handler, state);
		});
	}
	
	public synchronized boolean isControllerHandlerRegistered(KeyHandler handler) {
		return controllerHandler.containsKey(handler);
	}
	
	public synchronized Set<ControllerHandler> getControllerHandler() {
		return controllerHandler.keySet();
	}
	
	// public synchronized ControllerManager getControllerManager() {
	// return controllerManager;
	// }
	
}
