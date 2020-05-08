package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

public class PlaylistController {

    private HashMap<Button,Song> playlistSongsMap=new HashMap<>();
    private HashMap<Label,Song> playlistSongsMapWithLabel=new HashMap<>();


    @FXML
    private Label PlayListNameLabel;

    @FXML
    public JFXListView<Label> PlaylistLibrary;

    @FXML
    private JFXListView<HBox> PlaylistSongsLibrary;

    static public Label songNameLabel;
    static public MediaView mediaView;
    public MediaPlayer Player;

    public static void initialize(Parent root) {

        BorderPane Bpane= (BorderPane) root;
        Pane pane= (Pane) Bpane.getCenter();
        songNameLabel= (Label) pane.getChildren().get(2);
        mediaView=(MediaView) pane.getChildren().get(0);

        System.out.println("_____________"+root);

    }


    @FXML
    void LoadPlaylist(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        BorderPane p = fxmlLoader.load(getClass().getResource("sample.fxml").openStream());
        Controller ParentController = (Controller) fxmlLoader.getController();

        //Access Parent Controller

//        FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource("sample.fxml"));
////        fxmlLoader.setController(this);
//        Parent root = fxmlLoader.load();
//        Controller ParentController = fxmlLoader.getController();
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
//        Parent root = loader.load();
//        Controller ParentController = loader.getController();
//        Controller Parent = (Controller) fxmlLoader.getController();



        PlaylistLibrary.getItems().clear();
        for (String playlistKey : Controller.mapPlayList.keySet()) {

            System.out.println("--------"+playlistKey);
            Label myPlaylistName=new Label(playlistKey);

            PlaylistLibrary.getItems().addAll(myPlaylistName);
            PlaylistLibrary.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    PlaylistSongsLibrary.getItems().clear();

//                    System.out.println("You clicked "+ PlaylistLibrary.getSelectionModel().getSelectedItem().getText());
                    String playlistSelected= PlaylistLibrary.getSelectionModel().getSelectedItem().getText();
                    PlayListNameLabel.setText("Playlist Name : "+playlistSelected);
                    PlayList selectedPlaylist =Controller.mapPlayList.get(playlistSelected);

//                    System.out.println(selectedPlaylist.getAllSongsofPlayList());
                    HashSet<Song> playlistSongs=selectedPlaylist.getAllSongsofPlayList();



                    for (Song song : playlistSongs){



                        HBox hBox =new HBox();
                        Pane pane =new Pane();
                        JFXButton btn = new JFXButton("remove");
                        btn.setStyle("-fx-text-fill:red");
                        String SONG_NAME = song.getSongName().length() > 30 ? song.getSongName().substring(0, 25) + "..." : song.getSongName();
                        Label SongLaboulay=new Label(SONG_NAME);
                        hBox.getChildren().addAll(SongLaboulay,pane,btn);
                        HBox.setHgrow(pane, Priority.ALWAYS);

                        playlistSongsMap.put(btn,song);
                        playlistSongsMapWithLabel.put(SongLaboulay, song);


//                            playlistSongsMap.put(song.getSongName(), new Label(song.getSongName()));
                        PlaylistSongsLibrary.getItems().add(hBox);
                        btn.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {


                                System.out.println("_________ Called Remove From Playlist _________"+playlistSongsMap.get(btn).getSongName());

                                selectedPlaylist.RemoveSongFromPlaylist(playlistSongsMap.get(btn));

                                PlaylistSongsLibrary.getItems().remove(hBox);

                            }
                        });



                         }

//                    for(String key:playlistSongsMap.keySet()){
//
//
//
//                    }


PlaylistSongsLibrary.setOnMouseClicked(new EventHandler<MouseEvent>() {



    @Override
    public void handle(MouseEvent event) {


        Label selectedSongLabel = (Label) PlaylistSongsLibrary.getSelectionModel().getSelectedItem().getChildren().get(0);
        String selectedSong = playlistSongsMapWithLabel.get(selectedSongLabel).getSongName();
        String fileVideoPathToSong=playlistSongsMapWithLabel.get(selectedSongLabel).getKaraokeVideoPath();
        System.out.println("_____________Playlist song video path _________"+fileVideoPathToSong);



songNameLabel.setText(selectedSongLabel.getText());

        if (Player != null) {
       Player.dispose();
        }

            if(fileVideoPathToSong!=null){
                Media videoFile = new Media(fileVideoPathToSong);

                Player = new MediaPlayer(videoFile);

                mediaView.setMediaPlayer(Player);
              Player.stop();
             Player.play();

                songNameLabel.setTextFill(Paint.valueOf("#fdcb6e"));



            }
            else
            {
                songNameLabel.setTextFill(Paint.valueOf("red"));
                Player.stop();
                songNameLabel.setText("This Song Doesn't have a karaoke  video \n Set Yet  ! add by clicking + button");
            }


    }
});

                }
            });

        }


//        PlaylistSongsLibrary.getItems().addListener();




    }


    public  PlaylistController() throws IOException {

    }


    public void setLabel(String txt) {


    }
}
