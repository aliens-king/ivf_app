package org.cf.card.ui.model;


import java.util.ArrayList;
import java.util.List;

import org.cf.card.dto.EtTableDto;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIEtTable {
	private StringProperty index;

	private StringProperty time;

	private List<ObjectProperty<EtTableDto>> etDetails;

	/*public UIEtTable(String time, ObservableList<UIEtTableDetails> uiEtDetails){
//		this.index = new SimpleStringProperty(index);
		this.time = new SimpleStringProperty(time);
		this.uiEtDetails = new SimpleListProperty<>(uiEtDetails);

	}*/

	public UIEtTable(String time, List<EtTableDto> etDetails){
//		this.index = new SimpleStringProperty(index);
		this.time = new SimpleStringProperty(time);
		this.etDetails = new ArrayList<ObjectProperty<EtTableDto>>();
		List<ObjectProperty<EtTableDto>>  etDetailsList = new ArrayList<>();

		for(EtTableDto etDetail: etDetails){
			etDetailsList.add(new SimpleObjectProperty<EtTableDto>(etDetail));

		}

		this.etDetails =etDetailsList;


	}

/*	public UIEtTable(String time){
//		this.index = new SimpleStringProperty(index);
		this.time = new SimpleStringProperty(time);
	}
*/

	/**
	 * Index property.
	 *
	 * @return the string property
	 */
	public final StringProperty indexProperty() {
		return this.index;
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public final java.lang.String getIndex() {
		return this.indexProperty().get();
	}

	/**
	 * Sets the index.
	 *
	 * @param index
	 *            the new index
	 */
	public final void setIndex(final java.lang.String index) {
		this.indexProperty().set(index);
	}

	public final StringProperty timeProperty(){
		return this.time;
	}

	public final String getTime(){
		return this.timeProperty().get();
	}

	public final void setTime(final String time){
		this.timeProperty().set(time);
	}

	public List<ObjectProperty<EtTableDto>> getEtDetails() {
		return etDetails;
	}

	public void setEtDetails(List<ObjectProperty<EtTableDto>> etDetails) {
		this.etDetails = etDetails;
	}

}
