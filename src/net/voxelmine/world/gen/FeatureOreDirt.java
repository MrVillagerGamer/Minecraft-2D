package net.voxelmine.world.gen;

import net.voxelmine.blocks.Block;

public class FeatureOreDirt extends FeatureOre{
	public FeatureOreDirt(int id) {
		super(id);
		setVeinSize(2);
		setBlock(Block.DIRT.getId());
		setTries(50);
	}
}
