package javaapplication1.parser;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapplication1.AnimsValues;
import javaapplication1.AnimsValues.Anim;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import org.jdom.transform.JDOMSource;

public class XMLParser {

    public static Element openDoc(String fileName) {
        SAXBuilder sxb = new SAXBuilder();
        Element racine = null;
        File f = new File(fileName);
        try {
            Document document = sxb.build(f);
            racine = document.getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return racine;
    }

    public static void saveXmlFile(AnimsValues values, String fileName) {

        Element root = new Element("player");
        Element anims = new Element("animations");
        root.addContent(anims);
        for (String s : values.anims.keySet()) {
            Element anim = new Element("animation");
            anim.setAttribute("name", s);
            anim.setAttribute("period","80");
            Anim a = values.anims.get(s);
            for (int j = 0; j < a.size(); j++) {
                Element pic = new Element("image");

                pic.setAttribute("x", "" + a.get(j).posX);
                pic.setAttribute("y", "" + a.get(j).posY);
                pic.setAttribute("width", "" + a.get(j).width);
                pic.setAttribute("height", "" + a.get(j).height);
                pic.setAttribute("anchorX", "" + a.get(j).anchorX);
                pic.setAttribute("anchorY", "" + a.get(j).anchorY);
                pic.setAttribute("fireanchorX", "" + a.get(j).fireAnchorX);
                pic.setAttribute("fireanchorY", "" + a.get(j).fireAnchorY);
                pic.setAttribute("floorpos", "" + a.get(j).floorPos);

                anim.addContent(pic);
            }
            anims.addContent(anim);
        }

        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException ex) {
            //Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(
                "{http://xml.apache.org/xslt}indent-amount", "4");


        Document doc = new Document(root);
        StreamResult streamResult = new StreamResult(new StringWriter());

        JDOMSource domSource = new JDOMSource(doc);

        BufferedWriter bufferedWriter = null;
        try {
            transformer.transform(domSource, streamResult);
            String xmlString = streamResult.getWriter().toString();
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(new File(fileName))));
            bufferedWriter.write(xmlString);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (TransformerException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
