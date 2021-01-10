package msjfxuicomponents.uicomponents;

import java.util.Collection;
import java.util.function.Function;

import com.jfoenix.controls.JFXButton;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import msdatabaseutils.ICategorizer;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.MSLightweightJFXUIComponentsHolder;
import msjfxuicomponents.others.ICategorizerAdder;
import msjfxuicomponents.others.ICategorizerControllerAdder;

public class MSCategorizer<T extends ICategorizer> extends HBox {

	private MSAutoCompleteComboBox<T> combobox;
	private JFXButton button;
	private ICategorizerAdder<T> categorizerAdder;
	private ICategorizerControllerAdder<T> categorizerControllerAdder;

	public MSCategorizer() {
		super();

		this.initCategorizer();
	}

	public void initCategorizer() {
		this.combobox = new MSAutoCompleteComboBox<>();
		this.combobox.setMaxWidth(Double.MAX_VALUE);
		this.combobox.setMaxAllowedWidth(Double.MAX_VALUE);
		this.combobox.setPrefHeight(30.0);

		this.button = new JFXButton();
		this.button.setOnMouseClicked(event -> {
			Stage mother = (Stage) this.getScene().getWindow();

			if (this.categorizerAdder != null || this.categorizerControllerAdder != null) {
				T entity = null;

				if (this.categorizerControllerAdder != null) {
					this.categorizerControllerAdder.getStage().showAndWait();

					entity = this.categorizerControllerAdder.getConstructedCategorizer();
				} else {
					String title = mother.getTitle();
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

					this.combobox.getItems().add(entity);
					this.combobox.setEntity(entity);
				}
			} else
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("MSJFXUIComponents", "Fatal error...",
						"No adder specified !", mother);
		});

		this.setSpacing(5.0);
		this.setAlignment(Pos.CENTER);

		this.getChildren().add(this.combobox);
		this.getChildren().add(this.button);

		HBox.setHgrow(this.combobox, Priority.ALWAYS);
	}

	public void finishUIDesign() {
		this.button.setGraphic(new ImageView(MSLightweightJFXUIComponentsHolder.LITTLE_PLUS_ICON));

		this.combobox.setConverter(new StringConverter<T>() {

			@Override
			public String toString(T object) {
				if (object != null)
					return object.getDesignation();

				return "";
			}

			@Override
			public T fromString(String string) {
				return combobox.getEntity();
			}
		});
	}

	public T getEntity() {
		return this.combobox.getEntity();
	}

	public void setEntity(T entity) {
		this.combobox.setEntity(entity);
	}

	public ICategorizerAdder<T> getCategorizerAdder() {
		return categorizerAdder;
	}

	public void setDAOCategorizerAdder(ICategorizerAdder<T> categorizerAdder) {
		this.categorizerAdder = categorizerAdder;
	}

	public void setItems(ObservableList<T> items) {
		this.combobox.setItems(items);
	}

	public void setEntitiesLoader(Function<String, Collection<T>> entitiesLoader) {
		this.combobox.setEntitiesLoader(entitiesLoader);
	}

	public ICategorizerControllerAdder<T> getCategorizerControllerAdder() {
		return categorizerControllerAdder;
	}

	public void setCategorizerControllerAdder(ICategorizerControllerAdder<T> categorizerControllerAdder) {
		this.categorizerControllerAdder = categorizerControllerAdder;
	}
}
