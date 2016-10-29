import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

/**
 * @author jinxu
 * Display arbitrary paths
 */

public class PathNode extends Node{
	
	GeneralPath path;
	private boolean begin;
	private boolean empty;
	

	public PathNode() {
		super();
		path = new GeneralPath();
		begin = true;
	}
	
	public boolean isBegin() {
		return begin;
	}

	public void setBegin(boolean begin) {
		this.begin = begin;
	}
	
	@Override
	public Rectangle2D getBounds() {
		// TODO Auto-generated method stub
		return path.getBounds2D();
	}
	
	public void drawSegment(Double x, Double y){
		if(begin){
			path.moveTo(x, y);
		}
		else{
			path.lineTo(x, y);
		}
		setBegin(false);
		//path.lineTo(x1, y1);//draw a small path
	}

	@Override
	protected void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setPaint(getStrokeColor());
		g2d.draw(path);
	}

	@Override
	public void setBounds() {
		// TODO Auto-generated method stub
		
	}
	

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	

}
