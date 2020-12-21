package exceptions;

public class AccountFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7172639594999251975L;

	public AccountFailedException(Exception e) {
		super.addSuppressed(e);
		e.printStackTrace();
	}

}
