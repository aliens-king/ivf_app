/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cf.card.ui.service.printing.templates;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

import org.cf.card.ui.model.UIClient;
import org.cf.card.ui.model.UIPrintClient;
import org.cf.card.ui.service.UIClipartService;

/**
 * @author Dell
 */
public class FMDish extends JPanel {

    private final UIClipartService clipartService = new UIClipartService();

    private boolean mirror = false;
    /**
     * Creates new form FMDish
     * @wbp.parser.constructor
     */


    public FMDish(UIPrintClient client) {
    	init(client);
    }


    public FMDish(UIPrintClient client, boolean mirror) {
		this.mirror = mirror;
		init(client);
	}


    private void init(UIPrintClient client){
    	setMinimumSize(new Dimension(126, 166));
    	setMaximumSize(new Dimension(126, 166));
    	setPreferredSize(new Dimension(126, 166));
        initComponents();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(clipartService.getImage(client.getMainClient().getCouple().getId())));
        } catch (IOException e) {
            e.printStackTrace();

        }

        Image dimg = img.getScaledInstance(110, 100,
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);
        designLabel.setIcon(imageIcon);

        UIClient uiClient = new UIClient(client.getMainClient());

        surname.setText(uiClient.getClient().getSurname().toUpperCase());
//        codeLabel.setText("########");
        codeLabel.setText("" + client.getMainClientTreatmentCode().getCode());
//
//        designLabel.setVisible(false);
//        firstAlphanumericCharacter.setVisible(false);
//        secondAlphanumericCharacter.setVisible(false);
//        surname.setVisible(false);
//        thirdAlphanumericCharacter.setVisible(false);

    }


	/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        designLabel = new javax.swing.JLabel();
        designLabel.setPreferredSize(new Dimension(100, 100));
        designLabel.setMaximumSize(new Dimension(100, 100));
        designLabel.setMinimumSize(new Dimension(100, 100));
        designLabel.setBorder(null);
        surname = new javax.swing.JLabel();
        surname.setMinimumSize(new Dimension(20, 24));
        surname.setMaximumSize(new Dimension(20, 24));
        surname.setPreferredSize(new Dimension(20, 24));
        codeLabel = new javax.swing.JLabel();
        codeLabel.setMinimumSize(new Dimension(24, 24));
        codeLabel.setMaximumSize(new Dimension(24, 24));
        codeLabel.setPreferredSize(new Dimension(24, 24));

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new LineBorder(new Color(0, 0, 0)));

        designLabel.setBackground(new java.awt.Color(255, 255, 255));

        surname.setBackground(new java.awt.Color(255, 255, 255));
        surname.setFont(new Font("Open Sans", Font.BOLD, 20)); // NOI18N
        surname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        codeLabel.setBackground(new java.awt.Color(255, 255, 255));
        codeLabel.setFont(new Font("Open Sans", Font.BOLD, 20)); // NOI18N
        codeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        codeLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(designLabel, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
        			.addContainerGap())
        		.addGroup(layout.createSequentialGroup()
        			.addGap(8)
        			.addComponent(codeLabel, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
        			.addGap(8))
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addGap(3)
        			.addComponent(surname, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
        			.addGap(3))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(5)
        			.addComponent(surname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(5)
        			.addComponent(codeLabel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(designLabel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        this.setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel designLabel;
    private javax.swing.JLabel codeLabel;
    private javax.swing.JLabel surname;
    // End of variables declaration//GEN-END:variables

    @Override
    public void paint(Graphics g) {
    	if(mirror){
    		final Graphics2D g2d = (Graphics2D) g.create();
        	AffineTransform tx = new AffineTransform();
        	tx.translate(getWidth(), 0);
        	tx.scale(-1, 1);
        	g2d.transform(tx);
        	super.paint(g2d);
    	}else
    		super.paint(g);


    }

}
