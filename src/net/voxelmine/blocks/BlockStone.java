package net.voxelmine.blocks;

import net.voxelmine.entity.ItemStack;
import net.voxelmine.items.Item;

public class BlockStone extends Block {
	public BlockStone(int id, Material mat, int texX, int texY) {
		super(id, mat, texX, texY);
	}
	@Override
	public ItemStack getDrop(BlockPos pos) {
		return new ItemStack(Item.STONE.getId(), 1);
	}
}
