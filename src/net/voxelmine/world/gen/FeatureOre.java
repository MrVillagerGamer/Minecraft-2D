package net.voxelmine.world.gen;

import net.voxelmine.blocks.Block;
import net.voxelmine.blocks.BlockPos;
import net.voxelmine.blocks.Material;
import net.voxelmine.chunk.Chunk;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.world.World;

public class FeatureOre extends Feature {
	private int minY, maxY, veinSize, block;
	public FeatureOre(int id) {
		super(id);
		minY = 0;
		maxY = Chunk.HEIGHT-1;
		veinSize = 2;
		block = 3;
	}
	public void setBlock(int id) {
		block = id;
	}
	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}
	public void setMinY(int minY) {
		this.minY = minY;
	}
	public void setVeinSize(int veinSize) {
		this.veinSize = veinSize;
	}
	public int getMaxY() {
		return maxY;
	}
	public int getMinY() {
		return minY;
	}
	public int getVeinSize() {
		return veinSize;
	}
	@Override
	public boolean canGenerateAt(BlockPos pos) {
		World world = Voxelmine.getInstance().getWorld();
		return pos.getY() >= minY && pos.getY() <= maxY && Block.get(world.getBlock(pos)).getMaterial() == Material.ROCK;
	}
	@Override
	public void generateInstance(BlockPos pos) {
		World world = Voxelmine.getInstance().getWorld();
		for(int x = -veinSize; x <= veinSize; x++) {
			for(int y = -veinSize; y <= veinSize; y++) {
				for(int z = -veinSize; z <= veinSize; z++) {
					BlockPos pos2 = new BlockPos(pos.getX()+x, pos.getY()+y, pos.getZ()+z);
					if(Block.get(world.getBlock(pos2)).getMaterial() == Material.ROCK) {
						float f = (float) Math.sqrt(x*x+y*y+z*z);
						if(f < veinSize) {
							world.setBlockAsync(pos2, block);
						}
					}
				}
			}
		}
	}
}
