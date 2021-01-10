package msjfxuicomponents.mvc.mspavenumerique;

import com.sun.glass.ui.Screen;

import javafx.stage.Modality;
import javafx.stage.Window;
import msjfxuicomponents.mvc.SimpleStageType;

public class MSPaveNumeriqueStage<T extends MSPaveNumeriqueController> extends SimpleStageType<T> {

	public MSPaveNumeriqueStage(Window mother, String title, String controllerPath) {
		super(title, controllerPath);

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

		this.initOwner(mother);
		this.initModality(Modality.WINDOW_MODAL);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}

	public void setHeader(String header) {
		this.getController().setHeaderValue(header);
	}

	public void setInitialValue(String initalValue) {
		this.getController().setInitialValue(initalValue);
	}

	public boolean isConfirmed() {
		return this.getController().isConfirmed();
	}

	public Double getResultValue() {
		return this.getController().getResultValue();
	}
}
