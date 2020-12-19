package net.voxelmine.gui.screen;

import java.awt.Graphics;

import net.voxelmine.entity.EntityPlayer;
import net.voxelmine.gui.Alignment;
import net.voxelmine.gui.component.GuiInventoryBackground;
import net.voxelmine.gui.component.GuiSlotGroup;
import net.voxelmine.items.Item;
import net.voxelmine.main.Voxelmine;

public class InventoryScreen extends Screen {
	GuiSlotGroup inventorySlotGroup;
	GuiInventoryBackground background;
	int movedSlot = -1;
	Item movedItem;
	public InventoryScreen() {
		super();
		EntityPlayer player = Voxelmine.getInstance().getPlayer();
		inventorySlotGroup = new GuiSlotGroup(Alignment.CENTER, player.getInventory(), -45, -30, 9);
		background = new GuiInventoryBackground(Alignment.CENTER, -45-10, -30-10, -45-10+200, -30-10+100);
	}
	@Override
	public void onUpdate(float delta) {
		/*
		for(int i = 0; i < 36+9; i++) {
			slots[i].update();
		}
		for(int i = 0; i < 36+9; i++) {
			if(slots[i].clicked()
					&& movedSlot < 0
					&& Input.isKey(KeyEvent.VK_SHIFT)) {
				movedSlot = i;
			}else if(slots[i].clicked()
					&& movedSlot >= 0
					&& !Input.isKey(KeyEvent.VK_SHIFT)) {
				player.getInventory().swapSlots(movedSlot, i);
				movedSlot = -1;
			}else if(slots[i].clicked()
					&& movedSlot >= 0
					&& Input.isKey(KeyEvent.VK_SHIFT)) {
				player.getInventory().splitSlots(movedSlot, i);
				movedSlot = -1;
			}else if(slots[i].clicked()
					&& movedSlot < 0
					&& i < 36
					&& !Input.isKey(KeyEvent.VK_SHIFT)) {
				selSlot = i;
			}
		}
		for(int i = 0; i < 36+9; i++) {
			slots[i].setItem(player.getInventory().getItem(i));
			slots[i].setCount(player.getInventory().getCount(i));
			if(i == movedSlot) {
				movedItem = slots[i].getItem();
				slots[i].setItem(null);
				slots[i].setCount(0);
			}
			if(i == selSlot) {
				slots[i].setSelected(true);
			}else {
				slots[i].setSelected(false);
			}
		}
		*/
		inventorySlotGroup.update();
	}
	@Override
	public void onRender(Graphics g) {
		background.render(g);
		inventorySlotGroup.render(g);
		inventorySlotGroup.render2(g);
		
	}
	@Override
	public void onOpen() {
		
	}
	@Override
	public void onClose() {
		
	}
}
