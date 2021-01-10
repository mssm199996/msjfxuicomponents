package msjfxuicomponents.componentsStuffers;

import javafx.scene.control.Accordion;
import javafx.scene.control.TableView;

public class DisabilityBinder {

	public static void linkedTableViewToAccordion(TableView<?> tableView, Accordion accordion) {
		tableView.getSelectionModel().selectedItemProperty().addListener((__, ___, value) -> {
			accordion.setDisable(value == null);
		});
	}
}
