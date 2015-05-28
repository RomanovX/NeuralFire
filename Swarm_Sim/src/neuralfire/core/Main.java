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
			writer = new PrintWriter("SimResults.txt", "UTF-8");
			
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
				Constants.useMapDirectory = true;
				
			}
			
			java.nio.file.Path currentRelativePath = Paths.get("");
			String s = currentRelativePath.toAbsolutePath().toString();
			List<String> maps = new ArrayList<String>();
			if(Constants.useMapDirectory){
				File directory = new File("src/environmentMaps/"+Constants.mapDirectory+"/");
				for (final File fileEntry : directory.listFiles()) {
			        if (!fileEntry.isDirectory()) {
			        	maps.add("src/environmentMaps/"+Constants.mapDirectory+"/"+fileEntry.getName());
			        }
			    }
			} 
			else {
				maps.add("src/environmentMaps/"+Constants.mapFile);
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
			writer.write("##################################\n");
			writer.write("Nr Of Droids, Run 1,Run 2,Run 3, Run 4, Run 5\n");
			writer.write("##################################\n");
			
			
			for(String map : maps){
				Constants.mapFile = map;
				writer.write("Map: "+map+"\n");
				for(int NumberOfDroids = Constants.initialNumberOfDroids; NumberOfDroids < Constants.maxDroids+1; NumberOfDroids = NumberOfDroids+Constants.numberOfDroidsIncrease){
					Constants.droidsPerSpawner = NumberOfDroids;
					for (int i = 0; i < Constants.trials ; i++){
						Droid.droidNo = 0;
						Fire.fireNo = 0;
						long startTime = System.currentTimeMillis();
						result = runSim(writer);
						long stopTime = System.currentTimeMillis();
					    long elapsedTime = stopTime - startTime;
					    writer.write(Constants.delimiter + elapsedTime);
					}
					writer.write("\n");
				}
			}
			writer.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		window.setVisible(true);
		window.addKeyListener(grid);
		
		int w = (int) window.getWidth();
		
		StatWin statWin; // The object that represents the window.
		statWin = new StatWin(Constants.mapFile);  // Create a window with "Grid" in the title bar.
		statWin.setVisible(true);
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
		} else {
			writer.write(Constants.delimiter+itNo);
		}
		
		
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		statWin.dispatchEvent(new WindowEvent(statWin, WindowEvent.WINDOW_CLOSING));
		return itNo;
	}
}
