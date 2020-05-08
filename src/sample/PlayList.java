package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class PlayList implements Serializable {
    private String playListName;
    private HashSet<Song> song = new HashSet<Song>();

    //Creating a Playlist without AnySong
    public PlayList(String name) {
        this.playListName = name;

        }

    public HashSet<Song> getAllSongsofPlayList(){
        return song;
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
          return false;
        }
    }


    public String getPlayListName()
    {
     return  this.playListName ;
    }

    public Boolean RemoveSongFromPlaylist(Song song)
    {
        try {
            this.song.remove(song);
            return true;
        } catch (Exception ex) {
            System.out.println("__ERROR_ENCOUNTERED__" + ex);
            return false;
        }

    }


}
