package net.voxelmine.biome;

import java.util.ArrayList;

import net.voxelmine.blocks.Block;

public class Biome {
	// The BIOMES array must NOT have any null values!
	public static final Biome[] BIOMES = new Biome[2];
	public static final Biome PLAINS = new Biome(0, Block.GRASS.getId(), Block.DIRT.getId(), Block.STONE.getId());
	public static final Biome DESERT = new Biome(1, Block.SAND.getId(), Block.SAND.getId(), Block.STONE.getId());
	public static Biome get(int id) {
		if(id < 0 || id >= BIOMES.length) return null;
		return BIOMES[id];
	}
	private int id, primary, secondary, filler;
	public Biome(int id, int primary, int secondary, int filler) {
		this.primary = primary;
		this.secondary = secondary;
		this.filler = filler;
		BIOMES[id] = this;
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public int getPrimaryBlock() {
		return primary;
	}
	public int getSecondaryBlock() {
		return secondary;
	}
	public int getFillerBlock() {
		return filler;
	}
}