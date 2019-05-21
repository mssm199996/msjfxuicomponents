package msjfxuicomponents.cells;

import javafx.util.StringConverter;

public abstract class AutoCommitDoubleTextFieldCell<T> extends AutoCommitNumberTextFieldCell<T, Double> {

	private static StringConverter<Double> CONVERTER = new StringConverter<Double>() {
		@Override
		public Double fromString(String v) {
			return Double.parseDouble(v.replaceAll(",", "."));
		}

		@Override
		public String toString(Double v) {
			return String.format("%.2f", v);
		}
	};

	public AutoCommitDoubleTextFieldCell() {
		super(CONVERTER);
	}

	@Override
	public Double fromStringToS(String entityRepresentation) {
		return Double.parseDouble(entityRepresentation.replaceAll(",", "."));
	}
}
