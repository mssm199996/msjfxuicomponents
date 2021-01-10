package msjfxuicomponents.cells;

import javafx.util.StringConverter;

public abstract class AutoCommitDoubleTextFieldCell<T> extends AutoCommitNumberTextFieldCell<T, Double> {

	public AutoCommitDoubleTextFieldCell() {
		this("%.2f");
	}

	public AutoCommitDoubleTextFieldCell(String pattern) {
		super(new StringConverter<Double>() {
			@Override
			public Double fromString(String v) {
				return Double.parseDouble(v.replaceAll(",", "."));
			}

			@Override
			public String toString(Double v) {
				return String.format(pattern, v);
			}
		});
	}

	@Override
	public Double fromStringToS(String entityRepresentation) {
		return Double.parseDouble(entityRepresentation.replaceAll(",", "."));
	}
}
