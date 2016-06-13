import java.awt.Color;

class Stick {

	boolean ifStatic;
	boolean ifDraw;

	double lx1, ly1; // location x 1, location y 1
	double lx2, ly2; // location x 2, location y 2

	double vx, vy; // velocity x, velocity y
	double mass;
	double size;

	Water water[];

	Color color;

	Stick(double locationX1, double locationY1, double locationX2, double locationY2, boolean ifStatic) {
		this(locationX1, locationY1, locationX2, locationY2, 0, 5, Color.ORANGE, ifStatic);
	}

	Stick(double locationX1, double locationY1, double locationX2, double locationY2, double mass, double size,
			Color color, boolean ifStatic) {

		this.lx1 = locationX1;
		this.ly1 = locationY1;
		this.lx2 = locationX2;
		this.ly2 = locationY2;

		this.mass = mass;
		this.size = size;
		this.color = color;

		this.ifStatic = ifStatic;

		double length = Math.sqrt((lx2 - lx1) * (lx2 - lx1) + (ly2 - ly1) * (ly2 - ly1));

		int waterCount = (int) (length - 0.1) / 5 + 1;

		this.water = new Water[waterCount];

		// divide with 0 make error!
		if (waterCount == 1)
			this.water[0] = new Water((lx1 + lx2) / 2, (ly1 + ly2) / 2);

		else{
			for (int i = 0; i < water.length; i++)
				this.water[i] = new Water(lx1 + (lx2 - lx1) * i / (waterCount - 1), ly1 + (ly2 - ly1) * i / (waterCount - 1), 0, 0, 2, true, true);
			this.water[0].charge = 0.9;
			this.water[water.length - 1].charge = 0.9;
		}
	}

	public void move(double x, double y) {
		this.lx1 += x;
		this.lx2 += x;
		this.ly1 += y;
		this.ly2 += y;
		for(int i = 0; i < this.water.length; i++){
			this.water[i].lx += x;
			this.water[i].ly += y;
		}
	}
	
	
	//clockwise
	public void spin(double theta) {

		double[] pointCache;
		double middlePointX = (this.lx1 + this.lx2) / 2;
		double middlePointY = (this.ly1 + this.ly2) / 2;
		
		pointCache = spinPoint(this.lx1, this.ly1, middlePointX, middlePointY, theta);
		this.lx1 = pointCache[0];
		this.ly1 = pointCache[1];
		
		pointCache = spinPoint(this.lx2, this.ly2, middlePointX, middlePointY, theta);
		this.lx2 = pointCache[0];
		this.ly2 = pointCache[1];
		
		for(int i = 0; i < this.water.length; i++){
			pointCache = spinPoint(this.water[i].lx, this.water[i].ly, middlePointX, middlePointY, theta);
			this.water[i].lx = pointCache[0];
			this.water[i].ly = pointCache[1];
		}
		
	}
	
	public double[] spinPoint(double pointX, double pointY, double middlePointX, double middlePointY, double theta){

		double returnDouble[];
		returnDouble = new double[2];
		
		double cacheTheta = Math.atan2(pointY - middlePointY, pointX - middlePointX);
		double length = Math.sqrt((pointX - middlePointX) * (pointX - middlePointX) + (pointY - middlePointY) * (pointY - middlePointY));
		pointX = length * Math.cos(cacheTheta + theta);
		pointY = length * Math.sin(cacheTheta + theta);
		
		returnDouble[0] = pointX + middlePointX;
		returnDouble[1] = pointY + middlePointY;
		
		return returnDouble;
	}
	
	public void update() {

	}

	public double check(int mouseX, int mouseY) {

		return 0;
	} // check()

}
