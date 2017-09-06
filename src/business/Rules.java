package business;

public class Rules {

	public static boolean isNumeric(String str) {
		if (str.isEmpty())
			return false;
		for (int i = 0; i < str.length(); i++) {

			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;

	}

	public static boolean isEmpty(String id, String firstName, String lastName, String street, String city,
			String state, String zip, String telephone) {
		if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || street.isEmpty() || city.isEmpty()
				|| state.isEmpty() || zip.isEmpty() || telephone.isEmpty())
			return true;
		return false;
	}

	public static boolean isExactLength(String str, int len) {
		if (str.length() == len)
			return true;
		return false;
	}

	public static boolean isAllLetters(String str) {
		if (str.isEmpty())
			return false;
		for (int i = 0; i < str.length(); i++) {

			if (!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}
		return true;

	}

	// A-Z
	public static boolean isAllCapitals(String str) {
		if (str.isEmpty())
			return false;
		for (int i = 0; i < str.length(); i++) {

			if (!Character.isUpperCase(str.charAt(i))) {
				return false;
			}
		}
		return true;

	}

	public static boolean isIdEqualZip(String id, String zip) {
		if (id.equals(zip))
			return true;
		return false;

	}

}
