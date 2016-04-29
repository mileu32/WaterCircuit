import java.awt.Color;
import java.util.Random;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Water {
	
	boolean life;


	 
	public Vec2  speed;
	Color col;
	
	BodyDef bodyDef;
	Body waterBody;
	CircleShape waterShape;
	FixtureDef fixtureDef;
	Water(World world) {
		speed = new Vec2(0, 0);

		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		waterBody = world.createBody(bodyDef);
		waterShape = new CircleShape();
		waterShape.setRadius(10);
		fixtureDef = new FixtureDef();
		fixtureDef.shape = waterShape;
		fixtureDef.density = 1;
	    fixtureDef.friction = 0.3f;
	    waterBody.createFixture(fixtureDef);
	    waterBody.setLinearVelocity(speed);
	    
	    

		life = true;
		
	}

	public void update() {
		Random r1 = new Random();

	}

	public double check(int mouseX, int mouseY) {
		// TODO Auto-generated method stub

		return 0;
	} // check()


}
