package org.cf.card.ui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class UIEmbryoThaw {

	private LongProperty id;
	private IntegerProperty srNo;
	private ObjectProperty<UIDay> day1;
	private ObjectProperty<UIDay> day2;



	public UIEmbryoThaw(Long id, int srNo, UIDay day1, UIDay day2) {
		this.id = new SimpleLongProperty(id);
		this.srNo = new SimpleIntegerProperty(srNo);
		this.day1 = new SimpleObjectProperty<UIDay>(day1);
		this.day2 = new SimpleObjectProperty<UIDay>(day2);
	}

	public final LongProperty idProperty() {
		return this.id;
	}

	public final long getId() {
		return this.idProperty().get();
	}

	public final void setId(final long id) {
		this.idProperty().set(id);
	}

	public final IntegerProperty srNoProperty() {
		return this.srNo;
	}

	public final int getSrNo() {
		return this.srNoProperty().get();
	}

	public final void setSrNo(final int srNo) {
		this.srNoProperty().set(srNo);
	}

	public final ObjectProperty<UIDay> day2Property() {
		return this.day2;
	}

	public final org.cf.card.ui.model.UIDay getDay2() {
		return this.day2Property().get();
	}

	public final void setDay2(final org.cf.card.ui.model.UIDay day2) {
		this.day2Property().set(day2);
	}

	public final ObjectProperty<UIDay> day1Property() {
		return this.day1;
	}

	public final org.cf.card.ui.model.UIDay getDay1() {
		return this.day1Property().get();
	}

	public final void setDay1(final org.cf.card.ui.model.UIDay day1) {
		this.day1Property().set(day1);
	}

}
