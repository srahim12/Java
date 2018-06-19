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
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import classes.Photo;
import classes.PhotoList;
import classes.User;
import classes.UserList;
import classes.Album;
import classes.AlbumList;
import classes.TagList;

/**
 * Controls the photoViewer FXML
 * @author John Chen and Shah Rahim
 *
 */
public class PhotoViewerController implements Initializable{
	Image photo;
	@FXML 
	ListView<Path> listy;
	@FXML ImageView photoImage;
	@FXML Button photoAdd;
	@FXML Button remove;
	@FXML Button caption;
	@FXML TextField captionText;
	@FXML TextField tagName;
	@FXML TextField tagValue;
	@FXML ListView tagList;
	@FXML Text test;
	@FXML Text dateText;
	@FXML Button aTag;
	@FXML Button rTag;
	@FXML Button movePhoto;
	@FXML Button copyPhoto;
	@FXML Button logOut;
	@FXML ListView<String> AlbumListView;
	String tag;
	
	TagList tt = new TagList();
	public static Photo p = new Photo();
	ObservableList<String> tObs = FXCollections.observableArrayList();
	Path nn;
	ArrayList<Path> files = new ArrayList<Path>();
	classes.Album thisOne;
	classes.PhotoList photos;
	ArrayList<Album> a;
	ArrayList<classes.Tag> tags = new ArrayList<classes.Tag>();
	//classes.Caption cap = new classes.Caption();
	static int albumIndex;
	static String date;
	Stage stage;
	ObservableList<Path> observable = FXCollections.observableArrayList();
	public ObservableList<String> observableAlbum = FXCollections.observableArrayList();
	static int current = 0;
	String albumName;
	String ogAlbum;
	public static int photoI;
	
	/**
	 * loads up names into AlbumName observableList
	 * and checks if album is empty
	 * @param a check if it is empty
	 */
	public void start(Album a)
	{
		albumI();
		//adds all the other albums into a list
		ogAlbum = AlbumViewerController.album;
        for (int i = 0; i < AlbumList.aList.size(); i++) 
        {
        	if (!AlbumList.aList.get(i).albumName.equals(AlbumViewerController.album))
        	{
        		 observableAlbum.addAll(AlbumList.aList.get(i).albumName);
        	}
        }
        AlbumListView.setItems(observableAlbum);
        //adds the photos to the photo list
		if (a.phList.pList.isEmpty())
		{
			return;
		}
	}
	
	/**
	 * takes in ListView clicks
	 * and set texts equal to the appropriate name
	 * @param event used to locate which listview is clicked
	 * @throws IOException
	 */
	@FXML
    public void handleListy(MouseEvent event) throws IOException {
        if (event.getSource() == listy) {
        	TagList.tList.clear();
        	 captionText.setText("");
             int index = listy.getSelectionModel().getSelectedIndex();
               if (index < 0)
                     return;
               photoImage.setImage(new Image(listy.getSelectionModel().selectedItemProperty().get().toUri().toString()));
               p = getPhoto(listy.getSelectionModel().selectedItemProperty().get().toString());
               test.setText(PhotoList.cList.get(index));
               tagList.setItems(tObs);
               tObs = FXCollections.observableArrayList();
               if (PhotoList.cList.get(photoI) != null)
               {
            	   dateText.setText(PhotoList.pList.get(photoI).date);
            	   test.setText(PhotoList.cList.get(photoI));
               }
               File a = new File("./users/"+Controller.user+"/"+AlbumViewerController.album +"/"+AlbumViewerController.album+photoI+"Tags.txt");
	   			//does the file not exist
	   			if(!a.exists() &&!a.isDirectory()){
	   				TagList.writeApp(new Photo());
	   			}
	   			else{
	   				p = TagList.readApp();
	   				
	   			}
	   			//TagList.pList(p);
	   			for(int i = 0; i <p.taList.tagList.size();i++){
	   				TagList.tList.add(p.taList.tagList.get(i));
	   			}
	   			addToList(p);
        }
    }
	
	/**
	 * copies or moves files from one album to another
	 * @param e takes in a button press
	 * @throws IOException
	 */
	public void copy(ActionEvent e) throws IOException
	{
		//album1

		Path path;
		Photo p = new Photo();
		int j = -1;
		if (listy.getSelectionModel().selectedItemProperty().get() != null && AlbumListView.getSelectionModel().selectedItemProperty().get() != null)
		{
			for (int i = 0; i < AlbumList.aList.size(); i++) 
	        {
	        	if (AlbumListView.getSelectionModel().selectedItemProperty().get().equals(AlbumList.aList.get(i).albumName))
	        	{
	        		j = i;
	        		 break;
	        	}
	        }
			if (j != -1)
			{
				File d = new File("./users/"+Controller.user+"/"+AlbumList.aList.get(j).albumName);
		   		 boolean result = false;
				    try{
				        d.mkdir();
				        result = true;
				    } 
				   catch(SecurityException se){
					        //handle it
					    }  
				    File f = new File("./users/"+Controller.user+"/"+AlbumList.aList.get(j).albumName+"/"
				  			+AlbumList.aList.get(j).albumName +".txt");
				    if (f.exists())
				    {
						FileInputStream fis = new FileInputStream("./users/"+Controller.user+"/"+AlbumList.aList.get(j).albumName+"/"
					  			+AlbumList.aList.get(j).albumName +".txt");
						 ObjectInputStream ois = new ObjectInputStream(fis);
						 Album a = new Album();
						 try {
							 a = (Album)ois.readObject(); 
						 	} 
						   catch (ClassNotFoundException ff) {
							ff.printStackTrace();
						  }
						 
						 if(a != null){
							 AlbumList.aList.get(j).phList = a.phList;
						 }
						  fis.close();
						  ois.close();
				    }
				  
				path = (Path)listy.getSelectionModel().selectedItemProperty().get();
		   		 p = getPhoto(path.toString());
		   		 p.caption = "";
		   		
		   		 AlbumList.aList.get(j).phList.photoList.add(p);

		   		  FileOutputStream fos = new FileOutputStream("./users/"+Controller.user+"/"+AlbumList.aList.get(j).albumName+"/"
				  			+AlbumList.aList.get(j).albumName +".txt");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(AlbumList.aList.get(j));
			    	oos.flush();
			    	oos.close();
			}
			if (e.getSource() == movePhoto)
			{
				remove(e);
			}
		}
	}

	/**
	 * addes a photo to an album
	 * @param e takes in a button press
	 * @throws IOException
	 */
	public void add(ActionEvent e) throws IOException
		{
			boolean isDup = false;
	        stage = (Stage)photoAdd.getScene().getWindow();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Pictures");
			List<File> list = fileChooser.showOpenMultipleDialog(stage);
	        if (list != null) 
	        {
	            for (File filed : list) 
	            {	
	            	//compare all files in the arraylist to the ones being added
	            	for(Photo p: PhotoList.pList)
	            	{
	            		//make sure a duplicate file is not added
	            		Path thisPath = Paths.get(p.strPath);
	            		if (thisPath.getFileName().toString().equals(filed.getName().toString()))
	            		{
	            			isDup = true;
	            		}
	            	}
	            	if (!isDup)
	            	{
	                	clear(filed.toPath());
	                	String asdf = filed.toString();
	                	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); 
	                	
	                	BasicFileAttributes attr = Files.readAttributes(filed.toPath(),BasicFileAttributes.class); 
	                	FileTime dated = attr.creationTime();
	                    date = sdf.format(dated.toMillis());
	                    
	                	PhotoList.addPhoto(asdf, date);
	                	PhotoList.writeApp(AlbumList.aList.get(albumIndex));
	            	}
	            	isDup = false;
	            }
	        }
		}

	/**
	 * Adds a caption to a picture
	 * @param e Takes in a button press
	 * @throws IOException
	 */
	public void caption(ActionEvent e) throws IOException
	{
		if ((captionText.getText().trim().equals(""))){
			return;
		}
		Path nn = (Path)listy.getSelectionModel().selectedItemProperty().get();
		p = getPhoto(nn.toString());
		PhotoList.setCaption(captionText.getText(), photoI);
		test.setText(p.caption);
		test.setText(PhotoList.cList.get(photoI));
		PhotoList.writeApp(AlbumList.aList.get(albumIndex));
	}
	
	/**
	 * Adds a tag to a picture
	 * @param e Takes in a button press
	 * @throws IOException
	 */
	public void addTag(ActionEvent e) throws IOException
	{
		if(listy.getSelectionModel().selectedItemProperty().get()!=null){
			nn = (Path)listy.getSelectionModel().selectedItemProperty().get();
			p = getPhoto(nn.toString());
			if(tagName.getText().trim().equals("") || tagValue.getText().trim().equals("")){
				return;
			}
			if(TagList.addTag(tagName.getText(), tagValue.getText())==1){
				return;
			}
			//PhotoList.pList.get(photoI).taList.tagList.add(new Tag(tagName.getText(),tagValue.getText()));
			//nn = (Path)listy.getSelectionModel().selectedItemProperty().get();
			//p = getPhoto(nn.toString());
			addToList(p);
		
			TagList.writeApp(p);
			addToList(p);
		}
		
	}
	
	/**
	 * removes a picture from an album
	 * @param e Takes in a button press
	 * @throws IOException
	 */
	public void remove(ActionEvent e) throws IOException
	{
		if (listy.getSelectionModel().selectedItemProperty().get() != null)
		{
			Path p = (Path) listy.getSelectionModel().selectedItemProperty().get();
			File f = new File("./users/"+ Controller.user+"/"+ AlbumViewerController.album +"/" + AlbumViewerController.album + PhotoViewerController.photoI +"Tags.txt");
			if (f.exists())
			{
				Files.delete(f.toPath());
			}
			Photo thisPhoto = getPhoto(p.toString());
			AlbumList.aList.get(albumIndex).phList.deletePhoto(photoI);
			if (listy.getSelectionModel().getSelectedIndex() == observable.size() - 1)
			{
				listy.getSelectionModel().select(0);
			}
			else
			{
				listy.getSelectionModel().selectNext();
			}
			observable.remove(photoI);
			if(observable.size() > 0)
			{
				photoImage.setImage(new Image(listy.getSelectionModel().selectedItemProperty().get().toUri().toString()));
				dateText.setText(thisPhoto.date);
				if (photoI < PhotoList.cList.size())
				{
					if (PhotoList.cList.get(photoI) != null)
					{
						test.setText(PhotoList.cList.get(photoI));
					}
					else
					{
						test.setText("");
					}
				}
				
			}
			else
			{
				dateText.setText("");
				photoImage.setImage(null);
				test.setText("");
				AlbumList.aList.get(albumIndex).phList.deleteAll();
			}
			listy.setItems(observable);
			listy.refresh();
			PhotoList.writeApp(AlbumList.aList.get(albumIndex));
		}
	}

	/**
	 * Shows the next available picture
	 * @param e Takes in a button press
	 * @throws IOException
	 */
	public void next(ActionEvent e) throws IOException
	{
		if (observable.size() != 0)
		{
			if (listy.getSelectionModel().getSelectedIndex() == observable.size() - 1)
			{
				listy.getSelectionModel().select(0);
				test.setText(PhotoList.cList.get(0));
				addToList(p);
			}
			else
			{
				listy.getSelectionModel().selectNext();
				test.setText(PhotoList.cList.get(listy.getSelectionModel().getSelectedIndex()));
				addToList(p);
			}
			photoImage.setImage(new Image(listy.getSelectionModel().selectedItemProperty().get().toUri().toString()));
		}
	}
	
	/**
	 * Shows the previous picture
	 * @param e Takes in a button press
	 * @throws IOException
	 */
	public void previous(ActionEvent e) throws IOException
	{
		if (observable.size() != 0)
		{
			if (listy.getSelectionModel().getSelectedIndex() == 0 )
			{
				listy.getSelectionModel().select(observable.size() - 1);
				test.setText(PhotoList.cList.get(observable.size() - 1));
			}
			else
			{
				listy.getSelectionModel().selectPrevious();
				test.setText(PhotoList.cList.get(listy.getSelectionModel().getSelectedIndex()));
			}
			photoImage.setImage(new Image(listy.getSelectionModel().selectedItemProperty().get().toUri().toString()));
		}
	}

	/**
	 * Adds in Path h into the observableList
	 * and then shows it in the listview
	 * @param h is used to add in a picture in the listview
	 */
	public void clear(Path h)
	{
		observable.add(h);
		listy.setItems(observable);
		listy.refresh();
	}
	
	/**
	 * Gets the index of the current album
	 */
	public static void albumI()
	{
		for (int i = 0; i < AlbumList.aList.size(); i++)
		{
			if(AlbumList.aList.get(i).equals(AlbumViewerController.album))
			{
				albumIndex = i;
				break;
			}
		}
	}
	
	/**
	 * Adds tags to Photo p
	 * @param p is used to add tags to
	 */
	public void addToList(Photo p) {
    	tObs = FXCollections.observableArrayList();
    	String tag;
    	for(int i = 0;i<p.taList.tList.size();i++){
    		tag = p.taList.tList.get(i).name+"="+p.taList.tList.get(i).value;
    		tObs.addAll(tag);
    	}
    	tagList.setItems(tObs);
		/*tObs = FXCollections.observableArrayList();
    	//nn = (Path)listy.getSelectionModel().selectedItemProperty().get();
		//p = getPhoto(nn.toString());
    	for (int i = 0; i <  p.taList.tagList.size(); i++) {
	        ttList.add(p.taList.tagList.get(i));
    		//tag = p.taList.tagList.get(i).name+"="+p.taList.tagList.get(i).value;
    		tag = ttList.get(i).name +"="+ ttList.get(i).value;
	        tObs.addAll(tag);
	    }
	    tagList.setItems(tObs);*/
	}
	
	/**
	 * Selects the tag
	 * @param event takes in a mouse click
	 * @throws IOException
	 */
	@FXML
    public void handleTag(MouseEvent event) throws IOException {
        if (event.getSource() == tagList) {
            int index = tagList.getSelectionModel().getSelectedIndex();
            if (index < 0)
                return;
            /* tagName.setText(TagList.tList.get(index).name);
            tagValue.setText(TagList.tList.get(index).value);*/
            //rename = userText.getText();
        }
    }
	/**
	 * Deletes the tag from a picture
	 * @param e Takes in a button press
	 * @throws IOException
	 */
	public void deleteTag(ActionEvent e) throws IOException
	{
		if(tagList.getSelectionModel().getSelectedIndex()!=-1){

			TagList.deleteTag(tagList.getSelectionModel().getSelectedIndex());
			TagList.writeApp(p);
		}
		TagList.writeApp(p);
		addToList(p);
		
	}
	
	/**
	 * Returns the index of a photo with the same name as String s
	 * @param s used to find a photo
	 * @return Photo 
	 */
	public static Photo getPhoto(String s)
	{
		for(int i = 0;i<AlbumList.aList.get(albumIndex).phList.pList.size();i++){
			if(AlbumList.aList.get(albumIndex).phList.pList.get(i).strPath.equals(s))
					{
						photoI = i;
						return AlbumList.aList.get(albumIndex).phList.pList.get(i);
					}
		}
		return null;
	}
	
	/**
	 * Called to initialize the ListView displaying pictures
	 * @param location points to a url
	 * @param resources used to locate objects
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		observable.clear();
		for (int i = 0; i < PhotoList.pList.size(); i++)
		{
			observable.add(Paths.get(PhotoList.pList.get(i).strPath));
		}
		listy.setCellFactory(new Callback<ListView<Path>, ListCell<Path>>(){
			/**
			 * Adds in image viewing capabilities to the list view
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