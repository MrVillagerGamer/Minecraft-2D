package net.voxelmine.blocks;

import java.awt.image.BufferedImage;

public class ModelPiece {
	private float minX, minY, maxX, maxY;
	private int texX, texY;
	private BufferedImage img;
	public ModelPiece(float minX, float minY, float maxX, float maxY, int texX, int texY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.texX = texX;
		this.texY = texY;
	}
	public float getMinX() {
		return minX;
	}
	public float getMinY() {
		return minY;
	}
	public float getMaxX() {
		return maxX;
	}
	public float getMaxY() {
		return maxY;
	}
	public int getTexX() {
		return texX;
	}
	public int getTexY() {
		return texY;
	}
	public void setTex(BufferedImage img) {
		this.img = img;
	}
	public BufferedImage getTex() {
		return img;
	}
}
