package sample;

import java.util.ArrayList;

public class PlayList {
    private String playListName;
    private ArrayList<Song> song = new ArrayList<Song>();

    //Creating a Playlist without AnySong
    public PlayList(String name) {
        this.playListName = name;

        }

    //Creating a Playlist with A Song
    public PlayList(String name, Song song) {
        this.playListName = name;
        this.song.add(song);
    }

    public Boolean AddSongtoPlayList(Song song) {
        try {
            this.song.add(song);
            return true;
        } catch (Exception ex) {
            System.out.println("__ERROR_ENCOUNTERED__" + ex);
            throw ex;
        }
    }


    public String getPlayListName()
    {
     return  this.playListName   ;
    }

}
