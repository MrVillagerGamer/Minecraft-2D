package net.voxelmine.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WorldUtil {
	public static WorldData load(String path) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)));
		WorldData wdat = (WorldData)ois.readObject();
		ois.close();
		System.out.println("[INFO] Loaded world");
		return wdat;
		/*WorldData result = new WorldData();
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		ArrayList<String> strs = new ArrayList<>();
		String line = "";
		String cur = "";
		while((line = br.readLine()) != null) {
			if(line.contains("=") && cur.contains("=")) {
				strs.add(cur);
				cur = "";
			}
			cur += line.replace("\n", " ");
		}
		br.close();
		result.invItems = new int[36];
		result.invCounts = new int[36];
		result.blocks = new int[World.SIZE*Chunk.SIZE][Chunk.HEIGHT];
		result.biomes = new int[World.SIZE*Chunk.SIZE][Chunk.HEIGHT];
		for(String str : strs) {
			String[] toks = str.split("\\=");
			String val = toks[1].trim();
			String key = toks[0].trim();
			if(key.equals("voxelmine.format")) {
				continue;
			}else if(key.equals("player.position")) {
				String[] toks2 = val.split("\\s+");
				result.playerX = Float.parseFloat(toks2[0]);
				result.playerY = Float.parseFloat(toks2[1]);
			}else if(key.equals("inventory.items")) {
				String[] toks2 = val.split("\\s+");
				for(int i = 0; i < 36; i++) {
					int item = Integer.parseInt(toks2[i]);
					result.invItems[i] = item;
				}
			}else if(key.equals("inventory.counts")) {
				String[] toks2 = val.split("\\s+");
				for(int i = 0; i < 36; i++) {
					int count = Integer.parseInt(toks2[i]);
					result.invCounts[i] = count;
				}
			}else if(key.equals("world.blocks")) {
				String[] toks2 = val.split("\\s+");
				int i = 0;
				for(int x = 0; x < World.SIZE*Chunk.SIZE; x++) {
					for(int y = 0; y < Chunk.HEIGHT; y++) {
						result.blocks[x][y][0] = Integer.parseInt(toks2[i]);
						result.blocks[x][y][1] = Integer.parseInt(toks2[i+1]);
						i+=2;
					}
				}
			}else if(key.equals("world.biomes")) {
				String[] toks2 = val.split("\\s+");
				int i = 0;
				for(int x = 0; x < World.SIZE*Chunk.SIZE; x++) {
					for(int y = 0; y < Chunk.HEIGHT; y++) {
						result.biomes[x][y] = Integer.parseInt(toks2[i]);
						i++;
					}
				}
			}	
		}
		System.out.println("[INFO] Loaded world");
		return result;*/
	}
	public static void save(String path, WorldData wdata) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
		oos.writeObject(wdata);
		oos.close();
		/*String str = "";
		str += "voxelmine.format=1\n";
		str += "player.position=";
		str += wdata.playerX + " " + wdata.playerY + "\n";
		str += "inventory.items=";
		for(int i = 0; i < 36; i++) {
			str += wdata.invItems[i] + " ";
		}
		str += "\ninventory.counts=";
		for(int i = 0; i < 36; i++) {
			str += wdata.invCounts[i] + " ";
		}
		str += "\nworld.blocks=";
		for(int i = 0; i < World.SIZE*Chunk.SIZE; i++) {
			for(int j = 0; j < Chunk.HEIGHT; j++) {
				str += wdata.blocks[i][j] + " ";
			}
			str += "\n";
		}
		str += "world.biomes=";
		for(int i = 0; i < World.SIZE*Chunk.SIZE; i++) {
			for(int j = 0; j < Chunk.HEIGHT; j++) {
				str += wdata.biomes[i][j] + " ";
			}
			str += "\n";
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
		bw.write(str);
		bw.close();*/
		System.out.println("[INFO] Saved world");
	}
}
