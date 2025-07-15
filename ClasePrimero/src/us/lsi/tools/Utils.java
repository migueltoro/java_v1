package us.lsi.tools;

public class Utils {
	
	public static Boolean allNotNull(Object... objects) {
		for (Object o : objects) {
			if (o == null) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
