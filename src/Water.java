import java.awt.Color;
import java.util.Random;

public class Water {

	boolean ifStatic;
	boolean ifDraw;
	boolean updated;

	static Random r = new Random();

	double lx, ly; // location x, location y

	double vx, vy; // velocity x, velocity y
	double mass;
	double size;

	Color color;

	Water(double locationX, double locationY) {
		this(locationX, locationY, r.nextGaussian() / 2 - 1 / 4, r.nextGaussian() / 2 - 1 / 4, 5, 4, Color.CYAN, false, true);
	}
	
	Water(double locationX, double locationY, boolean ifStatic, boolean ifDraw) {
		this(locationX, locationY, r.nextGaussian() / 2 - 1 / 4, r.nextGaussian() / 2 - 1 / 4, 5, 4, Color.CYAN, ifStatic, ifDraw);
	}

	Water(double locaionX, double locationY, double velocityX, double velocityY) {
		this(locaionX, locationY, velocityX, velocityY, 5, 4, Color.CYAN, false, true);

	}
	Water(double locaionX, double locationY, double velocityX, double velocityY, boolean ifStatic, boolean ifDraw) {
		this(locaionX, locationY, velocityX, velocityY, 5, 4, Color.CYAN, ifStatic, ifDraw);

	}

	Water(double locaionX, double locationY, double velocityX, double velocityY, double mass) {
		this(locaionX, locationY, velocityX, velocityY, mass, 4, Color.CYAN, false, true);

	}

	Water(double locationX, double locationY, double velocityX, double velocityY, double mass, double size,
			Color color, boolean ifStatic, boolean ifDraw) {
		
		this.lx = locationX;
		this.ly = locationY;
		this.vx = velocityX;
		this.vy = velocityY;

		this.mass = mass;
		this.size = size;
		this.color = color;

		this.ifStatic = ifStatic;
		this.ifDraw = ifDraw;
		
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
