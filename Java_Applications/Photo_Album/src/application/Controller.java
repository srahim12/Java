package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.IOException;
import classes.*;

/**
 * Controls the Login FXML
 * @author John Chen and Shah Rahim
 *
 */
public class Controller {
	@FXML
	public Button loginButton;
	@FXML
	public TextField userName;
	public static String user;
	static UserList cUL = new UserList();
	static User u = new User();
	Stage stage;
	Parent root;
	String rUser;
	
	/**
	 * Login method logins in user into admin or a user
	 * and switches the stage
	 * @param takes in a button event which determines which user and/or stage is displayed
	 * @throws IOException
	 */
	@FXML
	public void login(ActionEvent event) throws IOException {
		//user wants to log in
		if(event.getSource() == loginButton){
			//user logs in as admin 
			//create a file to see if it exists
			File f = new File("./adminUsers.txt");
			//does the file not exist
			if(!f.exists() &&!f.isDirectory()){
				UserList.writeApp(cUL);
			}
			//file exists and read the object in
			else{
				cUL = UserList.readApp("./adminUsers.txt");
			}
			if(userName.getText().equals("admin")){
				//create fxml loader
				FXMLLoader loader = new FXMLLoader();
		       	stage = (Stage)loginButton.getScene().getWindow();
				loader.setLocation(getClass().getResource("Admin.fxml"));
				stage.setTitle("Admin");
				root = loader.load();
		       	AdminController adminC = loader.getController();
		       	try {
		       		adminC.start(cUL);
				} 
		       	catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				user = userName.getText();
				if(verify(user)==true){
					File theDir = new File("users");
					// if the directory does not exist, create it
					if (!theDir.exists()) {
					    boolean result = false;
					    try{
					        theDir.mkdir();
					        result = true;
					    } 
					    catch(SecurityException se){
					        //handle it
					    }        
					    if(result) {    

					    }
					}
					File d = new File("./users/"+userName.getText());
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
					File a = new File("./users/"+userName.getText()+"/"+userName.getText() +".txt");
					//does the file not exist
					if(!a.exists() &&!a.isDirectory()){
						AlbumList.writeApp(u);
					}
					//file exists and read the object in
					else{
						u = AlbumList.readApp();
					}
					FXMLLoader loader = new FXMLLoader();
			       	stage = (Stage)loginButton.getScene().getWindow();
					loader.setLocation(getClass().getResource("AlbumViewer.fxml"));
					stage.setTitle("Album Viewer");
					root = loader.load();
					AlbumViewerController aVC = loader.getController();
			       	try {
			       		aVC.start(u);
					} 
			       	catch (Exception e) {
						e.printStackTrace();
					}
				}
				else{
					return;
				}
			}
		}
		Scene admin = new Scene(root);
		stage.setScene(admin);
		stage.setResizable(false);
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try{
					UserList.writeApp(cUL);
				} 
				catch (IOException e){
					//System.out.println(e.getMessage());
				}
					//System.out.println(UserList.uList.get(0).uAlbum.aList.get(0).albumName);
					stage.close();
			}
      });
	}
	
	/**
	 * verifies if the user was created by admin
	 * @param user compared to the users in userlist
	 * @return
	 */
	public boolean verify(String user){
		for(int i = 0;i<UserList.uList.size();i++){
			if(UserList.uList.get(i).user.equals(user)){
				return true;
			}
		}
		return false;
	}
}