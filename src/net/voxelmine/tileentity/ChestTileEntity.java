package net.voxelmine.tileentity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.voxelmine.entity.InventoryChest;

public class ChestTileEntity implements ITileEntity {
	private InventoryChest inventory = new InventoryChest(12);
	public InventoryChest getInventory() {
		return inventory;
	}
	public void setInventory(InventoryChest inventory) {
		this.inventory = inventory;
	}
	@Override
	public String writeJson() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	@Override
	public void readJson(String json) {
		Gson gson = new GsonBuilder().create();
		ChestTileEntity te = gson.fromJson(json, getClass());
		this.inventory = te.inventory;
	}
}
