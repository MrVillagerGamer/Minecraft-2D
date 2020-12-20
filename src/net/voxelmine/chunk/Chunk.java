package net.voxelmine.chunk;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.voxelmine.biome.Biome;
import net.voxelmine.biome.BiomeProvider;
import net.voxelmine.blocks.Block;
import net.voxelmine.blocks.BlockFluid;
import net.voxelmine.blocks.BlockPos;
import net.voxelmine.blocks.BlockRenderMode;
import net.voxelmine.blocks.Material;
import net.voxelmine.blocks.ModelPiece;
import net.voxelmine.entity.EntityPos;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.world.World;

public class Chunk {
	public static final int SIZE = 16;
	public static final int HEIGHT = 128;
	public static final int DEPTH = 2;
	private BlockPos offset;
	private int[][] biomeMap;
	private int[][][] blockMap;
	private int[][][] metaMap;
	private float[][] lightMap;
	private World world;
	public Chunk(World world) {
		offset = new BlockPos(0, 0);
		biomeMap = new int[SIZE][HEIGHT];
		blockMap = new int[SIZE][HEIGHT][DEPTH];
		metaMap = new int[SIZE][HEIGHT][DEPTH];
		lightMap = new float[SIZE][HEIGHT];
		this.world = world;
	}
	private int fluidTickCounter = 0;
	private boolean needsFluidUpdate = false;
	public void tick() {
		fluidTickCounter = (fluidTickCounter + 1) % 4;
		if(fluidTickCounter == 0 && needsFluidUpdate) {
			needsFluidUpdate = false;
			for(int x = 0; x < SIZE; x++) {
				for(int y = 0; y < HEIGHT; y++) {
					for(int z = 0; z < DEPTH; z++) {
						// If the block is a fluid
						if(Block.get(blockMap[x][y][z]) instanceof BlockFluid) {
							// If the fluid is deep enough to spread
							if(metaMap[x][y][z] < 14) {
								needsFluidUpdate = true;
								// Spread the fluid
								World world = Voxelmine.getInstance().getWorld();
								for(int i = -1; i <= 1; i++) {
									for(int k = 0; k <= 0; k++) {
										if(k == 0 && i == 0) {
											continue;
										}
										if(k != 0 && i != 0) {
											continue;
										}
										if(z+k < 0 || z+k >= DEPTH) {
											continue;
										}
										int j = y;
										int ax = x + offset.getX();
										Block b = Block.get(world.getBlock(new BlockPos(ax+i, j, z+k)));
										if(b != null && b.getRenderMode() != BlockRenderMode.SOLID) {
											while(b != null && b.getRenderMode() != BlockRenderMode.SOLID) {
												j--;
												b = Block.get(world.getBlock(new BlockPos(ax+i, j, z+k)));
											}
											b = Block.get(world.getBlock(new BlockPos(ax+i, j+1, z+k)));
											if(!(b instanceof BlockFluid) || world.getBlockMeta(new BlockPos(ax+i, j+1, z+k)) > metaMap[x][y][z]) {
												int div = i == 1 ? 2 : 3;
												if(k != 0) div = 1;
												int cmeta = 15 - metaMap[x][y][z];
												int nmeta = cmeta / div;
												if(b instanceof BlockFluid) nmeta += 15 - world.getBlockMeta(new BlockPos(ax+i, j+1, z+k));
												world.setBlockAsync(new BlockPos(ax+i, j+1, z+k), blockMap[x][y][z], 15 - nmeta);
												metaMap[x][y][z] = 15 - (cmeta - cmeta / div);
											}
											
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	public int getLocalBiome(BlockPos loc) {
		return biomeMap[loc.getX()][loc.getY()];
	}
	public void setLocalBiome(BlockPos loc, int biome) {
		biomeMap[loc.getX()][loc.getY()] = biome;
	}
	public void generateBiomeMap(BiomeProvider bp) {
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				biomeMap[i][j] = bp.getBiome(new BlockPos(i+offset.getX(), j+offset.getY()));
			}
		}
	}
	public void setLocalBlock(BlockPos loc, int id) {
		if(loc.getX() < 0 || loc.getX() >= SIZE) return;
		if(loc.getY() < 0 || loc.getY() >= HEIGHT) return;
		if(loc.getZ() < 0 || loc.getZ() >= DEPTH) return;
		int y = loc.getY();
		if(Block.get(id).getMaterial() == Material.WATER) {
			needsFluidUpdate = true;
			Block b = Block.get(blockMap[loc.getX()][y][loc.getZ()]);
			while(b != null && b.getRenderMode() != BlockRenderMode.SOLID) {
				y--;
				b = Block.get(blockMap[loc.getX()][y][loc.getZ()]);
			}
			y++;
		}
		blockMap[loc.getX()][y][loc.getZ()] = id;
		metaMap[loc.getX()][y][loc.getZ()] = Block.get(id).getMetaFromState(Block.get(id).getDefaultState());
	}
	public void setLocalBlockMeta(BlockPos loc, int meta) {
		if(loc.getX() < 0 || loc.getX() >= SIZE) return;
		if(loc.getY() < 0 || loc.getY() >= HEIGHT) return;
		metaMap[loc.getX()][loc.getY()][loc.getZ()] = meta;
	}
	public void generateBlockMap(BlockProvider bp) {
		bp.generate(this, offset);
		needsFluidUpdate = true;
	}
	public int getLocalBiome(int x, int z) {
		return biomeMap[x][z];
	}
	public void generateLightMap() {
		float b = 0;
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				for(int x = -8; x <= 8; x++) {
					for(int y = -8; y <= 8; y++) {
						int id = world.getBlock(new BlockPos(offset.getX()+x+i, offset.getY()+y+j, DEPTH-1));
						if(Block.get(id) != null && Block.get(id).getRenderMode() == BlockRenderMode.SOLID) {
							b++;
						}
					}
				}
				b = Math.max(0, b-17*17/2);
				b /= (17*17/2);
				lightMap[i][j] = b>1?1:(b<0?0:b);
			}
		}
	}
	public void renderChunkMesh(Graphics g, EntityPos off, int sx, int ex, int sy, int ey) {
		int sw = Voxelmine.WIDTH;
		int sh = Voxelmine.HEIGHT;
		int sz = Block.RENDER_SIZE;
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				for(int z = DEPTH-1; z >= 0; z--) {
					if(Block.get(blockMap[i][j][z]).getRenderMode() == BlockRenderMode.EMPTY) {
						continue;
					}
					if(i < sx || i > ex || j < sy || j > ey) {
						continue;
					}
					Block b = Block.get(blockMap[i][j][z]);
					for(ModelPiece piece : b.getStateFromMeta(metaMap[i][j][z]).getModel().getPieces()) {
						BufferedImage img = piece.getSideTex();
						int szw = (int) (img.getWidth() / 8f * sz);
						int szh = (int) (img.getHeight() / 8f * sz);
						int btox = (int) (piece.getMinX() * sz);
						int btoy = (int) (piece.getMinY() * sz);
						g.drawImage(img, (int)(i*sz-off.getX()*sz+offset.getX()*sz+sw/2+btox), (int)(sh-j*sz+off.getY()*sz+offset.getY()*sz-sh/2+btoy-sz*z*0.33f), szw, szh, null);
						img = piece.getTopTex();
						g.drawImage(img, (int)(i*sz-off.getX()*sz+offset.getX()*sz+sw/2+btox), (int)(sh-j*sz+off.getY()*sz+offset.getY()*sz-sh/2-sz*z*0.33f-sz*0.33f), szw, (int)(szh*0.33f), null);	

						g.setColor(new Color(0, 0, 0, Math.min(lightMap[i][j]+0.33f, 1)));
						g.fillRect((int)(i*sz-off.getX()*sz+offset.getX()*sz+sw/2+btox), (int)(sh-j*sz+off.getY()*sz+offset.getY()*sz-sh/2+btoy-sz*z*0.33f), szw, szh);
						g.setColor(new Color(0, 0, 0, lightMap[i][j]));
						g.fillRect((int)(i*sz-off.getX()*sz+offset.getX()*sz+sw/2+btox), (int)(sh-j*sz+off.getY()*sz+offset.getY()*sz-sh/2-sz*z*0.33f-sz*0.33f), szw, (int)(szh*0.33f));
					}
					
				}/*
				for(int z = DEPTH-1; z >= 0; z--) {
					if(Block.get(blockMap[i][j][z]).getRenderMode() != BlockRenderMode.FLUID) {
						continue;
					}
					if(i < sx || i > ex || j < sy || j > ey) {
						continue;
					}
					Block b = Block.get(blockMap[i][j][z]);
					for(ModelPiece piece : b.getStateFromMeta(metaMap[i][j][z]).getModel().getPieces()) {
						BufferedImage img = piece.getSideTex();
						int szw = (int) (img.getWidth() / 8f * sz);
						int szh = (int) (img.getHeight() / 8f * sz);
						int btox = (int) (piece.getMinX() * sz);
						int btoy = (int) (piece.getMinY() * sz);
						g.drawImage(img, (int)(i*sz-off.getX()*sz+offset.getX()*sz+sw/2+btox), (int)(sh-j*sz+off.getY()*sz+offset.getY()*sz-sh/2+btoy), szw, szh, null);
						if(z == 1) {
							g.setColor(new Color(0, 0, 0, lightMap[i][j]));
							g.fillRect((int)(i*sz-off.getX()*sz+offset.getX()*sz+sw/2+btox), (int)(sh-j*sz+off.getY()*sz+offset.getY()*sz-sh/2+btoy), szw, szh);
							g.fillRect((int)(i*sz-off.getX()*sz+offset.getX()*sz+sw/2+btox), (int)(sh-j*sz+off.getY()*sz+offset.getY()*sz-sh/2+btoy), szw, szh);
							g.fillRect((int)(i*sz-off.getX()*sz+offset.getX()*sz+sw/2+btox), (int)(sh-j*sz+off.getY()*sz+offset.getY()*sz-sh/2+btoy), szw, szh);
						}else {
							g.setColor(new Color(0, 0, 0, lightMap[i][j]));
							g.fillRect((int)(i*sz-off.getX()*sz+offset.getX()*sz+sw/2+btox), (int)(sh-j*sz+off.getY()*sz+offset.getY()*sz-sh/2+btoy), szw, szh);
						}
					}
					
				}*/
			}
		}
	}
	public int getLocalBlock(BlockPos loc) {
		if(loc.getX() < 0 || loc.getY() < 0 || loc.getX() >= SIZE || loc.getY() >= HEIGHT) {
			return 0;
		}
		return blockMap[loc.getX()][loc.getY()][loc.getZ()];
	}
	public BlockPos getOffset() {
		return offset;
	}
	public void setOffset(BlockPos offset) {
		this.offset = offset;
	}
	public void setOffset(int x, int y) {
		this.offset = new BlockPos(x, y);
	}
	public int getLocalBlockMeta(BlockPos loc) {
		if(loc.getX() < 0 || loc.getY() < 0 || loc.getX() >= SIZE || loc.getY() >= HEIGHT || loc.getZ() < 0 || loc.getZ() >= DEPTH) {
			return 0;
		}
		return blockMap[loc.getX()][loc.getY()][loc.getZ()];
	}
}
