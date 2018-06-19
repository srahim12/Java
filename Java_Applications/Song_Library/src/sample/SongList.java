package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Shah on 2/7/2017.
 */
public class SongList {
    ArrayList<Song> songList = new ArrayList<>();
    public  String recSong, recArtist;
    public int addSong(String songName,String artist, String album, String date){
        Song song = new Song(songName,artist,album,date);
        for (int i = 0; i < songList.size(); i++) {
            if(songList.get(i).song.equals(songName) && songList.get(i).artist.equals(artist)){
                return 1;
            }
        }
        songList.add(song);
        recSong = song.song;
        recArtist = song.artist;
        traverse();
        sort();
        return 0;
    }

    public int editSong(String songName,String artist, String album, String date, int index){
        for (int i = 0; i < songList.size(); i++) {
            if(songList.get(i).song.equals(songName) && songList.get(i).artist.equals(artist) && index != i){
                return 1;
            }
        }
        songList.get(index).song = songName;
        songList.get(index).artist = artist;
        songList.get(index).album = album;
        songList.get(index).year = date;
        sort();
        return 0;
    }
    public void sort(){
        Collections.sort(songList, new Comparator<Song>(){
            @Override
            public int compare(Song o1, Song o2) {
                return String.valueOf(o1.song).compareTo(o2.song);
            }
        });
    }
    public  void traverse(){
        for (int i = 0; i < songList.size(); i++) {
            System.out.println("Song: " + songList.get(i).song + " Artist: " + songList.get(i).artist);
        }
    }
}