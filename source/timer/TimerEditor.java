package timer;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TimerEditor extends Stage {

    private Scene scene;
        private VBox root;
            private InputLine name;
            private InputLine time;
            private Button submit;

    private Timer timer;

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
                submit.setOnMouseClicked(event -> onDone(event));

            root.getChildren().addAll(name, time, submit);

        scene = new Scene(root);
        setScene(scene);

        setUp();
    }

    private void setUp() {
        setTitle("Editor");
        setMinWidth(500);
    }

    private void onDone(MouseEvent e) {

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
