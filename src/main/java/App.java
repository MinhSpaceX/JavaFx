import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Chat.fxml"))));
        stage.setScene(scene);
        stage.setTitle("Chat");
        stage.getIcons().add(new Image(getClass().getResource("/icon.png").toURI().toString()));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
