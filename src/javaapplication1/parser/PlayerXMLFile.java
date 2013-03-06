package javaapplication1.parser;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import org.jdom.Element;

public class PlayerXMLFile {

	Element root;
	public HashMap<String, AnimationElement> anims = new HashMap<String, AnimationElement>();
	public PlayerXMLFile(String playerXMLFile){
		root = XMLParser.openDoc(playerXMLFile);
                List<Element> elements = root.getChild("animations").getChildren("animation");
                for(int i=0;i<elements.size();i++){
                    String name = elements.get(i).getAttributeValue("name");
                  anims.put(name,new AnimationElement(name));
                }
	}

        
        public int getAnimsNumber(){
		return anims.size();
	}


	public class AnimationElement{
		private Element anim;
		
		public AnimationElement(String animName){
                    for(int i=0;i< root.getChildren().size();i++)
                   System.out.println("aa"+ root.getChildren().get(i));
			List<Element> ls = root.getChild("animations").getChildren();
			for(int i=0;i<ls.size();i++){
				if(ls.get(i).getAttribute("name").getValue().equals(animName))
					anim = ls.get(i);
			}
		}

		public int getFrameNumber(){
			return anim.getChildren("image").size();
		}
		
		public String getFileName(){
			return anim.getAttribute("src").getValue();
		}
		
		public int getWidth(int pic){
			return Integer.parseInt(((Element)anim.getChildren("image").get(pic)).getAttribute("width").getValue());
		}
		
		public int getHeight(int pic){
			return Integer.parseInt(((Element)anim.getChildren("image").get(pic)).getAttribute("height").getValue());
		}
		
		public Point getImageOrig(int pic){
			Point p = new Point();
			p.x = Integer.parseInt(((Element)anim.getChildren("image").get(pic)).getAttribute("x").getValue());
			p.y = Integer.parseInt(((Element)anim.getChildren("image").get(pic)).getAttribute("y").getValue());
			return p;
		}
		
		public Point getImageAnchor(int pic){
			Point p = new Point();
			p.x = Integer.parseInt(((Element)anim.getChildren("image").get(pic)).getAttribute("anchorX").getValue());
			p.y = Integer.parseInt(((Element)anim.getChildren("image").get(pic)).getAttribute("anchorY").getValue());
			return p;
		}
                
                public Point getFireOrig(int pic){
                    Point p = new Point(0,0);
                    try{
			p.x = Integer.parseInt(((Element)anim.getChildren("image").get(pic)).getAttribute("fireanchorX").getValue());
			p.y = Integer.parseInt(((Element)anim.getChildren("image").get(pic)).getAttribute("fireanchorY").getValue());
                    }catch(Exception e){}
                        return p;
                }
                
                public int getFloorLevel(int pic){
                int pos =0;
                    try{
			pos = Integer.parseInt(((Element)anim.getChildren("image").get(pic)).getAttribute("floorpos").getValue());
                    }catch(Exception e){}
                        return pos;
                }
                
	}
}
