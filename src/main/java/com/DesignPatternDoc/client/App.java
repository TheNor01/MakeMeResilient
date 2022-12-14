package com.DesignPatternDoc.client;
import java.awt.Image;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import java.awt.Container;
import com.DesignPatternDoc.DataContracts.models.DesignPatternDoc;


import com.DesignPatternDoc.DataContracts.DPDocumentationInterface;
/**
 * Hello world!
 *
 */
public class App {
    static JComboBox<String> comboBox = new JComboBox<String>();
    static JTextArea dpDescription = new JTextArea("");
    static JLabel dpTitle = new JLabel("", JLabel.CENTER);
    static JLabel picLabel = new JLabel("", JLabel.CENTER);


    //CHANGED, prima vi era proxy
    static DPDocumentationInterface service = new ServiceProxy();

    public static void main(String[] args) {
        System.out.println("Hello World!");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("DPDoc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(frame.getContentPane());
        frame.setVisible(true);
        //Display the window.
        getInitialData();
        frame.pack();
    }


    private static void addComponentsToPane(Container pane){
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(Box.createVerticalGlue());
        setComboBoxListener();
        JPanel comboBoxWrapper = new JPanel();
        comboBoxWrapper.add( comboBox );
        pane.add(comboBoxWrapper);
        setDpTitle();
        pane.add(dpTitle);
        setDpDescription();
        pane.add(dpDescription);
        setPicLabel();
        pane.add(picLabel);

    }


    private static void setDpDescription() {
        dpDescription.setFont(new FontUIResource("Courier", FontUIResource.PLAIN,12));
        addMargin(dpDescription, 10,50,10,50);
        dpDescription.setLineWrap(true);
    }


    private static void setDpTitle() {
        
        dpTitle.setFont(new FontUIResource("Courier", FontUIResource.BOLD,24));
        dpTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        addMargin(dpTitle, 10,50,10,50);
    }


    private static void setComboBoxListener() {
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
              int state = itemEvent.getStateChange();
              ItemSelectable is = itemEvent.getItemSelectable();
              onSelectChanges(selectedString(is));
            }
          };
          comboBox.addItemListener(itemListener);
    }


    static void setPicLabel(){

        picLabel.setSize(600, 300);
        picLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        addMargin(picLabel, 20,20,50,20); 
        //setImage();
    }

    static void addMargin(JComponent comp, int top, int left, int bottom, int right){
        Border border = comp.getBorder();
        Border margin = new EmptyBorder(top,left,bottom, right);
        comp.setBorder(new CompoundBorder(border, margin));
    }

    static void getInitialData(){
        String[] dpNames = service.getDPNames();
        for (String name : dpNames) {
            comboBox.addItem(name);
        }
        DesignPatternDoc doc = service.getDPDocumentation(dpNames[0]);
        dpTitle.setText(doc.title);
        dpDescription.setText(doc.description);
        String imageBase64 = service.getUMLDiagram(dpNames[0]);
        BufferedImage img = getImageFromBase64(imageBase64);
        if(img != null)
            setImage(img);

    }

    static private String selectedString(ItemSelectable is) {
        Object selected[] = is.getSelectedObjects();
        return ((selected.length == 0) ? "null" : (String) selected[0]);
      }

    static void onSelectChanges(String selection){
         DesignPatternDoc dpDoc = service.getDPDocumentation(selection);
         dpTitle.setText(dpDoc.title);
         dpDescription.setText(dpDoc.description);
         String imageBase64 = service.getUMLDiagram(selection);
        BufferedImage img = getImageFromBase64(imageBase64);
        if(img != null)
            setImage(img);
    }

    static BufferedImage getImageFromBase64(String base64){
        if(base64.equals("")) return null;
        String base64Image = base64;
        try{
            base64Image = base64.split(",")[1];
        }
        catch(Exception e){}
        
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (IOException e) {
            e.printStackTrace();
            return null;     
        }
    }


    static void setImage(BufferedImage img){ 
        Image dimg = img.getScaledInstance(600, 300, img.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        picLabel.setIcon(imageIcon);
    }
}
