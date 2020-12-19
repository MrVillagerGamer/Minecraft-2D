package net.voxelmine.world.gen;

import net.voxelmine.blocks.Block;
import net.voxelmine.blocks.BlockPos;
import net.voxelmine.chunk.Chunk;

public class FeatureStructureCastle extends FeatureStructure {
	public FeatureStructureCastle(int id) {
		super(id);
		setRarity(1);
		setTries(Chunk.HEIGHT/4);
		setSize(16, 7);
		setCenter(new BlockPos(8, 0));
		setIgnoreAir(false);
		for(int x = 0; x < 16; x++) {
			for(int y = 0; y < 6; y++) {
				setBlockIntoStructure(new BlockPos(x, y, 1), Block.STONE.getId());
			}
		}
		for(int x = 0; x < 16; x+=15) {
			for(int y = 3; y < 5; y++) {
				setBlockIntoStructure(new BlockPos(x, y, 0), Block.STONE.getId());
			}
		}
		for(int x = 0; x < 16; x++) {
			for(int y = 0; y < 7; y+=5) {
				setBlockIntoStructure(new BlockPos(x, y, 0), Block.STONE.getId());
			}
		}
		for(int x = 0; x < 16; x+=2) {
			setBlockIntoStructure(new BlockPos(x, 6, 0), Block.STONE.getId());
		}
	}
}
