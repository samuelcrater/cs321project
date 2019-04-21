package cs321project;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI extends JFrame {

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem menuItem;
	private RequirementsView reqView;
	private ScheduleView schedView;
	
	private ScheduleController sc;
	
	public GUI() {
		
		//makes GUI style itself like its host OS.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		this.createMenuBar();
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));
		reqView = new RequirementsView();
		this.add(reqView);
		//this.add(Box.createRigidArea(new Dimension(5, 0))); //optional padding
		schedView = new ScheduleView();
		this.add(schedView);
		
		this.setTitle("Degree Planner");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(1000,600));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
	}
    
    private void createMenuBar() {
    	menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		menuItem = new JMenuItem("New");
		menuItem.setPreferredSize(new Dimension(150, menuItem.getPreferredSize().height));
		//Toolkit will return Ctrl on Windows and Cmnd on Mac
		menuItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//dummy action
				setSize(900,700);
			}
			
		});
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("Open");
		menuItem.setPreferredSize(new Dimension(150, menuItem.getPreferredSize().height));
		//Toolkit will return Ctrl on Windows and Cmnd on Mac
		menuItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//dummy action
				setSize(900,700);
			}
			
		});
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("Save");
		menuItem.setPreferredSize(new Dimension(150, menuItem.getPreferredSize().height));
		//Toolkit will return Ctrl on Windows and Cmnd on Mac
		menuItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sc.saveSchedule();
			}
			
		});
		fileMenu.add(menuItem);
		
		this.setJMenuBar(menuBar);
    }
    
    //must be called before used
    public void setScheduleController(ScheduleController sc) {
    	this.sc = sc;
    	reqView.setScheduleController(sc);
    	schedView.setScheduleController(sc);
    }
    
	//must be called after initialized
    public void display() {
        this.setVisible(true);
    }
    
    public void updateDegree(HashMap<String, ArrayList<Requirement>> requirements) {
    	reqView.populate(requirements);
    }
    
    public void updateSchedule(ArrayList<Semester> semesters) {
    	System.out.println(semesters.size());
    	schedView.populate(semesters);
    }
}
