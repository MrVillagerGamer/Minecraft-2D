package net.voxelmine.gui.screen;

import java.awt.Graphics;

import net.voxelmine.entity.EntityPlayer;
import net.voxelmine.entity.Inventory;
import net.voxelmine.entity.InventoryChest;
import net.voxelmine.entity.InventoryCrafting;
import net.voxelmine.gui.Alignment;
import net.voxelmine.gui.component.GuiButton;
import net.voxelmine.gui.component.GuiInventoryBackground;
import net.voxelmine.gui.component.GuiSlotGroup;
import net.voxelmine.items.Item;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.recipe.Recipe;
import net.voxelmine.recipe.RecipeType;

public class ChestScreen extends Screen {
	GuiSlotGroup inventorySlotGroup;
	GuiSlotGroup craftingSlotGroup;
	Inventory craftingInventory;
	GuiInventoryBackground background1;
	GuiInventoryBackground background2;
	public ChestScreen() {
		super();
		craftingInventory = new InventoryChest(12);
		EntityPlayer player = Voxelmine.getInstance().getPlayer();
		inventorySlotGroup = new GuiSlotGroup(Alignment.CENTER, player.getInventory(), -90, -30, 9);
		craftingSlotGroup = new GuiSlotGroup(Alignment.CENTER, craftingInventory, 115, -30, 3);
		background1 = new GuiInventoryBackground(Alignment.CENTER, -90-10, -30-10, -90-10+200, -30-10+100);
		background2 = new GuiInventoryBackground(Alignment.CENTER, 115-10, -30-10, 115-10+80, -30-10+100);
	}
	public void setChestInventory(Inventory inventory) {
		craftingInventory = inventory;
		craftingSlotGroup = new GuiSlotGroup(Alignment.CENTER, craftingInventory, 115, -30, 3);
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
			slots[i].setItem(player.getInventory().getId(i));
			slots[i].setCount(player.getInventory().getCount(i));
			if(i == movedSlot) {
				movedItem = slots[i].getId();
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
		craftingSlotGroup.update();
	}
	@Override
	public void onRender(Graphics g) {
		background1.render(g);
		background2.render(g);
		inventorySlotGroup.render(g);
		craftingSlotGroup.render(g);
		inventorySlotGroup.render2(g);
		craftingSlotGroup.render2(g);
		
	}
	@Override
	public void onOpen() {
		
	}
	@Override
	public void onClose() {
		
	}
}