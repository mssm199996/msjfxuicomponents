package msjfxuicomponents.mvc.listecategorizers;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import msdatabaseutils.ICategorizer;
import msdatabaseutils.IParentCategorizer;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.cells.IndexCell;

public abstract class ListeParentCategorizersController<P extends IParentCategorizer, C extends ICategorizer>
		extends ListeCategorizersController<P> {

	public ListeParentCategorizersController(ObservableList<P> dataSource) {
		super(dataSource);
	}

	@FXML
	protected TableView<C> childsTableCategorizers;

	@FXML
	private TableColumn<C, Integer> childsCategorizersIndexColumn;

	@FXML
	private TableColumn<C, String> childsCategorizersNomColumn;

	@FXML
	public void addChildCategorizer() {
		P parent = this.tableCategorizers.getSelectionModel().getSelectedItem();

		if (parent != null) {
			List<C> unAffectedChilds = this.findAllNotAffectedChilds(parent);

			C child = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayChoiceAlert(this.getNomGestion(),
					this.getAddChildHeader(), unAffectedChilds, null, this.getWindow());

			if (child != null) {
				this.updateParentChild(parent, child);

				this.childsTableCategorizers.getItems().add(child);
			}
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getAddChildHeader(),
					"Veuillez d'abords selectionner un élement dans le tableau de gauche", this.getWindow());
	}

	@FXML
	public void deleteChildCategorizer() {
		P parent = this.tableCategorizers.getSelectionModel().getSelectedItem();
		C child = this.childsTableCategorizers.getSelectionModel().getSelectedItem();

		if (parent != null && child != null) {
			this.removeParentChild(parent, child);

			this.childsTableCategorizers.getItems().remove(child);
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(),
					this.getRemoveChildHeader(), "Veuillez d'abords selectionner un élement dans le tableau de droite",
					this.getWindow());
	}

	@Override
	public void initTables() {
		super.initTables();

		this.tableCategorizers.getSelectionModel().selectedItemProperty().addListener((__, oldValue, newValue) -> {
			this.childsTableCategorizers.getItems().clear();

			if (newValue != null)
				this.childsTableCategorizers.getItems().addAll(this.findAllChilds(newValue));
		});

		this.childsCategorizersIndexColumn.setCellFactory(call -> new IndexCell<>());
		this.childsCategorizersNomColumn.setCellValueFactory(call -> call.getValue().designationProperty());
	}

	public abstract List<C> findAllChilds(P parent);

	public abstract List<C> findAllNotAffectedChilds(P parent);

	public abstract void updateParentChild(P parent, C child);

	public abstract void removeParentChild(P parent, C child);

	public abstract String getAddChildHeader();

	public abstract String getRemoveChildHeader();
}
