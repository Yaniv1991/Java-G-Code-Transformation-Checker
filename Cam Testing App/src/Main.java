import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String path = AcceptNewFile();
		TransformationChecker checker = new TransformationChecker(path);
		checker.XInterval = 38f;
		checker.YInterval = 0;
		checker.tolerance = 0.001f;
		checker.CheckLinearTransformation();
	}

	private static String AcceptNewFile() {
		Scanner input = new Scanner(System.in);
		String result = null;
		while (result == null) {
			System.out.println("Enter File path");
			result = input.next();
		}
		input.close();
		return result;

	}

}
