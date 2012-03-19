package javaapplication1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javaapplication1.AnimsValues.Picture;

import javax.imageio.ImageIO;

public class ANim extends Component {

    private AnimsValues animsValues;
    private NewJFrame1 frame;
    private int currentpic = 0;
    BufferedImage img = null;

    public ANim(NewJFrame1 frame, AnimsValues animsValues) {
        super();
        this.animsValues = animsValues;
        this.frame = frame;
    }
    Timer th;
    TimerTask task;

    public void init(int per) {
        if (th != null) 
            th.cancel();
        if(task!=null)
            task.cancel();
        
        th = new Timer();
        try {
            System.out.println("path:" + NewJFrame1.PICTURE_PATH);
            img = ImageIO.read(new File(NewJFrame1.PICTURE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        task = createTask();
        th.schedule(task, 200, per);

    }

    private TimerTask createTask() {
        return new TimerTask() {

            @Override
            public void run() {
                Picture p = animsValues.getAnim(NewJFrame1.CURRENT_ANIM).get((Integer) currentpic);
                offsetX = p.posX;
                offsetY = p.posY;
                width = p.width;
                height = p.height;
                anchorX = p.anchorX - 120;
                anchorY = p.anchorY;
                paint(getGraphics());
                frame.refreshCurrentAnimPic(currentpic);
                currentpic = (currentpic + 1) % animsValues.getAnim(NewJFrame1.CURRENT_ANIM).size();

            }
        };
    }

    public void changeAnimPeriod(int per) {
        th.cancel();
        th.purge();
        task.cancel();
        this.period = per;
        th = new Timer();
        task = createTask();
        th.schedule(task, 100, period);
    }
    public int period = 100;
    private int width = 0;
    private int height = 0;
    private int offsetX = 0;
    private int offsetY = 0;
    private int anchorX = 0;
    private int anchorY = 0;

    
    private int offsetanimY = 200;
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(Color.black);

        g2d.drawImage(img, -anchorX, offsetanimY+-anchorY, width - anchorX, offsetanimY+height - anchorY, offsetX, offsetY, offsetX + width, offsetY + height, null);
    }
}
