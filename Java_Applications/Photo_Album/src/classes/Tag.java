package classes;

/**
 * creates a tag object
 * @author John Chen and Shah Rahim
 *
 */
public class Tag implements java.io.Serializable{
	public String name;
	public String value;
	private static final long serialVersionUID = 1L;
	/**
	 * initializes a Tag object
	 * @param name for the tag
	 * @param value for the tag
	 */
	public Tag(String name, String value){
		this.name = name;
		this.value = value;
	}
}
