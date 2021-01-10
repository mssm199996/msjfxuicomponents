package msjfxuicomponents.componentsStuffers;

import java.io.File;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class TextFieldFileChooser {
	private FileChooser fileChooser = new FileChooser();

	public TextFieldFileChooser(String title){
		this.fileChooser.setTitle(title);
	}
	
	public void fromTextFieldToImagesFileChooser(TextField textField){
		textField.setOnMouseClicked(event -> {
			fileChooser.setSelectedExtensionFilter(new ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg"));
						
			File image = this.fileChooser.showOpenDialog(textField.getScene().getWindow());

			if (image != null)
				textField.setText(image.getAbsolutePath());
			else textField.setText("");		
		});
	}
}
