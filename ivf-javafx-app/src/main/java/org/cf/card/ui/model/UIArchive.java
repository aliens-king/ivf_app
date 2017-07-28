package org.cf.card.ui.model;

import org.cf.card.dto.ArchiveDto;
import org.cf.card.util.IConstants;
import org.cf.card.util.Util;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Ankit
 *
 */
public class UIArchive {

	private LongProperty id;
	private StringProperty date;
	private ObjectProperty<ArchiveDto> fileName;
	private StringProperty extention;
	private BooleanProperty check;
	private IntegerProperty deleteFile;

	public UIArchive(ArchiveDto archiveDto) {
		this.id = new SimpleLongProperty(archiveDto.getId());
		this.date = new SimpleStringProperty(Util.formatDate(IConstants.DATE_FORMAT, archiveDto.getUploadedDate()));
		this.fileName = new SimpleObjectProperty<ArchiveDto>(archiveDto);
		this.extention = new SimpleStringProperty(archiveDto.getExtention());
		this.check = new SimpleBooleanProperty(false);
		this.deleteFile = new SimpleIntegerProperty();
	}

	public final StringProperty dateProperty() {
		return this.date;
	}

	public final java.lang.String getDate() {
		return this.dateProperty().get();
	}

	public final void setDate(final java.lang.String date) {
		this.dateProperty().set(date);
	}

	public final ObjectProperty<ArchiveDto> fileNameProperty() {
		return this.fileName;
	}

	public final org.cf.card.dto.ArchiveDto getFileName() {
		return this.fileNameProperty().get();
	}

	public final void setFileName(final org.cf.card.dto.ArchiveDto fileName) {
		this.fileNameProperty().set(fileName);
	}

	public final StringProperty extentionProperty() {
		return this.extention;
	}

	public final java.lang.String getExtention() {
		return this.extentionProperty().get();
	}

	public final void setExtention(final java.lang.String extention) {
		this.extentionProperty().set(extention);
	}

	public final IntegerProperty deleteFileProperty() {
		return this.deleteFile;
	}

	public final int getDeleteFile() {
		return this.deleteFileProperty().get();
	}

	public final void setDeleteFile(final int deleteFile) {
		this.deleteFileProperty().set(deleteFile);
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

	public final BooleanProperty checkProperty() {
		return this.check;
	}

	public final boolean isCheck() {
		return this.checkProperty().get();
	}

	public final void setCheck(final boolean check) {
		this.checkProperty().set(check);
	}


}
