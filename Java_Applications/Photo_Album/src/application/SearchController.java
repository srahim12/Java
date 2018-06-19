package application;

import java.awt.Desktop;
import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import classes.Photo;
import classes.PhotoList;
import classes.UserList;
import classes.Album;
import classes.AlbumList;
/**
 * Controller for Search FXML which searches through photos for 
 * specified tags and/or dates
 * @author Shah Rahim and John Chen
 *
 */
public class SearchController implements Initializable{
	@FXML Button searchButton;
	@FXML Button searchReturn;
	@FXML Button searchCreate;
	@FXML ListView<Path> listy;
	@FXML DatePicker searchDateFrom;
	@FXML DatePicker searchDateTo;
	ArrayList<Photo> p = new ArrayList<Photo>();
	ObservableList<Path> observable = FXCollections.observableArrayList();
	
	/**
	 * Called to search for photos which satisfy the tags and/or date specified
	 * @param 
	 * @throws IOException
	 */
	public void search(ActionEvent e) throws IOException
	{
		if(e.getSource() == searchButton)
		{
			
			if (searchDateFrom.getValue() != null && searchDateTo.getValue() != null)
			{
				System.out.println(searchDateFrom.getValue());
				for (Photo p1: p)
				{
					
				}
			}
		}
	}
	
	/**
	 * Initializes the ListView in Search FXML
	 * @param location points to a resource on the internet
	 * @param resources locates specific objects
	 */
	public void initialize(URL location, ResourceBundle resources)
	{
		observable.clear();
		for (int j = 0; j < AlbumList.aList.size(); j++)
		{
			System.out.println(AlbumList.aList.get(j).albumName + " " + AlbumList.aList.get(j).phList.pList.size());
			for (int i = 0; i < AlbumList.aList.get(j).phList.pList.size(); i++)
			{
				if (AlbumList.aList.get(j).phList.pList.get(i).caption != null)
				{
					p.get(i).setCaption(AlbumList.aList.get(j).phList.pList.get(i).caption);
				}
				else
				{
					p.get(i).setCaption("");
				}
				observable.add(Paths.get(AlbumList.aList.get(j).phList.photoList.get(i).strPath));
			}
		}
		listy.setCellFactory(new Callback<ListView<Path>, ListCell<Path>>(){
			/**
			 * initializes the listView with images
			 */
			@Override
			public ListCell<Path> call(ListView<Path> arg0)
			{
				ListCell<Path> cell = new ListCell<Path>()
				{
					@Override
					protected void updateItem(Path p, boolean empty)
					{
						super.updateItem(p, empty);
						if (empty) {
			                setText(null);
			                setGraphic(null);
						}
						else if(p != null)
						{
							Image img = new Image(p.toUri().toString(), 80, 80, true, true);
							ImageView view = new ImageView(img);
							setGraphic(view);
							setText(p.getFileName().toString());
						}
					}
				};
				return cell;
			}
		});
		listy.setItems(observable);
	}
}
