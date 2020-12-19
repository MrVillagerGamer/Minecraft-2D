package net.voxelmine.items;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.voxelmine.blocks.Block;

public class Item {
	public static final Item[] ITEMS = new Item[512];
	public static final Item GRASS = new Item(Block.GRASS.getId());
	public static final Item DIRT = new Item(Block.DIRT.getId());
	public static final Item STONE = new Item(Block.STONE.getId());
	public static final Item WOOD = new Item(Block.WOOD.getId());
	public static final Item LEAVES = new Item(Block.LEAVES.getId());
	public static final Item PLANKS = new Item(Block.PLANKS.getId());
	public static final Item SAPLING = new Item(Block.SAPLING.getId());
	public static final Item FURNACE = new Item(Block.FURNACE.getId());
	public static final Item SAND = new Item(Block.SAND.getId());
	public static final Item STICK = new Item(256).setImage(0, 2);
	public static final Item WOOD_SWORD = new Item(257).setImage(3, 0).setToolType(ToolType.SWORD).setToolTier(1);
	public static final Item WOOD_SHOVEL = new Item(258).setImage(2, 0).setToolType(ToolType.SHOVEL).setToolTier(1);
	public static final Item WOOD_PICKAXE = new Item(259).setImage(1, 0).setToolType(ToolType.PICKAXE).setToolTier(1);
	public static final Item WOOD_AXE = new Item(260).setImage(0, 0).setToolType(ToolType.AXE).setToolTier(1);
	public static final Item STONE_SWORD = new Item(261).setImage(3, 1).setToolType(ToolType.SWORD).setToolTier(2);
	public static final Item STONE_SHOVEL = new Item(262).setImage(2, 1).setToolType(ToolType.SHOVEL).setToolTier(2);
	public static final Item STONE_PICKAXE = new Item(263).setImage(1, 1).setToolType(ToolType.PICKAXE).setToolTier(2);
	public static final Item STONE_AXE = new Item(264).setImage(0, 1).setToolType(ToolType.AXE).setToolTier(2);
	public static final Item WORKBENCH = new Item(Block.WORKBENCH.getId());
	public static final Item CHEST = new Item(Block.CHEST.getId());
	public static final Item WATER = new Item(Block.WATER.getId());
	private static BufferedImage atlas;
	public static Item forBlock(Block block2) {
		for(Item ip : ITEMS) {
			if(ip != null && ip.getId() == block2.getId()) {
				return ip;
			}
		}
		return null;
	}
	public static Item get(int item) {
		if(item < 0 || item >= ITEMS.length) return null;
		return ITEMS[item];
	}
	private BufferedImage img;
	private int id;
	private ToolType toolType;
	private int toolTier;
	
	public Item(int id) {
		if(atlas == null) {
			try {
				atlas = ImageIO.read(new File("src/items.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ITEMS[id] = this;
		this.id = id;
	}
	public Item setToolType(ToolType toolType) {
		this.toolType = toolType;
		return this;
	}
	public ToolType getToolType() {
		return toolType;
	}
	public Item setImage(int x, int y) {
		img = atlas.getSubimage(x*8, y*8, 8, 8);
		return this;
	}
	public BufferedImage getImage() {
		Block b = Block.get(id);
		// TODO: Render block models in inventory
		if(img == null && b != null) {
			return b.getStateFromMeta(0).getModel().getPieces()[0].getTex();
		}
		return img;
	}
	public int getId() {
		return id;
	}
	public Item setToolTier(int tier) {
		toolTier = tier;
		return this;
	}
	public int getToolTier() {
		return toolTier;
	}
}