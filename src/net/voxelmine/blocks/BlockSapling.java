package net.voxelmine.blocks;

import net.voxelmine.main.Voxelmine;
import net.voxelmine.world.World;

public class BlockSapling extends Block{
	public BlockSapling(int id, Material mat, int texX, int texY) {
		super(id, mat, texX, texY);
	}
	@Override
	public void onInteract(BlockPos pos) {
		super.onInteract(pos);
		World world = Voxelmine.getInstance().getWorld();
		world.getBlockProvider().generateTree(pos);
	}
}
