package de.salocin.gameenginefx.gui;

import java.util.HashSet;

import de.salocin.gameenginefx.gui.layout.LayoutData;
import de.salocin.gameenginefx.handler.HandlerManager;
import de.salocin.gameenginefx.handler.MouseHandler;
import de.salocin.gameenginefx.render.RenderState;
import javafx.scene.input.MouseEvent;

public class Menu {
	
	private HashSet<Component> components = new HashSet<Component>();
	
	public Menu(RenderState state) {
		HandlerManager.getInstance().addMouseHandler(new MouseHandler() {
			@Override
			public void mouseMoved(MouseEvent e) {
				for (Component component : components) {
					if (component.getBounds().contains(e.getX(), e.getY())) {
						component.setHover(true);
					} else {
						component.setHover(false);
					}
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				for (Component component : components) {
					if (component.getBounds().contains(e.getX(), e.getY())) {
						component.simulateClick();
					}
				}
			}
		}, state);
	}
	
	public HashSet<Component> getComponents() {
		return components;
	}
	
	public void add(Component component) {
		add(component, component.getLayoutData());
	}
	
	public void add(Component component, LayoutData layoutData) {
		component.setLayoutData(layoutData);
		components.add(component);
		component.pack();
	}
	
	public void pack() {
		for (Component component : components) {
			component.pack();
		}
	}
}
