package msjfxuicomponents.mvc.networkscanner;

import java.net.SocketException;

import javafx.stage.Modality;
import msjfxuicomponents.MSHibernateBasedConfigurationStage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.SimpleStageType;
import mssoftutils.network.RemoteMachine;

public abstract class MSNetworkScannerStage extends SimpleStageType<MSNetworkScannerController>
		implements MSHibernateBasedConfigurationStage {

	public MSNetworkScannerStage(String stageTitle) {
		super(stageTitle, "/msjfxuicomponents/mvc/networkscanner/MSNetworkScanner.fxml");

		this.getController().setStage(this);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setAlwaysOnTop(true);

		MSJFXUIComponentsHolder.MS_NETWOR_SCANNER_STAGE = this;
	}

	public void initServer() throws SocketException {
		this.getController().initServer();
	}

	public abstract void onRemoteMachineSelected(RemoteMachine remoteMachine);

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
