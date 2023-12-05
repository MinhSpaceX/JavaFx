package Controller;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import javax.swing.text.LabelView;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Stack;

public class Chat implements Initializable {
    @FXML
    private Circle avatar;
    @FXML
    private Button button;
    @FXML
    private TextField message;
    @FXML
    private VBox vBox;
    @FXML
    ScrollPane scroll_pane;
    @FXML
    ImageView likeButton;
    Image img_tick;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vBox.heightProperty().addListener(o -> scroll_pane.setVvalue(1));
        Image img = null;
        Image like = null;
        try {
            img = new Image(Objects.requireNonNull(getClass().getResource("/gpt.jpg")).toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Image finalImg = img;
        avatar.setFill(new ImagePattern(finalImg));
        try {
            img_tick = new Image(Objects.requireNonNull(getClass().getResource("/tick.png")).toURI().toString());
            like = new Image(Objects.requireNonNull(getClass().getResource("/like.png")).toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        likeButton.setImage(like);
        sendLike();
        message.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && !message.getText().isEmpty()) {
                send();
                receive();
            }
        });
    }

    void send() {
        vBox.setPrefHeight(1000 + vBox.getHeight());
        vBox.getParent().layout();
        ImageView tick = new ImageView();
        tick.setImage(img_tick);
        tick.setFitWidth(20);
        tick.setFitHeight(20);
        Text msg_send = new Text();
        msg_send.getStyleClass().add("text");
        msg_send.setText(message.getText());


        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(msg_send);
        stackPane.getStyleClass().add("message_send");
        stackPane.widthProperty().addListener(observable -> {
            if (stackPane.getWidth() > 200) {
                msg_send.setWrappingWidth(200);
            }
        });
        HBox hBox = new HBox();
        hBox.getStyleClass().add("h-box");
        hBox.getChildren().addAll(stackPane, tick);

        vBox.getChildren().add(hBox);
        message.clear();
        Platform.runLater(() -> {
            vBox.getParent().layout();
            long height = 0;
            for (Node node : vBox.getChildren()) {
                height += (long) node.getBoundsInLocal().getHeight();
            }
            vBox.setPrefHeight(height);
            vBox.layout();
        });
    }


    void receive() {
        vBox.setPrefHeight(1000 + vBox.getHeight());
        vBox.getParent().layout();
        ImageView tick = new ImageView();
        tick.setImage(img_tick);
        tick.setFitWidth(20);
        tick.setFitHeight(20);
        Text msg_send = new Text();
        msg_send.getStyleClass().add("text");
        msg_send.setText("I don't know.");


        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(msg_send);
        stackPane.getStyleClass().add("message_receive");
        stackPane.widthProperty().addListener(observable -> {
            if (stackPane.getWidth() > 200) {
                msg_send.setWrappingWidth(200);
            }
        });
        HBox hBox = new HBox();
        hBox.getStyleClass().add("h-box-rec");
        hBox.getChildren().addAll(tick, stackPane);

        vBox.getChildren().add(hBox);
        message.clear();
        Platform.runLater(() -> {
            vBox.getParent().layout();
            long height = 0;
            for (Node node : vBox.getChildren()) {
                height += (long) node.getBoundsInLocal().getHeight();
            }
            vBox.setPrefHeight(height);
            vBox.layout();
        });
    }
    void sendLike() {
        likeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            ImageView tick = new ImageView();
            tick.setImage(img_tick);
            tick.setFitWidth(20);
            tick.setFitHeight(20);
            ImageView tempLike = new ImageView();
            tempLike.setImage(likeButton.getImage());
            tempLike.setFitWidth(20);
            tempLike.setFitHeight(20);
            HBox hBox = new HBox();
            hBox.getStyleClass().add("h-box");
            hBox.getChildren().addAll(tempLike, tick);
            vBox.getChildren().add(hBox);
            message.clear();
            vBox.setPrefHeight(vBox.getPrefHeight() + hBox.getHeight());
            vBox.heightProperty().addListener(observable -> scroll_pane.setVvalue(1));
        });
    }
}
