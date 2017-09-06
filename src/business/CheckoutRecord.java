package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {

	private static final long serialVersionUID = -3119855589946373695L;
	private List<CheckoutRecordEntry> entries = new ArrayList<>();

	public void addEntry(CheckoutRecordEntry c) {
		entries.add(c);
	}

	public List<CheckoutRecordEntry> getEntries() {
		return entries;
	}

	public String toString() {
		return entries.toString();
	}
}
