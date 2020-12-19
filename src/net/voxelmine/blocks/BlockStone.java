package net.voxelmine.blocks;

import net.voxelmine.items.Item;

public class BlockStone extends Block {
	public BlockStone(int id, BlockRenderMode renderMode, int texX, int texY) {
		super(id, renderMode, texX, texY);
	}
	@Override
	public int getDrop() {
		return Item.STONE.getId();
	}
}
