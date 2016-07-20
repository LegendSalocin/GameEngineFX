package de.salocin.test;

import de.salocin.gameenginefx.gui.Button;
import de.salocin.gameenginefx.gui.layout.RelativeData;
import de.salocin.gameenginefx.render.MenuRenderState;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class TestState extends MenuRenderState {
	
	@Override
	public void init(Canvas canvas) {
		Button b = new Button("Test");
		b.setBackgroundColor(Color.RED);
		addComponent(b, new RelativeData().top(50, -50).left(50, -100).width(200).height(100));
		
		b.setListener(() -> {
			b.setBackgroundColor(Color.rgb(randomColor(), randomColor(), randomColor()));
		});
	}
	
	private int randomColor() {
		return (int) (Math.random() * 255.0f);
	}
	
}
