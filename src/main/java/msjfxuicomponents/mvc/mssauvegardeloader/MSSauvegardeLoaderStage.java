package msjfxuicomponents.mvc.mssauvegardeloader;

import javafx.stage.Modality;
import msdatabaseutils.BackupsManager;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.SimpleStageType;

public class MSSauvegardeLoaderStage extends SimpleStageType<MSSauvegardeLoaderController> {

	public MSSauvegardeLoaderStage(String title, BackupsManager backupsManager) {
		super(title, "/msjfxuicomponents/mvc/mssauvegardeloader/MSSauvegardeLoader.fxml");

		this.getController().setBackupsManager(backupsManager);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setAlwaysOnTop(true);
		this.setResizable(false);

		MSJFXUIComponentsHolder.MS_SAUVEGARDE_LOADER_STAGE = this;
	}
}
