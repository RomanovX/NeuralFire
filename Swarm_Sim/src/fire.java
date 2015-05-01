public class fire extends object{
	public fire(){
		movable = false;
		stackable = false;
	}
	
	public void extinguish()
	{
		stackable = true;
	}
	
	public static void intRaytrace(int x0, int y0, int x1, int y1, Grid grid) 
	{
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int x = x0;
        int y = y0;
        int n = 1 + dx + dy;
        int x_inc = (x1 > x0) ? 1 : -1;
        int y_inc = (y1 > y0) ? 1 : -1;
        int error = dx - dy;

        dx *= 2;
        dy *= 2;

        for (; n > 0; --n) {
        	grid.getField(y, x);

            if (error > 0) {
                x += x_inc;
                error -= dy;
            } else if (error < 0) {
                y += y_inc;
                error += dx;
            } else {
                x += x_inc;
                y += y_inc;
                n--;
            }
        }
    }
}
