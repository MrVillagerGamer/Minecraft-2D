package net.voxelmine.gui.component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.voxelmine.gui.Alignment;
import net.voxelmine.main.Voxelmine;

public abstract class GuiComponent {
	public static final int SCALE = Voxelmine.WIDTH/480;
	protected static BufferedImage img;
	protected Alignment alignment;
	public GuiComponent(Alignment alignment) {
		if(img == null) {
			try {
				img = ImageIO.read(getClass().getResourceAsStream("/gui.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.alignment = alignment;
	}
	protected int getOriginX(int w) {
		return (int) (alignment.getX()*Voxelmine.WIDTH/SCALE-alignment.getX()*w);
	}
	protected int getOriginY(int h) {
		return (int) (alignment.getY()*Voxelmine.HEIGHT/SCALE-alignment.getY()*h);
	}
	public abstract void render(Graphics g);
	public abstract void update();
	public abstract boolean clicked();
}
