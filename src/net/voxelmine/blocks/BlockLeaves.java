package net.voxelmine.blocks;

import java.util.Random;

import net.voxelmine.items.Item;

public class BlockLeaves extends Block{
	public BlockLeaves(int id, BlockRenderMode renderMode, int texX, int texY) {
		super(id, renderMode, texX, texY);
	}
	@Override
	public int getDrop() {
		return new Random().nextInt()%5==0?Item.SAPLING.getId():0;
	}
}
