/**
 *
 */
package org.cf.card.ui.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

/**
 * @author Nikhil Mahajan
 *
 */
public class ComboBoxCell<R, C> extends TableCell<R, C> {

	R obj;
	private final ComboBox<C> comboBox;

	public ComboBoxCell(ObservableList<C> items) {

		this.comboBox = new ComboBox<>(items);

		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

		comboBox.valueProperty().addListener(new ChangeListener<C>() {
			@SuppressWarnings("unchecked")
			@Override
			public void changed(ObservableValue<? extends C> obs, C oldValue, C newValue) {
				// attempt to update property:
				ObservableValue<C> property = getTableColumn().getCellObservableValue(getIndex());
				if (property instanceof WritableValue) {
					((WritableValue<C>) property).setValue(newValue);
				}
			}
		});
	}

	@Override
	protected void updateItem(C item, boolean empty) {
		super.updateItem(item, empty);
		super.updateItem(item, empty);
		if (empty) {
			setGraphic(null);
		} else {
			comboBox.setValue(item);
			setGraphic(comboBox);
		}

		// if (getItem() != null) {
		// if(getItem() instanceof Injection){
		// UIEmbryoService uiEmbryoService = new UIEmbryoService();
		// ComboBox<Injection> comboBox =
		// uiEmbryoService.fillInjectionComboBox((Injection)getItem());
		// this.setGraphic(comboBox);
		// }
		// }

	}

}
