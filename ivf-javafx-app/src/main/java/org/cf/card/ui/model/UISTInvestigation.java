/**
 *
 */
package org.cf.card.ui.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

/**
 * @author surinder
 *
 */
/**
 * @author insonix
 *
 */
/**
 * @author insonix
 *
 */
public class UISTInvestigation {


	private ListProperty<UIInvestigation> investigation;

	private ListProperty<UIInvestigation> partnerInvestigation;

//	private ListProperty<UIMicrobiology> microbiology;
//
//	private ListProperty<UIHormonal> hormonal;

	/*public UISTInvestigation(ObservableList<UIInvestigation> investigation,ObservableList<UIMicrobiology> microbiology,ObservableList<UIHormonal> hormonal) {
		this.investigation = new SimpleListProperty<>(investigation);
		this.microbiology = new SimpleListProperty<>(microbiology);
		this.hormonal = new SimpleListProperty<>(hormonal);
	}*/

	public UISTInvestigation(ObservableList<UIInvestigation> investigation) {
		this.investigation = new SimpleListProperty<>(investigation);

	}

	public UISTInvestigation(ObservableList<UIInvestigation> investigation,ObservableList<UIInvestigation> partnerInvestigation) {
		this.investigation = new SimpleListProperty<>(investigation);
		this.partnerInvestigation = new SimpleListProperty<>(partnerInvestigation);

	}

	public final ListProperty<UIInvestigation> investigationProperty() {
		return this.investigation;
	}

	public final javafx.collections.ObservableList<org.cf.card.ui.model.UIInvestigation> getInvestigation() {
		return this.investigationProperty().get();
	}
	public final void setInvestigation(
			final javafx.collections.ObservableList<org.cf.card.ui.model.UIInvestigation> investigation) {
		this.investigationProperty().set(investigation);
	}
//------------------------

	public final ListProperty<UIInvestigation> partnerInvestigationProperty() {
		return this.partnerInvestigation;
	}

	public final javafx.collections.ObservableList<org.cf.card.ui.model.UIInvestigation> getPartnerInvestigation() {
		return this.partnerInvestigationProperty().get();
	}
	public final void setPartnerInvestigation(
			final javafx.collections.ObservableList<org.cf.card.ui.model.UIInvestigation> partnerInvestigation) {
		this.partnerInvestigationProperty().set(partnerInvestigation);
	}


//--------------------
//	public final ListProperty<UIMicrobiology> microbiologyProperty() {
//		return this.microbiology;
//	}
//
//	public final javafx.collections.ObservableList<org.cf.card.ui.model.UIMicrobiology> getMicrobiology() {
//		return this.microbiologyProperty().get();
//	}
//	public final void setMicrobiology(
//			final javafx.collections.ObservableList<org.cf.card.ui.model.UIMicrobiology> microbiology) {
//		this.microbiologyProperty().set(microbiology);
//	}
////-------------------------
//	public final ListProperty<UIHormonal> hormonalProperty() {
//		return this.hormonal;
//	}
//
//	public final javafx.collections.ObservableList<org.cf.card.ui.model.UIHormonal> getHormonal() {
//		return this.hormonalProperty().get();
//	}
//	public final void setHormonal(
//			final javafx.collections.ObservableList<org.cf.card.ui.model.UIHormonal> hormonal) {
//		this.hormonalProperty().set(hormonal);
//	}


}
