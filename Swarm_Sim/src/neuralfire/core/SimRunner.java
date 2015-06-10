package neuralfire.core;

import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class SimRunner implements Runnable{
	private String result;
	
	Grid grid;
	
	int droidsPerSpawner;
	String mapFile;
	double pheromoneDecay;
	int yellRadius;
	int yellRelay;

	
	SimRunner(){
		EnvironmentParser envParser = new EnvironmentParser();
		
		droidsPerSpawner = Constants.droidsPerSpawner;
		mapFile = Constants.mapFile;
		pheromoneDecay = Constants.pheromoneDecay;
		yellRadius = Constants.yellRadius;
		yellRelay = Constants.yellRelay;
				
		grid = envParser.parseImage(mapFile, droidsPerSpawner, pheromoneDecay, yellRadius, yellRelay);
				
		result = new String("" + mapFile + Constants.delimiter + droidsPerSpawner + Constants.delimiter + pheromoneDecay + Constants.delimiter +
				            yellRadius + Constants.delimiter + yellRelay + Constants.delimiter);
	}
		
	public String runSim() {
		JFrame window; // The object that represents the window.
		window = new JFrame("Grid");  // Create a window with "Grid" in the title bar.
				
		window.setContentPane(grid);  // Add the Grid panel to the window.
		window.pack(); // Set the size of the window based on the panel's preferred size.

		window.setResizable(false);
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(Constants.visualizeProgress);
		window.addKeyListener(grid);
		
		int w = (int) window.getWidth();
		
		StatWin statWin; // The object that represents the window.
		statWin = new StatWin(Constants.mapFile);  // Create a window with "Grid" in the title bar.
		statWin.setVisible(Constants.visualizeProgress);
		statWin.setLocationRelativeTo(window);
		statWin.setLocation(w + 100, 0);

		grid.finalizeSimStep();
		
		int itNo = 0;
		double initialFireNumber = grid.getFireLeft();
		boolean milestoneReached = false;
		while(grid.getFireLeft() != 0 && itNo < Constants.maxIterations)
		{
			if(!grid.isPaused()){
				if(((double)grid.getFireLeft())/initialFireNumber < Constants.fireExtinguishedMilestone && !milestoneReached){
					result.concat(Constants.delimiter+itNo);
					milestoneReached = true;
				}
				
				itNo++;
				grid.Spin(grid);
				statWin.updateValues(grid.getNumDroids(), grid.getFireLeft(), itNo);
			}
			Thread.yield();/*allow other threads to run*/
		}
		
		// check if fire extinguishing milestone was not reached
		if(!milestoneReached){
			result = result.concat("-1" + Constants.delimiter);
		}
		// Check if all fires were extinguished
		if(grid.getFireLeft() != 0){
			result = result.concat("-1" + Constants.delimiter);
			result = result.concat(grid.getFireLeft() + Constants.delimiter);
		} else {
			result = result.concat(itNo + Constants.delimiter);
			result = result.concat("1.0" +Constants.delimiter);
		}
		
		
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		statWin.dispatchEvent(new WindowEvent(statWin, WindowEvent.WINDOW_CLOSING));
		window.dispose();
		statWin.dispose();
		
		result = result.concat("\n");
		return result;
	}

	@Override
	public void run() {
		result = runSim();
		
		ResultPrinter printer = new ResultPrinter();
		boolean done = false;
			
		while(!done){
			if(!printer.getWriterInUse()){
				done = printer.WriteLine(result);
				break;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}