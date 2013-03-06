/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.HashMap;

/**
 *
 * @author Vince
 */
public class AnimsValues {
    public  HashMap<String,Anim> anims = new HashMap<String, Anim>();

    public  void addAnim(String animName){
        anims.put(animName,new Anim());
    }
    
    public   Anim getAnim(String animName){
        return anims.get(animName);
    }
    public class Anim extends HashMap<Integer,Picture>{
    public void addPicture(int index, int posX, int posY, int width, int height, int imageAnchorX, int imageAnchorY,int fireAnchorX, int fireAnchorY,int floorPos){
        Picture p = new Picture();
        p.posX = posX;
        p.posY = posY;
        p.width = width;
        p.height = height;
        p.anchorX = imageAnchorX;
        p.anchorY = imageAnchorY;
        p.fireAnchorX = fireAnchorX;
        p.fireAnchorY = fireAnchorY;
        p.floorPos = floorPos;
        put(index, p);
    }
    };
    
            public class Picture{
                public  int posX=0;
                public  int posY=0;
                public  int width=0;
                public  int height=0;
                public  int anchorX=0;
                public  int anchorY=0;
                public int fireAnchorX=0;
                public int fireAnchorY=0;
                public int floorPos=0;
            }
}
