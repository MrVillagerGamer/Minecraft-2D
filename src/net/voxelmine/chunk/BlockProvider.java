package net.voxelmine.chunk;

import net.voxelmine.blocks.BlockPos;

public abstract class BlockProvider {
	public abstract void generate(Chunk chunk, BlockPos cloc);
	public abstract void generateTree(BlockPos cloc);
}
