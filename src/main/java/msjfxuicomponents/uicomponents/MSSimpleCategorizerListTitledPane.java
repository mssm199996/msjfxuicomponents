package msjfxuicomponents.uicomponents;

import java.util.Collection;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import msdatabaseutils.ICategorizer;
import msjfxuicomponents.cells.ICategorizerListViewCell;

public class MSSimpleCategorizerListTitledPane<T extends ICategorizer> extends TitledPane {

	protected SimpleObjectProperty<Image> leftSideImage = new SimpleObjectProperty<>();
	protected ImageView imageView;
	protected ListView<T> listView;
	protected SimpleDoubleProperty leftSideImageWidthFit = new SimpleDoubleProperty(40.0),
			leftSideImageHeightFit = new SimpleDoubleProperty(40.0);

	public void constructImageView() {
		this.imageView = new ImageView(this.leftSideImage.getValue());
		this.imageView.setFitWidth(this.leftSideImageWidthFit.getValue());
		this.imageView.setFitHeight(this.leftSideImageHeightFit.getValue());

		this.imageView.imageProperty().bind(this.leftSideImage);
		this.imageView.fitWidthProperty().bind(this.leftSideImageWidthFit);
		this.imageView.fitHeightProperty().bind(this.leftSideImageHeightFit);
	}

	public MSSimpleCategorizerListTitledPane() {
		this.constructModules();
		this.assembleModules();
	}

	public void constructModules() {
		this.constructImageView();
		this.constructListView();
	}

	public void assembleModules() {
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));

		Pane top = this.constructTopContainer();
		Pane center = this.constructCenterContainer();

		borderPane.setTop(top);
		borderPane.setCenter(center);

		BorderPane.setAlignment(top, Pos.CENTER);
		BorderPane.setMargin(top, new Insets(0.0, 0.0, 5.0, 0.0));

		this.setText("No title");
		this.setGraphic(this.imageView);
		this.setContent(borderPane);
	}

	public void constructListView() {
		this.listView = new ListView<>();
		this.listView.setCellFactory(call -> {
			return new ICategorizerListViewCell<>(this.listView);
		});
	}

	public Pane constructCenterContainer() {
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(this.listView);

		return borderPane;
	}

	public Pane constructTopContainer() {
		HBox hbox = new HBox();

		return hbox;
	}

	public Double getLeftSideImageWidthFit() {
		return leftSideImageWidthFit.getValue();
	}

	public void setLeftSideImageWidthFit(Double leftSideImageWidthFit) {
		this.leftSideImageWidthFit.setValue(leftSideImageWidthFit);
	}

	public Double getLeftSideImageHeightFit() {
		return leftSideImageHeightFit.getValue();
	}

	public void setLeftSideImageHeightFit(Double leftSideImageHeightFit) {
		this.leftSideImageHeightFit.setValue(leftSideImageHeightFit);
	}

	public List<T> getItems() {
		return this.listView.getItems();
	}

	public void setItems(ObservableList<T> items) {
		this.listView.setItems(items);
	}

	public void addAll(Collection<T> items) {
		this.listView.getItems().addAll(items);
	}

	public void addAllIfNotContains(Collection<T> items) {
		for (T item : items) {
			if (!this.listView.getItems().contains(item))
				this.listView.getItems().add(item);
		}
	}

	public void addIfNotContains(T item) {
		if (!this.listView.getItems().contains(item))
			this.listView.getItems().add(item);
	}

	public void remove(T entity) {
		this.listView.getItems().remove(entity);
	}

	public void removeAll(Collection<T> items) {
		this.listView.getItems().removeAll(items);
	}

	public void clear() {
		this.listView.getItems().clear();
	}

	public Image getLeftSideImage() {
		return leftSideImage.getValue();
	}

	public void setLeftSideImage(Image leftSideImage) {
		this.leftSideImage.setValue(leftSideImage);
	}

	public void setCellFactory(Callback<ListView<T>, ListCell<T>> call) {
		this.listView.setCellFactory(call);
	}
}
