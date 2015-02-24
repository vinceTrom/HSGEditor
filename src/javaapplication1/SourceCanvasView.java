package javaapplication1;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SourceCanvasView extends JLabel {
    
    private MainFrame jframe;
    
    private static int posX = 0;
    private static int posY = 0;
    private static int width = 0;
    private static int height = 0;
    private static int imageAnchorX = 0;
    private static int imageAnchorY = 0;
    private static int fireAnchorX = 0;
    private static int fireAnchorY = 0;
    private static int floorPos = 0;
    public BufferedImage img = null;
    public static float _zoom = 1;
    
    public void updatePicInfo(int posX, int posY, int width, int height, int anchorX, int anchorY, int fireAnchorX, int fireAnchorY, int floorPos) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.imageAnchorX = anchorX;
        this.imageAnchorY = anchorY;
        this.fireAnchorX = fireAnchorX;
        this.fireAnchorY = fireAnchorY;
        this.floorPos = floorPos;
        repaint();
    }
    
    private int mouse_move_deltaX = 0;
    private int mouse_move_deltaY = 0;
    
    private final int MOUSE_RELEASED = 1;
    private final int MOUSE_PRESSED_LEFT = 2;
    private final int MOUSE_PRESSED_RIGHT = 3;
    private final int MOUSE_PRESSED_TOP = 4;
    private final int MOUSE_PRESSED_BOTTOM = 5;
    private final int MOUSE_PRESSED_MIDDLE = 6;
    
    private final int MOUSE_PRESSED_TOPLEFT = 7;
    private final int MOUSE_PRESSED_TOPRIGHT = 8;
    private final int MOUSE_PRESSED_BOTLEFT = 9;
    private final int MOUSE_PRESSED_BOTRIGHT = 10;
    
    
    private int mouse_state = MOUSE_RELEASED;
    
    
    
    public SourceCanvasView(MainFrame jframe) {
        super(new ImageIcon(MainFrame.PICTURE_PATH));
        this.jframe = jframe;
        init();
    }
    
    
    public void init() {
        try {
            img = ImageIO.read(new File(MainFrame.PICTURE_PATH));
            //paint(getGraphics());
        } catch (IOException e) {e.printStackTrace();}
        
        addMouseListener(new MouseListener() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                changeMouseState(e.getX(), e.getY());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                mouse_state = MOUSE_RELEASED;
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        
        addMouseMotionListener(new MouseMotionListener() {
            
            @Override
            public void mouseDragged(MouseEvent e) {
                switch(mouse_state){
                    case MOUSE_PRESSED_LEFT :
                        int delta = posX - (int)(e.getX()/_zoom);
                        posX = (int)(e.getX()/_zoom);
                        width = width + delta;
                        break;
                    case MOUSE_PRESSED_RIGHT :
                        width = (int)(e.getX()/_zoom-posX);
                        break;
                    case MOUSE_PRESSED_TOP :
                        int delta2 = posY - (int)(e.getY()/_zoom);
                        posY = (int)(e.getY()/_zoom);
                        height = height + delta2;
                        break;
                    case MOUSE_PRESSED_BOTTOM :
                        height = (int)(e.getY()/_zoom-posY);
                        break;
                    case MOUSE_PRESSED_MIDDLE :
                        posX = (int)(e.getX()/_zoom-mouse_move_deltaX);
                        posY = (int)(e.getY()/_zoom-mouse_move_deltaY);
                        break;
                        
                    case MOUSE_PRESSED_TOPLEFT:
                        int delta3 = posX - (int)(e.getX()/_zoom);
                        posX = (int)(e.getX()/_zoom);
                        width = width + delta3;
                        int delta4 = posY - (int)(e.getY()/_zoom);
                        posY = (int)(e.getY()/_zoom);
                        height = height + delta4;
                        break;
                    case MOUSE_PRESSED_TOPRIGHT:
                        width = (int)(e.getX()/_zoom-posX);
                        int delta5 = posY - (int)(e.getY()/_zoom);
                        posY = (int)(e.getY()/_zoom);
                        height = height + delta5;
                        break;
                    case MOUSE_PRESSED_BOTLEFT:
                        int delta6 = posX - (int)(e.getX()/_zoom);
                        posX = (int)(e.getX()/_zoom);
                        width = width + delta6;
                         height = (int)(e.getY()/_zoom-posY);
                        break;
                    case MOUSE_PRESSED_BOTRIGHT:
                         height = (int)(e.getY()/_zoom-posY);
                         width = (int)(e.getX()/_zoom-posX);
                        break;
                }
                jframe.updateAnimsValueFromBigPic(posX, posY, width, height, imageAnchorX, imageAnchorY, fireAnchorX, fireAnchorY, floorPos);
                //repaint();
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                if(!changeCursorIfNeeded(e.getX(),e.getY()))
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

  
        });
    }
    
    private void changeMouseState(int x, int y){
        if(touchLeftFrame(x, y) && touchTopFrame(x, y)){       //coté haut-gauche
            mouse_state = MOUSE_PRESSED_TOPLEFT;
            return;
        }else if(touchRightFrame(x, y) && touchTopFrame(x, y)){       //coté haut-droit
            mouse_state = MOUSE_PRESSED_TOPRIGHT;
            return;
        }else if(touchLeftFrame(x, y) && touchBottomFrame(x, y)){       //coté bas-gauche
            mouse_state = MOUSE_PRESSED_BOTLEFT;
            return;
        }else if(touchRightFrame(x, y) && touchBottomFrame(x, y)){       //coté bas-droit
            mouse_state = MOUSE_PRESSED_BOTRIGHT;
            return;
        } else if(touchLeftFrame(x, y)){       //coté gauche
            mouse_state = MOUSE_PRESSED_LEFT;
            return;
        }else if(touchRightFrame(x, y)){       //coté droit
            mouse_state = MOUSE_PRESSED_RIGHT;
            return;
        }else if(touchTopFrame(x, y)){       //coté haut
            mouse_state = MOUSE_PRESSED_TOP;
            return;
        }else if(touchBottomFrame(x, y)){       //coté bas
            mouse_state = MOUSE_PRESSED_BOTTOM;
            return;
        }
        else if(isInMiddleOfFrame(x, y)){       //coté bas
            mouse_move_deltaX = (int)(x/_zoom-posX);
            mouse_move_deltaY = (int)(y/_zoom-posY);
            mouse_state = MOUSE_PRESSED_MIDDLE;
            return;
        }
        mouse_state = MOUSE_RELEASED;
    }
    
    private boolean changeCursorIfNeeded(int x, int y){
        if(touchLeftFrame(x, y) && touchTopFrame(x, y)){       //coté haut-gauche
            setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
            return true;
        }else if(touchRightFrame(x, y) && touchTopFrame(x, y)){       //coté haut-droit
            setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
            return true;
        }else if(touchLeftFrame(x, y) && touchBottomFrame(x, y)){       //coté bas-gauche
            setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
            return true;
        }else if(touchRightFrame(x, y) && touchBottomFrame(x, y)){       //coté bas-droit
            setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
            return true;
        } else if(touchLeftFrame(x, y)){       //coté gauche
            setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
            return true;
        }else if(touchRightFrame(x, y)){       //coté droit
            setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
            return true;
        }else if(touchTopFrame(x, y)){       //coté haut
            setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
            return true;
        }else if(touchBottomFrame(x, y)){       //coté bas
            setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
            return true;
        }else if(isInMiddleOfFrame(x, y)){       //au milieu
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            return true;
        }
        return false;
    }
    
    private final int mouseDetectOffset = 5;
    private boolean touchLeftFrame(int x, int y){
        return  x > getFrameLeft()-mouseDetectOffset && x < getFrameLeft()+mouseDetectOffset && y>getFrameTop() && y<getFrameBottom();
    }
    private boolean touchRightFrame(int x, int y){
        return  x > getFrameRight()-mouseDetectOffset && x < getFrameRight()+mouseDetectOffset && y>getFrameTop() && y<getFrameBottom();
    }
    private boolean touchTopFrame(int x, int y){
        return  x > getFrameLeft() && x < getFrameRight() && y > getFrameTop()-mouseDetectOffset && y<getFrameTop()+mouseDetectOffset ;
    }
    private boolean touchBottomFrame(int x, int y){
        return  x > getFrameLeft() && x < getFrameRight() && y > getFrameBottom()-mouseDetectOffset && y<getFrameBottom()+mouseDetectOffset;
    }
    private boolean isInMiddleOfFrame(int x, int y){
        return  x > getFrameLeft() && x < getFrameRight() && y > getFrameTop() && y < getFrameBottom();
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
    
    private int getFrameTop(){
        return  (int)(posY*_zoom);
    }
    
    private int getFrameLeft(){
        return  (int)(posX*_zoom);
    }
    
    private int getFrameBottom(){
        return  (int)(int)((posY+height)*_zoom);
    }
    
    private int getFrameRight(){
        return  (int)((posX+width)*_zoom);
    }

    
    
    

}
