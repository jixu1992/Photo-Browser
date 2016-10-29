import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author jinxu
 * This is the main class of this photo browser.
 */
public class PhotoBrowser extends JFrame{
	
	private static final long serialVersionUID = 1L;
	static JLabel statusBar = new JLabel("Hello");	
	private Box categoryBtnBox = Box.createVerticalBox();
	
	//Shape choice buttons
	JButton pencil = new JButton("pencil");
	JButton ellipse = new JButton("ellipse");
	JButton rectangle = new JButton("rectangle");
	JButton straightline = new JButton("line");
	
	//Shape button group
	ButtonGroup shapeButtonGroup = new ButtonGroup();
	
	public boolean hasImage;
	
	private JToolBar shapeTBar;	
	private ArrayList<PhotoComponent> listPC = new ArrayList<PhotoComponent>();	
	private PhotoComponent photo_canvas = new PhotoComponent();
	

	public PhotoComponent getPhoto_canvas() {
		return photo_canvas;
	}

	public void setPhoto_canvas(PhotoComponent photo_canvas) {
		this.photo_canvas = photo_canvas;
	}

	//The background panel of the photo browser
	private JPanel mainPanel = new JPanel(new BorderLayout()){
		//Set the background picture
		protected void paintComponent(Graphics g) {  
            ImageIcon icon = new ImageIcon("wallpaper.jpg");  
            Image img = icon.getImage();  
            g.drawImage(img, 0, 0, mainPanel.getWidth(),  
                    mainPanel.getHeight(), icon.getImageObserver());  
            //setSize(icon.getIconWidth(), icon.getIconHeight());  

        }  
	};
	
	public static void main(String[] args){
		PhotoBrowser pBrowser = new PhotoBrowser();
		pBrowser.setVisible(true);
	}
	
	public PhotoBrowser(){
		super("My photo browser");
		
		//The main menu bar on top
		JMenuBar topMenu = new JMenuBar();
		
		//The menus "file" and "view" in the main menu bar
		JMenu fileMenu = new JMenu("File");
		JMenu viewMenu = new JMenu("View");
		
		setJMenuBar(topMenu);
		
		//Add the 2 menus on the top menu
		topMenu.add(fileMenu);
		topMenu.add(viewMenu);
		
		//File -> Import/Delete/Quit
		JMenuItem importItem = new JMenuItem("Import");
		JMenuItem deleteItem = new JMenuItem("Delete");
		JMenuItem quitItem = new JMenuItem("Quit");
		
		
		add(mainPanel, BorderLayout.CENTER);
					
		importItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				importImage();
			}
		});
			
		deleteItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				deletePhoto();
			}
		});
		
		quitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				closeWindow();
			}
		});
				
		fileMenu.add(importItem);
		fileMenu.add(deleteItem);
		fileMenu.add(quitItem);
		
		//Different viewing modes
		JRadioButton photoViewerButton = new JRadioButton("Photo viewer", true);
		JRadioButton browserButton = new JRadioButton("Browser", false);
		JRadioButton splitModeButton = new JRadioButton("Split mode", false);
		
		photoViewerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				photoViewerMode();
			}
		});
		
		browserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				browserMode();
			}
		});
		
		splitModeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				splitMode();
			}
		});
				
		viewMenu.add(photoViewerButton);
		viewMenu.add(browserButton);
		viewMenu.add(splitModeButton);
		
		ButtonGroup viewBtnGroup = new ButtonGroup();
		viewBtnGroup.add(photoViewerButton);
		viewBtnGroup.add(browserButton);
		viewBtnGroup.add(splitModeButton);	
		
		//The panel on the left
		JPanel tbPanel = new JPanel();
				
		//Tool bar in the left panel
		JToolBar leftTBar = new JToolBar();
		add(leftTBar,BorderLayout.NORTH);
		
		leftTBar.add(categoryBtnBox);		
		tbPanel.add(leftTBar);		
		add(tbPanel,BorderLayout.WEST);

		JToggleButton familyButton = new JToggleButton("Family",true);
		JToggleButton vacationButton = new JToggleButton("Vacation",false);
		JToggleButton schoolButton = new JToggleButton("School",false);
				
		familyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				familyMode();
			}
		});
		
		vacationButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				vacationMode();
			}
		});
		
		schoolButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				schoolMode();
			}
		});
		
		categoryBtnBox.add(familyButton);
		categoryBtnBox.add(vacationButton);
		categoryBtnBox.add(schoolButton);
		
		ButtonGroup categoryBtnGroup = new ButtonGroup();
		categoryBtnGroup.add(familyButton);
		categoryBtnGroup.add(vacationButton);
		categoryBtnGroup.add(schoolButton);
		
		
		JPanel stPanel = new JPanel();
		stPanel.add(statusBar);
		add(stPanel, BorderLayout.SOUTH);
				
		JPanel shapePanel = new JPanel();
		shapeTBar = new JToolBar();
		shapeTBar.setVisible(true);
		
		shapePanel.add(shapeTBar);
							
		add(shapeTBar, BorderLayout.NORTH);
									
		shapeTBar.add(pencil);
		shapeTBar.add(ellipse);
		shapeTBar.add(rectangle);
		shapeTBar.add(straightline);
		
		
		shapeButtonGroup.add(pencil);
		shapeButtonGroup.add(ellipse);
		shapeButtonGroup.add(rectangle);
		shapeButtonGroup.add(straightline);
		
		//initialize the selected button
		shapeButtonGroup.setSelected(pencil.getModel(),true);

		setPreferredSize(new Dimension(1000, 800));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
	}
	


	public void setStatusBar(String status){
		statusBar.setText(status);
	}
	
	public void importImage() {		
		//choose a local file
		JFileChooser jfilechooser = new JFileChooser();
		jfilechooser.setMultiSelectionEnabled(true);
		int result = jfilechooser.showOpenDialog(this);
		if(result == JFileChooser.APPROVE_OPTION){
			File[] listFiles = jfilechooser.getSelectedFiles();
			statusBar.setText("Importing Photos!");
			for(File f:listFiles){
				photo_canvas.importPhoto(f.getPath());
				System.out.println(photo_canvas);
				photo_canvas.setNewImage(true);
				listPC.add(photo_canvas);
			}
		}		
		initImage();
		setShapeTool();
	}
	
	public void initImage() {
		JScrollPane imgScroll = new JScrollPane(photo_canvas);
		imgScroll.setOpaque(false);
		imgScroll.getViewport().setOpaque(false);
		
		imgScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		imgScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mainPanel.removeAll();
		mainPanel.add(imgScroll, BorderLayout.CENTER);
		mainPanel.revalidate();
		photo_canvas.repaint();	
		photo_canvas.isFlipped = false;
	}
	
	public void setShapeTool(){
		pencil.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				photo_canvas.setShapeChoice(0);
				System.out.println(photo_canvas.getShapeChoice());
				shapeButtonGroup.setSelected(pencil.getModel(),true);
			}
		});
		ellipse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				photo_canvas.setShapeChoice(1);
				System.out.println(photo_canvas.getShapeChoice());
				shapeButtonGroup.setSelected(ellipse.getModel(),true);
			}
		});
		rectangle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				photo_canvas.setShapeChoice(2);
				System.out.println(photo_canvas.getShapeChoice());
				shapeButtonGroup.setSelected(rectangle.getModel(),true);
			}
		});
		straightline.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				photo_canvas.setShapeChoice(3);
				System.out.println(photo_canvas.getShapeChoice());
				shapeButtonGroup.setSelected(straightline.getModel(),true);
			}
		});
		
	}
	
	public void closeWindow(){
		this.setVisible(false);
		dispose();
	}
	
	public void deletePhoto(){
		mainPanel.removeAll();
		repaint();
		setStatusBar("Photo Deleted");
	}
	
	public void browserMode(){
		setStatusBar("Browser Mode!");
	}
	
	public void photoViewerMode(){
		setStatusBar("Photo Viewer Mode!");
	}
	
	public void splitMode(){
		setStatusBar("Split Mode!");
	}
	
	public void familyMode(){
		setStatusBar("Photo category: family!");
	}
	
	public void vacationMode(){
		setStatusBar("Photo category: vacation!");
	}
	
	public void schoolMode(){
		setStatusBar("Photo category: school!");
	}
}
