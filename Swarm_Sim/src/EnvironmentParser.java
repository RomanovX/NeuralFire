import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;

public class EnvironmentParser {

	public Grid parseImage(String filename) {
		byte[] pixels = null;
		BufferedImage image = null;
		try {
			image = ImageIO.read(main.class.getResource(filename));
			pixels = ((DataBufferByte) image.getRaster().getDataBuffer())
					.getData();
		} catch (Exception e) {
			System.out.println("Could not read/find image");
			e.printStackTrace();
		}
		final int pixelLength = 3;
		final int width = image.getWidth();
		final int height = image.getHeight();
		Grid grid = new Grid(height, width, Constants.preferredSquareSize);
		int blue = 0;
		int red = 0;
		int green = 0;
		for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {

			blue = ((int) pixels[pixel] & 0xff);
			green = ((int) pixels[pixel + 1] & 0xff);
			red = ((int) pixels[pixel + 2] & 0xff);

			Color fieldColor = new Color(red, green, blue);
			if (fieldColor.equals(Constants.droidColor)) {
				grid.AddObject(row, col, new droid());

			} else if (fieldColor.equals(Constants.fireColor)) {
				grid.AddObject(row, col, new fire());

			} else if (fieldColor.equals(Constants.wallColor)) {
				grid.AddObject(row, col, new wall());
			}

			col++;
			if (col == width) {
				col = 0;
				row++;
			}
		}
		return grid;
	}

}
