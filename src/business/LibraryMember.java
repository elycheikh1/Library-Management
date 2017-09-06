package business;

import java.io.Serializable;
import java.time.LocalDate;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord checkoutRecord;

	public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
		super(fname, lname, tel, add);
		this.memberId = memberId;
		checkoutRecord = new CheckoutRecord();
	}

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}

	public String getMemberId() {
		return memberId;
	}

	public CheckoutRecordEntry checkout(BookCopy bookCopy, LocalDate today, LocalDate dueDate) {
		bookCopy.changeAvailability();
		CheckoutRecordEntry checkoutRecordEntry = new CheckoutRecordEntry(bookCopy, today, dueDate);
		checkoutRecord.addEntry(checkoutRecordEntry);
		return checkoutRecordEntry;
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + ", "
				+ getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
