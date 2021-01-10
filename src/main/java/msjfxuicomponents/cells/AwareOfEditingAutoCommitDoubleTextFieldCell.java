package msjfxuicomponents.cells;

public abstract class AwareOfEditingAutoCommitDoubleTextFieldCell<T> extends AutoCommitDoubleTextFieldCell<T> {

	public AwareOfEditingAutoCommitDoubleTextFieldCell() {
		super();
	}

	public AwareOfEditingAutoCommitDoubleTextFieldCell(String pattern) {
		super(pattern);
	}

	@Override
	public void startEdit() {
		this.notifyEditingMode(true);

		super.startEdit();
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		this.notifyEditingMode(false);
	}

	@Override
	public void commitEdit(Double newValue) {
		super.commitEdit(newValue);

		this.notifyEditingMode(false);
	}

	public abstract void notifyEditingMode(boolean isEditing);
}
