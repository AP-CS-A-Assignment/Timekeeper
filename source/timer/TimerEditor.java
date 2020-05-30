package timer;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Editor for a Timer, allows for setting the name and total time
 */
public class TimerEditor extends Stage {

    //Scene Graph
    private Scene scene;
        private VBox root;
            private InputLine name;
            private InputLine time;
            private Button submit;
    /**
     * Timer that the editor is editing
     */
    private Timer timer;

    /**
     * Sets up the scene graph
     * @param _timer Timer that the editor edits
     */
    public TimerEditor(Timer _timer) {
        super();

        timer = _timer;

            root = new VBox(10);
            root.setPadding(new Insets(10));
            root.setAlignment(Pos.TOP_LEFT);

                name = new InputLine("Name", "Name of the Timer");

                time = new InputLine("Time", "Time in format hours:mins:secs\nhours <= 99\nmins <= 59\nsecs <= 59");

                submit = new Button("Done");
                submit.minWidthProperty().bind(this.widthProperty().subtract(40));
                submit.setOnMouseClicked(event -> onDone());

            root.getChildren().addAll(name, time, submit);

        scene = new Scene(root);
        setScene(scene);

        setUp();
    }

    /**
     * Sets up the Editor window
     */
    private void setUp() {
        setTitle("Editor");
        setMinWidth(500);
    }

    /**
     * Called when the submit button is pressed.
     * <br> Converts the string input of the Time inputLine
     * into Hours, Mins, and Secs
     * <br>Constrains hours to 99, mins to 59, and secs to 59
     */
    private void onDone() {

        String timerName = name.getText();
        int hours, mins, secs;
        hours = mins = secs = 0;

        boolean error = false;

        try {
            String timeOutput = time.getText();
            hours = Integer.valueOf(timeOutput.substring(0, timeOutput.indexOf(":")));
            mins = Integer.valueOf(timeOutput.substring(timeOutput.indexOf(":") + 1, timeOutput.lastIndexOf(":")));
            secs = Integer.valueOf(timeOutput.substring(timeOutput.lastIndexOf(":") + 1));
        } catch (Exception nfe) {
            time.error("Please use only numbers in the format Hr:Min:Sec");
            error = true;
        }

        if (hours > 99) hours = 99;
        if (mins > 59) mins = 59;
        if (secs > 59) secs = 59;

        if (!error) {
            timer.setName(timerName);
            setTitle(timerName + " | Editor");
            timer.setTotalTime(hours, mins, secs);

            hide();
        }
    }

}
