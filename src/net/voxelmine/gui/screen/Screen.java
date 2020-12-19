package net.voxelmine.gui.screen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Screen {
	public static final Screen HUD = new HUDScreen();
	public static final Screen INVENTORY = new InventoryScreen();
	public static final Screen CRAFTING = new CraftingScreen();
	public static final Screen SMELTING = new SmeltingScreen();
	public static final Screen PAUSE = new PauseScreen();
	public static final Screen CHEST = new ChestScreen();
	private static Screen currentScreen;
	public static Screen getCurrentScreen() {
		return currentScreen;
	}
	public static void setCurrentScreen(Screen currentScreen) {
		if(Screen.currentScreen != null) Screen.currentScreen.onClose();
		Screen.currentScreen = currentScreen;
		Screen.currentScreen.onOpen();
	}
	protected static BufferedImage gui;
	public Screen() {
		if(gui == null) {
			try {
				gui = ImageIO.read(getClass().getResourceAsStream("/gui.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public abstract void onUpdate(float delta);
	public abstract void onRender(Graphics g);
	public abstract void onOpen();
	public abstract void onClose();
}
