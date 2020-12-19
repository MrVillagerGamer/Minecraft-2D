package net.voxelmine.tileentity;

import net.voxelmine.blocks.BlockPos;

public interface ITileEntityProvider {
	public ITileEntity createNewTileEntity(BlockPos pos);
}
