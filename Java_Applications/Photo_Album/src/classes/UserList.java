package classes;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Serializes and deserializes Userlists
 * @author John
 *
 */
public class UserList implements Serializable {
	  public ArrayList<User> userList = new ArrayList<>();
	  public static ArrayList<User> uList = new ArrayList<>();
	  public static String recUser;
	  public static final String fileStore = "C:/Users/Shah/workspace/Photos48/adminUsers.txt";
	  private static final long serialVersionUID = 1L;
	  
	  /**
	   * adds in a user
	   * @param userName name for the user
	   * @return
	   */
	  public static int addUser(String userName) {
	        User user = new User(userName);
	        //check if duplicate song name and artist
	        for (int i = 0; i < uList.size(); i++) {
	            if (uList.get(i).user.equals(userName)) {
	                return 1;
	            }
	        }
	        uList.add(user);
	        recUser = userName;
	        sort();
	        return 0;
	  }
	  
	  /**
	   * sorts the users by name
	   */
	  public static void sort() {
	        Collections.sort(uList, (o1,o2) -> o1.user.toLowerCase().compareTo(o2.user.toLowerCase()));
	  }
	  
	  /**
	   * deletes a user
	   * @param index used to find that user to be deleted
	   */
	  public static void deleteSong(int index) {
	        //check if duplicate song name and artist
	        if (uList.size() > 0){
	            uList.remove(index);
	        }
	  }
	  
	  /**
	   * Serializes an Userlist object
	   * @param ul takes a userlist object to be serialized
	   * @throws IOException
	   */
	  public static void writeApp(UserList ul) throws IOException{
	    	//@SuppressWarnings("resource")
		  	FileOutputStream fos = new FileOutputStream("./adminUsers.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for(int i = 0;i<ul.userList.size();i++){
				ul.userList.remove(i);
			}
			for(int i = 0;i<uList.size();i++){
				ul.userList.add(uList.get(i));
			}
			oos.writeObject(ul);
	    	oos.flush();
	    	oos.close();
	  }
	  
	  /**
	   * returns a deserialized Userlist object
	   * @param path takes in a path to find the folders
	   * @return
	   * @throws IOException
	   */
	  public static UserList readApp(String path)  throws IOException{
		 // @SuppressWarnings("resource")
		  FileInputStream fis = new FileInputStream("./adminUsers.txt");
		  ObjectInputStream ois = new ObjectInputStream(fis);
		  UserList d = new UserList();
		  try {
			  d = (UserList)ois.readObject();

			  toList(d);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  fis.close();
		  ois.close();
		  return d;
	 }
	  

	  /**
	   * adds the deserialized users to the arraylist of users
	   * @param u
	   */
	 public static void toList(UserList u){
		 if(uList.isEmpty()){
			 for(int i = 0;i<u.userList.size();i++){
				 addUser(u.userList.get(i).user);
			 }
		 }
		 //System.out.println("A's album" +UserList.uList.get(0).uAlbum.aList.get(0).albumName);
	 }
}