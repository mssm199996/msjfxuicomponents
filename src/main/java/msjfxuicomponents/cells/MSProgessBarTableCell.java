package msjfxuicomponents.cells;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MSProgessBarTableCell<T> extends TableCell<T, Double> {

	private ProgressBar progressBar;
	private Text text;

	public MSProgessBarTableCell() {
		this.progressBar = new ProgressBar();
		this.progressBar.setMaxWidth(Double.MAX_VALUE);

		this.text = new Text();

		StackPane container = new StackPane();
		container.getChildren().addAll(this.progressBar, this.text);
		container.setAlignment(Pos.CENTER);
		container.setPadding(new Insets(0.0, 2.0, 0.0, 2.0));
	}

	@Override
	protected void updateItem(Double item, boolean empty) {
		super.updateItem(item, empty);

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

				this.text.setText(String.format("%.2f", item * 100) + "%");
				this.text.setFill(textColor);

				this.progressBar.setProgress(item);
				this.progressBar.setStyle("-fx-min-height: 22px !important; " + colorAsStyle);

				this.setGraphic(this.progressBar.getParent());
				this.setText("");
			}
		}
	}

	public Color getColor(Double progress) {
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

		return Color.rgb(red, green, blue);
	}

	public Color getTextColor(Double progress) {
		if (progress >= 0.80)
			return Color.WHITE;

		return Color.BLACK;
	}
}