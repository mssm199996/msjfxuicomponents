package msjfxuicomponents.mvc.msdatabaserepair;

import javafx.stage.Modality;
import msjfxuicomponents.MSHibernateBasedConfigurationStage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.SimpleStageType;

public abstract class MSDatabaseRepairStage extends SimpleStageType<MSDatabaseRepairController>
		implements MSHibernateBasedConfigurationStage {

	public MSDatabaseRepairStage(String stageTitle) {
		super(stageTitle, "/msjfxuicomponents/mvc/msdatabaserepair/MSDatabaseRepair.fxml");

		this.getController().setStage(this);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setAlwaysOnTop(true);

		MSJFXUIComponentsHolder.MS_DATABASE_REPAIR_STAGE = this;
	}

	public abstract String getInitialMySQLFolderPath();
}
