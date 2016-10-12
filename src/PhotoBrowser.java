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
	private Box bxMode = Box.createHorizontalBox();
	private JButton btnDraw = new JButton("Draw");
	private JButton btnText = new JButton("Text");

	//The background panel of the photo browser
	private JPanel myPanel = new JPanel(new BorderLayout()){
		//Set the background picture
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
		
		//The main menu bar on top
		JMenuBar menuBar = new JMenuBar();
		
		//The menus "file" and "view" in the main menu bar
		JMenu menu1 = new JMenu("File");
		JMenu menu2 = new JMenu("View");
		
		setJMenuBar(menuBar);
		
		menuBar.add(menu1);
		menuBar.add(menu2);
		
		chooser = new JFileChooser();
		
		//File -> Import/Delete/Quit
		JMenuItem importItem = new JMenuItem("Import");
		JMenuItem deleteItem = new JMenuItem("Delete");
		JMenuItem quitItem = new JMenuItem("Quit");
		
		
		add(myPanel, BorderLayout.CENTER);
					
		importItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				importImageTest();
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
				
		menu2.add(photoViewerButton);
		menu2.add(browserButton);
		menu2.add(splitModeButton);
		
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(photoViewerButton);
		bGroup.add(browserButton);
		bGroup.add(splitModeButton);	
		
		//The panel on the left
		JPanel tbPanel = new JPanel();
		
		//The mode panel on top of the photo area
		JPanel modePanel = new JPanel();
		
		//Tool bar in the left panel
		JToolBar tBar = new JToolBar();
		
		//Tool bar on the top panel
		JToolBar tBarMode = new JToolBar();
		
		add(tBar,BorderLayout.NORTH);
		add(tBarMode, BorderLayout.SOUTH);
		tBar.add(bx);
		tBarMode.add(bxMode);
		tbPanel.add(tBar);
		modePanel.add(tBarMode);
		add(tbPanel,BorderLayout.WEST);
		add(modePanel, BorderLayout.NORTH);

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
		
		
		
		bxMode.add(btnDraw);
		bxMode.add(btnText);
				
		setPreferredSize(new Dimension(600, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
	}
	
	public void setStatusBar(String status){
		statusBar.setText(status);
	}
	
	public void importImageTest(){
		PhotoComponent pc = new PhotoComponent("pikachu.png");
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
		        
		        if(pc.isFlipped()){
					btnDraw.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							pc.setChoice(1);
							System.out.println("Drawing");
							//pc.repaint();
						}
					});
					
					btnText.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							pc.setChoice(2);
							System.out.println("Texting");
							//pc.repaint();
						}
					});
				}
		    }
			
			
		});
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
				
				//Double click to change the flipping of the photo
				pc.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e){
				        if(e.getClickCount()==2){
				            pc.setFlipped(!pc.isFlipped);
				            pc.repaint();
				        }
				        
				        if(pc.isFlipped()){//If this is the back side of the photo
							btnDraw.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e){
									pc.setChoice(1);
									System.out.println("Drawing");
									//pc.repaint();
								}
							});
							
							btnText.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e){
									pc.setChoice(2);
									System.out.println("Texting");
									//pc.repaint();
								}
							});
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
