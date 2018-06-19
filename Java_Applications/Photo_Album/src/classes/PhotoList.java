package classes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

import application.AlbumViewerController;
import application.Controller;

/**
 * Serializes and deserializes albums
 * @author John Chen and Shah Rahim
 *
 */
public class PhotoList implements java.io.Serializable{
	 public ArrayList<Photo> photoList = new ArrayList<>();
	 public static ArrayList<Photo> pList = new ArrayList<>();
	 public ArrayList<String> captionList = new ArrayList<>();
	 public static int count = 0;
	 public static ArrayList<String> cList = new ArrayList<>();
	 public static String date;
	 
	 private static final long serialVersionUID = 1L;
	  
	 /**
	  * Adds in a photo to an arraylist of photos
	  * @param strPath path used to initialize the files for viewing
	  * @param dated date used for searching and displaying
	  * @return int 1 if the photo is there and 0 if the photo is not there
	  */
	  public static int addPhoto(String strPath, String dated) {
		    date = dated;
	        Photo p = new Photo(strPath, "", date);
	        //check if duplicate song name and artist
	        for (int i = 0; i < pList.size(); i++) {
	            if (pList.get(i).strPath.equals(strPath)) {
	                return 1;
	            }
	        }
	        pList.add(p);
	        cList.add("");
	        //sort();
	        return 0;
	  }
	  /**
	   * returns an arraylist of photo
	   * @return ArrayList<Photo>
	   */
	  public ArrayList<Photo> returnPhotoList()
	  {
		  return photoList;
	  }
	  /**
	   * Sets the caption for the photo
	   * @param cap string used to set the caption
	   * @param index int find the correct index in cList
	   */
	  public static void setCaption(String cap, int index){
		  if(cap.isEmpty()){
			  return;
		  }
		  cList.set(index,cap);
	  }
	  /**
	   * deletes the array lists
	   */
	  public void deleteAll() {
	        //check if duplicate song name and artist
	        if (pList.size() > 0){
	            pList.clear();
	            cList.clear();
	        }
	  }
	  /**
	   * deletes the caption
	   * @param index used to find the caption in cList
	   */
	  public static void delCaption(int index){
		  if(cList.isEmpty()){
			  return;
		  }
		  cList.set(index,"");
	  }

	  /**
	   * deletes a photo from the array list
	   * @param index used to find the photo
	   */
	  public void deletePhoto(int index) {
	        //check if duplicate song name and artist
	        if (pList.size() > 0){
	            pList.remove(index);
	            cList.remove(index);
	        }
	        //sort();
	  }
	  
	  /**
	   * serializes and album object
	   * @param a ablum object that is serialized
	   * @throws IOException
	   */
	  public static void writeApp(Album a) throws IOException{
	    	//@SuppressWarnings("resource")
		  	FileOutputStream fos = new FileOutputStream("./users/"+Controller.user+"/"+AlbumViewerController.album+"/"
		  			+AlbumViewerController.album +".txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			//for(int i = 0;i<a.phList.photoList.size();i++){
				a.phList.photoList.clear();
				a.phList.captionList.clear();
			//}
				//System.out.println(cList.size() + " " + pList.size());
			for(int i = 0;i<pList.size();i++){
				a.phList.photoList.add(pList.get(i));
				a.phList.captionList.add(cList.get(i));
			}
			oos.writeObject(a);
	    	oos.flush();
	    	oos.close();
	  }
	  
	  /**
	   * deserializes an album object
	   * @return Album that is taken from the file
	   * @throws IOException
	   */
	  public static Album readApp()  throws IOException{
		 // @SuppressWarnings("resource")
		  FileInputStream fis = new FileInputStream("./users/"+Controller.user+"/"+AlbumViewerController.album+"/"
		  			+AlbumViewerController.album +".txt");
		  ObjectInputStream ois = new ObjectInputStream(fis);
		  Album a = new Album();
		  try {
			  a = (Album)ois.readObject();
			  
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  toList(a);
		  fis.close();
		  ois.close();
		  return a;
	 }
	  /**
	   * takes in an album and adds the contents to an array list of photos
	   * @param a album used get the contents
	   */
	 public static void toList(Album a){
		 int w = a.phList.captionList.size();
		 if(pList.isEmpty()){
			 for(int i = 0;i<a.phList.photoList.size();i++){
				 addPhoto(a.phList.photoList.get(i).strPath, a.phList.photoList.get(i).date);
				 count++;
				 if(i < w && w != 0)
				 {
					 if(a.phList.captionList.get(i).equals(null)){
						 setCaption("",i);
					 }
					 else{
						 setCaption(a.phList.captionList.get(i),i);
					 }
				 }
			 }
		 }
	 }
	 
	 
}
