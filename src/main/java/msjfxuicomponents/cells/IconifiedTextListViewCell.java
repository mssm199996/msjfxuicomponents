package msjfxuicomponents.cells;

import com.jfoenix.controls.JFXListCell;
import de.jensd.fx.glyphs.GlyphsDude;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import msjfxuicomponents.models.IIconifiedCategorizer;
import org.apache.commons.lang.math.RandomUtils;

public class IconifiedTextListViewCell<T extends IIconifiedCategorizer> extends JFXListCell<T> {
    private Text glyphIcon;

    public IconifiedTextListViewCell() {
        this.getStyleClass().add("iconified-text-list-view-cell");
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty && item != null) {
            this.glyphIcon = GlyphsDude.createIcon(item.fromStringToGlyph(item.getIcon()));
            this.glyphIcon.setFill(this.getRandomColor());
            this.glyphIcon.getStyleClass().add("iconified-text-list-view-cell-glyph-icon");

            this.setText("  " + item.getDesignation());
            this.setGraphic(this.glyphIcon);
        } else {
            this.setText("");
            this.setGraphic(null);
        }
    }

    public Color getRandomColor() {
        double hue = RandomUtils.nextInt(360);

        return Color.hsb(hue, 1.0, 1.0);
    }
}