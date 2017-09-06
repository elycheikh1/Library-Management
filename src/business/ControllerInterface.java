package business;

import java.util.List;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;

	public List<String> allMemberIds();

	public List<String> allBookIds();

	public void addMember(String id, String firstName, String lastName, String street, String city, String state,
			String zip, String telephone) throws AddMemberException;

	public String checkoutBook(String memberId, String isbn) throws CheckoutException;

	public String addBookCopy(String isbn) throws AddCopyException;

}
