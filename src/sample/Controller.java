package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

public class Controller {

    private String DEFAULT_CONTROL_INNER_BACKGROUND = "red";
    private double xOffset = 0;
    private double yOffset = 0;
    private String filepath;
    public MediaPlayer Player;

    static public String directoryName = System.getProperty("user.home").toString();
    static public String appDirectory = directoryName + "/karaokeApp";
    static public HashMap<Label, Song> mapSongsWithLabel = new HashMap<Label, Song>();
    static public HashMap<JFXButton, Song> mapSongsWithButtons = new HashMap<JFXButton, Song>();
    static public HashMap<String,PlayList> mapPlayList=new HashMap<>();
    static public HashMap<HBox,Integer> mapHboxIndex=new HashMap<>();
    static public  Boolean playerStatus=false;

    @FXML
    private PlaylistController childController ;




   static public void initializePlaylists() throws IOException {




//       creating a FileDirectory and and empty file that will be required For the App if doesn't exits already

       //***************************************************************************

       File appTempDirectory = new File(directoryName + "/Karaoketemp");
       if (!appTempDirectory.exists()) {
           appTempDirectory.mkdir();
       }

       File file = new File(directoryName + "/Karaoketemp/Playlists.txt");
       if (file.exists()) {
           System.out.println("Exists");
                  } else {
           System.out.println("Does not Exists");
           file.createNewFile();

       }

       //***************************************************************************
       File fileobject = new File(directoryName + "/Karaoketemp/Playlists.txt");
       System.out.println(fileobject.length());
       if(fileobject.length()>0){
           System.out.println("Constructor Called ");


           PlayList playListsArray[]=null;

           FileInputStream fis = new FileInputStream(new File(directoryName + "/Karaoketemp/Playlists.txt"));
           ObjectInputStream ois = new ObjectInputStream(fis);

           try {
              playListsArray =(PlayList[]) ois.readObject();
           } catch (ClassNotFoundException e) {
               e.printStackTrace();

           }

           for(PlayList playList:playListsArray){

               System.out.println("__________________Playlist is __________"+playList.getPlayListName());
               mapPlayList.put(playList.getPlayListName(),playList);

           }
       }


    }



    @FXML
    private Pane topPane;



    @FXML
    private JFXTextField playListTextField;


    @FXML
    private JFXTextField addtoPlaylistField;


    @FXML
    private Pane centralPanel;

    @FXML
    private Label playlistSearch;

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
    public MediaView mediaView;


    @FXML
    public Label songNameLabel;

    @FXML
    private JFXTextField searchBar;

    @FXML
    private Label searchResults;



//
//    @FXML
//    void OpenFile(ActionEvent event) {
//        try {
//            FileChooser fileChooser = new FileChooser();
//            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("MP4 Files Only !", "*.mp4");
//            fileChooser.getExtensionFilters().add(filter);
//
//            File file = fileChooser.showOpenDialog(null);
//            filepath = file.toURI().toString();
//            if (filepath != null) {
//
//                Media videoFile = new Media(filepath);
//                Player = new MediaPlayer(videoFile);
//                mediaView.setMediaPlayer(Player);
//
//
////                Fit the MediaView The whole  StackPane( MediaPane )
////                DoubleProperty mvw = mediaView.fitWidthProperty();
////                DoubleProperty mvh = mediaView.fitHeightProperty();
////                mvw.bind(Bindings.selectDouble( mediaView.sceneProperty(), "width"));
////                mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
////                mediaView.setPreserveRatio(true);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


    String addVideopathToSong() {

        String videoFilePath = null;
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("MP4 Files Only !", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);

            File file = fileChooser.showOpenDialog(null);
            filepath = file.toURI().toString();
            if (filepath != null) {
                System.out.println("Yes FilePath isHere " + filepath);
                Media videoFile = new Media(filepath);
                Player = new MediaPlayer(videoFile);
                mediaView.setMediaPlayer(Player);

                videoFilePath = filepath;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------RECEIVED FILE PATH FOR KARAOKE VIDEO -------" + videoFilePath);
        return videoFilePath;

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
    void PopulateListView(ActionEvent event) throws IOException {

        SongLibrary.getItems().clear();
//        songList with songNumber->songName->videopath
        HashMap<String, HashMap<String, String>> songList = new HashMap<>();


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
            for (int i = 0; i < arr.length; i++) {
                File filename = arr[i];
                String songDirectoryName = filename.toString();
                songDirectoryName = songDirectoryName.substring(songDirectoryName.lastIndexOf("\\") + 1, songDirectoryName.length());

//                File songDirectory = new File(appDirectory+songDirectoryName);
                if (songList.containsKey(songDirectoryName)) {

                } else {
                    HashMap<String, String> songsMap = new HashMap<>();
                    songsMap.put("Playing " + songDirectoryName, "videopath");
                    songList.put(songDirectoryName, songsMap);
                }


            }


        }

//*****************************************************************************

        if (songList.isEmpty()) {
            Label listLabel = new Label("No Songs ! Add Songs From using pdf file ");
            HBox hbox = new HBox();
            hbox.getChildren().addAll(listLabel);
            SongLibrary.getItems().add(hbox);
            SongLibrary.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {

                    System.out.println("________________GOT HERE____________");


                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("pdf Files Only !", "*.pdf");
                    fileChooser.getExtensionFilters().add(filter);

                    File file = fileChooser.showOpenDialog(null);
                    filepath = file.toURI().toString();
                    filepath = filepath.substring(filepath.indexOf(":") + 2, filepath.length());

                    System.out.println("File Path Formatted : " + filepath);

                    PDFManager pdfManager = new PDFManager();
                    pdfManager.setFilePath(filepath);
                    try {
                        String text = pdfManager.toText();
//            System.out.println("TEXT Formatted is here :: \n "+text);
                    } catch (IOException ex) {
                        System.out.println("_______ERROR______" + ex);
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }


                }
            });


        }

        else
        {

            int index=0;
            for (String key : songList.keySet()) {

//            System.out.println(" "+key);
                FileInputStream fis = new FileInputStream(new File(directoryName + "/karaokeApp/" + key + "/songDetails.txt"));
                ObjectInputStream ois = new ObjectInputStream(fis);
                Song s1 = null;
                try {
                    s1 = (Song) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
//            System.out.println("____________________\n"+);


                HBox hbox = new HBox();
                Pane pane = new Pane();
                String SONG_NAME = s1.getSongName().length() > 30 ? s1.getSongName().substring(0, 25) + "..." : s1.getSongName();

                Label listLabel = new Label(SONG_NAME);
                mapSongsWithLabel.put((Label) listLabel, (Song) s1);


                JFXButton btn = new JFXButton("+");
                mapSongsWithButtons.put((JFXButton) btn, (Song) s1);
                btn.setStyle("-fx-text-fill:#10ac84;");
                hbox.getChildren().addAll(listLabel, pane, btn);
                HBox.setHgrow(pane, Priority.ALWAYS);

                Song finalS = s1;

                mapHboxIndex.put(hbox,index);
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        //Delete the file already present there before writing new
                        File fileObject = new File(directoryName + "/karaokeApp/" + key + "/songDetails.txt");
                        fileObject.delete();



                        if(btn.getText()=="+")
                        {
//                        System.out.println(" I : " + finalI + "" + event);
                            String pathToVideo= addVideopathToSong();
                            System.out.println("------Answer-----"+pathToVideo);
                            mapSongsWithButtons.get(btn).setKaraokeVideoPath(pathToVideo);

                            System.out.println("=====Right Song =======or not===== "+      mapSongsWithButtons.get(btn).getSongName());
                            songNameLabel.setText( mapSongsWithButtons.get(btn).getSongName());
                            songNameLabel.setTextFill(Paint.valueOf("#fdcb6e"));

                            //Adding Karaoke And saving the data
                            FileOutputStream fileOut = null;
                            try {
                                fileOut = new FileOutputStream(Controller.directoryName + "/karaokeApp/" + key + "/songDetails.txt");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            ObjectOutputStream objectOut = null;
                            try {
                                objectOut = new ObjectOutputStream(fileOut);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                objectOut.writeObject(finalS);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                objectOut.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }

//          ------------------------------------- Future reference --------------------
//                    System.getProperties().list(System.out);
//                    System.out.println(System.getProperty("user.home"));
//                    System.out.println(System.getProperty("os.name"));
                    }
                });


                SongLibrary.getItems().add(hbox);

                index++;
            }

            SongLibrary.setStyle("-fx-control-inner-background: #222f3e ;");

            //Cell Click Functionality Implementation
            SongLibrary.setOnMouseClicked(new EventHandler<MouseEvent>() {



                @Override
                public void handle(MouseEvent event) {

                    HBox selectedHbox= SongLibrary.getSelectionModel().getSelectedItem();
                    System.out.println("___________________SelectedItem Index___________"+mapHboxIndex.get(selectedHbox));
                    Label selectedSongLabel = (Label) SongLibrary.getSelectionModel().getSelectedItem().getChildren().get(0);
                    String selectedSong = mapSongsWithLabel.get(selectedSongLabel).getSongName();
                    System.out.println("Selected Song is  ===>>>>> " + mapSongsWithLabel.get(selectedSongLabel).getSongName());

                    System.out.println("_________________________Video path Present_________________"+ mapSongsWithLabel.get(selectedSongLabel).getKaraokeVideoPath());
                    String fileVideoPathToSong=mapSongsWithLabel.get(selectedSongLabel).getKaraokeVideoPath();


                    if (Player !=null) {

                        System.out.println("+++++++++++++++++"+Player);
//                    Player.stop();
                        Player.dispose();
                    }



                    if(playerStatus==false){
                        if(fileVideoPathToSong!=null){
                            Media videoFile = new Media(fileVideoPathToSong);

                            Player = new MediaPlayer(videoFile);

                            mediaView.setMediaPlayer(Player);
//                        Player.stop();
                            Player.play();
//                        playerStatus=true;
                            songNameLabel.setText(selectedSong);
                            songNameLabel.setTextFill(Paint.valueOf("#fdcb6e"));
                        }
                        else
                        {
//
//                    Player.stop();
                            songNameLabel.setText("This Song Doesn't have a karaoke  video \n Set Yet  ! add by clicking + button");
                            songNameLabel.setTextFill(Paint.valueOf("red"));
                        }

                    }

//                String selectedSongLabel=SongLibrary.getSelectionModel().getSelectedItem().getChildren().get(0).toString();
//                selectedSongLabel=selectedSongLabel.substring(selectedSongLabel.indexOf("'")+1,selectedSongLabel.length()-1);
//                System.out.println("clicked on " + selectedSongLabel);



                }
            });


        }




    }


//    @FXML
//    void getDataFromFile(ActionEvent event) throws IOException {
//
//
//
//    }


    @FXML
    void searchSong(ActionEvent event) {
        String songName;
        String searchString;
        int count = 0;
        Song song;
        ArrayList<Song> songArrayList = new ArrayList<>();
        ArrayList<Label> labelList = new ArrayList<>();

        System.out.println("GOT CALLED :::");

        for (Label key : mapSongsWithLabel.keySet()) {

            song = mapSongsWithLabel.get(key);
            songName = mapSongsWithLabel.get(key).getSongName().toLowerCase();

            searchString = searchBar.getText().toLowerCase();
//      System.out.println("Song :: "+songName    +"_____searchString_____"+searchString+"____Do they Match ");


            if (songName.contains(searchString)) {
                count++;
                songArrayList.add(song);
                System.out.println("Yay Found " + count + " :Result :: " + songName);
                labelList.add(key);
            }


        }

        SongLibrary.getItems().clear();
        //Adding Search Results to listView

        for (int i = 0; i < songArrayList.size(); i++) {

            HBox hbox = new HBox();
            Pane pane = new Pane();


//        Label listLabel = new Label(songArrayList.get(i).getSongName());
//        mapSongs.put((Label)listLabel, (Song)s1);

//            SongLabel listLabel=new SongLabel(s1);

//            ((Label)component).putClientProperty("person", value);

            JFXButton btn = new JFXButton("+");
            btn.setStyle("-fx-text-fill:#10ac84;");
            hbox.getChildren().addAll(labelList.get(i), pane, btn);
            HBox.setHgrow(pane, Priority.ALWAYS);
//        String finalI = key;
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    System.out.println(System.getProperty("user.home"));
                    System.out.println(System.getProperty("os.name"));
                }
            });


            SongLibrary.getItems().add(hbox);

        }
        searchResults.setText("Found Results : " + count);

    }





    //**********************PLAYLISTS*****************************

    @FXML
    void showPlayLists(ActionEvent event) throws IOException {

        Parent playlists = FXMLLoader.load(getClass().getResource("Playlists.fxml"));
        Stage playlistStage=new Stage();
        playlistStage.setScene(new Scene(playlists));
        playlistStage.show();


    }



    @FXML
    void createPlayList(ActionEvent event) {


        Label selectedSongLabel = (Label) SongLibrary.getSelectionModel().getSelectedItem().getChildren().get(0);
        System.out.println("_________SE_________"+selectedSongLabel.getText());
        try{
            String playlistName=playListTextField.getText();

           if(playlistName!=null){

               if(selectedSongLabel==null)
               {
                   PlayList playList=new PlayList(playlistName);
                   mapPlayList.put(playlistName,playList);
               }
               else
               {
                   Song selectedSong = mapSongsWithLabel.get(selectedSongLabel);
                   PlayList playList=new PlayList(playlistName,selectedSong);
                   mapPlayList.put(playlistName,playList);
               }
           }

        }
        catch (Exception ex)
        {
            System.out.println("__EXCEPTION _OCCURRED__"+ex);
            throw ex;
        }


    }

    @FXML
    void addToPlaylist(ActionEvent event) {

String playlistName=addtoPlaylistField.getText();

PlayList playListObject= mapPlayList.get(playlistName);
if(playListObject==null){
 playlistSearch.setText("Sorry ! No Playlist found \n of the name you entered");
 playlistSearch.setTextFill(Paint.valueOf("Orange"));

}

Label selectedSongLabel = (Label) SongLibrary.getSelectionModel().getSelectedItem().getChildren().get(0);

Song selectedSong=mapSongsWithLabel.get(selectedSongLabel);
playListObject.AddSongtoPlayList(selectedSong);
System.out.println("Songname ==> "+selectedSong.getSongName()+" is Added to Playlist "+playlistName);

    }





//SavePlaylists To File

    @FXML
    void savePlaylist(ActionEvent event) throws IOException {

        FileOutputStream fileOut = new FileOutputStream(Controller.directoryName + "/Karaoketemp/Playlists.txt");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

         PlayList playListArray[]=mapPlayList.values().toArray(PlayList[]::new);
        objectOut.writeObject(playListArray);
        objectOut.close();

    }


    @FXML
    void PreviousSong(ActionEvent event) {
        HBox selectedHbox= SongLibrary.getSelectionModel().getSelectedItem();
        System.out.println("___________________SelectedItem Index___________"+mapHboxIndex.get(selectedHbox));
        int currentIndex=mapHboxIndex.get(selectedHbox);

        if(currentIndex!=0)
        {
            SongLibrary.getSelectionModel().select(currentIndex-1);
            HBox PreviousHbox= SongLibrary.getSelectionModel().getSelectedItem();
            Label selectedLabel=(Label) PreviousHbox.getChildren().get(0);


            String fileVideoPathToSong=mapSongsWithLabel.get(selectedLabel).getKaraokeVideoPath();


            if (Player !=null) {

                System.out.println("+++++++++++++++++"+Player);
//                    Player.stop();
                Player.dispose();
            }


            String selectedSong= mapSongsWithLabel.get(selectedLabel).getSongName();

            if(playerStatus==false){
                if(fileVideoPathToSong!=null){
                    Media videoFile = new Media(fileVideoPathToSong);

                    Player = new MediaPlayer(videoFile);

                    mediaView.setMediaPlayer(Player);
//                        Player.stop();
                    Player.play();
//                        playerStatus=true;
                    songNameLabel.setText(selectedSong);
                    songNameLabel.setTextFill(Paint.valueOf("#fdcb6e"));
                }
                else
                {
//
//                    Player.stop();
                    songNameLabel.setText("This Song Doesn't have a karaoke  video \n Set Yet  ! add by clicking + button");
                    songNameLabel.setTextFill(Paint.valueOf("red"));
                }

            }



            SongLibrary.getFocusModel().focus(currentIndex-1);

        }

    }


    @FXML
    void NextSong(ActionEvent event) {

        HBox selectedHbox= SongLibrary.getSelectionModel().getSelectedItem();

        System.out.println("___________________SelectedItem Index___________"+mapHboxIndex.get(selectedHbox));
        int currentIndex=mapHboxIndex.get(selectedHbox);

        if(currentIndex!=mapHboxIndex.size())
        {
             SongLibrary.getSelectionModel().select(currentIndex+1);
            HBox NextHbox= SongLibrary.getSelectionModel().getSelectedItem();
         Label selectedLabel=(Label) NextHbox.getChildren().get(0);


            String fileVideoPathToSong=mapSongsWithLabel.get(selectedLabel).getKaraokeVideoPath();


            if (Player !=null) {

                System.out.println("+++++++++++++++++"+Player);
//                    Player.stop();
                Player.dispose();
            }


            String selectedSong= mapSongsWithLabel.get(selectedLabel).getSongName();

            if(playerStatus==false){
                if(fileVideoPathToSong!=null){
                    Media videoFile = new Media(fileVideoPathToSong);

                    Player = new MediaPlayer(videoFile);

                    mediaView.setMediaPlayer(Player);
//                        Player.stop();
                    Player.play();
//                        playerStatus=true;
                    songNameLabel.setText(selectedSong);
                    songNameLabel.setTextFill(Paint.valueOf("#fdcb6e"));
                }
                else
                {
//
//                    Player.stop();
                    songNameLabel.setText("This Song Doesn't have a karaoke  video \n Set Yet  ! add by clicking + button");
                    songNameLabel.setTextFill(Paint.valueOf("red"));
                }

            }


            SongLibrary.getFocusModel().focus(currentIndex+1);
//            SongLibrary.getFocusModel().





        }


    }

    public static void click(int x, int y) throws AWTException {
        Robot bot = new Robot();
        bot.mouseMove(x, y);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }





}


