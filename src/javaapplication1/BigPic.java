package javaapplication1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
    public static int imageAnchorX = 0;
    public static int imageAnchorY = 0;
    public static int fireAnchorX = 0;
    public static int fireAnchorY = 0;
    public static int floorPos = 0;
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
        setPreferredSize(new Dimension((int)(img.getWidth()*_zoom), (int)(img.getHeight()*_zoom)));
        revalidate();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, (int)(img.getWidth()*_zoom), (int)(img.getHeight()*_zoom));
        g2d.setColor(Color.black);
        
        //Dessine l'image
          g2d.drawImage(img, 0, 0, (int)(img.getWidth()*_zoom), (int)(img.getHeight()*_zoom), null);

          //Dessine le cadre de l'image
        g2d.setColor(Color.RED);
        g2d.drawLine((int)(posX*_zoom), (int)(posY*_zoom), (int)((posX + width)*_zoom), (int)(posY*_zoom));
        g2d.drawLine((int)(posX*_zoom), (int)((posY + height)*_zoom), (int)((posX + width)*_zoom), (int)((posY + height)*_zoom));
        g2d.drawLine((int)(posX*_zoom), (int)(posY*_zoom), (int)(posX*_zoom), (int)((posY + height)*_zoom));
        g2d.drawLine((int)((posX + width)*_zoom), (int)(posY*_zoom), (int)((posX + width)*_zoom), (int)((posY + height)*_zoom));
        
        //Dessine l'ancre de l'image
        g2d.setColor(Color.BLUE);
        g2d.drawLine((int)((posX +imageAnchorX-5)*_zoom),(int)((posY + imageAnchorY)*_zoom),(int)((posX +imageAnchorX+5)*_zoom),(int)((posY + imageAnchorY)*_zoom));
         g2d.drawLine((int)((posX +imageAnchorX)*_zoom),(int)((posY + imageAnchorY-5)*_zoom),(int)((posX +imageAnchorX)*_zoom),(int)((posY + imageAnchorY+5)*_zoom));
    
         //Dessine l'ancre de l'arme
        g2d.setColor(Color.GREEN);
        g2d.drawLine((int)((posX +fireAnchorX-5)*_zoom),(int)((posY + height - fireAnchorY)*_zoom),(int)((posX +fireAnchorX+5)*_zoom),(int)((posY + height - fireAnchorY)*_zoom));
         g2d.drawLine((int)((posX +fireAnchorX)*_zoom),(int)((posY + fireAnchorY-5)*_zoom),(int)((posX +fireAnchorX)*_zoom),(int)((posY + fireAnchorY+5)*_zoom));
    
         //Dessine la position du sol
         g2d.setColor(Color.GRAY);
         g2d.drawLine((int)(posX*_zoom),(int)((posY + height - floorPos)*_zoom),(int)((posX +width)*_zoom),(int)((posY + height - floorPos)*_zoom));

    }

 

    
}
