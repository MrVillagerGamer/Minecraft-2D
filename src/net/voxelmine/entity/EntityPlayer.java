package net.voxelmine.entity;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.voxelmine.blocks.Block;
import net.voxelmine.blocks.BlockPos;
import net.voxelmine.blocks.BlockRenderMode;
import net.voxelmine.chunk.Chunk;
import net.voxelmine.gui.screen.HUDScreen;
import net.voxelmine.gui.screen.Screen;
import net.voxelmine.input.Input;
import net.voxelmine.items.Item;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.tileentity.ITileEntity;
import net.voxelmine.tileentity.ITileEntityProvider;
import net.voxelmine.world.World;

public class EntityPlayer extends Entity{
	private Inventory inv;
	private BufferedImage skin;
	private BufferedImage leftArmLeft, rightArmLeft;
	private BufferedImage leftArmRight, rightArmRight;
	private BufferedImage leftLegLeft, rightLegLeft;
	private BufferedImage leftLegRight, rightLegRight;
	private BufferedImage headLeft, headRight;
	private BufferedImage bodyLeft, bodyRight;
	private BufferedImage leftArmFront, rightArmFront;
	private BufferedImage leftLegFront, rightLegFront;
	private BufferedImage headFront, bodyFront;
	private boolean escLock;
	private float breakTime = 0;
	private BlockPos breakLoc = new BlockPos(0, 0);
	private float jumpTime = 0;
	private boolean jumping = false;
	private boolean grounded = false;
	public EntityPlayer(EntityPos loc) {
		super(loc);
		maxHealth = 20;
		health = 20;
		inv = new InventoryPlayer(36);
		try {
			skin = ImageIO.read(new File("src/skin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		leftArmLeft = skin.getSubimage(40, 20, 4, 12);
		rightArmLeft = skin.getSubimage(40, 20, 4, 12);
		leftArmRight = skin.getSubimage(48, 20, 4, 12);
		rightArmRight = skin.getSubimage(48, 20, 4, 12);
		
		rightArmFront = skin.getSubimage(44, 20, 4, 12);
		leftArmFront = skin.getSubimage(44, 20, 4, 12);

		leftLegLeft = skin.getSubimage(0, 20, 4, 12);
		rightLegLeft = skin.getSubimage(0, 20, 4, 12);
		leftLegRight = skin.getSubimage(8, 20, 4, 12);
		rightLegRight = skin.getSubimage(8, 20, 4, 12);
		
		rightLegFront = skin.getSubimage(4, 20, 4, 12);
		leftLegFront = skin.getSubimage(4, 20, 4, 12);

		headLeft = skin.getSubimage(0, 8, 8, 8);
		headRight = skin.getSubimage(16, 8, 8, 8);
		bodyLeft = skin.getSubimage(16, 20, 4, 12);
		bodyRight = skin.getSubimage(28, 20, 4, 12);

		headFront = skin.getSubimage(8, 8, 8, 8);
		bodyFront = skin.getSubimage(20, 20, 8, 12);
	}
	public Inventory getInventory() {
		return inv;
	}
	private boolean mb3lock;
	private float fallStartY = 0;
	private boolean moveLeft = false, moveRight = true, moving = true;
	@Override
	public void onUpdate(float delta) {
		float dx = 0, dy = 0;
		if(Input.isKey(KeyEvent.VK_D)) {
			dx = 5*delta;
			moveRight = true;
			moveLeft = false;
			moving = true;
		}else if(Input.isKey(KeyEvent.VK_A)) {
			dx = -5*delta;
			moveRight = false;
			moveLeft = true;
			moving = true;
		}else {
			moving = false;
		}
		if(Input.isKey(KeyEvent.VK_E)) {
			if(Screen.getCurrentScreen() instanceof HUDScreen) {
				Screen.setCurrentScreen(Screen.INVENTORY);
			}
		}
		if(!Input.isKey(KeyEvent.VK_ESCAPE)) {
			escLock = false;
		}
		if(Input.isKey(KeyEvent.VK_ESCAPE) && !escLock) {
			escLock = true;
			if(!(Screen.getCurrentScreen() instanceof HUDScreen)) {
				Screen.setCurrentScreen(Screen.HUD);
			}else {
				Screen.setCurrentScreen(Screen.PAUSE);
			}
		}
		BlockPos mouseBlock = Input.getMouseBlock();
		boolean isHud = Screen.getCurrentScreen() == Screen.HUD;
		World world = Voxelmine.getInstance().getWorld();
		BlockPos mouseBlockFront = new BlockPos(mouseBlock.getX(), mouseBlock.getY(), Math.max(mouseBlock.getZ()-1, 0));
		if(!Input.isButton(MouseEvent.BUTTON3)) {
			mb3lock = false;
		}
		if(Input.isButton(MouseEvent.BUTTON3) && isHud && !mb3lock) {
			mb3lock = true;
			if(Block.get(world.getBlock(mouseBlock)).getRenderMode() != BlockRenderMode.EMPTY) Block.get(world.getBlock(mouseBlock)).onInteract(mouseBlock);
			int sel = inv.getSelectedSlot();
			int selItem = inv.getStack(sel).getItem();
			int selCount = inv.getStack(sel).getCount();
			String selJsonData = inv.getStack(sel).getJsonData();
			if(selCount > 0 && Item.get(selItem) != null && Block.get(selItem) != null) {
				if(placeBlock(mouseBlockFront, selItem, selJsonData)) {
					inv.getStack(sel).setCount(selCount-1);
				}
			}
		}
		if(Input.isButton(MouseEvent.BUTTON1) && isHud) {
			if(breakLoc.getX() == mouseBlock.getX()
					&& breakLoc.getY() == mouseBlock.getY()) {
				breakTime += delta;
				int bb = getBlock(mouseBlock);
				if(breakTime >= computeDestroyTime(bb)) {
					inv.addItems(Block.get(bb).getDrop(mouseBlock));
					breakBlock(mouseBlock);
					breakTime = 0;
					breakLoc = new BlockPos(0, 0);
				}
			}else {
				breakTime = 0;
				breakLoc = mouseBlock;
			}
		}else {
			breakTime = 0;
			breakLoc = new BlockPos(0, 0);
		}
		collidedMove(dx, 0);
		float oldY = loc.getY();
		for(int i = 0; i < 10; i++) {
			grounded = !collidedMove(0, -delta);
		}
		float newY = loc.getY();
		if(newY == oldY) {
			int damage = (int) Math.max(fallStartY-newY-2, 0);
			addHealth(-damage);
		}
		if(newY >= oldY) {

			fallStartY = newY;
		}
		if(grounded && Input.isKey(KeyEvent.VK_SPACE)) {
			jumping = true;
		}
		if(jumping) {
			jumpTime += delta;
			collidedMove(0, delta*20);
			if(jumpTime >= 0.1f) {
				jumping = false;
				jumpTime = 0;
			}
		}
	}
	public boolean placeBlock(BlockPos mouseBlock, int block, String jsonData) {
		World world = Voxelmine.getInstance().getWorld();
		BlockPos pos = mouseBlock;
		if(pos.getZ() >= 0 && pos.getZ() < Chunk.DEPTH && Block.get(world.getBlock(pos)) != null && Block.get(world.getBlock(pos)).getRenderMode() == BlockRenderMode.EMPTY) {
			BlockPos pos2 = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
			world.setBlock(pos2, block);
			ITileEntity te = world.getTileEntity(pos2);
			if(te != null) {
				te.readJson(jsonData);
			}
			return true;
		}
		return false;
	}
	public int getBlock(BlockPos mouseBlock) {
		World world = Voxelmine.getInstance().getWorld();
		if(mouseBlock.getZ() >= Chunk.DEPTH) {
			//mouseBlock = new BlockPos(mouseBlock.getX(), mouseBlock.getY(), Chunk.DEPTH-1);
			return 0;
		}
		int b2 = world.getBlock(mouseBlock);
		return b2;
	}
	public int breakBlock(BlockPos mouseBlock) {
		World world = Voxelmine.getInstance().getWorld();
		int bb = getBlock(mouseBlock);
		world.setBlock(mouseBlock, Block.AIR.getId());
		return bb;
	}
	private float computeDestroyTime(int block) {
		return computeDestroyTime(block, inv.getStack(inv.getSelectedSlot()).getItem());
	}
	private float computeDestroyTime(int blockId, int selItemId) {
		Block block = Block.get(blockId);
		Item selItem = Item.get(selItemId);
		float dt1 = 1 + block.getToolTier();
		float dt2 = 1;
		if(selItem != null && selItem.getToolType() == block.getToolType()) {
			dt2 += selItem.getToolTier();
		}
		if(selItem != null && block.getToolTier() > selItem.getToolTier() || (selItem != null && selItem.getToolType() != block.getToolType() && block.getToolTier() != 0)) {
			return 1000000;
		}
		if(selItem == null && block.getToolTier() > 0) {
			return 1000000;
		}
		return dt1/dt2;
	}
	private boolean collidedMove(float dx, float dy) {
		World world = Voxelmine.getInstance().getWorld();
		EntityPos loc = getLocation();
		EntityPos newLoc = new EntityPos(loc.getX()+dx, loc.getY()+dy);
		BlockPos test1 = new BlockPos((int)newLoc.getX(), (int)newLoc.getY());
		BlockPos test2 = new BlockPos((int)newLoc.getX(), (int)newLoc.getY()+1);
		
		if((Block.get(world.getBlock(test1)) == null || Block.get(world.getBlock(test1)).getRenderMode() != BlockRenderMode.SOLID) && (Block.get(world.getBlock(test2)) == null || Block.get(world.getBlock(test2)).getRenderMode() != BlockRenderMode.SOLID)) {
			setLocation(newLoc);
			return true;
		}
		return false;
	}
	@Override
	public void onWorldRender(Graphics2D g) {
		BlockPos mouseBlock = Input.getMouseBlock();
		if(mouseBlock.getZ() >= Chunk.DEPTH) return;
		int sz = Block.RENDER_SIZE;
		int x = breakLoc.getX();
		int y = breakLoc.getY();
		EntityPos off = getLocation();
		int sw = Voxelmine.WIDTH;
		int sh = Voxelmine.HEIGHT;
		World world = Voxelmine.getInstance().getWorld();
		int bb = world.getBlock(mouseBlock);
		if(Block.get(bb) != null) {
			if(breakTime != 0 && Block.get(bb).getRenderMode() == BlockRenderMode.SOLID) {
				Block[] cracks = new Block[] {
						Block.CRACK0, Block.CRACK1,
						Block.CRACK2, Block.CRACK3,
						Block.CRACK4, Block.CRACK5,
						Block.CRACK6, Block.CRACK7,
						Block.CRACK8, Block.CRACK9,
				};
				float time = computeDestroyTime(bb);
				if((int)(breakTime/time*10.0f) <= 9) {
					BufferedImage img = cracks[(int)(breakTime/time*10.0f)].getStateFromMeta(0).getModel().getPieces()[0].getTex();
					g.drawImage(img, (int)(x*sz-off.getX()*sz+sw/2), (int)(sh-y*sz+off.getY()*sz-sh/2), sz, sz, null);
				}
			}
		}
	}
	@Override
	public void onRender(Graphics2D g) {
		int sz = Block.RENDER_SIZE/16;
		int px = getRenderPosX()+Block.RENDER_SIZE/8;
		int py = getRenderPosY()+Block.RENDER_SIZE;
		if(moving) {
			if(moveLeft) {
				g.drawImage(bodyRight, px-2*sz, py-24*sz, sz*4, sz*12, null);
				g.drawImage(headRight, px-4*sz, py-32*sz, sz*8, sz*8, null);
				g.drawImage(leftArmRight, px-2*sz, py-24*sz, sz*4, sz*12, null);
				g.drawImage(rightArmRight, px-2*sz, py-24*sz, sz*4, sz*12, null);
				g.drawImage(leftLegRight, px-2*sz, py-12*sz, sz*4, sz*12, null);
				g.drawImage(rightLegRight, px-2*sz, py-12*sz, sz*4, sz*12, null);
			}else if(moveRight){
				g.drawImage(bodyLeft, px-2*sz, py-24*sz, sz*4, sz*12, null);
				g.drawImage(headLeft, px-4*sz, py-32*sz, sz*8, sz*8, null);
				g.drawImage(rightArmLeft, px-2*sz, py-24*sz, sz*4, sz*12, null);
				g.drawImage(leftArmLeft, px-2*sz, py-24*sz, sz*4, sz*12, null);
				g.drawImage(rightLegLeft, px-2*sz, py-12*sz, sz*4, sz*12, null);
				g.drawImage(leftLegLeft, px-2*sz, py-12*sz, sz*4, sz*12, null);
			}
		}else {
			g.drawImage(bodyFront, px-4*sz, py-24*sz, sz*8, sz*12, null);
			g.drawImage(headFront, px-4*sz, py-32*sz, sz*8, sz*8, null);
			g.drawImage(leftArmFront, px-8*sz, py-24*sz, sz*4, sz*12, null);
			g.drawImage(rightArmFront, px+4*sz, py-24*sz, sz*4, sz*12, null);
			g.drawImage(leftLegFront, px-4*sz, py-12*sz, sz*4, sz*12, null);
			g.drawImage(rightLegFront, px+0*sz, py-12*sz, sz*4, sz*12, null);
		}
	}
}