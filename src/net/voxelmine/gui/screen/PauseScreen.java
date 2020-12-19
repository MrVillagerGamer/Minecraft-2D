package net.voxelmine.gui.screen;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import net.voxelmine.gui.Alignment;
import net.voxelmine.gui.component.GuiButton;
import net.voxelmine.gui.component.GuiComponent;
import net.voxelmine.main.Voxelmine;
import net.voxelmine.world.WorldData;
import net.voxelmine.world.WorldUtil;

public class PauseScreen extends Screen {
	private GuiButton saveBtn;
	private GuiButton loadBtn;
	private GuiButton quitBtn;
	
	public PauseScreen() {
		int dist = 8;
		int sz = GuiComponent.SCALE;
		saveBtn = new GuiButton(Alignment.CENTER, 0, 24*-1, 200, "Save");
		loadBtn = new GuiButton(Alignment.CENTER, 0, 24*0, 200, "Load");
		quitBtn = new GuiButton(Alignment.CENTER, 0, 24*1, 200, "Quit");
	}
	@Override
	public void onUpdate(float delta) {
		saveBtn.update();
		loadBtn.update();
		quitBtn.update();
		if(saveBtn.clicked()) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Save world");
			int userSelection = fileChooser.showSaveDialog(null);
			if(userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				if(fileToSave.isFile() || !fileToSave.exists()) {
					try {
						WorldUtil.save(fileToSave.getAbsolutePath(), WorldData.getWorldData());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else if(loadBtn.clicked()) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Load world");
			int userSelection = fileChooser.showOpenDialog(null);
			if(userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToLoad = fileChooser.getSelectedFile();
				if(fileToLoad.isFile()) {
					try {
						WorldData.setWorldData(WorldUtil.load(fileToLoad.getAbsolutePath()));
					} catch (IOException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else if(quitBtn.clicked()) {
			System.exit(0);
		}
	}

	@Override
	public void onRender(Graphics g) {
		saveBtn.render(g);
		loadBtn.render(g);
		quitBtn.render(g);
	}

	@Override
	public void onOpen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
	
}
