package msjfxuicomponents.uicomponents;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.control.TextField;

public abstract class MSDesktopNavigator extends TextField {

	public MSDesktopNavigator() {
		this.initDirectoryChooser();
	}

	private void initDirectoryChooser() {
		this.setEditable(false);
		this.setOnMouseClicked(event -> {
			File file = this.getSelectedFile();

			if (file != null)
				setText(file.getAbsolutePath());
		});
	}

	public Path getChoosenFileOrDirectory() {
		if (this.getText() != null && !this.getText().equals(""))
			return Paths.get(this.getText());

		return null;
	}

	public String getChoosenFileOrDirectoryValue() {
		Path result = this.getChoosenFileOrDirectory();

		if (result == null)
			return null;

		return result.toString() + this.getSuffix();
	}

	public abstract String getSuffix();

	public abstract File getSelectedFile();

	public abstract void setTitle(String title);

	public abstract void setInitialDirectory(File file);
}
