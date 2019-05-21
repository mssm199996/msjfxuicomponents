package msjfxuicomponents.mvc;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import msjfxuicomponents.MSLightweightJFXUIComponentsHolder;
import msjfxuicomponents.mvc.compositions.AbstractNestedController;
import msjfxuicomponents.mvc.compositions.ComposedController;
import msjfxuicomponents.mvc.compositions.FromAbtractionsComposedController;
import msjfxuicomponents.mvc.compositions.NestedController;

public class StageType<C extends Initializable> extends Stage {
	private C controller = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StageType(String title, String fxmlPath, Initializable controller) {
		this.onBeforeInitControllerCallback();
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath).toURI().toURL());
			fxmlLoader.setControllerFactory(type -> {
				try {
					Initializable controllerToReturn = null;

					boolean isNestedController = NestedController.class.isAssignableFrom(type);
					boolean isAbstractNestedController = AbstractNestedController.class.isAssignableFrom(type);

					if (isNestedController) {
						ComposedController parent = (ComposedController) fxmlLoader.getNamespace().get("controller");
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

					this.initEvents(controllerToReturn);

					return controllerToReturn;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});

			this.initStage(title, fxmlLoader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void initStage(String title, FXMLLoader fxmlLoader) throws IOException {
		BorderPane borderPane = (BorderPane) fxmlLoader.load();
		borderPane.getStylesheets().add("/MVC/CSS/GeneralPurposes.css");

		this.setTitle(title);
		this.setController(fxmlLoader.getController());
		this.setScene(new Scene(borderPane));
		this.getIcons().addAll(MSLightweightJFXUIComponentsHolder.WINDOW_ICON);
	}

	private void initEvents(Initializable controller) {
		if (controller instanceof SplitableController<?>) {
			this.addEventHandler(WindowEvent.WINDOW_SHOWING, event -> {
				((SplitableController<?>) controller).splitInterface();
			});
		}

		if (controller instanceof RestrictionsHolder) {
			this.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
				Platform.runLater(() -> {
					((RestrictionsHolder) controller).applyRestriction();
				});
			});
		}

		if (controller instanceof KeyStageEvent) {
			this.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
				((KeyStageEvent) controller).onKeyReleasedStageEvent(event);
			});

			this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
				((KeyStageEvent) controller).onKeyPressedStageEvent(event);
			});
		}

		if (controller instanceof PreventBeforeCloseController) {
			this.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
				if (!((PreventBeforeCloseController) controller).checkBeforeLeaving())
					event.consume();
			});
		}

		if (controller instanceof ResetController) {
			this.addEventHandler(WindowEvent.WINDOW_SHOWING, event -> {
				Platform.runLater(() -> {
					((ResetController) controller).onShowingResetResult();
				});
			});

			this.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
				Platform.runLater(() -> {
					((ResetController) controller).onShownResetResult();
				});
			});

			this.addEventHandler(WindowEvent.WINDOW_HIDING, event -> {
				((ResetController) controller).onHidingResetResul();
			});

			this.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
				((ResetController) controller).onCloseRequestResetResult();
			});
		}

		Platform.runLater(() -> {
			if (controller instanceof ColoredController)
				((ColoredController) controller).applyColor();
		});
	}

	public C getController() {
		return controller;
	}

	public void setController(C controller) {
		this.controller = controller;
	}
	
	public void onBeforeInitControllerCallback(){
	}
}
