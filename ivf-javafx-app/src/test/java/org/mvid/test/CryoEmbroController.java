package org.mvid.test;

import java.util.Date;
import java.util.List;

import org.cf.card.model.Codes;
import org.cf.card.model.Couple;
import org.cf.card.model.DayProgressValue;
import org.cf.card.model.EmbryoCode;
import org.cf.card.ui.model.UiCryoTableView;
import org.cf.card.ui.service.UICoupleService;
import org.cf.card.util.EnumDayTable;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CryoEmbroController {

	UICoupleService coupleService = new UICoupleService();
	@FXML
	private TableView<UiCryoTableView> embryoTable;
	@FXML
	private TableColumn<UiCryoTableView, String> cycle;
	
	@FXML
	private TableColumn<UiCryoTableView, Integer> srNo;
	@FXML
	private TableColumn<UiCryoTableView, String> quality;
	@FXML
	private TableColumn<UiCryoTableView, String> dateOfUse;
	@FXML
	private TableColumn<UiCryoTableView, String> type;
	
	ObservableList<UiCryoTableView> aoUiCryoTableView = null;
	
	public void buildData(){
		aoUiCryoTableView =  FXCollections.observableArrayList();
		cycle.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("cycle"));
		srNo.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, Integer>("srNo"));
		quality.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("quality"));
		dateOfUse.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("used"));
		type.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("type"));
		

		
		Couple couple = coupleService.getCoupleById(1l);
		System.out.println(couple.getWoman().getCodes());
		UiCryoTableView cryoTableView = null;
		List<Codes> aoCodes = couple.getWoman().getCodes();
		
		for (Codes codes : aoCodes) {
			String code = codes.getCode();
			List<EmbryoCode> aoEmbryCode = codes.getEmbryoCode();
			int count=1;
			for (int i = 1; i <= aoEmbryCode.size(); i++) {
				EmbryoCode embryoCode = aoEmbryCode.get(i - 1);
				Date date = embryoCode.getDateOfUse();
				String dateOfUses = Util.formatDate(IConstants.DATE_FORMAT, date);
				
				Date dateOfEmb = embryoCode.getDestinyDate();
				String dateOfembryo = Util.formatDate(IConstants.DATE_FORMAT, dateOfEmb);
				List<DayProgressValue> aoDayProgresValue = embryoCode != null ? embryoCode.getDayProgressValues(): null;
				for (int k = 0; k < 10; k++) {
					String value = "";
					String cryoType = "";
					String cryoTypeEmb ="";
					for (DayProgressValue dayProgressValue : aoDayProgresValue) {
						if (dayProgressValue.getDayIndex() == k && embryoCode.getIndex() == i){
							value += EnumDayTable.Option.values()[dayProgressValue.getDayOptionId() - 1].getName()
									+ ".";
						if(dayProgressValue.getDayIndex() == 0 && dayProgressValue.getDayOptionId() == EnumDayTable.Option.CRYO.getKey())
							cryoType = "Egg";
						/*else
							cryoType = "";*/
						if(dayProgressValue.getDayIndex() > 0 && dayProgressValue.getDayOptionId() == EnumDayTable.Option.CRYO.getKey())
							cryoTypeEmb = "Embryo";
						/*else
							cryoTypeEmb = "";*/
						}
					}
					System.out.println("value : "+value);
					if(value.length() > 0 && cryoType.equals("Egg")){
						//cryoTableView = new  UiCryoTableView(embryoCode.getId(), code, count+"", value.length() > 0 ? value.substring(0, value.length() - 1) : value, dateOfUses, cryoType, "" ,couple);
						aoUiCryoTableView.add(cryoTableView);
						code ="";
						count++;
						cryoType = "";
					}else if(value.length() > 0 && cryoTypeEmb.equals("Embryo")){
					//	cryoTableView = new  UiCryoTableView(embryoCode.getId(), code, count+"", value.length() > 0 ? value.substring(0, value.length() - 1) : value, dateOfembryo, cryoTypeEmb, "",couple);
						aoUiCryoTableView.add(cryoTableView);
						code ="";	
						count++;
						cryoTypeEmb = "";
					}
					
				}

			}
			
			
			
			
		}
		
		
		
		embryoTable.setItems(aoUiCryoTableView);
		
	}
	
	
}
