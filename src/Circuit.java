import java.awt.Color;

public class Circuit {
	
	boolean ifDraw;
	
	//direction : ↑ : 1, → : 2, ↓ : 3, ← : 4, ↗ : 5, ↘ : 6, ↙ : 7, ↖ : 8
	int direction;
	
	int blockX, blockY;
	
	Stick stick[];
	Water water[];
	
	Color color;
	
	Circuit (int direction, int blockX, int blockY){
		this.direction = direction;
		this.blockX = blockX;
		this.blockY = blockY;
	}
	
}
