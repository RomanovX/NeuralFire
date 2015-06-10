package neuralfire.core;

import java.awt.Color;


public class Constants {
	public static int configuration = 9;
	
	public static enum Dir {
		UP, DOWN, LEFT, RIGHT, STAY 
	}
	/*
	 * COLOR SETTINGS
	 */
	public static Color droidColor = Color.green;
	public static Color fireColor = Color.red;
	public static Color wallColor = Color.black;
	public static Color floorColor = Color.white;
	public static Color lineColor = Color.black;
	public static Color backgroundColor = Color.white;
	
	/*
	 * DEBUG SETTINGS
	 */
	public static boolean debug = false;
	public static boolean displayPheromoneLines = false;
	public static boolean displayPheromoneValues = false;
	public static boolean displayPheromoneDots = true;
	
	/*
	 * UI SETTINGS
	 */
	public static int preferredSquareSize = 10;
	public static double scaleUI = 0.3;
	public static double maxPheromoneLineThickness = 30;
	
	
	/* 
	 * PHEROMONES
	 */
	public static double pheromoneIncrease = 50;
	public static double pheromoneZeroThreshold = 1;
	// between 0 and 1!
	public static double pheromoneDecay = 0.09;
	
	/*
	 * YELLING!! 
	 */
	public static double volumeDecay = 2;
	public static double volumeDecayThreshold = 1;
	public static int yellRadius = 50;
	public static int yellVolume = 50;
	public static int yellRelay = 3;
	
	/*
	 * FIRE
	 */
	public static int fireRadius = 10;
	public static int fireIntensity = fireRadius + yellRadius;
	public static int timeToSpread = 70;
	public static int droidstokillfire = 5;
	public static double fireSpread = 0.05;
	public static boolean fireSpreading = false;
	
	/*
	 * MISC
	 */
	public static int maxDroidPerField = 20;
	
	
	/*
	 * SIMULATION SETTINGS
	 */
	public static int maxIterations = 500;
	public static double fireExtinguishedMilestone = 0.3;
	public static String delimiter = ",";
	public static int initialNumberOfDroids = 50;
	public static int numberOfDroidsIncrease = 50;
	public static int maxDroids = 100;
	public static int trials = 10;
	public static boolean visualizeProgress = true;
	public static boolean spawner = true;
	public static int droidsPerSpawner = 10;
	public static int sleepDuration = 20;
	public static String mapFile = "map2";
	public static String mapDirectory = "testMaps";
	public static boolean useMapDirectory = false;
	public static int[] fireRadi = { 10 };
	public static int[] yellRadi = { 10 };
	public static int[] yellRelays = { 3 };
	public static double[] pheromoneDecays = { 0.09 };
}
