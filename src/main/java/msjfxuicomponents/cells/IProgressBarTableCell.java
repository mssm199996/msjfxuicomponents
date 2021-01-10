package msjfxuicomponents.cells;

import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public interface IProgressBarTableCell {

	public default void onDrawProgress(Double item, boolean empty) {
		if (item == null || empty) {
			this.setGraphic(null);
			this.setText("");
		} else {
			if (item != null) {
				if (item.equals(Double.NaN))
					item = 1.0;

				Color textColor = this.getTextColor(item);
				Color color = this.getColor(item);

				String colorAsStyle = "-fx-accent: " + color.toString().replaceAll("0x", "#");

				this.getTextComponent().setText(String.format("%.2f", item * 100) + "%");
				this.getTextComponent().setFill(textColor);

				this.getProgressBar().setProgress(item);
				this.getProgressBar().setStyle("-fx-min-height: 22px !important; " + colorAsStyle);

				this.setGraphic(this.getProgressBar().getParent());
				this.setText("");
			}
		}
	}

	public default Color getColor(Double progress) {
		int position = (int) (progress * 1023);

		int red;
		int green;
		int blue;

		if (position <= 255) {
			red = 255;
			green = position % 256;
			blue = 0;
		} else if (position <= 511) {
			red = 255 - (position % 256);
			green = 255;
			blue = 0;
		} else if (position <= 767) {
			red = 0;
			green = 255;
			blue = position % 256;
		} else if (position <= 1023) {
			red = 0;
			green = 255 - position % 256;
			blue = 255;
		} else {
			red = 0;
			green = 0;
			blue = 0;
		}

		red = Math.max(0, red);
		green = Math.max(0, green);
		blue = Math.max(0, blue);

		return Color.rgb(red, green, blue);
	}

	public default Color getTextColor(Double progress) {
		if (progress >= 0.80)
			return Color.WHITE;

		return Color.BLACK;
	}

	abstract void setGraphic(Node node);

	abstract void setText(String text);

	abstract ProgressBar getProgressBar();

	abstract Text getTextComponent();
}
