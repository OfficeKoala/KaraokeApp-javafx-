package sample;

import java.io.Serializable;

public class Song implements Serializable {
    private String SongName;
    private String ArtistName;
    private String Playlist;
    private String KaraokeVideoPath;

    public Song(String name, String artistName) {
        this.SongName = name;
        this.ArtistName = artistName;
    }

    public void setSongName(String fname) {
        this.SongName = fname;
    }

    public String getSongName() {
        return this.SongName;
    }

    public void setArtistName(String artistName) {
        this.ArtistName = artistName;
    }

    public void setKaraokeVideoPath(String videoPath) {
        this.KaraokeVideoPath = videoPath;
    }

    public String getKaraokeVideoPath() {
        return this.KaraokeVideoPath;
    }

    public String getArtistName() {
        return this.ArtistName;
    }


}
