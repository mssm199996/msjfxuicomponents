package msjfxuicomponents.mvc.networkscanner;

import javafx.stage.Modality;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.SimpleStageType;
import mssoftutils.network.RemoteMachine;

public abstract class MSNetworkScannerStage extends SimpleStageType<MSNetworkScannerController> {

	public MSNetworkScannerStage(String stageTitle) {
		super(stageTitle, "/msjfxuicomponents/mvc/networkscanner/MSNetworkScanner.fxml");

		this.getController().setStage(this);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setAlwaysOnTop(true);

		MSJFXUIComponentsHolder.MS_NETWOR_SCANNER_STAGE = this;
	}

	public abstract void onRemoteMachineSelected(RemoteMachine remoteMachine);

	public abstract String getInitialDatabaseUsername();

	public abstract String getInitialDatabasePassword();

	public abstract String getInitialServerPort();

	public String getDatabaseUsername() {
		return this.getController().getDatabaseUsername();
	}

	public String getDatabasePassword() {
		return this.getController().getDatabasePassword();
	}

	public String getPort() {
		return this.getController().getServerPort();
	}
}
