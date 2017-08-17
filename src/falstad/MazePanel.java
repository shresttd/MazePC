package falstad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;
import java.awt.Panel;
import java.util.Iterator;

import falstad.Constants.StateGUI;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 * 
 * @author pk
 *
 */
public class MazePanel extends Panel  {
	/* Panel operates a double buffer see
	 * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
	 * for details
	 */
	private Image bufferImage ;
	private Graphics2D gc ; 
	/**
	 * Constructor. Object is not focusable.
	 */
	public MazePanel() {
		super() ;
		this.setFocusable(false) ;
	}
	
	@Override
	public void update(Graphics g) {
		paint(g) ;
	}
	public void update() {
		paint(getGraphics()) ;
	}
	
	/**
	 * Draws the buffer image to the given graphics object.
	 * This method is called when this panel should redraw itself.
	 */
	@Override
	public void paint(Graphics g) {
		if (null == g) {
			System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation") ;
		}
		else {
			g.drawImage(bufferImage,0,0,null) ;	
		}
	}

	public void initBufferImage() {
		bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		if (null == bufferImage)
		{
			System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
		}
	}
	/**
	 * Obtains a graphics object that can be used for drawing. 
	 * Multiple calls to the method will return the same graphics object 
	 * such that drawing operations can be performed in a piecemeal manner 
	 * and accumulate. To make the drawing visible on screen, one
	 * needs to trigger a call of the paint method, which happens 
	 * when calling the update method. 
	 * @return graphics object to draw on
	 */
	public Graphics getBufferGraphics() {
		if (null == bufferImage)
			initBufferImage() ;
		if (null == bufferImage)
			return null ;
		return bufferImage.getGraphics() ;
	}

//////////////////////////////////////For MazeController\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Called in MazeController in notifyViewerRedraw()
	 * Redraws the graphics object
	 * @param it
	 * @param state
	 * @param px
	 * @param py
	 * @param viewdx
	 * @param viewdy
	 * @param walkStep
	 * @param rset
	 * @param angle
	 */
	protected void whileViewerRedraw (Iterator<Viewer> it, StateGUI state, int px, int py, 
			int viewdx, int viewdy, int walkStep, RangeSet rset, int angle) {
		Viewer v = it.next() ;
		Graphics g = getBufferGraphics() ;
		if (null == g) {
			System.out.println("Maze.notifierViewerRedraw: can't get graphics object to draw on, skipping redraw operation") ;
		}
		else {
		 v.redraw(this, state, px, py, viewdx, viewdy, walkStep, Constants.VIEW_OFFSET, rset, angle) ;
		}	
	}

/////////////////////////////////////////For FirstPersonDrawer\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Called in FirstPersonDrawer to set RenderingHints 
	 */
	public void setRenderingHints () {
		gc = (Graphics2D) getBufferGraphics() ;
		gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gc.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	}
	
	/**
	 * Called in FirstPersonDrawer to draw the backgrounds
	 * @param view_width
	 * @param view_height
	 */
	protected void drawBackground (int view_width, int view_height) {
		gc.setColor(Color.black);
		gc.fillRect(0, 0, view_width, view_height/2);
		gc.setColor(Color.darkGray);
		gc.fillRect(0, view_height/2, view_width, view_height/2);		
	}
	
	/**
	 * Called in FirstPersonDrawer to set the background to white
	 */
	protected void setColorToWhite() {
		gc.setColor(Color.white);
	}
	
	/**
	 * Called in FirstPersonDrawer to fill polygons
	 * @param xps
	 * @param yps
	 */
	protected void fillPolygon(int[] xps, int[] yps)  {
		gc.fillPolygon(xps, yps, 4);
	}
	
	///////////////////////////For Seg\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Called in Seg to set color of the seg
	 * @param col
	 */
	public void setColorSeg(int[] col) {
		gc.setColor(new Color(col[0], col[1], col[2])); 
	}
	
	///////////////////////////For MazeFileReader\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Called in MazeFileReader to set color of the seg to a RGB value
	 * @param col
	 */
	public void setColorRGBSeg(int col) {
		gc.setColor(new Color(col));
	}
	
	//////////////////////////For Map Drawer\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Called in MapDrawer to draw a line
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drawLine(int x1, int y1, int x2, int y2) {
		gc.drawLine(x1, y1, x2, y2);
	}
	/**
	 * Called in MapDrawer to fill the oval
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void fillOval(int x, int y, int width, int height) {
		gc.fillOval(x, y, width, height);
	}
	
	/**
	 * Called in MapDrawer to set a particular color to the graphics.
	 * @param color
	 */
	public void setColorGraphics(String color) {
		switch (color) {
		case "White" :
			gc.setColor(Color.WHITE);

			break;
		case "Black" :
			gc.setColor(Color.BLACK);

			break;
		case "Gray" : 
			gc.setColor(Color.GRAY);

			break;
		case "DarkGray" :
			gc.setColor(Color.DARK_GRAY);
			break;
		case "Yellow" :
			gc.setColor(Color.YELLOW);
			break;
		case "Red" : 
			gc.setColor(Color.RED);
			break;
		case "Green" :
			gc.setColor(Color.GREEN);
			break;
		case "Magenta" :
			gc.setColor(Color.MAGENTA);
			break;
		case "Blue" :
			gc.setColor(Color.BLUE);
			break;
		case "Pink" :
			gc.setColor(Color.PINK);
			break;
		}
	}
}
