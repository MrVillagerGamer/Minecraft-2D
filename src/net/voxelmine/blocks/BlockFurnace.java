package net.voxelmine.blocks;

import net.voxelmine.gui.screen.Screen;

public class BlockFurnace extends Block {
	public BlockFurnace(int id, Material mat, int texX, int texY) {
		super(id, mat, texX, texY);
	}
	@Override
	public void onInteract(BlockPos pos) {
		super.onInteract(pos);
		if(Screen.getCurrentScreen() != Screen.SMELTING) {
			Screen.setCurrentScreen(Screen.SMELTING);
		}
	}
}
