package client;
	import java.awt.*;
	import java.io.FileReader;
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
    /*private Image imageThumb, imageTrack;
    MyScrollbarUI() {
        try {
            imageThumb = ImageIO.read(new File("src/Assignment3Resources/img/scrollbar/red_button05.png"));
            imageTrack = ImageIO.read(new File("src/Assignment3Resources/img/scrollbar/red_button03.png"));
        } catch (IOException e){}
    }*/

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {        
        g.translate(thumbBounds.x, thumbBounds.y);
        g.setColor( Color.red );
        g.drawRect( 0, 0, thumbBounds.width - 2, thumbBounds.height - 1 );
/*        AffineTransform transform = AffineTransform.getScaleInstance((double)thumbBounds.width/imageThumb.getWidth(null),(double)thumbBounds.height/imageThumb.getHeight(null));
        ((Graphics2D)g).drawImage(imageThumb, transform, null);
        g.translate( -thumbBounds.x, -thumbBounds.y );*/
    }

 /*   @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {        
        g.translate(trackBounds.x, trackBounds.y);
        ((Graphics2D)g).drawImage(imageTrack,AffineTransform.getScaleInstance(1,(double)trackBounds.height/imageTrack.getHeight(null)),null);
        g.translate( -trackBounds.x, -trackBounds.y );
    }
*/
    
    
    
/*    @Override
    protected JButton createDecreaseButton(int orientation) {
    	ImageIcon decreaseIcon = new ImageIcon(new ImageIcon("src/Assignment3Resources/img/scrollbar/red_sliderUp.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    	JButton decreaseButton = new JButton(decreaseIcon){
        	private static final long serialVersionUID = -4649936833531540925L;

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(22, 22);
            }
        };
        return decreaseButton;
    }
*/
/*    @Override
    protected JButton createIncreaseButton(int orientation) {
    	ImageIcon increaseIcon = new ImageIcon(new ImageIcon("src/Assignment3Resources/img/scrollbar/red_sliderDown.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
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


