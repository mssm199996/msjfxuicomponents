package msjfxuicomponents.cells;

import javafx.util.StringConverter;

public abstract class AutoCommitIntegerTextFieldCell<T> extends AutoCommitNumberTextFieldCell<T, Integer> {

	private static StringConverter<Integer> CONVERTER = new StringConverter<Integer>() {
		@Override
		public Integer fromString(String v) {
			return Integer.parseInt(v);
		}

		@Override
		public String toString(Integer v) {
			return Integer.toString(v);
		}
	};

	public AutoCommitIntegerTextFieldCell() {
		super(CONVERTER);
	}

	@Override
	public Integer fromStringToS(String entityRepresentation) {
		return Integer.parseInt(entityRepresentation);
	}
}
