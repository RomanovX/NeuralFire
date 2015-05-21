package neuralfire.core;

import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
				Constants.mapFile = "map1";
				Constants.fireRadius = 3;
				Constants.yellRadius = 4;
				Constants.scaleUI = 5;
				Constants.sleepDuration = 0;
			} else if (Constants.configuration == 2){
				Constants.mapFile = "map2";
				Constants.fireRadius = 10;
				Constants.yellRadius = 25;
				Constants.scaleUI = 1;
				Constants.sleepDuration = 0;
				Constants.relays = 1;
			} else if(Constants.configuration == 3){
				Constants.mapFile = "map3";
				Constants.fireRadius = 3;
				Constants.yellRadius = 8;
				Constants.scaleUI = 5;
				Constants.sleepDuration = 0;
			} else if (Constants.configuration == 4){
				Constants.mapFile = "map4";
				Constants.fireRadius = 15;
				Constants.yellRadius = 50;
				Constants.scaleUI = 0.4;
				Constants.sleepDuration = 0;
			} else if (Constants.configuration == 5){
				Constants.mapFile = "map5";
				Constants.fireRadius = 10;
				Constants.yellRadius = 20;
				Constants.scaleUI = 1.5;
				Constants.sleepDuration = 0;
				Constants.displayPheromoneDots = true;
			}
			
			writer.write("Nr Of Droids, Run 1,Run 2,Run 3, Run 4, Run 5\n");
			
			for(int NumberOfDroids = 50; NumberOfDroids < 101; NumberOfDroids = NumberOfDroids+50){
				writer.write("" + NumberOfDroids);
				Constants.droidsPerSpawner = NumberOfDroids;
				for (int i = 0; i < 1 ; i++){
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
	
	public static int runSim() {
		
		JFrame window; // The object that represents the window.
		window = new JFrame("Grid");  // Create a window with "Grid" in the title bar.
		
		EnvironmentParser envParser = new EnvironmentParser();
		Grid grid = envParser.parseImage("../../environmentMaps/"+Constants.mapFile+".bmp");
		
		window.setContentPane(grid);  // Add the Grid panel to the window.
		window.pack(); // Set the size of the window based on the panel's preferred size.

		window.setResizable(false);
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		int w = (int) window.getWidth();
		
		StatWin statWin; // The object that represents the window.
		statWin = new StatWin();  // Create a window with "Grid" in the title bar.
		statWin.setVisible(true);
		statWin.setLocationRelativeTo(window);
		statWin.setLocation(w + 100, 0);

		grid.finalizeSimStep();
		
		int itNo = 0;
		
		while(Fire.fireNo != 0 && !StatWin.btnPressed)
		{
			itNo++;
			grid.Spin(grid);
			statWin.updateValues(Droid.droidNo, Fire.fireNo, itNo);
			
			try {
			    Thread.sleep(Constants.sleepDuration);                 //200 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		statWin.dispatchEvent(new WindowEvent(statWin, WindowEvent.WINDOW_CLOSING));
		return itNo;
	}
}
