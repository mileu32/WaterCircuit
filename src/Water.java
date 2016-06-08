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
	double charge;
	double size;

	Color color;

	Water(double locationX, double locationY) {
		this(locationX, locationY, r.nextGaussian() / 2 - 1 / 4, r.nextGaussian() / 2 - 1 / 4, 5, 1, 4, Color.CYAN, false, true);
	}
	
	Water(double locationX, double locationY, double charge) {
		this(locationX, locationY, r.nextGaussian() / 2 - 1 / 4, r.nextGaussian() / 2 - 1 / 4, 5, charge, 4, Color.CYAN, false, true);
	}
	
	Water(double locationX, double locationY, boolean ifStatic, boolean ifDraw) {
		this(locationX, locationY, r.nextGaussian() / 2 - 1 / 4, r.nextGaussian() / 2 - 1 / 4, 5, 1, 4, Color.CYAN, ifStatic, ifDraw);
	}
	
	Water(double locationX, double locationY, double charge, boolean ifStatic, boolean ifDraw) {
		this(locationX, locationY, r.nextGaussian() / 2 - 1 / 4, r.nextGaussian() / 2 - 1 / 4, 5, charge, 4, Color.CYAN, ifStatic, ifDraw);
	}

	Water(double locaionX, double locationY, double velocityX, double velocityY) {
		this(locaionX, locationY, velocityX, velocityY, 5, 1, 4, Color.CYAN, false, true);

	}
	
	
	Water(double locaionX, double locationY, double velocityX, double velocityY, boolean ifStatic, boolean ifDraw) {
		this(locaionX, locationY, velocityX, velocityY, 5, 1, 4, Color.CYAN, ifStatic, ifDraw);

	}

	Water(double locaionX, double locationY, double velocityX, double velocityY, double charge) {
		this(locaionX, locationY, velocityX, velocityY, 5, charge, 4, Color.CYAN, false, true);

	}

	Water(double locationX, double locationY, double velocityX, double velocityY, double mass, double charge, double size,
			Color color, boolean ifStatic, boolean ifDraw) {
		
		this.lx = locationX;
		this.ly = locationY;
		this.vx = velocityX;
		this.vy = velocityY;

		this.mass = mass;
		this.charge = charge;
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
