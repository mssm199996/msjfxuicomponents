package msjfxuicomponents.mvc;

import java.util.HashMap;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public interface ColoredController {

	public abstract HashMap<Node, String[]> getNodesToColor();

	public abstract Color getColor();

	public default void applyColor() {
		Color color = this.getColor();

		if (color != null) {
			Set<Node> nodes = this.getNodesToColor().keySet();

			for (Node node : nodes) {
				String[] styles = this.getNodesToColor().get(node);

				for (String style : styles)
					node.setStyle(style + color.toString().replaceAll("0x", "#") + ";");
			}
		}
	}
}
