package cz.zcu.fav.kiv.ups.sp.client.ui.element.text;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * @author honza on 06.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class ColorText extends Text {

    public ColorText(String text, Color color) {
        super(text);

        if (color != null) {
            super.setFill(color);
        }
    }


}
