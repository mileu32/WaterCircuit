import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class WaterCircuitInterface extends JFrame implements ActionListener, MouseListener {
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem newMenuItem, loadMenuItem, saveMenuItem, saveasMenuItem;

	WaterCircuitCanvas canvas;
	JPanel optionPanel;

	WaterCircuitInterface() {
		super("WaterCircuit v0.1.0b1 (BUILD 1) by mileu");
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

		optionPanel = new JPanel();
		add(optionPanel);

		setVisible(true);
		setSize(600, 300);
		setLocation(100, 100);
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

	}

	class WaterCircuitCanvas extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			setBackground(Color.BLACK);

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
