package neuralfire.core;

import java.awt.Color;


public class Constants {
	public static int configuration = 6;
	
	public static Color droidColor = Color.green;
	public static Color fireColor = Color.red;
	public static Color wallColor = Color.black;
	public static Color floorColor = Color.white;
	public static Color lineColor = Color.black;
	public static Color backgroundColor = Color.white;
	
	public static boolean spawner = true;
	public static int droidsPerSpawner = 10;
	
	public static boolean debug = false;
	public static boolean displayPheromoneLines = false;
	public static boolean displayPheromoneValues = false;
	public static boolean displayPheromoneDots = true;
	
	public static int preferredSquareSize = 10;
	public static double pheromoneIncrease = 50;
	public static double pheromoneZeroThreshold = 1;
	// between 0 and 1!
	public static double pheromoneDecay = 0.09;
	
	public static double scaleUI = 0.3;
	public static double maxPheromoneLineThickness = 30; 
	
	public static int sleepDuration = 20;
	
	public static String mapFile = "map2";
	
	public static enum Dir {
		UP, DOWN, LEFT, RIGHT, STAY 
	}
	
	public static double volumeDecay = 2;
	public static double volumeDecayThreshold = 1;
	public static int fireRadius = 10;

	public static int yellRadius = 50;
	public static int yellVolume = yellRadius;
	public static int relays = 2;

	public static int fireIntensity = fireRadius + relays*yellRadius;
	public static int timeToSpread = 70;
	
	public static int droidstokillfire = 5;
	
	public static int maxDroidPerField = 20;
	
	public static double fireSpread = 0.05;
	public static boolean fireSpreading = false;
	
	public static int maxIterations = 5000;
	
	public static double fireExtinguishedMilestone = 0.3;
	public static String delimiter = ";";
}
