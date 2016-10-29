import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;


/**
 * @author jinxu
 * This is a test class to render some examples of nodes.
 */
public class TestSceneGraph extends JComponent{
	
	private static final long serialVersionUID = 1L;
	private RootNode rn;
	
	public TestSceneGraph(){
		
	}
	
	public TestSceneGraph(RootNode rn) {
		super();
		this.rn = rn;
	}

	public RootNode getRn() {
		return rn;
	}

	public void setRn(RootNode rn) {
		this.rn = rn;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		if(rn!=null){
			rn.paint(g2d);
		}
	}

	public static void main(String[] args){
		JFrame testFrame = new JFrame("Scene Graph");
		TestSceneGraph tsg = new TestSceneGraph();
		testFrame.add(tsg);
		
		//create a new root node
		RootNode r = new RootNode();
		
		//create a rectangle node
		ShapeNode rect = new ShapeNode(new Rectangle2D.Double(0,0,50,50));
		r.addChild(rect);
		
		//create a circle node
		ShapeNode circle = new ShapeNode(new Ellipse2D.Double(500, 500, 100, 100));
		circle.setFillColor(Color.YELLOW);
		circle.setStrokeColor(Color.BLACK);
		r.addChild(circle);
		circle.addChild(rect);
		
		//create an image node
		ImageNode imgNode = null;
		try {
			imgNode = new ImageNode(ImageIO.read(new File("appicns_Chrome.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.addChild(imgNode);
		
		PathNode pthNode = new PathNode();
		pthNode.setStrokeColor(Color.DARK_GRAY);
		pthNode.drawSegment(100.0,30.0);
		pthNode.drawSegment(100.0,40.0);
		r.addChild(pthNode);
		
		//set the root node
		tsg.setRn(r);
		
		testFrame.setVisible(true);
		testFrame.setPreferredSize(new Dimension(1000, 800));
		testFrame.setSize(new Dimension(1000, 800));
	}	
}
