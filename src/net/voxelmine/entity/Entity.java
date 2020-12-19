package net.voxelmine.entity;

import java.awt.Graphics2D;

import net.voxelmine.blocks.Block;
import net.voxelmine.main.Voxelmine;

public abstract class Entity {
	protected EntityPos loc;
	protected int health, maxHealth;
	public Entity(EntityPos loc) {
		this.loc = loc;
		this.maxHealth = 10;
		this.health = 10;
	}
	public int addHealth(int health) {
		int oldHealth = this.health;
		this.health = Math.min(Math.max(this.health + health, 0), maxHealth);
		return this.health - oldHealth;
	}
	public boolean setHealth(int health) {
		if(health <= maxHealth) {
			this.health = health;
			return true;
		}
		return false;
	}
	public int getHealth() {
		return health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public int getRenderPosX() {
		int x = (int) ((loc.getX() - Voxelmine.getInstance().getPlayer().getLocation().getX())*Block.RENDER_SIZE+Voxelmine.WIDTH/2);
		return x;
	}
	public int getRenderPosY() {
		int x = (int) ((loc.getY() - Voxelmine.getInstance().getPlayer().getLocation().getY())*Block.RENDER_SIZE+Voxelmine.HEIGHT/2);
		return x;
	}
	public abstract void onUpdate(float delta);
	public abstract void onWorldRender(Graphics2D g);
	public abstract void onRender(Graphics2D g);
	public EntityPos getLocation() {return loc;}
	public void setLocation(EntityPos loc) {this.loc = loc;}
}
