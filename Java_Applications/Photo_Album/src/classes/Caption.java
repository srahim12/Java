package classes;

public class Caption implements java.io.Serializable {
	public String caption;
	private static final long serialVersionUID = 1L;
	public Caption(String caption){
		this.caption = caption;
	}
	
	public Caption(){}
	
	public void changeCaption(String caption){
		this.caption = caption;
	}

}