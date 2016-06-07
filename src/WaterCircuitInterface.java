import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class WaterCircuitInterface extends JFrame implements ActionListener, MouseListener {
	ArrayList<Water> waters;
	ArrayList<Stick> sticks;

	int update_period = 60;
	int max_number = 800;

	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem newMenuItem, loadMenuItem, saveMenuItem, saveasMenuItem;

	WaterCircuitCanvas canvas;
	JPanel optionPanel;

	WaterCircuitInterface() {

		super("WaterCircuit v0.1.0b1 (BUILD 13) by mileu");

		waters = new ArrayList<Water>();
		sticks = new ArrayList<Stick>();

		// for (int i = 0; i < 1000; i++) waters.add(new Water(50, 362));
		// for (int i = 0; i < max_number; i++) waters.add(new Water(950, 400));

		sticks.add(new Stick(75, 275, 1125, 275));
		sticks.add(new Stick(75, 325, 1125, 325));
		sticks.add(new Stick(75, 375, 1125, 375));
		sticks.add(new Stick(75, 425, 1125, 425));
		sticks.add(new Stick(75, 325, 75, 375));
		sticks.add(new Stick(1125, 325, 1125, 375));
		sticks.add(new Stick(25, 325, 25, 375));
		sticks.add(new Stick(1175, 325, 1175, 375));

		sticks.add(new Stick(25, 325, 75, 275));
		sticks.add(new Stick(25, 375, 75, 425));
		sticks.add(new Stick(1175, 325, 1125, 275));
		sticks.add(new Stick(1175, 375, 1125, 425));

		// sticks.add(new Stick(330, 275, 630, 300));
		// sticks.add(new Stick(630, 275, 630, 300));

		// sticks.add(new Stick(130, 325, 430, 300));
		// sticks.add(new Stick(430, 325, 430, 300));

		// sticks.add(new Stick(230, 390, 230, 425));
		// sticks.add(new Stick(230, 390, 930, 425));

		// for OS X
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		// Top Menu
		setLayout(new GridLayout(1, 3, 5, 5));
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		newMenuItem = new JMenuItem("New");
		loadMenuItem = new JMenuItem("Open File");
		saveMenuItem = new JMenuItem("Save");
		saveasMenuItem = new JMenuItem("Save As");

		// 단축기 설정, META_MASK : command
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.META_MASK));
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.META_MASK));

		fileMenu.add(newMenuItem);
		fileMenu.add(loadMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveasMenuItem);

		this.setJMenuBar(menuBar);

		canvas = new WaterCircuitCanvas();
		canvas.addMouseListener(this);
		add(canvas);
		Timer t = new Timer(update_period, this);
		t.start();

		/*
		 * optionPanel = new JPanel(); add(optionPanel);
		 */

		setVisible(true);
		setSize(1200, 725);
		setLocation(40, 50);

		// pack();

		// exit program, not only close window
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		waterEngine(4);
		//waterEngine(4);
		//waterEngine(4);
		//waterEngine(4);
		if (waters.size() < max_number)
			waters.add(new Water(950, 400));
		for (int i = 0; i < waters.size(); i++) {
			if (waters.get(i).lx < 0 || waters.get(i).lx > 1300 || waters.get(i).ly < 0 || waters.get(i).ly > 1000) {
				waters.remove(i);
				System.out.println("removed!");
			}
		}
		canvas.repaint();
	}

	class WaterCircuitCanvas extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub

			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			setBackground(Color.BLACK);

			for (int i = 0; i < 24; i++) {
				for (int j = 0; j < 14; j++) {
					g.setColor(Color.orange);
					g.drawOval(i * 50 + 25 - 3, j * 50 + 25 - 3, 6, 6);
				}
			}

			for (int i = 0; i < waters.size(); i++) {
				Water cacheWater = waters.get(i);
				if (cacheWater.life) {
					g.setColor(cacheWater.color);
					g.drawOval((int) cacheWater.lx, (int) cacheWater.ly, (int) cacheWater.size, (int) cacheWater.size);
				}
			}

			for (int i = 0; i < sticks.size(); i++) {
				Stick cacheStick = sticks.get(i);
				g.setColor(cacheStick.color);
				g.drawLine((int) cacheStick.lx1, (int) cacheStick.ly1, (int) cacheStick.lx2, (int) cacheStick.ly2);
			}

		}

	}

	public void semiWaterStickEngine(Water water, int neglect) {
		// battery
		if (water.lx > 900 && water.lx < 1000 && water.ly > 375 && water.ly < 425) water.vx -= 0.064;

		// force(?) between stick and water
		int collisionStickNum = -1;

		double collisionStickX = 0, collisionStickY = 0;

		for (int j = 0; j < sticks.size(); j++) {
			if (j != neglect) {
				Stick cacheStick = sticks.get(j);
				// ax + by + c = 0
				double a = cacheStick.ly2 - cacheStick.ly1;
				double b = cacheStick.lx1 - cacheStick.lx2;
				double c = cacheStick.ly1 * cacheStick.lx2 - cacheStick.lx1 * cacheStick.ly2;

				boolean ifCrossInfiniteLine = (a * water.lx + b * water.ly + c)
						* (a * (water.lx + water.vx) + b * (water.ly + water.vy) + c) < 0;

				if (ifCrossInfiniteLine) {

					// cross point between two line;
					double px = ((cacheStick.lx1 * cacheStick.ly2 - cacheStick.ly1 * cacheStick.lx2) * (-water.vx)
							- (cacheStick.lx1 - cacheStick.lx2)
									* (water.lx * (water.ly + water.vy) - water.ly * (water.lx + water.vx)))
							/ ((cacheStick.lx1 - cacheStick.lx2) * (-water.vy)
									- (cacheStick.ly1 - cacheStick.ly2) * (-water.vx));

					double py = ((cacheStick.lx1 * cacheStick.ly2 - cacheStick.ly1 * cacheStick.lx2) * (-water.vy)
							- (cacheStick.ly1 - cacheStick.ly2)
									* (water.lx * (water.ly + water.vy) - water.ly * (water.lx + water.vx)))
							/ ((cacheStick.lx1 - cacheStick.lx2) * (-water.vy)
									- (cacheStick.ly1 - cacheStick.ly2) * (-water.vx));
					// 실수 오차 해결..
					double verySmallD = 0.00001;
					boolean ifCrossLine = ((py <= cacheStick.ly1 + verySmallD && py >= cacheStick.ly2 - verySmallD)
							|| (py <= cacheStick.ly2 + verySmallD && py >= cacheStick.ly1 - verySmallD))
							&& ((px <= cacheStick.lx1 + verySmallD && px >= cacheStick.lx2 - verySmallD)
									|| (px <= cacheStick.lx2 + verySmallD && px >= cacheStick.lx1 - verySmallD));

					if (ifCrossLine) {

						if (collisionStickNum == -1) {
							collisionStickNum = j;
							collisionStickX = px;
							collisionStickY = py;
						} else {
							if ((water.lx - collisionStickX) * (water.lx - collisionStickX)
									+ (water.ly - collisionStickY) * (water.lx - collisionStickY) > (water.lx - px)
											* (water.lx - px) + (water.ly - py) * (water.lx - py)) {
								collisionStickNum = j;
								collisionStickX = px;
								collisionStickY = py;
							}
						}
					}
				}
			}
		}
		if (collisionStickNum != -1) {
			Stick cacheStick = sticks.get(collisionStickNum);
			double a = cacheStick.ly2 - cacheStick.ly1;
			double b = cacheStick.lx1 - cacheStick.lx2;

			Double cache = (((water.lx + water.vx) - cacheStick.lx1) * (cacheStick.lx2 - cacheStick.lx1)
					+ ((water.ly + water.vy) - cacheStick.ly1) * (cacheStick.ly2 - cacheStick.ly1))
					/ ((cacheStick.lx2 - cacheStick.lx1) * (cacheStick.lx2 - cacheStick.lx1)
							+ (cacheStick.ly2 - cacheStick.ly1) * (cacheStick.ly2 - cacheStick.ly1));

			// 막대는 정지했다고 가정
			Double colisionX = cache * (cacheStick.lx2 - cacheStick.lx1) + cacheStick.lx1;
			Double colisionY = cache * (cacheStick.ly2 - cacheStick.ly1) + cacheStick.ly1;

			Double afterColisionX = 2 * colisionX - (water.lx + water.vx);
			Double afterColisionY = 2 * colisionY - (water.ly + water.vy);

			// not yet set after v
			Double beforeSpeed = Math.sqrt(water.vx * water.vx + water.vy * water.vy);
			Double cacheS = (water.vy * a - water.vx * b) / (a * a + b * b);
			Double afterSpeedX, afterSpeedY;
			Double afterSpeed;
			afterSpeedX = 2 * b * cacheS + water.vx;
			afterSpeedY = water.vy - 2 * a * cacheS;
			afterSpeed = Math.sqrt(afterSpeedX * afterSpeedX + afterSpeedY * afterSpeedY);
			afterSpeedX = -afterSpeedX * beforeSpeed / afterSpeed;
			afterSpeedY = -afterSpeedY * beforeSpeed / afterSpeed;

			
			//water.updated = true;
			water.lx = afterColisionX - afterSpeedX;
			water.ly = afterColisionY - afterSpeedY;
			water.vx = afterSpeedX;
			water.vy = afterSpeedY;
			
			
			/*
			water.lx = afterColisionX - afterSpeedX;
			water.ly = afterColisionY - afterSpeedY;
			water.vx = afterSpeedX;
			water.vy = afterSpeedY;
			*/
			
			semiWaterStickEngine(water, collisionStickNum);

		}
	}

	public void waterEngine(int frequency) {
		for (int n = 0; n < frequency; n++) {

			// force between two water
			for (int i = 0; i < waters.size(); i++) {
				// force between two water
				for (int j = i + 1; j < waters.size(); j++) {

					Water cw1 = waters.get(i);
					Water cw2 = waters.get(j);
					double length = Math.sqrt((cw1.lx - cw2.lx) * (cw1.lx - cw2.lx) + (cw1.ly - cw2.ly) * (cw1.ly - cw2.ly));
					if (length < 50 && length > 0) {
						// constant is just...optional??(임의의)
						double force = 64 / (length * length);
						if (force > 4) force = 0;
						double forceX = force * (cw1.lx - cw2.lx) / length;
						double forceY = force * (cw1.ly - cw2.ly) / length;

						waters.get(i).vx += forceX / waters.get(i).mass;
						waters.get(i).vy += forceY / waters.get(i).mass;

						waters.get(j).vx += -forceX / waters.get(j).mass;
						waters.get(j).vy += -forceY / waters.get(j).mass;

					}
				}
			}

			for (int i = 0; i < waters.size(); i++) {
				// visual.
				// <- red, -> cyan
				semiWaterStickEngine(waters.get(i), -1);

				if (waters.get(i).vx < 0) waters.get(i).color = Color.red;
				else waters.get(i).color = Color.cyan;
				
				waters.get(i).update();
				waters.get(i).vx *= 0.988;
				waters.get(i).vy *= 0.988;
			}
			
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (e.getButton()) {
		case 1:
			waters.add(new Water(e.getX(), e.getY(), 0, 0));
			break;
		case 2:
			System.out.println("try to develop later.. zoom in and out");
			break;
		case 3:
			sticks.add(new Stick(((int) (e.getX() - 25) / 50) * 50 + 25, ((int) e.getY() / 50) * 50 + 25,
					((int) (e.getX() - 25) / 50) * 50 + 75, ((int) e.getY() / 50) * 50 + 25));
			break;

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
