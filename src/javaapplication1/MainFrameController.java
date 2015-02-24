/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package javaapplication1;

import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.prefs.Preferences;
import javaapplication1.parser.PlayerXMLFile;
import javax.swing.JFileChooser;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Vince
 */
public class MainFrameController {
    
    private MainFrame _mainFrame;
    
    public static String CURRENT_ANIM = "walk";
    public static String PICTURE_PATH;
    private static final String PREF_NAME = "last_path";
    
    private boolean ready = false;
    private SourceCanvasView _sourceCanvas;
    private AnimsValues animValues;
    private AnimationCanvasView _animCanvas;
    
    private String currentXmlFilePath;
    private boolean _spinnerValueChangedEnabled = true;
    
    public void init(MainFrame mainFrame){
        _mainFrame = mainFrame;
        initListeners();
        openFileChooser();
        _mainFrame.currentPicSpinner.setValue(0);
        
    }
    
    private void initListeners(){
        _mainFrame.periodSpinner.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                _animCanvas.changeAnimPeriod((Integer) _mainFrame.periodSpinner.getValue());
            }
        });
        
        _mainFrame.currentPicSpinner.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                _spinnerValueChangedEnabled = false;
                refreshSpinnerContent();
                _spinnerValueChangedEnabled = true;
            }
        });
        
        _mainFrame.animName.addItemListener(new ItemListener() {
            
            @Override
            public void itemStateChanged(ItemEvent e) {
                CURRENT_ANIM = (String) _mainFrame.animName.getItemAt(_mainFrame.animName.getSelectedIndex());
                _mainFrame.picNbSpinner.setValue(animValues.getAnim(CURRENT_ANIM).size());
                _mainFrame.currentPicSpinner.setValue(0);
                String OS = System.getProperty("os.name");
                if(OS.contains("Mac"))
                    PICTURE_PATH = currentXmlFilePath.substring(0, currentXmlFilePath.lastIndexOf("/")) + "/" + CURRENT_ANIM + ".png";
                else
                    PICTURE_PATH = currentXmlFilePath.substring(0, currentXmlFilePath.lastIndexOf("\\")) + "/" + CURRENT_ANIM + ".png";
                _animCanvas.init((Integer) _mainFrame.periodSpinner.getValue());
                _sourceCanvas = new SourceCanvasView(_mainFrame);
                _mainFrame.jScrollPane1.getViewport().add(_sourceCanvas);
                refreshSpinnerContent();
                _sourceCanvas.redraw();
                //bigPic.init();
                
            }
        });
        
        ChangeListener picValuesChanges = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(_spinnerValueChangedEnabled){
                    updateAnimsValueFromInterface();
                    
                    //refreshSpinnerContent();
                }
            }
        };
        
        _mainFrame.posXSpinner.addChangeListener(picValuesChanges);
        _mainFrame.posYSpinner.addChangeListener(picValuesChanges);
        _mainFrame.widthSpinner.addChangeListener(picValuesChanges);
        _mainFrame.heightSpinner.addChangeListener(picValuesChanges);
        _mainFrame.imageAnchorXSpinner.addChangeListener(picValuesChanges);
        _mainFrame.imageAnchorYSpinner.addChangeListener(picValuesChanges);
        _mainFrame.fireAnchorXSpinner.addChangeListener(picValuesChanges);
        _mainFrame.fireAnchorYSpinner.addChangeListener(picValuesChanges);
        _mainFrame.floorPosYSpinner.addChangeListener(picValuesChanges);
        
        _animCanvas.changeAnimPeriod((Integer) _mainFrame.periodSpinner.getValue());
        
    }
    
    private void openFileChooser(){
        Preferences prefs = Preferences.userRoot().node("/com/oqs/editor");
        String propertyValue = prefs.get(PREF_NAME, ""); // "a string"
        
        JFileChooser chooser = null;
        if(!propertyValue.equals(""))
            chooser = new JFileChooser(propertyValue);
        else
            chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(_mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentXmlFilePath = chooser.getSelectedFile().getAbsolutePath();
            System.out.println("XMK file path: "+currentXmlFilePath);
            String OS = System.getProperty("os.name");
            if(OS.contains("Mac"))
                PICTURE_PATH = currentXmlFilePath.substring(0, currentXmlFilePath.lastIndexOf("/")) + "/" + CURRENT_ANIM + ".png";
            else
                PICTURE_PATH = currentXmlFilePath.substring(0, currentXmlFilePath.lastIndexOf("\\")) + "/" + CURRENT_ANIM + ".png";
            
            prefs.put(PREF_NAME, currentXmlFilePath);
            openPlayerFile(currentXmlFilePath);
        }
        
        
    }
    
    private void openPlayerFile(String filepath) {
        
        animValues = new AnimsValues();
        _animCanvas = new AnimationCanvasView(_mainFrame, animValues);
        _animCanvas.init((Integer) _mainFrame.periodSpinner.getValue());
        //anim.setSize(330, 330);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        _mainFrame.animPanel.add(_animCanvas, c);
        _mainFrame.animPanel.invalidate();
        
        PlayerXMLFile xmlfile = new PlayerXMLFile(filepath);
        initAnimValues(xmlfile);
        _mainFrame.picNbSpinner.setValue(animValues.anims.get(CURRENT_ANIM).size());
        _mainFrame.currentPicSpinner.setValue(0);
        fillSPinnerFromPicNumber(0);
        _sourceCanvas = new SourceCanvasView(_mainFrame);
        _mainFrame.jScrollPane1.getViewport().add(_sourceCanvas);
        _mainFrame.jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        _mainFrame.jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        ready = true;
    }
    
    private void fillSPinnerFromPicNumber(int index) {
        AnimsValues.Picture p = getCurrentAnimPicture(index);
        _mainFrame.posXSpinner.setValue(p.posX);
        _mainFrame.posYSpinner.setValue(p.posY);
        _mainFrame.widthSpinner.setValue(p.width);
        _mainFrame.heightSpinner.setValue(p.height);
        _mainFrame.imageAnchorXSpinner.setValue(p.anchorX);
        _mainFrame.imageAnchorYSpinner.setValue(p.anchorY);
    }
    
    private void initAnimValues(PlayerXMLFile xmlfile) {
        for (String s : xmlfile.anims.keySet()) {
            animValues.addAnim(s);
            AnimsValues.Anim anima = animValues.getAnim(s);
            for (int j = 0; j < xmlfile.anims.get(s).getFrameNumber(); j++) {
                PlayerXMLFile.AnimationElement a = xmlfile.anims.get(s);
                int posX = a.getImageOrig(j).x;
                int posY = a.getImageOrig(j).y;
                int width = a.getWidth(j);
                int height = a.getHeight(j);
                int anchorX = a.getImageAnchor(j).x;
                int anchorY = a.getImageAnchor(j).y;
                anima.addPicture(j, posX, posY, width, height, anchorX, anchorY, a.getFireOrig(j).x, a.getFireOrig(j).y, a.getFloorLevel(j));
            }
            
        }
        
    }
    
    private void refreshSpinnerContent() {
        if (ready) {
            AnimsValues.Picture p = getCurrentAnimPicture();
            _mainFrame.widthSpinner.setValue(p.width);
            _mainFrame.heightSpinner.setValue(p.height);
            _mainFrame.posXSpinner.setValue(p.posX);
            System.out.println("refreshPicData p.posX:"+p.posX);
            _mainFrame.posYSpinner.setValue(p.posY);
            _mainFrame.imageAnchorXSpinner.setValue(p.anchorX);
            _mainFrame.imageAnchorYSpinner.setValue(p.anchorY);
            _mainFrame.fireAnchorXSpinner.setValue(p.fireAnchorX);
            _mainFrame.fireAnchorYSpinner.setValue(p.fireAnchorY);
            _mainFrame.floorPosYSpinner.setValue(p.floorPos);
            
            updateValueOfBigPic(p);
        }
    }
    
    private void updateValueOfBigPic(AnimsValues.Picture p){
        _sourceCanvas.updatePicInfo(p.posX, p.posY, p.width, p.height, p.anchorX, p.anchorY, p.fireAnchorX, p.fireAnchorY,p.floorPos);
        
    }
    
    public void refreshCurrentAnimPic(int nb) {
        _mainFrame.cPic.setText(String.valueOf(nb));
    }
    
    private void updateAnimsValueFromInterface() {
        AnimsValues.Picture p = getCurrentAnimPicture();
        p.width = (Integer) _mainFrame.widthSpinner.getValue();
        p.height = (Integer) _mainFrame.heightSpinner.getValue();
        p.posX = (Integer) _mainFrame.posXSpinner.getValue();
        p.posY = (Integer) _mainFrame.posYSpinner.getValue();
        p.anchorX = (Integer) _mainFrame.imageAnchorXSpinner.getValue();
        p.anchorY = (Integer) _mainFrame.imageAnchorYSpinner.getValue();
        p.fireAnchorX = (Integer) _mainFrame.fireAnchorXSpinner.getValue();
        p.fireAnchorY = (Integer) _mainFrame.fireAnchorYSpinner.getValue();
        p.floorPos = (Integer) _mainFrame.floorPosYSpinner.getValue();
        
        updateValueOfBigPic(p);
        
    }
    
    public void updateAnimsValueFromBigPic(int posX, int posY, int width, int height, int imageAnchorX, int imageAnchorY, int fireAnchorX, int fireAnchorY, int floorPos) {
        
        AnimsValues.Picture p = getCurrentAnimPicture();
        p.width = width;
        p.height = height;
        p.posX = posX;
        System.out.println("updateAnimsValueFromBigPic posX:"+posX);
        System.out.println("updateAnimsValueFromBigPic p.posX:"+p.posX);
        p.posY = posY;
        p.anchorX = imageAnchorX;
        p.anchorY = imageAnchorY;
        p.fireAnchorX = fireAnchorX;
        p.fireAnchorY = fireAnchorY;
        p.floorPos = floorPos;
        _spinnerValueChangedEnabled = false;
        refreshSpinnerContent();
        _spinnerValueChangedEnabled = true;
    }
    private AnimsValues.Picture getCurrentAnimPicture(int currentFrameIndex){
        return  animValues.getAnim((String) _mainFrame.animName.getItemAt(_mainFrame.animName.getSelectedIndex())).get((Integer) currentFrameIndex);    
    }
    
    private AnimsValues.Picture getCurrentAnimPicture(){
        int currentFrame = (Integer) _mainFrame.currentPicSpinner.getValue();
        return getCurrentAnimPicture(currentFrame);
    }

    void windowClosing() {
        _animCanvas.stop();
    }
}
