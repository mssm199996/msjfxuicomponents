package msjfxuicomponents.mvc.msnotificationsender;

import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import msjfxuicomponents.mvc.SimpleStageType;

public class MSNotificationSenderStage extends SimpleStageType<MSNotificationSenderController> {

	public MSNotificationSenderStage(String title) {
		super(title, "/msjfxuicomponents/mvc/msnotificationsender/MSNotificationSender.fxml");
		
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.initModality(Modality.APPLICATION_MODAL);
	}

	public void setSignal(Circle signal) {
		this.getController().setSignal(signal);
	}
}
