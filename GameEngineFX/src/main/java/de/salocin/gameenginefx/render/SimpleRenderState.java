package de.salocin.gameenginefx.render;

import de.salocin.gameenginefx.handler.HandlerManager;

public abstract class SimpleRenderState implements RenderState {
	
	public SimpleRenderState() {
		HandlerManager mgr = HandlerManager.getInstance();
		
		mgr.addMouseHandler(this, this);
		mgr.addKeyHandler(this, this);
		mgr.addControllerHandler(this, this);
	}
	
}
