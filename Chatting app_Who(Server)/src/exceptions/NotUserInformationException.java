package exceptions;

public class NotUserInformationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -754254556113142051L;

	public NotUserInformationException(Exception e) {
		super.addSuppressed(e);
	}
	
}
