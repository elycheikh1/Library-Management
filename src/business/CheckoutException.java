package business;

import java.io.Serializable;

public class CheckoutException extends Exception implements Serializable {

	private static final long serialVersionUID = 2306121660212166196L;

	public CheckoutException() {
		super();
	}

	public CheckoutException(String msg) {
		super(msg);
	}

	public CheckoutException(Throwable t) {
		super(t);
	}

}
