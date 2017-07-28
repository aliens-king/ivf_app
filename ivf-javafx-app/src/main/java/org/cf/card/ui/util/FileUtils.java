package org.cf.card.ui.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.cf.card.dto.RegistrantDto;
import org.cf.card.ui.configuration.MessageResource;
import org.cf.card.ui.frames.Notify;
import org.cf.card.ui.service.UIRegistrantService;
import org.cf.card.ui.validation.FormValidator;
import org.cf.card.util.EnumPermission;
import org.cf.card.util.EnumPermission.Module;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * @author Stefan Bulzan
 * @since 2/6/15
 */
public class FileUtils {
	

	public static InputStream loadFromClasspath(String path) {
		return FileUtils.class.getClassLoader().getResourceAsStream(path);
	}

	public static void privillegeError() {
		Notify notify = new Notify(AlertType.ERROR, MessageResource.getText("fileutil.error.message"));
		notify.showAndWait();
	}

	public static void privillegeEditError() {
		Notify notify = new Notify(AlertType.ERROR, MessageResource.getText("fileutil.error.message"));
		notify.showAndWait();
	}

	public static void playMedia(String soundFilePath) {
		Media media = new Media(FileUtils.class.getResource(soundFilePath).toString());
		MediaPlayer mp = new MediaPlayer(media);
		mp.play();
	}

	public static SequentialTransition createTransition(final ImageView iv, final Image img) {
		FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0), iv);
		fadeOutTransition.setFromValue(0.0);
		fadeOutTransition.setToValue(0.0);
		fadeOutTransition.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				iv.setImage(img);
				;
			}

		});

		FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(4), iv);
		fadeInTransition.setFromValue(0.0);
		fadeInTransition.setToValue(2.0);
		SequentialTransition sequentialTransition = SequentialTransitionBuilder.create()
				.children(fadeOutTransition, fadeInTransition).build();

		return sequentialTransition;
	}

	/**
	 * Check if screen requres treamte to be started to view
	 *
	 * @param moduleKey
	 * @return true if module is open and doesn't requires treatment to start t
	 *         view the screen
	 */
	public static boolean isOpenWithoutTreatmentStart(int moduleKey) {

		Map<Integer, Module> openScreens = new HashMap<Integer, Module>();

		openScreens.put(EnumPermission.Module.DIRECTORY.getKey(), Module.DIRECTORY);
		openScreens.put(EnumPermission.Module.REGISTER_PATIENT.getKey(), Module.REGISTER_PATIENT);
		openScreens.put(EnumPermission.Module.PATIENT_LIST.getKey(), Module.PATIENT_LIST);
		openScreens.put(EnumPermission.Module.PATIENT_DETAILS.getKey(), Module.PATIENT_DETAILS);
		openScreens.put(EnumPermission.Module.SEARCH_PATIENTS.getKey(), Module.SEARCH_PATIENTS);
		openScreens.put(EnumPermission.Module.PRINT.getKey(), Module.PRINT);
		openScreens.put(EnumPermission.Module.SEARCH_BY_NAME_CODE.getKey(), Module.SEARCH_BY_NAME_CODE);
		openScreens.put(EnumPermission.Module.APPOINTMENT_OVERVIEW.getKey(), Module.APPOINTMENT_OVERVIEW);
		openScreens.put(EnumPermission.Module.SCHEDULE_APPOINTMENT.getKey(), Module.SCHEDULE_APPOINTMENT);
		openScreens.put(EnumPermission.Module.REGISTER_USER.getKey(), Module.REGISTER_USER);
		openScreens.put(EnumPermission.Module.USER_DETAILS.getKey(), Module.USER_DETAILS);
		openScreens.put(EnumPermission.Module.USER_PRIVILLEGES.getKey(), Module.USER_PRIVILLEGES);
		openScreens.put(EnumPermission.Module.UNPAID_BILL_OVERALL.getKey(), Module.UNPAID_BILL_OVERALL);

		return openScreens.containsKey(moduleKey);

	}
	

	/**
	 * Save or update registrant.
	 *
	 * @param registrantDto the registrant dto
	 * @param womanCodeId the woman code id
	 * @param sceernId the sceern id
	 * @param userRegistrant the user registrant
	 * @param asstRegistrant the asst registrant
	 * @return the registrant dto
	 */
	public static RegistrantDto saveOrUpdateRegistrant(RegistrantDto registrantDto, Long womanCodeId, int sceernId, TextField userRegistrant, TextField asstRegistrant){
		UIRegistrantService uiRegistrantService = new UIRegistrantService();
		int ok = 1;
		ok = FormValidator.validateAddRegistrant(userRegistrant, asstRegistrant);
		if (ok == 1) {
			if (null == registrantDto) {
				registrantDto = new RegistrantDto();
			}
			registrantDto.setCodeId(womanCodeId);
			registrantDto.setNameOfUser(userRegistrant.getText());
			registrantDto.setAssistantUser(asstRegistrant.getText());
			registrantDto.setScreenId(sceernId);
			registrantDto = uiRegistrantService.save(registrantDto);
		} else {
			Notify notify = new Notify(AlertType.INFORMATION);
			notify.setContentText(MessageResource.getText("common.save.validate.msg"));
			notify.showAndWait();
		}
		return registrantDto;
	}
}
