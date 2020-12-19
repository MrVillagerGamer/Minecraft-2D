package net.voxelmine.gui.screen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.voxelmine.entity.Entity;
import net.voxelmine.gui.component.GuiComponent;
import net.voxelmine.main.Voxelmine;

public class HUDScreen extends Screen{
	private BufferedImage fullHeart, halfHeart;
	public HUDScreen() {
		super();
		fullHeart = gui.getSubimage(256-16, 0, 8, 8);
		halfHeart = gui.getSubimage(256-8, 0, 8, 8);
	}
	
	@Override
	public void onUpdate(float delta) {
		
	}
	@Override
	public void onRender(Graphics g) {
		Voxelmine vox = Voxelmine.getInstance();
		Entity player = vox.getPlayer();
		int currentHealth = player.getHealth();
		int idx = 0;
		int sz = 10 * GuiComponent.SCALE;
		while(true) {
			int remaining = currentHealth > 2 ? 0 : currentHealth % 2;
			
			if(remaining == 0) {
				g.drawImage(fullHeart, idx * sz + sz / 2, sz / 2, sz, sz, null);
			}else if(remaining == 1) {
				g.drawImage(halfHeart, idx * sz + sz / 2, sz / 2, sz, sz, null);
			}
			currentHealth -= 2;
			if(currentHealth <= 0) {
				break;
			}
			idx++;
		}
	}
	@Override
	public void onOpen() {
		
	}
	@Override
	public void onClose() {
		
	}
}