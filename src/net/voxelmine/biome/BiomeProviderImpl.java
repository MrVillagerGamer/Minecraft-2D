package net.voxelmine.biome;

import com.flowpowered.noise.module.source.Perlin;

import net.voxelmine.blocks.BlockPos;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.world.World;

public class BiomeProviderImpl extends BiomeProvider {

	@Override
	public int getBiome(BlockPos loc) {
		World world = Voxelmine.getInstance().getWorld();
		Perlin perlin = new Perlin();
		perlin.setOctaveCount(2);
		perlin.setSeed((int)world.getSeed());
		int val = Math.max(Math.min((int)(perlin.getValue(loc.getX()/32.0D, 0, 0)+1), 1), 0);
		return val;
	}
	
}