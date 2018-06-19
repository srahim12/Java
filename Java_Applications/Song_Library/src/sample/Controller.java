package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.Optional;

public class Controller {

    //declaring buttons from gui fxml window
    public Button addButton, editButton, deleteButton, clearButton;

    //declaring textfields from gui fxml window
    public TextField songName, songArtist, songAlbum, songDate;

    //declaring the listview from the gui fxml file
    public ListView<String> songList;

    //creating a observable list
    private ObservableList<String> obsList;

    //creating an instance of the SOngList class to create an arraylist for the songs
    SongList sl = new SongList();

    Alert cancel;

    //handles all events where the three buttons are clicked
    public void handleButton(ActionEvent event) throws IOException {
        obsList = FXCollections.observableArrayList();

        //if the ad button is clicked
        if (event.getSource() == addButton){

            //if song name or song artist textfields ar empty
            if ((songName.getText().trim().equals("")) || (songArtist.getText().trim().equals(""))){
               invalidInput("Empty");
            }

            //if atleast song name and song artist textfields are filled out
            else if (!songName.getText().trim().equals("") && !songArtist.getText().trim().equals("")){
                if(confirmAction(1)== -1)
                    return;
                //adding the song details to the arraylist model
                if(sl.addSong(songName.getText(), songArtist.getText(), songAlbum.getText(), songDate.getText())==1){
                    invalidInput("Repeat");
                }

            }

            //adding all elements from arraylist to the listview
            addToList();
            songList.getSelectionModel().select(getAddedIndex());
        }

        else if (event.getSource() == editButton){
            if ((songName.getText().trim().equals("")) || (songArtist.getText().trim().equals(""))) {
                invalidInput("Empty");
            }

            else if (!songName.getText().trim().equals("") && !songArtist.getText().trim().equals("")){
                if(confirmAction(3)== -1)
                    return;
                else if(editExists(songName.getText(), songArtist.getText())== false){
                    invalidInput("Edit");
                }
                //adding the song details to the arraylist model
                if(sl.editSong(songName.getText(), songArtist.getText(), songAlbum.getText(), songDate.getText(),
                        songList.getSelectionModel().getSelectedIndex())==1){
                    invalidInput("Repeat");
                }
            }
        }

        else if (event.getSource() == deleteButton){
        }

        else if (event.getSource() == clearButton){
            songName.setText("");
            songArtist.setText("");
            songDate.setText("");
            songAlbum.setText("");
        }
    }

    @FXML
    public void handleList(MouseEvent event){
        if(event.getSource() == songList){
            int index = songList.getSelectionModel().getSelectedIndex();
            songName.setText(sl.songList.get(index).song);
            songArtist.setText(sl.songList.get(index).artist);
            songAlbum.setText(sl.songList.get(index).album);
            songDate.setText(sl.songList.get(index).year);
        }
    }

    public int confirmAction(int type){
        cancel = new Alert(Alert.AlertType.CONFIRMATION);
        if(type == 1) {
            cancel.setTitle("Add?");
            cancel.setContentText("Are you sure you want to add this song");
        }
        else if(type ==2){
            cancel.setTitle("Delete?");
            cancel.setContentText("Are you sure you want to delete this song");
        }
        else if(type ==3){
            cancel.setTitle("Edit");
            cancel.setContentText("Are you sure you want to edit this song");
        }
        Optional<ButtonType> rCancel = cancel.showAndWait();
        if(rCancel.get() == ButtonType.OK){
            return 0;
        }
        return -1;
    }

    public int getAddedIndex(){
        String rSong,rArtist;
        rSong=sl.recSong;
        rArtist=sl.recArtist;
        for(int i = 0;i<sl.songList.size();i++){
            if(sl.songList.get(i).song.equals(rSong) && sl.songList.get(i).artist.equals(rArtist)){
                return i;
            }
        }
        return 0;
    }

    public void addToList(){
        obsList = FXCollections.observableArrayList();
        for (int i = 0; i < sl.songList.size(); i++) {
            obsList.addAll(sl.songList.get(i).song);
        }
        songList.setItems(obsList);
    }

    public void invalidInput(String type){
        if (type.equals("Repeat")) {
            Alert invalid = new Alert(Alert.AlertType.INFORMATION);
            invalid.setTitle("Error");
            invalid.setContentText("You Cannot enter same song twice");
            invalid.showAndWait();
        }
        else if (type.equals("Empty")) {
            Alert invalid = new Alert(Alert.AlertType.INFORMATION);
            invalid.setTitle("Error");
            invalid.setContentText("Please enter song name and artist");
            invalid.showAndWait();
        }
        else if(type.equals("Edit")){
            Alert invalid = new Alert(Alert.AlertType.INFORMATION);
            invalid.setTitle("Error");
            invalid.setContentText("Song Does Not exist for editing");
            invalid.showAndWait();
        }
    }

    public boolean editExists(String song, String artist){
        for(int i = 0;i<sl.songList.size();i++){
            if(sl.songList.get(i).song.equals(song) && sl.songList.get(i).artist.equals(artist)){
                return true;
            }
        }
        return false;
    }
}