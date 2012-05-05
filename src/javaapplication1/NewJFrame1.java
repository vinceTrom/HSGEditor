/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javaapplication1.AnimsValues.Picture;
import javaapplication1.parser.PlayerXMLFile;
import javaapplication1.parser.PlayerXMLFile.AnimationElement;
import javaapplication1.parser.XMLParser;
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
    public static String PICTURE_PATH = "C:/Users/Vince/Documents/AnimEditor/arm.png";
    private String currentXmlFilePath;

    /**
     * Creates new form NewJFrame1
     */
    public NewJFrame1() {
        initComponents();
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentXmlFilePath = chooser.getSelectedFile().getAbsolutePath();
            PICTURE_PATH = currentXmlFilePath.substring(0, currentXmlFilePath.lastIndexOf("\\")) + "/" + CURRENT_ANIM + ".png";

            openPlayerFile(currentXmlFilePath);
            //This is where a real application would open the file.
        }


        animName.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                CURRENT_ANIM = (String) animName.getItemAt(animName.getSelectedIndex());
                picNbSpinner.setValue(animValues.getAnim(CURRENT_ANIM).size());
                currentPicSpinner.setValue(0);
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
        anchorXSpinner.addChangeListener(picValuesChanges);
        anchorYSpinner.addChangeListener(picValuesChanges);

        anim.changeAnimPeriod((Integer) periodSpinner.getValue());

    }

    private void openPlayerFile(String filepath) {

        animValues = new AnimsValues();
        anim = new ANim(this, animValues);
        anim.init((Integer) periodSpinner.getValue());
        anim.setSize(330, 330);
        animPanel.add(anim);
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
        anchorXSpinner.setValue(p.anchorX);
        anchorYSpinner.setValue(p.anchorY);
    }

    private void initAnimValues(PlayerXMLFile xmlfile) {
        for (String s : xmlfile.anims.keySet()) {
            animValues.addAnim(s);
            AnimsValues.Anim anima = animValues.getAnim(s);
            for (int j = 0; j < xmlfile.anims.get(s).getFrameNumber(); j++) {
                AnimationElement a = xmlfile.anims.get(s);
                int posX = a.getOrig(j).x;
                int posY = a.getOrig(j).y;
                int width = a.getWidth(j);
                int height = a.getHeight(j);
                int anchorX = a.getAnchor(j).x;
                int anchorY = a.getAnchor(j).y;
                anima.addPicture(j, posX, posY, width, height, anchorX, anchorY);
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

        animName = new javax.swing.JComboBox();
        picNbSpinner = new javax.swing.JSpinner();
        currentPicSpinner = new javax.swing.JSpinner();
        posXSpinner = new javax.swing.JSpinner();
        posYSpinner = new javax.swing.JSpinner();
        widthSpinner = new javax.swing.JSpinner();
        heightSpinner = new javax.swing.JSpinner();
        anchorXSpinner = new javax.swing.JSpinner();
        anchorYSpinner = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        animPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        updateAnimsValue = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        SaveButton = new javax.swing.JButton();
        periodSpinner = new javax.swing.JSpinner();
        cPic = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        animMoveYp = new javax.swing.JButton();
        animMoveYm = new javax.swing.JButton();
        animMoveXp = new javax.swing.JButton();
        animMoveXm = new javax.swing.JButton();
        AnimZoomP = new javax.swing.JButton();
        AnimZoomM = new javax.swing.JButton();
        bigpicZoomP = new javax.swing.JButton();
        bigpicZoomM = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        animName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "walk", "leftarm", "jump", "fall", "armfire", "backfire", "explo", "explo2" }));

        picNbSpinner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changed(evt);
            }
        });

        jButton1.setText("refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout animPanelLayout = new javax.swing.GroupLayout(animPanel);
        animPanel.setLayout(animPanelLayout);
        animPanelLayout.setHorizontalGroup(
            animPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );
        animPanelLayout.setVerticalGroup(
            animPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
        );

        jLabel1.setText("Anchor");

        jLabel2.setText("Anim");

        jLabel3.setText("image");

        jLabel4.setText("X:");

        jLabel5.setText("Y:");

        jLabel6.setText("width:");

        jLabel7.setText("height:");

        jLabel8.setText("X:");

        jLabel9.setText("Y:");

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

        cPic.setText("jLabel12");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(animMoveXm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(animMoveXp)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(animMoveYp)
                                .addGap(32, 32, 32))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(animMoveYm)
                                .addGap(33, 33, 33))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(animMoveYp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(animMoveXp)
                    .addComponent(animMoveXm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(animMoveYm))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(currentPicSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(picNbSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(45, 45, 45)
                                        .addComponent(jLabel4))
                                    .addComponent(jLabel11))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(posYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(periodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(animName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(36, 36, 36)
                            .addComponent(posXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(165, 165, 165)
                            .addComponent(jLabel8)
                            .addGap(3, 3, 3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(anchorYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(63, 63, 63))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(anchorXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(49, 49, 49)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton1)
                                .addComponent(updateAnimsValue)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cPic)
                                    .addComponent(SaveButton))))))
                .addGap(71, 71, 71)
                .addComponent(animPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AnimZoomM)
                            .addComponent(AnimZoomP))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bigpicZoomP)
                    .addComponent(bigpicZoomM))
                .addGap(0, 36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(bigpicZoomP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bigpicZoomM)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cPic)
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(57, 57, 57)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(animName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel4)
                                                    .addComponent(posXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel5)
                                                    .addComponent(posYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(5, 5, 5)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel10)
                                            .addComponent(periodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel7))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(picNbSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(jLabel11)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(currentPicSpinner)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel1)
                                            .addComponent(SaveButton))
                                        .addGap(34, 34, 34)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(updateAnimsValue)
                                            .addComponent(anchorXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8))
                                        .addGap(77, 77, 77)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel9)
                                            .addComponent(anchorYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton1))
                                        .addGap(21, 21, 21))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(AnimZoomP)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AnimZoomM)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(animPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(807, 807, 807))))
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
    }//GEN-LAST:event_AnimZoomMActionPerformed

    private void AnimZoomPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnimZoomPActionPerformed
       ANim._currentZoom = ANim._currentZoom + 0.2f;
    }//GEN-LAST:event_AnimZoomPActionPerformed

    private void bigpicZoomPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bigpicZoomPActionPerformed
        BigPic._zoom = BigPic._zoom + 0.2f;
        bigPic.redraw();
    }//GEN-LAST:event_bigpicZoomPActionPerformed

    private void bigpicZoomMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bigpicZoomMActionPerformed
        BigPic._zoom = BigPic._zoom - 0.2f;
        bigPic.redraw();
    }//GEN-LAST:event_bigpicZoomMActionPerformed
    private void updateAnimsValue() {
        if (updateBigPic) {
            int currentFrame = (Integer) currentPicSpinner.getValue();

            Picture p = animValues.getAnim((String) animName.getItemAt(animName.getSelectedIndex())).get((Integer) currentFrame);

            p.width = (Integer) widthSpinner.getValue();
            p.height = (Integer) heightSpinner.getValue();
            p.posX = (Integer) posXSpinner.getValue();
            p.posY = (Integer) posYSpinner.getValue();
            p.anchorX = (Integer) anchorXSpinner.getValue();
            p.anchorY = (Integer) anchorYSpinner.getValue();
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
            anchorXSpinner.setValue(p.anchorX);
            anchorYSpinner.setValue(p.anchorY);
            bigPic.width = p.width;
            bigPic.height = p.height;
            bigPic.posX = p.posX;
            bigPic.posY = p.posY;
            bigPic.anchorX = p.anchorX;
            bigPic.anchorY = p.anchorY;
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
    private javax.swing.JSpinner anchorXSpinner;
    private javax.swing.JSpinner anchorYSpinner;
    private javax.swing.JButton animMoveXm;
    private javax.swing.JButton animMoveXp;
    private javax.swing.JButton animMoveYm;
    private javax.swing.JButton animMoveYp;
    private javax.swing.JComboBox animName;
    private javax.swing.JPanel animPanel;
    private javax.swing.JButton bigpicZoomM;
    private javax.swing.JButton bigpicZoomP;
    private javax.swing.JLabel cPic;
    private javax.swing.JSpinner currentPicSpinner;
    private javax.swing.JSpinner heightSpinner;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner periodSpinner;
    private javax.swing.JSpinner picNbSpinner;
    private javax.swing.JSpinner posXSpinner;
    private javax.swing.JSpinner posYSpinner;
    private javax.swing.JButton updateAnimsValue;
    private javax.swing.JSpinner widthSpinner;
    // End of variables declaration//GEN-END:variables
}
