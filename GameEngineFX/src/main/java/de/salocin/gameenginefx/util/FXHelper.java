package de.salocin.gameenginefx.util;

import java.util.HashMap;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;

import javafx.geometry.Dimension2D;
import javafx.scene.text.Font;

public class FXHelper {
	
	private static final HashMap<Font, FontMetrics> cache = new HashMap<Font, FontMetrics>();
	
	public static Dimension2D getStringSize(Font font, String string) {
		FontMetrics metrics = getCache(font);
		return new Dimension2D(metrics.computeStringWidth(string), metrics.getLineHeight());
	}
	
	public static double getStringWidth(Font font, String string) {
		return getCache(font).computeStringWidth(string);
	}
	
	public static double getStringHeight(Font font) {
		return font.getSize();
	}
	
	private static FontMetrics getCache(Font font) {
		FontMetrics metrics;
		if (cache.containsKey(font)) {
			metrics = cache.get(font);
		} else {
			metrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
			cache.put(font, metrics);
		}
		
		return metrics;
	}
	
}