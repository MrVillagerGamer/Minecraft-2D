package net.voxelmine.gui.component;

import java.awt.Graphics;
import java.util.ArrayList;

import net.voxelmine.gui.Alignment;

public class GuiGroup<T extends GuiComponent> extends GuiComponent {
	public GuiGroup(Alignment alignment) {
		super(alignment);
	}

	protected ArrayList<T> children = new ArrayList<T>();
	@Override
	public void render(Graphics g) {
		for(GuiComponent child : children) {
			child.render(g);
		}
	}

	@Override
	public void update() {
		for(GuiComponent child : children) {
			child.update();
		}
	}

	@Override
	public boolean clicked() {
		for(GuiComponent child : children) {
			if(child.clicked()) {
				return true;
			}
		}
		return false;
	}
}
