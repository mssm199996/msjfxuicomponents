package msjfxuicomponents.cells;

import java.io.IOException;

import javafx.application.Platform;
import javafx.stage.Stage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.MSLightweightJFXUIComponentsHolder;
import mssoftutils.update.SoftwareUpdateInstaller;
import mssoftutils.update.SoftwareVersion;

public class SoftwareUpdateInstallerTableCell extends OneButtonProgressIndicatorTableCell<SoftwareVersion> {

	private SoftwareUpdateInstaller softwareUpdateInstaller;
	private Stage stage;

	public SoftwareUpdateInstallerTableCell(Stage stage, SoftwareUpdateInstaller softwareUpdateInstaller) {
		super(MSLightweightJFXUIComponentsHolder.RIGHT_RED_ARROW_ICON, "Installer", 0);

		this.softwareUpdateInstaller = softwareUpdateInstaller;
		this.stage = stage;
	}

	@Override
	public boolean onPerformTask(SoftwareVersion softwareVersion) {
		try {
			this.softwareUpdateInstaller.install(softwareVersion);

			Platform.runLater(() -> {
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayInformationAlert(this.stage.getTitle(),
						"Installation de la version " + softwareVersion.getVersion(),
						"Installation faite avec succés. Le programme va s'éteindre automatiquement aprés la confirmation. Veuillez redémarrer le programme pour bénéficier des changements.",
						this.stage);

				System.exit(0);
			});

			return true;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();

			Platform.runLater(() -> {
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.stage.getTitle(),
						"Installation de la version " + softwareVersion.getVersion(),
						"Erreur mise à jour introuvable. Veuillez d'abords télécharger la mise à jour.", this.stage);
			});

			return false;
		}
	}

	@Override
	public void onAfterClicked(SoftwareVersion entity) {
	}
}
