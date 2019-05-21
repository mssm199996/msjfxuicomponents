package msjfxuicomponents.mvc.networkscanner;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import msjfxuicomponents.cells.IndexCell;
import msjfxuicomponents.rows.DoubleClickingRow;
import mssoftutils.network.MSClient;
import mssoftutils.network.MSServer;
import mssoftutils.network.RemoteMachine;

public class MSNetworkScannerController implements Initializable {

	@FXML
	private JFXButton confirmButton;

	@FXML
	private JFXButton scanButton;

	@FXML
	private JFXButton stopButton;

	@FXML
	private ProgressIndicator progressIndicator;

	@FXML
	private TableView<RemoteMachine> remoteMachinesTable;

	@FXML
	private TableColumn<RemoteMachine, Integer> indexColumn;

	@FXML
	private TableColumn<RemoteMachine, String> ipAdressColumn;

	@FXML
	private TableColumn<RemoteMachine, String> hostNameColumn;

	@FXML
	private JFXTextField databaseUsername, port;

	@FXML
	private JFXPasswordField databasePassword;

	private MSNetworkScannerStage stage = null;
	private MSClient msclient = null;

	@FXML
	void confirmHandler() {
		RemoteMachine remoteMachine = this.remoteMachinesTable.getSelectionModel().getSelectedItem();

		if (remoteMachine != null) {
			this.getWindow().onRemoteMachineSelected(remoteMachine);
			this.getWindow().hide();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(this.getWindow().getTitle());
			alert.setHeaderText("Modification du serveur");
			alert.setContentText("Veuillez d'abords choisir une machine !");
			alert.initOwner(this.getWindow());
			alert.show();
		}
	}

	@FXML
	void scanNetwork() {
		this.msclient.scanNetwork();
	}

	@FXML
	void stopScan() {
		this.msclient.stopScanning();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initTables();
		this.initVariables();
		this.initTextFields();
		this.initServer();
	}

	private void initServer() {
		new MSServer();
	}

	private void initVariables() {
		this.msclient = new MSClient() {
			@Override
			public void onScanStarted() {
				Platform.runLater(() -> {
					RemoteMachine remoteMachine = new RemoteMachine();
					remoteMachine.setHostName("La machine courrante");
					remoteMachine.setIpAddress("localhost");

					remoteMachinesTable.getItems().clear();
					remoteMachinesTable.getItems().add(remoteMachine);

					updateInterfaceAvailability(false);
				});
			}

			@Override
			public void onScanFinished() {
				Platform.runLater(() -> {
					updateInterfaceAvailability(true);
				});
			}

			@Override
			public void onRemoteMachineFound(RemoteMachine remoteMachine) {
				Platform.runLater(() -> {
					if (!remoteMachinesTable.getItems().contains(remoteMachine))
						remoteMachinesTable.getItems().add(remoteMachine);
				});
			}
		};
	}

	private void initTextFields() {
		Platform.runLater(() -> {
			this.databaseUsername.setText(this.getWindow().getInitialDatabaseUsername());
			this.databasePassword.setText(this.getWindow().getInitialDatabasePassword());
			this.port.setText(this.getWindow().getInitialServerPort());
		});
	}

	private void initTables() {
		this.remoteMachinesTable.setRowFactory(call -> {
			return new DoubleClickingRow<RemoteMachine>() {
				@Override
				public void onMouseDoubleClicked(RemoteMachine remoteMachine) {
					getWindow().onRemoteMachineSelected(remoteMachine);

					Platform.runLater(() -> {
						getWindow().hide();
					});
				}
			};
		});

		this.indexColumn.setCellFactory(call -> {
			return new IndexCell<RemoteMachine>();
		});

		this.ipAdressColumn.setCellValueFactory(call -> {
			return call.getValue().ipAddressProperty();
		});

		this.hostNameColumn.setCellValueFactory(call -> {
			return call.getValue().hostNameProperty();
		});
	}

	private void updateInterfaceAvailability(boolean isAvailable) {
		this.stopButton.setDisable(isAvailable);
		this.scanButton.setDisable(!isAvailable);
		this.confirmButton.setDisable(!isAvailable);
		this.remoteMachinesTable.setDisable(!isAvailable);
		this.progressIndicator.setProgress(isAvailable ? 1.0 : -1.0);
	}

	public void setStage(MSNetworkScannerStage stage) {
		this.stage = stage;
	}

	public MSNetworkScannerStage getWindow() {
		return this.stage;
	}

	public String getDatabaseUsername() {
		return this.databaseUsername.getText();
	}

	public String getDatabasePassword() {
		return this.databasePassword.getText();
	}

	public String getServerPort() {
		return this.port.getText();
	}
}