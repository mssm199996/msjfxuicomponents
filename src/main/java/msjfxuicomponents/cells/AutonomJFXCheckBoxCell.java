package msjfxuicomponents.cells;

public abstract class AutonomJFXCheckBoxCell<T> extends JFXCheckboxCell<T> {

	private boolean value = false;

	public AutonomJFXCheckBoxCell(boolean initialValue) {
		super();

		this.value = initialValue;
	}

	@Override
	public void updateItem(Boolean item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setGraphic(null);
			setText("");
		} else {
			this.isChecked.setSelected(this.value);
			this.setGraphic(this.isChecked.getParent());
			this.setText("");
		}
	}

	@Override
	public void onBeforeUpdateRow(T row, Boolean newValue) {
		this.value = newValue;
	}
}
