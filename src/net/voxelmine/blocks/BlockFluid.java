package net.voxelmine.blocks;

public class BlockFluid extends Block {
	private BlockState[] states;
	public BlockFluid(int id, Material mat, int texX, int texY) {
		super(id, mat, texX, texY);
		states = new BlockState[16];
		for(int i = 0; i < 16; i++) {
			ModelData model = new ModelData(new ModelPiece(0, 1f/8f*(i/2), 1, 1, texX, texY, texX, texY));
			model.bake(atlas);
			states[i] = new BlockState(model);
		}
		this.defaultState = states[3];
	}
	@Override
	public BlockState getStateFromMeta(int meta) {
		return states[meta];
	}
	@Override
	public int getMetaFromState(BlockState state) {
		int meta = 0;
		for(int i = 0; i < 16; i++) {
			if(state == states[i]) {
				meta = i;
			}
		}
		return meta;
	}
}
