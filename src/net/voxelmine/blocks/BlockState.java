package net.voxelmine.blocks;

public class BlockState {
	private ModelData model;
	public BlockState(ModelData model) {
		this.model = model;
	}
	public ModelData getModel() {
		return model;
	}
}
