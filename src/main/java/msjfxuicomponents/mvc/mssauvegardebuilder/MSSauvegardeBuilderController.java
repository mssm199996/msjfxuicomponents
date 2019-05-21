package msjfxuicomponents.mvc.mssauvegardebuilder;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import msdatabaseutils.BackupsManager;
import msjfxuicomponents.mvc.MSSauvegardeController;
import msjfxuicomponents.mvc.ResetController;
import msjfxuicomponents.uicomponents.MSDesktopNavigator;
import msjfxuicomponents.uicomponents.MSDirectoryChooser;

public class MSSauvegardeBuilderController extends MSSauvegardeController implements ResetController {

	@FXML
	private MSDirectoryChooser directoryChooser;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		this.initDirectoryChooser();
	}

	private void initDirectoryChooser() {
		this.directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
	}

	public void onShowingResetResult() {
		this.directoryChooser.setText(System.getProperty("user.home"));
	}

	@Override
	public MSDesktopNavigator getDesktopNavigator() {
		return this.directoryChooser;
	}

	@Override
	public void operate(BackupsManager backupsManager, Path path) {
		String filePath = path.toAbsolutePath().toString() + File.separator
				+ LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "@"
				+ LocalTime.now().format(DateTimeFormatter.ofPattern("hh-mm-ss")) + ".mssmsql";

		backupsManager.createBackup(Paths.get(filePath));
	}

	@Override
	public String getDesktopNavigatorTitle() {
		return "Création d'une nouvelle sauvegarde";
	}

	@Override
	public String getDialogTitle() {
		return "Création d'une nouvelle sauvegarde";
	}

	@Override
	public String getDialogCheminInvalide() {
		return "Veuillez selectionner un repertoire valide !";
	}

	@Override
	public boolean getAdditionalPathConditions(Path path) {
		return Files.isDirectory(path);
	}
}
