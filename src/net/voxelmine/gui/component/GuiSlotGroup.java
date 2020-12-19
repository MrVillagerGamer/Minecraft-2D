package net.voxelmine.gui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import net.voxelmine.entity.Inventory;
import net.voxelmine.gui.Alignment;
import net.voxelmine.input.Input;
import net.voxelmine.items.Item;

public class GuiSlotGroup extends GuiComponent {
	private static int movedSlot = -1;
	private static int movedItem;
	private static Inventory movedInv;
	private GuiSlot[] slots;
	private int selSlot = 0;
	private Inventory inv;
	private int x, y, cols;
	public GuiSlotGroup(Alignment align, Inventory inv, int x, int y, int cols) {
		super(align);
		this.cols = cols;
		this.x = x;
		this.y = y;
		this.inv = inv;
		slots = new GuiSlot[inv.getLength()];
		for(int i = 0; i < inv.getLength(); i++) {
			int cx = 20 * (i % cols);
			int cy = 20 * (i / cols);
			slots[i] = new GuiSlot(align, x+cx, y+cy);
			slots[i].setId(inv.getId(i));
			slots[i].setCount(inv.getCount(i));
		}
		selSlot = inv.getSelectedSlot();
	}
	public void render(Graphics g) {
		for(int i = 0; i < slots.length; i++) {
			slots[i].render(g);
		}
	}
	public void update() {
		selSlot = inv.getSelectedSlot();
		for(int i = 0; i < slots.length; i++) {
			slots[i].update();
		}
		for(int i = 0; i < slots.length; i++) {
			if(slots[i].clicked()
					&& movedSlot < 0
					&& Input.isKey(KeyEvent.VK_SHIFT)) {
				movedSlot = i;
				movedInv = inv;
			}else if(slots[i].clicked()
					&& movedSlot >= 0
					&& !Input.isKey(KeyEvent.VK_SHIFT)) {
				inv.swapSlots(i, movedSlot, movedInv);
				movedSlot = -1;
				movedInv = null;
			}else if(slots[i].clicked()
					&& movedSlot >= 0
					&& Input.isKey(KeyEvent.VK_SHIFT)) {
				movedInv.splitSlots(movedSlot, i, inv);
				movedSlot = -1;
				movedInv = null;
			}else if(slots[i].clicked()
					&& movedSlot < 0
					&& i < 36
					&& !Input.isKey(KeyEvent.VK_SHIFT)) {
				selSlot = i;
			}
		}
		for(int i = 0; i < slots.length; i++) {
			slots[i].setId(inv.getId(i));
			slots[i].setCount(inv.getCount(i));
			if(i == movedSlot && inv == movedInv) {
				movedItem = slots[i].getId();
				slots[i].setId(0);
				slots[i].setCount(0);
			}
			if(i == selSlot) {
				slots[i].setSelected(true);
			}else {
				slots[i].setSelected(false);
			}
		}
		inv.setSelectedSlot(selSlot);
	}
	@Override
	public boolean clicked() {
		// TODO Auto-generated method stub
		return false;
	}
	public void render2(Graphics g) {
		if(movedItem == 0 || movedSlot == -1 || movedInv != inv) {
			return;
		}
		int sz = GuiComponent.SCALE;
		int bsz = 14*sz;
		if(movedItem != 0) {
			BufferedImage img = Item.get(movedItem).getImage();
			if(img == null) {
				img = Item.get(movedItem).getImage();
			}
			int x1 = Input.getMouseX()-6;
			int y1 = Input.getMouseY()-6;
			g.drawImage(img, x1, y1, bsz, bsz, null);
			g.setColor(new Color(0, 0, 0, 0.33f));
			g.fillRect(x1, y1, bsz, bsz);
			g.setColor(Color.BLACK);
			int slen = Integer.toString(inv.getCount(movedSlot)).length()==1?8:2;
			g.drawString(Integer.toString(inv.getCount(movedSlot)), x1+slen*sz, y1+14*sz);
		}
	}
}