package net.voxelmine.gui.component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.voxelmine.gui.Alignment;

public class GuiInventoryBackground extends GuiComponent {
	private BufferedImage topLeft, topRight, topCenter;
	private BufferedImage centerLeft, centerRight, centerCenter;
	private BufferedImage bottomLeft, bottomRight, bottomCenter;
	private int x1, y1, x2, y2;
	public GuiInventoryBackground(Alignment alignment, int x1, int y1, int x2, int y2) {
		super(alignment);
		topLeft = img.getSubimage(20, 0, 20, 20);
		topCenter = img.getSubimage(40, 0, 20, 20);
		topRight = img.getSubimage(60, 0, 20, 20);
		centerLeft = img.getSubimage(20, 20, 20, 20);
		centerCenter = img.getSubimage(40, 20, 20, 20);
		centerRight = img.getSubimage(60, 20, 20, 20);
		bottomLeft = img.getSubimage(20, 40, 20, 20);
		bottomCenter = img.getSubimage(40, 40, 20, 20);
		bottomRight = img.getSubimage(60, 40, 20, 20);
		this.x1 = (x1+getOriginX(20))*SCALE;
		this.x2 = (x2+getOriginX(20))*SCALE;
		this.y1 = (y1+getOriginY(20))*SCALE;
		this.y2 = (y2+getOriginY(20))*SCALE;
	}
	@Override
	public void render(Graphics g) {
		int sz = GuiComponent.SCALE;
		g.drawImage(topLeft, x1, y1, 20*sz, 20*sz, null);
		g.drawImage(topRight, x2-20*sz, y1, 20*sz, 20*sz, null);
		g.drawImage(bottomLeft, x1, y2-20*sz, 20*sz, 20*sz, null);
		g.drawImage(bottomRight, x2-20*sz, y2-20*sz, 20*sz, 20*sz, null);
	
		g.drawImage(topCenter, x1+20*sz, y1, x2-x1-40*sz, 20*sz, null);
		g.drawImage(bottomCenter, x1+20*sz, y2-20*sz, x2-x1-40*sz, 20*sz, null);
		g.drawImage(centerLeft, x1, y1+20*sz, 20*sz, y2-y1-40*sz, null);
		g.drawImage(centerRight, x2-20*sz, y1+20*sz, 20*sz, y2-y1-40*sz, null);
		g.drawImage(centerCenter, x1+20*sz, y1+20*sz, x2-x1-40*sz, y2-y1-40*sz, null);
	}
	@Override
	public void update() {
		
	}
	@Override
	public boolean clicked() {
		return false;
	}
}
