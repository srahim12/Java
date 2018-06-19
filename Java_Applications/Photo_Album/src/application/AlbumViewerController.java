package application;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controls the AlbumViewer FXML
 * @author John Chen and Shah Rahim
 *
 */
public class AlbumViewerController {
	@FXML
	public Button userCreate, userLogout, userDelete,userRename,userOpen,userSearch;
	@FXML
	public TextField userText;
	@FXML
	public ListView<String> albumList = new ListView<String>();
	
	AlbumList al = new AlbumList();
	public ObservableList<String> alList;
	Stage stage;
	Parent root;
	static String user;
	String rename;
	ArrayList<File> direct;
	//Album a;
	public static String album;
	static Album ab = new Album();
	
	/**
	 * Handles the opening, creation, deletion, renaming, and searching of photo albums
	 * @param event Takes in a button press
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@FXML
	public void userHandle(ActionEvent event) throws IOException {
		if(event.getSource()==userCreate)
		{
			if ((userText.getText().trim().equals(""))){
				return;
			}
			if(UserList.uList.get(findUserIndex()).uAlbum.addAlbum(userText.getText())==1){
				return;
			}

			addToList();
			pAList();
			AlbumList.writeApp(UserList.uList.get(findUserIndex()));
		}
		
		else if(event.getSource()==userLogout){
			AlbumList.writeApp(UserList.uList.get(findUserIndex()));
			stage = (Stage)userLogout.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene logIn = new Scene(root);
		    stage.setScene(logIn);
		    stage.setResizable(false);
		    stage.show();
		}
		
		else if(event.getSource()==userDelete){
			
			if(albumList.getSelectionModel().getSelectedIndex()==-1){
				return;
			}
			al.deleteAlbum(albumList.getSelectionModel().getSelectedIndex());
			//deletes the files on disk
			File f = new File("./users/"+Controller.user+"/" + albumList.getSelectionModel().getSelectedItem());
			File[] d = f.listFiles();
			if (f.exists())
			{
				for (File a: d)
				{
					Files.delete(a.toPath());
				}
				Files.delete(f.toPath());
			}
            addToList();
            AlbumList.writeApp(UserList.uList.get(findUserIndex()));
            pAList();
            if(AlbumList.aList.size()>0){
            	albumList.getSelectionModel().select(0);
            	userText.setText(al.aList.get(0).albumName);
            }
            else{
            	userText.setText("");
            }
		}
		
		else if(event.getSource()==userRename){
			if ((userText.getText().trim().equals(""))){
				return;
			}
			al.renameAlbum(albumList.getSelectionModel().getSelectedIndex(),userText.getText());
			addToList();
            AlbumList.writeApp(UserList.uList.get(findUserIndex()));
            pAList();
            if(AlbumList.aList.size()>0){
            	albumList.getSelectionModel().select(0);
            	userText.setText(al.aList.get(0).albumName);
            }
            else{
            	userText.setText("");
            }
		}
		
		else if(event.getSource()==userOpen){
			album = userText.getText();
			if(album.equals("")){
				return;
			}
			File d = new File("./users/"+Controller.user+"/"+album);
			if (!d.exists()) {

			    boolean result = false;

			    try{
			        d.mkdir();
			        result = true;
			    } 
			    catch(SecurityException se){
			        //handle it
			    }        
			    if(result) {    
  
			    }
			}
			File a = new File("./users/"+Controller.user+"/"+album +"/"+album+".txt");
			//does the file not exist
			if(!a.exists() &&!a.isDirectory()){
				PhotoList.writeApp(ab);
			}
			else{
				ab = PhotoList.readApp();
			}
			stage = (Stage)userOpen.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("PhotoViewer.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("PhotoViewer.fxml"));
			root = loader.load();
			PhotoViewerController photoC = loader.getController();
	        photoC.start(ab);
			Scene open = new Scene(root);
		    stage.setScene(open);
		    stage.setResizable(false);
		    stage.show();
		}
		
		else if(event.getSource()==userSearch){
			stage = (Stage)userSearch.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("Search.fxml"));
			Scene search = new Scene(root);
		    stage.setScene(search);
		    stage.setResizable(false);
		    stage.show();
			
		}
	}
	
	/**
	 * returns nothing if user does not have any albums
	 * otherwise it sets the albumlist equal to the loaded in album
	 * @param u used to add in arraylists
	 */
	public void start(User u){
		if(u.uAlbum.aList.isEmpty()){
			return;
		}
		pAList();
		alList = FXCollections.observableArrayList();
        for (int i = 0; i < u.uAlbum.aList.size(); i++) {
            alList.addAll(u.uAlbum.aList.get(i).albumName);
        }
        albumList.setItems(alList);
        if (alList.isEmpty()) {
            return;
        }
        albumList.getSelectionModel().select(0);
	}
	
	
	public void addToList() {
    	alList = FXCollections.observableArrayList();
	    User u = Controller.u;
    	for (int i = 0; i <  u.uAlbum.aList.size(); i++) {
	        alList.addAll(u.uAlbum.aList.get(i).albumName);
	    }
	    albumList.setItems(alList);
	}
	
	public static int findUserIndex(){
		for(int i = 0;i<UserList.uList.size();i++){
			if(UserList.uList.get(i).user.equals(Controller.user)){
				return i;
			}
		}
		return 0;
	}
	
	public static void pAList(){
		for(int i = 0;i<UserList.uList.get(findUserIndex()).uAlbum.aList.size();i++){

		}
	}
	
	@FXML
    public void handleListy(MouseEvent event) throws IOException {
        if (event.getSource() == albumList) {
            int index = albumList.getSelectionModel().getSelectedIndex();
            if (index < 0)
                return;
            userText.setText(UserList.uList.get(findUserIndex()).uAlbum.aList.get(index).albumName);
            rename = userText.getText();
        }
    }
	
	
}