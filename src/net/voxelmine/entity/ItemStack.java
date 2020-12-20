package net.voxelmine.entity;

import java.io.Serializable;

public class ItemStack implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jsonData;
	private int item, count, damage;
	public ItemStack(int item, int count) {
		this(item, count, 0, "{}");
	}
	public ItemStack(int item, int count, int damage, String jsonData) {
		this.item = item;
		this.count = count;
		this.jsonData = jsonData;
		this.damage = damage;
	}
	public ItemStack(ItemStack stk) {
		this(stk.item, stk.count, stk.damage, stk.jsonData);
	}
	public boolean canStackWith(ItemStack stk) {
		return stk.item == item && stk.damage == damage && stk.jsonData.equals(jsonData);
	}
	public int getItem() {
		return item;
	}
	public void setItem(int item) {
		this.item = item;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
		if(count == 0) {
			item = 0;
		}
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
