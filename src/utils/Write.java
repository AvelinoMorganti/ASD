package utils;

public class Write {

	private final int tag;
	private final String value;
	
	public Write(int tag, String value){
		this.tag = tag;
		this.value = value;
	}
	
	public int getTag(){
		return tag;
	}
	
	public String getValue(){
		return value;
	}
}
