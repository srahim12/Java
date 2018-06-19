package classes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import application.Controller;

/**
 * Handles the serialization and deserialization of User objects
 * @author John Chen and Shah Rahim
 *
 */
public class AlbumList implements java.io.Serializable{
	  public ArrayList<Album> albumList = new ArrayList<>();
	  public static ArrayList<Album> aList = new ArrayList<>();
	  private static final long serialVersionUID = 1L;
	  
	  /**
	   * adds an album to an array list
	   * @param album used to name the album
	   * @return int 1 if it is a duplicate name 0 if it is not a duplicate
	   */
	  public static int addAlbum(String album) {
	        Album a = new Album(album);
	        //check if duplicate song name and artist
	        for (int i = 0; i < aList.size(); i++) {
	            if (aList.get(i).albumName.equals(album)) {
	                return 1;
	            }
	        }
	        aList.add(a);
	        sort();
	        return 0;
	  }
	  
	  /**
	   * returns an albumList
	   * @return albumList
	   */
	  public ArrayList<Album> returnAlbumList()
	  {
		  return albumList;
	  }
	  
	  /**
	   * sorts the albums
	   */
	  public static void sort() {
	        Collections.sort(aList, (o1,o2) -> o1.albumName.toLowerCase().compareTo(o2.albumName.toLowerCase()));
	  }
	  
	  /**
	   * deletes an album
	   * @param index used to find the album to be deleted
	   */
	  public void deleteAlbum(int index) {
	        //check if duplicate song name and artist
	        if (aList.size() > 0){
	            aList.remove(index);
	            
	        }
	        sort();
	  }
	  /**
	   * renames an album
	   * @param index find the album that is being renamed
	   * @param alName new name for the album
	   */
	  public void renameAlbum(int index, String alName) {
	        //check if duplicate song name and artist
	        if (aList.size() > 0){
	            aList.get(index).albumName=alName;
	            
	        }
	        sort();
	  }
	  
	  /**
	   * Serializes an User object
	   * @param u object to be serialized
	   * @throws IOException
	   */
	  public static void writeApp(User u) throws IOException{
	    	//@SuppressWarnings("resource")
		  	FileOutputStream fos = new FileOutputStream("./users/"+Controller.user+"/"+Controller.user+".txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for(int i = 0;i<u.uAlbum.albumList.size();i++){
				u.uAlbum.albumList.remove(i);
			}
			for(int i = 0;i<aList.size();i++){
				u.uAlbum.albumList.add(aList.get(i));
			}
			oos.writeObject(u);
	    	oos.flush();
	    	oos.close();
	  }
	  
	  /**
	   * returns a deserialized User object
	   * @return User
	   * @throws IOException
	   */
	  public static User readApp()  throws IOException{
		 // @SuppressWarnings("resource")
		  FileInputStream fis = new FileInputStream("./users/"+Controller.user+"/"+Controller.user+".txt");
		  ObjectInputStream ois = new ObjectInputStream(fis);
		  User u = new User();
		  try {
			  u = (User)ois.readObject();
			  
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  toList(u);
		  fis.close();
		  ois.close();
		  return u;
	 }
	  
	  /**
	   * Adds the deserialized albums to the current array list of albums
	   * @param u User used to find their albums
	   */
	 public static void toList(User u){
		 if(aList.isEmpty()){
			 for(int i = 0;i<u.uAlbum.albumList.size();i++){
				 addAlbum(u.uAlbum.albumList.get(i).albumName);
			 }
		 }
	 }
}