import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * @author jinxu
 * Display text
 */

public class TextNode extends Node{

	ArrayList<String> textList;
	
	private boolean displayCursor;
	Point positionCursor;
	
	int lengthText;
	int heightText;
	
	boolean isTyping;
	
	public TextNode(Point position) {
		super();
		textList = new ArrayList<>();
        displayCursor = false;
        positionCursor = position;
        x=position.x;
        y=position.y;
       
	}
	
	//add new chars with keyboard typing
	public void createNewChar(char c){
        if(textList.isEmpty()){
            textList.add(c+"");
        }
        else{
            textList.set(textList.size()-1, textList.get(textList.size()-1)+c);
        }
	}
	
	//set the position of the insertion point
	public void setCursor(Point position){
		positionCursor = position;
		setTyping(true);
	}
	
	public boolean isTyping() {
		return isTyping;
	}

	public void setTyping(boolean isTyping) {
		this.isTyping = isTyping;
	}
	
	int numLines;
	int heightLine;
	
	@Override
	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setColor(Color.black);
        int textHeight = g2d.getFontMetrics().getHeight();
        System.out.println("Textheight"+textHeight);
       
        g2d.setFont(new Font("New Times Roman",Font.PLAIN, 18));
        String line = "";
        int nLines = 0;
        for(String ele:textList){
            //set wrapping of texts
        	if(g2d.getFontMetrics().stringWidth(line+ele)+x>parent.getBounds().getWidth()){
                if(!line.isEmpty()){
                    g2d.drawString(line,(int)x,(int)y+nLines*textHeight);
                    nLines++;
                }
                while(g2d.getFontMetrics().stringWidth(ele)+x>parent.getBounds().getWidth()){
                    for(int i=1; i<=ele.length();i++){
                    	lengthText = g2d.getFontMetrics().stringWidth(ele.substring(0,i));
                        if(x+g2d.getFontMetrics().stringWidth(ele.substring(0,i))>parent.getBounds().getWidth()){
                            line = ele.substring(0,i-1);
                            ele = ele.substring(i-1);
                            break;
                        }
                    }
                    g2d.drawString(line,(int)x,(int)y+nLines*textHeight);
                    nLines++;
                }
                line=ele+' ';
            }
           else{
                line+=ele+' ';
                lengthText = g2d.getFontMetrics().stringWidth(line);
            }
        }
        numLines = nLines;
        heightText = heightLine = textHeight;
        if(!line.isEmpty()){
            g2d.drawString(line,(int)x,(int)y+nLines*textHeight);
        }
        //draw the cursor
        if(isTyping){
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine((int)x, (int)y, (int)x, (int)y-textHeight);
        }
    }
	
	public int getHeightText() {
		return heightText;
	}

	public void setHeightText(int heightText) {
		this.heightText = heightText;
	}

	public int getLengthText() {
		return lengthText;
	}

	public void setLengthText(int lengthText) {
		this.lengthText = lengthText;
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle((int)x,(int)y,lengthText,numLines*heightLine);
		// TODO Auto-generated method stub	
		
	}
	
	@Override
	public void setBounds() {
		// TODO Auto-generated method stub
		
	}

}
