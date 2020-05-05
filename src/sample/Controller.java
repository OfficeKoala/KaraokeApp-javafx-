package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private String DEFAULT_CONTROL_INNER_BACKGROUND = "red";
    private double xOffset = 0;
    private double yOffset = 0;
    private String filepath;
    private MediaPlayer Player;
   static public String directoryName = System.getProperty("user.home").toString();
   static public String appDirectory = directoryName + "/karaokeApp";

    @FXML
    private Pane topPane;

    @FXML
    private Pane centralPanel;

    @FXML
    private JFXButton btn_minimize;

    @FXML
    private JFXListView<HBox> SongLibrary;

    @FXML
    private JFXButton btn_close;

    @FXML
    private Button mPlayerbtn_play;

    @FXML
    private Button mPlayerbtn_stop;

    @FXML
    private MediaView mediaView;


    @FXML
    private Label songNameLabel;


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


//                Fit the MediaView The whole  StackPane( MediaPane )
//                DoubleProperty mvw = mediaView.fitWidthProperty();
//                DoubleProperty mvh = mediaView.fitHeightProperty();
//                mvw.bind(Bindings.selectDouble( mediaView.sceneProperty(), "width"));
//                mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
//                mediaView.setPreserveRatio(true);

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

    @FXML
    void pauseMediaPlayer(ActionEvent event) {
        Player.pause();
    }


    @FXML
    void handleSceneClose(ActionEvent event) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();

    }

    @FXML
    void handleSceneMinimize(ActionEvent event) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.setIconified(true);
    }
//
//    @FXML
//    void handleClickAction(MouseEvent event) {
//        Stage stage=(Stage) topPane.getScene().getWindow();
//        xOffset=stage.getX()-event.getSceneX();
//        yOffset=stage.getY()-event.getSceneY();
//        System.out.println("Clicked ");
//
//    }
//
//    @FXML
//    void handleMovementAction(MouseEvent event) {
//        Stage stage=(Stage) topPane.getScene().getWindow();
//        stage.setX(event.getSceneX()+xOffset);
//        stage.setY(event.getSceneY()+yOffset);
//
//    }


    @FXML
    void PopulateListView(ActionEvent event) throws FileNotFoundException {
//        songList with songNumber->songName->videopath
        HashMap<String, HashMap<String, String>> songList= new HashMap<>();


        //Making A Project Directory for Cross-Platform

        File directory = new File(appDirectory);
        if (!directory.exists()) {
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

//*****************************************************************************
        File maindir = new File(appDirectory);

        if (maindir.exists() && maindir.isDirectory()) {

            FileFilter fileFilter = new FileFilter() {
                public boolean accept(File file) {
                    return file.isDirectory();
                }
            };

            File arr[] = maindir.listFiles(fileFilter);

            System.out.println("**********************************************");
            System.out.println("Files from main directory : " + maindir);
            System.out.println("**********************************************");
            System.out.println("Files from main directory : " + arr.length);
            for(int i=0;i<arr.length;i++)
            {
                File filename = arr[i];
                String songDirectoryName=filename.toString();
                songDirectoryName=songDirectoryName.substring(songDirectoryName.lastIndexOf("\\")+1,songDirectoryName.length());

                System.out.println(songDirectoryName);

//                File songDirectory = new File(appDirectory+songDirectoryName);
                if(songList.containsKey(songDirectoryName))
                {

                }
                else
                {
                    HashMap<String, String> songsMap=new HashMap<>();
                    songsMap.put("Playing "+songDirectoryName,"videopath");
                    songList.put(songDirectoryName,songsMap);
                }




            }


        }

//*****************************************************************************

if(songList.isEmpty())
{
    Label listLabel = new Label("No Songs ! Add Songs By uploading a file");
    HBox hbox = new HBox();
    hbox.getChildren().addAll(listLabel);
    SongLibrary.getItems().add(hbox);
    SongLibrary.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
//                System.out.println("clicked on " + SongLibrary.getSelectionModel().getSelectedItem().getText());
//                songNameLabel.setText(SongLibrary.getSelectionModel().getSelectedItem().getChildren().get());

        }
    });
}

        for (String key : songList.keySet()) {
//            System.out.println("Key = " + key);
            HBox hbox = new HBox();
            Pane pane = new Pane();
            Label listLabel = new Label(key);
            JFXButton btn = new JFXButton("+");
            btn.setStyle("-fx-text-fill:#10ac84;");
            hbox.getChildren().addAll(listLabel, pane, btn);
            HBox.setHgrow(pane, Priority.ALWAYS);
            String finalI = key;
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    System.out.println(" I : " + finalI + "" + event);
//                    System.getProperties().list(System.out);
                    System.out.println(System.getProperty("user.home"));
                    System.out.println(System.getProperty("os.name"));

                    File songDirectory = new File(directoryName + "/karaokeApp/song" + finalI);
                    if (!songDirectory.exists()) {
                        songDirectory.mkdir();

                    }


                }
            });
//    listLabel.setGraphic(new ImageView(new Image(new FileInputStream("tone.png")))) ;

            SongLibrary.getItems().add(hbox);
        }



//        for (int i = 1; i < songList.size(); i++) {
//
//
//
//        }

        SongLibrary.setStyle("-fx-control-inner-background: #222f3e ;");
        SongLibrary.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
//                System.out.println("clicked on " + SongLibrary.getSelectionModel().getSelectedItem().getText());
//                songNameLabel.setText(SongLibrary.getSelectionModel().getSelectedItem().getChildren().get());

            }
        });


    }


    @FXML
    void getDataFromFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("pdf Files Only !", "*.pdf");
        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(null);
        filepath = file.toURI().toString();
        filepath=filepath.substring(filepath.indexOf(":")+2,filepath.length());

        System.out.println("File Path Formatted : "+filepath);

        PDFManager pdfManager = new PDFManager();
        pdfManager.setFilePath(filepath);
        try {
            String text = pdfManager.toText();
//            System.out.println("TEXT Formatted is here :: \n "+text);
        } catch (IOException ex) {
            System.out.println("_______ERROR______"+ex);
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("All the data that you want ! fucker ");
    }


}

