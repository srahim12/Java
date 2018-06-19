package application;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launches the program
 * @author John Chen and Shah Rahim
 *
 */
public class Main extends Application {
	Scene home;
	/**
	 * Loads login FXML to be displayed
	 * @param primaryStage is used to set the stage
	 */
    @Override
    public void start(Stage primaryStage) throws IOException,InvocationTargetException {
    	Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        home = new Scene(root);
        primaryStage.setScene(home);
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /**
     * Launches the application
     * @param args Takes in an argument
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        launch(args);
    }
}