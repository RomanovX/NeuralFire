package neuralfire.core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		PrintWriter writer;
		int result = 0;
		
		try {
			writer = new PrintWriter("SimResults", "UTF-8");
			
			if(Constants.configuration == 1){
				Constants.mapFile = "map1";
				Constants.fireRadius = 3;
				Constants.yellRadius = 4;
				Constants.scaleUI = 5;
				Constants.sleepDuration = 200;
			} else if (Constants.configuration == 2){
				Constants.mapFile = "map2";
				Constants.fireRadius = 10;
				Constants.yellRadius = 20;
				Constants.scaleUI = 1;
				Constants.sleepDuration = 20;
			} else if(Constants.configuration == 3){
				Constants.mapFile = "map3";
				Constants.fireRadius = 3;
				Constants.yellRadius = 4;
				Constants.scaleUI = 5;
				Constants.sleepDuration = 0;
			} else if (Constants.configuration == 4){
				Constants.mapFile = "map4";
				Constants.fireRadius = 15;
				Constants.yellRadius = 30;
				Constants.scaleUI = 0.4;
				Constants.sleepDuration = 20;
			}
			
			writer.write("Nr Of Droids, Run 1,Run 2,Run 3, Run 4, Run 5\n");
			
			for(int NumberOfDroids = 50; NumberOfDroids < 500; NumberOfDroids = NumberOfDroids+10){
				writer.write("" + NumberOfDroids);
				Constants.droidsPerSpawner = NumberOfDroids;
				for (int i = 0; i < 5 ; i++){
					result = runSim();
					writer.write("," + result);
				}
				writer.write("\n");
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
	
	/**
	 * This main routine creates a window and sets its content
	 * to be a panel of type Grid.  It shows the window in the
	 * center of the screen.
	 */
	private static int runSim() {
		int nrOfIterations = 0;
		boolean done = false;
		
		JFrame window; // The object that represents the window.
		window = new JFrame("Grid");  // Create a window with "Grid" in the title bar.
		
		EnvironmentParser envParser = new EnvironmentParser();
		Grid grid = envParser.parseImage("../../environmentMaps/"+Constants.mapFile+".bmp");
		
		window.setContentPane(grid);  // Add the Grid panel to the window.
		window.pack(); // Set the size of the window based on the panel's preferred size.

		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		grid.finalizeSimStep();
		
		while(!done)
		{
			grid.Spin(grid);
			nrOfIterations++;
			try {
			    Thread.sleep(Constants.sleepDuration);                 //200 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		return nrOfIterations;
	}
}
