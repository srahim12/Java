package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
 * Controls the Admin FXML
 * @author John Chen and Shah Rahim
 *
 */
public class AdminController {
	@FXML
	public Button adminCreate, adminLogout, adminDelete, adminSave;
	
	@FXML
	public TextField adminText;
	
	@FXML
	public ListView<String> adminUserList = new ListView<String>();
	public ObservableList<String> obsList;
	
	static UserList ul = new UserList();
	Stage stage;
	Parent root;
	String rUser;
	
	/**
	 * Handles the creation, deletion, and logging out functions
	 * @param event
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@FXML
	public void adminHandle(ActionEvent event) throws IOException {
		//if the admin clicks create button
		if(event.getSource() == adminCreate){
			//is the textfield empty?
			if ((adminText.getText().trim().equals(""))){
				return;
			}
			//does the user already exist?
			if(ul.addUser(adminText.getText())==1){
				return;
			}
			//otherwise add the user to the list
			addToList();
			adminUserList.getSelectionModel().select(getAddedIndex());
			ul.writeApp(ul);
			File file = new File(UserList.fileStore);
			long size = file.length();
	    	//System.out.println(size);
		}
		//admin wants to logout
		else if(event.getSource() == adminLogout){
			ul.writeApp(ul);
			stage = (Stage)adminLogout.getScene().getWindow();
	        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
	        Scene logIn = new Scene(root);
		    stage.setScene(logIn);
		    stage.setResizable(false);
		    stage.show();
		}
		//admin wants to delete a user
		else if(event.getSource() == adminDelete){
            //delete list from the arraylist of users
			if(adminUserList.getSelectionModel().getSelectedIndex()==-1){
				return;
			}
			ul.deleteSong(adminUserList.getSelectionModel().getSelectedIndex());
            addToList();
            ul.writeApp(ul);
            if(ul.uList.size()>0){
            	adminUserList.getSelectionModel().select(0);
            	adminText.setText(ul.uList.get(0).user);
            }
            else{
            	adminText.setText("");
            }
		}
	}
	
	/**
	 * Initializes the listview
	 * @param u UserList used to get the users
	 * @throws Exception
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public void start(UserList u) throws Exception,IOException {
        obsList = FXCollections.observableArrayList();
        for (int i = 0; i < u.uList.size(); i++) {
            obsList.addAll(u.uList.get(i).user);
        }
        adminUserList.setItems(obsList);
        if (obsList.isEmpty()) {
            return;
        }
        adminUserList.getSelectionModel().select(0);
        adminText.setText(u.uList.get(0).user);
    }
	
	/**
	 * Adds in users to the UserList
	 */
    @SuppressWarnings("static-access")
	public void addToList() {
    	obsList = FXCollections.observableArrayList();
	    for (int i = 0; i < ul.uList.size(); i++) {
	        obsList.addAll(ul.uList.get(i).user);
	    }
	    adminUserList.setItems(obsList);
	}
    
    /**
     * Handles clicks on the listview
     * @param event takes in clicks on the list view
     */
    @SuppressWarnings("static-access")
	@FXML
    public void handleList(MouseEvent event) {
    	//System.out.println("List is clicked");
        if (event.getSource() == adminUserList && ul.uList.size() != 0) {
            int index = adminUserList.getSelectionModel().getSelectedIndex();
            if (index < 0)
                return;
            adminText.setText(ul.uList.get(index).user);
        }
    }
    /**
     * gets the index of the added user
     * @return int of the added user
     */
    @SuppressWarnings("static-access")
	public int getAddedIndex() {
    	 rUser = ul.recUser;
         for (int i = 0; i < ul.uList.size(); i++) {
             if (ul.uList.get(i).user.equals(rUser)) {
                 return i;
             }
         }
         return 0;
	}
}