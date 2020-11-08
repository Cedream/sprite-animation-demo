import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main class
 * @author Cédric Thonus (cedream)
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.web("#404061"), CornerRadii.EMPTY, Insets.EMPTY)));
        Boy boy = new Boy();
        root.setCenter(boy);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.UP) {
                boy.jump();
            }

            if (e.getCode() == KeyCode.W) {
                boy.wounded();
            }

            if (e.getCode() == KeyCode.RIGHT) {
                boy.walk();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }

}