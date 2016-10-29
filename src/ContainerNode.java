import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author jinxu
 * Container node, without visual rendering
 */

public class ContainerNode extends Node{
	
	boolean childAdded;

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Double(getX(),getY(),getWidth(),getHeight());
	}

	@Override
	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
		if(this.visible){
			super.paintChildren(g);
		}
	}
	
	private void changeBound(Node childNode){
		if(childAdded){
			bound = childNode.bound.createUnion(bound);
		}
		else{
			if(!listChildren.isEmpty()){
				if(listChildren.size()==1){
					bound = listChildren.get(0).bound;
				}
				else{
					for(int i=0;i<listChildren.size();i++){
						bound = listChildren.get(i).bound.createUnion(bound);
					}
				}
			}
		}
	}
	
	@Override
	public void addChild(Node node){
		listChildren.add(node);
		childAdded = true;
		changeBound(node);		
	}
	
	@Override
	public void removeChild(Node node){
		listChildren.remove(node);
		childAdded = false;
		changeBound(node);
	}

	@Override
	public void setBounds() {
		// TODO Auto-generated method stub		
	}


}
