package msjfxuicomponents.mvc.mssauvegardebuilder;

import javafx.stage.Modality;
import msdatabaseutils.BackupsManager;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.SimpleStageType;

public class MSSauvegardeBuilderStage extends SimpleStageType<MSSauvegardeBuilderController> {

	public MSSauvegardeBuilderStage(String title, BackupsManager backupsManager) {
		super(title, "/msjfxuicomponents/mvc/mssauvegardebuilder/MSSauvegardeBuilder.fxml");

		this.getController().setBackupsManager(backupsManager);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		
		MSJFXUIComponentsHolder.MS_SAUVEGARDE_BUILDER_STAGE = this;
	}
}
