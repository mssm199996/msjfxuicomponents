package msjfxuicomponents.mvc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.function.Consumer;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import msjfxuicomponents.mvc.compositions.AbstractNestedController;
import msjfxuicomponents.mvc.compositions.ComposedController;
import msjfxuicomponents.mvc.compositions.FromAbtractionsComposedController;
import msjfxuicomponents.mvc.compositions.NestedController;

public class MSFXMLLoader<C extends Initializable> {

	private FXMLLoader fxmlLoader;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MSFXMLLoader(String fxmlPath, C controller, Consumer<Initializable> onControllerLoadedCallback) {
		try {
			this.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath).toURI().toURL());
			this.fxmlLoader.setControllerFactory(type -> {
				try {
					Initializable controllerToReturn = null;

					boolean isNestedController = NestedController.class.isAssignableFrom(type);
					boolean isAbstractNestedController = AbstractNestedController.class.isAssignableFrom(type);

					if (isNestedController) {
						ComposedController parent = (ComposedController) this.fxmlLoader.getNamespace()
								.get("controller");
						NestedController child = null;

						if (isAbstractNestedController)
							child = ((FromAbtractionsComposedController) parent).nestedComponentsControllers()
									.get(type.getSimpleName());
						else
							child = (NestedController) type.newInstance();

						child.setParent(parent);

						controllerToReturn = (Initializable) child;
					} else if (controller == null)
						controllerToReturn = (Initializable) type.newInstance();
					else
						controllerToReturn = controller;

					if (onControllerLoadedCallback != null)
						onControllerLoadedCallback.accept(controllerToReturn);

					return controllerToReturn;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
		} catch (MalformedURLException | URISyntaxException e1) {
			e1.printStackTrace();
		}
	}

	public BorderPane loadBorderPane() {
		BorderPane borderPane = null;

		try {
			borderPane = (BorderPane) (BorderPane) this.fxmlLoader.load();
			borderPane.getStylesheets().add("/MVC/CSS/GeneralPurposes.css");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return borderPane;
	}

	public Initializable getController() {
		return this.fxmlLoader.getController();
	}
}
