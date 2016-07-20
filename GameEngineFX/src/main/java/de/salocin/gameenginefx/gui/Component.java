package de.salocin.gameenginefx.gui;

import java.util.ArrayList;

import de.salocin.gameenginefx.core.Display;
import de.salocin.gameenginefx.gui.layout.LayoutData;
import de.salocin.gameenginefx.gui.text.TextAlgin;
import de.salocin.gameenginefx.gui.text.TextRenderer;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class Component implements TextRenderer {
	
	public static final Font DEFAULT_TITLE_FONT = new Font("Consolas", 18);
	public static final Color DEFAULT_TITLE_COLOR = Color.WHITE;
	public static final TextAlgin DEFAULT_TITLE_ALGIN = TextAlgin.AUTOMATICALLY;
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
	
	/* Eigenschaften */
	private Rectangle2D bounds;
	private LayoutData layoutData;
	private String title;
	private Font titleFont;
	private Color titleColor;
	private TextAlgin titleHAlgin;
	private TextAlgin titleVAlgin;
	private Image backgroundImage;
	private Image backgroundHoverImage;
	private Color backgroundColor;
	private Color backgroundHoverColor;
	
	/* Temp */
	private final ArrayList<String> computedTitle = new ArrayList<String>();
	private boolean hover;
	private boolean updateTitle;
	private double currentFontSize;
	
	public Component() {
		this(null);
	}
	
	public Component(String title) {
		this(title, 0, 0);
	}
	
	public Component(String title, double x, double y) {
		this(title, x, y, 0, 0);
	}
	
	public Component(String title, double x, double y, double width, double height) {
		this(title, new Rectangle2D(x, y, width, height));
	}
	
	public Component(String title, Rectangle2D bounds) {
		setBounds(bounds);
		setTitle(title);
		setTitleFont(DEFAULT_TITLE_FONT);
		setTitleColor(DEFAULT_TITLE_COLOR);
		setTitleHorizontalAlgin(DEFAULT_TITLE_ALGIN);
		setTitleVerticalAlgin(DEFAULT_TITLE_ALGIN);
		setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
	}
	
	public void setBounds(Rectangle2D bounds) {
		this.bounds = bounds == null ? new Rectangle2D(0, 0, 0, 0) : bounds;
	}
	
	public Rectangle2D getBounds() {
		return bounds;
	}
	
	public void setLayoutData(LayoutData layoutData) {
		this.layoutData = layoutData;
	}
	
	public LayoutData getLayoutData() {
		return layoutData;
	}
	
	public void pack() {
		setBounds(layoutData.getBounds(Display.getInstance().getCanvas(), this));
		updateTitle = true;
	}
	
	public void setTitle(String title) {
		this.title = title == null ? "" : title;
		updateTitle = true;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont == null ? DEFAULT_TITLE_FONT : titleFont;
	}
	
	public Font getTitleFont() {
		return titleFont;
	}
	
	public void setTitleColor(Color titleColor) {
		this.titleColor = titleColor == null ? DEFAULT_TITLE_COLOR : titleColor;
	}
	
	public Color getTitleColor() {
		return titleColor;
	}
	
	public void setTitleHorizontalAlgin(TextAlgin titleAlgin) {
		this.titleHAlgin = titleAlgin == null ? DEFAULT_TITLE_ALGIN : titleAlgin;
	}
	
	public TextAlgin getTitleHorizontalAlgin() {
		return titleHAlgin;
	}
	
	public void setTitleVerticalAlgin(TextAlgin titleAlgin) {
		this.titleVAlgin = titleAlgin == null ? DEFAULT_TITLE_ALGIN : titleAlgin;
	}
	
	public TextAlgin getTitleVerticalAlgin() {
		return titleVAlgin;
	}
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor == null ? DEFAULT_BACKGROUND_COLOR : backgroundColor;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	
	public Image getBackgroundImage() {
		return backgroundImage;
	}
	
	public void setBackgroundHoverColor(Color backgroundHoverColor) {
		this.backgroundHoverColor = backgroundHoverColor;
	}
	
	public Color getBackgroundHoverColor() {
		return backgroundHoverColor;
	}
	
	public void setBackgroundHoverImage(Image backgroundHoverImage) {
		this.backgroundHoverImage = backgroundHoverImage;
	}
	
	public Image getBackgroundHoverImage() {
		return backgroundHoverImage;
	}
	
	public boolean isHovered() {
		return hover;
	}
	
	public void setHover(boolean hover) {
		this.hover = hover;
	}
	
	public void simulateClick() {
		return;
	}
	
	public void update(Canvas canvas) {
		if (updateTitle) {			
			computedTitle.clear();
			computedTitle.addAll(computeLines(bounds.getWidth() - TextRenderer.LEFT_PADDING - TextRenderer.RIGHT_PADDING, title, titleFont));
			
			currentFontSize = computeFontSize(bounds.getHeight() - TextRenderer.TOP_PADDING - TextRenderer.BOTTOM_PADDING, computedTitle, titleFont);
			
			updateTitle = false;
		}
	}
	
	public void render(Canvas canvas, GraphicsContext gc) {
		renderBackground(gc, bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
		
		renderTitle(gc, new Font(titleFont.getName(), currentFontSize));
	}
	
	protected void renderTitle(GraphicsContext gc, Font font) {
		if (computedTitle.size() == 0)
			return;
			
		double y;
		
		if (titleVAlgin == TextAlgin.ABSOLUTE) {
			y = getBounds().getMinY() + font.getSize() + TextRenderer.TOP_PADDING;
		} else {
			y = getBounds().getMaxY() - font.getSize() * computedTitle.size() - TextRenderer.HEIGHT_PADDING * (computedTitle.size() - 1);
		}
		
		if (titleHAlgin == TextAlgin.ABSOLUTE) {
			renderAbsoluteText(gc, font, computedTitle, getBounds().getMinX() + TextRenderer.LEFT_PADDING, y);
		} else {
			renderCenteredText(gc, font, computedTitle, getBounds().getMaxX() - getBounds().getWidth() / 2, y);
		}
	}
	
	protected void renderBackground(GraphicsContext gc, double x, double y, double width, double height) {
		Image img = null;
		Paint oldFill = gc.getFill();
		Paint fill = oldFill;
		
		if (hover) {
			if (backgroundHoverImage != null) {
				img = backgroundHoverImage;
			} else {
				if (backgroundHoverColor != null) {
					fill = backgroundHoverColor;
				} else {
					fill = backgroundColor;
				}
			}
		} else {
			if (backgroundImage != null) {
				img = backgroundImage;
			} else {
				fill = backgroundColor;
			}
		}
		
		if (img != null) {
			gc.drawImage(img, x, y, width, height);
		} else {
			gc.setFill(fill);
			gc.fillRect(x, y, width, height);
		}
		
		gc.setFill(oldFill);
	}
	
}
