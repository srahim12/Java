package classes;

/**
 * Album object
 * @author John Chen and Shah Rahim
 *
 */
public class Album implements java.io.Serializable {
	public String albumName;
	public PhotoList phList = new PhotoList();  
	private static final long serialVersionUID = 1L;
	/**
	 * initializes the album object with a string name
	 * @param albumName used to name the album
	 */
	public Album(String albumName){
		this.albumName = albumName;
	}
	
	/**
	 * catches no parameter calls
	 */
	public Album(){}
}
