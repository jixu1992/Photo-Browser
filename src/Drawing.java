
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.Serializable;

public class Drawing implements Serializable{
	int x1, x2, y1, y2;
	int R, G, B;
	float stroke;
	String s1;
	void draw(Graphics2D g2d ){		
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
	    g2d.drawLine(x1,y1,x2,y2);
	}
}

class DrawPencil extends Drawing{
	void draw(Graphics2D g2d){
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(stroke,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
	    g2d.drawLine(x1,y1,x2,y2);
	}
}

class TextPen extends Drawing{
	void draw(Graphics2D g2d){
		g2d.setPaint(new Color(R,G,B));
		if(s1!=null){
			g2d.drawString(s1,x1,y1);
		}
		
		
	}
}
