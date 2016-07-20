package de.salocin.gameenginefx.gui.text;

import java.util.ArrayList;
import java.util.StringTokenizer;

import de.salocin.gameenginefx.util.CollectionHelper;
import de.salocin.gameenginefx.util.FXHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public interface TextRenderer {
	
	public static double HEIGHT_PADDING = 10;
	public static double TOP_PADDING = 10;
	public static double BOTTOM_PADDING = 10;
	public static double LEFT_PADDING = 10;
	public static double RIGHT_PADDING = 10;
	
	default ArrayList<String> computeLines(double maxWidth, String text, Font font) {
		ArrayList<String> lines = new ArrayList<String>();
		
		if (font == null || text == null || text.isEmpty())
			return lines;
			
		StringTokenizer lineTokens = new StringTokenizer(text, "\n");
		
		while (lineTokens.hasMoreTokens()) {
			lines.add(lineTokens.nextToken());
		}
		
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			
			if (FXHelper.getStringWidth(font, line) > maxWidth) {
				StringTokenizer wordTokens = new StringTokenizer(line, " ");
				ArrayList<String> words = new ArrayList<String>();
				
				while (wordTokens.hasMoreTokens()) {
					words.add(wordTokens.nextToken());
				}
				
				for (int j = 1; j < words.size(); j++) {
					if (FXHelper.getStringWidth(font, CollectionHelper.toString(words, 0, j)) > maxWidth) {
						lines.set(i, CollectionHelper.toString(words, 0, j - 1));
						lines.add(i + 1, CollectionHelper.toString(words, j, words.size() - 1));
						
						break;
					}
				}
			}
		}
		
		return lines;
	}
	
	default double computeFontSize(double maxHeight, ArrayList<String> lines, Font font) {
		if(maxHeight <= 0 || lines.size() == 0) {
			return 0;
		}
		
		double currentSize;
		
		for (currentSize = font.getSize(); currentSize >= -1; currentSize -= 0.5) {
			double height = lines.size() * currentSize + HEIGHT_PADDING * (lines.size() - 1);
			
			if (height <= maxHeight) {
				break;
			}
		}
		
		return currentSize;
	}
	
	default void renderAbsoluteText(GraphicsContext gc, Font font, ArrayList<String> lines, double startX, double startY) {
		for (String line : lines) {
			renderTextLine(gc, font, line, startX, startY);
			
			startY += font.getSize() + HEIGHT_PADDING;
		}
	}
	
	default void renderCenteredText(GraphicsContext gc, Font font, ArrayList<String> lines, double centerX, double startY) {
		for (String line : lines) {
			renderTextLine(gc, font, line, centerX - FXHelper.getStringWidth(font, line) / 2, startY);
			
			startY += font.getSize() + HEIGHT_PADDING;
		}
	}
	
	default void renderTextLine(GraphicsContext gc, Font font, String line, double x, double y) {
		gc.setFont(font);
		gc.fillText(line, x, y);
	}
	
}
