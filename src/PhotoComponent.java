import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class PhotoComponent extends JComponent implements MouseListener, KeyListener{
	private BufferedImage image;
	static int width, height;
	boolean isFlipped = false;
	Dimension initialSize;
	Dimension preferredSize;
	private int choice = 1;//default choice is drawing
	String keystr="";
	char key;
	boolean typingActivated = false;
	int keyX, keyY;
	
	
	Drawing[] itemList = new Drawing[8000];
	int R = 0;
	int G = 0;
	int B = 0;
	float stroke = 2.0f;
	int index = 0;
	int indexTemp = 0;
	
	public boolean isFlipped() {
		return isFlipped;
	}

	public void setFlipped(boolean isFlipped) {
		this.isFlipped = isFlipped;
	}
	

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	
	
	protected void paintComponent(Graphics g){
		width = this.getWidth();
		height = this.getHeight();
		Rectangle2D rect = new Rectangle2D.Float();
		rect.setFrame((this.getWidth()-image.getWidth())/2, (this.getHeight()-image.getHeight())/2, image.getWidth(), image.getHeight());
		super.paintComponent(g);
		
		if(!isFlipped){
			g.drawImage(image, (this.getWidth()-image.getWidth())/2, (this.getHeight()-image.getHeight())/2, null);
			//System.out.println("Photo imported!");
		}
		else{
			g.setColor(Color.WHITE);
			((Graphics2D) g).fill(new Rectangle.Double((this.getWidth()-image.getWidth())/2, (this.getHeight()-image.getHeight())/2, image.getWidth(), image.getHeight()));
			//System.out.println("Photo flipped")
			Graphics2D g2d = (Graphics2D)g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                   RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                   RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setClip(rect);
			
			int  i = 0;
			while(i<=index) {
				draw(g2d,itemList[i]);
				i++;
				repaint();
			}
			
		
			
		}
	}
	


	void draw(Graphics2D g2d, Drawing j){
		j.draw(g2d, this, image);
	}
	

	public PhotoComponent(String imgpath){
		try {
			image = ImageIO.read(new File(imgpath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setSize(image.getWidth(),image.getHeight());
		this.setPreferredSize(getPreferredSize());  

		setVisible(true);
		
		addMouseListener(this);
		addMouseMotionListener(new MouseB());
		addKeyListener(this);
		
		
		createNewItem();
		
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	void createNewItem(){
		if(choice == 1){
			itemList[index] = new DrawPencil();
		}
		if(choice == 2){
			itemList[index] = new TextPen();
		}
		
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
		itemList[index].stroke = stroke ;
	}
	
	void createNewItem(String str, int x, int y){
		itemList[index] = new TextPen();
		itemList[index].x1 = x;
		itemList[index].y1 = y;
		itemList[index].s1 = keystr;
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
		itemList[index].stroke = stroke ;
	}
	
	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}
	


	class MouseB extends MouseMotionAdapter{
		public void mouseDragged(MouseEvent me){
			if(choice == 1){
				itemList[index-1].x1 = itemList[index].x2 = itemList[index].x1 = me.getX()-(width-image.getWidth())/2;
	  		    itemList[index-1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY()-(height-image.getHeight())/2;
	  		    index++;
	  		    createNewItem();
			}
			else{
				itemList[index].x2 = me.getX()-(width-image.getWidth())/2;
	    		itemList[index].y2 = me.getY()-(height-image.getHeight())/2;
			}
			repaint();
			
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		display(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		display(e);	
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		display(e);
	}
	
	protected void display(KeyEvent e){
		
		int eventID = e.getID();
		
		if (eventID == KeyEvent.KEY_PRESSED){
			
				key = e.getKeyChar();				
				keystr+=key;
				createNewItem(keystr, keyX, keyY);
				repaint();			
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		itemList[index].x1 = itemList[index].x2 = e.getX()-(width-image.getWidth())/2;
		itemList[index].y1 = itemList[index].y2 = e.getY()-(height-image.getHeight())/2;
		
		if(choice == 1){
			itemList[index].x1 = itemList[index].x2 = e.getX()-(width-image.getWidth())/2;
			itemList[index].y1 = itemList[index].y2 = e.getY()-(height-image.getHeight())/2;
			index++;
			createNewItem();
			repaint();
		}
		if(choice == 2){
			
			keystr = "";
			keyX = itemList[index].x1 = e.getX()-(width-image.getWidth())/2;
			keyY = itemList[index].y1 = e.getY()-(height-image.getHeight())/2;
			
			this.setFocusable(true);
			requestFocus();
			
			//itemList[index].s1 = key+"";
		
			//itemList[index].s1 = keystr;
			
			//keystr="";
			
			index++;
			createNewItem(keystr, keyX, keyY);
			//index++;
			//choice = 2;
			repaint();
			//index++;
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(choice == 1){
			itemList[index].x1 = e.getX()-(width-image.getWidth())/2;
			itemList[index].y1 = e.getY()-(height-image.getHeight())/2;
			itemList[index].x2 = e.getX()-(width-image.getWidth())/2;
			itemList[index].y2 = e.getY()-(height-image.getHeight())/2;
			repaint();
			index++;
			createNewItem();
		}
		if(choice == 2){
			//itemList[index].x2 = e.getX()-(width-image.getWidth())/2;
			//itemList[index].y2 = e.getY()-(height-image.getHeight())/2;
			repaint();
			index++;
			createNewItem(keystr, keyX, keyY);
		}
		
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


