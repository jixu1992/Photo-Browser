import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author jinxu
 * Image node
 */
public class ImageNode extends Node{
	
	BufferedImage img;
	
	ImageNode(){
		
	}
	
	public ImageNode(BufferedImage img) {
		super();
		this.img = img;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	@Override
	public Rectangle2D getBounds() {
		// TODO Auto-generated method stub
		int imgW = img.getWidth();
		int imgH = img.getHeight();
		Rectangle2D rect= new Rectangle2D.Double(getX(),getY(),imgW,imgH);
		return rect;
	}

	@Override
	protected void paint(Graphics g) {
		// TODO Auto-generated method stub		
		g.drawImage(img, (int)getX(),(int)getY(),null);
	}

	@Override
	public void setBounds() {
		// TODO Auto-generated method stub
		
	}

}
