package neuralfire.core;

import java.awt.event.WindowEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;

public class Main {

	/**
	 * This main routine creates a window and sets its content
	 * to be a panel of type Grid.  It shows the window in the
	 * center of the screen.
	 */
	public static void main(String[] args) {
		PrintWriter writer;
		int result = 0;
		
		try {
			writer = new PrintWriter("SimResults-"+System.currentTimeMillis()+".txt", "UTF-8");
			
			if(Constants.configuration == 1){
				Constants.mapFile = "map1.bmp";
				Constants.fireRadius = 3;
				Constants.yellRadius = 4;
				Constants.scaleUI = 5;
				Constants.sleepDuration = 0;
			} else if (Constants.configuration == 2){
				Constants.mapFile = "map2.bmp";
				Constants.fireRadius = 10;
				Constants.yellRadius = 15;
				Constants.scaleUI = 1;
				Constants.sleepDuration = 0;
				//Constants.relays = 1;
			} else if(Constants.configuration == 3){
				Constants.mapFile = "map3.bmp";
				Constants.fireRadius = 3;
				Constants.yellRadius = 8;
				Constants.scaleUI = 5;
				Constants.sleepDuration = 800;
			} else if (Constants.configuration == 4){
				Constants.mapFile = "map4.bmp";
				Constants.fireRadius = 15;
				Constants.yellRadius = 50;
				Constants.scaleUI = 0.4;
				Constants.sleepDuration = 0;
			} else if (Constants.configuration == 5){
				Constants.mapFile = "map5.bmp";
				Constants.fireRadius = 10;
				Constants.yellRadius = 20;
				Constants.scaleUI = 1.5;
				Constants.sleepDuration = 0;
				Constants.displayPheromoneDots = true;
			}else if (Constants.configuration == 6){
				Constants.mapFile = "mapIO1.bmp";
				Constants.fireRadius = 10;
				Constants.yellRadius = 20;
				Constants.yellVolume = 60;
				Constants.scaleUI = 1;
				Constants.pheromoneIncrease = 100;
				Constants.pheromoneDecay = 0.02;
				Constants.sleepDuration = 0;
				Constants.displayPheromoneDots = true;
				Constants.spawner = true;
			}else if (Constants.configuration == 7){
				Constants.mapFile = "map6.bmp";
			} else if (Constants.configuration == 8){
				Constants.yellVolume = 60;
				Constants.scaleUI = 0.5;
				Constants.pheromoneIncrease = 100;
				Constants.sleepDuration = 0;
				Constants.displayPheromoneDots = true;
				
				
				/* Simulation settings */
				Constants.spawner = true;
				Constants.useMapDirectory = true;
				Constants.maxIterations = 100;
				Constants.trials = 2;
				Constants.fireRadi = new int[]{5, 10, 15};
				Constants.yellRadi = new int[]{11, 22};
				Constants.pheromoneDecays = new double[]{0.09, 0.15};
				Constants.initialNumberOfDroids = 50;
				Constants.numberOfDroidsIncrease = 50;
				Constants.maxDroids = 100;
				
				
				Constants.visualizeProgress = false;
			} else if (Constants.configuration == 9){
				// CONFIGURATION FOR TESTING SIMULATOR VARIABELS
				Constants.yellVolume = 60;
				Constants.scaleUI = 1;
				Constants.pheromoneIncrease = 100;
				Constants.sleepDuration = 0;
				Constants.displayPheromoneDots = true;
				
				/* Simulation settings */
				Constants.spawner = true;
				//Constants.mapFile = "mapIO1.bmp";
				Constants.useMapDirectory = true;
				Constants.maxIterations = 5000;
				Constants.trials = 10;
				Constants.fireRadi = new int[]{10}; // fixed to 10 based on previous evaluation
				Constants.yellRadi = new int[]{1, 3, 5, 7, 9, 11, 13, 15, 20, 25};
				Constants.yellRelays= new int[]{ 0, 1, 2, 3, 4 };
				Constants.pheromoneDecays = new double[]{0.005, 0.01, 0.05, 0.1, 0.2, 0.5};
				Constants.initialNumberOfDroids = 20;
				Constants.numberOfDroidsIncrease = 20;
				Constants.maxDroids = 80;
				Constants.visualizeProgress = false;
			}
			
			
			writer.write("##################################\n");
			writer.write("Configuration used:\n");
			writer.write("##################################\n");
			try {
				java.lang.reflect.Field[] fields = Constants.class.getDeclaredFields();
		        for (java.lang.reflect.Field field : fields) {
		        	writer.write(field.getName()+":"+field.get(new Constants())+"\n");
		        }
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
			java.nio.file.Path currentRelativePath = Paths.get("");
			String s = currentRelativePath.toAbsolutePath().toString();
			List<String> maps = new ArrayList<String>();
			if(Constants.useMapDirectory){
				writer.write("##################################\n");
				writer.write("Map mapping:\n");
				writer.write("##################################\n");
				File directory = new File("src/environmentMaps/"+Constants.mapDirectory+"/");
				int mapCount = 1;
				for (final File fileEntry : directory.listFiles()) {
			        if (!fileEntry.isDirectory()) {
			        	String map ="src/environmentMaps/"+Constants.mapDirectory+"/"+fileEntry.getName(); 
			        	maps.add(map);
			        	writer.write(mapCount+" : "+map+"\n");
			        	mapCount++;
			        }
			    }
			} 
			else {
				maps.add("src/environmentMaps/"+Constants.mapFile);
				writer.write("##################################\n");
				writer.write("Map: src/environmentMaps/"+Constants.mapFile+"\n");
				writer.write("##################################\n");
			}
			
			writer.write("##################################\n");
			writer.close();
			String filename = "importMeToMatlab-"+System.currentTimeMillis()+".csv";
			writer = new PrintWriter(filename, "UTF-8");
			writer.write("In matlab use: \n");
			writer.write("M = csvread('"+filename+"', 3)\n");
			int totalRuns = writeheader(writer, maps.size());
			System.out.println("Now starting "+totalRuns+" runs");
			int run = 1;
			
			int mapCount = 1;
			for(String map : maps){
				Constants.mapFile = map;
				for(int NumberOfDroids = Constants.initialNumberOfDroids; NumberOfDroids < Constants.maxDroids+1; NumberOfDroids = NumberOfDroids+Constants.numberOfDroidsIncrease){
					Constants.droidsPerSpawner = NumberOfDroids;
					// write map
					writer.write(mapCount+Constants.delimiter);
					for(int fireIndex = 0; fireIndex < Constants.fireRadi.length; fireIndex++){
						Constants.fireRadius = Constants.fireRadi[fireIndex];
						// write fire
						writer.write(Constants.fireRadius+Constants.delimiter);
						for(int yellIndex = 0; yellIndex < Constants.yellRadi.length; yellIndex++){
							Constants.yellRadius = Constants.yellRadi[yellIndex];							
							// write yelling
							writer.write(Constants.yellRadius+Constants.delimiter);
							for(int yellRelayIndex = 0; yellRelayIndex < Constants.yellRelays.length; yellRelayIndex++){
								Constants.yellRelay = Constants.yellRelays[yellRelayIndex];
								Constants.yellVolume = Constants.yellRadius * Constants.yellRelay;
								// write yell relay
								writer.write(Constants.yellRelay+Constants.delimiter);							
								for(int pheroIndex = 0; pheroIndex < Constants.pheromoneDecays.length; pheroIndex++){
									Constants.pheromoneDecay = Constants.pheromoneDecays[pheroIndex];
									// write pheromone decay
									writer.write(Constants.pheromoneDecay+Constants.delimiter);
									for (int i = 0; i < Constants.trials ; i++){
										Droid.droidNo = 0;
										Fire.fireNo = 0;
										long startTime = System.currentTimeMillis();
										result = runSim(writer);
										System.gc();
										long stopTime = System.currentTimeMillis();
									    long elapsedTime = stopTime - startTime;
									    // write time
									    writer.write(Constants.delimiter + elapsedTime+Constants.delimiter);
									    System.out.println("Run "+run+"/"+totalRuns);
									    run++;
									    writer.flush();
									    
									    Runtime runtime = Runtime.getRuntime();
	
									    StringBuilder sb = new StringBuilder();
									    long maxMemory = runtime.maxMemory();
									    long allocatedMemory = runtime.totalMemory();
									    long freeMemory = runtime.freeMemory();
	
									    sb.append("free memory: " + freeMemory / 1024 + "\n");
									    sb.append("allocated memory: " + allocatedMemory / 1024+ "\n");
									    sb.append("max memory: " + maxMemory / 1024+ "\n");
									    sb.append("total free memory: " + (freeMemory + (maxMemory - allocatedMemory)) / 1024 + "\n");
									    System.out.println(sb.toString());
									}
								}
							}
						}
					}
					writer.write("\n");
				}
				mapCount++;
			}
			writer.close();
			
			System.exit(0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static int writeheader(PrintWriter writer, int mapCount){
		int totalRuns = 0;
		writer.write("Map"+Constants.delimiter);
		for(int fireIndex = 0; fireIndex < Constants.fireRadi.length; fireIndex++){
			writer.write("FireRadius "+(fireIndex+1)+Constants.delimiter);
			for(int yellIndex = 0; yellIndex < Constants.yellRadi.length; yellIndex++){
				writer.write("YellRadius "+(yellIndex+1)+Constants.delimiter);
				for(int yellRelayIndex = 0; yellRelayIndex < Constants.yellRelays.length; yellRelayIndex++){
					writer.write("Yell relay "+(yellRelayIndex+1)+Constants.delimiter);
					for(int pheroIndex = 0; pheroIndex < Constants.pheromoneDecays.length; pheroIndex++){
						writer.write("PheromoneDecay "+(pheroIndex+1)+Constants.delimiter);
						for (int i = 0; i < Constants.trials ; i++){
							writer.write("Trial"+i+": Nr. of Droids "+Constants.delimiter);
							writer.write("Trial"+i+": Iter until "+((1-Constants.fireExtinguishedMilestone)*100) +"%" +Constants.delimiter);
							writer.write("Trial"+i+": Iter fin"+Constants.delimiter);
							writer.write("Trial"+i+": Percentag fire remaining"+Constants.delimiter);
							writer.write("Trial"+i+": Time"+Constants.delimiter);
							totalRuns++;
						}
					}
				}
			}
		}
	    int toolazy = 0;
		for(int NumberOfDroids = Constants.initialNumberOfDroids; NumberOfDroids < Constants.maxDroids+1; NumberOfDroids = NumberOfDroids+Constants.numberOfDroidsIncrease){
			toolazy++;
		}
		writer.write("\n");
		return totalRuns*mapCount*toolazy;
	}
	
	public static int runSim(PrintWriter writer) {
		
		JFrame window; // The object that represents the window.
		window = new JFrame("Grid");  // Create a window with "Grid" in the title bar.
		
		EnvironmentParser envParser = new EnvironmentParser();
		Grid grid = envParser.parseImage(Constants.mapFile);
		writer.write("" + Droid.droidNo);
		
		window.setContentPane(grid);  // Add the Grid panel to the window.
		window.pack(); // Set the size of the window based on the panel's preferred size.

		window.setResizable(false);
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(Constants.visualizeProgress);
		window.addKeyListener(grid);
		
		int w = (int) window.getWidth();
		
		StatWin statWin; // The object that represents the window.
		statWin = new StatWin(Constants.mapFile, writer);  // Create a window with "Grid" in the title bar.
		statWin.setVisible(Constants.visualizeProgress);
		statWin.setLocationRelativeTo(window);
		statWin.setLocation(w + 100, 0);

		grid.finalizeSimStep();
		
		int itNo = 0;
		double initialFireNumber = Fire.fireNo;
		boolean milestoneReached = false;
		while(Fire.fireNo != 0 && itNo < Constants.maxIterations)
		{
			if(!grid.isPaused()){
				if(((double)Fire.fireNo)/initialFireNumber < Constants.fireExtinguishedMilestone && !milestoneReached){
					writer.write(Constants.delimiter+itNo);
					milestoneReached = true;
				}
				
				itNo++;
				grid.Spin(grid);
				statWin.updateValues(Droid.droidNo, Fire.fireNo, itNo);
			}
			try {
			    Thread.sleep(Constants.sleepDuration);                 //200 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		
		// check if fire extinguishing milestone was not reached
		if(!milestoneReached){
			writer.write(Constants.delimiter+"-1");
		}
		// Check if all fires were extinguished
		if(Fire.fireNo != 0){
			writer.write(Constants.delimiter+"-1");
			writer.write(Constants.delimiter+((double)Fire.fireNo)/initialFireNumber);
		} else {
			writer.write(Constants.delimiter+itNo);
			writer.write(Constants.delimiter+"1.0");
		}
		
		
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		statWin.dispatchEvent(new WindowEvent(statWin, WindowEvent.WINDOW_CLOSING));
		window.dispose();
		statWin.dispose();
		return itNo;
	}
}
