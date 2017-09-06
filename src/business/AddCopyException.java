package business;

import java.io.Serializable;

public class AddCopyException extends Exception implements Serializable {

	private static final long serialVersionUID = -7027876909343476404L;

	public AddCopyException() {
		super();
	}

	public AddCopyException(String msg) {
		super(msg);
	}

	public AddCopyException(Throwable t) {
		super(t);
	}

}
