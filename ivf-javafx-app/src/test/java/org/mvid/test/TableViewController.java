package org.mvid.test;

import java.util.ArrayList;
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

public class TableViewController {

	UICoupleService coupleService = new UICoupleService();

	@FXML
	private TableView<UiCryoTableView> embryoTable;
	@FXML
	private TableColumn<UiCryoTableView,String> cycle;
	@FXML
	private TableColumn<UiCryoTableView,String> srNo;
	@FXML
	private TableColumn<UiCryoTableView,String> quality;
	@FXML
	private TableColumn<UiCryoTableView,String> used;
	@FXML
	private TableColumn<UiCryoTableView,String> embSrNo;
	@FXML
	private TableColumn<UiCryoTableView,String> embQuality;
	@FXML
	private TableColumn<UiCryoTableView,String> embDateOfUse;

	private ObservableList<UiCryoTableView> aoUiCryoTableView = null;

	public void buidData() {
		aoUiCryoTableView =  FXCollections.observableArrayList();
		cycle.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("cycle"));
		srNo.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("srNo"));
		quality.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("quality"));
		used.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("used"));

		embSrNo.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("embSrNo"));
		embQuality.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("embQuality"));
		embDateOfUse.setCellValueFactory(new PropertyValueFactory<UiCryoTableView, String>("embDateOfUse"));


		System.out.println("embryoTable " + embryoTable);

		Couple couple = coupleService.getCoupleById(1l);
		System.out.println(couple.getWoman().getCodes());

		List<Codes> aoCodes = couple.getWoman().getCodes();
		for (Codes codes : aoCodes) {
			int totalEgg = 0;
			//int available = 0;
			int used = 0;

			System.out.println("code :: " + codes.getCode());
			List<EmbryoCode> aoEmbryCode = codes.getEmbryoCode();
			String code = codes.getCode();
			int count =1;
			for (EmbryoCode embryoCode : aoEmbryCode) {
				String quality ="";
				String eggQuality = "";
				String dateOfUses = " ";
				totalEgg = totalEgg+1;
				Boolean flag = false;
				if(embryoCode.getDateOfUse()!=null){
					used = used+1;
					Date date = embryoCode.getDateOfUse();
					dateOfUses = Util.formatDate(IConstants.DATE_FORMAT, date);
					flag = true;
				}
				System.out.println(flag);
				List<DayProgressValue> aoDayProgressValue = new ArrayList<DayProgressValue>();//embryoCode.getDayProgressValues();
				for (DayProgressValue dayProgressValue : aoDayProgressValue) {
					System.out.println(dayProgressValue.getEmbryoCode());
					if(dayProgressValue.getDayOptionId()== EnumDayTable.Option.CRYO.getKey()){
						System.out.println(embryoCode.getId() +" "+dayProgressValue.getDayOptionId());
						quality += EnumDayTable.Option.values()[dayProgressValue.getDayOptionId() - 1].getName()+".";
					}
					//day 0 type cryo means Egg
					if(dayProgressValue.getDayOptionId()== EnumDayTable.Option.CRYO.getKey() && dayProgressValue.getDayIndex() == 0){
						eggQuality = EnumDayTable.Option.values()[dayProgressValue.getDayOptionId() - 1].getName()+".";
//						UiCryoTableView cryoTableView = new UiCryoTableView(code, embryoCode.getIndex()+"", eggQuality, dateOfUses,  embryoCode.getIndex()+"", quality, dateOfUses);
//						aoUiCryoTableView.add(cryoTableView);
					}

				}
				if(count == 1){

				}else{
//					UiCryoTableView cryoTableView = new UiCryoTableView("",  embryoCode.getIndex()+"", quality, dateOfUses,  embryoCode.getIndex()+"", quality, dateOfUses);
//					aoUiCryoTableView.add(cryoTableView);
				}

				count++;
			}


		}
		System.out.println(aoUiCryoTableView.size());
		embryoTable.setItems(aoUiCryoTableView);
	}
}
//UiCryoTableView cryoTableView = new UiCryoTableView(cycle, srNo, quality, used, embSrNo, embQuality, embDateOfUse)