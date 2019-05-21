package msjfxuicomponents.cells;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.scene.control.TableCell;

public class WellFormattedLocalDateCell<T> extends TableCell<T, LocalDate>{
	public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy").withLocale(Locale.FRANCE);
	
	@Override
	public void updateItem(LocalDate item, boolean empty) {
		if(empty || item == null) {
			setText("");
			setGraphic(null);
		}
		else {
			setText(item.format(WellFormattedLocalDateCell.FORMATTER));
			setGraphic(null);
		}
	}
}
