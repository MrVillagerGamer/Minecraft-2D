package net.voxelmine.biome;

import net.voxelmine.blocks.BlockPos;

public abstract class BiomeProvider {
	public abstract int getBiome(BlockPos loc);
}
