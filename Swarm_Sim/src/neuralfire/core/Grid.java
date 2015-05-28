package neuralfire.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import rlforj.los.ILosBoard;

/**
 * This class represents a "panel" that displays a grid of colored squares. The
 * class also include a main() routine that creates a window containing a panel
 * of this type.
 */
public class Grid extends JPanel implements ILosBoard, KeyListener {

	private int gridRows; // Number of rows of squares.
	private int gridCols; // Number of columns of squares.
	private Color lineColor; // Color for lines drawn between squares; if null,
								// no lines are drawn.

	private Field[][] ObjGrid = new Field[350][350];
	private ArrayList<Path> paths;
	private boolean paused = false;

	/**
	 * This constructor creates a panel with a specified number of rows and
	 * columns of squares. It sets the preferred size of the panel depending on
	 * the preferred square size. It also wires up mouse event handling.
	 * 
	 * @param rows
	 *            The number of rows of squares in the panel.
	 * @param columns
	 *            The number of columns of squares in the panel.
	 * @param preferredSquareSize
	 *            The desired size, in pixels, for the squares. This will be
	 *            used to compute the preferred size of the panel. Depending on
	 *            the context in which it is used, the actual size of the panel
	 *            might be different. The main() routine in this class will
	 *            respect the preferred size. (Note that the "squares" might
	 *            become rectangles if the preferred size is not respected.)
	 */
	public Grid(int rows, int columns, int preferredSquareSize) {
		
		gridRows = rows;
		gridCols = columns;
		lineColor = Constants.lineColor;
		paths = new ArrayList<Path>();

		for (int i = 0; i < gridRows; i++) {
			for (int j = 0; j < gridCols; j++) {
				ObjGrid[i][j] = new Field(i, j, this);
				if (i - 1 >= 0) {
					Path path = new Path(ObjGrid[i][j], ObjGrid[i - 1][j]);
					ObjGrid[i][j].AddPath(path, Constants.Dir.UP);
					ObjGrid[i - 1][j].AddPath(path, Constants.Dir.DOWN);
					paths.add(path);

				}
				if (j - 1 >= 0) {
					Path path = new Path(ObjGrid[i][j], ObjGrid[i][j - 1]);
					ObjGrid[i][j].AddPath(path, Constants.Dir.LEFT);
					ObjGrid[i][j - 1].AddPath(path, Constants.Dir.RIGHT);
					paths.add(path);
				}
			}
		}

		setPreferredSize(new Dimension((int) (preferredSquareSize * columns
				* Constants.scaleUI), (int) (preferredSquareSize * rows
				* Constants.scaleUI)));
		setBackground(Constants.backgroundColor); // Set the background color
													// for this panel.
	}

	/**
	 * Draws the grid of squares. Also draws lines separating the squares if
	 * lineColor is not null. The technique used here will exactly fill the
	 * panel with colored rectangles, whether the panel has its preferred size
	 * or not.
	 */
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		int row, col;
		double cellWidth = (double) getWidth() / gridCols;
		double cellHeight = (double) getHeight() / gridRows;
		for (row = 0; row < gridRows; row++) {
			for (col = 0; col < gridCols; col++) {
				// if (ObjGrid[row][col].GetColor() != Color.white) {
				int x1 = (int) (col * cellWidth);
				int y1 = (int) (row * cellHeight);
				int x2 = (int) ((col + 1) * cellWidth);
				int y2 = (int) ((row + 1) * cellHeight);
				g2.setColor(ObjGrid[row][col].GetColor());
				g2.fillRect(x1, y1, (x2 - x1), (y2 - y1));

				if (ObjGrid[row][col].getDroidCounter() > 1) 
				{
					g2.setColor(new Color(0, 0, 0));
					g2.drawString("" + ObjGrid[row][col].getDroidCounter(),
							(int) (x1 + cellWidth / 2),
							(int) (y1 + cellHeight / 2));
				}
				
				
				
				// Debug path code
				if (Constants.displayPheromoneLines) {
					if (ObjGrid[row][col].getPath(Constants.Dir.LEFT) != null && ObjGrid[row][col].getPath(Constants.Dir.LEFT).getPheromoneIntensity() > 0) {
						int curX = x1 + (x2 - x1) / 2;
						int curY = y1 + (y2 - y1) / 2;
						int neighborX = (int) (curX - cellWidth);
						g.setColor(new Color(0, 0, 255));
						Stroke oldStroke = g2.getStroke();
						double thickness = Constants.maxPheromoneLineThickness
								* (ObjGrid[row][col].getPath(Constants.Dir.LEFT)
										.getPheromoneIntensity() / Path.currentMaxPheromone);
						g2.setStroke(new BasicStroke((float) thickness));
						//g2.setStroke(new BasicStroke(1.0f));
						g2.setColor(new Color(0.5f, 0.5f, 0f, 0.8f));
						g.drawLine(curX, curY, neighborX, curY);
						g2.setStroke(oldStroke);
					}
					if (ObjGrid[row][col].getPath(Constants.Dir.UP) != null && ObjGrid[row][col].getPath(Constants.Dir.UP).getPheromoneIntensity() > 0) {
						int curX = x1 + (x2 - x1) / 2;
						int curY = y1 + (y2 - y1) / 2;
						int neighborY = (int) (curY - cellHeight);
						g.setColor(new Color(0, 0, 255));
						Stroke oldStroke = g2.getStroke();
						double thickness = Constants.maxPheromoneLineThickness
								* (ObjGrid[row][col].getPath(Constants.Dir.UP)
										.getPheromoneIntensity() / Path.currentMaxPheromone);
						g2.setStroke(new BasicStroke((float) thickness));
						//g2.setStroke(new BasicStroke(1.0f));
						g2.setColor(new Color(0.5f, 0.5f, 0f, 0.8f));
						g2.drawLine(curX, curY, curX, neighborY);
						g2.setStroke(oldStroke);
					}
				}
				if (Constants.displayPheromoneValues) {
					if (ObjGrid[row][col].getPath(Constants.Dir.LEFT) != null && ObjGrid[row][col].getPath(Constants.Dir.LEFT).getPheromoneIntensity() > 0) {
						int curX = (int) ((x1 + (x2 - x1) / 2) - cellWidth/2);
						int curY = y1 + (y2 - y1) / 2;
						g.setColor(new Color(0, 0, 255));
						g.drawString(""+(int)ObjGrid[row][col].getPath(Constants.Dir.LEFT).getPheromoneIntensity(), curX, curY);
					}
					if (ObjGrid[row][col].getPath(Constants.Dir.UP) != null && ObjGrid[row][col].getPath(Constants.Dir.UP).getPheromoneIntensity() > 0) {
						int curX = x1 + (x2 - x1) / 2;
						int curY = (int) ((y1 + (y2 - y1) / 2) - cellHeight/2);
						g.setColor(new Color(0, 0, 255));
						g.drawString(""+(int)ObjGrid[row][col].getPath(Constants.Dir.UP).getPheromoneIntensity(), curX, curY);
					}
				}
				
				if (Constants.displayPheromoneDots) {
					double pheromone = ObjGrid[row][col].getConcentratedPheromoneCount();
					if(pheromone > Constants.pheromoneZeroThreshold){
						float alpha = (float) (pheromone/Field.maxPheromoneConcentration);
						alpha = Math.max(alpha, 0.2f);
						alpha = Math.min(alpha, 1.0f);
						int offset = 10;
						x1 = (int) (col * cellWidth);
						y1 = (int) (row * cellHeight);
						g.setColor(new Color(0.8f, 0.2f, 0.2f, alpha));
						g.fillOval(x1, y1, (int)cellWidth , (int)cellHeight);
					}
					
				}

				// Debug path code
				if (Constants.debug) {

					// g.drawChars(new char[]{'t'}, 1, 5, x1, y1);
					g.setColor(Color.blue);
					// g.drawString(""+ObjGrid[row][col].getConcentratedPheromoneCount(),
					// x1, y1);
					
					if (ObjGrid[row][col].getVolume() > 0)
						g.drawString("" + ObjGrid[row][col].getVolume(),
								(int) (x1 + cellWidth / 2),
								(int) (y1 + cellHeight / 2));

					if (ObjGrid[row][col].getRightPath() != null)
						g.drawString(""
								+ ObjGrid[row][col].getRightPath()
										.getPheromoneIntensity(), x1,
								(int) (y1 + cellHeight / 2));
					if (ObjGrid[row][col].getDownPath() != null)
						g.drawString(""
								+ ObjGrid[row][col].getDownPath()
										.getPheromoneIntensity(),
								(int) (x1 + cellWidth / 2), y1);
					if (ObjGrid[row][col].getFireCounter() > 1) 
					{
						g2.setColor(new Color(0, 0, 0));
						g2.drawString("" + ObjGrid[row][col].getFireCounter(),
								(int) (x1 + cellWidth / 2),
								(int) (y1 + cellHeight / 2));
					}
				}
			}
		}
		if (lineColor != null) {
			g.setColor(lineColor);
			for (row = 1; row < gridRows; row++) {
				int y = (int) (row * cellHeight);
				g.drawLine(0, y, getWidth(), y);
			}
			for (col = 1; col < gridCols; col++) {
				int x = (int) (col * cellWidth);
				g.drawLine(x, 0, x, getHeight());
			}
		}
		
	}

	public void Spin(Grid grid) {
		if(!paused){
			if(Constants.visualizeProgress)
				repaint();
			clearYelling();
			for (int i = 0; i < gridRows; i++) {
				for (int j = 0; j < gridCols; j++) {
					ObjGrid[i][j].relay(grid);
				}
			}
	
			for (int i = 0; i < gridRows; i++) {
				for (int j = 0; j < gridCols; j++) {
					ObjGrid[i][j].UpdateField(grid);
				}
			}
	
			finalizeSimStep();
		}
	}

	public void finalizeSimStep() {
		// pheromone decay
		doPathLogic();
		for (int i = 0; i < gridRows; i++) {
			for (int j = 0; j < gridCols; j++) {
				ObjGrid[i][j].finalizeFieldUpdate();
			}
		}
	}
	
	private void doPathLogic(){
		for(Path path : paths){
			path.doPathLogic();
		}
	}

	public void AddObject(int row, int col, WorldObject x) {
		ObjGrid[row][col].AddObject(x);
	}

	public Field getField(int row, int col) {
		return ObjGrid[row][col];
	}

	public int getGridRows() {
		return gridRows;
	}

	public int getGridCols() {
		return gridCols;
	}

	private void clearYelling() {
		for (int x = 0; x < gridRows; x++) {
			for (int y = 0; y < gridCols; y++) {
				ObjGrid[x][y].clearVolume();
			}
		}
	}

	@Override
	public boolean boardContains(int x, int y) {
		return x > 0 && y > 0 && x < gridRows && y <gridCols;
	}

	@Override
	public boolean isObstacle(int x, int y) {
		return ObjGrid[x][y].isHasWall();
	}

	@Override
	public void visit(int x, int y) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if(c == ' ')
			paused = !paused;
	}
	
	
	
	public boolean isPaused() {
		return paused;
	}

} // end class Grid
