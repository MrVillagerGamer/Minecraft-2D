package net.voxelmine.blocks;

import java.util.Random;

import net.voxelmine.entity.ItemStack;
import net.voxelmine.items.Item;

public class BlockLeaves extends Block{
	public BlockLeaves(int id, Material mat, int texX, int texY) {
		super(id, mat, texX, texY);
	}
	@Override
	public ItemStack getDrop(BlockPos pos) {
		return new ItemStack(new Random().nextInt()%5==0?Item.SAPLING.getId():0, 1);
	}
}
