package dal;

public class DALException extends Exception{

	private static final long serialVersionUID = 1L;

	public DALException(Exception exception) {
		super(exception);
	}
}
