import java.util.List;

public class Procedure {

	private List<String> gCode;
	public String Name;
	public List<String> GCode(){
		return gCode;
	}
	
	public Procedure(String name,List<String> gCode) {
		Name = name;
		this.gCode=gCode;
		removeFirstTimeGCodes();
	}
	
	public  void removeFirstTimeGCodes() {
		for (int i = 0; i < gCode.size(); i++) {
			if(gCode.get(i).contains("G7")
					||gCode.get(i).contains("G14")
					||gCode.get(i).contains("G116")
					||gCode.get(i).contains("G4")
					||gCode.get(i).contains("M")
					||gCode.get(i).contains("/")
					||gCode.get(i).contains("G B C")){
				gCode.remove(i);
				i=-1;
			}
		}
	}
}
