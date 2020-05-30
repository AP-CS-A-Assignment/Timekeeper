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

public class Timer extends StackPane {

    private Rectangle bkgd;
    private BorderPane mainBorderPane;
        private HBox leftHBox;
            private Label countdownTime;
            private Label name;
        private HBox rightHBox;
            private Button pausePlay;
            private Button reset;
            private Button edit;

    private TimerEditor editor;
    private TimePiece timePiece;
    private Timeline timeLine;

    int hour, min, sec, totHour, totMin, totSec;

    private String play = "▶";
    private String pause = "\u23F8";//Pause Icon

    public Timer(ReadOnlyDoubleProperty widthProperty) {
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

            rightHBox.getChildren().addAll(pausePlay, reset, edit);

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

    void setName(String nameStr) {
        name.setText(nameStr);
    }

    void setTotalTime(int hours, int mins, int secs) {
        totHour = hour =hours;
        totMin = min = mins;
        totSec = sec = secs;
       setCurrentTime();
        setBackground(new Background(new BackgroundFill(Color.BEIGE, new CornerRadii(5), Insets.EMPTY)));
    }

    void onReset() {
        hour = totHour;
        min = totMin;
        sec = totSec;
        setBackground(new Background(new BackgroundFill(Color.BEIGE, new CornerRadii(5), Insets.EMPTY)));
        setCurrentTime();
    }

    void setCurrentTime() {
        countdownTime.setText(String.format("%1$02d:%2$02d:%3$02d/%4$02d:%5$02d:%6$02d", hour, min, sec, totHour, totMin, totSec));
    }

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

    private boolean isPlay = true;

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

    private void onEdit() {
        editor.show();
    }
}
