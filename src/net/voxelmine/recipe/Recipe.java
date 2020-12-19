package net.voxelmine.recipe;

import java.util.ArrayList;

import net.voxelmine.items.Item;

public class Recipe {
	private static final ArrayList<Recipe> RECIPES = new ArrayList<>();
	public static final Recipe PLANKS = new Recipe(RecipeType.CRAFTING, new int[] {
			Item.WOOD.getId(),
		}, RecipeMode.SHAPELESS, Item.PLANKS.getId(), 4);
	public static final Recipe STICK = new Recipe(RecipeType.CRAFTING, new int[] {
			0, Item.PLANKS.getId(),
			Item.PLANKS.getId(), 0,
		}, RecipeMode.SHAPED, Item.STICK.getId(), 4);
	public static final Recipe WOOD_AXE = new Recipe(RecipeType.CRAFTING, new int[] {
			0, Item.PLANKS.getId(), Item.PLANKS.getId(),
			0, Item.STICK.getId(), 0,
			Item.STICK.getId(), 0, 0,
		}, RecipeMode.SHAPED, Item.WOOD_AXE.getId(), 1);
	public static final Recipe WOOD_PICKAXE = new Recipe(RecipeType.CRAFTING, new int[] {
			0, Item.PLANKS.getId(), 0,
			0, Item.STICK.getId(), Item.PLANKS.getId(),
			Item.STICK.getId(), 0, 0,
		}, RecipeMode.SHAPED, Item.WOOD_PICKAXE.getId(), 1);
	public static final Recipe WOOD_SWORD = new Recipe(RecipeType.CRAFTING, new int[] {
			0, 0, Item.PLANKS.getId(),
			0, Item.PLANKS.getId(), 0,
			Item.STICK.getId(), 0, 0,
		}, RecipeMode.SHAPED, Item.WOOD_SWORD.getId(), 1);
	public static final Recipe WOOD_SHOVEL = new Recipe(RecipeType.CRAFTING, new int[] {
			0, 0, Item.PLANKS.getId(),
			0, Item.STICK.getId(), 0,
			Item.STICK.getId(), 0, 0,
		}, RecipeMode.SHAPED, Item.WOOD_SHOVEL.getId(), 1);
	public static final Recipe STONE_AXE = new Recipe(RecipeType.CRAFTING, new int[] {
			0, Item.STONE.getId(), Item.STONE.getId(),
			0, Item.STICK.getId(), 0,
			Item.STICK.getId(), 0, 0,
		}, RecipeMode.SHAPED, Item.STONE_AXE.getId(), 1);
	public static final Recipe STONE_PICKAXE = new Recipe(RecipeType.CRAFTING, new int[] {
			0, Item.STONE.getId(), 0,
			0, Item.STICK.getId(), Item.STONE.getId(),
			Item.STICK.getId(), 0, 0,
		}, RecipeMode.SHAPED, Item.STONE_PICKAXE.getId(), 1);
	public static final Recipe STONE_SWORD = new Recipe(RecipeType.CRAFTING, new int[] {
			0, 0, Item.STONE.getId(),
			0, Item.STONE.getId(), 0,
			Item.STICK.getId(), 0, 0,
		}, RecipeMode.SHAPED, Item.STONE_SWORD.getId(), 1);
	public static final Recipe STONE_SHOVEL = new Recipe(RecipeType.CRAFTING, new int[] {
			0, 0, Item.STONE.getId(),
			0, Item.STICK.getId(), 0,
			Item.STICK.getId(), 0, 0,
		}, RecipeMode.SHAPED, Item.STONE_SHOVEL.getId(), 1);
	public static final Recipe FURNACE = new Recipe(RecipeType.CRAFTING, new int[] {
			0, Item.STONE.getId(), 0,
			Item.STONE.getId(), 0, Item.STONE.getId(),
			Item.STONE.getId(), Item.STONE.getId(), Item.STONE.getId(),
		}, RecipeMode.SHAPED, Item.FURNACE.getId(), 1);
	public static final Recipe CHEST = new Recipe(RecipeType.CRAFTING, new int[] {
			Item.PLANKS.getId(), Item.PLANKS.getId(), Item.PLANKS.getId(),
			Item.PLANKS.getId(), 0, Item.PLANKS.getId(),
			Item.PLANKS.getId(), Item.PLANKS.getId(), Item.PLANKS.getId(),
		}, RecipeMode.SHAPED, Item.CHEST.getId(), 1);
	public static Recipe getMatchingRecipe(int[] supply, RecipeType type) {
		if(supply.length != 9) {
			return null;
		}
		ArrayList<Recipe> list = new ArrayList<>();
		for(Recipe recipe : RECIPES) {
			if(recipe.getType() == type) {
				list.add(recipe);
			}
		}
		if(list != null) {
			for(Recipe recipe : list) {
				int[] pattern = recipe.getPattern();
				if(recipe.getMode() == RecipeMode.SHAPED) {
					if(pattern.length == 9) {
						boolean matched = true;
						for(int i = 0; i < 9; i++) {
							if(supply[i] != pattern[i]) {
								matched = false;
							}
						}
						if(matched) {
							return recipe;
						}
					}else if(pattern.length == 4) {
						for(int x = 0; x <= 1; x++) {
							for(int y = 0; y <= 1; y++) {
								int[] pattern2 = new int[9];
								for(int i = 0; i < 2; i++) {
									for(int j = 0; j < 2; j++) {
										pattern2[(x+i)*3+(y+j)] = pattern[i*2+j];
									}
								}
								boolean matched = true;
								for(int i = 0; i < 9; i++) {
									if(supply[i] != pattern2[i]) {
										matched = false;
									}
								}
								if(matched) {
									return recipe;
								}
							}
						}
					}
				}else if(recipe.getMode() == RecipeMode.SHAPELESS){
					boolean[] done = new boolean[9];
					int total = 0;
					for(int item : pattern) {
						for(int i = 0; i < 9; i++) {
							if(item == supply[i] && !done[i]) {
								done[i] = true;
								total++;
							}
						}
					}
					if(total == pattern.length) {
						return recipe;
					}
				}
			}
		}
		return null;
	}
	private int[] pattern;
	private RecipeMode mode;
	private RecipeType type;
	private int result;
	private int count;
	public Recipe(RecipeType type, int[] pattern, RecipeMode mode, int result, int count) {
		this.type = type;
		this.pattern = pattern;
		this.mode = mode;
		this.result = result;
		this.count = count;
		RECIPES.add(this);
	}
	public int[] getPattern() {
		return pattern;
	}
	public RecipeMode getMode() {
		return mode;
	}
	public int getResult() {
		return result;
	}
	public int getCount() {
		return count;
	}
	public RecipeType getType() {
		return type;
	}
}