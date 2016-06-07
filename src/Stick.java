import java.awt.Color;
import java.util.Random;

class Stick {

	static Random r = new Random();

	double lx1, ly1; // location x 1, location y 1
	double lx2, ly2; // location x 2, location y 2
	
	double sl; // spin limit
	double vx, vy; // velocity x, velocity y
	double mass;
	double size;

	Color color;

	Stick(double locationX1, double locationY1, double locationX2, double locationY2, double spinLimit) {
		this(locationX1, locationY1, locationX2, locationY2, spinLimit, 5, 5, Color.ORANGE);
	}
	
	Stick(double locationX1, double locationY1, double locationX2, double locationY2) {
		this(locationX1, locationY1, locationX2, locationY2, 0, 0, 5, Color.ORANGE);
	}

	Stick(double locationX1, double locationY1, double locationX2, double locationY2 , double spinLimit, double mass,
			double size, Color color) {
		
		this.lx1 = locationX1;
		this.ly1 = locationY1;
		this.lx2 = locationX2;
		this.ly2 = locationY2;
		
		this.sl = spinLimit;
		
		this.mass = mass;
		this.size = size;
		this.color = color;
	}

	public void update() {

	}

	public double check(int mouseX, int mouseY) {

		return 0;
	} // check()

}
