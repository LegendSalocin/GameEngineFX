package de.salocin.gameenginefx.gui.layout;

import de.salocin.gameenginefx.gui.Component;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;

public class RelativeData implements LayoutData {
	
	private static double defaultComponentOffset = 10;
	
	public final RelativeAttachment top = new RelativeAttachment(50, 0);
	public final RelativeAttachment left = new RelativeAttachment(50, 0);
	public final RelativeAttachment right = new RelativeAttachment(50, 0);
	public final RelativeAttachment bottom = new RelativeAttachment(50, 0);
	
	public RelativeData top(Component relativeTo) {
		return top(relativeTo, defaultComponentOffset);
	}
	
	public RelativeData top(Component relativeTo, double offset) {
		LayoutData other = relativeTo.getLayoutData();
		if (other == null || !(other instanceof RelativeData))
			return this;
			
		RelativeData data = (RelativeData) other;
		relativeTo.pack();
		return top(data.bottom.percentage, data.bottom.offset + offset);
	}
	
	public RelativeData topOffset(double offset) {
		return top(top.percentage, offset);
	}
	
	public RelativeData topPercentage(double percentage) {
		return top(percentage, top.offset);
	}
	
	public RelativeData top(double percentage, double offset) {
		top.percentage = percentage;
		top.offset = offset;
		return this;
	}
	
	public RelativeData left(Component relativeTo) {
		return left(relativeTo, defaultComponentOffset);
	}
	
	public RelativeData left(Component relativeTo, double offset) {
		LayoutData other = relativeTo.getLayoutData();
		if (other == null || !(other instanceof RelativeData))
			return this;
			
		RelativeData data = (RelativeData) other;
		relativeTo.pack();
		return left(data.right.percentage, data.right.offset + offset);
	}
	
	public RelativeData leftOffset(double offset) {
		return left(left.percentage, offset);
	}
	
	public RelativeData leftPercentage(double percentage) {
		return left(percentage, left.offset);
	}
	
	public RelativeData left(double percentage, double offset) {
		left.percentage = percentage;
		left.offset = offset;
		return this;
	}
	
	public RelativeData right(Component relativeTo) {
		return right(relativeTo, defaultComponentOffset);
	}
	
	public RelativeData right(Component relativeTo, double offset) {
		LayoutData other = relativeTo.getLayoutData();
		if (other == null || !(other instanceof RelativeData))
			return this;
			
		RelativeData data = (RelativeData) other;
		relativeTo.pack();
		return right(data.left.percentage, data.left.offset + offset);
	}
	
	public RelativeData rightOffset(double offset) {
		return right(right.percentage, offset);
	}
	
	public RelativeData rightPercentage(double percentage) {
		return right(percentage, right.offset);
	}
	
	public RelativeData right(double percentage, double offset) {
		right.percentage = percentage;
		right.offset = offset;
		return this;
	}
	
	public RelativeData bottom(Component relativeTo) {
		return bottom(relativeTo, defaultComponentOffset);
	}
	
	public RelativeData bottom(Component relativeTo, double offset) {
		LayoutData other = relativeTo.getLayoutData();
		if (other == null || !(other instanceof RelativeData))
			return this;
			
		RelativeData data = (RelativeData) other;
		relativeTo.pack();
		return bottom(data.top.percentage, data.top.offset + offset);
	}
	
	public RelativeData bottomOffset(double offset) {
		return bottom(bottom.percentage, offset);
	}
	
	public RelativeData bottomPercentage(double percentage) {
		return bottom(percentage, bottom.offset);
	}
	
	public RelativeData bottom(double percentage, double offset) {
		bottom.percentage = percentage;
		bottom.offset = offset;
		return this;
	}
	
	public RelativeData width(double width) {
		right.percentage = left.percentage;
		right.offset = left.offset + width;
		return this;
	}
	
	public RelativeData width(double percentage, double offset) {
		right.percentage = left.percentage + percentage;
		right.offset = left.offset + offset;
		return this;
	}
	
	public RelativeData centeredWidth(double width) {
		left.percentage = 50;
		left.offset = -(width / 2);
		right.percentage = left.percentage;
		right.offset = left.offset + width;
		return this;
	}
	
	public RelativeData height(double height) {
		bottom.percentage = top.percentage;
		bottom.offset = top.offset + height;
		return this;
	}
	
	public RelativeData height(double percentage, double offset) {
		bottom.percentage = top.percentage + percentage;
		bottom.offset = top.offset + offset;
		return this;
	}
	
	@Override
	public Rectangle2D getBounds(Canvas canvas, Component component) {
		double minY = (top.percentage / 100.0) * canvas.getHeight() + top.offset;
		double minX = (left.percentage / 100.0) * canvas.getWidth() + left.offset;
		double maxX = (right.percentage / 100.0) * canvas.getWidth() + right.offset;
		double maxY = (bottom.percentage / 100.0) * canvas.getHeight() + bottom.offset;
		
		if (minX > maxX) {
			double tmp = maxX;
			maxX = minX;
			minX = tmp;
		}
		
		if (minY > maxY) {
			double tmp = maxY;
			maxY = minY;
			minY = tmp;
		}
		
		return new Rectangle2D(minX, minY, maxX - minX, maxY - minY);
	}
	
	public static void setDefaultComponentOffset(double defaultComponentOffset) {
		RelativeData.defaultComponentOffset = defaultComponentOffset;
	}
	
	public static double getDefaultComponentOffset() {
		return defaultComponentOffset;
	}
	
}
