package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	public static String idTemp = null;

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		idTemp = map.get(id).getId();

	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	private void saveNewMember(LibraryMember member) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(member);

	}

	public void addMember(String id, String firstName, String lastName, String street, String city, String state,
			String zip, String telephone) throws AddMemberException {

		if (Rules.isEmpty(id, firstName, lastName, street, city, state, zip, telephone))
			throw new AddMemberException("All fields must be not empty.");

		if (!Rules.isNumeric(id))
			throw new AddMemberException("ID field must be Numeric.");

		if (!Rules.isNumeric(zip)) {
			throw new AddMemberException("Zip field must be Numeric.");
		}

		if (!Rules.isExactLength(zip, 5)) {
			throw new AddMemberException("Zip field must be Numeric with exqctly 5 digits.");
		}
		if (!Rules.isExactLength(state, 2)) {
			throw new AddMemberException("State field must have exactly two characters.");
		}

		if (!Rules.isAllCapitals(state)) {
			throw new AddMemberException("State field must have exactly two characters in the range A-Z.");
		}

		if (Rules.isIdEqualZip(id, zip)) {
			throw new AddMemberException("ID field may not equal zip.");
		}
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(id);
		if (member != null) {
			throw new AddMemberException("Member ID exist please change ID");
		}

		Address newMemberAddress = new Address(street, city, state, zip);
		LibraryMember newMember = new LibraryMember(id, firstName, lastName, telephone, newMemberAddress);
		saveNewMember(newMember);

	}

	public String checkoutBook(String memberId, String isbn) throws CheckoutException {
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(memberId);
		if (member == null) {
			throw new CheckoutException("Member not found.");
		}
		Book book = da.searchBook(isbn);
		if (book == null) {
			throw new CheckoutException("Book not found.");
		}
		// check availability of a book copy
		if (!book.isAvailable()) {
			throw new CheckoutException("No Book copy available.");
		}
		BookCopy bookCopy = book.getNextAvailableCopy();

		// get max number of days for checkout of the book
		int maxCheckoutLength = book.getMaxCheckoutLength();
		LocalDate today = LocalDate.now();
		LocalDate dueDate = LocalDate.now().plusDays(maxCheckoutLength);
		CheckoutRecordEntry ch =member.checkout(bookCopy, today, dueDate);
		da.saveMember(member);
		da.saveBook(book);
		
		return ch.toString();
	}

	public String addBookCopy(String isbn) throws AddCopyException {
		DataAccess da = new DataAccessFacade();
		Book book = da.searchBook(isbn);
		if (book == null) {
			throw new AddCopyException(" Book with isbn '" + isbn + "' is not found.");
		}
		int previous = book.getNumCopies();
		book.addCopy();
		da.saveBook(book);

		return "Successfully Copy Added. previous number of book copy was " + previous + " and now is "
				+ book.getNumCopies();

	}

}
