import javax.swing.*;

public class main {
	
	static final int GRIDROWS = 100;
	static final int GRIDCOLS = 100;
	
	/**
	 * This main routine creates a window and sets its content
	 * to be a panel of type Grid.  It shows the window in the
	 * center of the screen.
	 */
	public static void main(String[] args) {
		JFrame window; // The object that represents the window.
		window = new JFrame("Grid");  // Create a window with "Grid" in the title bar.
		Grid grid = new Grid(GRIDROWS,GRIDCOLS,10);  // Create an object of type Grid.
		window.setContentPane(grid);  // Add the Grid panel to the window.
		window.pack(); // Set the size of the window based on the panel's preferred size.

		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		

		
		for(int i = 0; i < GRIDROWS; i++)
		{
			for (int j = 0; j < GRIDCOLS; j++)
			{
				if(i == 0 || i == 99 || j == 0 || j == 99)
					grid.AddObject(i,j, new wall());
			}
		}

		grid.AddObject(50,50, new droid());	
		grid.AddObject(52,50, new wall());	
		
		grid.finalizeSimStep();
		
		while(true)
		{
			grid.Spin(grid);
			
			try {
			    Thread.sleep(200);                 //200 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
}
