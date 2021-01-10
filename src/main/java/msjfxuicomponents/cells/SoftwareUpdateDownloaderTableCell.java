package msjfxuicomponents.cells;

import java.io.IOException;

import com.dropbox.core.DbxException;

import javafx.application.Platform;
import javafx.stage.Stage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.MSLightweightJFXUIComponentsHolder;
import mssoftutils.update.SoftwareUpdateDownloader;
import mssoftutils.update.SoftwareVersion;

public class SoftwareUpdateDownloaderTableCell extends OneButtonProgressBarTableCell<SoftwareVersion> {

	private SoftwareUpdateDownloader softwareUpdateDownloader;
	private Stage stage;

	public SoftwareUpdateDownloaderTableCell(Stage stage, SoftwareUpdateDownloader softwareUpdateDownloader) {
		super(MSLightweightJFXUIComponentsHolder.RIGHT_GREEN_ARROW_ICON, "Télécharger", 10_000);

		this.softwareUpdateDownloader = softwareUpdateDownloader;
		this.stage = stage;
	}

	@Override
	public boolean onPerformTask(SoftwareVersion softwareVersion) {
		try {
			double maxProgress = this.softwareUpdateDownloader.getFolderSize(softwareVersion);

			this.softwareUpdateDownloader.downloadAndStore(softwareVersion, e -> {
				double percentage = e / maxProgress;
				
				Platform.runLater(() -> {
					this.onDrawProgress(percentage, false);
				});
			});

			Platform.runLater(() -> {
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayInformationAlert(this.stage.getTitle(),
						"Téléchargement de la version " + softwareVersion.getVersion(),
						"Mise à jour téléchargée avec succés. Veuillez cliquer sur le boutton Installer pour installer la mise à jour.",
						this.stage);
			});

			return true;
		} catch (IOException | DbxException e) {
			e.printStackTrace();

			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.stage.getTitle(),
					"Téléchargement de la version " + softwareVersion.getVersion(),
					"Erreur serveur injoignable ou mise à jour inéxistante. Veuillez réessayer plus tard.", this.stage);

			return false;
		}
	}

	@Override
	public void onAfterClicked(SoftwareVersion entity) {
	}
}
