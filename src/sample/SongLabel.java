package sample;

import javafx.scene.control.Label;

public class SongLabel extends Label {
    private Song song;
    public void setSong(Song song) {
        this.song = song;
        setText(song == null ? null : song.getSongName());
    }

    public Song getSong() {
        return song;
    }
}
