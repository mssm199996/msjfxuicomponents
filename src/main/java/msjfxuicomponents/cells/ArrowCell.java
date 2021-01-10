package msjfxuicomponents.cells;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import msjfxuicomponents.MSLightweightJFXUIComponentsHolder;

public class ArrowCell<T> extends TableCell<T, Boolean> {
	private ImageView mouvementIcon = new ImageView();
	private Boolean inverser = false;

	public ArrowCell(boolean inverser) {
		this.inverser = inverser;

		HBox hbox = new HBox();
		hbox.getChildren().addAll(this.mouvementIcon);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(5.0);
	}

	@Override
	protected void updateItem(Boolean mouvement, boolean empty) {
		super.updateItem(mouvement, empty);

		if (mouvement == null || empty) {
			setGraphic(null);
			setText("");
		} else {
			if (mouvement && !this.inverser)
				this.mouvementIcon.setImage(MSLightweightJFXUIComponentsHolder.RIGHT_GREEN_ARROW_ICON);
			else if (mouvement && this.inverser)
				this.mouvementIcon.setImage(MSLightweightJFXUIComponentsHolder.LEFT_GREEN_ARROW_ICON);
			else if (!mouvement && !this.inverser)
				this.mouvementIcon.setImage(MSLightweightJFXUIComponentsHolder.LEFT_RED_ARROW_ICON);
			else
				this.mouvementIcon.setImage(MSLightweightJFXUIComponentsHolder.RIGHT_RED_ARROW_ICON);

			setGraphic(this.mouvementIcon.getParent());
			setText("");
		}
	}
}
