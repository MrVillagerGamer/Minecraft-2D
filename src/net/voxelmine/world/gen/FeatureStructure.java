package net.voxelmine.world.gen;

import net.voxelmine.blocks.Block;
import net.voxelmine.blocks.BlockPos;
import net.voxelmine.blocks.BlockRenderMode;
import net.voxelmine.chunk.Chunk;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.world.World;

public class FeatureStructure extends Feature {
	private BlockPos center;
	private int[][][] blocks;
	private StructureGenMode genMode;
	private boolean ignoreAir;
	public FeatureStructure(int id) {
		super(id);
		center = new BlockPos(0, 0, 0);
		blocks = new int[0][0][2];
		genMode = StructureGenMode.SURFACE;
		ignoreAir = true;
	}
	public void setGenMode(StructureGenMode genMode) {
		this.genMode = genMode;
	}
	public void setIgnoreAir(boolean ignoreAir) {
		this.ignoreAir = ignoreAir;
	}
	public void setSize(int x, int y) {
		blocks = new int[x][y][2];
	}
	public void setCenter(BlockPos center) {
		this.center = center;
	}
	@Override
	public boolean canGenerateAt(BlockPos pos) {
		World world = Voxelmine.getInstance().getWorld();
		int minY = Chunk.HEIGHT;
		for(int x = 0; x < blocks.length; x++) {
			if(world.getHighest(x+pos.getX()-center.getX(), 0) < minY) {
				minY = world.getHighest(x+pos.getX()-center.getX(), 0);
			}
		}
		return pos.getY() == minY;
	}
	public void setBlockIntoStructure(BlockPos pos, int id) {
		blocks[pos.getX()][pos.getY()][pos.getZ()] = id;
	}
	@Override
	public void generateInstance(BlockPos pos) {
		World world = Voxelmine.getInstance().getWorld();
		for(int x = 0; x < blocks.length; x++) {
			for(int y = 0; y < blocks[x].length; y++) {
				for(int z = 0; z < blocks[x][y].length; z++) {
					BlockPos pos2 = new BlockPos(pos.getX()+x-center.getX(), pos.getY()+y-center.getY(), pos.getZ()+z-center.getZ());
					if(Block.get(blocks[x][y][z]) == null) continue;
					if(Block.get(blocks[x][y][z]).getRenderMode() == BlockRenderMode.EMPTY && ignoreAir) continue;
					world.setBlockAsync(pos2, blocks[x][y][z]);
				}
			}
		}
	}
}
