package org.cf.card.ui.model;

import java.util.Date;

import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

public class CellButton<T> extends TableCell<T, String> {

	final Button cellButton = new Button(MessageResource.getText("cellbutton.button.use"));
	// final DatePicker datePicker = new DatePicker();
	T obj;

	public CellButton(TableView<T> tableView) {

		cellButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				Date date = new Date();
				int index = getTableRow().getIndex();
				obj = tableView.getItems().get(index);
				if (obj instanceof UICryoEmbryo) {
					((UICryoEmbryo) obj).setDate(Util.formatDate(IConstants.DATE_FORMAT,date));
				} else if (obj instanceof UICryoEgg) {
					((UICryoEgg) obj).setDate(Util.formatDate(IConstants.DATE_FORMAT,date));
				} else if (obj instanceof UICryoActionSemen) {
					((UICryoActionSemen) obj).setDate(Util.formatDate(IConstants.DATE_FORMAT,date));
				} else if (obj instanceof UICryoSemen) {
					((UICryoSemen) obj).setDateOfUse(Util.formatDate(IConstants.DATE_FORMAT,date));
				} else if (obj instanceof UiCryoTableView) {
					((UiCryoTableView) obj).setUiCryoTableView(((UiCryoTableView) obj));
					((UiCryoTableView) obj).setUsed(Util.formatDate(IConstants.DATE_FORMAT,date));
				}
			}
		});
	}

	@Override
	protected void updateItem(String item, boolean empty) {
		System.out.println("Item "+item+"empty "+empty);
		super.updateItem(item, empty);

		if (item != null) {
			if (item != "") {
				setText(empty ? null : getString());
				setGraphic(null);
			} else {
				setText("");
				setGraphic(this.cellButton);
			}
		}

	}

	private String getString() {
		return getItem() == null ? "" : getItem();
	}

}