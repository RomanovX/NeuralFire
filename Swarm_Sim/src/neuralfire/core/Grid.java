package neuralfire.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This class represents a "panel" that displays a grid of colored squares.
 * The class also include a main() routine that creates a window containing
 * a panel of this type.
 */
public class Grid extends JPanel {

	private int gridRows; // Number of rows of squares.
	private int gridCols; // Number of columns of squares.
	private Color lineColor; // Color for lines drawn between squares; if null, no lines are drawn.
	private Field[][] ObjGrid = new Field[100][100];
		 
	/**
	 * This constructor creates a panel with a specified number of rows and columns
	 * of squares.  It sets the preferred size of the panel depending on the
	 * preferred square size.  It also wires up mouse event handling.
	 * @param rows  The number of rows of squares in the panel.
	 * @param columns  The number of columns of squares in the panel.
	 * @param preferredSquareSize  The desired size, in pixels, for the squares.  This will
	 *     be used to compute the preferred size of the panel.  Depending on the context
	 *     in which it is used, the actual size of the panel might be different.  The
	 *     main() routine in this class will respect the preferred size.  (Note that the
	 *     "squares" might become rectangles if the preferred size is not respected.)
	 */
	public Grid(int rows, int columns, int preferredSquareSize) {
		gridRows = rows;
		gridCols = columns;
		lineColor = Constants.lineColor;
		
		for(int i = 0; i < gridRows; i++)
		{
			for(int j = 0; j < gridCols; j++)
			{
				ObjGrid[i][j] = new Field(i, j, this);
				if(i-1 >= 0){
					Path path = new Path(ObjGrid[i][j], ObjGrid[i-1][j]);
					ObjGrid[i][j].AddPath(path, Constants.Dir.DOWN);
					ObjGrid[i-1][j].AddPath(path, Constants.Dir.UP);
					
				}
				if(j-1 >= 0){
					Path path = new Path(ObjGrid[i][j], ObjGrid[i][j-1]);
					ObjGrid[i][j].AddPath(path, Constants.Dir.RIGHT);
					ObjGrid[i][j-1].AddPath(path, Constants.Dir.LEFT);
					
				}
			}
		}
		
		setPreferredSize( new Dimension(preferredSquareSize*columns*Constants.scaleUI, preferredSquareSize*rows*Constants.scaleUI) );
		setBackground(Constants.backgroundColor); // Set the background color for this panel.		
	}
	
	/**
	 * Draws the grid of squares.  Also draws lines separating the squares if lineColor
	 * is not null.  The technique used here will exactly fill the panel with colored
	 * rectangles, whether the panel has its preferred size or not.
	 */
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0,0,getWidth(),getHeight());
		int row, col;
		double cellWidth = (double)getWidth() / gridCols;
		double cellHeight = (double)getHeight() / gridRows;
		for (row = 0; row < gridRows; row++) {
			for (col = 0; col < gridCols; col++) {
				//if (ObjGrid[row][col].GetColor() != Color.white) {
					int x1 = (int)(col*cellWidth);
					int y1 = (int)(row*cellHeight);
					int x2 = (int)((col+1)*cellWidth);
					int y2 = (int)((row+1)*cellHeight);
					g.setColor(ObjGrid[row][col].GetColor());
					g.fillRect( x1, y1, (x2-x1), (y2-y1) );
					
					// Debug path code
					/*
					if(ObjGrid[row][col].getPath(Constants.Dir.LEFT) != null){
						int curX = x1 + (x2-x1)/2;
						int curY = y1 + (y2-y1)/2;
						int neighborX = (int) (curX - cellWidth);
						g.setColor(new Color(0, 0, 255));
						g.drawLine(curX, curY, neighborX, curY);
					}
					if(ObjGrid[row][col].getPath(Constants.Dir.UP) != null){
						int curX = x1 + (x2-x1)/2;
						int curY = y1 + (y2-y1)/2;
						int neighborY = (int) (curY - cellHeight);
						g.setColor(new Color(0, 0, 255));
						g.drawLine(curX, curY, curX, neighborY);
					}
					*/
					//g.drawChars(new char[]{'t'}, 1, 5, x1, y1);
					g.setColor(Color.blue);
					//g.drawString(""+ObjGrid[row][col].getConcentratedPheromoneCount(), x1, y1);
					if(Constants.debug){
						if(ObjGrid[row][col].getRightPath()  != null)
							g.drawString(""+ObjGrid[row][col].getRightPath().getPheromoneIntensity(), x1, (int)(y1+cellHeight/2));
						if(ObjGrid[row][col].getDownPath()  != null)
							g.drawString(""+ObjGrid[row][col].getDownPath().getPheromoneIntensity(), (int)(x1+cellWidth/2), y1);
					}
					
				//}
			}
		}
		if (lineColor != null) {
			g.setColor(lineColor);
			for (row = 1; row < gridRows; row++) {
				int y = (int)(row*cellHeight);
				g.drawLine(0,y,getWidth(),y);
			}
			for (col = 1; col < gridRows; col++) {
				int x = (int)(col*cellWidth);
				g.drawLine(x,0,x,getHeight());
			}
		}
	}
		
	public void Spin(Grid grid)
	{
		//clearYelling();
		for(int i = 0; i < gridRows; i++)
		{
			for(int j = 0; j < gridCols; j++)
			{
				ObjGrid[i][j].UpdateField(grid);
			}
		}
		
		finalizeSimStep();
	}
	
	public void finalizeSimStep()
	{
		for(int i = 0; i < gridRows; i++)
		{
			for(int j = 0; j < gridCols; j++)
			{
				ObjGrid[i][j].finalizeFieldUpdate();
			}
		}
		repaint();
	}
	
	public void AddObject(int row, int col, WorldObject x)
	{
		ObjGrid[row][col].AddObject(x);
	}
	
	public Field getField(int row, int col)
	{
		return ObjGrid[row][col];
	}
	
	public int getGridRows(){
		return gridRows;
	}
	
	public int getGridCols(){
		return gridCols;
	}
	
	private void clearYelling()
	{
		for(int x = 0;x < gridRows; x++)
		{
			for(int y = 0;y < gridCols; y++)
			{
				ObjGrid[x][y].clearVolume();
			}
		}
	}
} // end class Grid
