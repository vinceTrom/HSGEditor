/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.prefs.Preferences;
import javaapplication1.AnimsValues.Picture;
import javaapplication1.parser.PlayerXMLFile;
import javaapplication1.parser.PlayerXMLFile.AnimationElement;
import javaapplication1.parser.XMLParser;
import javax.swing.ComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Vince
 */
public class NewJFrame1 extends javax.swing.JFrame {

    private boolean ready = false;
    BigPic bigPic;
    AnimsValues animValues;
    private ANim anim;
    public static String CURRENT_ANIM = "walk";
    private boolean updateBigPic = true;
    //public static String PICTURE_PATH = "C:/Users/Vince/Documents/AnimEditor/walkshaded.png";
    public static String PICTURE_PATH = "C:/Users/Vince/arm.png";
    String PREF_NAME = "last_path";
    private String currentXmlFilePath;

    /**
     * Creates new form NewJFrame1
     */
    public NewJFrame1() {
        initComponents();
        Preferences prefs = Preferences.userRoot().node("/com/oqs/editor");
        String propertyValue = prefs.get(PREF_NAME, ""); // "a string"

        JFileChooser chooser = null;
        if(!propertyValue.equals(""))
            chooser = new JFileChooser(propertyValue);
        else
            chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
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
            //This is where a real application would open the file.
        }


        animName.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                CURRENT_ANIM = (String) animName.getItemAt(animName.getSelectedIndex());
                picNbSpinner.setValue(animValues.getAnim(CURRENT_ANIM).size());
                currentPicSpinner.setValue(0);
                String OS = System.getProperty("os.name");
                if(OS.contains("Mac"))
                    PICTURE_PATH = currentXmlFilePath.substring(0, currentXmlFilePath.lastIndexOf("/")) + "/" + CURRENT_ANIM + ".png";
                else
                    PICTURE_PATH = currentXmlFilePath.substring(0, currentXmlFilePath.lastIndexOf("\\")) + "/" + CURRENT_ANIM + ".png";
                anim.init((Integer) periodSpinner.getValue());
                bigPic = new BigPic();
                jScrollPane1.getViewport().add(bigPic);
                //bigPic.init();

            }
        });

        periodSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                anim.changeAnimPeriod((Integer) periodSpinner.getValue());
            }
        });
        currentPicSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                updateBigPic = false;
                refreshPicData();
            }
        });
        ChangeListener picValuesChanges = new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                updateAnimsValue();
                refreshPicData();

            }
        };

        posXSpinner.addChangeListener(picValuesChanges);
        posYSpinner.addChangeListener(picValuesChanges);
        widthSpinner.addChangeListener(picValuesChanges);
        heightSpinner.addChangeListener(picValuesChanges);
        imageAnchorXSpinner.addChangeListener(picValuesChanges);
        imageAnchorYSpinner.addChangeListener(picValuesChanges);
        fireAnchorXSpinner.addChangeListener(picValuesChanges);
        fireAnchorYSpinner.addChangeListener(picValuesChanges);
        floorPosYSpinner.addChangeListener(picValuesChanges);

        anim.changeAnimPeriod((Integer) periodSpinner.getValue());

    }
    /**
     * Catch l'evenement de fermeture de la fenetre
     * @param e 
     */
    public void windowClosing(WindowEvent e){
        anim.stop();
    }

    private void openPlayerFile(String filepath) {

        animValues = new AnimsValues();
        anim = new ANim(this, animValues);
        anim.init((Integer) periodSpinner.getValue());
        //anim.setSize(330, 330);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        animPanel.add(anim, c);
        animPanel.invalidate();
        

        //jScrollPane1.setSize(3800, 250);

        PlayerXMLFile xmlfile = new PlayerXMLFile(filepath);
        initAnimValues(xmlfile);
        picNbSpinner.setValue(animValues.anims.get(CURRENT_ANIM).size());
        currentPicSpinner.setValue(0);
        fillSPinnerFromPicNumber(0);
        bigPic = new BigPic();
        jScrollPane1.getViewport().add(bigPic);
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        ready = true;
    }

    public void refreshCurrentAnimPic(int nb) {
        cPic.setText("" + nb);
    }

    private void fillSPinnerFromPicNumber(int index) {
        Picture p = animValues.getAnim((String) animName.getItemAt(animName.getSelectedIndex())).get(index);
        posXSpinner.setValue(p.posX);
        posYSpinner.setValue(p.posY);
        widthSpinner.setValue(p.width);
        heightSpinner.setValue(p.height);
        imageAnchorXSpinner.setValue(p.anchorX);
        imageAnchorYSpinner.setValue(p.anchorY);
    }

    private void initAnimValues(PlayerXMLFile xmlfile) {
        for (String s : xmlfile.anims.keySet()) {
            animValues.addAnim(s);
            AnimsValues.Anim anima = animValues.getAnim(s);
            for (int j = 0; j < xmlfile.anims.get(s).getFrameNumber(); j++) {
                AnimationElement a = xmlfile.anims.get(s);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        animMoveYp = new javax.swing.JButton();
        animMoveYm = new javax.swing.JButton();
        animMoveXp = new javax.swing.JButton();
        animMoveXm = new javax.swing.JButton();
        AnimZoomP = new javax.swing.JButton();
        AnimZoomM = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        updateAnimsValue = new javax.swing.JButton();
        SaveButton = new javax.swing.JButton();
        periodSpinner = new javax.swing.JSpinner();
        picNbSpinner = new javax.swing.JSpinner();
        animName = new javax.swing.JComboBox();
        currentPicSpinner = new javax.swing.JSpinner();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        characterType = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        posXSpinner = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        posYSpinner = new javax.swing.JSpinner();
        heightSpinner = new javax.swing.JSpinner();
        widthSpinner = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        imageAnchorXSpinner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        imageAnchorYSpinner = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        floorPosYSpinner = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        fireAnchorXSpinner = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        fireAnchorYSpinner = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        animPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        bigpicZoomP = new javax.swing.JButton();
        bigpicZoomM = new javax.swing.JButton();
        cPic = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        animMoveYp.setText("+");
        animMoveYp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animMoveYpActionPerformed(evt);
            }
        });

        animMoveYm.setText("-");
        animMoveYm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animMoveYmActionPerformed(evt);
            }
        });

        animMoveXp.setText(">");
        animMoveXp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animMoveXpActionPerformed(evt);
            }
        });

        animMoveXm.setText("<");
        animMoveXm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animMoveXmActionPerformed(evt);
            }
        });

        AnimZoomP.setText("Z+");
        AnimZoomP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnimZoomPActionPerformed(evt);
            }
        });

        AnimZoomM.setText("Z-");
        AnimZoomM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnimZoomMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(animMoveXm)
                        .addGap(18, 18, 18)
                        .addComponent(animMoveXp)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(AnimZoomP)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(animMoveYp)
                                .addGap(32, 32, 32))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(animMoveYm)
                                .addGap(33, 33, 33)))
                        .addComponent(AnimZoomM))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AnimZoomP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AnimZoomM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(animMoveYp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(animMoveXp)
                    .addComponent(animMoveXm, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(animMoveYm))
        );

        jLabel2.setText("Anim");

        jButton1.setText("refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setText("picnb");

        jLabel11.setText("current pic");

        updateAnimsValue.setText("updateAnimValues");
        updateAnimsValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateAnimsValueActionPerformed(evt);
            }
        });

        SaveButton.setText("Save");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        periodSpinner.setValue(100);

        picNbSpinner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changed(evt);
            }
        });

        animName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "walk", "leftarm", "jump", "fall", "armfire", "backfire", "explo", "explo2" }));
        animName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animNameActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        characterType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "player", "enemy" }));
        characterType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                characterTypeActionPerformed(evt);
            }
        });

        jLabel3.setText("Image");

        jLabel5.setText("Y:");

        jLabel4.setText("X:");

        jLabel7.setText("height:");

        jLabel6.setText("width:");

        jLabel1.setText("Anchor");

        jLabel8.setText("X:");

        jLabel9.setText("Y:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(58, 58, 58))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(imageAnchorXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(imageAnchorYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imageAnchorXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imageAnchorYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(posYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel7))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(posXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(posXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(posYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jLabel14.setText("Y:");

        jLabel12.setText("Floor Pos.");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(floorPosYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(floorPosYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel14)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jLabel15.setText("Y:");

        jLabel16.setText("X:");

        jLabel17.setText("Fire Orig.");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(58, 58, 58))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(fireAnchorXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(fireAnchorYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(55, 55, 55))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addGap(4, 4, 4)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fireAnchorXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fireAnchorYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(picNbSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(currentPicSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(animName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2)))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(characterType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(37, 37, 37)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(periodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1)
                        .addComponent(updateAnimsValue)
                        .addComponent(SaveButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(92, 92, 92))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(360, 360, 360)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(283, 283, 283)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(SaveButton)
                        .addGap(34, 34, 34)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(updateAnimsValue)
                                .addGap(77, 77, 77)
                                .addComponent(jButton1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(periodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(44, 44, 44))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(characterType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(animName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(picNbSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 77, Short.MAX_VALUE)))
                        .addComponent(currentPicSpinner)))
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(77, 77, 77)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(231, Short.MAX_VALUE)))
        );

        animPanel.setBackground(new java.awt.Color(255, 51, 51));
        animPanel.setLayout(new java.awt.GridBagLayout());

        bigpicZoomP.setText("Z+");
        bigpicZoomP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bigpicZoomPActionPerformed(evt);
            }
        });

        bigpicZoomM.setText("Z-");
        bigpicZoomM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bigpicZoomMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bigpicZoomP)
                    .addComponent(bigpicZoomM))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(bigpicZoomP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bigpicZoomM)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        cPic.setText("jLabel12");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cPic)
                                .addGap(15, 15, 15)))
                        .addComponent(animPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(cPic)
                        .addGap(30, 30, 30)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(animPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changed
        refreshPicData();
    }//GEN-LAST:event_changed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        refreshPicData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void updateAnimsValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateAnimsValueActionPerformed
        updateAnimsValue();
    }//GEN-LAST:event_updateAnimsValueActionPerformed

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed
        JFileChooser chooser = new JFileChooser();

        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            XMLParser.saveXmlFile(animValues, path);
        }
    }//GEN-LAST:event_SaveButtonActionPerformed

    private void animMoveYpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animMoveYpActionPerformed
         anim.offsetanimY = anim.offsetanimY-10;
    }//GEN-LAST:event_animMoveYpActionPerformed

    private void animMoveYmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animMoveYmActionPerformed
         anim.offsetanimY = anim.offsetanimY+10;
    }//GEN-LAST:event_animMoveYmActionPerformed

    private void animMoveXmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animMoveXmActionPerformed
         anim.offsetanimX = anim.offsetanimX-10;
    }//GEN-LAST:event_animMoveXmActionPerformed

    private void animMoveXpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animMoveXpActionPerformed
       anim.offsetanimX = anim.offsetanimX+10;
    }//GEN-LAST:event_animMoveXpActionPerformed

    private void AnimZoomMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnimZoomMActionPerformed
        ANim._currentZoom = ANim._currentZoom - 0.2f;
        anim.offsetanimX = (int)(anim.offsetanimX+animValues.getAnim((String) animName.getItemAt(animName.getSelectedIndex())).get( 0).width*0.4f);
    }//GEN-LAST:event_AnimZoomMActionPerformed

    private void AnimZoomPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnimZoomPActionPerformed
       ANim._currentZoom = ANim._currentZoom + 0.2f;
       anim.offsetanimX = (int)(anim.offsetanimX-animValues.getAnim((String) animName.getItemAt(animName.getSelectedIndex())).get( 0).width*0.4f);
    }//GEN-LAST:event_AnimZoomPActionPerformed

    private void bigpicZoomPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bigpicZoomPActionPerformed
        BigPic._zoom = BigPic._zoom + 0.2f;
        bigPic.redraw();
    }//GEN-LAST:event_bigpicZoomPActionPerformed

    private void bigpicZoomMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bigpicZoomMActionPerformed
        BigPic._zoom = BigPic._zoom - 0.2f;
        bigPic.redraw();
    }//GEN-LAST:event_bigpicZoomMActionPerformed

    private void animNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_animNameActionPerformed

    private void characterTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_characterTypeActionPerformed
        if(characterType.getSelectedIndex()==0)
        animName.setModel(new javax.swing.DefaultComboBoxModel(new String[] {  "walk", "leftarm", "jump", "fall", "armfire", "backfire", "explo", "explo2" }));
else
        animName.setModel(new javax.swing.DefaultComboBoxModel(new String[] {  "walk", "die1", "die2", "die3" }));

        
    }//GEN-LAST:event_characterTypeActionPerformed
    private void updateAnimsValue() {
        if (updateBigPic) {
            int currentFrame = (Integer) currentPicSpinner.getValue();

            Picture p = animValues.getAnim((String) animName.getItemAt(animName.getSelectedIndex())).get((Integer) currentFrame);

            p.width = (Integer) widthSpinner.getValue();
            p.height = (Integer) heightSpinner.getValue();
            p.posX = (Integer) posXSpinner.getValue();
            p.posY = (Integer) posYSpinner.getValue();
            p.anchorX = (Integer) imageAnchorXSpinner.getValue();
            p.anchorY = (Integer) imageAnchorYSpinner.getValue();
            p.fireAnchorX = (Integer) fireAnchorXSpinner.getValue();
            p.fireAnchorY = (Integer) fireAnchorYSpinner.getValue();
            p.floorPos = (Integer) floorPosYSpinner.getValue();;
        }
    }

    private void refreshPicData() {
        System.out.println("update");
        if (ready) {
            System.out.println("update");
            int currentFrame = (Integer) currentPicSpinner.getValue();
            System.out.println("cuurent: " + currentFrame);
            Picture p = animValues.getAnim((String) animName.getItemAt(animName.getSelectedIndex())).get((Integer) currentFrame);
            widthSpinner.setValue(p.width);
            heightSpinner.setValue(p.height);
            posXSpinner.setValue(p.posX);
            posYSpinner.setValue(p.posY);
            imageAnchorXSpinner.setValue(p.anchorX);
            imageAnchorYSpinner.setValue(p.anchorY);
            fireAnchorXSpinner.setValue(p.fireAnchorX);
            fireAnchorYSpinner.setValue(p.fireAnchorY);
            floorPosYSpinner.setValue(p.floorPos);
            bigPic.width = p.width;
            bigPic.height = p.height;
            bigPic.posX = p.posX;
            bigPic.posY = p.posY;
            bigPic.imageAnchorX = p.anchorX;
            bigPic.imageAnchorY = p.anchorY;
            bigPic.fireAnchorX = p.fireAnchorX;
            bigPic.fireAnchorY = p.fireAnchorY;
            bigPic.floorPos = p.floorPos;
            bigPic.repaint();
            updateBigPic = true;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new NewJFrame1().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AnimZoomM;
    private javax.swing.JButton AnimZoomP;
    private javax.swing.JButton SaveButton;
    private javax.swing.JButton animMoveXm;
    private javax.swing.JButton animMoveXp;
    private javax.swing.JButton animMoveYm;
    private javax.swing.JButton animMoveYp;
    private javax.swing.JComboBox animName;
    private javax.swing.JPanel animPanel;
    private javax.swing.JButton bigpicZoomM;
    private javax.swing.JButton bigpicZoomP;
    private javax.swing.JLabel cPic;
    private javax.swing.JComboBox characterType;
    private javax.swing.JSpinner currentPicSpinner;
    private javax.swing.JSpinner fireAnchorXSpinner;
    private javax.swing.JSpinner fireAnchorYSpinner;
    private javax.swing.JSpinner floorPosYSpinner;
    private javax.swing.JSpinner heightSpinner;
    private javax.swing.JSpinner imageAnchorXSpinner;
    private javax.swing.JSpinner imageAnchorYSpinner;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSpinner periodSpinner;
    private javax.swing.JSpinner picNbSpinner;
    private javax.swing.JSpinner posXSpinner;
    private javax.swing.JSpinner posYSpinner;
    private javax.swing.JButton updateAnimsValue;
    private javax.swing.JSpinner widthSpinner;
    // End of variables declaration//GEN-END:variables
}
