package net.voxelmine.gui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import net.voxelmine.gui.Alignment;
import net.voxelmine.input.Input;

public class GuiButton extends GuiComponent {
	BufferedImage b1, b2, b3;
	int x1, y1, x2, y2;
	boolean clicked;
	boolean clicked2;
	boolean hovering;
	String s;
	public GuiButton(Alignment alignment, int x, int y, int w, String s) {
		super(alignment);
		b1 = img.getSubimage(0, 60, 16, 20);
		b2 = img.getSubimage(16, 60, w-16, 20);
		b3 = img.getSubimage(184, 60, 16, 20);
		x1 = (getOriginX(w)+x)*GuiComponent.SCALE;
		x2 = (getOriginX(w)+w+x)*GuiComponent.SCALE;
		y1 = (getOriginY(16)+y)*GuiComponent.SCALE;
		y2 = (getOriginY(16)+20+y)*GuiComponent.SCALE;
		this.s = s;
		hovering = false;
	}
	@Override
	public void render(Graphics g) {
		int sz = GuiComponent.SCALE;
		g.drawImage(b1, x1, y1, 16*sz, (y2-y1), null);
		g.drawImage(b2, x1+16*sz, y1, (x2-x1-16*sz), (y2-y1), null);
		g.drawImage(b3, x2-16*sz, y1, 16*sz, (y2-y1), null);
		if(hovering) {
			g.setColor(new Color(0.0f, 0.5f, 1.0f, 0.167f));
			g.fillRect(x1, y1, x2-x1, y2-y1);
		}
		int cbtx = ((x2+x1)/2)-3*sz*s.length();
		int cbty = ((y2+y1)/2+3*sz);
		g.setColor(Color.BLACK);
		g.drawString(s, cbtx, cbty);
	}
	@Override
	public void update() {
		if(clicked) {
			clicked = false;
		}
		if(Input.getMouseX() > x1 && Input.getMouseX() < x2
				&& Input.getMouseY() > y1 && Input.getMouseY() < y2) {
			hovering = true;
		}else {
			hovering = false;
		}
		if(Input.isButton(MouseEvent.BUTTON1) && !clicked2
				&& Input.getMouseX() > x1 && Input.getMouseX() < x2
				&& Input.getMouseY() > y1 && Input.getMouseY() < y2) {
			clicked2 = true;
			clicked = true;
		}
		if(!Input.isButton(MouseEvent.BUTTON1)) {
			clicked2 = false;
		}
	}
	@Override
	public boolean clicked() {
		return clicked;
	}
}
