package net.voxelmine.gui.screen;

import java.awt.Graphics;

import net.voxelmine.entity.EntityPlayer;
import net.voxelmine.entity.InventoryCrafting;
import net.voxelmine.gui.Alignment;
import net.voxelmine.gui.component.GuiButton;
import net.voxelmine.gui.component.GuiInventoryBackground;
import net.voxelmine.gui.component.GuiSlotGroup;
import net.voxelmine.items.Item;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.recipe.Recipe;
import net.voxelmine.recipe.RecipeType;

public class SmeltingScreen extends Screen {
	GuiSlotGroup inventorySlotGroup;
	GuiSlotGroup craftingSlotGroup;
	InventoryCrafting craftingInventory;
	GuiInventoryBackground background1;
	GuiInventoryBackground background2;
	GuiButton craftButton;
	public SmeltingScreen() {
		craftingInventory = new InventoryCrafting(9);
		EntityPlayer player = Voxelmine.getInstance().getPlayer();
		inventorySlotGroup = new GuiSlotGroup(Alignment.CENTER, player.getInventory(), -90, -30, 9);
		craftingSlotGroup = new GuiSlotGroup(Alignment.CENTER, craftingInventory, 115, -30, 3);
		craftButton = new GuiButton(Alignment.CENTER, 135, 30, 60, "Smelt");
		background1 = new GuiInventoryBackground(Alignment.CENTER, -90-10, -30-10, -90-10+200, -30-10+100);
		background2 = new GuiInventoryBackground(Alignment.CENTER, 115-10, -30-10, 115-10+80, -30-10+100);
	}
	@Override
	public void onUpdate(float delta) {
		inventorySlotGroup.update();
		craftingSlotGroup.update();
		craftButton.update();
		if(craftButton.clicked()) {
			int[] items = new int[] {
				craftingInventory.getStack(0).getItem(),
				craftingInventory.getStack(1).getItem(),
				craftingInventory.getStack(2).getItem(),
				craftingInventory.getStack(3).getItem(),
				craftingInventory.getStack(4).getItem(),
				craftingInventory.getStack(5).getItem(),
				craftingInventory.getStack(6).getItem(),
				craftingInventory.getStack(7).getItem(),
				craftingInventory.getStack(8).getItem(),	
			};
			EntityPlayer player = Voxelmine.getInstance().getPlayer();
			Recipe recipe = Recipe.getMatchingRecipe(items, RecipeType.SMELTING);
			if(recipe != null) {
				player.getInventory().addItems(recipe.getResult());
				for(int i = 0; i < 9; i++) {
					if(craftingInventory.getStack(i).getCount() > 0) {
						craftingInventory.getStack(i).setCount(craftingInventory.getStack(i).getCount()-1);
						if(craftingInventory.getStack(i).getCount() == 0) craftingInventory.getStack(i).setItem(0);
					}
				}
			}
				
		}
	}

	@Override
	public void onRender(Graphics g) {
		background1.render(g);
		background2.render(g);
		inventorySlotGroup.render(g);
		craftingSlotGroup.render(g);
		craftButton.render(g);
		inventorySlotGroup.render2(g);
		craftingSlotGroup.render2(g);
		
	}

	@Override
	public void onOpen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
	
}