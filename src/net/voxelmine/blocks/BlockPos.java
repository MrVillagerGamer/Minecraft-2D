package net.voxelmine.blocks;

import java.io.Serializable;
import java.util.Objects;

public class BlockPos implements Serializable{
	private int x, y, z;
	public BlockPos(int x, int y) {
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	public BlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getZ() {
		return z;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BlockPos) {
			BlockPos pos = (BlockPos)obj;
			return pos.x == x && pos.y == y && pos.z == z;
		}
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}
}
