package msjfxuicomponents.uicomponents;

import java.util.Collection;

import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionModel;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import msdatabaseutils.ICategorizer;
import msjfxuicomponents.MSLightweightJFXUIComponentsHolder;
import msjfxuicomponents.others.ICategorizerSearcher;

public class MSSearcheableCategorizerCrudListTitledPane<T extends ICategorizer>
		extends MSCategorizerCrudListTitledPane<T> {

	private JFXTextField searcher;
	private ImageView searchIcon;
	private ICategorizerSearcher<T> filterer;

	@Override
	public void constructModules() {
		super.constructModules();
		
		this.constructSearcher();
	}

	@Override
	public Pane constructTopContainer() {
		Pane crudButtonsContainer = super.constructTopContainer();

		HBox hbox = new HBox();
		hbox.setSpacing(5.0);
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.getChildren().addAll(this.searchIcon, this.searcher);
		hbox.setMaxWidth(Double.MAX_VALUE);

		HBox.setHgrow(this.searcher, Priority.ALWAYS);

		VBox vbox = new VBox();
		vbox.setSpacing(10.0);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));
		vbox.getChildren().addAll(hbox, crudButtonsContainer);

		return vbox;
	}

	public void constructSearcher() {
		this.searcher = new JFXTextField();
		this.searcher.setLabelFloat(true);
		this.searcher.setMaxWidth(Double.MAX_VALUE);
		this.searcher.setPromptText("Barre de recherche");
		this.searcher.setOnKeyReleased(event -> {
			String designation = this.searcher.getText();

			(new Thread(() -> {
				Collection<T> result = this.filterer.search(designation);

				Platform.runLater(() -> {
					this.listView.getItems().clear();
					this.listView.getItems().addAll(result);
				});
			})).start();
		});

		this.searchIcon = new ImageView(MSLightweightJFXUIComponentsHolder.SEARCH_ICON);
		this.searchIcon.setFitWidth(25.0);
		this.searchIcon.setFitHeight(25.0);
	}

	public JFXTextField getSearcher() {
		return searcher;
	}

	public void setSearcher(JFXTextField searcher) {
		this.searcher = searcher;
	}

	public ICategorizerSearcher<T> getFilterer() {
		return filterer;
	}

	public void setFilterer(ICategorizerSearcher<T> filterer) {
		this.filterer = filterer;
	}

	public SelectionModel<T> getSelectionModel() {
		return this.listView.getSelectionModel();
	}
}
