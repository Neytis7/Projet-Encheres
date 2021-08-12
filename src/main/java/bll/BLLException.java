package bll;

public class BLLException extends Exception{

	private static final long serialVersionUID = 1L;

	public BLLException(Exception exception) {
		super(exception);
	}	
}
