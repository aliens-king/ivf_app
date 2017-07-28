package org.cf.card.ui.model;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.control.ComboBox;

public class FixedComboBoxListViewSkin<T> extends ComboBoxListViewSkin<T> {
    public FixedComboBoxListViewSkin(ComboBox<T> comboBox) {
        super(comboBox);
        getPopup().setAutoFix(false);
    }
}