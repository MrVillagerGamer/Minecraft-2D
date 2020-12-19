package net.voxelmine.world;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import net.voxelmine.biome.Biome;
import net.voxelmine.blocks.Block;
import net.voxelmine.blocks.BlockPos;
import net.voxelmine.chunk.Chunk;
import net.voxelmine.entity.EntityPlayer;
import net.voxelmine.entity.EntityPos;
import net.voxelmine.entity.ItemStack;
import net.voxelmine.items.Item;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.tileentity.ITileEntity;
import net.voxelmine.tileentity.ITileEntityProvider;

public class WorldData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public float playerX, playerY;
	public ItemStack[] inv;
	public int[][][] blocks;
	public int[][] biomes;
	public long seed;
	public HashMap<BlockPos, String> tes;
	
	public static void setWorldData(WorldData data) {
		Voxelmine inst = Voxelmine.getInstance();
		World world = inst.getWorld();
		EntityPlayer player = inst.getPlayer();
		player.setLocation(new EntityPos(data.playerX, data.playerY));
		for(int i = 0; i < 36; i++) {
			player.getInventory().setStack(i, data.inv[i]);
		}
		for(int x = 0; x < World.SIZE*Chunk.SIZE; x++) {
			for(int y = 0; y < Chunk.HEIGHT; y++) {
				for(int z = 0; z < Chunk.DEPTH; z++) {
					world.setBlockAsync(new BlockPos(x, y, z), data.blocks[x][y][z]);
				}
				world.setBiome(new BlockPos(x, y), data.biomes[x][y]);
			}
		}
		world.generateLightMaps();
		world.setSeed(data.seed);
		HashMap<BlockPos, ITileEntity> tes = new HashMap<BlockPos, ITileEntity>();
		for(Entry<BlockPos, String> e : data.tes.entrySet()) {
			ITileEntityProvider tep = ((ITileEntityProvider)Block.get(world.getBlock(e.getKey())));
			ITileEntity te = tep.createNewTileEntity(e.getKey());
			te.readJson(e.getValue());
			tes.put(e.getKey(), te);
		}
		world.setTileEntities(tes);
	}
	
	public static WorldData getWorldData() {
		Voxelmine inst = Voxelmine.getInstance();
		World world = inst.getWorld();
		EntityPlayer player = inst.getPlayer();
		WorldData data = new WorldData();
		data.playerX = Voxelmine.getInstance().getPlayer().getLocation().getX();
		data.playerY = Voxelmine.getInstance().getPlayer().getLocation().getY();
		data.inv = new ItemStack[36];
		for(int i = 0; i < 36; i++) {
			data.inv[i] = player.getInventory().getStack(i);
		}
		data.blocks = new int[World.SIZE*Chunk.SIZE][Chunk.HEIGHT][Chunk.DEPTH];
		data.biomes = new int[World.SIZE*Chunk.SIZE][Chunk.HEIGHT];
		for(int x = 0; x < World.SIZE*Chunk.SIZE; x++) {
			for(int y = 0; y < Chunk.HEIGHT; y++) {
				for(int z = 0; z < Chunk.DEPTH; z++) {
					int b = world.getBlock(new BlockPos(x, y, z));
					data.blocks[x][y][z] = b;
				}
				int b = world.getBiome(new BlockPos(x, y));
				data.biomes[x][y] = b;
			}
		}
		data.seed = world.getSeed();
		HashMap<BlockPos, ITileEntity> tes = world.getTileEntities();
		data.tes = new HashMap<BlockPos, String>();
		for(Entry<BlockPos, ITileEntity> e : tes.entrySet()) {
			String json = e.getValue().writeJson();
			data.tes.put(e.getKey(), json);
		}
		return data;
	}
}
