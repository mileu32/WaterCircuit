import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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

public class WaterCircuitInterface extends JFrame implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
	ArrayList<Object> objects;
	
	Water classWater = new Water(0,0);
	Stick classStick = new Stick(0,0,10,10);
	
	final int update_period = 60;
	final int max_number = 000;

	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem newMenuItem, loadMenuItem, saveMenuItem, saveasMenuItem;

	WaterCircuitCanvas canvas;
	JPanel optionPanel;
	
	// 0 : water, 1~8 : circuit
	final int numMod = 8;
	int inputMod = 0;
	
	boolean ifPaused = true;
	
	int frame = 0;
	
	int mouseX = 0, mouseY = 0;

	WaterCircuitInterface() {

		super("WaterCircuit v0.2.0b1 (BUILD 23) by mileu");

		objects = new ArrayList<Object>();
		
		objects.add(new Stick(600, 325, 600, 425, false));
		
		
		objects.add(new Stick(1000, 325, 1000, 425, 0.014));
		objects.add(new Stick(950, 375, 1050, 375, 0.014));
		
		
		objects.add(new Stick(75, 275, 1125, 275));
		objects.add(new Stick(75, 325, 1125, 325));
		objects.add(new Stick(75, 375, 1125, 375));
		objects.add(new Stick(75, 425, 1125, 425));
		objects.add(new Stick(75, 325, 75, 375));
		objects.add(new Stick(1125, 325, 1125, 375));
		objects.add(new Stick(25, 325, 25, 375));
		objects.add(new Stick(1175, 325, 1175, 375));

		objects.add(new Stick(25, 325, 75 - 50 / Math.sqrt(2), 325 - 50 / Math.sqrt(2)));
		objects.add(new Stick(75 - 50 / Math.sqrt(2), 325 - 50 / Math.sqrt(2), 75, 275));
		
		objects.add(new Stick(25, 375, 75 - 50 / Math.sqrt(2), 375 + 50 / Math.sqrt(2)));
		objects.add(new Stick(75 - 50 / Math.sqrt(2), 375 + 50 / Math.sqrt(2), 75, 425));
		
		objects.add(new Stick(1175, 325, 1125 + 50 / Math.sqrt(2), 325 - 50 / Math.sqrt(2)));
		objects.add(new Stick(1125 + 50 / Math.sqrt(2), 325 - 50 / Math.sqrt(2), 1125, 275));
		
		objects.add(new Stick(1175, 375, 1125 + 50 / Math.sqrt(2), 375 + 50 / Math.sqrt(2)));
		objects.add(new Stick(1125 + 50 / Math.sqrt(2), 375 + 50 / Math.sqrt(2), 1125, 425));
		
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
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		addKeyListener(this);
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
		if(!ifPaused){
			
			waterEngine(8);

			frame++;
			frame = frame % 4;
			if (frame == 0 && objects.size() < max_number)
			objects.add(new Water(950, 400));
		}
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
			
			//mouse
			
			switch(inputMod){
			case 0:
				g.setColor(Color.cyan);
				g.drawString("  mode : water", mouseX, mouseY);
				
				g.setColor(Color.cyan);
				g.drawOval(mouseX - 2, mouseY - 2, 4, 4);
				
				break;
				
			case 1:
				g.setColor(Color.cyan);
				g.drawString("  mode : vertical circuit", mouseX, mouseY);
				
				g.setColor(Color.orange);
				g.drawLine(((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 25, ((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 75);
				g.drawLine(((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 25, ((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 75);
				
				g.setColor(Color.cyan);
				for(int i = 0; i < 4; i++)
					for(int j = 0; j < 3; j++)
						g.drawOval(((mouseX - 25) / 50) * 50 + 40 + 10 * j - 2 , ((mouseY - 25) / 50) * 50 + 35 + 10 * i - 2, 4, 4);
				
				break;
			
			case 2:
				g.setColor(Color.cyan);
				g.drawString("  mode : horizonal circuit", mouseX, mouseY);
				
				g.setColor(Color.orange);
				g.drawLine(((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 25, ((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 25);
				g.drawLine(((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 75, ((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 75);
				
				g.setColor(Color.cyan);
				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 4; j++)
						g.drawOval(((mouseX + 25) / 50) * 50 - 15 + 10 * j - 2 , ((mouseY - 25) / 50) * 50 + 40 + 10 * i - 2, 4, 4);
				
				break;
				
			case 3:
				g.setColor(Color.cyan);
				g.drawString("  mode : curved circuit 1", mouseX, mouseY);
				
				g.setColor(Color.orange);
				g.drawLine(((mouseX - 25) / 50) * 50 + 25 + (int) (50 / Math.sqrt(2)), ((mouseY - 25) / 50) * 50 + 75 - (int) (50 / Math.sqrt(2)), ((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 25);
				g.drawLine(((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 75, ((mouseX - 25) / 50) * 50 + 25 + (int) (50 / Math.sqrt(2)), ((mouseY - 25) / 50) * 50 + 75 - (int) (50 / Math.sqrt(2)));
				
				g.setColor(Color.cyan);
				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 3; j++)
						g.drawOval(((mouseX - 25) / 50) * 50 + 30 + 10 * j - 2 , ((mouseY - 25) / 50) * 50 + 70 - 10 * i - 2, 4, 4);
				
				break;
				
			case 4:
				g.setColor(Color.cyan);
				g.drawString("  mode : curved circuit 2", mouseX, mouseY);
				
				g.setColor(Color.orange);
				g.drawLine(((mouseX - 25) / 50) * 50 + 25 + (int) (50 / Math.sqrt(2)), ((mouseY - 25) / 50) * 50 + 25 + (int) (50 / Math.sqrt(2)), ((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 25);
				g.drawLine(((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 75, ((mouseX - 25) / 50) * 50 + 25 + (int) (50 / Math.sqrt(2)), ((mouseY - 25) / 50) * 50 + 25 + (int) (50 / Math.sqrt(2)));
				
				g.setColor(Color.cyan);
				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 3; j++)
						g.drawOval(((mouseX - 25) / 50) * 50 + 30 + 10 * j - 2 , ((mouseY - 25) / 50) * 50 + 30 + 10 * i - 2, 4, 4);
				
				break;
				
			case 5:
				g.setColor(Color.cyan);
				g.drawString("  mode : curved circuit 3", mouseX, mouseY);
				
				g.setColor(Color.orange);
				g.drawLine(((mouseX - 25) / 50) * 50 + 75 - (int) (50 / Math.sqrt(2)), ((mouseY - 25) / 50) * 50 + 25 + (int) (50 / Math.sqrt(2)), ((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 25);
				g.drawLine(((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 75, ((mouseX - 25) / 50) * 50 + 75 - (int) (50 / Math.sqrt(2)), ((mouseY - 25) / 50) * 50 + 25 + (int) (50 / Math.sqrt(2)));
				
				g.setColor(Color.cyan);
				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 3; j++)
						g.drawOval(((mouseX - 25) / 50) * 50 + 70 - 10 * j - 2 , ((mouseY - 25) / 50) * 50 + 30 + 10 * i - 2, 4, 4);
				
				break;
				
			case 6:
				g.setColor(Color.cyan);
				g.drawString("  mode : curved circuit 4", mouseX, mouseY);
				
				g.setColor(Color.orange);
				g.drawLine(((mouseX - 25) / 50) * 50 + 75 - (int) (50 / Math.sqrt(2)), ((mouseY - 25) / 50) * 50 + 75 - (int) (50 / Math.sqrt(2)), ((mouseX - 25) / 50) * 50 + 75, ((mouseY - 25) / 50) * 50 + 25);
				g.drawLine(((mouseX - 25) / 50) * 50 + 25, ((mouseY - 25) / 50) * 50 + 75, ((mouseX - 25) / 50) * 50 + 75 - (int) (50 / Math.sqrt(2)), ((mouseY - 25) / 50) * 50 + 75 - (int) (50 / Math.sqrt(2)));
				
				g.setColor(Color.cyan);
				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 3; j++)
						g.drawOval(((mouseX - 25) / 50) * 50 + 70 - 10 * j - 2 , ((mouseY - 25) / 50) * 50 + 70 - 10 * i - 2, 4, 4);
				
				break;
				
			case 7:
				g.setColor(Color.cyan);
				g.drawString("  mode : motor", mouseX, mouseY);
				
				g.setColor(Color.orange);
				g.drawLine(((int) (mouseX - 25) / 50) * 50, ((int) mouseY / 50) * 50 + 25,
				((int) (mouseX - 25) / 50) * 50 + 100, ((int) mouseY / 50) * 50 + 25);
				g.drawLine(((int) (mouseX - 25) / 50) * 50 + 50, ((int) mouseY / 50) * 50 - 25,
						((int) (mouseX - 25) / 50) * 50 + 50, ((int) mouseY / 50) * 50 + 75);
				break;
			}
			if(ifPaused) g.drawString("Paused", 10, 15);
			
		}
	}

	public void semiWaterEngine(Water water1, Water water2) {
		double length = Math.sqrt(
				(water1.lx - water2.lx) * (water1.lx - water2.lx) + (water1.ly - water2.ly) * (water1.ly - water2.ly));
		
		if (length < 50 && length > 0) {
			// constant is just...optional??(임의의)
			double force = 12 * water1.charge * water2.charge / (length * length);
			if (force > 12)
				force = 12;
			double forceX = force * (water1.lx - water2.lx) / length;
			double forceY = force * (water1.ly - water2.ly) / length;

			water1.vx += forceX / water1.mass;
			water1.vy += forceY / water1.mass;

			water2.vx += -forceX / water2.mass;
			water2.vy += -forceY / water2.mass;
		}
	}

	public void semiWaterStickEngine(Water water, Stick stick) {
		for (int i = 0; i < stick.water.length; i++) {
			semiWaterEngine(water, stick.water[i]);
		}
	}
	
	class ThreadEngine extends Thread {
		int start, end;

		public ThreadEngine(int start, int end) {
			//System.out.println(start + "에서부터 "+ end + "까지 연산하는 스레드가 생성되었습니다.");
			this.start = start;
			this.end = end;
		}
		
		public void run() {
			//System.out.println("연산이 시작됩니다.");
			long a = System.currentTimeMillis();
			for(int i = start; i < end; i++){
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
			long b = System.currentTimeMillis();
			//System.out.println(start+":"+end+" 연산이 완료되었습니다." + "실행 시간 : " +  (b - a) );

		}
	}
	public void waterEngine(int frequency) {
		for (int n = 0; n < frequency; n++) {
			// force between two water
			int thread0 = 0;
			//int thread1 = (int) (0.1339 * objects.size());
			int thread1 = (int) (0.08 * objects.size());
			int thread2 = (int) (0.2928 * objects.size());
			int thread3 = (int) (0.5 * objects.size());
			int thread4 = objects.size();
			
			ThreadEngine te1 = new ThreadEngine(thread0, thread1);
			ThreadEngine te2 = new ThreadEngine(thread1, thread2);
			ThreadEngine te3 = new ThreadEngine(thread2, thread3);
			ThreadEngine te4 = new ThreadEngine(thread3, thread4);
			
			te1.start();
			te2.start();
			te3.start();
			te4.start();
			
			try {
				te1.join();
				te2.join();
				te3.join();
				te4.join();
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			//System.out.println("calculation finished!");
			
			for (int i = 0; i < objects.size(); i++) {
				// update..
				if (objects.get(i).getClass() == classWater.getClass()) {
					Water cacheWater = (Water) objects.get(i);

					cacheWater.update();
					cacheWater.vx *= 0.996;
					cacheWater.vy *= 0.996;
				} else if(objects.get(i).getClass() == classStick.getClass()) {
					Stick cacheStick = (Stick) objects.get(i);
					cacheStick.update();
				}

			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(Math.atan2(1,Math.sqrt(3))/3.1415926535*180);
		System.out.println("mouseClicked");
		switch (e.getButton()) {
		case 1:
			if(inputMod == 0) objects.add(new Water(e.getX(), e.getY()));
			Circuit.createCircuit(inputMod, e.getX(), e.getY(), objects);
			break;
		case 2:
			System.out.println("try to develop later.. zoom in and out");
			break;
		case 3:
			objects.add(new Stick(((int) (e.getX() - 25) / 50) * 50 + 25, ((int) e.getY() / 50) * 50 + 25,
					((int) (e.getX() - 25) / 50) * 50 + 75, ((int) e.getY() / 50) * 50 + 25));
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

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		inputMod = (inputMod + 10 * numMod + e.getWheelRotation()) % numMod;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_P){
			   ifPaused = !ifPaused;
			   System.out.println("paused");
		   }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
