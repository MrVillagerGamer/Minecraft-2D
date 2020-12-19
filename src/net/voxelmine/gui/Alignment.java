package net.voxelmine.gui;

public enum Alignment {
	CENTER(0.5f,0.5f),
	LEFT(0,0.5f),
	RIGHT(1,0.5f),
	TOP(0.5f,0),
	BOTTOM(0.5f,1),
	TOP_LEFT(0,0),
	TOP_RIGHT(1,0),
	BOTTOM_LEFT(0,1),
	BOTTOM_RIGHT(1,1);
	
	private float x, y;
	private Alignment(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
}
