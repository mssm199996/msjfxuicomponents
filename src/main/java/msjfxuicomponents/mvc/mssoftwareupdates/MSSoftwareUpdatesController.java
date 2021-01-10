package msjfxuicomponents.mvc.mssoftwareupdates;

import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.cells.IndexCell;
import msjfxuicomponents.cells.SoftwareUpdateDownloaderTableCell;
import msjfxuicomponents.cells.SoftwareUpdateInstallerTableCell;
import msjfxuicomponents.cells.WellFormattedLocalDateCell;
import msjfxuicomponents.cells.WellFormattedLocalDateTimeCell;
import mssoftutils.update.SoftwareUpdateDownloader;
import mssoftutils.update.SoftwareUpdateInstaller;
import mssoftutils.update.SoftwareVersion;
import mssoftutils.update.SoftwareVersionDownloader;
import mssoftutils.update.SoftwareVersionDownloader.Software;

public class MSSoftwareUpdatesController implements Initializable {

	private SoftwareUpdateDownloader softwareUpdateDownloader;
	private SoftwareUpdateInstaller softwareUpdateInstaller;
	private SoftwareVersionDownloader softwareVersionDownloader;

	@FXML
	private TableView<SoftwareVersion> tableVersions;

	@FXML
	private TableColumn<SoftwareVersion, Integer> indexColumn;

	@FXML
	private TableColumn<SoftwareVersion, String> fileUrlColumn;

	@FXML
	private TableColumn<SoftwareVersion, String> versionColumn;

	@FXML
	private TableColumn<SoftwareVersion, LocalDate> creationDate;

	@FXML
	private TableColumn<SoftwareVersion, LocalDateTime> uploadDate;

	@FXML
	private TableColumn<SoftwareVersion, Boolean> downloadColumn;

	@FXML
	private TableColumn<SoftwareVersion, Boolean> installColumn;

	@FXML
	private JFXTextArea versionDescriptionContainer;

	@FXML
	private ProgressIndicator downloadProgressIndicator;

	@FXML
	private BorderPane mainContainer;

	private Software software = null;

	@FXML
	void downloadSoftwareVersions(ActionEvent event) {
		this.mainContainer.setDisable(true);
		this.downloadProgressIndicator.setVisible(true);

		(new Thread(() -> {
			try {
				List<SoftwareVersion> softwareVersions = this.softwareVersionDownloader
						.downloadSoftwareVersions(this.software);

				Platform.runLater(() -> {
					this.tableVersions.getItems().clear();
					this.tableVersions.getItems().addAll(softwareVersions);
				});
			} catch (Exception e) {
				e.printStackTrace();

				Platform.runLater(() -> {
					MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getWindow().getTitle(),
							"Téléchargement des versions disponibles",
							"Impossible de joindre le serveur distant, veuillez réessayer ultérieurement.",
							this.getWindow());
				});
			}

			Platform.runLater(() -> {
				this.mainContainer.setDisable(false);
				this.downloadProgressIndicator.setVisible(false);
			});
		})).start();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initTables();
	}

	private void initTables() {
		this.indexColumn.setCellFactory(call -> new IndexCell<>());

		this.fileUrlColumn.setCellValueFactory(call -> new SimpleStringProperty(call.getValue().getFileUrl()));
		this.versionColumn.setCellValueFactory(call -> new SimpleStringProperty(call.getValue().getVersion()));

		this.creationDate.setCellFactory(call -> new WellFormattedLocalDateCell<>());
		this.creationDate.setCellValueFactory(call -> new SimpleObjectProperty<>(call.getValue().getCreatedAt()));

		this.uploadDate.setCellFactory(call -> new WellFormattedLocalDateTimeCell<>());
		this.uploadDate.setCellValueFactory(call -> new SimpleObjectProperty<>(call.getValue().getHostedAt()));

		this.downloadColumn.setCellValueFactory(call -> new SimpleBooleanProperty(true));
		this.downloadColumn.setCellFactory(
				call -> new SoftwareUpdateDownloaderTableCell(this.getWindow(), this.softwareUpdateDownloader));

		this.installColumn.setCellValueFactory(call -> new SimpleBooleanProperty(true));
		this.installColumn.setCellFactory(
				call -> new SoftwareUpdateInstallerTableCell(this.getWindow(), this.softwareUpdateInstaller));

		this.tableVersions.getSelectionModel().selectedItemProperty().addListener((__, ___, newValue) -> {
			this.versionDescriptionContainer.setText("");

			if (newValue != null)
				this.versionDescriptionContainer.setText(newValue.getDescription());
		});
	}

	private static URI getBaseURI() {
		return URI.create("https://ms-software-utils.herokuapp.com");
	}

	public void setSoftware(Software software) {
		this.software = software;
	}

	public Software getSoftware() {
		return this.software;
	}

	public void initSoftwareUpdateComponents() {
		this.softwareUpdateDownloader = new SoftwareUpdateDownloader(this.getSoftware());
		this.softwareUpdateInstaller = new SoftwareUpdateInstaller();
		this.softwareVersionDownloader = new SoftwareVersionDownloader(MSSoftwareUpdatesController.getBaseURI());
	}

	public Stage getWindow() {
		return (Stage) this.tableVersions.getScene().getWindow();
	}
}
