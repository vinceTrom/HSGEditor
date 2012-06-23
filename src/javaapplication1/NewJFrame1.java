/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.prefs.Preferences;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        animMoveYp = new javax.swing.JButton();
        animMoveYm = new javax.swing.JButton();
        animMoveXp = new javax.swing.JButton();
        animMoveXm = new javax.swing.JButton();
        AnimZoomP = new javax.swing.JButton();
        AnimZoomM = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        updateAnimsValue = new javax.swing.JButton();
        SaveButton = new javax.swing.JButton();
        periodSpinner = new javax.swing.JSpinner();
        cPic = new javax.swing.JLabel();
        picNbSpinner = new javax.swing.JSpinner();
        animName = new javax.swing.JComboBox();
        anchorYSpinner = new javax.swing.JSpinner();
        widthSpinner = new javax.swing.JSpinner();
        posYSpinner = new javax.swing.JSpinner();
        posXSpinner = new javax.swing.JSpinner();
        currentPicSpinner = new javax.swing.JSpinner();
        anchorXSpinner = new javax.swing.JSpinner();
        heightSpinner = new javax.swing.JSpinner();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        animPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        bigpicZoomP = new javax.swing.JButton();
        bigpicZoomM = new javax.swing.JButton();

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

        jLabel7.setText("height:");

        jLabel5.setText("Y:");

        jLabel6.setText("width:");

        jLabel3.setText("image");

        jLabel4.setText("X:");

        jLabel1.setText("Anchor");

        jLabel2.setText("Anim");

        jButton1.setText("refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setText("Y:");

        jLabel8.setText("X:");

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

        picNbSpinner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changed(evt);
            }
        });

        animName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "walk", "leftarm", "jump", "fall", "armfire", "backfire", "explo", "explo2" }));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(42, 42, 42)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(72, 72, 72)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(posYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(66, 66, 66)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(posXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(anchorXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(anchorYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)))
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(periodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1)
                        .addComponent(updateAnimsValue)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cPic)
                            .addComponent(SaveButton))))
                .addGap(92, 92, 92))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(anchorXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(anchorYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(153, 153, 153)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(animName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4)
                                            .addComponent(posXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(posYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5))))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel11)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(picNbSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 56, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(currentPicSpinner)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(cPic)
                                        .addGap(14, 14, 14)
                                        .addComponent(SaveButton)
                                        .addGap(34, 34, 34)
                                        .addComponent(updateAnimsValue)
                                        .addGap(77, 77, 77)
                                        .addComponent(jButton1))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(137, 137, 137)
                                        .addGap(5, 5, 5)
                                        .addComponent(periodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(46, 46, 46)))
                                .addGap(44, 44, 44)))))
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(animPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
