package net.voxelmine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import net.voxelmine.blocks.Block;
import net.voxelmine.blocks.BlockPos;
import net.voxelmine.blocks.BlockRenderMode;
import net.voxelmine.chunk.Chunk;
import net.voxelmine.entity.EntityPos;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.world.World;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
	private static boolean[] keys = new boolean[256];
	private static boolean[] buttons = new boolean[MouseEvent.MOUSE_LAST];
	private static int mx = 0, my = 0;
	public Input() {
		for(int i = 0; i < 256; i++) {
			keys[i] = false;
		}
		for(int i = 0; i < MouseEvent.MOUSE_LAST; i++) {
			buttons[i] = false;
		}
	}
	public static boolean isKey(int key) {
		return keys[key];
	}
	public static boolean isButton(int button) {
		return buttons[button];
	}
	public static int getMouseX() {
		return mx;
	}
	public static int getMouseY() {
		return my;
	}
	public static BlockPos getMouseBlock() {
		EntityPos off = Voxelmine.getInstance().getPlayer().getLocation();
		World world = Voxelmine.getInstance().getWorld();
		int sz = Block.RENDER_SIZE;
		int sw = Voxelmine.WIDTH;
		int sh = Voxelmine.HEIGHT;
		int x = (int)((mx+off.getX()*sz-sw/2)/sz);
		int y = (int)(((sh-my)+off.getY()*sz-sh/2)/sz+1);
		int z;
		for(z = Chunk.DEPTH-1; z >= 0; z--) {
			if(Block.get(world.getBlock(new BlockPos(x, y, z))) != null) {
				if(Block.get(world.getBlock(new BlockPos(x, y, z))).getRenderMode() == BlockRenderMode.EMPTY) {
					break;
				}
			}
		}
		return new BlockPos(x, y, z+1);
	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		buttons[e.getButton()] = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void mouseDragged(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}
}
