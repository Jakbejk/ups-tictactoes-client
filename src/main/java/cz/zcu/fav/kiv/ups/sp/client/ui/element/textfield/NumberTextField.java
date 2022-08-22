package cz.zcu.fav.kiv.ups.sp.client.ui.element.textfield;

import javafx.scene.control.TextField;

/**
 * @author honza on 17.01.2022
 * @version 0.0.0
 * @project temp
 */
public class NumberTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (validateNumeric(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validateNumeric(text)) {
            super.replaceSelection(text);
        }
    }

    private boolean validateNumeric(String text) {
        return text.matches("[0-9]*");
    }

    public int getValue() {
        return Integer.parseInt(super.getText());
    }

}
