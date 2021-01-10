package msjfxuicomponents.mvc;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import msjfxuicomponents.MSComponentsHolder;
import msjfxuicomponents.MSLightweightComponentsHolder;
import msjfxuicomponents.MSLightweightJFXUIComponentsHolder;
import msjfxuicomponents.componentsStuffers.ScalableContentPane;
import msjfxuicomponents.models.UIComponentParam;

public class StageType<C extends Initializable> extends Stage {
	private C controller = null;
	private String stageId;
	private Map<String, UIComponentParam> uiComponentParams = null;

	public StageType(String title, String fxmlPath, C controller) {
		this.stageId = this.getClass().getSimpleName()
				+ (this.getStageIndex().length() > 0 ? "_" + this.getStageIndex() : "");

		this.onBeforeInitControllerCallback();

		try {
			this.initStage(title, fxmlPath, controller);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void initStage(String title, String fxmlPath, C controller) throws IOException {
		MSFXMLLoader<C> fxmlLoader = new MSFXMLLoader<C>(fxmlPath, controller, initializable -> {
			this.initEvents(initializable);
		});

		Platform.runLater(() -> {
			Boolean scaleInterface = MSLightweightComponentsHolder.APPLICATION_STARTUP_XML_CONFIGURATION
					.getNodeContentAsBoolean("//config/scaleInterfaceConfiguration/scale");

			if (scaleInterface) {
				Double xScale = MSLightweightComponentsHolder.APPLICATION_STARTUP_XML_CONFIGURATION
						.getNodeContentAsDouble("//config/scaleInterfaceConfiguration/xScale");

				Double yScale = MSLightweightComponentsHolder.APPLICATION_STARTUP_XML_CONFIGURATION
						.getNodeContentAsDouble("//config/scaleInterfaceConfiguration/yScale");

				if (xScale != null && yScale != null) {
					this.getScene().setRoot(new ScalableContentPane(this.getScene().getRoot(), xScale, yScale));

					if (this.getScene().getWindow().isShowing()) {
						this.hide();
						this.show();
					}
				}
			}
		});

		this.setTitle(title);
		this.setScene(new Scene(fxmlLoader.loadBorderPane()));
		this.setController((C) fxmlLoader.getController());
		this.getIcons().addAll(MSLightweightJFXUIComponentsHolder.WINDOW_ICON);
	}

	private void initEvents(Initializable controller) {
		if (controller instanceof StatefullController) {
			(new Thread(() -> {
				this.uiComponentParams = MSComponentsHolder.UI_COMPONENT_PARAM_DAO.findAllByStageId(this.stageId);
			})).start();

			final Map<String, Supplier<String>> statefullUIComponentNameToValueRetrieverMap = ((StatefullController) controller)
					.statefullUIComponentNameToValueRetrieverMap();

			final Map<String, Consumer<String>> statefullUIComponentNameToValueModifiarMap = ((StatefullController) controller)
					.statefullUIComponentNameToValueModifiarMap();

			this.addEventHandler(WindowEvent.WINDOW_SHOWING, event -> {
				statefullUIComponentNameToValueModifiarMap.forEach((uiComponent, updater) -> {
					UIComponentParam uiComponentParam = this.uiComponentParams.get(uiComponent);

					if (uiComponentParam != null) {
						updater.accept(uiComponentParam.getValue());
					}
				});
			});

			this.addEventHandler(WindowEvent.WINDOW_HIDDEN, event -> {
				statefullUIComponentNameToValueRetrieverMap.forEach((key, value) -> {
					UIComponentParam uiComponentParam = this.uiComponentParams.get(key);

					if (uiComponentParam == null) {
						uiComponentParam = new UIComponentParam();
						uiComponentParam.setStageId(this.stageId);
						uiComponentParam.setUiComponentParamEnumId(key);

						this.uiComponentParams.put(key, uiComponentParam);
					}

					uiComponentParam.setValue(value.get());

					final UIComponentParam uiComponentParamAsFinal = uiComponentParam;

					(new Thread(() -> {
						MSComponentsHolder.UI_COMPONENT_PARAM_DAO.getSessionFactoryHandler()
								.insertOrUpdate(uiComponentParamAsFinal);
					})).start();
				});
			});
		}

		if (controller instanceof SplitableController<?>) {
			this.addEventHandler(WindowEvent.WINDOW_SHOWING, event -> {
				((SplitableController<?>) controller).splitInterface();
			});
		}

		if (controller instanceof IFeaturesRightsRestrictionsHolder) {
			this.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
				Platform.runLater(() -> {
					((IFeaturesRightsRestrictionsHolder) controller).applyFeaturesRightsRestrictions();
				});
			});
		} else {
			if (controller instanceof RestrictionsHolder) {
				this.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
					Platform.runLater(() -> {
						((RestrictionsHolder) controller).applyRestriction();
					});
				});
			}

			if (controller instanceof IFeaturesRestrictionHolder) {
				this.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
					Platform.runLater(() -> {
						((IFeaturesRestrictionHolder) controller).applyFeaturesRestriction();
					});
				});
			}
		}

		if (controller instanceof KeyStageEvent) {
			this.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
				((KeyStageEvent) controller).onKeyReleasedStageEvent(event);
			});

			this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
				((KeyStageEvent) controller).onKeyPressedStageEvent(event);
			});

			Platform.runLater(() -> {
				((KeyStageEvent) controller).initDescribers();
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

	public void onBeforeInitControllerCallback() {
	}

	public String getStageIndex() {
		return "";
	}

	public void showOrToFront() {
		if (this.isShowing())
			this.toFront();
		else
			this.show();
	}

	public void showAndWaitOrToFront() {
		if (this.isShowing())
			this.toFront();
		else
			this.showAndWait();
	}
}
