package net.voxelmine.blocks;

import java.awt.image.BufferedImage;

public class ModelPiece {
	private float minX, minY, maxX, maxY;
	private int sideTexX, sideTexY, topTexX, topTexY;
	private BufferedImage topTex, sideTex;
	public ModelPiece(float minX, float minY, float maxX, float maxY, int sideTexX, int sideTexY, int topTexX, int topTexY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.sideTexX = sideTexX;
		this.sideTexY = sideTexY;
		this.topTexX = topTexX;
		this.topTexY = topTexY;
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
	public int getSideTexX() {
		return sideTexX;
	}
	public void setSideTexX(int sideTexX) {
		this.sideTexX = sideTexX;
	}
	public int getSideTexY() {
		return sideTexY;
	}
	public void setSideTexY(int sideTexY) {
		this.sideTexY = sideTexY;
	}
	public int getTopTexX() {
		return topTexX;
	}
	public void setTopTexX(int topTexX) {
		this.topTexX = topTexX;
	}
	public int getTopTexY() {
		return topTexY;
	}
	public void setTopTexY(int topTexY) {
		this.topTexY = topTexY;
	}
	public BufferedImage getTopTex() {
		return topTex;
	}
	public void setTopTex(BufferedImage topTex) {
		this.topTex = topTex;
	}
	public BufferedImage getSideTex() {
		return sideTex;
	}
	public void setSideTex(BufferedImage sideTex) {
		this.sideTex = sideTex;
	}
	public void setMinX(float minX) {
		this.minX = minX;
	}
	public void setMinY(float minY) {
		this.minY = minY;
	}
	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}
	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}
	
}
