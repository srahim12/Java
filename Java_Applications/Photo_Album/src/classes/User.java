package classes;

/**
 * Creates a User object
 * @author John Chen and Shah Rahim
 *
 */
public class User implements java.io.Serializable{ 
	public String user;
	public AlbumList uAlbum= new AlbumList();
	private static final long serialVersionUID = -3406254331806172674L;
	
	/** 
	 * creates an user object
	 * @param user is for the user name
	 */
	public User(String user){
		this.user = user;
	}
	
	/**
	 * takes a no parameter call
	 */
	public User(){}
	
}
