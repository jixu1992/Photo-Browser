import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * @author jinxu
 * Display arbitrary shapes
 */

public class ShapeNode extends Node{
	
	Shape shape;
	
	public ShapeNode(){
		super();
	}
	
	public ShapeNode(Shape shape) {
		super();
		this.shape = shape;
	}
	
	@Override
	public Rectangle2D getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle2D.Double(getX(),getY(),getWidth(),getHeight());
	}

	@Override
	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
		if(visible){
			Graphics2D g2d = (Graphics2D)g;
			
			//set the stroke color of the shape
			g2d.setColor(getStrokeColor());
			g2d.draw(shape);
			
			//set the filling color of the shape
			g2d.setColor(getFillColor());
			g2d.fill(shape);
			
			//draw its children nodes 
			if(this.getChild()!=null){
				for(Node nn: this.getChild()){
					nn.paint(g2d);
				}
			}
		}
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	//set the bounds information of the shape
	@Override
	public void setBounds() {
		// TODO Auto-generated method stub
		setX(this.shape.getBounds().getX());
		setY(this.shape.getBounds().getY());
		setWidth(this.shape.getBounds().getWidth());
		setHeight(this.shape.getBounds().getHeight());
	}
}
