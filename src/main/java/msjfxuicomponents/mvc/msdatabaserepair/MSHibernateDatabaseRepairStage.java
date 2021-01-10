package msjfxuicomponents.mvc.msdatabaserepair;

public class MSHibernateDatabaseRepairStage extends MSDatabaseRepairStage {

	public MSHibernateDatabaseRepairStage(String stageTitle) {
		super(stageTitle);
	}

	@Override
	public String getInitialMySQLFolderPath() {
		return "C:/Program Files/MySQL/MySQL Server 5.7";
	}
}
