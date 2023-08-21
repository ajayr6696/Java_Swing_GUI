import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FrameTest extends JFrame {

	private JTabbedPane tabbedPane;
	private JTextPane textPane;
	private List<Point> points1 = new ArrayList<>();
	private List<Point> points2 = new ArrayList<>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameTest frame = new FrameTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FrameTest() {
		// Set up the frame
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 800, 600);

	    // Create the menu bar
	    JMenuBar menuBar = new JMenuBar();
	    JMenu fileMenu = new JMenu("File");
	    JMenuItem openMenuItem = new JMenuItem("Open");
	    JMenuItem saveMenuItem = new JMenuItem("Save");
	    fileMenu.add(openMenuItem);
	    fileMenu.add(saveMenuItem);
	    menuBar.add(fileMenu);
	    JMenu helpMenu = new JMenu("Help");
	    menuBar.add(helpMenu);
	    setJMenuBar(menuBar);
	    
	    // Create the tabbedPane, which has the 2 tabs of the Graphic Pane
        tabbedPane = new JTabbedPane();
        // Setting Maximum width and height for the the tabbedPane so that it can be scrolled, 
        // and circle can be added anywhere, even if its resized
        Dimension preferredSize = new Dimension(1500, 800);
        tabbedPane.setPreferredSize(preferredSize);
        
        // Create the first graphic tab 
        //Override the paintComponent() method of the panel and paint the points in there when repaint method is called
        JPanel tab1Panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Point point : points1) {
                    g.setColor(Color.BLUE);
                    g.fillOval(point.x - 25, point.y - 25, 50, 50);
                }
            }
        };
        //Add tab1Panel Panel to the tabbedPane
        tabbedPane.addTab("Tab 1", tab1Panel);
        
        // Create the second graphic tab
        JPanel tab2Panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Point point : points2) {
                    g.setColor(Color.RED);
                    g.fillOval(point.x - 25, point.y - 25, 50, 50);
                }
            }
        };
        //Add tab1Panel Panel to the tabbedPane
        tabbedPane.addTab("Tab 2", tab2Panel);

        // A mouse click inside graphic pane of Tab 1 (tab1Panel) will add a circle to the pane,
        // and the point is stored in the list point1
        tab1Panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	System.out.println("Graphic Pane in Tab 1 clicked");
            	int x = e.getX();
                int y = e.getY();
                Point point = new Point(x, y);
                points1.add(point);
                Graphics g = tab1Panel.getGraphics();
                g.setColor(Color.BLUE);
                g.fillOval(x-25, y-25, 50, 50);
            }
        });
        
        // A mouse click inside graphic pane of Tab 2 (tab2Panel) will add a circle to the pane,
        // and the point is stored in the list point2
        tab2Panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	System.out.println("Graphic Pane in Tab 2 clicked");
                int x = e.getX();
                int y = e.getY();
                Point point = new Point(x, y);
                points2.add(point);
                Graphics g = tab2Panel.getGraphics();
                g.setColor(Color.RED);
                g.fillOval(x-25, y-25, 50, 50);
            }
        });
        
        // Method that is invoked, only when tab is changed in tabbedPane
        // The previous points that are stored in list for each tabs(points1 and points2) are repainted
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Get the index of the selected tab
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    System.out.println("Selected Tab 1");
                    tab1Panel.repaint();
                }
                else if (selectedIndex == 1) {
                    System.out.println("Selected Tab 2");
                    tab2Panel.repaint();
                }
            }
        });
        
        
        // Create the text pane
        textPane = new JTextPane();
        //Put “Hello, world!” in the text pane.
        textPane.setText("Hello, world!");
        
        
        // Create the scroll panes for tabbedPane and textPane, which are always visible
        JScrollPane tabbedScrollPane = new JScrollPane(tabbedPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JScrollPane textScrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        	
        /// Create the split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedScrollPane, textScrollPane);
        splitPane.setDividerLocation(400);
        
        // Add the Pane to the content Pane in the center area of a BorderLayout
        getContentPane().add(splitPane, BorderLayout.CENTER);
        
    }

}