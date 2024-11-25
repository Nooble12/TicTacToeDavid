package edu.sdccd.cisc191.template;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

/**
 * Extends the basic JavaFX Label with game functionality
 */
public class GameBoardLabel extends Label {
    public static Insets LABEL_PADDING = new Insets(20);

    public GameBoardLabel() {
        // set Label properties like padding
        // adds space between the text
        setPadding(LABEL_PADDING);

    }


}
