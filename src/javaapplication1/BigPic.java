package javaapplication1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.TileObserver;
import java.awt.image.WritableRenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BigPic extends JLabel {

    public static int posX = 0;
    public static int posY = 0;
    public static int width = 0;
    public static int height = 0;
    public static int anchorX = 0;
    public static int anchorY = 0;
    public BufferedImage img = null;
    public static float _zoom = 1;

    public BigPic() {
        super(new ImageIcon(NewJFrame1.PICTURE_PATH));
        init();
       
    }

    public void init() {
        try {
            img = ImageIO.read(new File(NewJFrame1.PICTURE_PATH));
            //paint(getGraphics());
        } catch (IOException e) {e.printStackTrace();}      
    }
    
    public void redraw(){
        paint(getGraphics());
    }

    @Override
    public void paint(Graphics g) {
       // super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
       // g2d.drawImage(img, 0, 0, null);
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, (int)(img.getWidth()*_zoom), (int)(img.getHeight()*_zoom));
        g2d.setColor(Color.black);
        //g2d.drawImage(img, 0, 0, null
          g2d.drawImage(img, 0, 0, (int)(img.getWidth()*_zoom), (int)(img.getHeight()*_zoom), null);

        g2d.setColor(Color.RED);
        
        g2d.drawLine((int)(posX*_zoom), (int)(posY*_zoom), (int)((posX + width)*_zoom), (int)(posY*_zoom));
        g2d.drawLine((int)(posX*_zoom), (int)((posY + height)*_zoom), (int)((posX + width)*_zoom), (int)((posY + height)*_zoom));
        g2d.drawLine((int)(posX*_zoom), (int)(posY*_zoom), (int)(posX*_zoom), (int)((posY + height)*_zoom));
        g2d.drawLine((int)((posX + width)*_zoom), (int)(posY*_zoom), (int)((posX + width)*_zoom), (int)((posY + height)*_zoom));
        
        g2d.setColor(Color.BLUE);
        g2d.drawLine((int)((posX +anchorX-5)*_zoom),(int)((posY + anchorY)*_zoom),(int)((posX +anchorX+5)*_zoom),(int)((posY + anchorY)*_zoom));
         g2d.drawLine((int)((posX +anchorX)*_zoom),(int)((posY + anchorY-5)*_zoom),(int)((posX +anchorX)*_zoom),(int)((posY + anchorY+5)*_zoom));
    
    }

 

    
}
