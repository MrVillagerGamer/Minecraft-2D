package net.voxelmine.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

import net.voxelmine.biome.BiomeProviderImpl;
import net.voxelmine.blocks.BlockPos;
import net.voxelmine.chunk.BlockProviderImpl;
import net.voxelmine.entity.EntityPos;
import net.voxelmine.entity.EntityPlayer;
import net.voxelmine.gui.component.GuiComponent;
import net.voxelmine.gui.screen.Screen;
import net.voxelmine.input.Input;
import net.voxelmine.util.Logger;
import net.voxelmine.world.World;

public class Voxelmine {
	public static final boolean FULLSCREEN = true;
	public static final int WIDTH = FULLSCREEN?(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth():1024;
	public static final int HEIGHT = FULLSCREEN?(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight():768;
	public static final int TICKS_PER_SECOND = 20;
	public static final float TIME_PER_TICK = 1.0f/TICKS_PER_SECOND;
	private static Voxelmine instance;
	public static Voxelmine getInstance() {
		return instance;
	}
	private World world;
	private EntityPlayer player;
	public Voxelmine() {
		instance = this;
	}
	public World getWorld() {
		return world;
	}
	public EntityPlayer getPlayer() {
		return player;
	}
	JFrame frame;
	public void start() throws IOException, FontFormatException {
		try {
			Logger.info("Generating world.");
			// 969971589
			// -765495904
			int seed = 969971589;//new Random().nextInt();
			System.out.println(seed);
			world = new World((long) seed, new BiomeProviderImpl(),new BlockProviderImpl());
			world.generate();
			
			player = new EntityPlayer(new EntityPos(0, 0));
			
			Logger.info("Creating window.");
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, Voxelmine.class.getResourceAsStream("/font.ttf"));
			customFont = customFont.deriveFont(GuiComponent.SCALE*8.0f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);
			frame = new JFrame();
			frame.setTitle("VoxelMine");
			if(FULLSCREEN) {
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setUndecorated(true);
			}else {
				frame.setSize(WIDTH, HEIGHT);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			frame.setResizable(false);
			Canvas canvas = new Canvas();
			canvas.setSize(frame.getSize());
			canvas.setFocusable(false);
			canvas.addMouseListener(new Input());
			canvas.addMouseMotionListener(new Input());
			frame.add(canvas);
			frame.pack();
			frame.addKeyListener(new Input());
			frame.setVisible(true);
			canvas.createBufferStrategy(2);
			Screen.setCurrentScreen(Screen.HUD);
			
			long time = 0;
			long lastTime = 0;
			float delta = 0;
			float elapsedTime = 0;
			float lastElapsedTime = 0;
			Logger.info("Spawning player.");
			EntityPos loc = world.calcSpawnLocation();
			//world.setupSpawn(new BlockLocation((int)loc.getX(), (int)loc.getY()));
			player.setLocation(loc);
			int frames = 0;
			float frameTime = 0;
			while(true) {
				time = System.nanoTime();
				delta = (float)((time-lastTime)/1000000000D);
				lastTime = time;
				if(delta>1) delta=0;
				elapsedTime += delta;
				frameTime += delta;
				while((elapsedTime-lastElapsedTime) >= TIME_PER_TICK) {
					delta = TIME_PER_TICK;
					player.onUpdate(delta);
					world.tick();
					Screen.getCurrentScreen().onUpdate(delta);
					lastElapsedTime += TIME_PER_TICK;
				}
				frames++;
				if(frameTime >= 1f) {
					
					frame.setTitle("Voxelmine - " + frames + " FPS");
					frames = 0;
					frameTime -= 1f;
				}
				BufferStrategy bs = canvas.getBufferStrategy();
				Graphics2D g = (Graphics2D)bs.getDrawGraphics();
				g.setFont(customFont);
				g.setColor(new Color(0.4f, 0.7f, 1.0f));
				g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				world.render(g);
				player.onWorldRender(g);
				player.onRender(g);
				Screen.getCurrentScreen().onRender(g);
				bs.show();
				g.dispose();
			}
		} catch (Exception e) {
			if(frame != null) {
				frame.dispose();
			}
			e.printStackTrace();
		}
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public void setPlayer(EntityPlayer player) {
		this.player = player;
	}
	
}
