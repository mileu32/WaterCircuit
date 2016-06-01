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
	//Water[] waters;
	int update_period = 60;

	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem newMenuItem, loadMenuItem, saveMenuItem, saveasMenuItem;

	WaterCircuitCanvas canvas;
	JPanel optionPanel;

	WaterCircuitInterface() {

		super("WaterCircuit v0.1.0b1 (BUILD 6) by mileu");
		
		waters = new ArrayList<Water>();
		sticks = new ArrayList<Stick>();
		//waters = new Water[500];
		for (int i = 0; i < 10; i++) {
			waters.add(new Water(600,362));
		}

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
		optionPanel = new JPanel();
		add(optionPanel);
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
		waterEngine();
		for (int i = 0; i < waters.size(); i++) {
			Water cacheWater = waters.get(i);
			if (cacheWater.life) {
				cacheWater.update();
			}
		}
		canvas.repaint();
	}

	class WaterCircuitCanvas extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			setBackground(Color.BLACK);

			for (int i = 0; i < 24; i++) {
				for (int j = 0; j < 14; j++){
					g.setColor(Color.orange);
					g.drawOval(i*50 + 25 - 3, j*50 + 25 - 3, 6, 6);
				}
			}
			
			for (int i = 0; i < waters.size(); i++) {
				Water cacheWater = waters.get(i);
				if (cacheWater.life) {
					g.setColor(cacheWater.color);
					g.drawOval((int)cacheWater.lx, (int)cacheWater.ly, (int)cacheWater.size, (int)cacheWater.size);
				}
			}
			
			for (int i = 0; i < sticks.size(); i++) {
				Stick cacheStick = sticks.get(i);
				g.setColor(cacheStick.color);
				g.drawLine((int)cacheStick.lx1, (int)cacheStick.ly1, (int)cacheStick.lx2, (int)cacheStick.ly2);
			}

		}

	}

	public void waterEngine(){
		
		for (int i = 0; i < waters.size(); i++) {
			for (int j = 0; j < sticks.size(); j++){
				Water cacheWater = waters.get(i);
				Stick cacheStick = sticks.get(j);
				//ax + by + c = 0
				double a = cacheStick.ly2 - cacheStick.ly1;
				double b = cacheStick.lx1 - cacheStick.lx2;
				double c = cacheStick.ly1 * cacheStick.lx2 - cacheStick.lx1 * cacheStick.ly2;
				
				boolean ifCrossInfiniteLine = (a * cacheWater.lx + b * cacheWater.ly + c) * (a * (cacheWater.lx + cacheWater.vx) + b * (cacheWater.ly + cacheWater.vy) + c) < 0;
				
				if(ifCrossInfiniteLine){
					//cross point between two line;
					double px = ((cacheStick.lx1 * cacheStick.ly2 - cacheStick.ly1 * cacheStick.lx2) * (-cacheWater.vx) - (cacheStick.lx1 - cacheStick.lx2) * (cacheWater.lx * (cacheWater.ly + cacheWater.vy) - cacheWater.ly * (cacheWater.lx + cacheWater.vx)))/((cacheStick.lx1-cacheStick.lx2)*(-cacheWater.vy)-(cacheStick.ly1-cacheStick.ly2) * (-cacheWater.vx));
					
					boolean ifCrossLine = (px<=cacheStick.lx1 && px >=cacheStick.lx2) || (px<=cacheStick.lx2 && px >=cacheStick.lx1);
					if (ifCrossLine){
						System.out.println("collision!");
						
					}
				}
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		switch(e.getButton()){
		case 1:
			waters.add(new Water(e.getX(), e.getY()));
			break;
		case 2:
			System.out.println("try to develop later.. zoom in and out");
			break;
		case 3:
			sticks.add(new Stick(((int)(e.getX()-25)/50)*50+25, ((int)e.getY()/50)*50+25, ((int)(e.getX()-25)/50)*50+75, ((int)e.getY()/50)*50+25));
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
