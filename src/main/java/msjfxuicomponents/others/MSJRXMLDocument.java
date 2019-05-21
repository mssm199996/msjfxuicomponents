package msjfxuicomponents.others;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.beans.property.SimpleStringProperty;

public class MSJRXMLDocument {

	public MSJRXMLDocument() {
		this("NO_NAME", "NO_PATH", "NO_RECOVERY_PATH");
	}

	public MSJRXMLDocument(String name, String path) {
		this(name, path, "");

		int firstIndexOfSlash = path.indexOf('/');

		String pathFolder = path.substring(0, firstIndexOfSlash);
		String fileName = path.substring(firstIndexOfSlash + 1);
		String recoveryFile = pathFolder + "/recoveryFolder/" + fileName;

		this.setRecoveryPath(recoveryFile);
	}

	public MSJRXMLDocument(String name, String path, String recoveryPath) {
		Path filePath = Paths.get(path);
		
		this.setName(name);
		this.setPath(path);
		this.setRecoveryPath(recoveryPath);
		this.setPrintablePath(filePath.toAbsolutePath().toString());
	}

	private SimpleStringProperty name = new SimpleStringProperty(""), path = new SimpleStringProperty(""),
			recoveryPath = new SimpleStringProperty(""), printablePath = new SimpleStringProperty("");

	public String getName() {
		return name.getValue();
	}

	public void setName(String name) {
		this.name.setValue(name);
	}

	public SimpleStringProperty nameProperty() {
		return this.name;
	}

	public String getPath() {
		return path.getValue();
	}

	public void setPath(String path) {
		this.path.setValue(path);
	}

	public SimpleStringProperty pathProperty() {
		return this.path;
	}

	public String getRecoveryPath() {
		return recoveryPath.getValue();
	}

	public void setRecoveryPath(String recoveryPath) {
		this.recoveryPath.setValue(recoveryPath);
	}

	public SimpleStringProperty recoveryPathProperty() {
		return this.recoveryPath;
	}

	public String getPrintablePath() {
		return printablePath.getValue();
	}

	public void setPrintablePath(String printablePath) {
		this.printablePath.setValue(printablePath);
	}

	public SimpleStringProperty printablePathProperty() {
		return this.printablePath;
	}
}
