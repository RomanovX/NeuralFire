import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * This class represents a "panel" that displays a grid of colored squares.
 * The class also include a main() routine that creates a window containing
 * a panel of this type.
 */
public class Grid extends JPanel {

	private int gridRows; // Number of rows of squares.
	private int gridCols; // Number of columns of squares.
	private Color lineColor; // Color for lines drawn between squares; if null, no lines are drawn.
	private field[][] ObjGrid = new field[100][100];
		 
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
		lineColor = Color.BLACK;
		
		for(int i = 0; i < gridRows; i++)
		{
			for(int j = 0; j < gridCols; j++)
			{
				ObjGrid[i][j] = new field(i, j);
			}
		}
		
		setPreferredSize( new Dimension(preferredSquareSize*columns, preferredSquareSize*rows) );
		setBackground(Color.WHITE); // Set the background color for this panel.		
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
				if (ObjGrid[row][col].GetColor() != Color.white) {
					int x1 = (int)(col*cellWidth);
					int y1 = (int)(row*cellHeight);
					int x2 = (int)((col+1)*cellWidth);
					int y2 = (int)((row+1)*cellHeight);
					g.setColor(ObjGrid[row][col].GetColor());
					g.fillRect( x1, y1, (x2-x1), (y2-y1) );
				}
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
		
	public void Spin()
	{
		for(int i = 0; i < gridRows; i++)
		{
			for(int j = 0; j < gridCols; j++)
			{
				ObjGrid[i][j].UpdateField();
			}
		}
		repaint();
	}
	
	public void AddObject(int col, int row, object x)
	{
		ObjGrid[col][row].AddObject(x);
	}
	
} // end class Grid