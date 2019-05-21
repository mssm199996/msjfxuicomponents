package msjfxuicomponents.uicomponents;

import java.io.File;

import javafx.stage.DirectoryChooser;

public class MSDirectoryChooser extends MSDesktopNavigator {

	private DirectoryChooser directoryChooser = new DirectoryChooser();

	@Override
	public void setTitle(String title) {
		this.directoryChooser.setTitle(title);
	}

	@Override
	public void setInitialDirectory(File file) {
		this.directoryChooser.setInitialDirectory(file);
	}

	@Override
	public String getSuffix() {
		return "\\";
	}

	@Override
	public File getSelectedFile() {
		return this.directoryChooser.showDialog(this.getScene().getWindow());
	}
}
