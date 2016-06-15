import java.awt.Color;
import java.util.ArrayList;

public class Circuit {
	
	public static void createCircuit (int mode, int mouseX, int mouseY, ArrayList<Object> objects){
		
		switch(mode){
		case 1:
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 25, ((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 75));
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 25, ((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 75));
			
			for(int i = 0; i < 4; i++)
				for(int j = 0; j < 3; j++)
					objects.add(new Water(((mouseX - 25) / 50) * 50 + 40 + 10 * j, ((mouseY - 25) / 50) * 50 + 35 + 10 * i, 0, 0));
			
			break;
			
		case 2:
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 25, ((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 25));
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 75, ((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 75));
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 4; j++)
					objects.add(new Water(((mouseX + 25) / 50) * 50 - 15 + 10 * j, ((mouseY - 25) / 50) * 50 + 40 + 10 * i, 0, 0));
			
			break;
			
		case 3:
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 25 + 50 / Math.sqrt(2), ((mouseY - 25) / 50) * 50 + 75 - 50 / Math.sqrt(2), ((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 25));
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 75, ((mouseX - 25) / 50) * 50 + 25 + 50 / Math.sqrt(2), ((mouseY - 25) / 50) * 50 + 75 - 50 / Math.sqrt(2)));
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					objects.add(new Water(((mouseX - 25) / 50) * 50 + 30 + 10 * j, ((mouseY - 25) / 50) * 50 + 70 - 10 * i, 0, 0));
			
			break;
			
		case 4:
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 25 + 50 / Math.sqrt(2), ((mouseY - 25) / 50) * 50 + 25 + 50 / Math.sqrt(2), ((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 25));
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 75, ((mouseX - 25) / 50) * 50 + 25 + 50 / Math.sqrt(2), ((mouseY - 25) / 50) * 50 + 25 + 50 / Math.sqrt(2)));
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					objects.add(new Water(((mouseX - 25) / 50) * 50 + 30 + 10 * j, ((mouseY - 25) / 50) * 50 + 30 + 10 * i, 0, 0));
			
			break;
			
		case 5:
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 75 - 50 / Math.sqrt(2), ((mouseY - 25) / 50) * 50 + 25 + 50 / Math.sqrt(2), ((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 25));
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 75, ((mouseX - 25) / 50) * 50 + 75 - 50 / Math.sqrt(2), ((mouseY - 25) / 50) * 50 + 25 + 50 / Math.sqrt(2)));
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					objects.add(new Water(((mouseX - 25) / 50) * 50 + 70 - 10 * j, ((mouseY - 25) / 50) * 50 + 30 + 10 * i, 0, 0));
			
			break;
			
		case 6:
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 75 - 50 / Math.sqrt(2), ((mouseY - 25) / 50) * 50 + 75 - 50 / Math.sqrt(2), ((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 25));
			objects.add(new Stick(((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 75, ((mouseX - 25) / 50) * 50 + 75 - 50 / Math.sqrt(2), ((mouseY - 25) / 50) * 50 + 75 - 50 / Math.sqrt(2)));
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					objects.add(new Water(((mouseX - 25) / 50) * 50 + 70 - 10 * j, ((mouseY - 25) / 50) * 50 + 70 - 10 * i, 0, 0));
			
			break;
		
		case 7:
			objects.add(new Stick(((int) (mouseX - 25) / 50) * 50, ((int) mouseY / 50) * 50 + 25,
					((int) (mouseX - 25) / 50) * 50 + 100, ((int) mouseY / 50) * 50 + 25, 0.014));
			objects.add(new Stick(((int) (mouseX - 25) / 50) * 50 + 50, ((int) mouseY / 50) * 50 - 25,
							((int) (mouseX - 25) / 50) * 50 + 50, ((int) mouseY / 50) * 50 + 75, 0.014));
			break;
		}
		
	}
	
}
