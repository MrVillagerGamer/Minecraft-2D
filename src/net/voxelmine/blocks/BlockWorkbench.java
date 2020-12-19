package net.voxelmine.blocks;

import net.voxelmine.gui.screen.Screen;

public class BlockWorkbench extends Block{
	public BlockWorkbench(int id, Material mat, int texX, int texY) {
		super(id, mat, texX, texY);
	}
	@Override
	public void onInteract(BlockPos pos) {
		super.onInteract(pos);
		if(Screen.getCurrentScreen() != Screen.CRAFTING) {
			Screen.setCurrentScreen(Screen.CRAFTING);
		}
	}
}
