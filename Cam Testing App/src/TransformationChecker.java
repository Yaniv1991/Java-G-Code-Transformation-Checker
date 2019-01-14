import java.io.IOException;
import java.util.List;

public class TransformationChecker {

	private List<Procedure> toolpath;
	private String path;
	int errors = 0;
	public float XInterval, YInterval, tolerance;

	public TransformationChecker(String path) {
		try {
			this.path = path;
			GCodeTranslator translator = new GCodeTranslator(path);
			toolpath = translator.Toolpath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void CheckLinearTransformation() {
		for (int i = 0; i < toolpath.size() - 1; i++) {
			for (int j = i + 1; j < toolpath.size(); j++) {
				if (toolpath.get(i).Name.contentEquals(toolpath.get(j).Name)) {
					CheckForFixedIntervalOfPoints(toolpath.get(i), toolpath.get(j));
					i++;
					break;
				}
			}
		}
		
		if(errors > 0) {
			System.out.println("There were " + errors + " Errors");
		}
		else {
			System.out.println("There were no errors in " + path);
		}
	}


	
	private void CheckForFixedIntervalOfPoints(Procedure a, Procedure b) {

		if (a.GCode().size() != b.GCode().size()) {
			System.out.println("a size is " + a.GCode().size() + " . b size is " + b.GCode().size());
			System.out.println(a.Name);
			return;
		}

		int mismatches = 0;
		for (int i = 0; i < a.GCode().size(); i++) {
			Point3 pointA = a.Points().get(i);
			Point3 pointB = b.Points().get(i);
			
			float currentXInterval = Math.abs(pointA.X() - pointB.X());
			if (currentXInterval - XInterval > tolerance) { // To Be Better Coded
				System.out.println("Mismatch at line " + i + " Of Procedure" + a.Name);
				System.out.printf("xb = %f , xa = %f ; the interval is %f \n", pointB.X(), pointA.X(), currentXInterval);
				System.out.println(a.GCode().get(i));
				System.out.println(b.GCode().get(i));

				mismatches++;
				errors++;
			}

		}
		if (mismatches > 0) {
			System.out.println("There were " + mismatches + " mismatches");
		}
	}

}

//	private float getCoordinate(Procedure p, int i, String identifier) {
//		//
//		String line = p.GCode().get(i);
//		float result = 0;
//		if (line.contains("G1") || line.contains("G0") || line.contains("G2") || line.contains("G3")) {
//			String[] split = line.split(" ");
//			for (int j = 0; j < split.length; j++) {
//				if (split[j].startsWith(identifier) /* && split[j].length() > 1 */) {
//					result = Float.parseFloat(split[j].replace(identifier.charAt(0), ' '));
//				}
//			}
//		}
//
//		return result;
//	}

//private void CheckForFixedInterval(Procedure a, Procedure b) {
//
//	if (a.GCode().size() != b.GCode().size()) {
//		System.out.println("a size is " + a.GCode().size() + " . b size is " + b.GCode().size());
//		System.out.println(a.Name);
//		return;
//	}
//
//	int mismatches = 0;
//	for (int i = 0; i < a.GCode().size(); i++) {
//		float xa = getCoordinate(a, i, "X");
//		float xb = getCoordinate(b, i, "X");
//		float interval = Math.abs(xb - xa);
//		if (interval - XInterval > tolerance) { // To Be Better Coded
//			System.out.println("Mismatch at line " + i + " Of Procedure" + a.Name);
//			System.out.printf("xb = %f , xa = %f ; the interval is %f \n", xb, xa, interval);
//			System.out.println(a.GCode().get(i));
//			System.out.println(b.GCode().get(i));
//
//			mismatches++;
//		}

//	}
//	if (mismatches > 0) {
//		System.out.println("There were " + mismatches + " mismatches");
//	}
//}
