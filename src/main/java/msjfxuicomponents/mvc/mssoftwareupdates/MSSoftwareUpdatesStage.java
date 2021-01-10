package msjfxuicomponents.mvc.mssoftwareupdates;

import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.SimpleStageType;
import mssoftutils.update.SoftwareVersionDownloader.Software;

public class MSSoftwareUpdatesStage extends SimpleStageType<MSSoftwareUpdatesController> {

	public MSSoftwareUpdatesStage(String title, Software software) {
		super(title, "/msjfxuicomponents/mvc/mssoftwareupdates/MSSoftwareUpdates.fxml");

		this.setMaximized(true);
		this.getController().setSoftware(software);
		this.getController().initSoftwareUpdateComponents();

		MSJFXUIComponentsHolder.MS_SOFTWARE_UPDATES_STAGE = this;
	}
}