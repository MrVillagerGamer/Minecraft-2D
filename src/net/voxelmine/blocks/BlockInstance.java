package net.voxelmine.blocks;

public class BlockInstance {
	private Block prop;
	//private BlockLocation loc;
	public BlockInstance(Block prop, BlockPos loc) {
		this.prop = prop;
		//this.loc = loc;
	}
	/*
	public BlockLocation getLocation() {
		return loc;
	}
	*/
	public Block getProperties() {
		return prop;
	}
}
