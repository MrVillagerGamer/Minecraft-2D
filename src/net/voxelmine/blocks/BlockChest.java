package net.voxelmine.blocks;

import net.voxelmine.gui.screen.ChestScreen;
import net.voxelmine.gui.screen.Screen;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.tileentity.ChestTileEntity;
import net.voxelmine.tileentity.ITileEntity;
import net.voxelmine.tileentity.ITileEntityProvider;
import net.voxelmine.world.World;

public class BlockChest extends Block implements ITileEntityProvider {
	public BlockChest(int id, BlockRenderMode renderMode, int texX, int texY) {
		super(id, renderMode, texX, texY);
	}
	@Override
	public ITileEntity createNewTileEntity(BlockPos pos) {
		return new ChestTileEntity();
	}
	@Override
	public void onInteract(BlockPos pos) {
		World world = Voxelmine.getInstance().getWorld();
		ITileEntity te = world.getTileEntity(pos);
		((ChestScreen)Screen.CHEST).setChestInventory(((ChestTileEntity)te).getInventory());
		Screen.setCurrentScreen(Screen.CHEST);
	}
}
