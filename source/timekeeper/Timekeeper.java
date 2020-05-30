package timekeeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import timer.Timer;

public class Timekeeper extends Application {

    public static void main(String[] args) {
        Timekeeper.launch(args);
    }

    private Stage stage;
        private Scene scene;
            private BorderPane root;
                private MenuBar menuBar;
                    private Menu settingsMenu;
                        private MenuItem closeItem;
                private ScrollPane timersScrollPane;
                    private VBox timerList;
                private Button addTimer;

    private void setupStage() {
        stage.setTitle("Timekeeper");
        try {
            stage.getIcons().addAll(new Image(getClass().getClassLoader().getResourceAsStream("Icon/TimekeeperIcon256.png")),
                new Image(getClass().getClassLoader().getResourceAsStream("Icon/TimekeeperIcon.png")),
                    new Image(getClass().getClassLoader().getResourceAsStream("Icon/TimekeeperIcon32.png")));
        } catch (Exception e) {
            System.out.println("oops");
            e.printStackTrace();
        }
        stage.setWidth(700);
        stage.setHeight(800);
        stage.show();

        stage.setOnCloseRequest(event -> Platform.exit());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        setupStage();

            root = new BorderPane();
            root.setPadding(new Insets(0,0,10,0));
            root.setFocusTraversable(false);

                menuBar = new MenuBar();

                    settingsMenu = new Menu("Settings");

                        closeItem = new MenuItem("Close");
                        closeItem.setOnAction(event -> Platform.exit());

                    settingsMenu.getItems().addAll(closeItem);

                menuBar.getMenus().addAll(settingsMenu);

                timersScrollPane = new ScrollPane();
                timersScrollPane.minWidthProperty().bind(stage.widthProperty().subtract(30));
                timersScrollPane.maxWidthProperty().bind(timersScrollPane.minWidthProperty());
                timersScrollPane.setFocusTraversable(false);
                timersScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                    timerList = new VBox();
                    timerList.setAlignment(Pos.TOP_CENTER);
                    timerList.setFocusTraversable(false);

                timersScrollPane.setContent(timerList);

                addTimer = new Button("Add Timer");
                addTimer.setMinHeight(50);
                addTimer.maxWidthProperty().bind(stage.widthProperty());
                addTimer.setOnMouseClicked(event -> addTimerOnClick(event));

            root.setTop(menuBar);
            root.setCenter(timersScrollPane);
            root.setBottom(addTimer);
            BorderPane.setAlignment(addTimer, Pos.CENTER);

        scene = new Scene(root);
        stage.setScene(scene);

    }

    private void addTimerOnClick(MouseEvent e) {
        timerList.getChildren().add(new Timer(timersScrollPane.widthProperty()));
    }

}
