import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * @author jinxu
 * Root node, which displays nothing but initiates the rendering of the tree
 */
public class RootNode extends Node{
	
	
	public RootNode(){
		super();
		setParent(null);
	}
	

	@Override
	public Rectangle2D getBounds() {
		// TODO Auto-generated method stub
		return null; //null bounds, infinite
	}

	@Override
	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
		if(this.visible){
			super.paintChildren(g);
		}		
	}

	@Override
	public void setBounds() {
		// TODO Auto-generated method stub
		
	}
	
}
