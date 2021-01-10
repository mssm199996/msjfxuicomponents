package msjfxuicomponents.mvc.mssauvegardeloader;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.stage.FileChooser.ExtensionFilter;
import msdatabaseutils.BackupsManager;
import msjfxuicomponents.mvc.MSSauvegardeController;
import msjfxuicomponents.uicomponents.MSDesktopNavigator;
import msjfxuicomponents.uicomponents.MSFileChooser;

public class MSSauvegardeLoaderController extends MSSauvegardeController {

	@FXML
	private MSFileChooser fileChooser;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		this.initFileChoosers();
	}

	private void initFileChoosers() {
		this.fileChooser.addExtensionFilter(new ExtensionFilter("MSMarket SQL", "*.mssmsql"));
	}

	@Override
	public MSDesktopNavigator getDesktopNavigator() {
		return this.fileChooser;
	}

	@Override
	public void operate(BackupsManager backupsManager, Path path) {
		backupsManager.loadBackup(path);
	}

	@Override
	public String getDesktopNavigatorTitle() {
		return "Chargement d'une nouvelle sauvegarde";
	}

	@Override
	public String getDialogTitle() {
		return "Chargement d'une nouvelle sauvegarde";
	}

	@Override
	public String getDialogCheminInvalide() {
		return "Veuillez selectionner un fichier valide !";
	}

	@Override
	public boolean getAdditionalPathConditions(Path path) {
		return true;
	}
}
