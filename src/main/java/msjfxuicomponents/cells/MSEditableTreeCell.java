package msjfxuicomponents.cells;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import msjfxuicomponents.others.ITreeItemNode;

public abstract class MSEditableTreeCell<T extends ITreeItemNode<T>> extends TreeCell<T> {
	private TextField textField;

	@Override
	public void startEdit() {
		super.startEdit();

		if (textField == null) {
			createTextField();
		}

		setText(null);
		setGraphic(textField);

		textField.selectAll();
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(getItem().getStringRepresentation());
		setGraphic(getTreeItem().getGraphic());
	}

	@Override
	public void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (textField != null) {
					textField.setText(getString());
				}
				setText(null);
				setGraphic(textField);
			} else {
				setText(getString());
				setGraphic(null);
			}
		}
	}

	public abstract void doAfterUpdate(T item);

	private void createTextField() {
		textField = new TextField(getString());
		textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ENTER) {
					getItem().setStringRepresention(textField.getText());

					commitEdit(getItem());

					doAfterUpdate(getItem());
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
			}
		});
	}

	private String getString() {
		return getItem() == null ? "" : getItem().getStringRepresentation();
	}
}
