package msjfxuicomponents.uicomponents;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MSFileChooser extends MSDesktopNavigator {

	private FileChooser fileChooser = new FileChooser();

	public void addExtensionFilter(ExtensionFilter extensionFilter) {
		this.fileChooser.getExtensionFilters().add(extensionFilter);
	}

	@Override
	public void setTitle(String title) {
		this.fileChooser.setTitle(title);
	}

	@Override
	public void setInitialDirectory(File file) {
		this.fileChooser.setInitialDirectory(file);
	}

	@Override
	public String getSuffix() {
		return "";
	}

	@Override
	public File getSelectedFile() {
		return this.fileChooser.showOpenDialog(this.getScene().getWindow());
	}
}
