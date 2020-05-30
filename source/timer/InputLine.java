package timer;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;


public class InputLine extends HBox {

    private Label name;
    private TextField field;
    private Label error;

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

    public String getText() {
        return field.getText();
    }

    public void error(String errorMsg) {
        error.setText(errorMsg);
        error.setVisible(true);
    }

}
