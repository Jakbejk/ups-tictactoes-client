package cz.zcu.fav.kiv.ups.sp.client.ui.element.label;

import javafx.scene.control.Label;

/**
 * @author honza on 22.11.2021
 * @version 0.0.0
 * @project ups
 */
public class QuizLabel extends Label {

    private boolean right;

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isRight() {
        return right;
    }


}
