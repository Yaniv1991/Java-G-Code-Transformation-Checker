import java.util.ArrayList;
import java.util.List;

public class Procedure {

	private List<String> gCode;
	private List<Point3> points;
	public String Name;

	public List<String> GCode() {
		return gCode;
	}

	public List<Point3> Points() {
		return points;
	}

	String[] AuxiliaryCodes = { "G7", "G14", "G116", "G4", "M", "/", "G B C" };

	public Procedure(String name, List<String> gCode) {
		Name = name;
		this.gCode = gCode;
		removeFirstTimeGCodes();
		points = ParseStringToPoint();
	}

	public void removeFirstTimeGCodes() {
		for (int i = 0; i < gCode.size(); i++) {
			if (isAuxiliaryGCode(gCode.get(i))) {
				gCode.remove(i);
				i = -1;
			}
		}
	}

	List<Point3> ParseStringToPoint(){
		List<Point3> result = new ArrayList<Point3>();
		
		Point3 previousPoint = null;
		for (int i = 0; i < gCode.size(); i++) {
			float x = getCoordinate(i,"X");
			float y = getCoordinate(i,"Y");
			float z = getCoordinate(i, "Z");
			if(previousPoint != null) {
				if(x == Float.MIN_VALUE) {
					x = previousPoint.X();
				}
				if(y == Float.MIN_VALUE) {
					y = previousPoint.Y();
				}
				if(z == Float.MIN_VALUE) {
					z = previousPoint.Z();
				}
			
		}
			result.add(new Point3(x,y,z));
			previousPoint = result.get(result.size()-1);
		
	}
		return result;
		}

	private float getCoordinate(int i, String identifier) {
		//
		String line = gCode.get(i);
		float result = Float.MIN_VALUE;
		if (line.contains("G1") || line.contains("G0") || line.contains("G2") || line.contains("G3")) {
			String[] split = line.split(" ");
			for (int j = 0; j < split.length; j++) {
				if (split[j].startsWith(identifier)) {
					result = Float.parseFloat(split[j].replace(identifier.charAt(0), ' '));
				}
			}
		}

		return result;
	}

	private Boolean isAuxiliaryGCode(String str) {
		for (int i = 0; i < AuxiliaryCodes.length; i++) {
			if (str.contains(AuxiliaryCodes[i])) {
				return true;
			}
		}
		return false;
	}
}
