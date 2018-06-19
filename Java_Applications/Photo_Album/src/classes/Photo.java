package classes;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Intiializes photo object
 * @author John Chen and Shah Rahim
 *
 */
public class Photo implements java.io.Serializable {
	public String strPath;
	public String caption;
	public String date;
	public TagList taList = new TagList();
	public ArrayList<Tag> tags;
	private static final long serialVersionUID = 1L;
	
	/**
	 * creates a photo object
	 * @param strPath path for the photo
	 * @param caption caption for the photo
	 * @param date date for the photo
	 */
	public Photo(String strPath, String caption, String date){
		this.strPath = strPath;
		this.caption = caption;
		this.date = date;
	}
	
	/**
	 * takes a no parameter call for a photo object
	 */
	public Photo(){}
	
	/**
	 * sets the caption for the photo
	 * @param caption text for the photo
	 */
	public void setCaption(String caption){
		this.caption = caption;
	}
	
}
