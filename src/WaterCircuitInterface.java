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
	ArrayList<Object> objects;
	
	Water classWater = new Water(0,0);
	Stick classStick = new Stick(0,0,10,10,true);
	final int update_period = 60;
	final int max_number = 500;

	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem newMenuItem, loadMenuItem, saveMenuItem, saveasMenuItem;

	WaterCircuitCanvas canvas;
	JPanel optionPanel;

	WaterCircuitInterface() {

		super("WaterCircuit v0.1.0b2 (BUILD 16) by mileu");

		objects = new ArrayList<Object>();

		objects.add(new Stick(75, 275, 1125, 275, true));
		objects.add(new Stick(75, 325, 1125, 325, true));
		objects.add(new Stick(75, 375, 1125, 375, true));
		objects.add(new Stick(75, 425, 1125, 425, true));
		objects.add(new Stick(75, 325, 75, 375, true));
		objects.add(new Stick(1125, 325, 1125, 375, true));
		objects.add(new Stick(25, 325, 25, 375, true));
		objects.add(new Stick(1175, 325, 1175, 375, true));

		objects.add(new Stick(25, 325, 75, 275, true));
		objects.add(new Stick(25, 375, 75, 425, true));
		objects.add(new Stick(1175, 325, 1125, 275, true));
		objects.add(new Stick(1175, 375, 1125, 425, true));

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

		waterEngine(16);

		if (objects.size() < max_number)
			objects.add(new Water(950, 400));

		canvas.repaint();
	}

	class WaterCircuitCanvas extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {

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

			
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i).getClass() == classWater.getClass()) {
					Water cacheWater = (Water) objects.get(i);
					
					if (cacheWater.ifDraw) {
						g.setColor(cacheWater.color);
						g.drawOval((int) (cacheWater.lx - cacheWater.size / 2),
								(int) (cacheWater.ly - cacheWater.size / 2), (int) cacheWater.size,
								(int) cacheWater.size);
					}
				} else if (objects.get(i).getClass() == classStick.getClass()) {
					Stick cacheStick = (Stick) objects.get(i);
					g.setColor(cacheStick.color);
					g.drawLine((int) cacheStick.lx1, (int) cacheStick.ly1, (int) cacheStick.lx2, (int) cacheStick.ly2);

				}
			}
		}
	}

	public void semiWaterEngine(Water water1, Water water2) {
		double length = Math.sqrt(
				(water1.lx - water2.lx) * (water1.lx - water2.lx) + (water1.ly - water2.ly) * (water1.ly - water2.ly));
		
		if (length < 50 && length > 0) {
			// constant is just...optional??(임의의)
			double force = 64 / (length * length);
			if (force > 4)
				force = 4;
			double forceX = force * (water1.lx - water2.lx) / length;
			double forceY = force * (water1.ly - water2.ly) / length;

			if (!water1.ifStatic) {
				
				water1.vx += forceX / water1.mass;
				water1.vy += forceY / water1.mass;
			}

			if (!water2.ifStatic) {
				water2.vx += -forceX / water2.mass;
				water2.vy += -forceY / water2.mass;
			}
		}
	}

	public void semiWaterStickEngine(Water water, Stick stick) {
		for (int i = 0; i < stick.water.length; i++) {
			semiWaterEngine(water, stick.water[i]);
		}
	}

	public void waterEngine(int frequency) {
		for (int n = 0; n < frequency; n++) {
			// force between two water
			for (int i = 0; i < objects.size(); i++) {
				// force between two water
				for (int j = i + 1; j < objects.size(); j++) {

					if (objects.get(i).getClass() == classWater.getClass() && objects.get(j).getClass() == classWater.getClass())
						semiWaterEngine((Water) objects.get(i), (Water) objects.get(j));

					if (objects.get(i).getClass() == classWater.getClass() && objects.get(j).getClass() == classStick.getClass())
						semiWaterStickEngine((Water) objects.get(i), (Stick) objects.get(j));

					if (objects.get(i).getClass() == classStick.getClass() && objects.get(j).getClass() == classWater.getClass())
						semiWaterStickEngine((Water) objects.get(j), (Stick) objects.get(i));

					if (objects.get(i).getClass() == classStick.getClass() && objects.get(j).getClass() == classStick.getClass())
						continue;

				}
			}
			
			for (int i = 0; i < objects.size(); i++) {
				// visual.
				// <- red, -> cyan
				if (objects.get(i).getClass() == classWater.getClass()) {
					Water cacheWater = (Water) objects.get(i);
					
					if (cacheWater.vx < 0)
						cacheWater.color = Color.red;
					else
						cacheWater.color = Color.cyan;
					
					//battery
					if (cacheWater.lx > 900 && cacheWater.lx < 1000 && cacheWater.ly > 375 && cacheWater.ly < 425) cacheWater.vx -= 0.064;
					
					cacheWater.update();
					cacheWater.vx *= 0.988;
					cacheWater.vy *= 0.988;
				}

			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		switch (e.getButton()) {
		case 1:
			objects.add(new Water(e.getX(), e.getY()));
			break;
		case 2:
			System.out.println("try to develop later.. zoom in and out");
			break;
		case 3:
			objects.add(new Stick(((int) (e.getX() - 25) / 50) * 50 + 25, ((int) e.getY() / 50) * 50 + 25,
					((int) (e.getX() - 25) / 50) * 50 + 75, ((int) e.getY() / 50) * 50 + 25, true));
			break;

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
