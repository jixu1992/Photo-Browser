import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.*;

public class PhotoBrowser extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JLabel statusBar = new JLabel("Hello");
	JMenuBar menuBar = new JMenuBar();
	
	JMenu menu1 = new JMenu("File");
	JMenu menu2 = new JMenu("View");
	
	JMenuItem importItem = new JMenuItem("Import");
	JMenuItem deleteItem = new JMenuItem("Delete");
	JMenuItem quitItem = new JMenuItem("Quit");
	
	JRadioButton photoViewerButton = new JRadioButton("Photo viewer", true);
	JRadioButton browserButton = new JRadioButton("Browser", false);
	JRadioButton splitModeButton = new JRadioButton("Split mode", false);
	
	JToolBar tBar = new JToolBar();
	
	JToggleButton familyButton = new JToggleButton("Family",true);
	JToggleButton vacationButton = new JToggleButton("Vacation",false);
	JToggleButton schoolButton = new JToggleButton("School",false);
	
	private Box bx = Box.createVerticalBox();

	public static void main(String[] args){
		PhotoBrowser myphototheque = new PhotoBrowser();
		myphototheque.setVisible(true);		
	}
	
	public PhotoBrowser(){
		super("My photo browser");
		
		setJMenuBar(menuBar);
		
		menuBar.add(menu1);
		menuBar.add(menu2);
				
		importItem.addActionListener(
				event ->
					chooseFile()
				);
		
		deleteItem.addActionListener(
				event ->
					deletePhoto()
				);
		
		quitItem.addActionListener(
				event ->
					closeWindow()
				);
		
		menu1.add(importItem);
		menu1.add(deleteItem);
		menu1.add(quitItem);
		
		photoViewerButton.addActionListener(
				event ->
					photoViewerMode()
				);
		
		browserButton.addActionListener(
				event ->
					browserMode()
				);
		
		splitModeButton.addActionListener(
				event ->
					splitMode()
				);
		
		menu2.add(photoViewerButton);
		menu2.add(browserButton);
		menu2.add(splitModeButton);
		
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(photoViewerButton);
		bGroup.add(browserButton);
		bGroup.add(splitModeButton);	
		
		JPanel tbPanel = new JPanel();
		
		add(tBar,BorderLayout.NORTH);
		tBar.add(bx);
		tbPanel.add(tBar);
		add(tbPanel,BorderLayout.WEST);
		
		familyButton.addActionListener(
				event ->
					familyMode()
				);
		
		vacationButton.addActionListener(
				event ->
					vacationMode()
				);
		
		schoolButton.addActionListener(
				event ->
					schoolMode()
				);
		
		bx.add(familyButton);
		bx.add(vacationButton);
		bx.add(schoolButton);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(familyButton);
		bg.add(vacationButton);
		bg.add(schoolButton);
		
		JPanel myPanel = new JPanel();
		myPanel.setBackground(Color.DARK_GRAY);
		add(myPanel, BorderLayout.CENTER);
		
		JPanel stPanel = new JPanel();
		stPanel.add(statusBar);
		add(stPanel, BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(600, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
	}
	
	public void chooseFile(){
		JFileChooser filechooser = new JFileChooser();
		filechooser.showOpenDialog(null);
		File f = filechooser.getSelectedFile();
		if(f!=null){
		}		
	}
	
	public void closeWindow(){
		this.setVisible(false);
		dispose();
	}
	
	public void deletePhoto(){
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
