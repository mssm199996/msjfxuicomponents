package msjfxuicomponents.rows;

import javafx.scene.control.TableRow;

public interface ColoredRow {

	public abstract String[] getPossibleClasses();

	public default void addColorfullClass(String colorClass, TableRow<?> tableRow) {
		for (String possibleColorClass : this.getPossibleClasses())
			tableRow.getStyleClass().remove(possibleColorClass);

		tableRow.getStyleClass().add(colorClass);
	}
	
	public default void clearAll(TableRow<?> tableRow){
		for (String possibleColorClass : this.getPossibleClasses())
			tableRow.getStyleClass().remove(possibleColorClass);
	}
}
