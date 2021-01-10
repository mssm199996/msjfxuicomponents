package msjfxuicomponents.mvc.listecategorizers;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import msdatabaseutils.IParentCategorizer;
import msdatabaseutils.SimpleCategorizer;
import msjfxuicomponents.MSJFXUIComponentsHolder;

public abstract class ListeParentSimpleCategorizersController<P extends IParentCategorizer>
		extends ListeParentCategorizersController<P, SimpleCategorizer> {

	public ListeParentSimpleCategorizersController(ObservableList<P> dataSource) {
		super(dataSource);
	}

	@FXML
	@Override
	public void addChildCategorizer() {
		P parent = this.tableCategorizers.getSelectionModel().getSelectedItem();

		if (parent != null) {
			String child = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayStringAlert(this.getNomGestion(),
					this.getAddChildHeader(), "", this.getWindow());

			if (child != null) {
				SimpleCategorizer simpleCategorizer = new SimpleCategorizer();
				simpleCategorizer.setDesignation(child);

				this.updateParentChild(parent, simpleCategorizer);

				this.childsTableCategorizers.getItems().add(simpleCategorizer);
			}
		} else
			super.addChildCategorizer();
	}

	@Override
	public List<SimpleCategorizer> findAllNotAffectedChilds(P parent) {
		return new ArrayList<>();
	}
}
