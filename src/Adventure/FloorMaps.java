package Adventure;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import ExpositoryConstant.ExpositoryConstant;
import java.awt.Color;

public class FloorMaps implements ExpositoryConstant {
	
	private Vector [][] tiles;
	private int seed = ThreadLocalRandom.current().nextInt(0, 10);
	private SimplexValueNoise noise;
	
	public FloorMaps () {
		System.out.println("Floor Maps being created");
		
		tiles = generateFloorMap();
	}
	
	/**
	 * Returns a Floor map of the world the user will be in, consisting of ASCII characters
	 * representing the various different elements, beach, forest, mountain etc.
	 * @return a 2D char array consisting of the various ASCII characters representing the map
	 */
	public Vector[][] generateFloorMap() {
		// Method of map generation taken from:
		// http://www.nolithius.com/articles/world-generation/world-generation-breakdown
		
		double [][] simplexHeightMap = generateSimplexMap();
		//double[][] maskMap = generateRollingMask(40, 3000);
		double [][] maskMap = null;
		Vector[][] terrainMap = generateTerrainMap(simplexHeightMap, maskMap);
		
		return terrainMap;
	}

	/**
	 * Returns a noise map, normalized to be between 0 and 1, to be used in determining the height
	 * of a particular point in the map
	 * @return a noise map
	 */
	private double[][] generateSimplexMap() {
		System.out.println("Generating simplexMap...");
		noise = new SimplexValueNoise(seed);
		double [][] simplexMap = new double[WORLD_HEIGHT][WORLD_WIDTH];
		for (int i = 0; i < WORLD_HEIGHT; i ++) {
			for (int j = 0; j < WORLD_WIDTH; j++) {
				double currentNoise = 1.2 * noise.eval(0.1 * j, 0.1 * i);
				currentNoise += 0.75 * noise.eval(0.3 * j, 0.3 * i);
				simplexMap[i][j] = Math.pow(currentNoise, 1.5);
			}
		}
		return simplexMap;
	}

	/**
	 * Returns a noise map with its values normalized between 0 and 1.
	 * Used to multiply over the simplex noise map to bias higher values towards the center of the map
	 * to create a more island-y feel
	 * @param particleLife the number of roll-offs the will occur from one particle.
	 * @param numParticle the number of particle that will be affected.
	 * @return a noise map with a bias towards the center of the map (2D array).
	 */
	public double[][] generateRollingMask(int particleLife, int numParticle) {
		System.out.println("Generating RollingMask...");
		double maxVal = 0;
		double[][] rollingMaskMap = new double[WORLD_HEIGHT][WORLD_WIDTH];
		for (int i = 0; i < numParticle; i ++) {
			
			int randX = ThreadLocalRandom.current().nextInt(ThreadLocalRandom.current().nextInt(0, 5)
					, ThreadLocalRandom.current().nextInt(WORLD_WIDTH - 5, WORLD_WIDTH));;
			int randY = ThreadLocalRandom.current().nextInt(ThreadLocalRandom.current().nextInt(0, 5)
					, ThreadLocalRandom.current().nextInt(WORLD_HEIGHT - 5, WORLD_HEIGHT));
			rollingMaskMap[randY][randX] += 1;
			double currPosVal = rollingMaskMap[randY][randX];
			if (currPosVal > maxVal) maxVal = currPosVal;
			
			for (int j = 0; j < particleLife; j ++) {
				int randAdjX = -1;
				int randAdjY = -1;
				while (((randAdjX < 0 || randAdjX >= WORLD_WIDTH)
						|| (randAdjY < 0 || randAdjY >= WORLD_HEIGHT))
						|| (randAdjX == randX && randAdjY == randY)) {
					randAdjX = ThreadLocalRandom.current().nextInt(randX - 1, randX + 2);
					randAdjY = ThreadLocalRandom.current().nextInt(randY - 1, randY + 2);
				}
				
				if (rollingMaskMap[randAdjY][randAdjX] <= currPosVal) {
					rollingMaskMap[randAdjY][randAdjX] += 1;
					currPosVal = rollingMaskMap[randAdjY][randAdjX];
					if (currPosVal > maxVal) maxVal = currPosVal;
				}
			}
		}	
		
		
		String output = "";
		for (int i = 0; i < WORLD_HEIGHT; i ++) {
			output = "";
			for (int j = 0; j < WORLD_WIDTH; j++) {
				output += rollingMaskMap[i][j] + ", ";
			}
			System.out.println(output);
		}
		
		// Normalizing the rollingMaskMap value to ensure it is between 0 and 1
		for (int i = 0; i < WORLD_HEIGHT; i ++) {
			for (int j = 0; j < WORLD_WIDTH; j++) {
				if (rollingMaskMap[i][j] != 0 ) {
					rollingMaskMap[i][j] /= maxVal;
				}
			}
		}
		
		return rollingMaskMap;
	}

	/**
	 * 
	 * @param simplexHeightMap
	 * @param maskMap
	 * @return
	 */
	private Vector[][] generateTerrainMap(double[][] simplexHeightMap, double[][] maskMap) {
		// Determining the value for which 60% of the map is water
		ArrayList<Double> waterLine = new ArrayList<Double>();
		for (int i = 0; i < WORLD_HEIGHT; i ++) {
			for (int j = 0; j < WORLD_WIDTH; j ++) {
//				simplexHeightMap[i][j] *= maskMap[i][j];
				waterLine.add(simplexHeightMap[i][j]);
			}
		}
		Collections.sort(waterLine);
		Double waterLevel = waterLine.get((int) ((waterLine.size() - 1) * 0.6));
		
		// Assigning the characters to the terrainMap
		Vector[][] terrainMap = new Vector[WORLD_HEIGHT][WORLD_WIDTH];
		
		for (int i = 0; i < WORLD_HEIGHT; i ++) {
			for (int j = 0; j < WORLD_WIDTH; j ++) {
				terrainMap[i][j] = getTypeByElevation(waterLevel, simplexHeightMap[i][j]);
			}
		}
		
//		String toPrint = ""; 
//		for (int i = 0; i < WORLD_HEIGHT; i ++) {
//			toPrint = "";
//			for (int j = 0; j < WORLD_WIDTH; j ++) {
//				toPrint += terrainMap[i][j];
//			}
//			System.out.println(toPrint);
//		}
		return terrainMap;
	}

	private Vector getTypeByElevation(double waterLevel, double height) {
		int elevation = (int) (height * 255);
		int waterLine = (int) (waterLevel * 255);
		Vector toReturn = new Vector();
		
		// Water
		if (elevation <= waterLine) {
			//Shallow water
			if (elevation > waterLine - 20) {
				toReturn.add(",");
				toReturn.add(Color.blue);
			} 
			// Deep water
			else {
				toReturn.add(",");
				toReturn.add(Color.BLACK);
			}
		} 
		// Land
		else if (elevation > waterLine) {
			// Beach
			if (elevation <= waterLine + 15) {
				toReturn.add("-");
				toReturn.add(Color.YELLOW);
			} 
			// Plains
			else if (elevation > waterLine + 15
					&& elevation <= waterLine + 35) {
				toReturn.add("~");
				toReturn.add(Color.GRAY);
			}
			// Hills 
			else if (elevation > (255 - 60)
					&& elevation < (255 - 25)) {
				toReturn.add("+");
				toReturn.add(Color.cyan);
			} 
			// Mountains
			else if (elevation > (255 - 25)) {
				toReturn.add("^");
				toReturn.add(Color.WHITE);
			}
			// forest
			else {
				toReturn.add("*");
				toReturn.add(Color.GREEN);
			}
		}
		return toReturn;
	}

	/**
	 * Returns the height of the map
	 * @return the height of the map
	 */
	public int getHeight() {
		return tiles.length;
	}
	
	/**
	 * Returns the width of the map
	 * @return the width of the map
	 */
	public int getWidth() {
		return tiles[0].length;
	}
	
	/**
	 * Returns a tile in the map
	 * @param x of type int, the x coordinate of the tile
	 * @param y of type int, the y coordinate of the tile
	 * @return a tile, of type char, representing the tile at location (@param x, @param y)
	 */
	public String getTile (int x, int y) {
		return (String) tiles[y][x].get(terrain.TILE.ordinal());
	}
	
	/**
	 * Returns the color for that tile in the map
	 * @param x of type int, the x coordinate of the tile
	 * @param y of type int, the y coordinate of the tile
	 * @return a Color, of type Color, representing the color of the tile at location (@param x, @param y)
	 */
	public Color getColor (int x, int y) {
		return (Color) tiles[y][x].get(terrain.COLOR.ordinal());
	}
}
