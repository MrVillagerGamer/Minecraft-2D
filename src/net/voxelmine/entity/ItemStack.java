package net.voxelmine.entity;

public class ItemStack {
	private String jsonData;
	private int item, count;
	public ItemStack(int item, int count) {
		this(item, count, "{}");
	}
	public ItemStack(int item, int count, String jsonData) {
		this.item = item;
		this.count = count;
		this.jsonData = jsonData;
	}
	public ItemStack(ItemStack stk) {
		this(stk.item, stk.count, stk.jsonData);
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
}
