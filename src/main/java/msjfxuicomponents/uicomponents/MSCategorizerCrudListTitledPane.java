package msjfxuicomponents.uicomponents;

import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import msdatabaseutils.ICategorizer;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.MSLightweightJFXUIComponentsHolder;
import msjfxuicomponents.others.ICategorizerAdder;
import msjfxuicomponents.others.ICategorizerControllerAdder;
import msjfxuicomponents.others.ICategorizerDeleter;

public class MSCategorizerCrudListTitledPane<T extends ICategorizer> extends MSSimpleCategorizerListTitledPane<T> {

	protected JFXButton addButton, deleteButton;
	protected SimpleStringProperty addButtonCaption, deleteButtonCaption;

	protected ICategorizerControllerAdder<T> categorizerControllerAdder;
	protected ICategorizerAdder<T> categorizerAdder = null;
	protected ICategorizerDeleter<T> categorizerDeleter = null;

	protected Runnable beforeAddCallback, beforeDeleteCallback;

	@Override
	public void constructModules() {
		this.constructProperties();
		this.constructImageView();
		this.constructAddButton();
		this.constructDeleteButton();
		this.constructListView();
	}

	@Override
	public Pane constructTopContainer() {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.getChildren().addAll(this.addButton, this.deleteButton);
		hbox.setMaxWidth(Double.MAX_VALUE);
		hbox.setSpacing(5.0);

		HBox.setHgrow(this.addButton, Priority.ALWAYS);
		HBox.setHgrow(this.deleteButton, Priority.ALWAYS);

		return hbox;
	}

	public void constructAddButton() {
		this.addButton = new JFXButton("", new ImageView(MSLightweightJFXUIComponentsHolder.LITTLE_PLUS_ICON));
		this.addButton.setMaxWidth(Double.MAX_VALUE);
		this.addButton.textProperty().bind(this.addButtonCaption);
		this.addButton.getStyleClass().add("lightGreenButton");
		this.addButton.setOnMouseClicked(event -> {
			if (this.beforeAddCallback != null)
				this.beforeAddCallback.run();

			Stage mother = this.getStage();
			String title = mother.getTitle();

			if (this.categorizerAdder != null || this.categorizerControllerAdder != null) {
				T entity = null;

				if (this.categorizerControllerAdder != null) {
					if (this.categorizerControllerAdder.isPrerequisiesAvailable()) {
						this.categorizerControllerAdder.getStage().showAndWait();

						entity = this.categorizerControllerAdder.getConstructedCategorizer();
					} else {
						MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(title, "Nouvelle entitée",
								this.categorizerControllerAdder.prerequisiesUnavailableMessage(), mother);

						return;
					}
				} else {
					String header = "Nouvelle entitée";
					String content = "Veuillez saisir une désignation";

					String designation = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayStringAlert(title, header,
							content, "", mother);

					if (designation != null && !designation.equals("")) {
						entity = this.categorizerAdder.createEntity();

						if (designation != null)
							entity.setDesignation(designation);
					}
				}

				if (entity != null) {
					final T persistable = entity;

					(new Thread(() -> {
						this.categorizerAdder.insertEntity(persistable);
					})).start();

					this.listView.getItems().add(entity);
					this.listView.getSelectionModel().select(entity);
				}
			} else
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("MSJFXUIComponents", "Fatal error...",
						"No adder specified !", mother);
		});
	}

	public void constructDeleteButton() {
		this.deleteButton = new JFXButton("", new ImageView(MSLightweightJFXUIComponentsHolder.LITTLE_MINUS_ICON));
		this.deleteButton.setMaxWidth(Double.MAX_VALUE);
		this.deleteButton.textProperty().bind(this.deleteButtonCaption);
		this.deleteButton.getStyleClass().add("lightRedButton");
		this.deleteButton.setOnMouseClicked(event -> {
			if (this.beforeDeleteCallback != null)
				this.beforeDeleteCallback.run();

			Stage mother = this.getStage();

			if (this.categorizerDeleter != null) {
				T entity = this.listView.getSelectionModel().getSelectedItem();

				String title = mother.getTitle();
				String header = "Suppression de la sélection";

				if (entity != null) {
					String content = "Veuillez confirmer pour procéder à la suppression";

					boolean confirmation = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(title,
							header, content, mother);

					if (confirmation) {
						(new Thread(() -> {
							this.categorizerDeleter.deleteEntity(entity);
						})).start();

						this.listView.getItems().remove(entity);
					}
				} else
					MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(title, header,
							"Erreur... veuillez d'abords selectionner un élement dans la liste !", mother);
			} else
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("MSJFXUIComponents", "Fatal error...",
						"No deleter specified !", mother);
		});
	}

	protected void constructProperties() {
		this.addButtonCaption = new SimpleStringProperty("Add entity");
		this.deleteButtonCaption = new SimpleStringProperty("Delete entity");
	}

	public String getAddButtonCaption() {
		return addButtonCaption.getValue();
	}

	public void setAddButtonCaption(String addButtonCaption) {
		this.addButtonCaption.setValue(addButtonCaption);
	}

	public String getDeleteButtonCaption() {
		return deleteButtonCaption.getValue();
	}

	public void setDeleteButtonCaption(String deleteButtonCaption) {
		this.deleteButtonCaption.setValue(deleteButtonCaption);
	}

	public ICategorizerAdder<T> getCategorizerAdder() {
		return categorizerAdder;
	}

	public void setCategorizerAdder(ICategorizerAdder<T> categorizerAdder) {
		this.categorizerAdder = categorizerAdder;
	}

	public ICategorizerDeleter<T> getCategorizerDeleter() {
		return categorizerDeleter;
	}

	public void setCategorizerDeleter(ICategorizerDeleter<T> categorizerDeleter) {
		this.categorizerDeleter = categorizerDeleter;
	}

	public ICategorizerControllerAdder<T> getCategorizerControllerAdder() {
		return categorizerControllerAdder;
	}

	public void setCategorizerControllerAdder(ICategorizerControllerAdder<T> categorizerControllerAdder) {
		this.categorizerControllerAdder = categorizerControllerAdder;
	}

	public T getSelectedItem() {
		return this.listView.getSelectionModel().getSelectedItem();
	}

	public void clearSelection() {
		this.listView.getSelectionModel().clearSelection();
	}

	public Stage getStage() {
		return (Stage) this.getScene().getWindow();
	}

	public Runnable getBeforeAddCallback() {
		return beforeAddCallback;
	}

	public void setBeforeAddCallback(Runnable beforeAddCallback) {
		this.beforeAddCallback = beforeAddCallback;
	}

	public Runnable getBeforeDeleteCallback() {
		return beforeDeleteCallback;
	}

	public void setBeforeDeleteCallback(Runnable beforeDeleteCallback) {
		this.beforeDeleteCallback = beforeDeleteCallback;
	}
}
