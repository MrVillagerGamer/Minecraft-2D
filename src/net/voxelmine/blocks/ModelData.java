package net.voxelmine.blocks;

import java.awt.image.BufferedImage;

public class ModelData {
	public static final ModelData BLOCK = new ModelData(
		new ModelPiece(0, 0, 1, 1, 0, 0)
	);
	private ModelPiece[] pieces;
	public ModelData(ModelPiece... pieces) {
		this.pieces = pieces;
	}
	public ModelPiece[] getPieces() {
		return pieces;
	}
	public ModelData copy() {
		return new ModelData(pieces);
	}
	public void bake(BufferedImage atlas) {
		for(ModelPiece piece : pieces) {
			int sizeX = (int)((piece.getMaxX() - piece.getMinX())*8);
			int sizeY = (int)((piece.getMaxY() - piece.getMinY())*8);
			BufferedImage img = atlas.getSubimage(piece.getTexX()*8, piece.getTexY()*8, sizeX, sizeY);
			piece.setTex(img);
		}
	}
}
