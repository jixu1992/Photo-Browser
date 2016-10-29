import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * @author jinxu
 *
 */
public abstract class Node{
	
	Node parent;//reference to parent
	ArrayList<Node> listChildren;//list of children
	boolean visible;//whether this node will be displayed or not
	Rectangle2D bound;
	Color strokeColor;
	Color fillColor;
	AffineTransform affinetransform;
	double x,y,width,height;//bounds, relative position to parent
		
	public abstract void setBounds();
	public abstract Rectangle2D getBounds(); //bounds getter
	protected abstract void paint(Graphics g); //paint method
		
	
	public Node(){
		listChildren = new ArrayList<Node>();
		visible = true;
		strokeColor = Color.BLACK;
		fillColor = new Color(Color.TRANSLUCENT);
		x=0;
		y=0;
	}	
	

	public void setParent(Node parent) {
		this.parent = parent;
	}


	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public AffineTransform getAffinetransform() {
		return affinetransform;
	}

	public void setAffinetransform(AffineTransform affinetransform) {
		this.affinetransform = affinetransform;
	}
		
	public Node getParent(){
		return this.parent;
	}
	
	//list of children
	public ArrayList<Node> getChild(){
		return this.listChildren;
	}
	
	//adding children
	public void addChild(Node child){
		child.setParent(this);
		listChildren.add(child);		
	}
	
	//removing children
	public void removeChild(Node child){
		child.setParent(null);
		listChildren.remove(child);		
	}

	//paint all the children nodes
	protected void paintChildren(Graphics g){
		if(visible){
			for(int i=0;i<listChildren.size();i++){
				listChildren.get(i).paint(g);
			}
		}
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

}
