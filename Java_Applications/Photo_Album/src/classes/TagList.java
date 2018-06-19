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
import application.PhotoViewerController;

/**
 * Serializes and deserializes TagList objects
 * @author John Chen and Shar Rahim
 * */
public class TagList implements java.io.Serializable{
	 public ArrayList<Tag> tagList = new ArrayList<>();
	 public static ArrayList<Tag> tList = new ArrayList<>();
	 private static final long serialVersionUID = 1L;
	  
	 /**
	  * adds in a tag
	  * @param name is the name for the tag
	  * @param value is the value for the tag
	  * @return int returns 1 if duplicate and 0 if not
	  */
	 public static int addTag(String name, String value) {
	        Tag t = new Tag(name, value); 
	        
	        for (int i = 0; i < PhotoViewerController.p.taList.tagList.size(); i++) {
	            if (PhotoViewerController.p.taList.tagList.get(i).name.equals(name) &&PhotoViewerController.p.taList.tagList.get(i).value.equals(value)) {
	                return 1;
	            }
	        }
	        //System.out.println("Adding tag to Photo: "+PhotoList.pList.get(PhotoViewerController.photoI).caption);
	        PhotoList.pList.get(PhotoViewerController.photoI).taList.tList.add(t);
	        //PhotoList.pList.get(PhotoViewerController.photoI).taList.tagList.add(t);
	        
	        
	        return 0;
	 }

	 /**
	  * deletes a tag
	  * @param index is used to find the tag to be deleted
	  */
	 public static void deleteTag(int index) {
	        //check if duplicate song name and artist
	        if (PhotoViewerController.p.taList.tList.size() > 0){
	        	PhotoViewerController.p.taList.tList.remove(index);
	        	
	        }
	        //sort();*/
	  }
	 
	 /**
	  * Serializes a photo object
	  * @param p photo object to be serialized
	  * @throws IOException
	  */
	 public static void writeApp(Photo p) throws IOException{
	    	//@SuppressWarnings("resource")
		  	FileOutputStream fos = new FileOutputStream("./users/"+Controller.user+"/"+AlbumViewerController.album 
		  			+"/"+AlbumViewerController.album+PhotoViewerController.photoI+"Tags.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			
			PhotoViewerController.p.taList.tagList.clear();	
			
			for(int i = 0;i<tList.size();i++){
				PhotoViewerController.p.taList.tagList.add(tList.get(i));
			}
		
			oos.writeObject(PhotoViewerController.p);
	    	oos.flush();
	    	oos.close();
	  }
	  
	 /**
	  * deserializes a photo object
	  * @return the deserialized photo object
	  * @throws IOException
	  */
	  public static Photo readApp()  throws IOException{
		 // @SuppressWarnings("resource")
		  FileInputStream fis = new FileInputStream("./users/"+Controller.user+"/"+AlbumViewerController.album+"/"
		  			+AlbumViewerController.album +PhotoViewerController.photoI+"Tags.txt");
		  ObjectInputStream ois = new ObjectInputStream(fis);
		  Photo p = new Photo();
		  try {
			  p = (Photo)ois.readObject();
			  
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  //toList(p);
		  fis.close();
		  ois.close();
		  return p;
	 }
	  
	  /**
	   * adds in the deserialized tags from the photo to this photo
	   * @param p photo that tags are being added to
	   */
	  public static void toList(Photo p){
			 if(tList.isEmpty()){
				 for(int i = 0;i<p.taList.tagList.size();i++){
					 addTag(p.taList.tagList.get(i).name,p.taList.tagList.get(i).value);
				 }
			 }
	  }
	  

}
