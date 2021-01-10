package msjfxuicomponents.mvc.msdatabaserepair;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import msdatabaseutils.DatabaseRepairTool;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.uicomponents.MSDirectoryChooser;

public class MSDatabaseRepairController implements Initializable {

	@FXML
	private JFXTextField databaseUsername;

	@FXML
	private JFXPasswordField databasePassword;

	@FXML
	private JFXTextField databaseName;

	@FXML
	private MSDirectoryChooser mysqlFolder;

	@FXML
	private JFXButton repairButton;

	@FXML
	private ProgressIndicator repairProgress;

	private MSDatabaseRepairStage stage;

	@FXML
	void repair(ActionEvent event) {
		boolean repair = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getStage().getTitle(),
				"Réparation de la base de données",
				"Voulez vous vraiment procéder à la réparation de la base ? (Ne faites cela que si le logiciel n'arrive pas à accéder à la fênête d'accueil après que vous avez essayé de vous connecter)",
				this.getStage());

		if (repair) {
			String databaseName = this.databaseName.getText();
			String databaseUsername = this.databaseUsername.getText();
			String databasePassword = this.databasePassword.getText();
			Path mysqlFolderFile = Paths.get(this.mysqlFolder.getText());

			if (!databaseName.trim().equals("") && !databaseUsername.trim().equals("")
					&& !databasePassword.trim().equals("") && mysqlFolderFile != null) {

				if (Files.isDirectory(mysqlFolderFile)) {
					this.updateComponents(false, false);

					DatabaseRepairTool databaseRepairTool = new DatabaseRepairTool();
					databaseRepairTool.setDatabaseName(databaseName);
					databaseRepairTool.setDatabasePassword(databasePassword);
					databaseRepairTool.setDatabaseUsername(databaseUsername);
					databaseRepairTool.setMysqlFolder(mysqlFolderFile.toAbsolutePath().toString());

					(new Thread(() -> {
						try {
							databaseRepairTool.repair();

							Platform.runLater(() -> {
								this.updateComponents(true, true);
							});
						} catch (IOException | InterruptedException e) {
							e.printStackTrace();

							Platform.runLater(() -> {
								MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getStage().getTitle(),
										"R�paration de la base de donn�es",
										"Le logiciel n'a pas pu r�parer la base de donn�es", this.getStage());

								this.updateComponents(true, false);
							});
						}
					})).start();
				} else
					MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getStage().getTitle(),
							"R�paration de la base de donn�es", "Le dossier de MySQL est invalide", this.getStage());
			} else {
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getStage().getTitle(),
						"R�paration de la base de donn�es", "Les champs sont invalides", this.getStage());
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initTextFields();
	}

	private void initTextFields() {
		Platform.runLater(() -> {
			String initialMysqlDirectoryPath = this.stage.getInitialMySQLFolderPath();
			Path initialMysqlDirectory = Paths.get(initialMysqlDirectoryPath);

			this.databaseName.setText(this.stage.getInitialDatabaseName());
			this.databasePassword.setText(this.stage.getInitialDatabasePassword());
			this.databaseUsername.setText(this.stage.getInitialDatabaseUsername());

			if (initialMysqlDirectory != null && initialMysqlDirectory.toFile() != null) {
				this.mysqlFolder.setInitialDirectory(initialMysqlDirectory.toFile());
				this.mysqlFolder.setText(initialMysqlDirectoryPath);
			}
		});
	}

	private void updateComponents(boolean enableRepair, boolean repairSucceeded) {
		this.databaseName.setDisable(!enableRepair);
		this.databaseUsername.setDisable(!enableRepair);
		this.databasePassword.setDisable(!enableRepair);
		this.mysqlFolder.setDisable(!enableRepair);
		this.repairButton.setDisable(!enableRepair);

		if (enableRepair) {
			if (repairSucceeded)
				this.repairProgress.setProgress(1.0);
			else
				this.repairProgress.setProgress(0.0);
		} else
			this.repairProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
	}

	public Stage getStage() {
		return this.stage;
	}

	public void setStage(MSDatabaseRepairStage stage) {
		this.stage = stage;
	}
}
