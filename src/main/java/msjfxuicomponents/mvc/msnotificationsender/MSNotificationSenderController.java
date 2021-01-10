package msjfxuicomponents.mvc.msnotificationsender;

import java.net.SocketException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.ResetController;
import msjfxuicomponents.others.SoundsPlayer;
import mssoftutils.network.MSClient;
import mssoftutils.network.MSServer;
import mssoftutils.network.RemoteMachine;

public class MSNotificationSenderController implements Initializable, ResetController {

	public static int NOTIFICATIONS_FRENQUENCY = 10_000;

	private long lastNotification = 0;
	private boolean isScanning = false;
	private String notificationRequest = "PLAY_NOTIFICATION";

	private ObservableList<RemoteMachine> remoteMachinesList = FXCollections.observableList(new LinkedList<>());

	private SoundsPlayer soundsPlayer = new SoundsPlayer();

	private MSServer notificationsServer;
	private MSClient notificationsClient;
	private MSClient notifiarsDiscover;

	@FXML
	private JFXComboBox<RemoteMachine> remoteMachines;

	@FXML
	private JFXButton discoveryButton, confirmButton;

	@FXML
	private JFXProgressBar progressBar;

	@FXML
	void annuler(ActionEvent event) {
		this.getWindow().hide();
	}

	@FXML
	void discoverFriends() {
		String image;
		double progress;
		ImageView imageView = (ImageView) this.discoveryButton.getGraphic();

		if (this.isScanning) {
			progress = 1.0;
			image = "/msjfxuicomponents/mvc/icons/reset.png";

			this.notifiarsDiscover.stopScanning();
		} else {
			progress = -1.0;
			image = "/msjfxuicomponents/mvc/icons/stop.png";

			this.remoteMachines.getItems().clear();
			this.notifiarsDiscover.scanNetwork();
		}

		imageView.setImage(new Image(getClass().getResource(image).toString()));

		this.progressBar.setProgress(progress);
		this.remoteMachines.setDisable(!this.isScanning);
		this.isScanning = !this.isScanning;
		this.confirmButton.setDisable(this.isScanning);
	}

	@FXML
	void confirmer(ActionEvent event) {
		RemoteMachine remoteMachine = this.remoteMachines.getSelectionModel().getSelectedItem();

		if (remoteMachine != null) {
			long dt = System.currentTimeMillis() - this.lastNotification
					- MSNotificationSenderController.NOTIFICATIONS_FRENQUENCY;

			if (dt >= 0) {
				this.lastNotification = System.currentTimeMillis();
				this.notificationsClient.sendRequest(this.notificationRequest, remoteMachine);

				this.getWindow().hide();
			}

			else
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Gestion des notifications",
						"Nouvelle notification",
						"Vous devez attendre " + (-dt) + " avant de relancer une notification !", this.getWindow());
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Gestion des notifications",
					"Nouvelle notification", "Veuillez d'abords selectionner un correspondant", this.getWindow());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initComboBoxes();
		this.initNetworkUtilities();
	}

	private void initComboBoxes() {
		this.remoteMachines.setItems(this.remoteMachinesList);
	}

	private void initNetworkUtilities() {
		try {
			this.notificationsServer = new MSServer(56489);
			this.notificationsClient = new MSClient(56489) {

				@Override
				public void onScanStarted() {
				}

				@Override
				public void onScanFinished() {
				}

				@Override
				public void onRemoteMachineFound(RemoteMachine remoteMachine) {
				}
			};

			this.notifiarsDiscover = new MSClient(56489) {

				@Override
				public void onScanStarted() {
				}

				@Override
				public void onScanFinished() {
				}

				@Override
				public void onRemoteMachineFound(RemoteMachine remoteMachine) {
					remoteMachines.getItems().add(remoteMachine);
				}
			};
		} catch (SocketException e) {
			e.printStackTrace();
		}

		this.notificationsServer.addHandler(this.notificationRequest, () -> {
			this.soundsPlayer.ring(MSNotificationSenderController.NOTIFICATIONS_FRENQUENCY / 1_000);
		});
	}

	@Override
	public void onShowingResetResult() {
		this.remoteMachines.getItems().clear();
	}

	public Window getWindow() {
		return this.remoteMachines.getScene().getWindow();
	}

	public void setSignal(Circle signal) {
		this.soundsPlayer.setSignal(signal);
	}
}
