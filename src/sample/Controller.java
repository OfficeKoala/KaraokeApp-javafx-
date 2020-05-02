package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    private String filepath;
    private MediaPlayer Player;

    @FXML
    private Button mPlayerbtn_play;

    @FXML
    private Button mPlayerbtn_stop;

    @FXML
    private MediaView mediaView;



    @FXML
    void OpenFile(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("MP4 Files Only !", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);

            File file = fileChooser.showOpenDialog(null);
            filepath = file.toURI().toString();
            if (filepath != null) {
                System.out.println("Hello fucktard filepath to the media is  " + filepath);
                Media videoFile = new Media(filepath);
                Player = new MediaPlayer(videoFile);
                mediaView.setMediaPlayer(Player);


                //Fit the MediaView The whole  StackPane( MediaPane )
                DoubleProperty mvw = mediaView.fitWidthProperty();
                DoubleProperty mvh = mediaView.fitHeightProperty();
                mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                mediaView.setPreserveRatio(true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    @FXML
    void playMediaPlayer(ActionEvent event) {
Player.play();
    }

    @FXML
    void stopMediaPlayer(ActionEvent event) {
Player.stop();

    }


}

