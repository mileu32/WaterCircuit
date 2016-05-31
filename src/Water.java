import java.awt.Color;
import java.util.Random;

public class Water {
	
	boolean life;
	
	static Random r = new Random();
	
	double lx, ly; // location x, location y
	double sl; // speed limit
	double vx, vy; //velocity x, velocity y
	double mass;
	double size;
	
	Color color;
	
	Water(double locationX, double locationY) {
		this(locationX, locationY, 2*r.nextGaussian()-1, 2*r.nextGaussian()-1, 3, 5, 5, Color.CYAN);
		

		
	}
	
	Water(double locationX, double locationY, double velocityX, double velocityY, double speedLimit, double mass, double size, Color color) {
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

	public void update() {

		lx+=vx;
		ly+=vy;

	}

	public double check(int mouseX, int mouseY) {
		// TODO Auto-generated method stub

		return 0;
	} // check()


}
