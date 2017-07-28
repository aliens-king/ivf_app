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
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * @author Dell
 */
public class SpecialPot extends JPanel {

    private final UIClipartService clipartService = new UIClipartService();

    /**
     * Creates new form SpecialPot
     */


    public SpecialPot(UIPrintClient client) {
    	setMinimumSize(new Dimension(490, 303));
    	setMaximumSize(new Dimension(490, 303));
    	setPreferredSize(new Dimension(490, 303));
        initComponents();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(clipartService.getImage(client.getMainClient().getCouple().getId())));
        } catch (IOException e) {
            e.printStackTrace();

        }

        Image dimg = img.getScaledInstance(192, 299,
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);
        designLabel.setIcon(imageIcon);

        UIClient woman = new UIClient(client.getMainClient());
        UIClient partner = new UIClient(client.getPartner());


        womanSurname.setText(woman.getClient().getSurname().toUpperCase());
        try {
            womanOtherNames.setText(woman.getClient().getFirstName() + " " + woman.getClient().getMiddleName());
        }catch(StringIndexOutOfBoundsException e){
            womanOtherNames.setText(woman.getClient().getFirstName());
        }
            womanDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(woman.getClient().getdOB()));
        womanAge.setText(woman.getAge());
        womanFirstAlphanumericCharacter.setText("" + client.getMainClientTreatmentCode().getCode());
   //     womanSecondAlphanumericCharacter.setText("" + client.getMainClientTreatmentCode().getCode().charAt(1));
    //    womanThirdAlphanumericCharacter.setText("" + client.getMainClientTreatmentCode().getCode().charAt(2));


        partnerSurname.setText(partner.getClient().getSurname().toUpperCase());
        try {
            partnerOtherNames.setText(partner.getClient().getFirstName() + " " + partner.getClient().getMiddleName());
        }catch (StringIndexOutOfBoundsException e){
            partnerOtherNames.setText(partner.getClient().getFirstName());
        }
            partnerDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(partner.getClient().getdOB()));
        partnerAge.setText(partner.getAge());
        partnerFirstAlphanumericCharacter.setText("" + client.getPartnerTreatmentCode().getCode());
     //   partnerSecondAlphanumericCharacter.setText("" + client.getPartnerTreatmentCode().getCode().charAt(1));
      //  partnerThirdAlphanumericCharacter.setText("" + client.getPartnerTreatmentCode().getCode().charAt(2));

//
//        designLabel.setVisible(false);
//        jLabel13.setVisible(false);
//        jLabel15.setVisible(false);
//        jLabel17.setVisible(false);
//        jLabel19.setVisible(false);
//        jLabel3.setVisible(false);
//        jLabel5.setVisible(false);
//        jLabel7.setVisible(false);
//        jLabel9.setVisible(false);
//        partnerAge.setVisible(false);
//        partnerDate.setVisible(false);
//        partnerFirstAlphanumericCharacter.setVisible(false);
//        partnerJlabel1.setVisible(false);
//        partnerOtherNames.setVisible(false);
//        partnerSecondAlphanumericCharacter.setVisible(false);
//        partnerSurname.setVisible(false);
//        partnerThirdAlphanumericCharacter.setVisible(false);
//        womanAge.setVisible(false);
//        womanDate.setVisible(false);
//        womanFirstAlphanumericCharacter.setVisible(false);
//        womanJlabel.setVisible(false);
//        womanOtherNames.setVisible(false);
//        womanSecondAlphanumericCharacter.setVisible(false);
//        womanSurname.setVisible(false);
//        womanThirdAlphanumericCharacter.setVisible(false);

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        womanSurname = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        womanOtherNames = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        womanDate = new javax.swing.JLabel();
        womanAge = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        womanFirstAlphanumericCharacter = new javax.swing.JLabel();
        womanJlabel = new javax.swing.JLabel();
        partnerJlabel1 = new javax.swing.JLabel();
        partnerSurname = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        partnerOtherNames = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        partnerDate = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        partnerAge = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        partnerFirstAlphanumericCharacter = new javax.swing.JLabel();
        designLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        womanSurname.setBackground(new java.awt.Color(255, 255, 255));
        womanSurname.setFont(new Font("Open Sans", Font.BOLD, 22)); // NOI18N
        womanSurname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        womanSurname.setText("KOLOROV");
        womanSurname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        womanSurname.setIconTextGap(6);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new Font("Open Sans", Font.ITALIC, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Surname");

        womanOtherNames.setBackground(new java.awt.Color(255, 255, 255));
        womanOtherNames.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 20)); // NOI18N
        womanOtherNames.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        womanOtherNames.setText("Freeman R.");
        womanOtherNames.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        womanOtherNames.setIconTextGap(6);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new Font("Open Sans", Font.ITALIC, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Other Names");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new Font("Open Sans", Font.ITALIC, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("D.O.B.");

        womanDate.setBackground(new java.awt.Color(255, 255, 255));
        womanDate.setFont(new Font("Open Sans", Font.PLAIN, 16)); // NOI18N
        womanDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        womanDate.setText("22/01/94");
        womanDate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        womanDate.setIconTextGap(6);

        womanAge.setBackground(new java.awt.Color(255, 255, 255));
        womanAge.setFont(new Font("Open Sans", Font.PLAIN, 21)); // NOI18N
        womanAge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        womanAge.setText("20");
        womanAge.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        womanAge.setIconTextGap(6);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new Font("Open Sans", Font.ITALIC, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Age\n");

        womanFirstAlphanumericCharacter.setBackground(new java.awt.Color(255, 255, 255));
        womanFirstAlphanumericCharacter.setFont(new Font("Open Sans", Font.BOLD, 36)); // NOI18N
        womanFirstAlphanumericCharacter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        womanFirstAlphanumericCharacter.setText("G");
        womanFirstAlphanumericCharacter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        womanJlabel.setBackground(new java.awt.Color(255, 255, 255));
        womanJlabel.setFont(new Font("Open Sans", Font.BOLD, 14)); // NOI18N
        womanJlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        womanJlabel.setText("<HTML>W<br>O<br>M<br>A<br>N</HTML>"); // NOI18N
        womanJlabel.setToolTipText("");
        womanJlabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        womanJlabel.setCursor(new java.awt.Cursor(java.awt.Cursor.SE_RESIZE_CURSOR));
        womanJlabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        partnerJlabel1.setBackground(new java.awt.Color(255, 255, 255));
        partnerJlabel1.setFont(new Font("Open Sans", Font.BOLD, 14)); // NOI18N
        partnerJlabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        partnerJlabel1.setText("<HTML>P<br>A<br>R<br>T<br>N<br>E<br>R</HTML>"); // NOI18N
        partnerJlabel1.setToolTipText("");
        partnerJlabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        partnerJlabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.SE_RESIZE_CURSOR));
        partnerJlabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        partnerSurname.setBackground(new java.awt.Color(255, 255, 255));
        partnerSurname.setFont(new Font("Open Sans", Font.BOLD, 22)); // NOI18N
        partnerSurname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        partnerSurname.setText("KOLOROV");
        partnerSurname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        partnerSurname.setIconTextGap(6);

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new Font("Open Sans", Font.ITALIC, 11)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Surname");

        partnerOtherNames.setBackground(new java.awt.Color(255, 255, 255));
        partnerOtherNames.setFont(new java.awt.Font("Open Sans", 3, 22)); // NOI18N
        partnerOtherNames.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        partnerOtherNames.setText("Freeman R.");
        partnerOtherNames.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        partnerOtherNames.setIconTextGap(6);

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new Font("Open Sans", Font.ITALIC, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Other Names");

        partnerDate.setBackground(new java.awt.Color(255, 255, 255));
        partnerDate.setFont(new Font("Open Sans", Font.PLAIN, 16)); // NOI18N
        partnerDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        partnerDate.setText("22/01/94");
        partnerDate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        partnerDate.setIconTextGap(6);

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new Font("Open Sans", Font.ITALIC, 11)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("D.O.B.");

        partnerAge.setBackground(new java.awt.Color(255, 255, 255));
        partnerAge.setFont(new Font("Open Sans", Font.PLAIN, 21)); // NOI18N
        partnerAge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        partnerAge.setText("20");
        partnerAge.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(204, 204, 204)));
        partnerAge.setIconTextGap(6);

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new Font("Open Sans", Font.ITALIC, 11)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Age\n");

        partnerFirstAlphanumericCharacter.setBackground(new java.awt.Color(255, 255, 255));
        partnerFirstAlphanumericCharacter.setFont(new Font("Open Sans", Font.BOLD, 36)); // NOI18N
        partnerFirstAlphanumericCharacter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        partnerFirstAlphanumericCharacter.setText("G");
        partnerFirstAlphanumericCharacter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(2)
        			.addComponent(designLabel, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
        			.addGap(10)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        					.addComponent(partnerOtherNames, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
        					.addComponent(jLabel13, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(jLabel15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(partnerSurname, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
        					.addGroup(layout.createSequentialGroup()
        						.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        							.addComponent(jLabel17, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(partnerDate, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        							.addComponent(jLabel19, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(partnerAge, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))))
        				.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(womanOtherNames, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
        					.addComponent(jLabel3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(jLabel5, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(womanSurname, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
        					.addGroup(layout.createSequentialGroup()
        						.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        							.addComponent(jLabel7, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(womanDate, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        							.addComponent(jLabel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(womanAge, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(partnerFirstAlphanumericCharacter, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
        					.addGap(2))
        				.addComponent(womanFirstAlphanumericCharacter, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE))
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(partnerJlabel1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
        				.addComponent(womanJlabel, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
        			.addGap(8))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(10)
        					.addComponent(designLabel, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        						.addComponent(womanJlabel, Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(womanSurname, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        									.addGap(0)
        									.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        									.addGap(0)
        									.addComponent(womanOtherNames, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        									.addGap(0)
        									.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        									.addGap(0)
        									.addComponent(womanAge, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        								.addGroup(layout.createSequentialGroup()
        									.addContainerGap(70, Short.MAX_VALUE)
        									.addComponent(womanDate, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        									.addGap(0)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        										.addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))))
        							.addGap(1)
        							.addComponent(womanFirstAlphanumericCharacter, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)))
        					.addGap(3)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(partnerSurname, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        							.addGap(0)
        							.addComponent(jLabel13, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        							.addGap(0)
        							.addComponent(partnerOtherNames, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        							.addGap(0)
        							.addComponent(jLabel15, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(partnerDate, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        									.addGap(0)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(jLabel17, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        										.addComponent(jLabel19, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)))
        								.addComponent(partnerAge, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        							.addGap(1)
        							.addComponent(partnerFirstAlphanumericCharacter, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
        						.addComponent(partnerJlabel1))))
        			.addGap(10))
        );
        this.setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel designLabel;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel partnerAge;
    private javax.swing.JLabel partnerDate;
    private javax.swing.JLabel partnerFirstAlphanumericCharacter;
    private javax.swing.JLabel partnerJlabel1;
    private javax.swing.JLabel partnerOtherNames;
    private javax.swing.JLabel partnerSurname;
    private javax.swing.JLabel womanAge;
    private javax.swing.JLabel womanDate;
    private javax.swing.JLabel womanFirstAlphanumericCharacter;
    private javax.swing.JLabel womanJlabel;
    private javax.swing.JLabel womanOtherNames;
    private javax.swing.JLabel womanSurname;
    // End of variables declaration//GEN-END:variables
}
