package msjfxuicomponents.cells;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.util.StringConverter;

public abstract class AutoCommitNumberTextFieldCell<T, S extends Number> extends AutoCommitTextFieldCell<T, S> {

	public AutoCommitNumberTextFieldCell(StringConverter<S> converter) {
		super(converter);
	}

	@Override
	public void setNewValueToEntityAttribute(ObservableValue<S> observableValue, S newValue) {
		if (observableValue instanceof WritableValue) {
			@SuppressWarnings("unchecked")
			WritableValue<Number> property = (WritableValue<Number>) observableValue;
			property.setValue(newValue);
		}
	}
}
