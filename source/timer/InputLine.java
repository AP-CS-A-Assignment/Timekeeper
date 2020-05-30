package timer;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * Represents an input line consisting of a Name and a Textfield to type into
 */
public class InputLine extends HBox {

    //Node Graph
    private Label name;
    private TextField field;
    private Label error;

    /**
     * Sets up the node graph
     * @param _name
     * @param tooltip
     */
    public InputLine(String _name, String tooltip) {
        super(10);

        name = new Label(_name);
        name.setTooltip(new Tooltip(tooltip));

        field = new TextField();
        field.setText("");
        field.textProperty().addListener(observable -> error.setVisible(false));

        error = new Label();
        error.setVisible(false);

        getChildren().addAll(name, field, error);

    }

    /**
     * @return The text from the input textField
     */
    public String getText() {
        return field.getText();
    }

    /**
     * Shows an error message
     * @param errorMsg
     */
    public void error(String errorMsg) {
        error.setText(errorMsg);
        error.setVisible(true);
    }

}
