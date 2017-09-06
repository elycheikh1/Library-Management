package business;

import java.io.Serializable;

public class AddMemberException extends Exception implements Serializable {

	private static final long serialVersionUID = -5387531683856117621L;

	public AddMemberException() {
		super();
	}

	public AddMemberException(String msg) {
		super(msg);
	}

	public AddMemberException(Throwable t) {
		super(t);
	}

}
