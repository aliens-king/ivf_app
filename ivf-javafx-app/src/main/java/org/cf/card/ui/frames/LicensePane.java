package org.cf.card.ui.frames;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dell on 3/29/2015.
 */
public class LicensePane extends JPanel {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTextField companyName;
    private JTextField licenseKey;

    public LicensePane() {
        companyName = new JTextField(100);
        licenseKey = new JTextField(100);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        add(new JLabel("Company name: "), gbc);
        gbc.gridy++;
        add(new JLabel("License key: "), gbc);

        gbc.gridy = 0;
        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(companyName, gbc);
        gbc.gridy++;
        add(licenseKey, gbc);
    }

    public String getCompanyName() {
        return companyName.getText();
    }

    public String getPassword() {
        return licenseKey.getText();
    }
}

