import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * @author jinxu
 * The component of the photo/canvas
 */
public class PhotoComponent extends JComponent implements MouseListener, MouseMotionListener, KeyListener{
	
	private BufferedImage image;
	
	private ImageNode imageNode;
	private RootNode canvasRoot;
	private ShapeNode backGround;//white background of the canvas
	private PathNode pathDrawn;
	private ShapeNode shapeDrawn;
	private TextNode textTyped;
	private ArrayList<TextNode> linesTyped = new ArrayList<TextNode>();
	
	private Point position = new Point();
	
	boolean isFlipped = false;
	boolean isDrawing = false;
	boolean typing = false;
	boolean newImage = false;
	
	int shapeChoice = 0;
	Point currentPoint = new Point();
	
	int borderWest;
    int borderEast;
    int borderNorth;
    int borderSouth;

	public PhotoComponent(){
		
		this.setSize(new Dimension(800,600));
		this.setPreferredSize(getPreferredSize());  
		
		setVisible(true);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		addKeyListener(this);
		
	}
	
	
	public int getShapeChoice() {
		return shapeChoice;
	}


	public void setShapeChoice(int shapeChoice) {
		this.shapeChoice = shapeChoice;
	}

	
	
	public boolean isFlipped() {
		return isFlipped;
	}

	public void setFlipped(boolean isFlipped) {
		this.isFlipped = isFlipped;
	}
	
	@Override
	protected void paintComponent(Graphics g){

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		Point imagePosition = new Point(0,0);
		
		borderWest = 0;
		borderEast = image.getWidth();
		borderNorth = 0;
		borderSouth = image.getHeight();
        
		//Paint the image node
		if(!isFlipped){
			imageNode = new ImageNode(image);
			imageNode.setX(imagePosition.x);
			imageNode.setY(imagePosition.y);
			imageNode.setHeight(image.getHeight());
			imageNode.setWidth(image.getWidth());
			
			if(canvasRoot == null){
				canvasRoot = new RootNode();
			}
			if(!canvasRoot.listChildren.isEmpty()){
				canvasRoot.removeChild(canvasRoot.listChildren.get(0));
			}			
			canvasRoot.addChild(imageNode);
			
			canvasRoot.paint(g2d);
			repaint();
		}
		//The back side of the photo: paint the white background and its children nodes
		else{
			if(backGround == null||newImage){
				backGround = new ShapeNode();
			}
			newImage = false;
			
			backGround.setShape(new Rectangle(image.getWidth(),image.getHeight()));
			backGround.setX(imagePosition.x);
			backGround.setY(imagePosition.y);
			backGround.setFillColor(Color.WHITE);
			
			canvasRoot.paint(g2d);		
		}
	}
	
	public void loadShapeNode(Point pt){
		int x = currentPoint.x-position.x;
        int y = currentPoint.y-position.y;
              
        int w = pt.x - currentPoint.x;
        int h = pt.y - currentPoint.y;
        
        int x2 = pt.x - position.x;
        int y2 = pt.y - position.y;
            
        if(w<0){
        	w=-w;
        }
        if(h<0){
        	h=-h;
        }

        int xx = Math.min(x,x2);
        int yy = Math.min(y,y2);

        switch (shapeChoice){
            case 0:
            	pathDrawn.drawSegment((double)pt.x-position.x, (double)pt.y-position.y);                
                break;
            case 1:
            	shapeDrawn.setShape(new Ellipse2D.Double(xx,yy,w,h));
                break;
            case 2:
            	shapeDrawn.setShape(new Rectangle2D.Double(xx,yy,w,h));               
                break;
            case 3:
            	shapeDrawn.setShape(new Line2D.Double(x,y,x2,y2));
                break;
            default:
                break;
        }
	}
		
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void importPhoto(String imgPath){
		try {
			image = ImageIO.read(new File(imgPath));
			setSize(new Dimension(image.getWidth(),image.getHeight()));
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//See if the point is in the canvas
	private boolean isClipping(Point pt){
		if(pt.x>borderWest && pt.x<borderEast && pt.y>borderNorth && pt.y<borderSouth){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(typing){
			textTyped.createNewChar(e.getKeyChar());
			System.out.println("text length"+textTyped.getLengthText());
		    repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e){
		if(textTyped!=null){
			textTyped.setTyping(false);
			textTyped = null;
		}
		Point pt = e.getPoint();

		if(isFlipped&&isClipping(pt)){
			if(!isDrawing){
				currentPoint = e.getPoint();
				if(shapeChoice == 0){
					pathDrawn = new PathNode();
					pathDrawn.setFillColor(Color.BLACK);
					backGround.addChild(pathDrawn);
				}
				else{
					shapeDrawn = new ShapeNode();
					shapeDrawn.setFillColor(Color.GREEN);
					shapeDrawn.setStrokeColor(Color.PINK);
					backGround.addChild(shapeDrawn);
				}
				isDrawing=true;
			}
			loadShapeNode(pt);
		}
		repaint();
	}

	

	public void mouseClicked(MouseEvent e) {
		// Double click to change the side
		if(e.getClickCount()==2){
			System.out.println("double click");
			setFlipped(!isFlipped);
            System.out.println(isFlipped);
            
            if(isFlipped){
            	if(backGround == null||newImage){
	            	backGround = new ShapeNode(new Rectangle.Double(0.0, 0.0, image.getWidth(), image.getHeight()));
	            	backGround.setFillColor(Color.WHITE);
	            }
	            newImage = false;
	            canvasRoot.addChild(backGround);
	            backGround.setVisible(true);	           
	            repaint();
            }
            else{
            	backGround.setVisible(false);
            }
        }	
		//single click to insert cursor and then texts
		else if(e.getClickCount()==1){
			System.out.println("single click");
			if(isClipping(e.getPoint())){
				if (isFlipped&&isClipping(e.getPoint())) {
	                if (textTyped != null && typing) {
	                    textTyped.setTyping(false);
	                    textTyped = null;
	                }
	                typing = true;
	                Point pt = new Point(e.getX(),e.getY());
	                textTyped = new TextNode(pt);
	                textTyped.setTyping(true);	                
	                backGround.setBounds();
	                backGround.addChild(textTyped);
	                requestFocusInWindow();
	            }
				
			}
		}
		
	}

	public boolean isNewImage() {
		return newImage;
	}


	public void setNewImage(boolean newImage) {
		this.newImage = newImage;
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		isDrawing = false;
        if(shapeDrawn!= null && shapeDrawn.getShape()==null){
            backGround.removeChild(shapeDrawn);
        }
        if(pathDrawn!= null && pathDrawn.isEmpty()){
            backGround.removeChild(pathDrawn);
        }
        repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public RootNode getCanvasRoot() {
		return canvasRoot;
	}

	public void setCanvasRoot(RootNode canvasRoot) {
		this.canvasRoot = canvasRoot;
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
		
}


