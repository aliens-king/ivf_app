package org.cf.card.ui.util;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLUtils {

	public static void getElementByFxIDAndSetText(Node parentNode, String fxId, String text) {
		Node element = parentNode.lookup(fxId);
		if (element instanceof Label) {
			((Label) element).setText(text);
		}else if (element instanceof TextField) {
			((TextField) element).setText(text);
		}
	}
}
