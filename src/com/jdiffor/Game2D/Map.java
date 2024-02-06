package com.jdiffor.Game2D;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map {

	private int tileSize;
	private int widthInTiles;
	private int heightInTiles;
	private Tile[][] tiles;
	private int playerSpawnX;
	private int playerSpawnY;
	private int playerSpawnFromTeleporterX = -1;
	private int playerSpawnFromTeleporterY = -1;
	private String name;
	private ArrayList<Chest> chests;
	
	public Map(Camera camera, Dimension worldSizeInTiles, int tileSize) {
		this.widthInTiles = worldSizeInTiles.width;
		this.heightInTiles = worldSizeInTiles.height;
		this.tileSize = tileSize;
		tiles = new Tile[this.widthInTiles][this.heightInTiles];		
	}
	
	public Map(Camera camera, String newMapName, String oldMapName) {
		this.name = newMapName;
		this.chests = new ArrayList<Chest>();
		this.createMapFromFile(camera, newMapName, oldMapName);
	}
	
	/*
	 * Read in a map file and parse it line by line to create the map
	 */
	private void createMapFromFile(Camera camera, String newMapName, String oldMapName) {
		File file = new File(AssetManager.loadResource(AssetManager.map(newMapName)));
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String line = reader.readLine(); // unit size
			this.tileSize = Integer.parseInt(line) * GamePanel.SCALE;
			
			line = reader.readLine(); // image location
			//String imagePath = line;  // Commenting out - assumed it is in same folder as the .mmak file
			BufferedImage image = Utils.tryGetImage(AssetManager.mapImage(newMapName));
			
			line = reader.readLine(); // unit array dimensions
			String[] mapDims = line.split(";");
			
			this.widthInTiles = Integer.parseInt(mapDims[0]);
			this.heightInTiles = Integer.parseInt(mapDims[1]);
			tiles = new Tile[this.widthInTiles][this.heightInTiles];
			
			line = reader.readLine(); // list of possible attributes
			line = reader.readLine(); // first tile in array
			
			// Initialize each tile
			while(line != null) {
				String[] tileLine = line.split(";");
				
				int tileIndexX = Integer.parseInt(tileLine[0]);
				int tileIndexY = Integer.parseInt(tileLine[1]);
				int tileX = Integer.parseInt(tileLine[2]);
				int tileY = Integer.parseInt(tileLine[3]);
				
				boolean isCollisionable = false;
				boolean isInFrontOfPlayer = false;
				String chestColor = null;
				String itemColor = null;
				String teleportToMap = null;
				
				for(int i = 4; i < tileLine.length; i++) {
					// Set attributes of tiles here
					if(tileLine[i].equals("collisionable")) {
						isCollisionable = true;
					} else if(tileLine[i].equals("inFrontOfPlayer")) {
						isInFrontOfPlayer = true;
					} else if(tileLine[i].indexOf("teleporter:") == 0) {
						
						if(tileLine[i].equals("teleporter:" + oldMapName + ":return")) {
						// Tile is location that we spawn at if coming from indicated map
							this.playerSpawnFromTeleporterX = tileX*GamePanel.SCALE;
							this.playerSpawnFromTeleporterY = tileY*GamePanel.SCALE;
						} else if(!tileLine[i].endsWith(":return")){
						// Tile is teleporter to a new map
							teleportToMap = tileLine[i].substring("teleporter:".length());
						}
						
					} else if(tileLine[i].equals("characterSpawn")) {
						this.playerSpawnX = tileX*GamePanel.SCALE;
						this.playerSpawnY = tileY*GamePanel.SCALE;
					} else if(tileLine[i].indexOf("chest:") == 0) {
						chestColor = tileLine[i].substring("chest:".length());
					} else if(tileLine[i].indexOf("item:") == 0) {
						if(tileLine[i].indexOf("circle:") == "item:".length()) {
							itemColor = tileLine[i].substring("item:circle:".length());
						}
					}
				}
				
				tiles[tileIndexX][tileIndexY] = new Tile(camera, tileX*GamePanel.SCALE, tileY*GamePanel.SCALE, this.tileSize, teleportToMap, Utils.getImagePart(image, tileX, tileY, tileSize)).collisionable(isCollisionable).inFrontOfPlayer(isInFrontOfPlayer);
				tiles[tileIndexX][tileIndexY].addChest(chestColor, chests);
				tiles[tileIndexX][tileIndexY].addItem(itemColor);
				
				line = reader.readLine();
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			// If file can't load, initialze a random map
			e.printStackTrace();
			Map map = randomMap(camera, new Dimension(32, 32), GamePanel.SCALED_TILE_SIZE);
			this.tileSize = map.tileSize;
			this.widthInTiles = map.widthInTiles;
			this.heightInTiles = map.heightInTiles;
			this.tiles = map.tiles;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Creates a random map from hardcoded tile options
	 */
	public static Map randomMap(Camera camera, Dimension worldSizeInTiles, int tileSize) {
		Map map = new Map(camera, worldSizeInTiles, tileSize);
		for(int x = 0; x < map.widthInTiles; x++) {
			for(int y = 0; y < map.heightInTiles; y++) {
				if(x <= 10 || y <= 10 || x >= map.widthInTiles - 10 || y >= map.heightInTiles - 10) {
					// Collisionable border (water for now)
					map.tiles[x][y] = new Tile(camera, x * map.tileSize, y * map.tileSize, map.tileSize, null, AssetManager.groundImage("waterSpriteSheet.png"), true, Utils.randomInt(1)).collisionable(true);
				} else {
					String randomImage = randomGroundImage();
					map.tiles[x][y] = new Tile(camera, x * map.tileSize, y * map.tileSize, map.tileSize, null, randomImage).collisionable((randomImage.indexOf("stone") != -1));
				}				
			}
		}
		map.playerSpawnX = map.tileSize * 12;
		map.playerSpawnY = map.tileSize * 12;
		return map;
	}
	
	public void removeChest(Chest chest) {
		this.chests.remove(chest);
	}
	
	public boolean chestsCompleted() {
		return this.chests.size() == 0;
	}
	
	public String getMapName() {
		return this.name;
	}
	
	public int getPlayerSpawnX() {
		return this.playerSpawnFromTeleporterX >= 0 ? this.playerSpawnFromTeleporterX : this.playerSpawnX;
	}
	
	public int getPlayerSpawnY() {
		return this.playerSpawnFromTeleporterY >= 0 ? this.playerSpawnFromTeleporterY : this.playerSpawnY;
	}
	
	public Tile[] getSurrounding(int x, int y) {
		x /= tileSize;
		y /= tileSize;

		Tile[] surroundingTiles = new Tile[9];
		int index = 0;
		for(int i = x - 1; i <= x + 1; i++) {
			for(int j = y - 1; j <= y + 1; j++) {
				surroundingTiles[index] = this.tiles[i][j];
				index++;
			}
		}
		
		return surroundingTiles;
	}
	
	public Tile getTileAtCoordinates(int x, int y) {
		return tiles[x / tileSize][y / tileSize];
	}
	
	public ArrayList<Tile> draw(Graphics2D g, int centerX, int centerY, int screenWidth, int screenHeight) {
		
		// Trying to optimize tile drawing by not drawing tiles that are off screen
		int startX = Utils.max(((centerX - screenWidth) / this.tileSize) - 1, 0);
		int endX = Utils.min(((centerX + screenWidth) / this.tileSize) + 1, widthInTiles);
		int startY = Utils.max(((centerY - screenHeight) / this.tileSize) - 1, 0);
		int endY = Utils.min(((centerY + screenHeight) / this.tileSize) + 1, heightInTiles);
		
		ArrayList<Tile> inFrontOfPlayer = new ArrayList<Tile>();
		
		for(int x = startX; x < endX; x++) {
			for(int y = startY; y < endY; y++) {
				if(tiles[x][y].isInFrontOfPlayer()) {
					inFrontOfPlayer.add(tiles[x][y]);
				} else {
					tiles[x][y].draw(g, false);
				}
			}
		}
		
		return inFrontOfPlayer;
	}
	
	/*
	 * Used for testing, picks between hardcoded image paths and returns a random one
	 */
	private static String randomGroundImage() {
		double random = Math.random();
		return random > 0.9 ? "ground/stone.png" : random > 0.8 ? "ground/stone2.png" : random > 0.4 ? "ground/grass.png" : "ground/grass2.png";
	}
	
}
