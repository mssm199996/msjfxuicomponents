package msjfxuicomponents.cells;

public abstract class AwareOfEditingAutoCommitStringTextFieldCell<T> extends AutoCommitStringTextFieldCell<T> {

	// Knows when you starts editing
	
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
	public void commitEdit(String newValue) {
		super.commitEdit(newValue);

		this.notifyEditingMode(false);
	}

	public abstract void notifyEditingMode(boolean isEditing);
}
