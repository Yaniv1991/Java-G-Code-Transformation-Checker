import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GCodeTranslator {


	public List<String> GCode;
	
	public GCodeTranslator(String path) throws IOException {
		GCode = Files.readAllLines(Paths.get(path));
		System.out.println("File Loaded");
	}

	public List<Procedure> Toolpath() throws IOException {
		List<Procedure> result = new ArrayList<Procedure>();
		for (int i = 0; i < GCode.size(); i++) {
			if (GCode.get(i).contains("#") && !(GCode.get(i).contains("G4"))) {
				result.add(new Procedure(GCode.get(i),getProcedureText(i + 1)));
			}
		}

		return result;
	}

	private List<String> getProcedureText(int startLine) {
		List<String> result = new ArrayList<String>();
		for (int j = startLine; j < GCode.size(); j++) {
			result.add(GCode.get(j));
			if (GCode.get(j).contains("#")) {
				break;
			}
		}
		return result;
	}
	
	public void showGCode() throws IOException {
		for (Procedure pro : Toolpath()) {
			for (String string : pro.GCode()) {
				System.out.println(string);
			}
			;
		}
	}
}