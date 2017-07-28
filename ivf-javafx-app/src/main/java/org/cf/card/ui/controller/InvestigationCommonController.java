package org.cf.card.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.cf.card.model.InvestigatinValue;
import org.cf.card.model.Investigation;
import org.cf.card.model.PatientInvestigation;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.model.UIInvestigation;
import org.cf.card.ui.model.UISTInvestigation;
import org.cf.card.ui.service.UIInvestigationService;
import org.cf.card.util.EnumInvestigation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class InvestigationCommonController extends BaseController{
	public static final int BLOODWORK = 1;
	public static final int MICROBIOLOGY = 2;
	public static final int HORMONAL = 3;
	@FXML
	private TableView<UISTInvestigation> womanBloodWorkTable;

	@FXML
	private TableView<UISTInvestigation> womanMicrobiologyTable;

	@FXML
	private TableView<UISTInvestigation> womanHormonalTable;

	@FXML
	private TableView<UISTInvestigation> manBloodWorkTable;

	@FXML
	private TableView<UISTInvestigation> manMicrobiologyTable;

	@FXML
	private TableView<UISTInvestigation> manHormonalTable;

	List<Investigation> groups = null;


	private PatientInvestigation womanPatientInvestigation;
	private PatientInvestigation partnerPatientInvestigation;

	/*Calling rest api */
	UIInvestigationService uiInvestigationService = new UIInvestigationService();

	ObservableList<UISTInvestigation> aoBloodList = null;
	ObservableList<UISTInvestigation> aoMicrobiologyList = null;
	ObservableList<UISTInvestigation> aoHormonalList = null;

	/**Partner table view data*/
	ObservableList<UISTInvestigation> aoPartnerBloodList = null;
	ObservableList<UISTInvestigation> aoPartnerMicrobiologyList = null;
	ObservableList<UISTInvestigation> aoPartnerHormonalList = null;


	@FXML
	void initialize() {
		womanBloodWorkTable.setEditable(false);
		womanMicrobiologyTable.setEditable(false);
		womanHormonalTable.setEditable(false);
		manBloodWorkTable.setEditable(false);
		manMicrobiologyTable.setEditable(false);
		manHormonalTable.setEditable(false);

		groups = uiInvestigationService.findAllInvestigation();
		int count = 0;

		/**woman tableview's column list*/
		List<TableColumn<UISTInvestigation, String>> bloodColumnName = new ArrayList<>();
		List<TableColumn<UISTInvestigation, String>> microbiologyColumnName = new ArrayList<>();
		List<TableColumn<UISTInvestigation, String>> hormonalColumnName = new ArrayList<>();

		/**partner tableview's column list*/
		List<TableColumn<UISTInvestigation, String>> partnerBloodColumnName = new ArrayList<>();
		List<TableColumn<UISTInvestigation, String>> partnerMicrobiologyColumnName = new ArrayList<>();
		List<TableColumn<UISTInvestigation, String>> partnerHormonalColumnName = new ArrayList<>();

		for (Investigation investigation : groups) {
			if (investigation.getGroup() == BLOODWORK) {

				TableColumn<UISTInvestigation, String> column = new TableColumn<>(MessageResource.getText("BLOODWORK."+(EnumInvestigation.BloodWork.getEnumByKey(investigation.getName()))));

				final int s = new Integer(count);

//				column.setCellValueFactory(cellData -> cellData.getValue().getInvestigation().get(s).valueProperty());
				column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UISTInvestigation,String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<UISTInvestigation, String> param) {
						ObservableList<UIInvestigation> innerList =param.getValue().getInvestigation();

						if(s < innerList.size()){

							return innerList.get(s).valueProperty();
						}else{
							return new SimpleStringProperty();
						}

					}
				});

				column.setId(investigation.getName());
				column.setEditable(true);
				column.setCellFactory(TextFieldTableCell.forTableColumn());
				if(count==2 ||count==3 || count ==5){
					column.setMinWidth(10.0);
					column.setPrefWidth(70.5);
					column.setMaxWidth(200.0);
				}else{
					column.setMinWidth(10.0);
					column.setPrefWidth(60);
					column.setMaxWidth(200.0);
				}
				bloodColumnName.add(column);
				//partner blood tableview
				TableColumn<UISTInvestigation, String> partnerBloodColumn = new TableColumn<>(MessageResource.getText("BLOODWORK."+(EnumInvestigation.BloodWork.getEnumByKey(investigation.getName()))));
//				partnerBloodColumn.setCellValueFactory(cellData -> cellData.getValue().getPartnerInvestigation().get(s).valueProperty());
				partnerBloodColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UISTInvestigation,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<UISTInvestigation, String> param) {

						ObservableList<UIInvestigation> innerList =param.getValue().getPartnerInvestigation();
						if(s < innerList.size()){
							return innerList.get(s).valueProperty();
						}else{
							return new SimpleStringProperty();
						}
					}
				});

				partnerBloodColumn.setId(investigation.getName());
				partnerBloodColumn.setEditable(true);
				partnerBloodColumn.setCellFactory(TextFieldTableCell.forTableColumn());
				if(count==2 ||count==3 || count == 5){
					partnerBloodColumn.setMinWidth(10.0);
					partnerBloodColumn.setPrefWidth(70.5);
					partnerBloodColumn.setMaxWidth(200.0);
				}else{
					partnerBloodColumn.setMinWidth(10.0);
					partnerBloodColumn.setPrefWidth(60);
					partnerBloodColumn.setMaxWidth(200.0);
				}
				count++;
				partnerBloodColumnName.add(partnerBloodColumn);
				if(count==6)
					count =0;
			}else if (investigation.getGroup() == MICROBIOLOGY) {
				TableColumn<UISTInvestigation, String> column = new TableColumn<>(MessageResource.getText("MICROBIOLOGY."+(EnumInvestigation.MicroBiology.getEnumByKey(investigation.getName()))));
				final int s = new Integer(count);

//				column.setCellValueFactory(cellData -> cellData.getValue().getInvestigation().get(s).valueProperty());
				column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UISTInvestigation,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<UISTInvestigation, String> param) {

						ObservableList<UIInvestigation> innerList =param.getValue().getInvestigation();
						if(s < innerList.size()){
							return innerList.get(s).valueProperty();
						}else{
							return new SimpleStringProperty();
						}
					}
				});


				column.setId(investigation.getName());
				column.setEditable(true);
				column.setCellFactory(TextFieldTableCell.forTableColumn());
				column.setMinWidth(190);
				column.setPrefWidth(190);
				//count++;
				microbiologyColumnName.add(column);

				//partner Microbiology tableview
				TableColumn<UISTInvestigation, String> partnerMicroColumn = new TableColumn<>(MessageResource.getText("MICROBIOLOGY."+(EnumInvestigation.MicroBiology.getEnumByKey(investigation.getName()))));
//				partnerMicroColumn.setCellValueFactory(cellData -> cellData.getValue().getPartnerInvestigation().get(s).valueProperty());
				partnerMicroColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UISTInvestigation,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<UISTInvestigation, String> param) {

						ObservableList<UIInvestigation> innerList =param.getValue().getPartnerInvestigation();
						if(s < innerList.size()){
							return innerList.get(s).valueProperty();
						}else{
							return new SimpleStringProperty();
						}
					}
				});

				partnerMicroColumn.setId(investigation.getName());
				partnerMicroColumn.setEditable(true);
				partnerMicroColumn.setCellFactory(TextFieldTableCell.forTableColumn());
				partnerMicroColumn.setMinWidth(190);
				partnerMicroColumn.setPrefWidth(190);
				count++;
				partnerMicrobiologyColumnName.add(partnerMicroColumn);
				if(count==2)
					count =0;
			} else {
				TableColumn<UISTInvestigation, String> column = new TableColumn<>(MessageResource.getText("HORMONAL."+(EnumInvestigation.HormonalAssay.getEnumByKey(investigation.getName()))));
				final int s = new Integer(count);

//				column.setCellValueFactory(cellData -> cellData.getValue().getInvestigation().get(s).valueProperty());
				column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UISTInvestigation,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<UISTInvestigation, String> param) {

						ObservableList<UIInvestigation> innerList =param.getValue().getInvestigation();
						if(s < innerList.size()){
							return innerList.get(s).valueProperty();
						}else{
							return new SimpleStringProperty();
						}
					}
				});


				column.setId(investigation.getName());
				column.setEditable(true);
				column.setCellFactory(TextFieldTableCell.forTableColumn());
				if(count==3){
					column.setMinWidth(110);
					column.setPrefWidth(110);
				}else{
					column.setMinWidth(90);
					column.setPrefWidth(90);
				}
				//count++;
				hormonalColumnName.add(column);
				//partner Microbiology tableview
				TableColumn<UISTInvestigation, String> partnerHromonalColumn = new TableColumn<>(MessageResource.getText("HORMONAL."+(EnumInvestigation.HormonalAssay.getEnumByKey(investigation.getName()))));
//				partnerHromonalColumn.setCellValueFactory(cellData -> cellData.getValue().getPartnerInvestigation().get(s).valueProperty());
				partnerHromonalColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UISTInvestigation,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<UISTInvestigation, String> param) {

						ObservableList<UIInvestigation> innerList =param.getValue().getPartnerInvestigation();
						if(s < innerList.size()){
							return innerList.get(s).valueProperty();
						}else{
							return new SimpleStringProperty();
						}
					}
				});
				partnerHromonalColumn.setId(investigation.getName());
				partnerHromonalColumn.setEditable(true);
				partnerHromonalColumn.setCellFactory(TextFieldTableCell.forTableColumn());
				if(count==3){
					partnerHromonalColumn.setMinWidth(100);
					partnerHromonalColumn.setPrefWidth(100);
				}else{
					partnerHromonalColumn.setMinWidth(90);
					partnerHromonalColumn.setPrefWidth(90);
				}
				count++;
				partnerHormonalColumnName.add(partnerHromonalColumn);
				if(count==4)
					count =0;
			}

		}
		womanBloodWorkTable.getColumns().addAll(bloodColumnName);
		womanMicrobiologyTable.getColumns().addAll(microbiologyColumnName);
		womanHormonalTable.getColumns().addAll(hormonalColumnName);

		manBloodWorkTable.getColumns().addAll(partnerBloodColumnName);
		manMicrobiologyTable.getColumns().addAll(partnerMicrobiologyColumnName);
		manHormonalTable.getColumns().addAll(partnerHormonalColumnName);


	}

	public void build(){
		aoBloodList = FXCollections.observableArrayList();
		aoMicrobiologyList = FXCollections.observableArrayList();
		aoHormonalList = FXCollections.observableArrayList();
		aoPartnerBloodList = FXCollections.observableArrayList();
		aoPartnerMicrobiologyList = FXCollections.observableArrayList();
		aoPartnerHormonalList = FXCollections.observableArrayList();
		ObservableList<UIInvestigation> aoBlood = FXCollections.observableArrayList();
		ObservableList<UIInvestigation> aoMicrobiology = FXCollections.observableArrayList();
		ObservableList<UIInvestigation> aoHormonal = FXCollections.observableArrayList();

		/**Partner table view data*/
		ObservableList<UIInvestigation> aoPartnerBlood = FXCollections.observableArrayList();
		ObservableList<UIInvestigation> aoPartnerMicrobiology = FXCollections.observableArrayList();
		ObservableList<UIInvestigation> aoPartnerHormonal = FXCollections.observableArrayList();

		if(null != manCode && null != womanCode){
			partnerPatientInvestigation = manCode.getPatientInvestigation();
			womanPatientInvestigation = womanCode.getPatientInvestigation();
			if(partnerPatientInvestigation!=null){
				List<InvestigatinValue> aoPartnerInvestigtaionValue = partnerPatientInvestigation.getInvestigatinValues();
				for (InvestigatinValue investigatinValue : aoPartnerInvestigtaionValue) {
					if(investigatinValue.getInvestigation().getGroup() == BLOODWORK){
						UIInvestigation uiInvestigation1 = new UIInvestigation(investigatinValue.getValue(), investigatinValue.getInvestigation().getId().intValue());
						aoPartnerBlood.add(uiInvestigation1);
					}else if(investigatinValue.getInvestigation().getGroup() == MICROBIOLOGY){
						UIInvestigation uiInvestigation1 = new UIInvestigation(investigatinValue.getValue(), investigatinValue.getInvestigation().getId().intValue());
						aoPartnerMicrobiology.add(uiInvestigation1);
					}else{
						UIInvestigation uiInvestigation1 = new UIInvestigation(investigatinValue.getValue(), investigatinValue.getInvestigation().getId().intValue());
						aoPartnerHormonal.add(uiInvestigation1);
					}
				}
			}
			if(womanPatientInvestigation!=null){
				List<InvestigatinValue> aoWomanInvestigationValue = womanPatientInvestigation.getInvestigatinValues();
				for (InvestigatinValue investigatinValue : aoWomanInvestigationValue) {
					if(investigatinValue.getInvestigation().getGroup() == BLOODWORK){
						UIInvestigation uiInvestigation = new UIInvestigation(investigatinValue.getValue(), investigatinValue.getInvestigation().getId().intValue());
						aoBlood.add(uiInvestigation);
					}else if(investigatinValue.getInvestigation().getGroup() == MICROBIOLOGY){
						UIInvestigation uiInvestigation = new UIInvestigation(investigatinValue.getValue(), investigatinValue.getInvestigation().getId().intValue());
						aoMicrobiology.add(uiInvestigation);
					}else{
						UIInvestigation uiInvestigation = new UIInvestigation(investigatinValue.getValue(), investigatinValue.getInvestigation().getId().intValue());
						aoHormonal.add(uiInvestigation);
					}
				}
			}
		}
		UISTInvestigation uiSTBloodInvestigation = new UISTInvestigation(aoBlood,null);
		aoBloodList.add(uiSTBloodInvestigation);
		UISTInvestigation uiSTMicrobiologyInvestigation = new UISTInvestigation(aoMicrobiology,null);
		aoMicrobiologyList.add(uiSTMicrobiologyInvestigation);
		UISTInvestigation uiSTHormonalInvestigation = new UISTInvestigation(aoHormonal,null);
		aoHormonalList.add(uiSTHormonalInvestigation);

		UISTInvestigation uiSTPartnerBloodInvestigation = new UISTInvestigation(null,aoPartnerBlood);
		aoPartnerBloodList.add(uiSTPartnerBloodInvestigation);
		UISTInvestigation uiSTPartnerMicrobiologyInvestigation = new UISTInvestigation(null,aoPartnerMicrobiology);
		aoPartnerMicrobiologyList.add(uiSTPartnerMicrobiologyInvestigation);
		UISTInvestigation uiSTPartnerHormonalInvestigation = new UISTInvestigation(null,aoPartnerHormonal);
		aoPartnerHormonalList.add(uiSTPartnerHormonalInvestigation);
		womanBloodWorkTable.setItems(aoBloodList);
		womanMicrobiologyTable.setItems(aoMicrobiologyList);
		womanHormonalTable.setItems(aoHormonalList);

		manBloodWorkTable.setItems(aoPartnerBloodList);
		manMicrobiologyTable.setItems(aoPartnerMicrobiologyList);
		manHormonalTable.setItems(aoPartnerHormonalList);
	}
}
