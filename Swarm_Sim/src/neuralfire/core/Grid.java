package neuralfire.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JPanel;

import rlforj.los.ILosBoard;

/**
 * This class represents a "panel" that displays a grid of colored squares. The
 * class also include a main() routine that creates a window containing a panel
 * of this type.
 */
public class Grid extends JPanel implements ILosBoard {

	private int gridRows; // Number of rows of squares.
	private int gridCols; // Number of columns of squares.
	private Color lineColor; // Color for lines drawn between squares; if null,
								// no lines are drawn.
	private Field[][] ObjGrid = new Field[100][100];

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

		for (int i = 0; i < gridRows; i++) {
			for (int j = 0; j < gridCols; j++) {
				ObjGrid[i][j] = new Field(i, j, this);
				if (i - 1 >= 0) {
					Path path = new Path(ObjGrid[i][j], ObjGrid[i - 1][j]);
					ObjGrid[i][j].AddPath(path, Constants.Dir.UP);
					ObjGrid[i - 1][j].AddPath(path, Constants.Dir.DOWN);

				}
				if (j - 1 >= 0) {
					Path path = new Path(ObjGrid[i][j], ObjGrid[i][j - 1]);
					ObjGrid[i][j].AddPath(path, Constants.Dir.LEFT);
					ObjGrid[i][j - 1].AddPath(path, Constants.Dir.RIGHT);

				}
			}
		}

		setPreferredSize(new Dimension(preferredSquareSize * columns
				* Constants.scaleUI, preferredSquareSize * rows
				* Constants.scaleUI));
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
				}
			}
		}
		if (lineColor != null) {
			g.setColor(lineColor);
			for (row = 1; row < gridRows; row++) {
				int y = (int) (row * cellHeight);
				g.drawLine(0, y, getWidth(), y);
			}
			for (col = 1; col < gridRows; col++) {
				int x = (int) (col * cellWidth);
				g.drawLine(x, 0, x, getHeight());
			}
		}
	}

	public void Spin(Grid grid) {
		repaint();
		clearYelling();
		for (int k = 0; k < Constants.relays; k++) {
			for (int i = 0; i < gridRows; i++) {
				for (int j = 0; j < gridCols; j++) {
					ObjGrid[i][j].relay(grid);
				}
			}
		}

		for (int i = 0; i < gridRows; i++) {
			for (int j = 0; j < gridCols; j++) {
				ObjGrid[i][j].UpdateField(grid);
			}
		}

		finalizeSimStep();
	}

	public void finalizeSimStep() {
		for (int i = 0; i < gridRows; i++) {
			for (int j = 0; j < gridCols; j++) {
				ObjGrid[i][j].finalizeFieldUpdate();
			}
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
		return x > 0 && y > 0 && x < gridCols && y < gridRows;
	}

	@Override
	public boolean isObstacle(int x, int y) {
		return ObjGrid[x][y].isHasWall();
	}

	@Override
	public void visit(int x, int y) {

	}
} // end class Grid
