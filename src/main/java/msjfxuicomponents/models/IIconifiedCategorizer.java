package msjfxuicomponents.models;

import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.weathericons.WeatherIcon;
import javafx.beans.property.SimpleStringProperty;
import msdatabaseutils.ICategorizer;

public interface IIconifiedCategorizer extends ICategorizer {

    public void setIcon(String icon);

    public String getIcon();

    public SimpleStringProperty iconProperty();

    public default String fromGlyphToString(GlyphIcons glyphIcons) {
        if (glyphIcons != null)
            return glyphIcons.characterToString();
        else
            return FontAwesomeIcon.ANDROID.characterToString();
    }

    public default GlyphIcons fromStringToGlyph(String name) {
        if (name != null) {
            GlyphIcons icon = FontAwesomeIcon.valueOf(name);

            if (icon == null) {
                icon = WeatherIcon.valueOf(name);

                if (icon == null) {
                    icon = MaterialDesignIcon.valueOf(name);

                    if (icon == null) {
                        icon = MaterialIcon.valueOf(name);

                        if (icon == null) {
                            icon = OctIcon.valueOf(name);
                        }
                    }
                }
            }

            return icon;
        } else return FontAwesomeIcon.ANDROID;
    }
}
