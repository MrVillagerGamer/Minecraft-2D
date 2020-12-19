package net.voxelmine.tileentity;

public interface ITileEntity {
	public String writeJson();
	public void readJson(String gson);
}
