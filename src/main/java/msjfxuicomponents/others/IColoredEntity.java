package msjfxuicomponents.others;

import java.util.function.Consumer;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

public interface IColoredEntity {

	public String getHexColor();

	public void setHexColor(String value);

	public static String fromLightedColorToHexColor(Color color) {
		if (color != null) {
			Integer red = (int) (color.getRed() * 255.0);
			Integer green = (int) (color.getGreen() * 255.0);
			Integer blue = (int) (color.getBlue() * 255.0);
			Double alpha = 1.0;

			return "rgba(" + red + ", " + green + ", " + blue + ", " + alpha + ")";
		}

		return "rgb(255, 255, 255)";
	}

	public static Color fromHexColorToLightedColor(IColoredEntity coloredEntity) {
		String hexColor = coloredEntity.getHexColor();

		if (hexColor != null && !hexColor.equals("")) {
			hexColor = hexColor.replace("rgba(", "");
			hexColor = hexColor.replace(")", "");
			hexColor = hexColor.replaceAll(" ", "");

			String[] values = hexColor.split(",");

			Integer red = Integer.parseInt(values[0]);
			Integer green = Integer.parseInt(values[1]);
			Integer blue = Integer.parseInt(values[2]);

			return Color.rgb(red, green, blue);
		}

		return Color.WHITE;
	}

	public static <P extends IColoredEntity> void initLinkTableViewJFXColorPicker(TableView<P> tableView,
			ColorPicker colorPicker, Consumer<P> onUpdateRequest) {
		tableView.getSelectionModel().selectedItemProperty().addListener((__, ___, value) -> {
			if (value != null)
				colorPicker.setValue(IColoredEntity.fromHexColorToLightedColor(value));
			else
				colorPicker.setValue(Color.WHITE);
		});

		colorPicker.valueProperty().addListener((__, ___, value) -> {
			P entity = tableView.getSelectionModel().getSelectedItem();

			if (entity != null) {
				entity.setHexColor(IColoredEntity.fromLightedColorToHexColor(value));

				tableView.refresh();

				if (onUpdateRequest != null)
					onUpdateRequest.accept(entity);
			}
		});
	}
}
