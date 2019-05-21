package msjfxuicomponents.cells;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public abstract class AutoCommitStringTextFieldCell<T> extends AutoCommitTextFieldCell<T, String> {

	private static StringConverter<String> CONVERTER = new DefaultStringConverter();

	public AutoCommitStringTextFieldCell() {
		super(CONVERTER);
	}

	@Override
	public String fromStringToS(String entityRepresentation) {
		return entityRepresentation;
	}

	public void setNewValueToEntityAttribute(ObservableValue<String> observableValue, String newValue) {
		if (observableValue instanceof WritableValue) {
			@SuppressWarnings("unchecked")
			WritableValue<String> property = (WritableValue<String>) observableValue;
			property.setValue(newValue);
		}
	}
}