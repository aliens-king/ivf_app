/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cf.card.ui.service.printing.templates;

import org.cf.card.ui.model.UIClient;
import org.cf.card.ui.model.UIPrintClient;
import org.cf.card.ui.service.UIClipartService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author Dell
 */
public class Straw extends JPanel {

    private final UIClipartService clipartService = new UIClipartService();

    /**
     * Creates new form Straw
     */



    public Straw(UIPrintClient client, String gender) {
    	setMinimumSize(new Dimension(440, 232));
    	setMaximumSize(new Dimension(440, 232));
    	setPreferredSize(new Dimension(440, 232));
        initComponents();

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(clipartService.getImage(client.getMainClient().getCouple().getId())));
        } catch (IOException e) {
            e.printStackTrace();

        }

        Image dimg = img.getScaledInstance(200, 30,
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);
        designLabel.setIcon(imageIcon);

        UIClient uiClient;

        if(gender.equals("man")) {
            uiClient = new UIClient(client.getPartner());
            codeLabel.setText("" + client.getMainClientTreatmentCode().getCode());
           // secondAlphanumericCharacter.setText("" + client.getMainClientTreatmentCode().getCode().charAt(1));
           // thirdAlphanumericCharacter.setText("" + client.getMainClientTreatmentCode().getCode().charAt(2));
        }
        else {
            uiClient = new UIClient(client.getMainClient());
            codeLabel.setText("" + client.getPartnerTreatmentCode().getCode());
          //  secondAlphanumericCharacter.setText("" + client.getPartnerTreatmentCode().getCode().charAt(1));
           // thirdAlphanumericCharacter.setText("" + client.getPartnerTreatmentCode().getCode().charAt(2));
        }
        surname.setText(uiClient.getClient().getSurname().toUpperCase());
        try {
            otherNames.setText(uiClient.getClient().getFirstName() + " " + uiClient.getClient().getMiddleName());
        }catch (StringIndexOutOfBoundsException e){
            otherNames.setText(uiClient.getClient().getFirstName());
        }

        
        Calendar c = Calendar.getInstance();
        c.setTime(client.getMainClientTreatmentCode().getTreatment().getStartDate());
        c.add(Calendar.DATE, 1);
        dateLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
        
        dopDynamicLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
//
//        dateLabel.setVisible(false);
//        designLabel.setVisible(false);
//        firstAlphanumericCharacter.setVisible(false);
//        jLabel3.setVisible(false);
//        jLabel4.setVisible(false);
//        otherNames.setVisible(false);
//        secondAlphanumericCharacter.setVisible(false);
//        surname.setVisible(false);
//        thirdAlphanumericCharacter.setVisible(false);

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        surname = new JLabel();
        jLabel3 = new JLabel();
        otherNames = new JLabel();
        jLabel4 = new JLabel();
        codeLabel = new JLabel();
        designLabel = new JLabel();
        dateLabel = new JLabel();
        dopStaticLabel = new JLabel();
        dopDynamicLabel = new JLabel();

        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        surname.setBackground(new Color(255, 255, 255));
        surname.setFont(new Font("Open Sans", 1, 18)); // NOI18N
        surname.setHorizontalAlignment(SwingConstants.CENTER);
        surname.setText("KOLOROV");
        surname.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(204, 204, 204)));
        surname.setIconTextGap(6);

        jLabel3.setBackground(new Color(255, 255, 255));
        jLabel3.setFont(new Font("Open Sans", 2, 13)); // NOI18N
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setText("Surname");

        otherNames.setBackground(new Color(255, 255, 255));
        otherNames.setFont(new Font("Open Sans", 3, 16)); // NOI18N
        otherNames.setHorizontalAlignment(SwingConstants.CENTER);
        otherNames.setText("Freeman R.");
        otherNames.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(204, 204, 204)));
        otherNames.setIconTextGap(6);

        jLabel4.setBackground(new Color(255, 255, 255));
        jLabel4.setFont(new Font("Open Sans", 2, 13)); // NOI18N
        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setText("Other Names");

        codeLabel.setBackground(new Color(255, 255, 255));
        codeLabel.setFont(new Font("Open Sans", 1, 28)); // NOI18N
        codeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        codeLabel.setText("AGGSDGZ");
        codeLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        dateLabel.setFont(new Font("Open Sans", 0, 24)); // NOI18N
        dateLabel.setText("11/5/2016");
        
        dopStaticLabel.setFont(new Font("Open Sans", 2, 13)); // NOI18N
        dopStaticLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dopStaticLabel.setText("Date of print");
        
        dopDynamicLabel.setFont(new Font("Open Sans", 0, 24)); // NOI18N
        dopDynamicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dopDynamicLabel.setText("11/5/2016");

      
        

        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(surname, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
        			.addGap(0)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(jLabel4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(otherNames, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
        			.addGap(0)
        			.addComponent(designLabel, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(dateLabel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(codeLabel, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        			.addGap(7)
        			.addComponent(dopDynamicLabel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap(316, Short.MAX_VALUE)
        			.addComponent(dopStaticLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
        			.addGap(43))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(110)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(surname, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        					.addGap(0)
        					.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(otherNames, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        					.addGap(0)
        					.addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
        				.addComponent(designLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(40)
        					.addComponent(dateLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(18)
        					.addComponent(dopStaticLabel, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
        					.addGap(4)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(dopDynamicLabel, 0, 0, Short.MAX_VALUE)
        						.addComponent(codeLabel, GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE))))
        			.addContainerGap(21, Short.MAX_VALUE))
        );
        this.setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel dateLabel;
    private JLabel designLabel;
    private JLabel codeLabel;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel otherNames;
    private JLabel surname;
    private JLabel dopStaticLabel;
    private JLabel dopDynamicLabel;
}
