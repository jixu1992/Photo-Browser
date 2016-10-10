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

public class PhotoBrowser extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JLabel statusBar = new JLabel("Hello");	
	private JFileChooser chooser;	
	private Box bx = Box.createVerticalBox();
	private JPanel myPanel = new JPanel(new BorderLayout()){
		protected void paintComponent(Graphics g) {  
            ImageIcon icon = new ImageIcon("wallpaper.jpg");  
            Image img = icon.getImage();  
            g.drawImage(img, 0, 0, myPanel.getWidth(),  
                    myPanel.getHeight(), icon.getImageObserver());  
            //setSize(icon.getIconWidth(), icon.getIconHeight());  

        }  
	};
	
	public static void main(String[] args){
		PhotoBrowser myphototheque = new PhotoBrowser();
		myphototheque.setVisible(true);		
	}
	
	public PhotoBrowser(){
		super("My photo browser");
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu1 = new JMenu("File");
		JMenu menu2 = new JMenu("View");
		
		setJMenuBar(menuBar);
		
		menuBar.add(menu1);
		menuBar.add(menu2);
		
		chooser = new JFileChooser();
		
		JMenuItem importItem = new JMenuItem("Import");
		JMenuItem deleteItem = new JMenuItem("Delete");
		JMenuItem quitItem = new JMenuItem("Quit");
		
		
		//myPanel.setBackground(null);
		//myPanel.add(new ImagePanel());
		//JFrame frame = new JFrame();
		//frame.getContentPane().add(new ImagePanel());
		//frame.setVisible(true);
		add(myPanel, BorderLayout.CENTER);
					
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
				
		menu1.add(importItem);
		menu1.add(deleteItem);
		menu1.add(quitItem);
		
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
				
		menu2.add(photoViewerButton);
		menu2.add(browserButton);
		menu2.add(splitModeButton);
		
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(photoViewerButton);
		bGroup.add(browserButton);
		bGroup.add(splitModeButton);	
		
		JPanel tbPanel = new JPanel();
		
		JToolBar tBar = new JToolBar();
		
		add(tBar,BorderLayout.NORTH);
		tBar.add(bx);
		tbPanel.add(tBar);
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
		
		bx.add(familyButton);
		bx.add(vacationButton);
		bx.add(schoolButton);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(familyButton);
		bg.add(vacationButton);
		bg.add(schoolButton);
		
		
		
		JPanel stPanel = new JPanel();
		stPanel.add(statusBar);
		add(stPanel, BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(600, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
	}
	
	public void importImage() {
			int result = chooser.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				
				String imgpath = chooser.getSelectedFile().getPath();
				PhotoComponent pc = new PhotoComponent(imgpath);
					
				JScrollPane imgScroll = new JScrollPane(pc);
				imgScroll.setOpaque(false);
				imgScroll.getViewport().setOpaque(false);
				
				imgScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				imgScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				myPanel.removeAll();
				myPanel.add(imgScroll, BorderLayout.CENTER);
				myPanel.revalidate();
				pc.repaint();	
				
				pc.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e){
				        if(e.getClickCount()==2){
				            pc.setFlipped(!pc.isFlipped);
				            pc.repaint();
				        }
				    }
				});
			}						
	}
	
	public void closeWindow(){
		this.setVisible(false);
		dispose();
	}
	
	public void deletePhoto(){
		myPanel.removeAll();
		repaint();
		statusBar.setText("Photo Deleted!");
	}
	
	public void browserMode(){
		statusBar.setText("Browser Mode!");
	}
	
	public void photoViewerMode(){
		statusBar.setText("Photo Viewer Mode!");
	}
	
	public void splitMode(){
		statusBar.setText("Split Mode!");
	}
	
	public void familyMode(){
		statusBar.setText("Photo category: family!");
	}
	
	public void vacationMode(){
		statusBar.setText("Photo category: vacation!");
	}
	
	public void schoolMode(){
		statusBar.setText("Photo category: school!");
	}
}
