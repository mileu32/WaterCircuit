import java.awt.Color;
import java.util.Random;

public class Water {

	boolean life;
	boolean updated;

	static Random r = new Random();

	double lx, ly; // location x, location y
	double sl; // speed limit
	double vx, vy; // velocity x, velocity y
	double mass;
	double size;

	Color color;

	Water(double locationX, double locationY) {
		this(locationX, locationY, r.nextGaussian() / 2 - 1 / 4, r.nextGaussian() / 2 - 1 / 4, 1, 5, 4, Color.CYAN);

	}
	
	Water(double locaionX, double locationY, double velocityX, double velocityY) {
		this(locaionX, locationY, velocityX, velocityY, 1, 5, 4, Color.CYAN);

	}

	Water(double locationX, double locationY, double velocityX, double velocityY, double speedLimit, double mass,
			double size, Color color) {
		life = true;
		this.lx = locationX;
		this.ly = locationY;
		this.vx = velocityX;
		this.vy = velocityY;
		this.sl = speedLimit;
		this.mass = mass;
		this.size = size;
		this.color = color;
	}

	public void apply() {
		
	}
	
	public void update() {
		if (!updated) {
			lx += vx;
			ly += vy;
		}
		updated = false;

	}

	public double check(int mouseX, int mouseY) {
		// TODO Auto-generated method stub

		return 0;
	} // check()

}
