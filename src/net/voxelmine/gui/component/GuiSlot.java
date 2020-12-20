package net.voxelmine.gui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import net.voxelmine.blocks.Block;
import net.voxelmine.blocks.BlockPos;
import net.voxelmine.entity.ItemStack;
import net.voxelmine.gui.Alignment;
import net.voxelmine.input.Input;
import net.voxelmine.items.Item;

public class GuiSlot extends GuiComponent {
	BufferedImage tex, tex2;
	int x, y;
	boolean clicked = false, clicked2 = false, sel = false;
	ItemStack stack;
	public GuiSlot(Alignment alignment, int x, int y) {
		super(alignment);
		tex = img.getSubimage(0, 0, 20, 20);
		tex2 = img.getSubimage(0, 20, 20, 20);
		int sz = GuiComponent.SCALE;
		this.x = (x+getOriginX(20))*sz;
		this.y = (y+getOriginY(20))*sz;
	}
	public void setStack(ItemStack stack) {
		this.stack = stack;
	}
	public ItemStack getStack() {
		return stack;
	}
	public void setSelected(boolean sel) {
		this.sel = sel;
	}
	@Override
	public void render(Graphics g) {
		int count = stack.getCount();
		int damage = stack.getDamage();
		int sz = GuiComponent.SCALE;
		int bsz = 14*sz;
		Item item = Item.get(stack.getItem());
		g.drawImage(tex, x, y, 20*sz, 20*sz, null);
		if(sel) {
			g.drawImage(tex2, x, y, 20*sz, 20*sz, null);
		}
		if(item != null) {
			BufferedImage img = item.getImage();
			int x1 = x+3*GuiComponent.SCALE;
			int y1 = y+3*GuiComponent.SCALE;
			g.drawImage(img, x1, y1, bsz, bsz, null);
			g.setColor(Color.BLACK);
			int slen = Integer.toString(count).length()==1?8:2;
			g.drawString(Integer.toString(count), x1+slen*sz, y1+14*sz);
		
			if(damage != 0) {
				g.setColor(new Color(1.0f, 0.0f, 0.0f));
				//int dlen = Integer.toString(damage).length()==1?8:2;
				g.drawString(Integer.toString(damage), x1, y1+14*sz);
			}
		}
	}
	@Override
	public void update() {
		int sz = GuiComponent.SCALE;
		if(clicked) {
			clicked = false;
		}
		if(Input.isButton(MouseEvent.BUTTON1) && !clicked2
				&& Input.getMouseX() > x && Input.getMouseX() < x+20*sz
				&& Input.getMouseY() > y && Input.getMouseY() < y+20*sz) {
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
