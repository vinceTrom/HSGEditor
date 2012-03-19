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

    @Override
    public void paint(Graphics g) {
       // super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, 0, 0, null);
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2d.setColor(Color.black);
        g2d.drawImage(img, 0, 0, null);
        g2d.setColor(Color.RED);
        
        g2d.drawLine(posX, posY, posX + width, posY);
        g2d.drawLine(posX, posY + height, posX + width, posY + height);
        g2d.drawLine(posX, posY, posX, posY + height);
        g2d.drawLine(posX + width, posY, posX + width, posY + height);
        
        g2d.setColor(Color.BLUE);
        g2d.drawLine(posX +anchorX-5,posY + anchorY,posX +anchorX+5,posY + anchorY);
         g2d.drawLine(posX +anchorX,posY + anchorY-5,posX +anchorX,posY + anchorY+5);
    
    }

 

    
}
