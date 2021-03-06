package client;
	import java.awt.*;
	import java.io.IOException;
	import javax.swing.*;
	import javax.swing.plaf.metal.MetalScrollBarUI;
	import javax.imageio.ImageIO;
	import java.io.File;
	import java.awt.geom.AffineTransform;


	public class CustomScrollBar {
	
	/*custom scroll bar code adapted from URL: 
	  http://stackoverflow.com/questions/12265740/make-a-custom-jscrollbar-using-an-image
	  increase and decrease button code adapted from URL: 
	  http://stackoverflow.com/questions/19577893/custom-scrollbar-arrows	
	*/	
  static class MyScrollbarUI extends MetalScrollBarUI {
    private Image imageThumb = null;
    private Image imageTrack = null;

    MyScrollbarUI() {
    	UIManager.put("Button.background", Color.black);
    	
    	try {
             imageThumb = ImageIO.read(new File("img/ThumbColor.png"));
            imageTrack = ImageIO.read(new File("img/bgColor.png"));
       } catch (IOException e){
    	   System.out.println("Exception Loading the scroll bar images" + e.getMessage());
       }
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {        
        g.translate(thumbBounds.x, thumbBounds.y);
        g.setColor( Color.red );
        g.drawRect( 0, 0, thumbBounds.width - 2, thumbBounds.height - 1 );
        AffineTransform transform = AffineTransform.getScaleInstance((double)thumbBounds.width/imageThumb.getWidth(null),(double)thumbBounds.height/imageThumb.getHeight(null));
        ((Graphics2D)g).drawImage(imageThumb, transform, null);
        g.translate( -thumbBounds.x, -thumbBounds.y );
    }

   @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {        
        g.translate(trackBounds.x, trackBounds.y);
        ((Graphics2D)g).drawImage(imageTrack,AffineTransform.getScaleInstance(1,(double)trackBounds.height/imageTrack.getHeight(null)),null);
        g.translate( -trackBounds.x, -trackBounds.y );
    }


    
    
/*    @Override
    protected JButton createDecreaseButton(int orientation) {
    	ImageIcon decreaseIcon = new ImageIcon(new ImageIcon("img/UpArrow.png").getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
    	JButton decreaseButton = new JButton(decreaseIcon){
        	private static final long serialVersionUID = -4649936833531540925L;

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(22, 22);
            }
        };
        return decreaseButton;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
    	ImageIcon increaseIcon = new ImageIcon(new ImageIcon("img/downArrow.png").getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
        JButton increaseButton = new JButton(increaseIcon){
        	private static final long serialVersionUID = -464993683531540925L;

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(22, 22);
            }
        };
        return increaseButton;
    }
*/
  }
}


