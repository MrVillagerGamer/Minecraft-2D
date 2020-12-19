package net.voxelmine.world.gen;

import java.util.ArrayList;
import java.util.Random;

import net.voxelmine.blocks.BlockPos;
import net.voxelmine.chunk.Chunk;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.world.World;

public abstract class Feature {
	public static final Feature[] FEATURES = new Feature[256];
	//public static final Feature CASTLE = new FeatureStructureCastle(0);
	public static final Feature DIRT = new FeatureOreDirt(1);
	protected int id;
	private MaskMode biomeMaskMode;
	private ArrayList<Integer> maskBiomes;
	private int rarity;
	private int tries;
	public Feature(int id) {
		this.id = id;
		FEATURES[id] = this;
		biomeMaskMode = MaskMode.BLACKLIST;
		maskBiomes = new ArrayList<Integer>();
		rarity = 1; // try on every chunk
		tries = 1; // try once per chunk
	}
	public void setBiomeMaskMode(MaskMode maskMode) {
		biomeMaskMode = maskMode;
	}
	public boolean canGenerateInBiome(int id) {
		if(biomeMaskMode == MaskMode.WHITELIST) {
			return maskBiomes.contains(id);
		}else {
			return !maskBiomes.contains(id);
		}
	}
	/**
	 * Generates the feature throughout the world
	 */
	public void generate() {
		World world = Voxelmine.getInstance().getWorld();
		Random rand = new Random(world.getSeed()+id*10000);
		for(int cx = 0; cx < World.SIZE; cx++) {
			if((rand.nextInt()%rarity) == 0) {
				for(int i = 0; i < tries; i++) {
					int bx = rand.nextInt()%Chunk.SIZE;
					int by = rand.nextInt()%Chunk.HEIGHT;
					int bz = 0;//rand.nextInt()%Chunk.DEPTH;
					BlockPos pos = new BlockPos(cx*Chunk.SIZE+bx, by, bz);
					if(canGenerateAt(pos) && canGenerateInBiome(world.getBiome(pos))) {
						generateInstance(pos);
					}
				}
			}
		}
	}
	public void setRarity(int rarity) {
		this.rarity = rarity;
	}
	public void setTries(int tries) {
		this.tries = tries;
	}
	public abstract boolean canGenerateAt(BlockPos pos);
	public abstract void generateInstance(BlockPos pos);
}
