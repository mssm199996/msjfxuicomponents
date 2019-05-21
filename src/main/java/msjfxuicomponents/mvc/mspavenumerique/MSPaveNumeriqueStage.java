package msjfxuicomponents.mvc.mspavenumerique;

import com.sun.glass.ui.Screen;

import javafx.stage.Modality;
import msjfxuicomponents.mvc.SimpleStageType;

public class MSPaveNumeriqueStage extends SimpleStageType<MSPaveNumeriqueController> {
	
	public MSPaveNumeriqueStage() {
		super("Pavé numérique", "/msjfxuicomponents/mvc/mspavenumerique/MSPaveNumerique.fxml");

		this.setOnShown(event -> {
			double screenWidth = Screen.getMainScreen().getWidth();

			double stageWidth = getWidth();

			double dx = 100;
			double dy = 100;

			double newXPos = screenWidth - stageWidth - dx;
			double newYPos = dy;

			setX(newXPos);
			setY(newYPos);
		});
		
		this.initModality(Modality.APPLICATION_MODAL);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
