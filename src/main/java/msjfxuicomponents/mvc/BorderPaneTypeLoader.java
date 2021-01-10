package msjfxuicomponents.mvc;

import java.util.function.Consumer;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class BorderPaneTypeLoader {

	public static <C extends Initializable> BorderPane loadBorderPane(String fxmlPath) {
		return BorderPaneTypeLoader.loadBorderPane(fxmlPath, null, null);
	}

	public static <C extends Initializable> BorderPane loadBorderPane(String fxmlPath,
			Consumer<Initializable> onControllerConstructedConsumer) {
		return BorderPaneTypeLoader.loadBorderPane(fxmlPath, null, onControllerConstructedConsumer);
	}

	public static <C extends Initializable> BorderPane loadBorderPane(String fxmlPath, C controller,
			Consumer<Initializable> onControllerConstructedConsumer) {
		MSFXMLLoader<C> loader = new MSFXMLLoader<C>(fxmlPath, controller, onControllerConstructedConsumer);

		return loader.loadBorderPane();
	}
}
