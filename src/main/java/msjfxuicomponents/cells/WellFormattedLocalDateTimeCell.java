package msjfxuicomponents.cells;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.scene.control.TableCell;

public class WellFormattedLocalDateTimeCell<T> extends TableCell<T, LocalDateTime> {
	public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
			.withLocale(Locale.FRANCE);

	@Override
	public void updateItem(LocalDateTime item, boolean empty) {
		if (empty || item == null) {
			setText("");
			setGraphic(null);
		} else {
			setText(item.format(WellFormattedLocalDateCell.FORMATTER));
			setGraphic(null);
		}
	}
}
