package timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import timekeeper.Timekeeper;

/**
 * A Timer that displays the time remaining, total time, and its name as well as a few buttons
 */
public class Timer extends StackPane {

    //Scene Graph
    private Rectangle bkgd;
    private BorderPane mainBorderPane;
        private HBox leftHBox;
            private Label countdownTime;
            private Label name;
        private HBox rightHBox;
            private Button pausePlay;
            private Button reset;
            private Button edit;
            private Button delete;

    /**
     * Editor window to set the name and total time for the Timer
     */
    private TimerEditor editor;
    /**
     * Timeline tha is used update the time on the Timer
     */
    private Timeline timeLine;

    //The current times and total times respectively
    int hour, min, sec, totHour, totMin, totSec;

    /**
     * Unicode character for the play button
     */
    private String play = "▶";
    /**
     * Unicode character for the pause button
     */
    private String pause = "\u23F8";//Pause Icon

    /**
     * Constructor that intializes the node graph and fields
     * @param widthProperty Width that the timer conforms to minus some padding
     * @param timekeeper Parent of the Timer
     */
    public Timer(ReadOnlyDoubleProperty widthProperty, Timekeeper timekeeper) {
        super();

        hour = min = sec = totHour = totMin = totSec = 0;

        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        Font buttonFont = Font.font(20);

        bkgd = new Rectangle();
        bkgd.widthProperty().bind(widthProperty.subtract(23));
        bkgd.setFill(Color.BEIGE);

        mainBorderPane = new BorderPane();
        mainBorderPane.minWidthProperty().bind(bkgd.widthProperty());
        bkgd.heightProperty().bind(mainBorderPane.heightProperty());

            leftHBox = new HBox(10);
            leftHBox.setAlignment(Pos.CENTER);

                countdownTime = new Label("--/--");
                countdownTime.setFont(buttonFont);

                name = new Label("--");
                name.setFont(buttonFont);
                name.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));

            leftHBox.getChildren().addAll(countdownTime, name);

            rightHBox = new HBox(10);
            rightHBox.setAlignment(Pos.CENTER);

                pausePlay = new Button(play);
                pausePlay.setFont(buttonFont);
                pausePlay.setOnMouseClicked(event -> onPausePlay());

                reset = new Button("↺");
                reset.setFont(buttonFont);
                reset.setOnMouseClicked(event -> onReset());

                edit = new Button("\u2699");//GearIcon
                edit.setFont(buttonFont);
                edit.setOnMouseClicked(event -> onEdit());

                delete = new Button( "X");
                delete.setFont(buttonFont);
                delete.setOnMouseClicked(event -> {
                    timekeeper.deleteTimer(this);
                });

            rightHBox.getChildren().addAll(pausePlay, reset, edit, delete);

        mainBorderPane.setLeft(leftHBox);
        BorderPane.setAlignment(leftHBox, Pos.CENTER_LEFT);
        mainBorderPane.setRight(rightHBox);
        BorderPane.setAlignment(rightHBox,Pos.CENTER_RIGHT);
        getChildren().addAll(mainBorderPane);

        editor = new TimerEditor(this);
        editor.show();

        timeLine = new Timeline(new KeyFrame(Duration.millis(1000), event -> updateTime()));
        timeLine.setCycleCount(Timeline.INDEFINITE);

    }

    /**
     * Sets the name of the Timer
     * @param nameStr name to give the Timer
     */
    void setName(String nameStr) {
        name.setText(nameStr);
    }

    /**
     * Sets the total amount of time for the Timer
     * @param hours Number of Hours
     * @param mins Number of Minutes
     * @param secs Number of Seconds
     */
    void setTotalTime(int hours, int mins, int secs) {
        totHour = hour =hours;
        totMin = min = mins;
        totSec = sec = secs;
       setCurrentTime();
        setBackground(new Background(new BackgroundFill(Color.BEIGE, new CornerRadii(5), Insets.EMPTY)));
    }

    /**
     * Resets the Timer
     */
    void onReset() {
        hour = totHour;
        min = totMin;
        sec = totSec;
        setBackground(new Background(new BackgroundFill(Color.BEIGE, new CornerRadii(5), Insets.EMPTY)));
        setCurrentTime();
    }

    /**
     * Updates the time displayed by the Timer based on the time fields
     */
    void setCurrentTime() {
        countdownTime.setText(String.format("%1$02d:%2$02d:%3$02d/%4$02d:%5$02d:%6$02d", hour, min, sec, totHour, totMin, totSec));
    }

    /**
     * Called by the timeline every second and updates the time accordingly
     * <br>When the time reaches 00:00:00 it is paused and turned red
     */
    private void updateTime() {
        if (hour > 0 || min > 0 || sec > 0) {
            sec--;
            if (sec < 0) {
                sec = 59;
                min--;
                if (min < 0) {
                    min = 59;
                    hour--;
                }
            }
            setCurrentTime();
            System.out.println(countdownTime.getText());
        } else {
            timeLine.stop();
            setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(5), Insets.EMPTY)));
            onPausePlay();
        }
    }

    /**
     * represents if the Pause/Play button is in Play mode
     */
    private boolean isPlay = true;

    /**
     * Called when the PausePlay button is pressed
     * <br> Pauses and plays the timeline and
     * flips the icon from pause to tplay nad vice-versa
     */
    private void onPausePlay() {
        if (isPlay) {
            pausePlay.setText(pause);
            timeLine.play();
        } else {
            pausePlay.setText(play);
            timeLine.stop();
        }
        isPlay = !isPlay;
    }

    /**
     * Called by the edit button
     * <br>Opens the editor
     */
    private void onEdit() {
        editor.show();
    }
}
