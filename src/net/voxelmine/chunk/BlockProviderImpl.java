package net.voxelmine.chunk;

import java.util.Random;

import com.flowpowered.noise.module.source.Perlin;

import net.voxelmine.biome.Biome;
import net.voxelmine.blocks.Block;
import net.voxelmine.blocks.BlockPos;
import net.voxelmine.blocks.BlockRenderMode;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.world.World;

public class BlockProviderImpl extends BlockProvider{
	@Override
	public void generate(Chunk chunk, BlockPos cloc) {
		World world = Voxelmine.getInstance().getWorld();
		Perlin perlin = new Perlin();
		perlin.setSeed((int) world.getSeed());
		float lh = 0;
		for(int x = 0; x < Chunk.SIZE; x++) {
			for(int y = 0; y < Chunk.HEIGHT; y++) {
				int biome = chunk.getLocalBiome(new BlockPos(x, y));
				float h = (float)(perlin.getValue((x+chunk.getOffset().getX())*0.01f, 0, 0));
				h = h * 0.5f + 0.5f;
				float variance = 40.0f;
				float height = 50.0f;
				h /= y / variance;
				h += (height - y) / height;
				boolean m = h > 0.5f;
				if(m) {
					chunk.setLocalBlock(new BlockPos(x, y), Biome.get(biome).getFillerBlock());
				//}else if(y < h) {
				//	chunk.setLocalBlock(new BlockPos(x, y), Block.DIRT);
				}else {
					chunk.setLocalBlock(new BlockPos(x, y), Block.AIR.getId());
				}
				for(int z = 1; z < Chunk.DEPTH; z++) {
					chunk.setLocalBlock(new BlockPos(x, y, z), chunk.getLocalBlock(new BlockPos(x, y)));
				}
				lh = h;
			}
		}
		for(int x = 0; x < Chunk.SIZE; x++) {
			for(int y = 0; y < Chunk.HEIGHT; y++) {
				int biome = chunk.getLocalBiome(new BlockPos(x, y));
				int b1 = chunk.getLocalBlock(new BlockPos(x, y));
				if(b1 == Block.STONE.getId()) {
					int b2 = chunk.getLocalBlock(new BlockPos(x, y+1));
					int b3 = chunk.getLocalBlock(new BlockPos(x, y+3+new Random(1337+x*10000).nextInt()%2));
					if(b3 != Block.STONE.getId()) {
						for(int z = 0; z < Chunk.DEPTH; z++) {
							chunk.setLocalBlock(new BlockPos(x, y, z), Biome.get(biome).getSecondaryBlock());
						}
					}
					if(b2 != Block.STONE.getId()) {
						for(int z = 0; z < Chunk.DEPTH; z++) {
							chunk.setLocalBlock(new BlockPos(x, y, z), Biome.get(biome).getPrimaryBlock());
						}
					}
					/*if(generateCave(x, y)) {
						chunk.setLocalBlock(new BlockPos(x, y, 0), Block.AIR.getId());
					}*/
				}
			}
		}
		int x = Chunk.SIZE/2;
		for(int y = 0; y < Chunk.HEIGHT; y++) {
			if(new Random((cloc.getX()+x)*10000+world.getSeed()*10000).nextInt()%2 == 0 && chunk.getLocalBlock(new BlockPos(x, y)) == Block.GRASS.getId()) {
				generateTree(new BlockPos(chunk.getOffset().getX()+x, y+1));
			}
		}
	}
	public boolean generateCave(int x, int y) {
		World world = Voxelmine.getInstance().getWorld();
		Perlin perlin = new Perlin();
		perlin.setOctaveCount(1);
		perlin.setSeed((int)world.getSeed());
		float nx = x / 10.0f;
		float ny = y / 10.0f;
		float v1 = (float) perlin.getValue(nx, ny, 0);
		return v1 > -0.33f ? false : true;
	}
	@Override
	public void generateTree(BlockPos cloc) {
		int th = 3;
		World world = Voxelmine.getInstance().getWorld();
		for(int x = -2; x <= 2; x++) {
			for(int y = th-1; y <= th; y++) {
				for(int z = 0; z < Chunk.DEPTH; z++) {
					world.setBlockAsync(new BlockPos(cloc.getX()+x, cloc.getY()+y, z), Block.LEAVES.getId());
				}
			}
		}
		for(int x = -1; x <= 1; x++) {
			for(int z = 0; z < Chunk.DEPTH; z++) {
				world.setBlockAsync(new BlockPos(cloc.getX()+x, cloc.getY()+th+1, z), Block.LEAVES.getId());
			}
		}
		for(int z = 0; z < Chunk.DEPTH; z++) {
			world.setBlockAsync(new BlockPos(cloc.getX(), cloc.getY()+th+2, z), Block.LEAVES.getId());
		}
		for(int y = 0; y < th; y++) {
			world.setBlockAsync(new BlockPos(cloc.getX(), cloc.getY()+y, 1), Block.WOOD.getId());
		}
	}
}