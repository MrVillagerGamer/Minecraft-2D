package net.voxelmine.blocks;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.voxelmine.items.Item;
import net.voxelmine.items.ToolType;
import net.voxelmine.main.Voxelmine;

public class Block {
	public static final Block[] BLOCKS = new Block[256];
	public static final int RENDER_SIZE = Voxelmine.WIDTH/33;
	public static final Block AIR = new Block(0, BlockRenderMode.EMPTY, 0, 0);
	public static final Block GRASS = new Block(1, BlockRenderMode.SOLID, 0, 0).setToolType(ToolType.SHOVEL);
	public static final Block DIRT = new Block(2, BlockRenderMode.SOLID, 1, 0).setToolType(ToolType.SHOVEL);
	public static final Block STONE = new BlockStone(3, BlockRenderMode.SOLID, 2, 0).setToolType(ToolType.PICKAXE).setToolTier(1);
	public static final Block WOOD = new Block(4, BlockRenderMode.SOLID, 3, 0).setToolType(ToolType.AXE);
	public static final Block LEAVES = new BlockLeaves(5, BlockRenderMode.SOLID, 4, 0).setToolType(ToolType.AXE);
	public static final Block PLANKS = new Block(6, BlockRenderMode.SOLID, 5, 0).setToolType(ToolType.AXE);
	public static final Block SAPLING = new BlockSapling(7, BlockRenderMode.FLUID, 6, 0).setToolType(ToolType.AXE);
	public static final Block WORKBENCH = new BlockWorkbench(8, BlockRenderMode.SOLID, 7, 0).setToolType(ToolType.AXE);
	public static final Block FURNACE = new BlockFurnace(10, BlockRenderMode.SOLID, 8, 0).setToolType(ToolType.PICKAXE).setToolTier(1);
	public static final Block SAND = new Block(11, BlockRenderMode.SOLID, 9, 0).setToolType(ToolType.SHOVEL);
	public static final Block WATER = new BlockFluid(11, BlockRenderMode.FLUID, 10, 0).setToolType(ToolType.BUCKET);
	public static final Block CHEST = new BlockChest(12, BlockRenderMode.SOLID, 11, 0).setToolType(ToolType.AXE);

	
	public static final Block CRACK0 = new Block(246, BlockRenderMode.SOLID, 0, 15);
	public static final Block CRACK1 = new Block(247, BlockRenderMode.SOLID, 1, 15);
	public static final Block CRACK2 = new Block(248, BlockRenderMode.SOLID, 2, 15);
	public static final Block CRACK3 = new Block(249, BlockRenderMode.SOLID, 3, 15);
	public static final Block CRACK4 = new Block(250, BlockRenderMode.SOLID, 4, 15);
	public static final Block CRACK5 = new Block(251, BlockRenderMode.SOLID, 5, 15);
	public static final Block CRACK6 = new Block(252, BlockRenderMode.SOLID, 6, 15);
	public static final Block CRACK7 = new Block(253, BlockRenderMode.SOLID, 7, 15);
	public static final Block CRACK8 = new Block(254, BlockRenderMode.SOLID, 8, 15);
	public static final Block CRACK9 = new Block(255, BlockRenderMode.SOLID, 9, 15);
	public static Block get(int id) {
		if(id < 0 || id >= BLOCKS.length) return null;
		return BLOCKS[id];
	}
	private BlockRenderMode renderMode;
	protected static BufferedImage atlas;
	private BufferedImage img;
	private int toolTier;
	private ToolType toolType;
	private int id;
	protected BlockState defaultState;
	public Block(int id, BlockRenderMode renderMode, int texX, int texY) {
		this.renderMode = renderMode;
		this.toolTier = 0;
		if(atlas == null) {
			try {
				atlas = ImageIO.read(new File("src/atlas.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ModelData model = new ModelData(new ModelPiece(0, 0, 1, 1, texX, texY));
		model.bake(atlas);
		this.defaultState = new BlockState(model);
		BLOCKS[id] = this;
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public BlockRenderMode getRenderMode() {
		return renderMode;
	}
	public void onInteract(BlockPos pos) {
		
	}
	public Block setToolType(ToolType type) {
		toolType = type;
		return this;
	}
	public ToolType getToolType() {
		return toolType;
	}
	public Block setToolTier(int tier) {
		toolTier = tier;
		return this;
	}
	public int getToolTier() {
		return toolTier;
	}
	public int getDrop() {
		return Item.forBlock(this).getId();
	}
	public int getMetaFromState(BlockState state) {
		return 0;
	}
	public BlockState getStateFromMeta(int meta) {
		return defaultState;
	}
	public BlockState getDefaultState() {
		return defaultState;
	}
}