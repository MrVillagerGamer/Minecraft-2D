package net.voxelmine.blocks;

import net.voxelmine.main.Voxelmine;
import net.voxelmine.world.World;

public class BlockSapling extends Block{
	public BlockSapling(int id, BlockRenderMode renderMode, int texX, int texY) {
		super(id, renderMode, texX, texY);
	}
	@Override
	public void onInteract(BlockPos pos) {
		super.onInteract(pos);
		World world = Voxelmine.getInstance().getWorld();
		world.getBlockProvider().generateTree(pos);
	}
}
