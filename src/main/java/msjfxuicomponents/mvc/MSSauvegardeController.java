package msjfxuicomponents.mvc;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Window;
import msdatabaseutils.BackupsManager;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.uicomponents.MSDesktopNavigator;

public abstract class MSSauvegardeController implements Initializable {

	@FXML
	private ProgressIndicator progress;

	private BackupsManager backupsManager;

	@FXML
	void cancel() {
		this.getWindow().hide();
	}

	@FXML
	void confirm() {
		Path path = this.getDesktopNavigator().getChoosenFileOrDirectory();

		if (path != null && this.getAdditionalPathConditions(path)) {
			this.progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

			(new Thread(() -> {
				this.operate(this.backupsManager, path);

				Platform.runLater(() -> {
					this.progress.setProgress(1.0);
				});
			})).start();
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getDialogTitle(),
					"Erreur... chemin invalide", this.getDialogCheminInvalide(), this.getWindow());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initDesktopNavigator();
	}

	private void initDesktopNavigator() {
		this.getDesktopNavigator().setInitialDirectory(this.getDesktopNavigatorInitialFile());
		this.getDesktopNavigator().setTitle(this.getDesktopNavigatorTitle());
	}

	public BackupsManager getBackupsManager() {
		return backupsManager;
	}

	public void setBackupsManager(BackupsManager backupsManager) {
		this.backupsManager = backupsManager;
	}

	public Window getWindow() {
		return this.progress.getScene().getWindow();
	}

	public abstract MSDesktopNavigator getDesktopNavigator();

	public abstract void operate(BackupsManager backupsManager, Path path);

	public abstract String getDesktopNavigatorTitle();

	public abstract String getDialogTitle();

	public abstract String getDialogCheminInvalide();

	public abstract boolean getAdditionalPathConditions(Path path);

	protected File getDesktopNavigatorInitialFile() {
		File file = new File("Sauvegardes");

		return file.exists() ? file : this.getDesktopFile();
	}

	protected File getDesktopFile() {
		File file = new File(System.getProperty("user.home") + "/Desktop");

		return file.exists() ? file : null;
	}
}
