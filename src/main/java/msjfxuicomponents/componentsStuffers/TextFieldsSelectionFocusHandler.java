package msjfxuicomponents.componentsStuffers;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TextFieldsSelectionFocusHandler {
	public void performOperation(TextField textField) {
		textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
			final int numberOfClicksForFullySelected = 3;
			int numberOfClicks = 2;
			
			
			@Override
			public void handle(MouseEvent event) {
				this.numberOfClicks++;
				
				if(this.numberOfClicks == this.numberOfClicksForFullySelected){
					textField.selectAll();
					
					this.numberOfClicks = 0;
				}
			}
		});
	}
}
