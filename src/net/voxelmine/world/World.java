package net.voxelmine.world;

import java.awt.Graphics2D;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.voxelmine.biome.Biome;
import net.voxelmine.biome.BiomeProvider;
import net.voxelmine.blocks.BlockPos;
import net.voxelmine.blocks.Block;
import net.voxelmine.blocks.BlockRenderMode;
import net.voxelmine.chunk.BlockProvider;
import net.voxelmine.chunk.Chunk;
import net.voxelmine.entity.EntityPos;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.tileentity.ITileEntity;
import net.voxelmine.tileentity.ITileEntityProvider;
import net.voxelmine.util.ITickable;

public class World {
	public static final int SIZE = 16;
	private static Chunk[] chunks = new Chunk[SIZE];
	private BlockProvider blockProvider;
	private BiomeProvider biomeProvider;
	private HashMap<BlockPos, ITileEntity> tes;
	private long seed;
	public World(long seed, BiomeProvider biomeProvider, BlockProvider blockProvider) {
		this.blockProvider = blockProvider;
		this.biomeProvider = biomeProvider;
		this.seed = seed;
		tes = new HashMap<BlockPos, ITileEntity>();
	}
	public long getSeed() {
		return seed;
	}
	public void setSeed(long seed) {
		this.seed = seed;
	}
	public void generate() {
		for(int i = 0; i < SIZE; i++) {
			chunks[i] = new Chunk(this);
			chunks[i].setOffset(i*Chunk.SIZE, 0);
			chunks[i].generateBiomeMap(biomeProvider);
		}
		for(int i = 0; i < SIZE; i++) {
			chunks[i].generateBlockMap(blockProvider);
		}
		generateLightMaps();
	}
	public void generateLightMaps() {
		for(int i = 0; i < SIZE; i++) {
			chunks[i].generateLightMap();
		}
	}
	/** 
	 * WARNING: It is very dangerous to call this function outside of world loading.
	 * Doing so will erase all tile entity data, basically clearing block inventories,
	 * unless the supplied HashMap is the same as the old one.
	 * */
	public void setTileEntities(HashMap<BlockPos,ITileEntity> tes) {
		this.tes = tes;
	}
	public HashMap<BlockPos,ITileEntity> getTileEntities() {
		return tes;
	}
	public ITileEntity getTileEntity(BlockPos pos) {
		return tes.get(pos);
	}
	public void setTileEntity(BlockPos pos, ITileEntity te) {
		if(te == null) {
			if(tes.containsKey(pos)) {
				tes.remove(pos);
			}
		}else {
			tes.put(pos, te);
		}
	}
	public void setBlock(BlockPos loc, int id) {
		
		setBlockAsync(loc, id);
		
		int cx = loc.getX()/Chunk.SIZE;
		for(int x = cx-1; x <= cx+1; x++) {
			if(x < 0 || x >= SIZE) continue;
			chunks[x].generateLightMap();
		}
	}
	public void setBlockAsync(BlockPos loc, int id) {
		if(loc.getX() < 0 || loc.getX() >= SIZE*Chunk.SIZE || loc.getY() < 0 || loc.getY() >= Chunk.HEIGHT) {
			return;
		}
		if(Block.get(getBlock(loc)) instanceof ITileEntityProvider) {
			setTileEntity(loc, null);
		}
		BlockPos bloc = new BlockPos(loc.getX()%Chunk.SIZE, loc.getY(), loc.getZ());
		chunks[loc.getX()/Chunk.SIZE].setLocalBlock(bloc, id);
		if(Block.get(id) instanceof ITileEntityProvider) {
			setTileEntity(loc, ((ITileEntityProvider)Block.get(id)).createNewTileEntity(loc));
		}
	}
	public void setBlockAsync(BlockPos loc, int props, int meta) {
		if(loc.getX() < 0 || loc.getX() >= SIZE*Chunk.SIZE || loc.getY() < 0 || loc.getY() >= Chunk.HEIGHT) {
			return;
		}
		BlockPos bloc = new BlockPos(loc.getX()%Chunk.SIZE, loc.getY(), loc.getZ());
		chunks[loc.getX()/Chunk.SIZE].setLocalBlock(bloc, props);
		chunks[loc.getX()/Chunk.SIZE].setLocalBlockMeta(bloc, meta);
	}
	public void setBiome(BlockPos loc, int props) {
		if(loc.getX() < 0 || loc.getX() >= SIZE*Chunk.SIZE || loc.getY() < 0 || loc.getY() >= Chunk.HEIGHT) {
			return;
		}
		BlockPos bloc = new BlockPos(loc.getX()%Chunk.SIZE, loc.getY());
		chunks[loc.getX()/Chunk.SIZE].setLocalBiome(bloc, props);
	}
	public int getBiome(BlockPos loc) {
		if(loc.getX() < 0 || loc.getX() >= SIZE*Chunk.SIZE || loc.getY() < 0 || loc.getY() >= Chunk.HEIGHT) {
			return 0;
		}
		BlockPos bloc = new BlockPos(loc.getX()%Chunk.SIZE, loc.getY());
		return chunks[loc.getX()/Chunk.SIZE].getLocalBiome(bloc);
	}
	public int getBlock(BlockPos loc) {
		if(loc.getX() < 0 || loc.getX() >= SIZE*Chunk.SIZE || loc.getY() < 0 || loc.getY() >= Chunk.HEIGHT || loc.getZ() < 0 || loc.getZ() >= Chunk.DEPTH) {
			return 0;
		}
		BlockPos bloc = new BlockPos(loc.getX()%Chunk.SIZE, loc.getY(), loc.getZ());
		return chunks[loc.getX()/Chunk.SIZE].getLocalBlock(bloc);
	}
	public int getBlockMeta(BlockPos loc) {
		if(loc.getX() < 0 || loc.getX() >= SIZE*Chunk.SIZE || loc.getY() < 0 || loc.getY() >= Chunk.HEIGHT || loc.getZ() < 0 || loc.getZ() >= Chunk.DEPTH) {
			return 0;
		}
		BlockPos bloc = new BlockPos(loc.getX()%Chunk.SIZE, loc.getY(), loc.getZ());
		return chunks[loc.getX()/Chunk.SIZE].getLocalBlockMeta(bloc);
	}
	public EntityPos calcSpawnLocation() {
		int height = 0;
		for(int i = Chunk.HEIGHT-1; i >= 0; i--) {
			if(Block.get(getBlock(new BlockPos(SIZE*Chunk.SIZE/2, i))).getRenderMode() != BlockRenderMode.EMPTY) {
				height = i+5;
				break;
			}
		}
		return new EntityPos(SIZE*Chunk.SIZE/2, height);
	}
	public void render(Graphics2D g) {
		EntityPos off = Voxelmine.getInstance().getPlayer().getLocation();
		for(Chunk chunk : chunks) {
			int sx = (int)off.getX() - Voxelmine.WIDTH/Block.RENDER_SIZE;
			int ex = (int)off.getX() + Voxelmine.WIDTH/Block.RENDER_SIZE;
			int sy = (int)off.getY() - Voxelmine.HEIGHT/Block.RENDER_SIZE;
			int ey = (int)off.getY() + Voxelmine.HEIGHT/Block.RENDER_SIZE;
			sx = Math.max(sx - chunk.getOffset().getX(), 0);
			ex = Math.max(ex - chunk.getOffset().getX(), 0);
			chunk.renderChunkMesh(g, off, sx, ex, sy, ey);
		}
	}
	public BlockProvider getBlockProvider() {
		return blockProvider;
	}
	public BiomeProvider getBiomeProvider() {
		return biomeProvider;
	}
	public void tick() {
		for(int i = 0; i < SIZE; i++) {
			chunks[i].tick();
		}
		for(Entry<BlockPos, ITileEntity> e : tes.entrySet()) {
			if(e.getValue() instanceof ITickable) {
				((ITickable)e.getValue()).tick();
			}
		}
	}
}